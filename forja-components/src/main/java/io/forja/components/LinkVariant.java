package io.forja.components;

/**
 * Visual variants for {@link FxLink}.
 *
 * <p>Each variant maps to a token-driven color and is applied via the
 * {@code :default}, {@code :muted}, {@code :external} CSS pseudo-classes
 * on the {@code .forja-link} style class.
 *
 * @see FxLink
 */
public enum LinkVariant {
    /** Standard accent-colored link. */
    DEFAULT,
    /** De-emphasized link using the muted text color. */
    MUTED,
    /** Accent link with a trailing external-link icon. */
    EXTERNAL
}
