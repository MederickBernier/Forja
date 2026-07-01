# FxGauge

Speedometer-style gauge — semicircular arc + numeric value label. Value maps to angle `[180°, 0°]` across the arc.

## Usage

```java
FxGauge cpu = FxGauge.builder()
    .min(0).max(100).value(42)
    .arcColor(Color.web("#4F46E5"))
    .suffix("%")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `min(double)` | `double` | `0` | Minimum. |
| `max(double)` | `double` | `100` | Maximum. |
| `value(double)` | `double` | `0` | Current value. |
| `arcColor(Color)` | `Color` | indigo | Filled arc color. |
| `trackColor(Color)` | `Color` | zinc-200 | Background arc color. |
| `suffix(String)` | `String` | `""` | Value-label suffix. |
| `width(double)` | `double` | `160` | Preferred width. |
| `height(double)` | `double` | `100` | Preferred height. |
