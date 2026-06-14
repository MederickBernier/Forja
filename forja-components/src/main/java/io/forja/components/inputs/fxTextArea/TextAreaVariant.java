package io.forja.components.inputs.fxTextArea;

/**
 * Validation-state variants for {@link FxTextArea}.
 *
 * <p>Each variant maps to a border/text color via the {@code :default},
 * {@code :error}, {@code :success} pseudo-classes on the
 * {@code .forja-text-area} style class. Mirrors
 * {@code TextFieldVariant} — the two will be consolidated to a shared
 * {@code InputVariant} enum if a third input component adopts the same set.
 *
 * @see FxTextArea
 */
public enum TextAreaVariant {
    /** Neutral area, no validation feedback. */
    DEFAULT,
    /** Failed validation — red border + danger-colored error text. */
    ERROR,
    /** Validated successfully — green border. */
    SUCCESS
}
