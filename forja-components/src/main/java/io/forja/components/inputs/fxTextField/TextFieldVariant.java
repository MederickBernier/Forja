package io.forja.components.inputs.fxTextField;

/**
 * Validation-state variants for {@link FxTextField}.
 *
 * <p>Each variant maps to a different border/text color via the
 * {@code :default}, {@code :error}, {@code :success} CSS pseudo-classes on
 * the {@code .forja-text-field} style class. The variant typically tracks the
 * field's validation state — applications can drive it via
 * {@link FxTextField#setVariant} or have it auto-flip to {@code ERROR} when
 * {@link FxTextField#setErrorText} is given a non-empty value.
 *
 * @see FxTextField
 */
public enum TextFieldVariant {
    /** Neutral field, no validation feedback. */
    DEFAULT,
    /** Field failed validation — red border + danger-colored error text. */
    ERROR,
    /** Field validated successfully — green border. */
    SUCCESS
}
