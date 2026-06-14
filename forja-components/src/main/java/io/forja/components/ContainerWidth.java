package io.forja.components;

import javafx.scene.layout.Region;

/**
 * Width breakpoints for {@link FxContainer}.
 *
 * <p>Each variant caps the container at a max width in pixels. {@link #FLUID}
 * applies no cap. Defaults align with common design-system breakpoints.
 *
 * @see FxContainer
 */
public enum ContainerWidth {
    /** 640px — narrow reading column. */
    SM(640),
    /** 768px — single-column content. */
    MD(768),
    /** 1024px — standard app surface (default). */
    LG(1024),
    /** 1280px — wide marketing or dashboard surface. */
    XL(1280),
    /** No cap — container grows with its parent. */
    FLUID(Region.USE_COMPUTED_SIZE);

    private final double pixels;

    ContainerWidth(double pixels) {
        this.pixels = pixels;
    }

    /** Returns the pixel cap for this width, or {@link Region#USE_COMPUTED_SIZE} for FLUID. */
    public double pixels() {
        return pixels;
    }
}
