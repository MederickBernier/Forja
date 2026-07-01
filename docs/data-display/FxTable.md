# FxTable

Styled `TableView` wrapper. Column construction, sorting, filtering, and selection APIs remain fully accessible.

## Usage

```java
TableColumn<User, String> nameCol = new TableColumn<>("Name");
nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

FxTable<User> users = FxTable.<User>builder()
    .items(userList)
    .columns(nameCol, emailCol)
    .selectionMode(SelectionMode.MULTIPLE)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` / `items(T...)` | items | empty | Row items. |
| `columns(TableColumn<T, ?>...)` | columns | empty | Table columns. |
| `selectionMode(SelectionMode)` | `SelectionMode` | `SINGLE` | Selection mode. |
| `editable(boolean)` | `boolean` | `false` | Editable flag. |
