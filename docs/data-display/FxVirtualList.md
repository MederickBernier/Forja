# FxVirtualList

Fixed-height virtual scroller. Renders only the visible slice of a large `ObservableList<T>` — a spacer keeps the `ScrollPane` sized to total item count while a moving `VBox` paints only the on-screen rows.

## Usage

```java
FxVirtualList<String> list = FxVirtualList.<String>builder()
    .items(oneMillionStrings)
    .itemHeight(28)
    .cellFactory(s -> new FxLabel(s))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` | items | empty | Backing list. |
| `itemHeight(int)` | `int` | `28` | Fixed row height in px. |
| `cellFactory(Function<T, Node>)` | fn | returns `null` | Renders one item. |
