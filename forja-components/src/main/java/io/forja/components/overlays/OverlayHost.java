package io.forja.components.overlays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

/**
 * Per-{@link Scene} overlay layer for Forja overlays (toasts, popovers,
 * dialogs).
 *
 * <p>On first use for a given scene, {@code OverlayHost} wraps the scene's
 * root in a transparent {@link StackPane} that contains the original root at
 * the bottom and an overlay {@link StackPane} on top. All overlay nodes
 * ({@code FxToast}, {@code FxDialog}, {@code FxPopover}, etc.) are added
 * to the overlay layer via {@link #show(Scene, Node)}.
 *
 * <p>The overlay layer is {@code mouseTransparent} except where its own
 * children absorb events, so the underlying app remains fully interactive
 * outside overlays.
 *
 * <p>Usage is typically indirect — components call {@code OverlayHost.show}
 * internally. Direct use is fine for custom overlay content:
 * <pre>
 *     {@code
 *      OverlayHost.show(scene, myCustomOverlayNode);
 *      OverlayHost.dismiss(myCustomOverlayNode);
 *     }
 * </pre>
 */
public final class OverlayHost {

    private static final String OVERLAY_ID = "forja-overlay-layer";
    private static final String WRAPPER_ID = "forja-overlay-wrapper";

    private OverlayHost() {}

    /**
     * Returns the overlay layer for the given scene, installing it if this
     * is the first call.
     *
     * @param scene the target scene
     * @return the overlay {@link StackPane}
     */
    public static StackPane getLayer(Scene scene) {
        if (scene == null) throw new IllegalArgumentException("scene must not be null");
        StackPane existing = findExistingLayer(scene);
        if (existing != null) return existing;
        return install(scene);
    }

    /**
     * Adds an overlay node to the scene's overlay layer.
     *
     * @param scene the target scene
     * @param node  the overlay node
     */
    public static void show(Scene scene, Node node) {
        if (node == null) return;
        StackPane layer = getLayer(scene);
        if (!layer.getChildren().contains(node)) {
            layer.getChildren().add(node);
        }
    }

    /**
     * Removes an overlay node from any Forja overlay layer it may be in.
     *
     * @param node the overlay node to remove
     */
    public static void dismiss(Node node) {
        if (node == null) return;
        Parent p = node.getParent();
        if (p instanceof StackPane && OVERLAY_ID.equals(p.getId())) {
            ((StackPane) p).getChildren().remove(node);
        }
    }

    /**
     * Removes every overlay from the scene's overlay layer.
     *
     * @param scene the target scene
     */
    public static void clear(Scene scene) {
        StackPane layer = findExistingLayer(scene);
        if (layer != null) layer.getChildren().clear();
    }

    /**
     * Returns a live, read-only view of the overlay layer's children. Useful
     * for testing.
     *
     * @param scene the target scene
     * @return an observable list; may be empty
     */
    public static ObservableList<Node> overlays(Scene scene) {
        StackPane layer = findExistingLayer(scene);
        return layer == null ? FXCollections.<Node>observableArrayList()
                             : FXCollections.unmodifiableObservableList(layer.getChildren());
    }

    private static StackPane findExistingLayer(Scene scene) {
        Parent root = scene.getRoot();
        if (root instanceof StackPane && WRAPPER_ID.equals(root.getId())) {
            for (Node child : ((StackPane) root).getChildren()) {
                if (child instanceof StackPane && OVERLAY_ID.equals(child.getId())) {
                    return (StackPane) child;
                }
            }
        }
        return null;
    }

    private static StackPane install(Scene scene) {
        Parent oldRoot = scene.getRoot();
        StackPane wrapper = new StackPane();
        wrapper.setId(WRAPPER_ID);
        wrapper.setPickOnBounds(false);

        // Swap the scene's root to the empty wrapper first so oldRoot is
        // detached; then re-parent oldRoot under wrapper.
        scene.setRoot(wrapper);
        wrapper.getChildren().add(oldRoot);

        StackPane overlay = new StackPane();
        overlay.setId(OVERLAY_ID);
        overlay.setPickOnBounds(false);
        overlay.setMouseTransparent(false);
        overlay.getStyleClass().add("forja-overlay-layer");
        StackPane.setAlignment(overlay, Pos.TOP_LEFT);
        wrapper.getChildren().add(overlay);

        return overlay;
    }
}
