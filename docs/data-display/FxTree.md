# FxTree

Styled `TreeView` wrapper.

## Usage

```java
TreeItem<String> root = new TreeItem<>("Files");
root.getChildren().add(new TreeItem<>("README.md"));

FxTree<String> tree = FxTree.<String>builder()
    .root(root)
    .showRoot(true)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `root(TreeItem<T>)` | `TreeItem<T>` | `null` | Root item. |
| `showRoot(boolean)` | `boolean` | `true` | Render the root. |
| `selectionMode(SelectionMode)` | `SelectionMode` | `SINGLE` | Selection mode. |
| `editable(boolean)` | `boolean` | `false` | Editable flag. |
