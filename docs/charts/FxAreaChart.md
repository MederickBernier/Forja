# FxAreaChart

Styled area chart built on `javafx.scene.chart.AreaChart`. Category X + number Y.

## Usage

```java
FxAreaChart chart = FxAreaChart.builder()
    .series(usage)
    .xLabel("Time").yLabel("MB")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `series(XYChart.Series<String, Number>...)` | series | empty | Data series. |
| `xLabel(String)` | `String` | `""` | X-axis label. |
| `yLabel(String)` | `String` | `""` | Y-axis label. |
| `createSymbols(boolean)` | `boolean` | `false` | Show point symbols. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |
