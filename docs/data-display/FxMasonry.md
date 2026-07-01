# FxMasonry

Masonry layout — packs children into equal-width columns using a greedy "shortest column" algorithm. Each child's height is respected; columns grow independently.

## Usage

```java
FxMasonry m = FxMasonry.builder()
    .columns(3)
    .gap(8)
    .children(card1, card2, card3, card4, card5)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `columns(int)` | `int` | `3` | Column count. |
| `gap(double)` | `double` | `8` | Gap in px. |
| `children(Node...)` | `Node[]` | empty | Children to pack. |
