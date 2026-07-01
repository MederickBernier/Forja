package io.forja.components.feedbackAndStatus.fxSkeleton;

import io.forja.builder.FxNodeBuilder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * A shimmer placeholder shape for loading states.
 *
 * <p>{@code FxSkeleton} is a {@link Region} sized by prefWidth/prefHeight
 * (via {@link Builder#width}, {@link Builder#height}) or by inheriting from
 * its parent. It animates a pulsing opacity to signal "still loading" and
 * supports three shape variants: {@link Shape#RECT}, {@link Shape#TEXT}
 * (rounded pill), and {@link Shape#CIRCLE}.
 *
 * <p>The {@link #animatingProperty()} controls the shimmer animation; stop it
 * once real content is ready.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxSkeleton avatar = FxSkeleton.builder()
 *          .shape(FxSkeleton.Shape.CIRCLE)
 *          .width(48).height(48)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxSkeleton extends Region {

    /** Shape variant options. */
    public enum Shape { RECT, TEXT, CIRCLE }

    private static final PseudoClass RECT_PC   = PseudoClass.getPseudoClass("rect");
    private static final PseudoClass TEXT_PC   = PseudoClass.getPseudoClass("text");
    private static final PseudoClass CIRCLE_PC = PseudoClass.getPseudoClass("circle");

    private final ObjectProperty<Shape> shape = new SimpleObjectProperty<>(this, "shape", Shape.RECT);
    private final BooleanProperty animating = new SimpleBooleanProperty(this, "animating", true);

    private final Timeline shimmer;

    /**
     * Creates a rectangular {@code FxSkeleton} with animation enabled.
     */
    public FxSkeleton() {
        super();
        getStyleClass().add("forja-skeleton");
        shape.addListener((obs, o, v) -> applyShapePseudoClass());
        applyShapePseudoClass();

        setOpacity(1.0);
        shimmer = new Timeline(
                new KeyFrame(Duration.ZERO,       new KeyValue(opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(600), new KeyValue(opacityProperty(), 0.4)),
                new KeyFrame(Duration.millis(1200),new KeyValue(opacityProperty(), 1.0))
        );
        shimmer.setCycleCount(Animation.INDEFINITE);

        animating.addListener((obs, o, v) -> {
            if (v) shimmer.playFromStart();
            else { shimmer.stop(); setOpacity(1.0); }
        });
    }

    private void applyShapePseudoClass() {
        pseudoClassStateChanged(RECT_PC,   false);
        pseudoClassStateChanged(TEXT_PC,   false);
        pseudoClassStateChanged(CIRCLE_PC, false);
        switch (getSkeletonShape()) {
            case RECT:   pseudoClassStateChanged(RECT_PC,   true); break;
            case TEXT:   pseudoClassStateChanged(TEXT_PC,   true); break;
            case CIRCLE: pseudoClassStateChanged(CIRCLE_PC, true); break;
        }
    }

    /** Returns the shape property. */
    public ObjectProperty<Shape> skeletonShapeProperty() { return shape; }

    /** Returns the current shape. */
    public Shape getSkeletonShape() { return shape.get(); }

    /** Sets the shape. */
    public void setSkeletonShape(Shape v) { shape.set(v == null ? Shape.RECT : v); }

    /** Returns the animating property. */
    public BooleanProperty animatingProperty() { return animating; }

    /** Returns whether the shimmer animation is running. */
    public boolean isAnimating() { return animating.get(); }

    /** Sets whether the shimmer animation runs. */
    public void setAnimating(boolean v) { animating.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSkeleton}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxSkeleton}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>shape — {@link Shape#RECT}</li>
     *   <li>width / height — inherit (unset)</li>
     *   <li>animating — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxSkeleton, Builder> {

        private Shape shape = Shape.RECT;
        private Double width;
        private Double height;
        private boolean animating = true;

        public Builder shape(Shape shape) { this.shape = shape == null ? Shape.RECT : shape; return this; }
        public Builder width(double width) { this.width = width; return this; }
        public Builder height(double height) { this.height = height; return this; }
        public Builder animating(boolean animating) { this.animating = animating; return this; }

        @Override
        public FxSkeleton build() {
            FxSkeleton s = new FxSkeleton();
            s.setSkeletonShape(shape);
            if (width != null)  { s.setPrefWidth(width);  s.setMinWidth(width);  s.setMaxWidth(width); }
            if (height != null) { s.setPrefHeight(height);s.setMinHeight(height);s.setMaxHeight(height); }
            s.setAnimating(animating);
            applyBase(s);
            return s;
        }
    }
}
