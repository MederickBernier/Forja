package io.forja.components.inputs.fxPasswordField;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A styled password input with optional leading icon, helper/error text, and
 * an optional reveal-toggle that swaps to a plain text view.
 *
 * <p>{@code FxPasswordField} is a composite {@link VBox} of two rows:</p>
 * <ol>
 *   <li>A framed {@link HBox} carrying the leading icon (if any), a
 *       {@link StackPane} holding the masked {@link PasswordField} and the
 *       plain {@link TextField} (swapped via {@code revealable} + {@code revealed}),
 *       and the trailing reveal-icon button (if revealable).</li>
 *   <li>A {@link FxLabel} carrying the helper or error text — auto-hidden
 *       when both are empty.</li>
 * </ol>
 *
 * <p>The two inner inputs share the same text via a bidirectional binding.
 * When {@link #setRevealed setRevealed(true)} on a revealable field, the
 * plain {@link TextField} is shown; otherwise the masked {@link PasswordField}
 * is shown. When {@link #setRevealable setRevealable(false)} the reveal icon
 * is hidden and the field is forced back to masked.
 *
 * <p>The preferred way to construct an {@code FxPasswordField} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxPasswordField password = FxPasswordField.builder()
 *          .promptText("••••••••")
 *          .leadingIcon("fth-lock")
 *          .revealable(true)
 *          .helperText("8+ characters with a number and symbol.")
 *          .build();
 *     }
 * </pre>
 *
 * @see InputVariant
 * @see Builder
 */
public class FxPasswordField extends VBox {

    private static final PseudoClass DEFAULT_PC = PseudoClass.getPseudoClass("default");
    private static final PseudoClass ERROR_PC   = PseudoClass.getPseudoClass("error");
    private static final PseudoClass SUCCESS_PC = PseudoClass.getPseudoClass("success");
    private static final PseudoClass FOCUSED_PC = PseudoClass.getPseudoClass("focused");

    private static final String REVEAL_ICON_LITERAL = "fth-eye";
    private static final String HIDE_ICON_LITERAL = "fth-eye-off";

    private final PasswordField passwordField = new PasswordField();
    private final TextField revealField = new TextField();
    private final StackPane inputStack = new StackPane(passwordField, revealField);
    private final HBox fieldRow = new HBox();
    private final FxLabel helperLabel = new FxLabel("", LabelVariant.SMALL);
    private final FxIcon revealIcon = new FxIcon(REVEAL_ICON_LITERAL);

    private final ObjectProperty<InputVariant> variant = new SimpleObjectProperty<>(this, "variant", InputVariant.DEFAULT);
    private final ObjectProperty<Node> leadingIcon = new SimpleObjectProperty<>(this, "leadingIcon");
    private final StringProperty helperText = new SimpleStringProperty(this, "helperText", "");
    private final StringProperty errorText = new SimpleStringProperty(this, "errorText", "");
    private final BooleanProperty revealable = new SimpleBooleanProperty(this, "revealable", false);
    private final BooleanProperty revealed = new SimpleBooleanProperty(this, "revealed", false);
    private final BooleanProperty showingError = new SimpleBooleanProperty(this, "showingError", false);

    /**
     * Creates an empty {@code FxPasswordField} with the default variant.
     */
    public FxPasswordField() {
        super();
        getStyleClass().add("forja-password-field");
        setSpacing(4);

        fieldRow.getStyleClass().add("forja-password-field-row");
        fieldRow.setAlignment(Pos.CENTER_LEFT);
        fieldRow.setSpacing(8);

        passwordField.getStyleClass().add("forja-password-field-inner");
        revealField.getStyleClass().add("forja-password-field-inner");
        passwordField.promptTextProperty().bindBidirectional(revealField.promptTextProperty());
        passwordField.textProperty().bindBidirectional(revealField.textProperty());

        inputStack.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(inputStack, Priority.ALWAYS);

        helperLabel.getStyleClass().add("forja-password-field-helper");
        helperLabel.setMuted(true);
        helperLabel.setVisible(false);
        helperLabel.setManaged(false);

        revealIcon.getStyleClass().add("forja-password-field-reveal");
        revealIcon.setOnMouseClicked(e -> {
            if (isRevealable()) {
                setRevealed(!isRevealed());
            }
            e.consume();
        });

        getChildren().addAll(fieldRow, helperLabel);

        variant.addListener((obs, old, val) -> applyVariantPseudoClass());
        leadingIcon.addListener((obs, old, val) -> rebuildRow());
        helperText.addListener((obs, old, val) -> refreshHelper());
        errorText.addListener((obs, old, val) -> {
            String e = val == null ? "" : val;
            if (!e.isEmpty()) setVariant(InputVariant.ERROR);
            refreshHelper();
        });
        revealable.addListener((obs, old, val) -> {
            if (!val) setRevealed(false);
            rebuildRow();
        });
        revealed.addListener((obs, old, val) -> applyRevealedState());
        passwordField.focusedProperty().addListener((obs, old, val) ->
                pseudoClassStateChanged(FOCUSED_PC, val || revealField.isFocused()));
        revealField.focusedProperty().addListener((obs, old, val) ->
                pseudoClassStateChanged(FOCUSED_PC, val || passwordField.isFocused()));

        revealed.addListener((obs, old, val) ->
                revealIcon.setIconLiteral(val ? HIDE_ICON_LITERAL : REVEAL_ICON_LITERAL));
        revealIcon.setIconLiteral(isRevealed() ? HIDE_ICON_LITERAL : REVEAL_ICON_LITERAL);

        applyVariantPseudoClass();
        applyRevealedState();
        rebuildRow();
        refreshHelper();
    }

    /**
     * Creates an {@code FxPasswordField} with the given prompt text.
     *
     * @param promptText placeholder text shown when the field is empty
     */
    public FxPasswordField(String promptText) {
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

    private void applyRevealedState() {
        boolean show = isRevealed() && isRevealable();
        passwordField.setVisible(!show);
        passwordField.setManaged(!show);
        revealField.setVisible(show);
        revealField.setManaged(show);
    }

    private void rebuildRow() {
        fieldRow.getChildren().clear();
        Node lead = getLeadingIcon();
        if (lead != null) fieldRow.getChildren().add(lead);
        fieldRow.getChildren().add(inputStack);
        if (isRevealable()) {
            fieldRow.getChildren().add(revealIcon);
        }
    }

    private void refreshHelper() {
        String err = getErrorText();
        String help = getHelperText();
        boolean hasError = err != null && !err.isEmpty();
        boolean hasHelper = help != null && !help.isEmpty();
        if (hasError) {
            helperLabel.setText(err);
            helperLabel.setMuted(false);
            if (!helperLabel.getStyleClass().contains("forja-password-field-error")) {
                helperLabel.getStyleClass().add("forja-password-field-error");
            }
            showingError.set(true);
        } else if (hasHelper) {
            helperLabel.setText(help);
            helperLabel.setMuted(true);
            helperLabel.getStyleClass().remove("forja-password-field-error");
            showingError.set(false);
        } else {
            helperLabel.setText("");
            showingError.set(false);
        }
        boolean visible = hasError || hasHelper;
        helperLabel.setVisible(visible);
        helperLabel.setManaged(visible);
    }

    /** Returns the inner masked {@link PasswordField} for advanced binding. */
    public PasswordField getPasswordField() { return passwordField; }

    /** Returns the inner plain {@link TextField} used when revealed. */
    public TextField getRevealField() { return revealField; }

    /** Returns the field-row {@link HBox}. */
    public HBox getFieldRow() { return fieldRow; }

    /** Returns the helper/error label node. */
    public FxLabel getHelperLabel() { return helperLabel; }

    /** Returns the reveal-toggle icon node. */
    public FxIcon getRevealIcon() { return revealIcon; }

    /** Returns the inner text property (delegates to the inner inputs). */
    public StringProperty textProperty() { return passwordField.textProperty(); }

    /** Returns the current text. */
    public String getText() { return passwordField.getText(); }

    /** Sets the text. */
    public void setText(String v) { passwordField.setText(v); }

    /** Returns the prompt-text property. */
    public StringProperty promptTextProperty() { return passwordField.promptTextProperty(); }

    /** Returns the current prompt text. */
    public String getPromptText() { return passwordField.getPromptText(); }

    /** Sets the prompt text. */
    public void setPromptText(String v) { passwordField.setPromptText(v); }

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

    /** Sets the leading icon node. */
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
     * Sets the error text. Non-empty value auto-flips variant to
     * {@link InputVariant#ERROR}; clearing does not auto-revert.
     */
    public void setErrorText(String v) { errorText.set(v == null ? "" : v); }

    /** Returns the revealable property — whether the eye toggle is shown. */
    public BooleanProperty revealableProperty() { return revealable; }

    /** Returns whether the eye toggle is shown. */
    public boolean isRevealable() { return revealable.get(); }

    /** Sets whether the eye toggle is shown. Disabling forces masked state. */
    public void setRevealable(boolean v) { revealable.set(v); }

    /** Returns the revealed property — whether the plain text is shown. */
    public BooleanProperty revealedProperty() { return revealed; }

    /** Returns whether the field is currently showing plain text. */
    public boolean isRevealed() { return revealed.get(); }

    /** Sets whether the field shows plain text. Only honored when revealable. */
    public void setRevealed(boolean v) { revealed.set(v); }

    /** Returns whether the helper label is showing the error text. */
    public boolean isShowingError() { return showingError.get(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxPasswordField}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxPasswordField}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text / promptText — empty</li>
     *   <li>variant — {@link InputVariant#DEFAULT}</li>
     *   <li>leadingIcon — none</li>
     *   <li>helperText / errorText — empty (line hidden)</li>
     *   <li>revealable — {@code false}</li>
     *   <li>revealed — {@code false}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxPasswordField, Builder> {

        private String text = "";
        private String promptText = "";
        private InputVariant variant = InputVariant.DEFAULT;
        private Node leadingIcon;
        private String helperText = "";
        private String errorText = "";
        private boolean revealable = false;
        private boolean revealed = false;
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

        public Builder leadingIcon(Node node) {
            this.leadingIcon = node;
            return this;
        }

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

        public Builder revealable(boolean revealable) {
            this.revealable = revealable;
            return this;
        }

        public Builder revealed(boolean revealed) {
            this.revealed = revealed;
            return this;
        }

        public Builder editable(boolean editable) {
            this.editable = editable;
            return this;
        }

        @Override
        public FxPasswordField build() {
            FxPasswordField field = new FxPasswordField();
            field.setText(text);
            field.setPromptText(promptText);
            field.setLeadingIcon(leadingIcon);
            field.setHelperText(helperText);
            field.setRevealable(revealable);
            field.setRevealed(revealed);
            // Apply variant first so the auto-flip on errorText takes effect.
            field.setVariant(variant);
            field.setErrorText(errorText);
            field.getPasswordField().setEditable(editable);
            field.getRevealField().setEditable(editable);
            applyBase(field);
            return field;
        }
    }
}
