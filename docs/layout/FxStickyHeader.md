# FxStickyHeader

Pins its first child (the header) to the top of a host `ScrollPane`'s viewport while the rest of the content scrolls underneath.

## Usage

```java
FxStickyHeader sticky = FxStickyHeader.builder()
    .header(sectionTitle)
    .body(longContent)
    .build();
scrollPane.setContent(sticky);
sticky.attachTo(scrollPane);
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `header(Node)` | `Node` | none | Pinned header. |
| `body(Node)` | `Node` | none | Scrolling body. |
| `attachTo(ScrollPane)` | `ScrollPane` | none | Wires the header's translateY to the scroll pane's vvalue. |
