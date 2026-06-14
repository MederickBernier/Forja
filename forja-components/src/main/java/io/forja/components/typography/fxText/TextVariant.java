package io.forja.components.typography.fxText;

/**
 * Paragraph variants for {@link FxText}.
 *
 * <p>Each variant maps to a different font size + line-height combination,
 * applied via the {@code :body}, {@code :lead} CSS pseudo-classes on the
 * {@code .forja-text} style class.
 *
 * @see FxText
 */
public enum TextVariant {
    /** Standard body paragraph text (13px). */
    BODY,
    /** Larger lead/intro paragraph (16px). */
    LEAD
}
