package io.forja.components.inputs.fxTextField;

import io.forja.components.inputs.InputVariant;

import io.forja.builder.FxNodeBuilder;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * A styled text input with optional leading/trailing icons, helper text,
 * and validation-state variants.
 *
 * <p>{@code FxTextField} is a composite {@link VBox} of two rows:</p>
 * <ol>
 *   <li>A framed {@link HBox} carrying the leading icon (if any), the actual
 *       {@link TextField}, and the trailing icon (if any).</li>
 *   <li>A {@link FxLabel} carrying the helper or error text — auto-hidden
 *       when both are empty.</li>
 * </ol>
 *
 * <p>The inner {@link TextField} is accessible via {@link #getTextField} for
 * advanced binding or behavior tweaks. The {@code text} and {@code promptText}
 * properties on {@code FxTextField} delegate directly to the inner field.
 *
 * <p>Setting {@link #setErrorText} to a non-empty value automatically flips
 * the variant to {@link InputVariant#ERROR}; clearing it does NOT auto-
 * revert (callers should reset the variant explicitly if needed).
 *
 * <p>The preferred way to construct an {@code FxTextField} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxTextField email = FxTextField.builder()
 *          .promptText("name@example.com")
 *          .leadingIcon("fth-mail")
 *          .helperText("We'll never share your email.")
 *          .build();
 *     }
 * </pre>
 *
 * @see InputVariant
 * @see Builder
 */
public class FxTextField extends VBox {

    private static final PseudoClass DEFAULT_PC = PseudoClass.getPseudoClass("default");
    private static final PseudoClass ERROR_PC   = PseudoClass.getPseudoClass("error");
    private static final PseudoClass SUCCESS_PC = PseudoClass.getPseudoClass("success");
    private static final PseudoClass FOCUSED_PC = PseudoClass.getPseudoClass("focused");

    private final TextField textField = new TextField();
    private final HBox fieldRow = new HBox();
    private final FxLabel helperLabel = new FxLabel("", LabelVariant.SMALL);

    private final ObjectProperty<InputVariant> variant = new SimpleObjectProperty<>(this, "variant", InputVariant.DEFAULT);
    private final ObjectProperty<Node> leadingIcon = new SimpleObjectProperty<>(this, "leadingIcon");
    private final ObjectProperty<Node> trailingIcon = new SimpleObjectProperty<>(this, "trailingIcon");
    private final StringProperty helperText = new SimpleStringProperty(this, "helperText", "");
    private final StringProperty errorText = new SimpleStringProperty(this, "errorText", "");
    private final BooleanProperty showingError = new SimpleBooleanProperty(this, "showingError", false);

    /**
     * Creates an empty {@code FxTextField} with the default variant.
     */
    public FxTextField() {
        super();
        getStyleClass().add("forja-text-field");
        setSpacing(4);

        fieldRow.getStyleClass().add("forja-text-field-row");
        fieldRow.setAlignment(Pos.CENTER_LEFT);
        fieldRow.setSpacing(8);

        textField.getStyleClass().add("forja-text-field-inner");
        HBox.setHgrow(textField, Priority.ALWAYS);

        helperLabel.getStyleClass().add("forja-text-field-helper");
        helperLabel.setMuted(true);
        helperLabel.setVisible(false);
        helperLabel.setManaged(false);

        getChildren().addAll(fieldRow, helperLabel);

        variant.addListener((obs, old, val) -> applyVariantPseudoClass());
        leadingIcon.addListener((obs, old, val) -> rebuildRow());
        trailingIcon.addListener((obs, old, val) -> rebuildRow());
        helperText.addListener((obs, old, val) -> refreshHelper());
        errorText.addListener((obs, old, val) -> {
            String e = val == null ? "" : val;
            if (!e.isEmpty()) {
                setVariant(InputVariant.ERROR);
            }
            refreshHelper();
        });
        textField.focusedProperty().addListener((obs, old, val) ->
                pseudoClassStateChanged(FOCUSED_PC, val));

        applyVariantPseudoClass();
        rebuildRow();
        refreshHelper();
    }

    /**
     * Creates an {@code FxTextField} with the given prompt text.
     *
     * @param promptText placeholder text shown when the field is empty
     */
    public FxTextField(String promptText) {
        this();
        setPromptText(promptText);
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
        fieldRow.getChildren().add(textField);
        Node trail = getTrailingIcon();
        if (trail != null) fieldRow.getChildren().add(trail);
    }

    private void refreshHelper() {
        String err = getErrorText();
        String help = getHelperText();
        boolean hasError = err != null && !err.isEmpty();
        boolean hasHelper = help != null && !help.isEmpty();
        if (hasError) {
            helperLabel.setText(err);
            helperLabel.setMuted(false);
            helperLabel.getStyleClass().add("forja-text-field-error");
            showingError.set(true);
        } else if (hasHelper) {
            helperLabel.setText(help);
            helperLabel.setMuted(true);
            helperLabel.getStyleClass().remove("forja-text-field-error");
            showingError.set(false);
        } else {
            helperLabel.setText("");
            showingError.set(false);
        }
        boolean visible = hasError || hasHelper;
        helperLabel.setVisible(visible);
        helperLabel.setManaged(visible);
    }

    /** Returns the underlying {@link TextField} for advanced binding cases. */
    public TextField getTextField() { return textField; }

    /** Returns the helper/error label node — for advanced styling. */
    public FxLabel getHelperLabel() { return helperLabel; }

    /** Returns the field-row {@link HBox} containing icons and the input. */
    public HBox getFieldRow() { return fieldRow; }

    /** Returns the inner text property (delegates to the inner TextField). */
    public StringProperty textProperty() { return textField.textProperty(); }

    /** Returns the current text. */
    public String getText() { return textField.getText(); }

    /** Sets the text. */
    public void setText(String v) { textField.setText(v); }

    /** Returns the prompt-text property (delegates to the inner TextField). */
    public StringProperty promptTextProperty() { return textField.promptTextProperty(); }

    /** Returns the current prompt text. */
    public String getPromptText() { return textField.getPromptText(); }

    /** Sets the prompt text. */
    public void setPromptText(String v) { textField.setPromptText(v); }

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

    /** Returns the trailing-icon slot property. */
    public ObjectProperty<Node> trailingIconProperty() { return trailingIcon; }

    /** Returns the trailing icon node, or {@code null}. */
    public Node getTrailingIcon() { return trailingIcon.get(); }

    /** Sets the trailing icon node. Pass {@code null} to clear. */
    public void setTrailingIcon(Node node) { trailingIcon.set(node); }

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
     * Sets the error text. Setting a non-empty value automatically flips the
     * variant to {@link InputVariant#ERROR} so the visual matches the
     * message; clearing it does not auto-revert.
     */
    public void setErrorText(String v) { errorText.set(v == null ? "" : v); }

    /** Returns whether the helper label is currently showing the error text. */
    public boolean isShowingError() { return showingError.get(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxTextField}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxTextField}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     *   <li>promptText — empty</li>
     *   <li>variant — {@link InputVariant#DEFAULT}</li>
     *   <li>leadingIcon / trailingIcon — none</li>
     *   <li>helperText / errorText — empty (line hidden)</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxTextField, Builder> {

        private String text = "";
        private String promptText = "";
        private InputVariant variant = InputVariant.DEFAULT;
        private Node leadingIcon;
        private Node trailingIcon;
        private String helperText = "";
        private String errorText = "";
        private boolean editable = true;

        public Builder text(String text) {
            this.text = text == null ? "" : text;
            return this;
        }

        public Builder promptText(String promptText) {
            this.promptText = promptText == null ? "" : promptText;
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

        /** Sets a pre-built {@link Node} as the trailing icon. */
        public Builder trailingIcon(Node node) {
            this.trailingIcon = node;
            return this;
        }

        /** Sugar for {@link #trailingIcon(Node)} using an Ikonli literal. */
        public Builder trailingIcon(String iconLiteral) {
            this.trailingIcon = new FxIcon(iconLiteral);
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
        public FxTextField build() {
            FxTextField field = new FxTextField();
            field.setText(text);
            field.setPromptText(promptText);
            field.setLeadingIcon(leadingIcon);
            field.setTrailingIcon(trailingIcon);
            field.setHelperText(helperText);
            // Apply variant first, then errorText so the auto-flip to ERROR
            // wins when a non-empty errorText is supplied.
            field.setVariant(variant);
            field.setErrorText(errorText);
            field.getTextField().setEditable(editable);
            applyBase(field);
            return field;
        }
    }
}
