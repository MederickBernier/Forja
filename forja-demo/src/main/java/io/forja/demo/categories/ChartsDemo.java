package io.forja.demo.categories;

import io.forja.components.charts.fxAreaChart.FxAreaChart;
import io.forja.components.charts.fxBarChart.FxBarChart;
import io.forja.components.charts.fxDonutChart.FxDonutChart;
import io.forja.components.charts.fxGauge.FxGauge;
import io.forja.components.charts.fxHeatmap.FxHeatmap;
import io.forja.components.charts.fxLineChart.FxLineChart;
import io.forja.components.charts.fxPieChart.FxPieChart;
import io.forja.components.charts.fxRadarChart.FxRadarChart;
import io.forja.components.charts.fxScatterPlot.FxScatterPlot;
import io.forja.components.charts.fxSparkline.FxSparkline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

public class ChartsDemo implements CategoryDemo {

    @Override public String key() { return "charts"; }
    @Override public String title() { return "Charts"; }
    @Override public String icon() { return "fth-bar-chart-2"; }

    @Override
    public Node build(Scene scene) {
        String[] months = { "Jan", "Feb", "Mar", "Apr", "May" };

        FxLineChart line = FxLineChart.builder()
                .series(categorySeries("Revenue", months, new Number[] { 120, 145, 130, 178, 210 }))
                .xLabel("Month").yLabel("k$").build();
        line.setPrefSize(360, 240);

        FxAreaChart area = FxAreaChart.builder()
                .series(categorySeries("Users", months, new Number[] { 40, 62, 55, 90, 120 }))
                .xLabel("Month").yLabel("Count").build();
        area.setPrefSize(360, 240);

        FxBarChart bar = FxBarChart.builder()
                .series(categorySeries("Sales", months, new Number[] { 30, 45, 28, 60, 52 }))
                .xLabel("Month").yLabel("Units").build();
        bar.setPrefSize(360, 240);

        XYChart.Series<Number, Number> points = new XYChart.Series<Number, Number>();
        points.setName("Samples");
        double[][] pts = { { 1, 2 }, { 2, 4.5 }, { 3, 3 }, { 4, 6 }, { 5, 5.5 }, { 6, 8 } };
        for (int i = 0; i < pts.length; i++) {
            points.getData().add(new XYChart.Data<Number, Number>(pts[i][0], pts[i][1]));
        }
        FxScatterPlot scatter = FxScatterPlot.builder().series(points)
                .xLabel("X").yLabel("Y").build();
        scatter.setPrefSize(360, 240);

        FxPieChart pie = FxPieChart.builder()
                .data(new PieChart.Data("Direct", 45), new PieChart.Data("Referral", 25),
                        new PieChart.Data("Social", 20), new PieChart.Data("Other", 10))
                .title("Traffic sources").build();
        pie.setPrefSize(360, 240);

        FxDonutChart donut = FxDonutChart.builder()
                .data(new PieChart.Data("Chrome", 60), new PieChart.Data("Firefox", 20),
                        new PieChart.Data("Safari", 15), new PieChart.Data("Edge", 5))
                .title("Browsers").build();
        donut.setPrefSize(360, 240);

        FxGauge gauge = FxGauge.builder().min(0).max(100).value(72).suffix("%")
                .width(360).height(240).build();

        FxSparkline sparkline = FxSparkline.builder()
                .data(3, 5, 4, 8, 6, 9, 7, 11, 10)
                .stroke(Color.web("#4F46E5")).strokeWidth(2)
                .width(360).height(80).build();

        double[][] heat = {
                { 0.1, 0.4, 0.8, 0.3 },
                { 0.6, 0.9, 0.2, 0.5 },
                { 0.3, 0.7, 0.6, 1.0 }
        };
        FxHeatmap heatmap = FxHeatmap.builder().data(heat).min(0).max(1)
                .width(360).height(240).build();

        FxRadarChart radar = FxRadarChart.builder()
                .categories("Speed", "Power", "Range", "Cost", "Weight")
                .series(new double[] { 80, 60, 90, 50, 70 }, Color.web("#4F46E5"))
                .max(100).width(360).height(240).build();

        return Demo.category(title(),
                Demo.block("FxLineChart", "Category line chart from XYChart series.", line),
                Demo.block("FxAreaChart", "Filled area chart over a category axis.", area),
                Demo.block("FxBarChart", "Vertical bar chart over a category axis.", bar),
                Demo.block("FxScatterPlot", "Numeric scatter plot of XY data points.", scatter),
                Demo.block("FxPieChart", "Pie chart built from PieChart.Data slices.", pie),
                Demo.block("FxDonutChart", "Donut variant of the pie chart.", donut),
                Demo.block("FxGauge", "Radial gauge showing a single value in a range.", gauge),
                Demo.block("FxSparkline", "Compact inline trend line from a number series.", sparkline),
                Demo.block("FxHeatmap", "Grid heatmap coloring a 2D value matrix.", heatmap),
                Demo.block("FxRadarChart", "Radar/spider chart over labeled categories.", radar));
    }

    private static XYChart.Series<String, Number> categorySeries(String name, String[] labels, Number[] values) {
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName(name);
        for (int i = 0; i < labels.length; i++) {
            series.getData().add(new XYChart.Data<String, Number>(labels[i], values[i]));
        }
        return series;
    }
}
