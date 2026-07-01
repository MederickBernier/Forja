package io.forja.components.dateAndTime.fxTimePicker;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.inputs.InputVariant;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalTime;

/**
 * A styled time input with hour and minute spinners, optional seconds, and an
 * optional 12-hour AM/PM toggle. Layout mirrors {@code FxTextField} — a
 * framed row on top, helper/error label beneath.
 *
 * <p>{@code FxTimePicker} is a composite {@link VBox} of two rows:</p>
 * <ol>
 *   <li>A framed {@link HBox} carrying the leading icon (if any), the hour
 *       {@link Spinner}, {@code ":"} separator, minute {@link Spinner},
 *       optional {@code ":"} + second {@link Spinner}, and optional AM/PM
 *       toggle {@link Label}.</li>
 *   <li>A {@link FxLabel} carrying the helper or error text — auto-hidden
 *       when both are empty.</li>
 * </ol>
 *
 * <p>The {@link #valueProperty value} is an {@link ObjectProperty} holding a
 * {@link LocalTime} or {@code null} for empty. Spinner edits update value in
 * real time. Hour range is {@code 0..23} for 24-hour mode or {@code 1..12}
 * for 12-hour mode. Minutes wrap by {@link #getStepMinutes} (default 1).
 *
 * <p>{@link #setMin} / {@link #setMax} clamp the returned value on set (the
 * spinners themselves stay full-range; clamp is applied on assignment). When
 * seconds are hidden ({@link #isShowSeconds} == false), the seconds field
 * is fixed to {@code 0}.
 *
 * <p>Setting {@link #setErrorText} to a non-empty value auto-flips the variant
 * to {@link InputVariant#ERROR}; clearing does not auto-revert.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxTimePicker t = FxTimePicker.builder()
 *          .value(LocalTime.of(9, 30))
 *          .use24Hour(false)
 *          .stepMinutes(5)
 *          .leadingIcon("fth-clock")
 *          .helperText("Meeting time.")
 *          .build();
 *     }
 * </pre>
 *
 * @see InputVariant
 * @see Builder
 */
public class FxTimePicker extends VBox {

    private static final PseudoClass DEFAULT_PC = PseudoClass.getPseudoClass("default");
    private static final PseudoClass ERROR_PC   = PseudoClass.getPseudoClass("error");
    private static final PseudoClass SUCCESS_PC = PseudoClass.getPseudoClass("success");
    private static final PseudoClass FOCUSED_PC = PseudoClass.getPseudoClass("focused");

    private final Spinner<Integer> hourSpinner = new Spinner<>();
    private final Spinner<Integer> minuteSpinner = new Spinner<>();
    private final Spinner<Integer> secondSpinner = new Spinner<>();
    private final Label sepHm = new Label(":");
    private final Label sepMs = new Label(":");
    private final Label amPmLabel = new Label("AM");
    private final HBox fieldRow = new HBox();
    private final FxLabel helperLabel = new FxLabel("", LabelVariant.SMALL);

    private final ObjectProperty<InputVariant> variant = new SimpleObjectProperty<>(this, "variant", InputVariant.DEFAULT);
    private final ObjectProperty<Node> leadingIcon = new SimpleObjectProperty<>(this, "leadingIcon");
    private final ObjectProperty<LocalTime> value = new SimpleObjectProperty<>(this, "value");
    private final ObjectProperty<LocalTime> min = new SimpleObjectProperty<>(this, "min");
    private final ObjectProperty<LocalTime> max = new SimpleObjectProperty<>(this, "max");
    private final BooleanProperty use24Hour = new SimpleBooleanProperty(this, "use24Hour", true);
    private final BooleanProperty showSeconds = new SimpleBooleanProperty(this, "showSeconds", false);
    private final IntegerProperty stepMinutes = new SimpleIntegerProperty(this, "stepMinutes", 1);
    private final StringProperty helperText = new SimpleStringProperty(this, "helperText", "");
    private final StringProperty errorText = new SimpleStringProperty(this, "errorText", "");
    private final BooleanProperty showingError = new SimpleBooleanProperty(this, "showingError", false);

