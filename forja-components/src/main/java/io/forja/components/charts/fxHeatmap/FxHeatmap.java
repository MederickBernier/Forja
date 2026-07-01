package io.forja.components.charts.fxHeatmap;

import io.forja.builder.FxNodeBuilder;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * A grid heatmap — rectangular cells colored by value between {@link #getLowColor}
 * and {@link #getHighColor}.
 *
 * <p>Data is a 2D array {@code double[rows][cols]}; missing rows are treated
 * as empty. Values below {@link #getMin} / above {@link #getMax} are
 * clamped.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxHeatmap hm = FxHeatmap.builder()
 *          .data(new double[][]{{1, 2, 3},{4, 5, 6}})
 *          .min(0).max(6)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxHeatmap extends Region {

    private double[][] data = new double[0][0];
    private double min = 0;
    private double max = 1;
    private Color lowColor = Color.web("#EEF2FF");
    private Color highColor = Color.web("#4F46E5");
    private double cellGap = 2;
    private final Canvas canvas = new Canvas(240, 160);

    /**
     * Creates an empty {@code FxHeatmap}.
     */
    public FxHeatmap() {
        super();
        getStyleClass().add("forja-heatmap");
        getChildren().add(canvas);
        setPrefSize(240, 160);
        widthProperty().addListener((obs, o, v) -> { canvas.setWidth(v.doubleValue()); redraw(); });
        heightProperty().addListener((obs, o, v) -> { canvas.setHeight(v.doubleValue()); redraw(); });
    }

    private void redraw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        g.clearRect(0, 0, w, h);
        int rows = data.length;
        if (rows == 0 || w <= 0 || h <= 0) return;
        int cols = 0;
        for (double[] row : data) if (row != null && row.length > cols) cols = row.length;
        if (cols == 0) return;
        double cw = (w - (cols + 1) * cellGap) / cols;
        double ch = (h - (rows + 1) * cellGap) / rows;
        double range = Math.max(1e-9, max - min);
        for (int r = 0; r < rows; r++) {
            double[] row = data[r];
            if (row == null) continue;
            for (int c = 0; c < row.length; c++) {
                double v = row[c];
                double pct = Math.max(0, Math.min(1, (v - min) / range));
                g.setFill(lowColor.interpolate(highColor, pct));
                double x = cellGap + c * (cw + cellGap);
                double y = cellGap + r * (ch + cellGap);
                g.fillRect(x, y, cw, ch);
            }
        }
    }

    /** Sets the heatmap data + redraws. */
    public void setData(double[][] data) { this.data = data == null ? new double[0][0] : data; redraw(); }
    /** Returns the current data reference. */
    public double[][] getData() { return data; }

    /** Sets the value min. */
    public void setMin(double v) { this.min = v; redraw(); }
    /** Returns the value min. */
    public double getMin() { return min; }
    /** Sets the value max. */
    public void setMax(double v) { this.max = v; redraw(); }
    /** Returns the value max. */
    public double getMax() { return max; }

    /** Sets the low-end color. */
    public void setLowColor(Color v) { this.lowColor = v == null ? Color.web("#EEF2FF") : v; redraw(); }
    /** Returns the low-end color. */
    public Color getLowColor() { return lowColor; }
    /** Sets the high-end color. */
    public void setHighColor(Color v) { this.highColor = v == null ? Color.web("#4F46E5") : v; redraw(); }
    /** Returns the high-end color. */
    public Color getHighColor() { return highColor; }

    /** Sets the cell gap in px. */
    public void setCellGap(double v) { this.cellGap = Math.max(0, v); redraw(); }
    /** Returns the cell gap. */
    public double getCellGap() { return cellGap; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxHeatmap}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxHeatmap}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>data — empty</li>
     *   <li>min / max — {@code 0} / {@code 1}</li>
     *   <li>lowColor — accent-tint indigo</li>
     *   <li>highColor — accent indigo</li>
     *   <li>cellGap — {@code 2}</li>
     *   <li>width / height — {@code 240} / {@code 160}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxHeatmap, Builder> {

        private double[][] data = new double[0][0];
        private double min = 0;
        private double max = 1;
        private Color lowColor = Color.web("#EEF2FF");
        private Color highColor = Color.web("#4F46E5");
        private double cellGap = 2;
        private double width = 240;
        private double height = 160;

        public Builder data(double[][] data) { this.data = data == null ? new double[0][0] : data; return this; }
        public Builder min(double min) { this.min = min; return this; }
        public Builder max(double max) { this.max = max; return this; }
        public Builder lowColor(Color lowColor) { this.lowColor = lowColor == null ? Color.web("#EEF2FF") : lowColor; return this; }
        public Builder highColor(Color highColor) { this.highColor = highColor == null ? Color.web("#4F46E5") : highColor; return this; }
        public Builder cellGap(double cellGap) { this.cellGap = Math.max(0, cellGap); return this; }
        public Builder width(double width) { this.width = width; return this; }
        public Builder height(double height) { this.height = height; return this; }

        @Override
        public FxHeatmap build() {
            FxHeatmap hm = new FxHeatmap();
            hm.setMin(min);
            hm.setMax(max);
            hm.setLowColor(lowColor);
            hm.setHighColor(highColor);
            hm.setCellGap(cellGap);
            hm.setPrefSize(width, height);
            hm.setData(data);
            applyBase(hm);
            return hm;
        }
    }
}
