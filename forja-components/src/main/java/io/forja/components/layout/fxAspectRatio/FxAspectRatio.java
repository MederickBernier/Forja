package io.forja.components.layout.fxAspectRatio;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Region;

/**
 * A container that constrains its single child to a fixed width:height ratio.
 *
 * <p>{@code FxAspectRatio} takes the available width from its parent, computes
 * {@code height = width / ratio}, and lays out the child (if any) to fill
 * both dimensions.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxAspectRatio banner = FxAspectRatio.builder()
 *          .ratio(16.0 / 9.0)
 *          .child(imageView)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxAspectRatio extends Region {

    private final DoubleProperty ratio = new SimpleDoubleProperty(this, "ratio", 1.0);
    private final ObjectProperty<Node> child = new SimpleObjectProperty<>(this, "child");

    /**
     * Creates a 1:1 {@code FxAspectRatio} with no child.
     */
    public FxAspectRatio() {
        super();
        getStyleClass().add("forja-aspect-ratio");
        ratio.addListener((obs, o, v) -> requestLayout());
        child.addListener((obs, o, v) -> {
            getChildren().clear();
            if (v != null) getChildren().add(v);
            requestLayout();
        });
    }

    @Override
    protected double computePrefHeight(double width) {
        double r = Math.max(0.0001, getRatio());
        return width < 0 ? getPrefHeight() : width / r;
    }

    @Override
    protected void layoutChildren() {
        Node c = getChild();
        if (c == null) return;
        double w = getWidth();
        double r = Math.max(0.0001, getRatio());
        double h = w / r;
        c.resizeRelocate(0, 0, w, h);
    }

    /** Returns the ratio (width / height) property. */
    public DoubleProperty ratioProperty() { return ratio; }

    /** Returns the current width/height ratio. */
    public double getRatio() { return ratio.get(); }

    /** Sets the width/height ratio (e.g. {@code 16.0/9.0}). */
    public void setRatio(double v) { ratio.set(v); }

    /** Returns the child property. */
    public ObjectProperty<Node> childProperty() { return child; }

    /** Returns the current child node, or {@code null}. */
    public Node getChild() { return child.get(); }

    /** Sets the child node; {@code null} clears. */
    public void setChild(Node v) { child.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxAspectRatio}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxAspectRatio}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>ratio — {@code 1.0}</li>
     *   <li>child — {@code null}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxAspectRatio, Builder> {

        private double ratio = 1.0;
        private Node child;

        public Builder ratio(double ratio) { this.ratio = ratio; return this; }
        public Builder child(Node child) { this.child = child; return this; }

        @Override
        public FxAspectRatio build() {
            FxAspectRatio r = new FxAspectRatio();
            r.setRatio(ratio);
            r.setChild(child);
            applyBase(r);
            return r;
        }
    }
}
