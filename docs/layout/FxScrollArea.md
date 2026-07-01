# FxScrollArea

Styled `ScrollPane` wrapper. Content, viewport, and scrollbar APIs remain fully accessible.

## Usage

```java
FxScrollArea sa = FxScrollArea.builder()
    .content(longVBox)
    .fitToWidth(true)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `content(Node)` | `Node` | none | Scrolled content. |
| `fitToWidth(boolean)` | `boolean` | `true` | Match content width to viewport. |
| `fitToHeight(boolean)` | `boolean` | `false` | Match content height to viewport. |
| `hbarPolicy(ScrollBarPolicy)` | `ScrollBarPolicy` | `AS_NEEDED` | Horizontal scroll bar policy. |
| `vbarPolicy(ScrollBarPolicy)` | `ScrollBarPolicy` | `AS_NEEDED` | Vertical scroll bar policy. |
