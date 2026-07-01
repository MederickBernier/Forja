package io.forja.components.layout.fxSplitView;

import io.forja.builder.FxComponentBuilder;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

/**
 * A styled resizable split view — a {@link SplitPane} wrapper.
 *
 * <p>Extends {@code SplitPane} directly — divider positioning, item
 * management, and native APIs remain fully accessible.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxSplitView editor = FxSplitView.builder()
 *          .items(fileTree, code)
 *          .dividerPositions(0.25)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxSplitView extends SplitPane {

    /**
     * Creates an empty horizontal {@code FxSplitView}.
     */
    public FxSplitView() {
        super();
        getStyleClass().add("forja-split-view");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSplitView}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxSplitView}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>orientation — {@link Orientation#HORIZONTAL}</li>
     *   <li>items — empty</li>
     *   <li>dividerPositions — auto (default)</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxSplitView, Builder> {

        private Orientation orientation = Orientation.HORIZONTAL;
        private Node[] items = new Node[0];
        private double[] dividerPositions;

        public Builder orientation(Orientation orientation) { this.orientation = orientation == null ? Orientation.HORIZONTAL : orientation; return this; }
        public Builder items(Node... items) { this.items = items == null ? new Node[0] : items; return this; }
        public Builder dividerPositions(double... positions) { this.dividerPositions = positions; return this; }

        @Override
        public FxSplitView build() {
            FxSplitView s = new FxSplitView();
            s.setOrientation(orientation);
            s.getItems().addAll(items);
            if (dividerPositions != null && dividerPositions.length > 0) {
                s.setDividerPositions(dividerPositions);
            }
            applyBase(s);
            return s;
        }
    }
}
