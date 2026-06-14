package io.forja.components;

/**
 * Semantic color variants for {@link FxChip}.
 *
 * <p>Mirrors {@link BadgeVariant} (and the broader Forja semantic-token
 * scheme): {@code DEFAULT}, {@code MUTED}, {@code ACCENT}, {@code SUCCESS},
 * {@code WARNING}, {@code DANGER}, {@code INFO}. Applied via the matching
 * CSS pseudo-classes on {@code .forja-chip}.
 *
 * @see FxChip
 */
public enum ChipVariant {
    DEFAULT,
    MUTED,
    ACCENT,
    SUCCESS,
    WARNING,
    DANGER,
    INFO
}
