package io.forja.components.inputs.fxMaskedInput;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.inputs.InputVariant;
import io.forja.components.inputs.fxTextField.FxTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

import java.util.function.UnaryOperator;

/**
 * A text input with a fixed input mask.
 *
 * <p>The mask uses placeholder characters that map to input classes:
 * <ul>
 *   <li>{@code #} — a digit ({@code 0..9})</li>
 *   <li>{@code A} — an ASCII letter ({@code a..z / A..Z})</li>
 *   <li>{@code *} — any character</li>
 * </ul>
 * Any other character in the mask is inserted as a literal separator that
 * the user cannot type over.
 *
 * <p>Example masks:
 * <ul>
 *   <li>{@code ###-###-####} — US phone number</li>
 *   <li>{@code AAA-####} — plate number</li>
 *   <li>{@code ####-####-####-####} — credit card</li>
 * </ul>
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxMaskedInput phone = FxMaskedInput.builder()
 *          .mask("###-###-####")
 *          .promptText("555-123-4567")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxMaskedInput extends VBox {

    private final FxTextField field = new FxTextField();
    private final StringProperty mask = new SimpleStringProperty(this, "mask", "");

    /**
     * Creates an unmask'd {@code FxMaskedInput} (identical to
     * {@link FxTextField} until a mask is set).
     */
    public FxMaskedInput() {
        super();
        getStyleClass().add("forja-masked-input");
        getChildren().add(field);
        installFormatter();
        mask.addListener((obs, o, v) -> installFormatter());
    }

    private void installFormatter() {
        field.getTextField().setTextFormatter(new TextFormatter<>(maskFilter()));
    }

    private UnaryOperator<TextFormatter.Change> maskFilter() {
        return change -> {
            String m = getMask() == null ? "" : getMask();
            if (m.isEmpty()) return change;
            String proposed = change.getControlNewText();
            if (proposed.length() > m.length()) return null;
            for (int i = 0; i < proposed.length(); i++) {
                char slot = m.charAt(i);
                char c = proposed.charAt(i);
                if (!matches(slot, c, m, i)) return null;
            }
            return change;
        };
    }

    private static boolean matches(char slot, char c, String mask, int pos) {
        if (slot == '#') return Character.isDigit(c);
        if (slot == 'A') return Character.isLetter(c) && (int) c < 128;
        if (slot == '*') return true;
        return c == slot;
    }

    /** Returns the underlying {@link FxTextField}. */
    public FxTextField getField() { return field; }

    /** Returns the mask property. */
    public StringProperty maskProperty() { return mask; }

    /** Returns the current mask pattern. */
    public String getMask() { return mask.get(); }

    /** Sets the mask pattern. Empty disables masking. */
    public void setMask(String v) { mask.set(v == null ? "" : v); }

    /** Returns the text property. */
    public StringProperty textProperty() { return field.textProperty(); }

    /** Returns the current text. */
    public String getText() { return field.getText(); }

    /** Sets the text. */
    public void setText(String v) { field.setText(v); }

    /** Returns the prompt-text property. */
    public StringProperty promptTextProperty() { return field.promptTextProperty(); }

    /** Returns the current prompt text. */
    public String getPromptText() { return field.getPromptText(); }

    /** Sets the prompt text. */
    public void setPromptText(String v) { field.setPromptText(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxMaskedInput}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxMaskedInput}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>mask — empty (behaves as plain text field)</li>
     *   <li>text / promptText — empty</li>
     *   <li>variant — {@link InputVariant#DEFAULT}</li>
     *   <li>helperText / errorText — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxMaskedInput, Builder> {

        private String mask = "";
        private String text = "";
        private String promptText = "";
        private InputVariant variant = InputVariant.DEFAULT;
        private String helperText = "";
        private String errorText = "";

        public Builder mask(String mask) { this.mask = mask == null ? "" : mask; return this; }
        public Builder text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder promptText(String promptText) { this.promptText = promptText == null ? "" : promptText; return this; }
        public Builder variant(InputVariant variant) { this.variant = variant; return this; }
        public Builder helperText(String helperText) { this.helperText = helperText == null ? "" : helperText; return this; }
        public Builder errorText(String errorText) { this.errorText = errorText == null ? "" : errorText; return this; }

        @Override
        public FxMaskedInput build() {
            FxMaskedInput m = new FxMaskedInput();
            m.setMask(mask);
            m.setPromptText(promptText);
            m.getField().setVariant(variant);
            m.getField().setHelperText(helperText);
            m.getField().setErrorText(errorText);
            m.setText(text);
            applyBase(m);
            return m;
        }
    }
}
