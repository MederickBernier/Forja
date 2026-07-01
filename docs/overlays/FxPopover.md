# FxPopover

Floating panel anchored to a target `Node`. Wraps a `Popup` sized to arbitrary content and positioned on any `Side` of its anchor. Auto-hides on outside click.

## Usage

```java
FxPopover pop = FxPopover.builder()
    .anchor(button)
    .content(menuBox)
    .side(Side.BOTTOM)
    .build();
pop.show();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `anchor(Node)` | `Node` | none | Anchor node. |
| `content(Node)` | `Node` | none | Popover content. |
| `side(Side)` | `Side` | `BOTTOM` | Side to appear on. |
| `autoHide(boolean)` | `boolean` | `true` | Hide on outside click. |
