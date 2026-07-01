package io.forja.components.layout.fxResizablePane;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

/**
 * A container whose single child can be resized along one edge by dragging a
 * user-visible handle.
 *
 * <p>{@code FxResizablePane} is a {@link BorderPane} — the resize handle sits
 * on the configured {@link Side} (default {@link Side#RIGHT}). Drag the
 * handle to grow/shrink the child within {@link #getMinExtent} and
 * {@link #getMaxExtent}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxResizablePane sidebar = FxResizablePane.builder()
 *          .child(sidebarContent)
 *          .side(Side.RIGHT)
 *          .extent(240).minExtent(120).maxExtent(480)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxResizablePane extends BorderPane {

    private final ObjectProperty<Node> child = new SimpleObjectProperty<>(this, "child");
    private final ObjectProperty<Side> side = new SimpleObjectProperty<>(this, "side", Side.RIGHT);
    private final DoubleProperty extent = new SimpleDoubleProperty(this, "extent", 240);
    private final DoubleProperty minExtent = new SimpleDoubleProperty(this, "minExtent", 80);
    private final DoubleProperty maxExtent = new SimpleDoubleProperty(this, "maxExtent", Double.MAX_VALUE);

    private final Region handle = new Region();
    private double dragStartValue;
    private double dragStartCoord;

    /**
     * Creates a right-edge-resizable pane with no child.
     */
    public FxResizablePane() {
        super();
        getStyleClass().add("forja-resizable-pane");
        handle.getStyleClass().add("forja-resizable-pane-handle");

        child.addListener((obs, o, v) -> applyChild());
        side.addListener((obs, o, v) -> { applyHandlePlacement(); applyChild(); });
        extent.addListener((obs, o, v) -> applyExtentToChild());
        minExtent.addListener((obs, o, v) -> { if (getExtent() < v.doubleValue()) setExtent(v.doubleValue()); });
        maxExtent.addListener((obs, o, v) -> { if (getExtent() > v.doubleValue()) setExtent(v.doubleValue()); });

        applyHandlePlacement();

        handle.setOnMousePressed(e -> {
            dragStartValue = getExtent();
            dragStartCoord = isHorizontal() ? e.getSceneX() : e.getSceneY();
            e.consume();
        });
        handle.setOnMouseDragged(e -> {
            double delta = (isHorizontal() ? e.getSceneX() : e.getSceneY()) - dragStartCoord;
            double sign = (getSide() == Side.RIGHT || getSide() == Side.BOTTOM) ? -1 : 1;
            double next = dragStartValue - sign * delta;
            if (getSide() == Side.LEFT || getSide() == Side.TOP) next = dragStartValue + delta;
            else next = dragStartValue - delta;
            // Correct sign: dragging away from child edge grows the extent
            if (getSide() == Side.RIGHT || getSide() == Side.BOTTOM) next = dragStartValue + delta;
            else next = dragStartValue - delta;
            double clamped = Math.max(getMinExtent(), Math.min(getMaxExtent(), next));
            setExtent(clamped);
            e.consume();
        });
    }

    private boolean isHorizontal() {
        return getSide() == Side.LEFT || getSide() == Side.RIGHT;
    }

    private void applyHandlePlacement() {
        setTop(null); setBottom(null); setLeft(null); setRight(null);
        // Handle always sits on the opposite edge from the child, but for
        // simple RIGHT/BOTTOM: handle is on the right/bottom of the child.
        handle.setCursor(isHorizontal() ? Cursor.H_RESIZE : Cursor.V_RESIZE);
        if (isHorizontal()) {
            handle.setPrefWidth(6);
            handle.setMinWidth(6);
            handle.setMaxWidth(6);
            handle.setPrefHeight(Region.USE_COMPUTED_SIZE);
        } else {
            handle.setPrefHeight(6);
            handle.setMinHeight(6);
            handle.setMaxHeight(6);
            handle.setPrefWidth(Region.USE_COMPUTED_SIZE);
        }
        switch (getSide()) {
            case RIGHT:  setRight(handle);  break;
            case LEFT:   setLeft(handle);   break;
            case BOTTOM: setBottom(handle); break;
            case TOP:    setTop(handle);    break;
        }
    }

    private void applyChild() {
        Node c = getChild();
        setCenter(c);
        applyExtentToChild();
    }

    private void applyExtentToChild() {
        Node c = getChild();
        if (!(c instanceof Region)) return;
        Region r = (Region) c;
        double e = getExtent();
        if (isHorizontal()) {
            r.setPrefWidth(e);
            r.setMinWidth(e);
            r.setMaxWidth(e);
        } else {
            r.setPrefHeight(e);
            r.setMinHeight(e);
            r.setMaxHeight(e);
        }
    }

    /** Returns the child property. */
    public ObjectProperty<Node> childProperty() { return child; }

    /** Returns the current child. */
    public Node getChild() { return child.get(); }

    /** Sets the child. */
    public void setChild(Node v) { child.set(v); }

    /** Returns the resize-side property. */
    public ObjectProperty<Side> sideProperty() { return side; }

    /** Returns the current resize side. */
    public Side getSide() { return side.get(); }

    /** Sets which side hosts the resize handle. */
    public void setSide(Side v) { side.set(v == null ? Side.RIGHT : v); }

    /** Returns the extent (child's width or height on the drag axis). */
    public DoubleProperty extentProperty() { return extent; }

    /** Returns the current extent. */
    public double getExtent() { return extent.get(); }

    /** Sets the extent, clamped to {@code [min, max]}. */
    public void setExtent(double v) {
        double clamped = Math.max(getMinExtent(), Math.min(getMaxExtent(), v));
        extent.set(clamped);
    }

    /** Returns the min-extent property. */
    public DoubleProperty minExtentProperty() { return minExtent; }

    /** Returns the current min extent. */
    public double getMinExtent() { return minExtent.get(); }

    /** Sets the minimum extent. */
    public void setMinExtent(double v) { minExtent.set(Math.max(0, v)); }

    /** Returns the max-extent property. */
    public DoubleProperty maxExtentProperty() { return maxExtent; }

    /** Returns the current max extent. */
    public double getMaxExtent() { return maxExtent.get(); }

    /** Sets the maximum extent. */
    public void setMaxExtent(double v) { maxExtent.set(Math.max(0, v)); }

    /** Returns the resize-handle node. */
    public Region getHandle() { return handle; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxResizablePane}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxResizablePane}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>child — {@code null}</li>
     *   <li>side — {@link Side#RIGHT}</li>
     *   <li>extent — {@code 240}</li>
     *   <li>minExtent — {@code 80}</li>
     *   <li>maxExtent — {@link Double#MAX_VALUE}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxResizablePane, Builder> {

        private Node child;
        private Side side = Side.RIGHT;
        private double extent = 240;
        private double minExtent = 80;
        private double maxExtent = Double.MAX_VALUE;

        public Builder child(Node child) { this.child = child; return this; }
        public Builder side(Side side) { this.side = side == null ? Side.RIGHT : side; return this; }
        public Builder extent(double extent) { this.extent = extent; return this; }
        public Builder minExtent(double minExtent) { this.minExtent = Math.max(0, minExtent); return this; }
        public Builder maxExtent(double maxExtent) { this.maxExtent = Math.max(0, maxExtent); return this; }

        @Override
        public FxResizablePane build() {
            FxResizablePane p = new FxResizablePane();
            p.setSide(side);
            p.setMinExtent(minExtent);
            p.setMaxExtent(maxExtent);
            p.setExtent(extent);
            p.setChild(child);
            applyBase(p);
            return p;
        }
    }
}
