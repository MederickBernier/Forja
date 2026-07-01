# FxSkeleton

Shimmering placeholder shape for loading states. Three shape variants: `RECT`, `TEXT` (rounded pill), `CIRCLE`.

## Usage

```java
FxSkeleton avatar = FxSkeleton.builder()
    .shape(FxSkeleton.Shape.CIRCLE)
    .width(48).height(48)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `shape(Shape)` | `Shape` | `RECT` | `RECT` / `TEXT` / `CIRCLE`. |
| `width(double)` | `double` | inherit | Fixed width. |
| `height(double)` | `double` | inherit | Fixed height. |
| `animating(boolean)` | `boolean` | `true` | Toggle shimmer animation. |
