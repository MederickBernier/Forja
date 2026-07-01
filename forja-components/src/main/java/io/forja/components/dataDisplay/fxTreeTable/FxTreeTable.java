package io.forja.components.dataDisplay.fxTreeTable;

import io.forja.builder.FxComponentBuilder;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A styled tree-table view built on {@link TreeTableView}.
 *
 * <p>Extends the standard JavaFX {@code TreeTableView<T>} — column
 * construction, hierarchy, and selection APIs remain fully accessible. Forja
 * only adds the {@code forja-tree-table} style class and a fluent builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxTreeTable<File> tt = FxTreeTable.<File>builder()
 *          .root(rootItem)
 *          .columns(nameCol, sizeCol)
 *          .build();
 *     }
 * </pre>
 *
 * @param <T> the item type
 * @see Builder
 */
public class FxTreeTable<T> extends TreeTableView<T> {

    /**
     * Creates an empty {@code FxTreeTable}.
     */
    public FxTreeTable() {
        super();
        getStyleClass().add("forja-tree-table");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxTreeTable}.
     *
     * @param <T> item type
     * @return a new {@link Builder} instance
     */
    public static <T> Builder<T> builder() { return new Builder<T>(); }

    /**
     * Fluent builder for constructing an {@link FxTreeTable}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>root — {@code null}</li>
     *   <li>showRoot — {@code true}</li>
     *   <li>columns — empty</li>
     *   <li>selectionMode — {@link SelectionMode#SINGLE}</li>
     *   <li>editable — {@code false}</li>
     * </ul>
     *
     * @param <T> item type
     */
    public static class Builder<T> extends FxComponentBuilder<FxTreeTable<T>, Builder<T>> {

        private TreeItem<T> root;
        private boolean showRoot = true;
        private List<TreeTableColumn<T, ?>> columns = Collections.emptyList();
        private SelectionMode selectionMode = SelectionMode.SINGLE;
        private boolean editable = false;

        public Builder<T> root(TreeItem<T> root) { this.root = root; return this; }
        public Builder<T> showRoot(boolean showRoot) { this.showRoot = showRoot; return this; }

        @SafeVarargs
        public final Builder<T> columns(TreeTableColumn<T, ?>... columns) {
            this.columns = columns == null ? Collections.<TreeTableColumn<T, ?>>emptyList() : Arrays.asList(columns);
            return this;
        }

        public Builder<T> selectionMode(SelectionMode selectionMode) { this.selectionMode = selectionMode == null ? SelectionMode.SINGLE : selectionMode; return this; }
        public Builder<T> editable(boolean editable) { this.editable = editable; return this; }

        @Override
        public FxTreeTable<T> build() {
            FxTreeTable<T> t = new FxTreeTable<T>();
            t.setRoot(root);
            t.setShowRoot(showRoot);
            t.getColumns().addAll(columns);
            t.getSelectionModel().setSelectionMode(selectionMode);
            t.setEditable(editable);
            applyBase(t);
            return t;
        }
    }
}
