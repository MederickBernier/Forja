package io.forja.components.layout.fxSeparator;

/**
 * Thickness variants for {@link FxSeparator}.
 *
 * <p>Each variant maps to a different visible weight, applied via the
 * {@code :hairline}, {@code :default}, {@code :strong} CSS pseudo-classes
 * on the {@code .forja-separator} style class.
 *
 * @see FxSeparator
 */
public enum SeparatorVariant {
    /** Sub-pixel divider for dense layouts (0.5px). */
    HAIRLINE,
    /** Standard divider (1px). */
    DEFAULT,
    /** Emphasized divider for major section breaks (2px). */
    STRONG
}
