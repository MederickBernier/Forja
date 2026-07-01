# FxBarChart

Styled bar chart built on `javafx.scene.chart.BarChart`. Category X + number Y.

## Usage

```java
FxBarChart chart = FxBarChart.builder()
    .series(revenue, expenses)
    .xLabel("Month").yLabel("USD")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `series(XYChart.Series<String, Number>...)` | series | empty | Data series. |
| `xLabel(String)` | `String` | `""` | X-axis label. |
| `yLabel(String)` | `String` | `""` | Y-axis label. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |
