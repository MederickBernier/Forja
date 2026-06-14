package io.forja.components;

/**
 * Token-driven spacing presets shared by Forja layout primitives.
 *
 * <p>Each value maps to a pixel size that matches a {@code -forja-spacing-*}
 * token declared in {@code base.css}. Used by {@link FxStack} and
 * {@link FxRow} for {@code spacing} (gap between children) and may be used
 * elsewhere where token-aligned sizing is needed.
 *
 * @see FxStack
 * @see FxRow
 */
public enum SpacingSize {
    /** No gap. */
    NONE(0),
    /** 4px — pairs with {@code -forja-spacing-xs}. */
    XS(4),
    /** 8px — pairs with {@code -forja-spacing-sm}. */
    SM(8),
    /** 12px — pairs with {@code -forja-spacing-md}. */
    MD(12),
    /** 16px — pairs with {@code -forja-spacing-lg}. */
    LG(16),
    /** 24px — pairs with {@code -forja-spacing-xl}. */
    XL(24),
    /** 32px — pairs with {@code -forja-spacing-2xl}. */
    XL2(32),
    /** 48px — pairs with {@code -forja-spacing-3xl}. */
    XL3(48);

    private final double pixels;

    SpacingSize(double pixels) {
        this.pixels = pixels;
    }

    /** Returns the pixel size for this token. */
    public double pixels() {
        return pixels;
    }
}
