# FxCarousel

Single-slide-at-a-time carousel — next/prev icon buttons + dot indicators + optional auto-advance.

## Usage

```java
FxCarousel c = FxCarousel.builder()
    .slides(imgA, imgB, imgC)
    .autoAdvance(3000)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `slides(Node...)` | `Node[]` | empty | Slide nodes. |
| `index(int)` | `int` | `0` | Initial slide index. |
| `autoAdvance(long)` | `long` | `0` | Auto-advance interval in ms (`0` = off). |
