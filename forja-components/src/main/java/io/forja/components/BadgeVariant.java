package io.forja.components;

/**
 * Semantic color variants for {@link FxBadge}.
 *
 * <p>Each variant maps to a token-driven background + text color, applied
 * via the {@code :default}, {@code :muted}, {@code :accent}, {@code :success},
 * {@code :warning}, {@code :danger}, {@code :info} CSS pseudo-classes on
 * the {@code .forja-badge} style class.
 *
 * @see FxBadge
 */
public enum BadgeVariant {
    /** Neutral badge (zinc tint). */
    DEFAULT,
    /** De-emphasized badge for low-priority labels. */
    MUTED,
    /** Brand accent badge. */
    ACCENT,
    /** Positive / success indication. */
    SUCCESS,
    /** Cautionary indication. */
    WARNING,
    /** Destructive / error indication. */
    DANGER,
    /** Informational indication. */
    INFO
}
