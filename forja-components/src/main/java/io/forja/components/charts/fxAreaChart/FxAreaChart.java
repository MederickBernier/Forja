package io.forja.components.charts.fxAreaChart;

import io.forja.builder.FxNodeBuilder;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Arrays;

/**
 * A styled area chart built on {@link AreaChart}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxAreaChart chart = FxAreaChart.builder()
 *          .series(usage)
 *          .xLabel("Time").yLabel("MB")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxAreaChart extends AreaChart<String, Number> {

    /**
     * Creates an empty {@code FxAreaChart}.
     */
    public FxAreaChart() {
        super(new CategoryAxis(), new NumberAxis());
        getStyleClass().add("forja-area-chart");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxAreaChart}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxAreaChart}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>series — empty</li>
     *   <li>xLabel / yLabel — empty</li>
     *   <li>createSymbols — {@code false}</li>
     *   <li>animated — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxAreaChart, Builder> {

        private XYChart.Series<String, Number>[] series = null;
        private String xLabel = "";
        private String yLabel = "";
        private boolean createSymbols = false;
        private boolean animated = true;

        @SafeVarargs
        public final Builder series(XYChart.Series<String, Number>... series) { this.series = series; return this; }
        public Builder xLabel(String xLabel) { this.xLabel = xLabel == null ? "" : xLabel; return this; }
        public Builder yLabel(String yLabel) { this.yLabel = yLabel == null ? "" : yLabel; return this; }
        public Builder createSymbols(boolean createSymbols) { this.createSymbols = createSymbols; return this; }
        public Builder animated(boolean animated) { this.animated = animated; return this; }

        @Override
        public FxAreaChart build() {
            FxAreaChart c = new FxAreaChart();
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
