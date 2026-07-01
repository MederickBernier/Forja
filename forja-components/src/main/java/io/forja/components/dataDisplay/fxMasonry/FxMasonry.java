package io.forja.components.dataDisplay.fxMasonry;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.layout.Region;

import java.util.Arrays;

/**
 * A masonry layout — a {@link Region} that packs children into equal-width
 * columns using a greedy "shortest column" algorithm. Each child's height
 * is respected, columns grow independently.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxMasonry m = FxMasonry.builder()
 *          .columns(3)
 *          .gap(8)
 *          .children(card1, card2, card3, card4, card5)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxMasonry extends Region {

    private final IntegerProperty columns = new SimpleIntegerProperty(this, "columns", 3);
    private final DoubleProperty gap = new SimpleDoubleProperty(this, "gap", 8);

    /**
     * Creates an empty 3-column {@code FxMasonry}.
     */
    public FxMasonry() {
        super();
        getStyleClass().add("forja-masonry");
        columns.addListener((obs, o, v) -> requestLayout());
        gap.addListener((obs, o, v) -> requestLayout());
    }

    /** Returns the mutable children list (proxy for {@code getChildren}). */
    public javafx.collections.ObservableList<Node> getItems() { return getChildren(); }

    /** Returns the column-count property. */
    public IntegerProperty columnsProperty() { return columns; }
    /** Returns the column count. */
    public int getColumns() { return columns.get(); }
    /** Sets the column count. */
    public void setColumns(int v) { columns.set(Math.max(1, v)); }

    /** Returns the gap property. */
    public DoubleProperty gapProperty() { return gap; }
    /** Returns the gap in px. */
    public double getGap() { return gap.get(); }
    /** Sets the gap in px. */
    public void setGap(double v) { gap.set(Math.max(0, v)); }

    @Override
    protected double computePrefHeight(double width) {
        double[] heights = layoutColumns(width, false);
        double max = 0;
        for (double h : heights) if (h > max) max = h;
        return max;
    }

    @Override
    protected void layoutChildren() {
        layoutColumns(getWidth(), true);
    }

    private double[] layoutColumns(double width, boolean apply) {
        int cols = Math.max(1, getColumns());
        double g = getGap();
        double colW = Math.max(1, (width - g * (cols - 1)) / cols);
        double[] colHeights = new double[cols];
        for (Node n : getChildren()) {
            int target = 0;
            for (int i = 1; i < cols; i++) if (colHeights[i] < colHeights[target]) target = i;
            double x = target * (colW + g);
            double y = colHeights[target];
            double h = n.prefHeight(colW);
            if (apply) n.resizeRelocate(x, y, colW, h);
            colHeights[target] = y + h + g;
        }
        return colHeights;
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxMasonry}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxMasonry}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>columns — {@code 3}</li>
     *   <li>gap — {@code 8}</li>
     *   <li>children — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxMasonry, Builder> {

        private int cols = 3;
        private double gap = 8;
        private Node[] children = new Node[0];

        public Builder columns(int cols) { this.cols = Math.max(1, cols); return this; }
        public Builder gap(double gap) { this.gap = Math.max(0, gap); return this; }
        public Builder children(Node... children) { this.children = children == null ? new Node[0] : children; return this; }

        @Override
        public FxMasonry build() {
            FxMasonry m = new FxMasonry();
            m.setColumns(cols);
            m.setGap(gap);
            m.getChildren().addAll(Arrays.asList(children));
            applyBase(m);
            return m;
        }
    }
}
