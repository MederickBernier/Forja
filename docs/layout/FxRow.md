# FxRow

Horizontal flow with spacing. Thin wrapper around `HBox` with a token-based spacing scale.

## Usage

```java
FxRow row = FxRow.builder()
    .spacing(SpacingSize.SM)
    .children(icon, label, spacer, action)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `spacing(SpacingSize)` | `SpacingSize` | `SM` | Gap between children. |
| `alignment(Pos)` | `Pos` | `CENTER_LEFT` | Content alignment. |
| `children(Node...)` | `Node[]` | empty | Child nodes. |
