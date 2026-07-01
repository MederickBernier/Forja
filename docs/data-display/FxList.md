# FxList

Styled `ListView` wrapper. All native cell-factory, selection, and binding APIs remain accessible.

## Usage

```java
FxList<String> tags = FxList.<String>builder()
    .items("bug", "enhancement", "docs")
    .selectionMode(SelectionMode.MULTIPLE)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` / `items(T...)` | items | empty | Cell items. |
| `selectionMode(SelectionMode)` | `SelectionMode` | `SINGLE` | Selection mode. |
| `editable(boolean)` | `boolean` | `false` | Editable flag. |