    private boolean syncing = false;

    /**
     * Creates an empty {@code FxTimePicker} with the default variant.
     */
    public FxTimePicker() {
        super();
        getStyleClass().add("forja-time-picker");
        setSpacing(4);

        fieldRow.getStyleClass().add("forja-time-picker-row");
        fieldRow.setAlignment(Pos.CENTER_LEFT);
        fieldRow.setSpacing(4);

        configureSpinner(hourSpinner, 0, 23, 0);
        configureSpinner(minuteSpinner, 0, 59, 0);
        configureSpinner(secondSpinner, 0, 59, 0);
        hourSpinner.getStyleClass().add("forja-time-picker-spinner");
        minuteSpinner.getStyleClass().add("forja-time-picker-spinner");
        secondSpinner.getStyleClass().add("forja-time-picker-spinner");

        sepHm.getStyleClass().add("forja-time-picker-sep");
        sepMs.getStyleClass().add("forja-time-picker-sep");
        amPmLabel.getStyleClass().add("forja-time-picker-ampm");
        amPmLabel.setOnMouseClicked(e -> {
            if (!isUse24Hour()) toggleAmPm();
            e.consume();
        });

        helperLabel.getStyleClass().add("forja-time-picker-helper");
        helperLabel.setMuted(true);
        helperLabel.setVisible(false);
        helperLabel.setManaged(false);

        getChildren().addAll(fieldRow, helperLabel);

        variant.addListener((obs, o, v) -> applyVariantPseudoClass());
        leadingIcon.addListener((obs, o, v) -> rebuildRow());
        use24Hour.addListener((obs, o, v) -> {
            reconfigureHourRange();
            rebuildRow();
            syncFromValue();
        });
        showSeconds.addListener((obs, o, v) -> {
            rebuildRow();
            if (!v) { syncing = true; try { secondSpinner.getValueFactory().setValue(0); } finally { syncing = false; } }
            recomputeValueFromSpinners();
        });
        stepMinutes.addListener((obs, o, v) -> {
            SpinnerValueFactory.IntegerSpinnerValueFactory f =
                    (SpinnerValueFactory.IntegerSpinnerValueFactory) minuteSpinner.getValueFactory();
            f.setAmountToStepBy(Math.max(1, v.intValue()));
        });

        value.addListener((obs, o, v) -> syncFromValue());
        min.addListener((obs, o, v) -> { LocalTime cur = getValue(); if (cur != null) setValue(clamp(cur)); });
        max.addListener((obs, o, v) -> { LocalTime cur = getValue(); if (cur != null) setValue(clamp(cur)); });

        hourSpinner.valueProperty().addListener((obs, o, v) -> recomputeValueFromSpinners());
        minuteSpinner.valueProperty().addListener((obs, o, v) -> recomputeValueFromSpinners());
        secondSpinner.valueProperty().addListener((obs, o, v) -> recomputeValueFromSpinners());

        helperText.addListener((obs, o, v) -> refreshHelper());
        errorText.addListener((obs, o, v) -> {
            String e = v == null ? "" : v;
            if (!e.isEmpty()) setVariant(InputVariant.ERROR);
            refreshHelper();
        });

        hourSpinner.focusedProperty().addListener((obs, o, f) -> updateFocused());
        minuteSpinner.focusedProperty().addListener((obs, o, f) -> updateFocused());
        secondSpinner.focusedProperty().addListener((obs, o, f) -> updateFocused());

        applyVariantPseudoClass();
        rebuildRow();
        refreshHelper();
    }

    /**
     * Creates an {@code FxTimePicker} initialized to the given time.
     *
     * @param value initial time, or {@code null} for empty
     */
    public FxTimePicker(LocalTime value) {
        this();
        setValue(value);
    }

