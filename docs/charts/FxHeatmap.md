# FxHeatmap

Grid heatmap — rectangular cells colored by value between `lowColor` and `highColor`. Data is a 2D `double[rows][cols]` array; values are clamped to `[min, max]`.

## Usage

```java
FxHeatmap hm = FxHeatmap.builder()
    .data(new double[][]{{1, 2, 3}, {4, 5, 6}})
    .min(0).max(6)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `data(double[][])` | `double[][]` | empty | Row-major cell values. |
| `min(double)` | `double` | `0` | Value min. |
| `max(double)` | `double` | `1` | Value max. |
| `lowColor(Color)` | `Color` | accent-tint | Color at min. |
| `highColor(Color)` | `Color` | accent | Color at max. |
| `cellGap(double)` | `double` | `2` | Gap between cells. |
| `width(double)` | `double` | `240` | Preferred width. |
| `height(double)` | `double` | `160` | Preferred height. |
