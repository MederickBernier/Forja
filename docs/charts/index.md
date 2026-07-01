# Charts

Data visualization. First six wrap `javafx.scene.chart.*`; the last four are Canvas-drawn.

| Component | Summary |
|---|---|
| [FxLineChart](#fxlinechart) | Category X + number Y line chart wrapper. |
| [FxBarChart](#fxbarchart) | Category X + number Y bar chart wrapper. |
| [FxAreaChart](#fxareachart) | Category X + number Y area chart wrapper. |
| [FxScatterPlot](#fxscatterplot) | Number X + number Y scatter plot wrapper. |
| [FxPieChart](#fxpiechart) | Styled `PieChart` wrapper. |
| [FxDonutChart](#fxdonutchart) | Donut variant of `FxPieChart`. |
| [FxSparkline](#fxsparkline) | Compact inline polyline chart. |
| [FxGauge](#fxgauge) | Semicircle arc gauge with value label. |
| [FxHeatmap](#fxheatmap) | Color-lerped grid heatmap for 2D data. |
| [FxRadarChart](#fxradarchart) | Polar polygon-per-series radar chart. |

## FxLineChart

Styled line chart built on `javafx.scene.chart.LineChart`. Category X axis + number Y axis.

### Usage

```java
XYChart.Series<String, Number> revenue = new XYChart.Series<>();
revenue.setName("Revenue");
revenue.getData().add(new XYChart.Data<>("Jan", 1000));

FxLineChart chart = FxLineChart.builder()
    .series(revenue)
    .xLabel("Month").yLabel("USD")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `series(XYChart.Series<String, Number>...)` | series | empty | Data series. |
| `xLabel(String)` | `String` | `""` | X-axis label. |
| `yLabel(String)` | `String` | `""` | Y-axis label. |
| `createSymbols(boolean)` | `boolean` | `true` | Show point symbols. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |

## FxBarChart

Styled bar chart built on `javafx.scene.chart.BarChart`. Category X + number Y.

### Usage

```java
FxBarChart chart = FxBarChart.builder()
    .series(revenue, expenses)
    .xLabel("Month").yLabel("USD")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `series(XYChart.Series<String, Number>...)` | series | empty | Data series. |
| `xLabel(String)` | `String` | `""` | X-axis label. |
| `yLabel(String)` | `String` | `""` | Y-axis label. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |

## FxAreaChart

Styled area chart built on `javafx.scene.chart.AreaChart`. Category X + number Y.

### Usage

```java
FxAreaChart chart = FxAreaChart.builder()
    .series(usage)
    .xLabel("Time").yLabel("MB")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `series(XYChart.Series<String, Number>...)` | series | empty | Data series. |
| `xLabel(String)` | `String` | `""` | X-axis label. |
| `yLabel(String)` | `String` | `""` | Y-axis label. |
| `createSymbols(boolean)` | `boolean` | `false` | Show point symbols. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |

## FxScatterPlot

Styled scatter plot built on `javafx.scene.chart.ScatterChart`. Number axes on both dimensions.

### Usage

```java
FxScatterPlot chart = FxScatterPlot.builder()
    .series(measurements)
    .xLabel("X").yLabel("Y")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `series(XYChart.Series<Number, Number>...)` | series | empty | Data series. |
| `xLabel(String)` | `String` | `""` | X-axis label. |
| `yLabel(String)` | `String` | `""` | Y-axis label. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |

## FxPieChart

Styled pie chart built on `javafx.scene.chart.PieChart`.

### Usage

```java
FxPieChart chart = FxPieChart.builder()
    .data(new PieChart.Data("Chrome", 60),
          new PieChart.Data("Safari", 22),
          new PieChart.Data("Other", 18))
    .title("Browser share")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `data(PieChart.Data...)` / `data(List<PieChart.Data>)` | data | empty | Slices. |
| `title(String)` | `String` | `""` | Chart title. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |
| `labelsVisible(boolean)` | `boolean` | `true` | Show slice labels. |

## FxDonutChart

Donut variant of `FxPieChart`. Same data model, adds the `forja-donut-chart` style class so CSS can clip a center circle.

### Usage

```java
FxDonutChart chart = FxDonutChart.builder()
    .data(new PieChart.Data("A", 40), new PieChart.Data("B", 60))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `data(PieChart.Data...)` | data | empty | Slices. |
| `title(String)` | `String` | `""` | Chart title. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |
| `labelsVisible(boolean)` | `boolean` | `true` | Show slice labels. |

## FxSparkline

Compact inline polyline chart drawn on a `Canvas`. Sized to fit its parent. Ideal for tables/lists where a numeric trend needs to appear inline.

### Usage

```java
FxSparkline s = FxSparkline.builder()
    .data(1, 3, 2, 6, 5, 8, 7)
    .stroke(Color.web("#4F46E5"))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `data(Number...)` | `Number[]` | empty | Data points. |
| `stroke(Color)` | `Color` | indigo | Line color. |
| `strokeWidth(double)` | `double` | `1.5` | Line width. |
| `width(double)` | `double` | `120` | Preferred width. |
| `height(double)` | `double` | `32` | Preferred height. |

## FxGauge

Speedometer-style gauge — semicircular arc + numeric value label. Value maps to angle `[180°, 0°]` across the arc.

### Usage

```java
FxGauge cpu = FxGauge.builder()
    .min(0).max(100).value(42)
    .arcColor(Color.web("#4F46E5"))
    .suffix("%")
    .build();
```

### Builder methods

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

## FxHeatmap

Grid heatmap — rectangular cells colored by value between `lowColor` and `highColor`. Data is a 2D `double[rows][cols]` array; values are clamped to `[min, max]`.

### Usage

```java
FxHeatmap hm = FxHeatmap.builder()
    .data(new double[][]{{1, 2, 3}, {4, 5, 6}})
    .min(0).max(6)
    .build();
```

### Builder methods

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

## FxRadarChart

Radar (spider) chart — polygon-per-series over polar axes. Categories are laid out equally around the circle; each series is a `double[]` of length `categories.size()`.

### Usage

```java
FxRadarChart r = FxRadarChart.builder()
    .categories("Speed", "Power", "Range", "Comfort", "Cost")
    .series(new double[]{8, 6, 5, 7, 9}, Color.web("#4F46E5"))
    .max(10)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `categories(String...)` / `categories(List<String>)` | items | empty | Axis categories. |
| `series(double[], Color)` | values + color | — | Add a series (call multiple times). |
| `max(double)` | `double` | `10` | Max value for radius normalization. |
| `width(double)` | `double` | `240` | Preferred width. |
| `height(double)` | `double` | `240` | Preferred height. |
