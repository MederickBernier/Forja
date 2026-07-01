# FxRangeSlider

Two-thumb range slider. Implemented as two overlaid `Slider`s; the low thumb is clamped to `≤ highValue` and the high thumb to `≥ lowValue`.

## Usage

```java
FxRangeSlider price = FxRangeSlider.builder()
    .min(0).max(1000)
    .lowValue(100).highValue(500)
    .step(10)
    .prefix("$")
    .showValues(true)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `min(double)` | `double` | `0` | Minimum. |
| `max(double)` | `double` | `100` | Maximum. |
| `lowValue(double)` | `double` | `0` | Low thumb value. |
| `highValue(double)` | `double` | `100` | High thumb value. |
| `step(double)` | `double` | `1` | Block increment. |
| `showValues(boolean)` | `boolean` | `false` | Show trailing "low – high" label. |
| `prefix(String)` | `String` | `""` | Value-label prefix. |
| `suffix(String)` | `String` | `""` | Value-label suffix. |
