# FxDrawer

Side-anchored sliding panel rendered on `OverlayHost`. Opening slides the panel in via a translate transition; closing slides it back out. Scrim absorbs clicks; ESC closes.

## Usage

```java
FxDrawer settings = FxDrawer.builder()
    .content(settingsPanel)
    .side(Side.RIGHT)
    .size(320)
    .build();
settings.open(scene);
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `side(Side)` | `Side` | `RIGHT` | Anchor side. |
| `size(double)` | `double` | `320` | Panel size along the drawer's axis. |
| `content(Node)` | `Node` | none | Panel content. |
