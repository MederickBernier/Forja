package io.forja.components.utilities.fxPortal;

import io.forja.components.overlays.OverlayHost;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

/**
 * A "render-elsewhere" helper — attaches a target node to the
 * {@link OverlayHost} overlay layer of a given {@link Scene} whenever
 * {@link #isVisible} is {@code true}, and removes it when {@code false}.
 *
 * <p>Useful for lightbox, dropdown-menu, or popup content that logically
 * belongs to a component but should render on top of the whole scene.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxPortal portal = FxPortal.builder()
 *          .scene(scene)
 *          .node(mySnackbar)
 *          .position(Pos.BOTTOM_CENTER)
 *          .visible(true)
 *          .build();
 *      // later
 *      portal.setVisible(false); // detaches from overlay
 *     }
 * </pre>
 *
 * @see OverlayHost
 * @see Builder
 */
public class FxPortal {

    private final ObjectProperty<Node> node = new SimpleObjectProperty<>(this, "node");
    private final ObjectProperty<Pos> position = new SimpleObjectProperty<>(this, "position", Pos.CENTER);
    private final BooleanProperty visible = new SimpleBooleanProperty(this, "visible", false);
    private Scene scene;

    /**
     * Creates an unattached, hidden {@code FxPortal}.
     */
    public FxPortal() {
        node.addListener((obs, o, v) -> reattach());
        position.addListener((obs, o, v) -> reattach());
        visible.addListener((obs, o, v) -> reattach());
    }

    /** Sets the target scene the node should be attached into. */
    public void setScene(Scene scene) { this.scene = scene; reattach(); }

    /** Returns the current target scene. */
    public Scene getScene() { return scene; }

    /** Returns the node property. */
    public ObjectProperty<Node> nodeProperty() { return node; }

    /** Returns the current attached node. */
    public Node getNode() { return node.get(); }

    /** Sets the node to render into the overlay layer. */
    public void setNode(Node v) { node.set(v); }

    /** Returns the position property. */
    public ObjectProperty<Pos> positionProperty() { return position; }

    /** Returns the current alignment on the overlay layer. */
    public Pos getPosition() { return position.get(); }

    /** Sets the alignment. */
    public void setPosition(Pos v) { position.set(v == null ? Pos.CENTER : v); }

    /** Returns the visible property. */
    public BooleanProperty visibleProperty() { return visible; }

    /** Returns whether the node is currently attached. */
    public boolean isVisible() { return visible.get(); }

    /** Sets whether the node is attached to the overlay layer. */
    public void setVisible(boolean v) { visible.set(v); }

    private void reattach() {
        Node n = getNode();
        if (n == null) return;
        OverlayHost.dismiss(n);
        if (scene != null && isVisible()) {
            StackPane.setAlignment(n, getPosition());
            OverlayHost.show(scene, n);
        }
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxPortal}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxPortal}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>scene / node — {@code null}</li>
     *   <li>position — {@link Pos#CENTER}</li>
     *   <li>visible — {@code false}</li>
     * </ul>
     */
    public static class Builder {

        private Scene scene;
        private Node node;
        private Pos position = Pos.CENTER;
        private boolean visible = false;

        public Builder scene(Scene scene) { this.scene = scene; return this; }
        public Builder node(Node node) { this.node = node; return this; }
        public Builder position(Pos position) { this.position = position == null ? Pos.CENTER : position; return this; }
        public Builder visible(boolean visible) { this.visible = visible; return this; }

        public FxPortal build() {
            FxPortal p = new FxPortal();
            p.setPosition(position);
            p.setNode(node);
            p.setScene(scene);
            p.setVisible(visible);
            return p;
        }
    }
}
