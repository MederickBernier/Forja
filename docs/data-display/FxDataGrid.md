# FxDataGrid

Sortable, filterable, paginated table. Composes an `FxTable` with an `FxSearchField` filter and `FxPagination`. Filtering uses a caller-provided `BiPredicate<T, String>`.

## Usage

```java
FxDataGrid<User> grid = FxDataGrid.<User>builder()
    .items(users)
    .columns(nameCol, emailCol)
    .pageSize(20)
    .filter((row, q) -> row.name().toLowerCase().contains(q.toLowerCase()))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` / `items(T...)` | items | empty | Source rows. |
| `columns(TableColumn<T, ?>...)` | columns | empty | Table columns. |
| `pageSize(int)` | `int` | `20` | Rows per page. |
| `filter(BiPredicate<T, String>)` | fn | none | Filter predicate `(row, query) -> matches`. |
