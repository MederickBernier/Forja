package io.forja.components.navigation.fxSidebar;

import io.forja.builder.FxNodeBuilder;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.Arrays;

/**
 * A vertical container for navigation content — a styled {@link VBox} with
 * a fixed default width.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxSidebar side = FxSidebar.builder()
 *          .width(240)
 *          .children(sidebarNav, footer)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxSidebar extends VBox {

    /**
     * Creates an empty {@code FxSidebar} at default width (240).
     */
    public FxSidebar() {
        super();
        getStyleClass().add("forja-sidebar");
        setPrefWidth(240);
        setSpacing(6);
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSidebar}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxSidebar}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>width — {@code 240}</li>
     *   <li>spacing — {@code 6}</li>
     *   <li>children — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxSidebar, Builder> {

        private double width = 240;
        private double spacing = 6;
        private Node[] children = new Node[0];

        public Builder width(double width) { this.width = width; return this; }
        public Builder spacing(double spacing) { this.spacing = spacing; return this; }
        public Builder children(Node... children) { this.children = children == null ? new Node[0] : children; return this; }

        @Override
        public FxSidebar build() {
            FxSidebar s = new FxSidebar();
            s.setPrefWidth(width);
            s.setSpacing(spacing);
            s.getChildren().addAll(Arrays.asList(children));
            applyBase(s);
            return s;
        }
    }
}
