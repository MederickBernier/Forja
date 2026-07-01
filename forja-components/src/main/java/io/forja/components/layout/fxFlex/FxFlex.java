package io.forja.components.layout.fxFlex;

import io.forja.builder.FxNodeBuilder;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.util.Arrays;

/**
 * A wrap-flow layout — a styled {@link FlowPane}.
 *
 * <p>Extends {@code FlowPane} directly — children are laid out row-by-row
 * (or column-by-column) and wrap to a new line when the axis fills.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxFlex tags = FxFlex.builder()
 *          .hgap(6).vgap(6)
 *          .children(tag1, tag2, tag3, tag4)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxFlex extends FlowPane {

    /**
     * Creates an empty horizontal {@code FxFlex}.
     */
    public FxFlex() {
        super();
        getStyleClass().add("forja-flex");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxFlex}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxFlex}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>orientation — {@link Orientation#HORIZONTAL}</li>
     *   <li>hgap / vgap — {@code 0}</li>
     *   <li>alignment — {@link Pos#TOP_LEFT}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxFlex, Builder> {

        private Orientation orientation = Orientation.HORIZONTAL;
        private double hgap = 0;
        private double vgap = 0;
        private Pos alignment = Pos.TOP_LEFT;
        private Node[] children = new Node[0];

        public Builder orientation(Orientation orientation) { this.orientation = orientation == null ? Orientation.HORIZONTAL : orientation; return this; }
        public Builder hgap(double hgap) { this.hgap = hgap; return this; }
        public Builder vgap(double vgap) { this.vgap = vgap; return this; }
        public Builder gap(double gap) { this.hgap = gap; this.vgap = gap; return this; }
        public Builder alignment(Pos alignment) { this.alignment = alignment == null ? Pos.TOP_LEFT : alignment; return this; }
        public Builder children(Node... children) { this.children = children == null ? new Node[0] : children; return this; }

        @Override
        public FxFlex build() {
            FxFlex f = new FxFlex();
            f.setOrientation(orientation);
            f.setHgap(hgap);
            f.setVgap(vgap);
            f.setAlignment(alignment);
            f.getChildren().addAll(Arrays.asList(children));
            applyBase(f);
            return f;
        }
    }
}
