package io.forja.components.dataDisplay.fxDataGrid;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.dataDisplay.fxTable.FxTable;
import io.forja.components.inputs.fxSearchField.FxSearchField;
import io.forja.components.navigation.fxPagination.FxPagination;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * A sortable, filterable, paginated table.
 *
 * <p>{@code FxDataGrid} composes an {@link FxTable} with an
 * {@link FxSearchField} filter (uses a caller-provided
 * {@link BiPredicate}) and an {@link FxPagination} control. All state is
 * exposed for advanced binding.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxDataGrid<User> grid = FxDataGrid.<User>builder()
 *          .items(users)
 *          .columns(nameCol, emailCol)
 *          .pageSize(20)
 *          .filter((row, q) -> row.name().toLowerCase().contains(q.toLowerCase()))
 *          .build();
 *     }
 * </pre>
 *
 * @param <T> the row type
 * @see Builder
 */
public class FxDataGrid<T> extends VBox {

    private final FxSearchField searchField = FxSearchField.builder().promptText("Filter").clearable(true).build();
    private final HBox toolbar = new HBox(8, searchField);
    private final FxTable<T> table;
    private final FxPagination pagination = FxPagination.builder().totalPages(1).build();

    private final ObservableList<T> source = FXCollections.observableArrayList();
    private FilteredList<T> filtered;
    private SortedList<T> sorted;
    private int pageSize = 20;
    private BiPredicate<T, String> filter;

    /**
     * Creates an empty {@code FxDataGrid}.
     */
    public FxDataGrid() {
        super();
        getStyleClass().add("forja-data-grid");
        setSpacing(6);
        table = new FxTable<T>();
        table.getStyleClass().add("forja-data-grid-table");
        VBox.setVgrow(table, Priority.ALWAYS);
        toolbar.getStyleClass().add("forja-data-grid-toolbar");
        toolbar.setAlignment(Pos.CENTER_LEFT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox paginationRow = new HBox(pagination);
        paginationRow.setAlignment(Pos.CENTER);
        getChildren().addAll(toolbar, table, paginationRow);

        filtered = new FilteredList<>(source, x -> true);
        sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(table.comparatorProperty());

        searchField.textProperty().addListener((obs, o, v) -> applyFilter());
        pagination.currentPageProperty().addListener((obs, o, v) -> refreshPage());
        source.addListener((javafx.collections.ListChangeListener<T>) c -> refreshPage());
    }

    private void applyFilter() {
        final String q = searchField.getText() == null ? "" : searchField.getText();
        if (filter == null || q.isEmpty()) {
            filtered.setPredicate(x -> true);
        } else {
            filtered.setPredicate(x -> filter.test(x, q));
        }
        refreshPage();
    }

    private void refreshPage() {
        int size = Math.max(1, pageSize);
        int total = Math.max(1, (int) Math.ceil(sorted.size() / (double) size));
        pagination.setTotalPages(total);
        int page = Math.max(0, Math.min(pagination.getCurrentPage(), total - 1));
        int from = page * size;
        int to = Math.min(sorted.size(), from + size);
        table.getItems().setAll(sorted.subList(from, to));
    }

    /** Returns the source items (add/remove to update grid). */
    public ObservableList<T> getItems() { return source; }
    /** Returns the underlying table. */
    public FxTable<T> getTable() { return table; }
    /** Returns the search field. */
    public FxSearchField getSearchField() { return searchField; }
    /** Returns the pagination control. */
    public FxPagination getPagination() { return pagination; }
    /** Returns the toolbar row (search + custom nodes). */
    public HBox getToolbar() { return toolbar; }

    /** Sets the filter predicate: {@code (row, query) -> matches}. */
    public void setFilter(BiPredicate<T, String> filter) { this.filter = filter; applyFilter(); }
    /** Returns the filter predicate. */
    public BiPredicate<T, String> getFilter() { return filter; }

    /** Sets the page size. */
    public void setPageSize(int v) { this.pageSize = Math.max(1, v); refreshPage(); }
    /** Returns the page size. */
    public int getPageSize() { return pageSize; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxDataGrid}.
     *
     * @param <T> row type
     * @return a new {@link Builder} instance
     */
    public static <T> Builder<T> builder() { return new Builder<T>(); }

    /**
     * Fluent builder for constructing an {@link FxDataGrid}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     *   <li>columns — empty</li>
     *   <li>pageSize — {@code 20}</li>
     *   <li>filter — none (search field visible but inert)</li>
     * </ul>
     *
     * @param <T> row type
     */
    public static class Builder<T> extends FxNodeBuilder<FxDataGrid<T>, Builder<T>> {

        private List<T> items = Collections.emptyList();
        private List<TableColumn<T, ?>> columns = Collections.emptyList();
        private int pageSize = 20;
        private BiPredicate<T, String> filter;

        public Builder<T> items(List<T> items) { this.items = items == null ? Collections.<T>emptyList() : items; return this; }

        @SafeVarargs
        public final Builder<T> items(T... items) { return items(items == null ? Collections.<T>emptyList() : Arrays.asList(items)); }

        @SafeVarargs
        public final Builder<T> columns(TableColumn<T, ?>... columns) {
            this.columns = columns == null ? Collections.<TableColumn<T, ?>>emptyList() : Arrays.asList(columns);
            return this;
        }

        public Builder<T> pageSize(int pageSize) { this.pageSize = Math.max(1, pageSize); return this; }
        public Builder<T> filter(BiPredicate<T, String> filter) { this.filter = filter; return this; }

        @Override
        public FxDataGrid<T> build() {
            FxDataGrid<T> g = new FxDataGrid<T>();
            g.getTable().getColumns().addAll(columns);
            g.setPageSize(pageSize);
            g.setFilter(filter);
            g.getItems().setAll(items);
            applyBase(g);
            return g;
        }
    }
}
