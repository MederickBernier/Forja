package io.forja.components.layout.fxSpacer;

import io.forja.builder.FxNodeBuilder;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * A spacing primitive for {@link HBox} / {@link VBox} layouts.
 *
 * <p>{@code FxSpacer} comes in two modes:</p>
 * <ul>
 *   <li><b>Flex</b> (default) — fills available space, pushing adjacent
 *       children apart. Equivalent to a CSS {@code flex: 1} spacer.</li>
 *   <li><b>Fixed</b> — occupies a fixed pixel size along an explicit axis.
 *       Useful for inserting precise gaps where a parent's {@code spacing}
 *       isn't granular enough.</li>
 * </ul>
 *
 * <p>The preferred way to construct an {@code FxSpacer} is via the builder:</p>
 * <pre>
 *     {@code
 *      HBox row = new HBox(
 *          new FxButton("Back"),
 *          FxSpacer.builder().build(),   // pushes Save/Cancel to the right
 *          new FxButton("Cancel"),
 *          new FxButton("Save")
 *      );
 *
 *      FxSpacer gap = FxSpacer.builder().size(24).build();
 *     }
 * </pre>
 *
 * <p>Like {@link FxIcon} and {@link FxStatusDot}, {@code FxSpacer} is not a
 * JavaFX {@link javafx.scene.control.Control} — its builder is standalone.
 *
 * @see Builder
 */
public class FxSpacer extends Region {

    /**
     * Creates a flex spacer that grows along both axes when placed inside
     * an {@link HBox} or {@link VBox}.
     */
    public FxSpacer() {
        getStyleClass().add("forja-spacer");
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    /**
     * Creates a fixed-size spacer of the given pixel size along both axes.
     *
     * @param size the spacer size in pixels
     */
    public FxSpacer(double size) {
        getStyleClass().add("forja-spacer");
        setMinWidth(size);
        setPrefWidth(size);
        setMaxWidth(size);
        setMinHeight(size);
        setPrefHeight(size);
        setMaxHeight(size);
    }

    /**
     * Creates a fixed-size spacer along the given axis. The orthogonal axis
     * stays at its default (auto).
     *
     * @param size the spacer size in pixels along {@code axis}
     * @param axis the axis the size applies to
     */
    public FxSpacer(double size, Orientation axis) {
        getStyleClass().add("forja-spacer");
        if (axis == Orientation.HORIZONTAL) {
            setMinWidth(size);
            setPrefWidth(size);
            setMaxWidth(size);
        } else {
            setMinHeight(size);
            setPrefHeight(size);
            setMaxHeight(size);
        }
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSpacer}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxSpacer}.
     *
     * <p>Defaults (when neither {@code size} nor {@code axis} is set):
     * flex-grow spacer on both axes.
     */
    public static class Builder extends FxNodeBuilder<FxSpacer, Builder> {

        private Double size;
        private Orientation axis;

        /**
         * Sets a fixed size along both axes. Replaces flex-grow behavior.
         *
         * @param size pixel size
         * @return this builder
         */
        public Builder size(double size) {
            this.size = size;
            this.axis = null;
            return this;
        }

        /**
         * Sets a fixed size along a single axis. The orthogonal axis stays
         * at its default size.
         *
         * @param size pixel size along {@code axis}
         * @param axis the constrained axis
         * @return this builder
         */
        public Builder size(double size, Orientation axis) {
            this.size = size;
            this.axis = axis;
            return this;
        }

        public FxSpacer build() {
            FxSpacer spacer;
            if (size == null) {
                spacer = new FxSpacer();
            } else if (axis == null) {
                spacer = new FxSpacer(size);
            } else {
                spacer = new FxSpacer(size, axis);
            }
            applyBase(spacer);
            return spacer;
        }
    }
}
