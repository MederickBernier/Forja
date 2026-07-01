package io.forja.components.dateAndTime.fxDatePicker;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.inputs.InputVariant;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DateCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A styled date input with optional leading icon, min/max bounds, helper
 * text, and validation-state variants. Wraps a JavaFX {@link DatePicker}.
 *
 * <p>{@code FxDatePicker} is a composite {@link VBox} of two rows:</p>
 * <ol>
 *   <li>A framed {@link HBox} carrying the leading icon (if any) and the
 *       inner {@link DatePicker} (editor + calendar popup toggle).</li>
 *   <li>A {@link FxLabel} carrying the helper or error text — auto-hidden
 *       when both are empty.</li>
 * </ol>
 *
 * <p>The {@link #valueProperty value} is a bidirectional bridge to the inner
 * picker's {@code valueProperty}. When {@link #setMin} / {@link #setMax} are
 * set (inclusive), the calendar disables out-of-range cells; typed values
 * outside the range are clamped on commit.
 *
 * <p>The display and parse format is controlled by {@link #setFormatPattern}
 * (default {@code "yyyy-MM-dd"}). Unparseable text reverts to the previous
 * value on commit.
 *
 * <p>Setting {@link #setErrorText} to a non-empty value auto-flips the variant
 * to {@link InputVariant#ERROR}; clearing does not auto-revert.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxDatePicker due = FxDatePicker.builder()
 *          .value(LocalDate.now())
 *          .min(LocalDate.now())
 *          .formatPattern("yyyy-MM-dd")
 *          .leadingIcon("fth-calendar")
 *          .helperText("Pick a due date.")
 *          .build();
 *     }
 * </pre>
 *
 * @see InputVariant
 * @see Builder
 */
public class FxDatePicker extends VBox {

    private static final PseudoClass DEFAULT_PC = PseudoClass.getPseudoClass("default");
    private static final PseudoClass ERROR_PC   = PseudoClass.getPseudoClass("error");
    private static final PseudoClass SUCCESS_PC = PseudoClass.getPseudoClass("success");
    private static final PseudoClass FOCUSED_PC = PseudoClass.getPseudoClass("focused");

    private final DatePicker datePicker = new DatePicker();
    private final HBox fieldRow = new HBox();
    private final FxLabel helperLabel = new FxLabel("", LabelVariant.SMALL);

    private final ObjectProperty<InputVariant> variant = new SimpleObjectProperty<>(this, "variant", InputVariant.DEFAULT);
    private final ObjectProperty<Node> leadingIcon = new SimpleObjectProperty<>(this, "leadingIcon");
    private final ObjectProperty<LocalDate> value = new SimpleObjectProperty<>(this, "value");
    private final ObjectProperty<LocalDate> min = new SimpleObjectProperty<>(this, "min");
    private final ObjectProperty<LocalDate> max = new SimpleObjectProperty<>(this, "max");
    private final StringProperty formatPattern = new SimpleStringProperty(this, "formatPattern", "yyyy-MM-dd");
    private final StringProperty helperText = new SimpleStringProperty(this, "helperText", "");
    private final StringProperty errorText = new SimpleStringProperty(this, "errorText", "");
    private final BooleanProperty showingError = new SimpleBooleanProperty(this, "showingError", false);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private boolean syncing = false;

    /**
     * Creates an empty {@code FxDatePicker} with the default variant.
     */
    public FxDatePicker() {
        super();
        getStyleClass().add("forja-date-picker");
        setSpacing(4);

        fieldRow.getStyleClass().add("forja-date-picker-row");
        fieldRow.setAlignment(Pos.CENTER_LEFT);
        fieldRow.setSpacing(8);

        datePicker.getStyleClass().add("forja-date-picker-inner");
        datePicker.setConverter(buildConverter());
        datePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                LocalDate lo = getMin();
                LocalDate hi = getMax();
                boolean disabled =
                        (lo != null && item != null && item.isBefore(lo)) ||
                        (hi != null && item != null && item.isAfter(hi));
                setDisable(empty || disabled);
            }
        });
        HBox.setHgrow(datePicker, Priority.ALWAYS);

        helperLabel.getStyleClass().add("forja-date-picker-helper");
        helperLabel.setMuted(true);
        helperLabel.setVisible(false);
        helperLabel.setManaged(false);

        getChildren().addAll(fieldRow, helperLabel);

        variant.addListener((obs, o, v) -> applyVariantPseudoClass());
        leadingIcon.addListener((obs, o, v) -> rebuildRow());
        formatPattern.addListener((obs, o, v) -> {
            formatter = DateTimeFormatter.ofPattern(v == null || v.isEmpty() ? "yyyy-MM-dd" : v);
            datePicker.setConverter(buildConverter());
        });

        value.addListener((obs, o, v) -> {
            if (syncing) return;
            syncing = true;
            try { datePicker.setValue(clamp(v)); } finally { syncing = false; }
        });
        datePicker.valueProperty().addListener((obs, o, v) -> {
            if (syncing) return;
            syncing = true;
            try {
                LocalDate clamped = clamp(v);
                if (clamped != v) datePicker.setValue(clamped);
                value.set(clamped);
            } finally { syncing = false; }
        });

        min.addListener((obs, o, v) -> { LocalDate cur = getValue(); if (cur != null) setValue(clamp(cur)); });
        max.addListener((obs, o, v) -> { LocalDate cur = getValue(); if (cur != null) setValue(clamp(cur)); });

        helperText.addListener((obs, o, v) -> refreshHelper());
        errorText.addListener((obs, o, v) -> {
            String e = v == null ? "" : v;
            if (!e.isEmpty()) setVariant(InputVariant.ERROR);
            refreshHelper();
        });

        datePicker.focusedProperty().addListener((obs, o, focused) ->
                pseudoClassStateChanged(FOCUSED_PC, focused));

        applyVariantPseudoClass();
        rebuildRow();
        refreshHelper();
    }

    /**
     * Creates an {@code FxDatePicker} initialized to the given value.
     *
     * @param value initial date, or {@code null} for empty
     */
    public FxDatePicker(LocalDate value) {
        this();
        setValue(value);
    }

    private StringConverter<LocalDate> buildConverter() {
        return new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate d) {
                return d == null ? "" : formatter.format(d);
            }
            @Override
            public LocalDate fromString(String s) {
                if (s == null || s.trim().isEmpty()) return null;
                try { return LocalDate.parse(s.trim(), formatter); }
                catch (DateTimeParseException ex) { return datePicker.getValue(); }
            }
        };
    }

    private LocalDate clamp(LocalDate v) {
        if (v == null) return null;
        LocalDate lo = getMin();
        LocalDate hi = getMax();
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
        fieldRow.getChildren().add(datePicker);
    }

    private void refreshHelper() {
        String err = getErrorText();
        String help = getHelperText();
        boolean hasError = err != null && !err.isEmpty();
        boolean hasHelper = help != null && !help.isEmpty();
        if (hasError) {
            helperLabel.setText(err);
            helperLabel.setMuted(false);
            if (!helperLabel.getStyleClass().contains("forja-date-picker-error")) {
                helperLabel.getStyleClass().add("forja-date-picker-error");
            }
            showingError.set(true);
        } else if (hasHelper) {
            helperLabel.setText(help);
            helperLabel.setMuted(true);
            helperLabel.getStyleClass().remove("forja-date-picker-error");
            showingError.set(false);
        } else {
            helperLabel.setText("");
            showingError.set(false);
        }
        boolean visible = hasError || hasHelper;
        helperLabel.setVisible(visible);
        helperLabel.setManaged(visible);
    }

    /** Returns the underlying {@link DatePicker} for advanced binding. */
    public DatePicker getDatePicker() { return datePicker; }

    /** Returns the helper/error label node. */
    public FxLabel getHelperLabel() { return helperLabel; }

    /** Returns the field-row {@link HBox}. */
    public HBox getFieldRow() { return fieldRow; }

    /** Returns the value property. Holds {@code null} when empty. */
    public ObjectProperty<LocalDate> valueProperty() { return value; }

    /** Returns the current selected date, or {@code null}. */
    public LocalDate getValue() { return value.get(); }

    /** Sets the value; {@code null} clears. Clamped to {@code [min, max]}. */
    public void setValue(LocalDate v) { value.set(clamp(v)); }

    /** Returns the min property (inclusive, {@code null} = unbounded). */
    public ObjectProperty<LocalDate> minProperty() { return min; }

    /** Returns the current min bound. */
    public LocalDate getMin() { return min.get(); }

    /** Sets the inclusive minimum; {@code null} for unbounded. */
    public void setMin(LocalDate v) { min.set(v); }

    /** Returns the max property (inclusive, {@code null} = unbounded). */
    public ObjectProperty<LocalDate> maxProperty() { return max; }

    /** Returns the current max bound. */
    public LocalDate getMax() { return max.get(); }

    /** Sets the inclusive maximum; {@code null} for unbounded. */
    public void setMax(LocalDate v) { max.set(v); }

    /** Returns the format-pattern property. */
    public StringProperty formatPatternProperty() { return formatPattern; }

    /** Returns the current display/parse pattern. */
    public String getFormatPattern() { return formatPattern.get(); }

    /**
     * Sets the pattern used for display and parsing (see
     * {@link DateTimeFormatter#ofPattern(String)}). Default {@code "yyyy-MM-dd"}.
     */
    public void setFormatPattern(String pattern) {
        formatPattern.set(pattern == null || pattern.isEmpty() ? "yyyy-MM-dd" : pattern);
    }

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

    /** Returns the prompt-text property (delegates to the inner editor). */
    public StringProperty promptTextProperty() { return datePicker.promptTextProperty(); }

    /** Returns the current prompt text. */
    public String getPromptText() { return datePicker.getPromptText(); }

    /** Sets the prompt text shown when empty. */
    public void setPromptText(String v) { datePicker.setPromptText(v); }

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
     * Returns a new {@link Builder} for constructing an {@code FxDatePicker}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxDatePicker}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>value — {@code null}</li>
     *   <li>min / max — {@code null} (unbounded)</li>
     *   <li>formatPattern — {@code "yyyy-MM-dd"}</li>
     *   <li>variant — {@link InputVariant#DEFAULT}</li>
     *   <li>leadingIcon — none</li>
     *   <li>helperText / errorText — empty (line hidden)</li>
     *   <li>showWeekNumbers — {@code false}</li>
     *   <li>editable — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxDatePicker, Builder> {

        private LocalDate value;
        private LocalDate min;
        private LocalDate max;
        private String formatPattern = "yyyy-MM-dd";
        private InputVariant variant = InputVariant.DEFAULT;
        private Node leadingIcon;
        private String promptText = "";
        private String helperText = "";
        private String errorText = "";
        private boolean showWeekNumbers = false;
        private boolean editable = true;

        public Builder value(LocalDate value) {
            this.value = value;
            return this;
        }

        public Builder min(LocalDate min) {
            this.min = min;
            return this;
        }

        public Builder max(LocalDate max) {
            this.max = max;
            return this;
        }

        public Builder formatPattern(String pattern) {
            this.formatPattern = (pattern == null || pattern.isEmpty()) ? "yyyy-MM-dd" : pattern;
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

        public Builder promptText(String promptText) {
            this.promptText = promptText == null ? "" : promptText;
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

        public Builder showWeekNumbers(boolean showWeekNumbers) {
            this.showWeekNumbers = showWeekNumbers;
            return this;
        }

        public Builder editable(boolean editable) {
            this.editable = editable;
            return this;
        }

        @Override
        public FxDatePicker build() {
            FxDatePicker field = new FxDatePicker();
            field.setFormatPattern(formatPattern);
            field.setMin(min);
            field.setMax(max);
            field.setLeadingIcon(leadingIcon);
            field.setPromptText(promptText);
            field.setHelperText(helperText);
            // Variant before errorText so auto-flip wins.
            field.setVariant(variant);
            field.setValue(value);
            field.setErrorText(errorText);
            field.getDatePicker().setShowWeekNumbers(showWeekNumbers);
            field.getDatePicker().setEditable(editable);
            applyBase(field);
            return field;
        }
    }
}
