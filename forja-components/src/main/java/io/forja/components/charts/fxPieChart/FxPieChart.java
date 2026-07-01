package io.forja.components.charts.fxPieChart;

import io.forja.builder.FxNodeBuilder;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A styled pie chart built on {@link PieChart}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxPieChart chart = FxPieChart.builder()
 *          .data(new PieChart.Data("Chrome", 60), new PieChart.Data("Safari", 22), new PieChart.Data("Other", 18))
 *          .title("Browser share")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxPieChart extends PieChart {

    /**
     * Creates an empty {@code FxPieChart}.
     */
    public FxPieChart() {
        super();
        getStyleClass().add("forja-pie-chart");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxPieChart}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxPieChart}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>data — empty</li>
     *   <li>title — empty</li>
     *   <li>animated — {@code true}</li>
     *   <li>labelsVisible — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxPieChart, Builder> {

        private List<PieChart.Data> data = Collections.emptyList();
        private String title = "";
        private boolean animated = true;
        private boolean labelsVisible = true;

        public Builder data(PieChart.Data... data) { this.data = data == null ? Collections.<PieChart.Data>emptyList() : Arrays.asList(data); return this; }
        public Builder data(List<PieChart.Data> data) { this.data = data == null ? Collections.<PieChart.Data>emptyList() : data; return this; }
        public Builder title(String title) { this.title = title == null ? "" : title; return this; }
        public Builder animated(boolean animated) { this.animated = animated; return this; }
        public Builder labelsVisible(boolean labelsVisible) { this.labelsVisible = labelsVisible; return this; }

        @Override
        public FxPieChart build() {
            FxPieChart c = new FxPieChart();
            c.setData(FXCollections.observableArrayList(data));
            c.setTitle(title);
            c.setAnimated(animated);
            c.setLabelsVisible(labelsVisible);
            applyBase(c);
            return c;
        }
    }
}
