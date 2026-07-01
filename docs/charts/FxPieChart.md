# FxPieChart

Styled pie chart built on `javafx.scene.chart.PieChart`.

## Usage

```java
FxPieChart chart = FxPieChart.builder()
    .data(new PieChart.Data("Chrome", 60),
          new PieChart.Data("Safari", 22),
          new PieChart.Data("Other", 18))
    .title("Browser share")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `data(PieChart.Data...)` / `data(List<PieChart.Data>)` | data | empty | Slices. |
| `title(String)` | `String` | `""` | Chart title. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |
| `labelsVisible(boolean)` | `boolean` | `true` | Show slice labels. |
