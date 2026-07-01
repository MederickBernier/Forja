# FxFlex

Wrap-flow layout — a styled `FlowPane`. Children flow row-by-row (or column-by-column) and wrap to a new line when the axis fills.

## Usage

```java
FxFlex tags = FxFlex.builder()
    .hgap(6).vgap(6)
    .children(tag1, tag2, tag3, tag4)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `orientation(Orientation)` | `Orientation` | `HORIZONTAL` | Flow direction. |
| `hgap(double)` | `double` | `0` | Horizontal gap. |
| `vgap(double)` | `double` | `0` | Vertical gap. |
| `gap(double)` | `double` | `0` | Sets both `hgap` and `vgap`. |
| `alignment(Pos)` | `Pos` | `TOP_LEFT` | Content alignment. |
| `children(Node...)` | `Node[]` | empty | Child nodes. |
