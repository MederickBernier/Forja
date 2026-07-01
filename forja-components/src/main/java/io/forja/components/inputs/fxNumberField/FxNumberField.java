package io.forja.components.inputs.fxNumberField;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.inputs.InputVariant;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * A styled numeric input with optional leading icon, prefix/suffix affixes,
 * increment/decrement steppers, and validation-state variants.
 *
 * <p>{@code FxNumberField} is a composite {@link VBox} of two rows:</p>
 * <ol>
 *   <li>A framed {@link HBox} carrying the leading icon (if any), an optional
 *       prefix {@link FxLabel}, the inner {@link TextField}, an optional
 *       suffix {@link FxLabel}, and a stepper column with up/down icons
 *       (when {@code showSteppers} is on).</li>
 *   <li>A {@link FxLabel} carrying the helper or error text — auto-hidden
 *       when both are empty.</li>
 * </ol>
 *
 * <p>The {@link #valueProperty value} is an {@link ObjectProperty} holding a
 * {@link Double} or {@code null} when the field is empty. Typing is filtered
 * against a regex that admits the sign, digits, and up to {@link #getDecimals}
 * fractional places. On focus loss or {@code ENTER}, the current text is
 * parsed, clamped to {@code [min, max]}, and re-formatted; unparseable input
 * reverts to the last committed value.
 *
 * <p>Setting {@link #setErrorText} to a non-empty value auto-flips the variant
 * to {@link InputVariant#ERROR}; clearing it does not auto-revert.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxNumberField price = FxNumberField.builder()
 *          .value(9.99)
 *          .min(0.0)
 *          .max(1000.0)
 *          .step(0.25)
 *          .decimals(2)
 *          .prefix("$")
 *          .showSteppers(true)
 *          .helperText("Retail price, taxes excluded.")
 *          .build();
 *     }
 * </pre>
 *
 * @see InputVariant
 * @see Builder
 */
public class FxNumberField extends VBox {

    private static final PseudoClass DEFAULT_PC = PseudoClass.getPseudoClass("default");
    private static final PseudoClass ERROR_PC   = PseudoClass.getPseudoClass("error");
    private static final PseudoClass SUCCESS_PC = PseudoClass.getPseudoClass("success");
    private static final PseudoClass FOCUSED_PC = PseudoClass.getPseudoClass("focused");

    private static final String STEPPER_UP_ICON   = "fth-chevron-up";
    private static final String STEPPER_DOWN_ICON = "fth-chevron-down";

    private final TextField textField = new TextField();
    private final HBox fieldRow = new HBox();
    private final FxLabel prefixLabel = new FxLabel("", LabelVariant.BODY);
    private final FxLabel suffixLabel = new FxLabel("", LabelVariant.BODY);
    private final FxIcon upIcon = new FxIcon(STEPPER_UP_ICON);
    private final FxIcon downIcon = new FxIcon(STEPPER_DOWN_ICON);
    private final VBox stepperColumn = new VBox(upIcon, downIcon);
    private final FxLabel helperLabel = new FxLabel("", LabelVariant.SMALL);

    private final ObjectProperty<InputVariant> variant = new SimpleObjectProperty<>(this, "variant", InputVariant.DEFAULT);
    private final ObjectProperty<Node> leadingIcon = new SimpleObjectProperty<>(this, "leadingIcon");
    private final ObjectProperty<Double> value = new SimpleObjectProperty<>(this, "value", null);
    private final ObjectProperty<Double> min = new SimpleObjectProperty<>(this, "min", null);
    private final ObjectProperty<Double> max = new SimpleObjectProperty<>(this, "max", null);
    private final DoubleProperty step = new SimpleDoubleProperty(this, "step", 1.0);
    private final IntegerProperty decimals = new SimpleIntegerProperty(this, "decimals", 0);
    private final StringProperty prefix = new SimpleStringProperty(this, "prefix", "");
    private final StringProperty suffix = new SimpleStringProperty(this, "suffix", "");
    private final BooleanProperty showSteppers = new SimpleBooleanProperty(this, "showSteppers", false);
    private final StringProperty helperText = new SimpleStringProperty(this, "helperText", "");
    private final StringProperty errorText = new SimpleStringProperty(this, "errorText", "");
    private final BooleanProperty showingError = new SimpleBooleanProperty(this, "showingError", false);

    private DecimalFormat format = buildFormat(0);
    private boolean syncingText = false;

    /**
     * Creates an empty {@code FxNumberField} with the default variant.
     */
    public FxNumberField() {
        super();
        getStyleClass().add("forja-number-field");
        setSpacing(4);

        fieldRow.getStyleClass().add("forja-number-field-row");
        fieldRow.setAlignment(Pos.CENTER_LEFT);
        fieldRow.setSpacing(8);

        textField.getStyleClass().add("forja-number-field-inner");
        HBox.setHgrow(textField, Priority.ALWAYS);
        textField.setTextFormatter(new TextFormatter<>(inputFilter()));

        prefixLabel.getStyleClass().add("forja-number-field-affix");
        prefixLabel.setMuted(true);
        prefixLabel.setVisible(false);
        prefixLabel.setManaged(false);
        suffixLabel.getStyleClass().add("forja-number-field-affix");
        suffixLabel.setMuted(true);
        suffixLabel.setVisible(false);
        suffixLabel.setManaged(false);

        stepperColumn.getStyleClass().add("forja-number-field-steppers");
        stepperColumn.setAlignment(Pos.CENTER);
        upIcon.getStyleClass().add("forja-number-field-stepper");
        downIcon.getStyleClass().add("forja-number-field-stepper");
        upIcon.setOnMouseClicked(e -> { increment(); e.consume(); });
        downIcon.setOnMouseClicked(e -> { decrement(); e.consume(); });

        helperLabel.getStyleClass().add("forja-number-field-helper");
        helperLabel.setMuted(true);
        helperLabel.setVisible(false);
        helperLabel.setManaged(false);

        getChildren().addAll(fieldRow, helperLabel);

        variant.addListener((obs, o, v) -> applyVariantPseudoClass());
        leadingIcon.addListener((obs, o, v) -> rebuildRow());
        prefix.addListener((obs, o, v) -> {
            String s = v == null ? "" : v;
            prefixLabel.setText(s);
            boolean vis = !s.isEmpty();
            prefixLabel.setVisible(vis);
            prefixLabel.setManaged(vis);
        });
        suffix.addListener((obs, o, v) -> {
            String s = v == null ? "" : v;
            suffixLabel.setText(s);
            boolean vis = !s.isEmpty();
            suffixLabel.setVisible(vis);
            suffixLabel.setManaged(vis);
        });
        showSteppers.addListener((obs, o, v) -> rebuildRow());
        decimals.addListener((obs, o, v) -> {
            format = buildFormat(v.intValue());
            syncTextFromValue();
        });
        value.addListener((obs, o, v) -> syncTextFromValue());
        min.addListener((obs, o, v) -> { Double cur = getValue(); if (cur != null) setValue(clamp(cur)); });
        max.addListener((obs, o, v) -> { Double cur = getValue(); if (cur != null) setValue(clamp(cur)); });

        helperText.addListener((obs, o, v) -> refreshHelper());
        errorText.addListener((obs, o, v) -> {
            String e = v == null ? "" : v;
            if (!e.isEmpty()) setVariant(InputVariant.ERROR);
            refreshHelper();
        });

        textField.focusedProperty().addListener((obs, o, focused) -> {
            pseudoClassStateChanged(FOCUSED_PC, focused);
            if (!focused) commitTextToValue();
        });
        textField.setOnAction(e -> commitTextToValue());
        textField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) { increment(); e.consume(); }
            else if (e.getCode() == KeyCode.DOWN) { decrement(); e.consume(); }
        });

        applyVariantPseudoClass();
        rebuildRow();
        refreshHelper();
        syncTextFromValue();
    }

    /**
     * Creates an {@code FxNumberField} initialized to the given value.
     *
     * @param value initial numeric value, or {@code null} for empty
     */
    public FxNumberField(Double value) {
        this();
        setValue(value);
    }

    private DecimalFormat buildFormat(int places) {
        DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance(Locale.US);
        StringBuilder pattern = new StringBuilder("0");
        if (places > 0) {
            pattern.append('.');
            for (int i = 0; i < places; i++) pattern.append('0');
        }
        DecimalFormat df = new DecimalFormat(pattern.toString(), sym);
        df.setGroupingUsed(false);
        return df;
    }

    private UnaryOperator<TextFormatter.Change> inputFilter() {
        return change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) return change;
            int places = Math.max(0, getDecimals());
            String regex = places == 0
                    ? "-?\\d*"
                    : "-?\\d*(\\.\\d{0," + places + "})?";
            return Pattern.matches(regex, newText) ? change : null;
        };
    }

    private void syncTextFromValue() {
        if (syncingText) return;
        syncingText = true;
        try {
            Double v = getValue();
            textField.setText(v == null ? "" : format.format(v));
        } finally {
            syncingText = false;
        }
    }

    private void commitTextToValue() {
        String raw = textField.getText();
        if (raw == null || raw.isEmpty() || raw.equals("-") || raw.equals(".") || raw.equals("-.")) {
            setValue(null);
            syncTextFromValue();
            return;
        }
        try {
            double parsed = Double.parseDouble(raw);
            setValue(clamp(parsed));
        } catch (NumberFormatException ex) {
            syncTextFromValue();
        }
    }

    private Double clamp(Double v) {
        if (v == null) return null;
        Double lo = getMin();
        Double hi = getMax();
        double x = v;
        if (lo != null && x < lo) x = lo;
        if (hi != null && x > hi) x = hi;
        return x;
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
        if (!getPrefix().isEmpty()) fieldRow.getChildren().add(prefixLabel);
        fieldRow.getChildren().add(textField);
        if (!getSuffix().isEmpty()) fieldRow.getChildren().add(suffixLabel);
        if (isShowSteppers()) fieldRow.getChildren().add(stepperColumn);
    }

    private void refreshHelper() {
        String err = getErrorText();
        String help = getHelperText();
        boolean hasError = err != null && !err.isEmpty();
        boolean hasHelper = help != null && !help.isEmpty();
        if (hasError) {
            helperLabel.setText(err);
            helperLabel.setMuted(false);
            if (!helperLabel.getStyleClass().contains("forja-number-field-error")) {
                helperLabel.getStyleClass().add("forja-number-field-error");
            }
            showingError.set(true);
        } else if (hasHelper) {
            helperLabel.setText(help);
            helperLabel.setMuted(true);
            helperLabel.getStyleClass().remove("forja-number-field-error");
            showingError.set(false);
        } else {
            helperLabel.setText("");
            showingError.set(false);
        }
        boolean visible = hasError || hasHelper;
        helperLabel.setVisible(visible);
        helperLabel.setManaged(visible);
    }

    /** Increments the value by {@link #getStep}, clamped to {@code [min, max]}. */
    public void increment() {
        double base = getValue() == null ? defaultForStep() : getValue();
        setValue(clamp(base + getStep()));
    }

    /** Decrements the value by {@link #getStep}, clamped to {@code [min, max]}. */
    public void decrement() {
        double base = getValue() == null ? defaultForStep() : getValue();
        setValue(clamp(base - getStep()));
    }

    private double defaultForStep() {
        Double lo = getMin();
        Double hi = getMax();
        if (lo != null && 0.0 < lo) return lo;
        if (hi != null && 0.0 > hi) return hi;
        return 0.0;
    }

    /** Returns the underlying {@link TextField} for advanced binding. */
    public TextField getTextField() { return textField; }

    /** Returns the helper/error label node. */
    public FxLabel getHelperLabel() { return helperLabel; }

    /** Returns the field-row {@link HBox}. */
    public HBox getFieldRow() { return fieldRow; }

    /** Returns the prefix affix label. */
    public FxLabel getPrefixLabel() { return prefixLabel; }

    /** Returns the suffix affix label. */
    public FxLabel getSuffixLabel() { return suffixLabel; }

    /** Returns the stepper column (up/down icons). */
    public VBox getStepperColumn() { return stepperColumn; }

    /** Returns the up-stepper icon node. */
    public FxIcon getUpIcon() { return upIcon; }

    /** Returns the down-stepper icon node. */
    public FxIcon getDownIcon() { return downIcon; }

    /** Returns the value property. Holds {@code null} when empty. */
    public ObjectProperty<Double> valueProperty() { return value; }

    /** Returns the current value, or {@code null} when empty. */
    public Double getValue() { return value.get(); }

    /** Sets the value; {@code null} clears the field. Clamped to {@code [min, max]}. */
    public void setValue(Double v) { value.set(v == null ? null : clamp(v)); }

    /** Returns the min property (inclusive, {@code null} = unbounded). */
    public ObjectProperty<Double> minProperty() { return min; }

    /** Returns the current min bound. */
    public Double getMin() { return min.get(); }

    /** Sets the inclusive minimum; {@code null} for unbounded. */
    public void setMin(Double v) { min.set(v); }

    /** Returns the max property (inclusive, {@code null} = unbounded). */
    public ObjectProperty<Double> maxProperty() { return max; }

    /** Returns the current max bound. */
    public Double getMax() { return max.get(); }

    /** Sets the inclusive maximum; {@code null} for unbounded. */
    public void setMax(Double v) { max.set(v); }

    /** Returns the step property (default 1.0). */
    public DoubleProperty stepProperty() { return step; }

    /** Returns the current step amount. */
    public double getStep() { return step.get(); }

    /** Sets the step amount applied by increment/decrement. */
    public void setStep(double v) { step.set(v); }

    /** Returns the decimals property (default 0). */
    public IntegerProperty decimalsProperty() { return decimals; }

    /** Returns the current fractional-digit count. */
    public int getDecimals() { return decimals.get(); }

    /** Sets the fractional-digit count for display and input filtering. */
    public void setDecimals(int v) { decimals.set(Math.max(0, v)); }

    /** Returns the prefix property. */
    public StringProperty prefixProperty() { return prefix; }

    /** Returns the current prefix affix text. */
    public String getPrefix() { return prefix.get(); }

    /** Sets the prefix affix (e.g. {@code "$"}); empty/null hides it. */
    public void setPrefix(String v) { prefix.set(v == null ? "" : v); }

    /** Returns the suffix property. */
    public StringProperty suffixProperty() { return suffix; }

    /** Returns the current suffix affix text. */
    public String getSuffix() { return suffix.get(); }

    /** Sets the suffix affix (e.g. {@code "kg"}); empty/null hides it. */
    public void setSuffix(String v) { suffix.set(v == null ? "" : v); }

    /** Returns the show-steppers property. */
    public BooleanProperty showSteppersProperty() { return showSteppers; }

    /** Returns whether the up/down stepper column is shown. */
    public boolean isShowSteppers() { return showSteppers.get(); }

    /** Sets whether the up/down stepper column is shown. */
    public void setShowSteppers(boolean v) { showSteppers.set(v); }

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

    /** Returns the prompt-text property (delegates to the inner TextField). */
    public StringProperty promptTextProperty() { return textField.promptTextProperty(); }

    /** Returns the current prompt text. */
    public String getPromptText() { return textField.getPromptText(); }

    /** Sets the prompt text. */
    public void setPromptText(String v) { textField.setPromptText(v); }

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
     * Returns a new {@link Builder} for constructing an {@code FxNumberField}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxNumberField}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>value — {@code null} (empty)</li>
     *   <li>min / max — {@code null} (unbounded)</li>
     *   <li>step — {@code 1.0}</li>
     *   <li>decimals — {@code 0}</li>
     *   <li>prefix / suffix — empty (hidden)</li>
     *   <li>showSteppers — {@code false}</li>
     *   <li>variant — {@link InputVariant#DEFAULT}</li>
     *   <li>leadingIcon — none</li>
     *   <li>helperText / errorText — empty (line hidden)</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxNumberField, Builder> {

        private Double value;
        private Double min;
        private Double max;
        private double step = 1.0;
        private int decimals = 0;
        private String prefix = "";
        private String suffix = "";
        private boolean showSteppers = false;
        private InputVariant variant = InputVariant.DEFAULT;
        private Node leadingIcon;
        private String promptText = "";
        private String helperText = "";
        private String errorText = "";
        private boolean editable = true;

        public Builder value(Double value) {
            this.value = value;
            return this;
        }

        public Builder value(double value) {
            this.value = value;
            return this;
        }

        public Builder min(Double min) {
            this.min = min;
            return this;
        }

        public Builder min(double min) {
            this.min = min;
            return this;
        }

        public Builder max(Double max) {
            this.max = max;
            return this;
        }

        public Builder max(double max) {
            this.max = max;
            return this;
        }

        public Builder step(double step) {
            this.step = step;
            return this;
        }

        public Builder decimals(int decimals) {
            this.decimals = Math.max(0, decimals);
            return this;
        }

        public Builder prefix(String prefix) {
            this.prefix = prefix == null ? "" : prefix;
            return this;
        }

        public Builder suffix(String suffix) {
            this.suffix = suffix == null ? "" : suffix;
            return this;
        }

        public Builder showSteppers(boolean showSteppers) {
            this.showSteppers = showSteppers;
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

        public Builder editable(boolean editable) {
            this.editable = editable;
            return this;
        }

        @Override
        public FxNumberField build() {
            FxNumberField field = new FxNumberField();
            // Apply bounds and format-shape first so setValue clamps + formats
            // against final state.
            field.setDecimals(decimals);
            field.setStep(step);
            field.setMin(min);
            field.setMax(max);
            field.setPrefix(prefix);
            field.setSuffix(suffix);
            field.setShowSteppers(showSteppers);
            field.setLeadingIcon(leadingIcon);
            field.setPromptText(promptText);
            field.setHelperText(helperText);
            // Variant before errorText so the auto-flip wins.
            field.setVariant(variant);
            field.setValue(value);
            field.setErrorText(errorText);
            field.getTextField().setEditable(editable);
            applyBase(field);
            return field;
        }
    }
}
