package io.forja.components.layout.fxCard;

/**
 * Visual variants for {@link FxCard}.
 *
 * <p>Each variant maps to a different surface treatment via the {@code :default},
 * {@code :outlined}, {@code :elevated} pseudo-classes on the
 * {@code .forja-card} style class. Forja's design language explicitly avoids
 * drop shadows, so ELEVATED uses a stronger top-edge border instead of a
 * shadow to signal lift.
 *
 * @see FxCard
 */
public enum CardVariant {
    /** Subtle border, card background — the standard surface. */
    DEFAULT,
    /** Prominent border, transparent background — emphasizes the boundary. */
    OUTLINED,
    /** Card background with a stronger top edge — signals elevation without a shadow. */
    ELEVATED
}
