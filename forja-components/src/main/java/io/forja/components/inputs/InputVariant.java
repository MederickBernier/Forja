package io.forja.components.inputs;

/**
 * Shared validation-state variants for Forja form inputs.
 *
 * <p>Used by {@code FxTextField}, {@code FxTextArea}, {@code FxPasswordField},
 * and other input components. Each value maps to a {@code :default},
 * {@code :error}, or {@code :success} pseudo-class on the component's outer
 * style class.
 *
 * <p>Setting an input's {@code errorText} to a non-empty value auto-flips
 * the variant to {@link #ERROR}; clearing it does not auto-revert (callers
 * choose whether to reset).
 */
public enum InputVariant {
    /** Neutral input, no validation feedback. */
    DEFAULT,
    /** Failed validation — red border + danger-colored error text. */
    ERROR,
    /** Validated successfully — green border. */
    SUCCESS
}
