package io.forja.components.charts.fxLineChart;

import io.forja.builder.FxNodeBuilder;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Arrays;

/**
 * A styled line chart built on {@link LineChart}. Category X axis + number
 * Y axis. Wrapper for the common case; use the JavaFX API directly for
 * numeric X.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      XYChart.Series<String, Number> revenue = new XYChart.Series<>();
 *      revenue.setName("Revenue");
 *      revenue.getData().add(new XYChart.Data<>("Jan", 1000));
 *      FxLineChart chart = FxLineChart.builder()
 *          .series(revenue)
 *          .xLabel("Month").yLabel("USD")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxLineChart extends LineChart<String, Number> {

    /**
     * Creates an empty {@code FxLineChart} with category X + number Y.
     */
    public FxLineChart() {
        super(new CategoryAxis(), new NumberAxis());
        getStyleClass().add("forja-line-chart");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxLineChart}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxLineChart}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>series — empty</li>
     *   <li>xLabel / yLabel — empty</li>
     *   <li>createSymbols — {@code true}</li>
     *   <li>animated — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxLineChart, Builder> {

        private XYChart.Series<String, Number>[] series = null;
        private String xLabel = "";
        private String yLabel = "";
        private boolean createSymbols = true;
        private boolean animated = true;

        @SafeVarargs
        public final Builder series(XYChart.Series<String, Number>... series) { this.series = series; return this; }
        public Builder xLabel(String xLabel) { this.xLabel = xLabel == null ? "" : xLabel; return this; }
        public Builder yLabel(String yLabel) { this.yLabel = yLabel == null ? "" : yLabel; return this; }
        public Builder createSymbols(boolean createSymbols) { this.createSymbols = createSymbols; return this; }
        public Builder animated(boolean animated) { this.animated = animated; return this; }

        @Override
        public FxLineChart build() {
            FxLineChart c = new FxLineChart();
            c.getXAxis().setLabel(xLabel);
            c.getYAxis().setLabel(yLabel);
            c.setCreateSymbols(createSymbols);
            c.setAnimated(animated);
            if (series != null) c.getData().addAll(Arrays.asList(series));
            applyBase(c);
            return c;
        }
    }
}
