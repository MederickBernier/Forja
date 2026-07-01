# FxResizablePane

Container whose single child can be resized along one edge by dragging a user-visible handle. Useful for sidebars and drawer-style panels that aren't full overlays.

## Usage

```java
FxResizablePane sidebar = FxResizablePane.builder()
    .child(sidebarContent)
    .side(Side.RIGHT)
    .extent(240).minExtent(120).maxExtent(480)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `child(Node)` | `Node` | none | Single child. |
| `side(Side)` | `Side` | `RIGHT` | Which edge hosts the drag handle. |
| `extent(double)` | `double` | `240` | Child size along the drag axis. |
| `minExtent(double)` | `double` | `80` | Minimum extent. |
| `maxExtent(double)` | `double` | `MAX_VALUE` | Maximum extent. |
