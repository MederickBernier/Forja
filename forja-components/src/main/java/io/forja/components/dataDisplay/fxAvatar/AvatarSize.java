package io.forja.components.dataDisplay.fxAvatar;

/**
 * Size variants for {@link FxAvatar}.
 *
 * <p>Each variant maps to a fixed diameter that mirrors the Forja
 * {@code -forja-component-height-*} tokens. Applied via the {@code :compact},
 * {@code :default}, {@code :comfortable} CSS pseudo-classes on the
 * {@code .forja-avatar} style class.
 *
 * @see FxAvatar
 */
public enum AvatarSize {
    /** 28px diameter — pairs with {@code -forja-component-height-compact}. */
    COMPACT(28),
    /** 36px diameter — pairs with {@code -forja-component-height-default}. */
    DEFAULT(36),
    /** 44px diameter — pairs with {@code -forja-component-height-comfortable}. */
    COMFORTABLE(44);

    private final double diameter;

    AvatarSize(double diameter) {
        this.diameter = diameter;
    }

    /** Returns the pixel diameter for this size. */
    public double diameter() {
        return diameter;
    }
}
