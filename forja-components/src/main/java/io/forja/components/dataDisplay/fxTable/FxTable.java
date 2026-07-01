package io.forja.components.dataDisplay.fxTable;

import io.forja.builder.FxComponentBuilder;
import javafx.collections.FXCollections;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A styled table view built on {@link TableView}.
 *
 * <p>Extends the standard JavaFX {@code TableView<T>} — column construction,
 * sorting, filtering, and selection APIs remain fully accessible. Forja only
 * adds the {@code forja-table} style class and a fluent builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      TableColumn<User, String> nameCol = new TableColumn<>("Name");
 *      nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
 *      FxTable<User> users = FxTable.<User>builder()
 *          .items(userList)
 *          .columns(nameCol, emailCol)
 *          .selectionMode(SelectionMode.MULTIPLE)
 *          .build();
 *     }
 * </pre>
 *
 * @param <T> the row type
 * @see Builder
 */
public class FxTable<T> extends TableView<T> {

    /**
     * Creates an empty {@code FxTable}.
     */
    public FxTable() {
        super();
        getStyleClass().add("forja-table");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxTable}.
     *
     * @param <T> row type
     * @return a new {@link Builder} instance
     */
    public static <T> Builder<T> builder() { return new Builder<T>(); }

    /**
     * Fluent builder for constructing an {@link FxTable}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     *   <li>columns — empty</li>
     *   <li>selectionMode — {@link SelectionMode#SINGLE}</li>
     *   <li>editable — {@code false}</li>
     * </ul>
     *
     * @param <T> row type
     */
    public static class Builder<T> extends FxComponentBuilder<FxTable<T>, Builder<T>> {

        private List<T> items = Collections.emptyList();
        private List<TableColumn<T, ?>> columns = Collections.emptyList();
        private SelectionMode selectionMode = SelectionMode.SINGLE;
        private boolean editable = false;

        public Builder<T> items(List<T> items) { this.items = items == null ? Collections.<T>emptyList() : items; return this; }

        @SafeVarargs
        public final Builder<T> items(T... items) { return items(items == null ? Collections.<T>emptyList() : Arrays.asList(items)); }

        @SafeVarargs
        public final Builder<T> columns(TableColumn<T, ?>... columns) {
            this.columns = columns == null ? Collections.<TableColumn<T, ?>>emptyList() : Arrays.asList(columns);
            return this;
        }

        public Builder<T> selectionMode(SelectionMode selectionMode) { this.selectionMode = selectionMode == null ? SelectionMode.SINGLE : selectionMode; return this; }
        public Builder<T> editable(boolean editable) { this.editable = editable; return this; }

        @Override
        public FxTable<T> build() {
            FxTable<T> t = new FxTable<T>();
            t.setItems(FXCollections.observableArrayList(items));
            t.getColumns().addAll(columns);
            t.getSelectionModel().setSelectionMode(selectionMode);
            t.setEditable(editable);
            applyBase(t);
            return t;
        }
    }
}
