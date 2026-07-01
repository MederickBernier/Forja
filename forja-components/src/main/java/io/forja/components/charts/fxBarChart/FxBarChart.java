package io.forja.components.charts.fxBarChart;

import io.forja.builder.FxNodeBuilder;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Arrays;

/**
 * A styled bar chart built on {@link BarChart}. Category X axis + number Y
 * axis.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxBarChart chart = FxBarChart.builder()
 *          .series(revenue, expenses)
 *          .xLabel("Month").yLabel("USD")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxBarChart extends BarChart<String, Number> {

    /**
     * Creates an empty {@code FxBarChart}.
     */
    public FxBarChart() {
        super(new CategoryAxis(), new NumberAxis());
        getStyleClass().add("forja-bar-chart");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxBarChart}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxBarChart}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>series — empty</li>
     *   <li>xLabel / yLabel — empty</li>
     *   <li>animated — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxBarChart, Builder> {

        private XYChart.Series<String, Number>[] series = null;
        private String xLabel = "";
        private String yLabel = "";
        private boolean animated = true;

        @SafeVarargs
        public final Builder series(XYChart.Series<String, Number>... series) { this.series = series; return this; }
        public Builder xLabel(String xLabel) { this.xLabel = xLabel == null ? "" : xLabel; return this; }
        public Builder yLabel(String yLabel) { this.yLabel = yLabel == null ? "" : yLabel; return this; }
        public Builder animated(boolean animated) { this.animated = animated; return this; }

        @Override
        public FxBarChart build() {
            FxBarChart c = new FxBarChart();
            c.getXAxis().setLabel(xLabel);
            c.getYAxis().setLabel(yLabel);
            c.setAnimated(animated);
            if (series != null) c.getData().addAll(Arrays.asList(series));
            applyBase(c);
            return c;
        }
    }
}
