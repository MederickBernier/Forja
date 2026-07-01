# FxTreeTable

Styled `TreeTableView` wrapper.

## Usage

```java
FxTreeTable<File> tt = FxTreeTable.<File>builder()
    .root(rootItem)
    .columns(nameCol, sizeCol)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `root(TreeItem<T>)` | `TreeItem<T>` | `null` | Root item. |
| `showRoot(boolean)` | `boolean` | `true` | Render the root. |
| `columns(TreeTableColumn<T, ?>...)` | columns | empty | Table columns. |
| `selectionMode(SelectionMode)` | `SelectionMode` | `SINGLE` | Selection mode. |
| `editable(boolean)` | `boolean` | `false` | Editable flag. |
