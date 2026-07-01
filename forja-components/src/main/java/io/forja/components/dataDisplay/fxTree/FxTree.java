package io.forja.components.dataDisplay.fxTree;

import io.forja.builder.FxComponentBuilder;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * A styled tree view built on {@link TreeView}.
 *
 * <p>Extends the standard JavaFX {@code TreeView<T>} — cell factories, item
 * expansion, and selection APIs remain fully accessible. Forja only adds
 * the {@code forja-tree} style class and a fluent builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      TreeItem<String> root = new TreeItem<>("Files");
 *      root.getChildren().add(new TreeItem<>("README.md"));
 *      FxTree<String> tree = FxTree.<String>builder()
 *          .root(root)
 *          .showRoot(true)
 *          .build();
 *     }
 * </pre>
 *
 * @param <T> the item type
 * @see Builder
 */
public class FxTree<T> extends TreeView<T> {

    /**
     * Creates an empty {@code FxTree}.
     */
    public FxTree() {
        super();
        getStyleClass().add("forja-tree");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxTree}.
     *
     * @param <T> item type
     * @return a new {@link Builder} instance
     */
    public static <T> Builder<T> builder() { return new Builder<T>(); }

    /**
     * Fluent builder for constructing an {@link FxTree}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>root — {@code null}</li>
     *   <li>showRoot — {@code true}</li>
     *   <li>selectionMode — {@link SelectionMode#SINGLE}</li>
     *   <li>editable — {@code false}</li>
     * </ul>
     *
     * @param <T> item type
     */
    public static class Builder<T> extends FxComponentBuilder<FxTree<T>, Builder<T>> {

        private TreeItem<T> root;
        private boolean showRoot = true;
        private SelectionMode selectionMode = SelectionMode.SINGLE;
        private boolean editable = false;

        public Builder<T> root(TreeItem<T> root) { this.root = root; return this; }
        public Builder<T> showRoot(boolean showRoot) { this.showRoot = showRoot; return this; }
        public Builder<T> selectionMode(SelectionMode selectionMode) { this.selectionMode = selectionMode == null ? SelectionMode.SINGLE : selectionMode; return this; }
        public Builder<T> editable(boolean editable) { this.editable = editable; return this; }

        @Override
        public FxTree<T> build() {
            FxTree<T> t = new FxTree<T>();
            t.setRoot(root);
            t.setShowRoot(showRoot);
            t.getSelectionModel().setSelectionMode(selectionMode);
            t.setEditable(editable);
            applyBase(t);
            return t;
        }
    }
}