    private void configureSpinner(Spinner<Integer> s, int min, int max, int initial) {
        SpinnerValueFactory.IntegerSpinnerValueFactory f =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initial);
        f.setWrapAround(true);
        s.setValueFactory(f);
        s.setEditable(true);
        s.setPrefWidth(56);
    }

    private void reconfigureHourRange() {
        Integer keep = hourSpinner.getValue();
        SpinnerValueFactory.IntegerSpinnerValueFactory f =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) hourSpinner.getValueFactory();
        if (isUse24Hour()) {
            f.setMin(0);
            f.setMax(23);
            if (keep == null || keep < 0 || keep > 23) f.setValue(0);
        } else {
            f.setMin(1);
            f.setMax(12);
            if (keep == null || keep < 1 || keep > 12) f.setValue(12);
        }
    }

    private void toggleAmPm() {
        amPmLabel.setText("AM".equals(amPmLabel.getText()) ? "PM" : "AM");
        recomputeValueFromSpinners();
    }

    private void updateFocused() {
        boolean any = hourSpinner.isFocused() || minuteSpinner.isFocused() || secondSpinner.isFocused();
        pseudoClassStateChanged(FOCUSED_PC, any);
    }

    private void syncFromValue() {
        if (syncing) return;
        syncing = true;
        try {
            LocalTime v = getValue();
            if (v == null) {
                hourSpinner.getValueFactory().setValue(isUse24Hour() ? 0 : 12);
                minuteSpinner.getValueFactory().setValue(0);
                secondSpinner.getValueFactory().setValue(0);
                amPmLabel.setText("AM");
                return;
            }
            int h24 = v.getHour();
            if (isUse24Hour()) {
                hourSpinner.getValueFactory().setValue(h24);
            } else {
                int h12 = h24 % 12;
                if (h12 == 0) h12 = 12;
                hourSpinner.getValueFactory().setValue(h12);
                amPmLabel.setText(h24 < 12 ? "AM" : "PM");
            }
            minuteSpinner.getValueFactory().setValue(v.getMinute());
            secondSpinner.getValueFactory().setValue(isShowSeconds() ? v.getSecond() : 0);
        } finally {
            syncing = false;
        }
    }

    private void recomputeValueFromSpinners() {
        if (syncing) return;
        Integer h = hourSpinner.getValue();
        Integer m = minuteSpinner.getValue();
        Integer s = secondSpinner.getValue();
        if (h == null || m == null) return;
        int hour24;
        if (isUse24Hour()) {
            hour24 = h;
        } else {
            int h12 = h % 12;
            hour24 = h12 + ("PM".equals(amPmLabel.getText()) ? 12 : 0);
        }
        LocalTime t = LocalTime.of(
                Math.max(0, Math.min(23, hour24)),
                Math.max(0, Math.min(59, m)),
                isShowSeconds() ? Math.max(0, Math.min(59, s == null ? 0 : s)) : 0);
        syncing = true;
        try { value.set(clamp(t)); } finally { syncing = false; }
    }

    private LocalTime clamp(LocalTime v) {
        if (v == null) return null;
        LocalTime lo = getMin();
        LocalTime hi = getMax();
        if (lo != null && v.isBefore(lo)) return lo;
        if (hi != null && v.isAfter(hi))  return hi;
        return v;
    }

    private void applyVariantPseudoClass() {
        pseudoClassStateChanged(DEFAULT_PC, false);
        pseudoClassStateChanged(ERROR_PC,   false);
        pseudoClassStateChanged(SUCCESS_PC, false);

        switch (getVariant()) {
            case DEFAULT: pseudoClassStateChanged(DEFAULT_PC, true); break;
            case ERROR:   pseudoClassStateChanged(ERROR_PC,   true); break;
            case SUCCESS: pseudoClassStateChanged(SUCCESS_PC, true); break;
        }
    }

    private void rebuildRow() {
        fieldRow.getChildren().clear();
        Node lead = getLeadingIcon();
        if (lead != null) fieldRow.getChildren().add(lead);
        fieldRow.getChildren().addAll(hourSpinner, sepHm, minuteSpinner);
        if (isShowSeconds()) fieldRow.getChildren().addAll(sepMs, secondSpinner);
        if (!isUse24Hour()) fieldRow.getChildren().add(amPmLabel);
    }

    private void refreshHelper() {
        String err = getErrorText();
        String help = getHelperText();
        boolean hasError = err != null && !err.isEmpty();
        boolean hasHelper = help != null && !help.isEmpty();
        if (hasError) {
            helperLabel.setText(err);
            helperLabel.setMuted(false);
            if (!helperLabel.getStyleClass().contains("forja-time-picker-error")) {
                helperLabel.getStyleClass().add("forja-time-picker-error");
            }
            showingError.set(true);
        } else if (hasHelper) {
            helperLabel.setText(help);
            helperLabel.setMuted(true);
            helperLabel.getStyleClass().remove("forja-time-picker-error");
            showingError.set(false);
        } else {
            helperLabel.setText("");
            showingError.set(false);
        }
        boolean visible = hasError || hasHelper;
        helperLabel.setVisible(visible);
        helperLabel.setManaged(visible);
    }

    /** Returns the hour {@link Spinner}. */
    public Spinner<Integer> getHourSpinner() { return hourSpinner; }

    /** Returns the minute {@link Spinner}. */
    public Spinner<Integer> getMinuteSpinner() { return minuteSpinner; }

    /** Returns the second {@link Spinner}. */
    public Spinner<Integer> getSecondSpinner() { return secondSpinner; }

    /** Returns the AM/PM toggle label. */
    public Label getAmPmLabel() { return amPmLabel; }

    /** Returns the field-row {@link HBox}. */
    public HBox getFieldRow() { return fieldRow; }

    /** Returns the helper/error label node. */
    public FxLabel getHelperLabel() { return helperLabel; }

    /** Returns the value property. Holds {@code null} when empty. */
    public ObjectProperty<LocalTime> valueProperty() { return value; }

    /** Returns the current time, or {@code null}. */
    public LocalTime getValue() { return value.get(); }

    /** Sets the value; {@code null} clears. Clamped to {@code [min, max]}. */
    public void setValue(LocalTime v) { value.set(clamp(v)); }

    /** Returns the min property (inclusive, {@code null} = unbounded). */
    public ObjectProperty<LocalTime> minProperty() { return min; }

    /** Returns the current min bound. */
    public LocalTime getMin() { return min.get(); }

    /** Sets the inclusive minimum; {@code null} for unbounded. */
    public void setMin(LocalTime v) { min.set(v); }

    /** Returns the max property (inclusive, {@code null} = unbounded). */
    public ObjectProperty<LocalTime> maxProperty() { return max; }

    /** Returns the current max bound. */
    public LocalTime getMax() { return max.get(); }

    /** Sets the inclusive maximum; {@code null} for unbounded. */
    public void setMax(LocalTime v) { max.set(v); }

    /** Returns the use-24-hour property (default {@code true}). */
    public BooleanProperty use24HourProperty() { return use24Hour; }

    /** Returns whether the picker is in 24-hour mode. */
    public boolean isUse24Hour() { return use24Hour.get(); }

    /** Sets whether the picker is in 24-hour mode; {@code false} shows AM/PM. */
    public void setUse24Hour(boolean v) { use24Hour.set(v); }

    /** Returns the show-seconds property. */
    public BooleanProperty showSecondsProperty() { return showSeconds; }

    /** Returns whether the seconds field is shown. */
    public boolean isShowSeconds() { return showSeconds.get(); }

    /** Sets whether the seconds field is shown. */
    public void setShowSeconds(boolean v) { showSeconds.set(v); }

    /** Returns the step-minutes property (default 1). */
    public IntegerProperty stepMinutesProperty() { return stepMinutes; }

    /** Returns the current minute step amount. */
    public int getStepMinutes() { return stepMinutes.get(); }

    /** Sets the minute spinner step amount (clamped to {@code >= 1}). */
    public void setStepMinutes(int v) { stepMinutes.set(Math.max(1, v)); }

    /** Returns the variant property. */
    public ObjectProperty<InputVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public InputVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(InputVariant v) { variant.set(v); }

    /** Returns the leading-icon slot property. */
    public ObjectProperty<Node> leadingIconProperty() { return leadingIcon; }

    /** Returns the leading icon node, or {@code null}. */
    public Node getLeadingIcon() { return leadingIcon.get(); }

    /** Sets the leading icon node. Pass {@code null} to clear. */
    public void setLeadingIcon(Node node) { leadingIcon.set(node); }

    /** Returns the helper-text property. */
    public StringProperty helperTextProperty() { return helperText; }

    /** Returns the current helper text. */
    public String getHelperText() { return helperText.get(); }

    /** Sets the helper text. Empty/null hides the helper line. */
    public void setHelperText(String v) { helperText.set(v == null ? "" : v); }

    /** Returns the error-text property. */
    public StringProperty errorTextProperty() { return errorText; }

    /** Returns the current error text. */
    public String getErrorText() { return errorText.get(); }

    /**
     * Sets the error text. Non-empty value auto-flips the variant to
     * {@link InputVariant#ERROR}; clearing does not auto-revert.
     */
    public void setErrorText(String v) { errorText.set(v == null ? "" : v); }

    /** Returns whether the helper label is currently showing the error text. */
    public boolean isShowingError() { return showingError.get(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxTimePicker}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxTimePicker}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>value — {@code null}</li>
     *   <li>min / max — {@code null} (unbounded)</li>
     *   <li>use24Hour — {@code true}</li>
     *   <li>showSeconds — {@code false}</li>
     *   <li>stepMinutes — {@code 1}</li>
     *   <li>variant — {@link InputVariant#DEFAULT}</li>
     *   <li>leadingIcon — none</li>
     *   <li>helperText / errorText — empty (line hidden)</li>
     *   <li>editable — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxTimePicker, Builder> {

        private LocalTime value;
        private LocalTime min;
        private LocalTime max;
        private boolean use24Hour = true;
        private boolean showSeconds = false;
        private int stepMinutes = 1;
        private InputVariant variant = InputVariant.DEFAULT;
        private Node leadingIcon;
        private String helperText = "";
        private String errorText = "";
        private boolean editable = true;

        public Builder value(LocalTime value) {
            this.value = value;
            return this;
        }

        public Builder min(LocalTime min) {
            this.min = min;
            return this;
        }

        public Builder max(LocalTime max) {
            this.max = max;
            return this;
        }

        public Builder use24Hour(boolean use24Hour) {
            this.use24Hour = use24Hour;
            return this;
        }

        public Builder showSeconds(boolean showSeconds) {
            this.showSeconds = showSeconds;
            return this;
        }

        public Builder stepMinutes(int stepMinutes) {
            this.stepMinutes = Math.max(1, stepMinutes);
            return this;
        }

        public Builder variant(InputVariant variant) {
            this.variant = variant;
            return this;
        }

        /** Sets a pre-built {@link Node} as the leading icon. */
        public Builder leadingIcon(Node node) {
            this.leadingIcon = node;
            return this;
        }

        /** Sugar for {@link #leadingIcon(Node)} using an Ikonli literal. */
        public Builder leadingIcon(String iconLiteral) {
            this.leadingIcon = new FxIcon(iconLiteral);
            return this;
        }

        public Builder helperText(String helperText) {
            this.helperText = helperText == null ? "" : helperText;
            return this;
        }

        public Builder errorText(String errorText) {
            this.errorText = errorText == null ? "" : errorText;
            return this;
        }

        public Builder editable(boolean editable) {
            this.editable = editable;
            return this;
        }

        @Override
        public FxTimePicker build() {
            FxTimePicker field = new FxTimePicker();
            // Shape mode before value so hour range + AM/PM sync correctly.
            field.setUse24Hour(use24Hour);
            field.setShowSeconds(showSeconds);
            field.setStepMinutes(stepMinutes);
            field.setMin(min);
            field.setMax(max);
            field.setLeadingIcon(leadingIcon);
            field.setHelperText(helperText);
            field.setVariant(variant);
            field.setValue(value);
            field.setErrorText(errorText);
            field.getHourSpinner().setEditable(editable);
            field.getMinuteSpinner().setEditable(editable);
            field.getSecondSpinner().setEditable(editable);
            applyBase(field);
            return field;
        }
    }
}
