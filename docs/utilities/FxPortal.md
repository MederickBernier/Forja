# FxPortal

"Render-elsewhere" helper — attaches a target node to the `OverlayHost` overlay layer of a given `Scene` whenever `visible` is `true`, and removes it when `false`.

Useful for lightbox, dropdown-menu, or popup content that logically belongs to a component but should render on top of the whole scene.

## Usage

```java
FxPortal portal = FxPortal.builder()
    .scene(scene)
    .node(mySnackbar)
    .position(Pos.BOTTOM_CENTER)
    .visible(true)
    .build();

portal.setVisible(false); // detaches from overlay
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `scene(Scene)` | `Scene` | `null` | Target scene. |
| `node(Node)` | `Node` | `null` | Node to portal into the overlay. |
| `position(Pos)` | `Pos` | `CENTER` | Alignment on the overlay layer. |
| `visible(boolean)` | `boolean` | `false` | Initial attach state. |
