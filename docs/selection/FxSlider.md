# FxSlider

Numeric range slider with optional value label + prefix/suffix. Value label auto-formats with the configured `decimals`.

## Usage

```java
FxSlider volume = FxSlider.builder()
    .min(0).max(100)
    .value(60).step(5)
    .suffix("%")
    .showValue(true)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `min(double)` | `double` | `0` | Minimum value. |
| `max(double)` | `double` | `100` | Maximum value. |
| `value(double)` | `double` | `0` | Initial value. |
| `step(double)` | `double` | `1` | Block increment. |
| `decimals(int)` | `int` | `0` | Fractional digits for value label. |
| `prefix(String)` | `String` | `""` | Value-label prefix. |
| `suffix(String)` | `String` | `""` | Value-label suffix. |
| `showValue(boolean)` | `boolean` | `false` | Show trailing value label. |
| `showTickMarks(boolean)` | `boolean` | `false` | Show tick marks. |
| `showTickLabels(boolean)` | `boolean` | `false` | Show tick labels. |
| `majorTickUnit(double)` | `double` | `25` | Major tick unit. |
