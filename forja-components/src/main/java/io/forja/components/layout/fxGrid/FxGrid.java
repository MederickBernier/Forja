package io.forja.components.layout.fxGrid;

import io.forja.builder.FxNodeBuilder;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * A styled {@link GridPane} for aligning children on a two-dimensional grid.
 *
 * <p>Extends {@code GridPane} directly — all native APIs (row/column
 * constraints, cell spanning, {@link GridPane#setColumnIndex}, etc.) remain
 * fully accessible.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxGrid form = FxGrid.builder()
 *          .hgap(12).vgap(8)
 *          .add(nameLabel, 0, 0).add(nameField, 1, 0)
 *          .add(emailLabel, 0, 1).add(emailField, 1, 1)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxGrid extends GridPane {

    /**
     * Creates an empty {@code FxGrid}.
     */
    public FxGrid() {
        super();
        getStyleClass().add("forja-grid");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxGrid}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Positional record for {@link Builder#add}. */
    private static final class Cell {
        final Node node;
        final int col, row, colSpan, rowSpan;
        Cell(Node node, int col, int row, int colSpan, int rowSpan) {
            this.node = node; this.col = col; this.row = row; this.colSpan = colSpan; this.rowSpan = rowSpan;
        }
    }

    /**
     * Fluent builder for constructing an {@link FxGrid}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>hgap / vgap — {@code 0}</li>
     *   <li>alignment — {@link Pos#TOP_LEFT}</li>
     *   <li>cells — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxGrid, Builder> {

        private double hgap = 0;
        private double vgap = 0;
        private Pos alignment = Pos.TOP_LEFT;
        private final java.util.List<Cell> cells = new java.util.ArrayList<>();

        public Builder hgap(double hgap) { this.hgap = hgap; return this; }
        public Builder vgap(double vgap) { this.vgap = vgap; return this; }
        public Builder alignment(Pos alignment) { this.alignment = alignment == null ? Pos.TOP_LEFT : alignment; return this; }

        /** Adds a child at (col, row) with span 1x1. */
        public Builder add(Node node, int col, int row) {
            return add(node, col, row, 1, 1);
        }

        /** Adds a child at (col, row) with the given colSpan/rowSpan. */
        public Builder add(Node node, int col, int row, int colSpan, int rowSpan) {
            if (node != null) cells.add(new Cell(node, col, row, Math.max(1, colSpan), Math.max(1, rowSpan)));
            return this;
        }

        @Override
        public FxGrid build() {
            FxGrid g = new FxGrid();
            g.setHgap(hgap);
            g.setVgap(vgap);
            g.setAlignment(alignment);
            for (Cell c : cells) g.add(c.node, c.col, c.row, c.colSpan, c.rowSpan);
            applyBase(g);
            return g;
        }
    }
}
