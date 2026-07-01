package io.forja.components;

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
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseLSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void lineChart_holdsSeries() {
        XYChart.Series<String, Number> s = new XYChart.Series<>();
        s.getData().add(new XYChart.Data<>("A", 1));
        FxLineChart c = onFx(() -> FxLineChart.builder().series(s).xLabel("X").yLabel("Y").build());
        assertEquals(1, c.getData().size());
        assertEquals("X", c.getXAxis().getLabel());
    }

    @Test
    void barChart_holdsSeries() {
        XYChart.Series<String, Number> s = new XYChart.Series<>();
        s.getData().add(new XYChart.Data<>("A", 1));
        FxBarChart c = onFx(() -> FxBarChart.builder().series(s).build());
        assertEquals(1, c.getData().size());
    }

    @Test
    void areaChart_createSymbolsDefaultsFalse() {
        FxAreaChart c = onFx(() -> FxAreaChart.builder().build());
        assertTrue(!c.getCreateSymbols());
    }

    @Test
    void scatterPlot_holdsSeries() {
        XYChart.Series<Number, Number> s = new XYChart.Series<>();
        s.getData().add(new XYChart.Data<>(1, 2));
        FxScatterPlot c = onFx(() -> FxScatterPlot.builder().series(s).build());
        assertEquals(1, c.getData().size());
    }

    @Test
    void pieChart_holdsData() {
        FxPieChart c = onFx(() -> FxPieChart.builder()
                .data(new PieChart.Data("A", 30), new PieChart.Data("B", 70))
                .title("Share")
                .build());
        assertEquals(2, c.getData().size());
        assertEquals("Share", c.getTitle());
    }

    @Test
    void donutChart_hasDonutClass() {
        FxDonutChart c = onFx(() -> FxDonutChart.builder()
                .data(new PieChart.Data("A", 50), new PieChart.Data("B", 50))
                .build());
        assertTrue(c.getStyleClass().contains("forja-donut-chart"));
        assertEquals(2, c.getData().size());
    }

    @Test
    void sparkline_holdsData() {
        FxSparkline s = onFx(() -> FxSparkline.builder().data(1, 2, 3, 4, 5).build());
        assertEquals(5, s.getData().size());
    }

    @Test
    void gauge_setsValueRange() {
        FxGauge g = onFx(() -> FxGauge.builder().min(0).max(100).value(60).suffix("%").build());
        assertEquals(60.0, g.getValue());
        assertEquals("%", g.getSuffix());
    }

    @Test
    void heatmap_setsData() {
        double[][] data = { {1, 2, 3}, {4, 5, 6} };
        FxHeatmap hm = onFx(() -> FxHeatmap.builder().data(data).min(0).max(6).build());
        assertEquals(2, hm.getData().length);
        assertEquals(6.0, hm.getMax());
    }

    @Test
    void radarChart_registersSeries() {
        FxRadarChart r = onFx(() -> FxRadarChart.builder()
                .categories("A", "B", "C", "D")
                .series(new double[]{5, 4, 3, 2}, Color.RED)
                .max(10)
                .build());
        assertEquals(4, r.getCategories().size());
        assertEquals(10.0, r.getMax());
    }
}
