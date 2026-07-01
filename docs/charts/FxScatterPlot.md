# FxScatterPlot

Styled scatter plot built on `javafx.scene.chart.ScatterChart`. Number axes on both dimensions.

## Usage

```java
FxScatterPlot chart = FxScatterPlot.builder()
    .series(measurements)
    .xLabel("X").yLabel("Y")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `series(XYChart.Series<Number, Number>...)` | series | empty | Data series. |
| `xLabel(String)` | `String` | `""` | X-axis label. |
| `yLabel(String)` | `String` | `""` | Y-axis label. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |
