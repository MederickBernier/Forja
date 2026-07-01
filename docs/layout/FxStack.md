# FxStack

Vertical flow with spacing. Thin wrapper around `VBox` with a token-based spacing scale.

## Usage

```java
FxStack stack = FxStack.builder()
    .spacing(SpacingSize.MD)
    .children(header, body, footer)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `spacing(SpacingSize)` | `SpacingSize` | `SM` | Gap between children. |
| `alignment(Pos)` | `Pos` | `TOP_LEFT` | Content alignment. |
| `children(Node...)` | `Node[]` | empty | Child nodes. |
