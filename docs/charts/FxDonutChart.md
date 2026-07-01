# FxDonutChart

Donut variant of `FxPieChart`. Same data model, adds the `forja-donut-chart` style class so CSS can clip a center circle.

## Usage

```java
FxDonutChart chart = FxDonutChart.builder()
    .data(new PieChart.Data("A", 40), new PieChart.Data("B", 60))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `data(PieChart.Data...)` | data | empty | Slices. |
| `title(String)` | `String` | `""` | Chart title. |
| `animated(boolean)` | `boolean` | `true` | Enable animations. |
| `labelsVisible(boolean)` | `boolean` | `true` | Show slice labels. |
