# FxGrid

Fluent-builder `GridPane` wrapper. Add children with explicit `(col, row)` coordinates plus optional span.

## Usage

```java
FxGrid form = FxGrid.builder()
    .hgap(12).vgap(8)
    .add(nameLabel, 0, 0).add(nameField, 1, 0)
    .add(emailLabel, 0, 1).add(emailField, 1, 1)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `hgap(double)` | `double` | `0` | Horizontal gap. |
| `vgap(double)` | `double` | `0` | Vertical gap. |
| `alignment(Pos)` | `Pos` | `TOP_LEFT` | Content alignment. |
| `add(Node, int, int)` | node + col + row | — | Add child at (col, row) with span 1x1. |
| `add(Node, int, int, int, int)` | node + col + row + colSpan + rowSpan | — | Add child with span. |
