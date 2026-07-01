package io.forja.components.utilities.fxFocusTrap;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Non-visual utility that traps keyboard focus inside a root node's subtree.
 *
 * <p>When installed, {@code FxFocusTrap} intercepts {@code TAB} /
 * {@code SHIFT+TAB} on the root's key-pressed event and cycles focus among
 * the focusable descendants (nodes with {@link Node#isFocusTraversable()} ==
 * {@code true} that are visible and not disabled).
 *
 * <p>Typical use: overlay dialogs — install on the dialog's panel so users
 * can't tab out into the underlying app.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxFocusTrap trap = FxFocusTrap.builder()
 *          .root(dialogPanel)
 *          .build();
 *      trap.install();
 *      // later
 *      trap.uninstall();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxFocusTrap {

    private Parent root;
    private EventHandler<KeyEvent> keyHandler;

    /**
     * Creates a detached {@code FxFocusTrap}.
     */
    public FxFocusTrap() {}

    /** Sets the root node to trap focus inside. */
    public void setRoot(Parent root) { this.root = root; }

    /** Returns the current root. */
    public Parent getRoot() { return root; }

    /** Attaches the key-listener to the root. Idempotent. */
    public void install() {
        if (root == null || keyHandler != null) return;
        keyHandler = e -> {
            if (e.getCode() != KeyCode.TAB) return;
            List<Node> focusables = collectFocusable(root);
            if (focusables.isEmpty()) return;
            Node current = findFocused(root);
            int idx = current == null ? -1 : focusables.indexOf(current);
            int next;
            if (e.isShiftDown()) {
                next = idx <= 0 ? focusables.size() - 1 : idx - 1;
            } else {
                next = (idx + 1) % focusables.size();
            }
            focusables.get(next).requestFocus();
            e.consume();
        };
        root.addEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
    }

    /** Detaches the key-listener from the root. */
    public void uninstall() {
        if (root != null && keyHandler != null) {
            root.removeEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
        }
        keyHandler = null;
    }

    private static List<Node> collectFocusable(Parent root) {
        List<Node> out = new ArrayList<>();
        walk(root, out);
        return out;
    }

    private static void walk(Node n, List<Node> out) {
        if (n == null || !n.isVisible() || n.isDisabled()) return;
        if (n.isFocusTraversable()) out.add(n);
        if (n instanceof Parent) {
            for (Node child : ((Parent) n).getChildrenUnmodifiable()) walk(child, out);
        }
    }

    private static Node findFocused(Parent root) {
        if (root.getScene() == null) return null;
        return root.getScene().getFocusOwner();
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxFocusTrap}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxFocusTrap}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>root — {@code null}</li>
     *   <li>autoInstall — {@code true}</li>
     * </ul>
     */
    public static class Builder {

        private Parent root;
        private boolean autoInstall = true;

        public Builder root(Parent root) { this.root = root; return this; }
        public Builder autoInstall(boolean autoInstall) { this.autoInstall = autoInstall; return this; }

        public FxFocusTrap build() {
            FxFocusTrap t = new FxFocusTrap();
            t.setRoot(root);
            if (autoInstall && root != null) t.install();
            return t;
        }
    }
}
