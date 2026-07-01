# FxRadarChart

Radar (spider) chart — polygon-per-series over polar axes. Categories are laid out equally around the circle; each series is a `double[]` of length `categories.size()`.

## Usage

```java
FxRadarChart r = FxRadarChart.builder()
    .categories("Speed", "Power", "Range", "Comfort", "Cost")
    .series(new double[]{8, 6, 5, 7, 9}, Color.web("#4F46E5"))
    .max(10)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `categories(String...)` / `categories(List<String>)` | items | empty | Axis categories. |
| `series(double[], Color)` | values + color | — | Add a series (call multiple times). |
| `max(double)` | `double` | `10` | Max value for radius normalization. |
| `width(double)` | `double` | `240` | Preferred width. |
| `height(double)` | `double` | `240` | Preferred height. |
