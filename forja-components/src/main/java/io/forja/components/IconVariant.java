package io.forja.components;

/**
 * Semantic color variants for {@link FxIcon}.
 *
 * <p>Each variant maps to a token-driven color from the active theme, applied
 * via the {@code :default}, {@code :muted}, {@code :accent}, {@code :success},
 * {@code :warning}, {@code :danger}, {@code :info} CSS pseudo-classes on the
 * {@code .forja-icon} style class.
 *
 * @see FxIcon
 */
public enum IconVariant {
    /** Default text color (matches {@code -forja-text-primary}). */
    DEFAULT,
    /** De-emphasized color for secondary content (matches {@code -forja-text-muted}). */
    MUTED,
    /** Brand accent color (matches {@code -forja-accent}). */
    ACCENT,
    /** Positive / success indication (matches {@code -forja-success}). */
    SUCCESS,
    /** Cautionary indication (matches {@code -forja-warning}). */
    WARNING,
    /** Destructive / error indication (matches {@code -forja-danger}). */
    DANGER,
    /** Informational indication (matches {@code -forja-info}). */
    INFO
}
