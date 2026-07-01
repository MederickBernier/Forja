# FxHoverCard

Hover-triggered rich card. Wraps an `FxPopover` whose show/hide is driven by the target node's `hoverProperty` with configurable open/close delays.

## Usage

```java
FxHoverCard hc = FxHoverCard.builder()
    .target(avatar)
    .content(profileCard)
    .openDelayMs(400)
    .closeDelayMs(200)
    .build();
// autoInstall=true wires listeners immediately; pass autoInstall(false) + call hc.install() later.
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `target(Node)` | `Node` | none | Node whose hover drives the card. |
| `content(Node)` | `Node` | none | Card content. |
| `side(Side)` | `Side` | `BOTTOM` | Side to appear on. |
| `openDelayMs(long)` | `long` | `400` | Delay before showing. |
| `closeDelayMs(long)` | `long` | `200` | Delay before hiding. |
| `autoInstall(boolean)` | `boolean` | `true` | Install hover listeners at build time. |
