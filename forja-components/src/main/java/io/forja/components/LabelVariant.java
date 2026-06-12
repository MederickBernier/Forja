package io.forja.components;

/**
 * Typography variants for {@link FxLabel}.
 *
 * <p>Each variant maps to a size + weight + (optional) family combination
 * defined in the Forja token layer. Variants are applied via the
 * {@code :display}, {@code :heading}, {@code :subheading}, {@code :body},
 * {@code :small}, {@code :mono} CSS pseudo-classes on the
 * {@code .forja-label} style class.
 *
 * @see FxLabel
 */
public enum LabelVariant {
    /** Large, attention-grabbing title text (24px / bold). */
    DISPLAY,
    /** Section heading (18px / bold). */
    HEADING,
    /** Sub-section heading (14px / bold). */
    SUBHEADING,
    /** Default body text (13px / normal). */
    BODY,
    /** De-emphasized, small print (11px / normal). */
    SMALL,
    /** Monospaced text for code or fixed-width content (12px / JetBrains Mono). */
    MONO
}
