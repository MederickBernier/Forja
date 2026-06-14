package io.forja.components;

/**
 * Semantic color variants for {@link FxStatusDot}.
 *
 * <p>Mirrors {@link BadgeVariant} and {@link ChipVariant}. Applied via the
 * matching CSS pseudo-classes on {@code .forja-status-dot}.
 *
 * @see FxStatusDot
 */
public enum StatusDotVariant {
    DEFAULT,
    MUTED,
    ACCENT,
    SUCCESS,
    WARNING,
    DANGER,
    INFO
}
