# FxLineChart

Styled line chart built on `javafx.scene.chart.LineChart`. Category X axis + number Y axis.

## Usage

```java
XYChart.Series<String, Number> revenue = new XYChart.Series<>();
revenue.setName("Revenue");
revenue.getData().add(new XYChart.Data<>("Jan", 1000));

FxLineChart chart = FxLineChart.builder()
    .series(revenue)
    .xLabel("Month").yLabel("USD")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `series(XYChart.Series<String, Number>...)` | series | empty | Data series. |
| `xLabel(String)` | `String` | `""` | X-axis label. |
| `yLabel(String)` | `String` | `""` | Y-axis label. |
| `createSymbols(boolean)` | `boolean` | `true` | Show point symbols. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |
