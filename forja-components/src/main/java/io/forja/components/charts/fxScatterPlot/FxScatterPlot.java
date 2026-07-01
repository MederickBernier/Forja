package io.forja.components.charts.fxScatterPlot;

import io.forja.builder.FxNodeBuilder;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.util.Arrays;

/**
 * A styled scatter plot built on {@link ScatterChart}. Number axes on both.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxScatterPlot chart = FxScatterPlot.builder()
 *          .series(measurements)
 *          .xLabel("X").yLabel("Y")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxScatterPlot extends ScatterChart<Number, Number> {

    /**
     * Creates an empty {@code FxScatterPlot}.
     */
    public FxScatterPlot() {
        super(new NumberAxis(), new NumberAxis());
        getStyleClass().add("forja-scatter-plot");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxScatterPlot}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxScatterPlot}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>series — empty</li>
     *   <li>xLabel / yLabel — empty</li>
     *   <li>animated — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxScatterPlot, Builder> {

        private XYChart.Series<Number, Number>[] series = null;
        private String xLabel = "";
        private String yLabel = "";
        private boolean animated = true;

        @SafeVarargs
        public final Builder series(XYChart.Series<Number, Number>... series) { this.series = series; return this; }
        public Builder xLabel(String xLabel) { this.xLabel = xLabel == null ? "" : xLabel; return this; }
        public Builder yLabel(String yLabel) { this.yLabel = yLabel == null ? "" : yLabel; return this; }
        public Builder animated(boolean animated) { this.animated = animated; return this; }

        @Override
        public FxScatterPlot build() {
            FxScatterPlot c = new FxScatterPlot();
            c.getXAxis().setLabel(xLabel);
            c.getYAxis().setLabel(yLabel);
            c.setAnimated(animated);
            if (series != null) c.getData().addAll(Arrays.asList(series));
            applyBase(c);
            return c;
        }
    }
}
