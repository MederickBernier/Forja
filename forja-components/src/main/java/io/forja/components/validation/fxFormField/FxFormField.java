package io.forja.components.validation.fxFormField;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.validation.fxValidator.FxValidator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.function.Supplier;

/**
 * A form-field row: label + control + optional error message.
 *
 * <p>{@code FxFormField} is a {@link VBox} of a {@link FxLabel} label, the
 * wrapped control node, and a {@link FxLabel} error line that shows when
 * {@link #getErrorText} is non-empty. Set {@link #setValidator} to plug a
 * {@link FxValidator} into the field; call {@link #validate()} to compute
 * the error against a value read from {@link #getValueSupplier}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxFormField emailField = FxFormField.<String>builder()
 *          .label("Email")
 *          .control(emailInput)
 *          .validator(FxValidator.<String>of(FxValidator.required(), FxValidator.email("Invalid email")))
 *          .valueSupplier(emailInput::getText)
 *          .required(true)
 *          .build();
 *     }
 * </pre>
 *
 * @param <T> value type (used by the validator)
 * @see FxValidator
 * @see Builder
 */
public class FxFormField<T> extends VBox {

    private static final PseudoClass INVALID_PC = PseudoClass.getPseudoClass("invalid");
    private static final PseudoClass REQUIRED_PC = PseudoClass.getPseudoClass("required");

    private final FxLabel labelNode = new FxLabel("", LabelVariant.SMALL);
    private final ObjectProperty<Node> control = new SimpleObjectProperty<>(this, "control");
    private final FxLabel errorLabel = new FxLabel("", LabelVariant.SMALL);

    private final StringProperty label = new SimpleStringProperty(this, "label", "");
    private final StringProperty errorText = new SimpleStringProperty(this, "errorText", "");
    private final BooleanProperty required = new SimpleBooleanProperty(this, "required", false);
    private FxValidator<T> validator;
    private Supplier<T> valueSupplier;

    /**
     * Creates an empty {@code FxFormField}.
     */
    public FxFormField() {
        super();
        getStyleClass().add("forja-form-field");
        setSpacing(4);
        labelNode.getStyleClass().add("forja-form-field-label");
        labelNode.setMuted(true);
        errorLabel.getStyleClass().add("forja-form-field-error");
        errorLabel.setMuted(false);
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        getChildren().addAll(labelNode, errorLabel);

        label.addListener((obs, o, v) -> {
            String s = v == null ? "" : v;
            labelNode.setText(isRequired() ? s + " *" : s);
        });
        required.addListener((obs, o, v) -> {
            String s = getLabel();
            labelNode.setText(v ? s + " *" : s);
            pseudoClassStateChanged(REQUIRED_PC, v);
        });
        errorText.addListener((obs, o, v) -> {
            String e = v == null ? "" : v;
            errorLabel.setText(e);
            boolean vis = !e.isEmpty();
            errorLabel.setVisible(vis);
            errorLabel.setManaged(vis);
            pseudoClassStateChanged(INVALID_PC, vis);
        });
        control.addListener((obs, o, v) -> rebuild());
    }

    private void rebuild() {
        getChildren().clear();
        getChildren().add(labelNode);
        Node c = getControl();
        if (c != null) getChildren().add(c);
        getChildren().add(errorLabel);
    }

    /**
     * Runs the configured validator against the current value.
     *
     * @return {@code true} if valid; {@code false} sets the error text
     */
    public boolean validate() {
        if (validator == null || valueSupplier == null) { setErrorText(""); return true; }
        String err = validator.validate(valueSupplier.get());
        setErrorText(err == null ? "" : err);
        return err == null;
    }

    /** Returns the label property. */
    public StringProperty labelProperty() { return label; }
    /** Returns the current label text. */
    public String getLabel() { return label.get(); }
    /** Sets the label text. */
    public void setLabel(String v) { label.set(v == null ? "" : v); }

    /** Returns the control property. */
    public ObjectProperty<Node> controlProperty() { return control; }
    /** Returns the current control node. */
    public Node getControl() { return control.get(); }
    /** Sets the control node. */
    public void setControl(Node v) { control.set(v); }

    /** Returns the error-text property. */
    public StringProperty errorTextProperty() { return errorText; }
    /** Returns the current error text. */
    public String getErrorText() { return errorText.get(); }
    /** Sets the error text; empty hides the error line. */
    public void setErrorText(String v) { errorText.set(v == null ? "" : v); }

    /** Returns the required property. */
    public BooleanProperty requiredProperty() { return required; }
    /** Returns whether the field is marked required. */
    public boolean isRequired() { return required.get(); }
    /** Sets whether the field is marked required. */
    public void setRequired(boolean v) { required.set(v); }

    /** Sets the validator. */
    public void setValidator(FxValidator<T> validator) { this.validator = validator; }
    /** Returns the validator. */
    public FxValidator<T> getValidator() { return validator; }

    /** Sets the supplier used to read the field's current value. */
    public void setValueSupplier(Supplier<T> supplier) { this.valueSupplier = supplier; }
    /** Returns the value supplier. */
    public Supplier<T> getValueSupplier() { return valueSupplier; }

    /** Returns the label node. */
    public FxLabel getLabelNode() { return labelNode; }
    /** Returns the error-label node. */
    public FxLabel getErrorLabel() { return errorLabel; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxFormField}.
     *
     * @param <T> value type
     * @return a new {@link Builder} instance
     */
    public static <T> Builder<T> builder() { return new Builder<T>(); }

    /**
     * Fluent builder for constructing an {@link FxFormField}.
     *
     * @param <T> value type
     */
    public static class Builder<T> extends FxNodeBuilder<FxFormField<T>, Builder<T>> {

        private String label = "";
        private Node control;
        private FxValidator<T> validator;
        private Supplier<T> valueSupplier;
        private boolean required = false;

        public Builder<T> label(String label) { this.label = label == null ? "" : label; return this; }
        public Builder<T> control(Node control) { this.control = control; return this; }
        public Builder<T> validator(FxValidator<T> validator) { this.validator = validator; return this; }
        public Builder<T> valueSupplier(Supplier<T> supplier) { this.valueSupplier = supplier; return this; }
        public Builder<T> required(boolean required) { this.required = required; return this; }

        @Override
        public FxFormField<T> build() {
            FxFormField<T> f = new FxFormField<T>();
            f.setRequired(required);
            f.setLabel(label);
            f.setControl(control);
            f.setValidator(validator);
            f.setValueSupplier(valueSupplier);
            applyBase(f);
            return f;
        }
    }
}
