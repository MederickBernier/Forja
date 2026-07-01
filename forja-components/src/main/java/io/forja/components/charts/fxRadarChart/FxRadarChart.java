package io.forja.components.charts.fxRadarChart;

import io.forja.builder.FxNodeBuilder;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A radar (spider) chart — polygon-per-series over polar axes.
 *
 * <p>Categories are laid out equally around the circle. Each series is a
 * {@code double[]} of length {@code categories}; values are normalized to
 * {@code [0, max]} and drawn as a filled polygon at the corresponding
 * radius on each axis.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxRadarChart r = FxRadarChart.builder()
 *          .categories("Speed", "Power", "Range", "Comfort", "Cost")
 *          .series(new double[]{8, 6, 5, 7, 9}, Color.web("#4F46E5"))
 *          .max(10)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxRadarChart extends Region {

    private final List<String> categories = new ArrayList<>();
    private final List<Series> seriesList = new ArrayList<>();
    private double max = 10;
    private final Canvas canvas = new Canvas(240, 240);

    /**
     * Creates an empty {@code FxRadarChart}.
     */
    public FxRadarChart() {
        super();
        getStyleClass().add("forja-radar-chart");
        getChildren().add(canvas);
        setPrefSize(240, 240);
        widthProperty().addListener((obs, o, v) -> { canvas.setWidth(v.doubleValue()); redraw(); });
        heightProperty().addListener((obs, o, v) -> { canvas.setHeight(v.doubleValue()); redraw(); });
    }

    private void redraw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        g.clearRect(0, 0, w, h);
        int n = categories.size();
        if (n < 3 || w <= 0 || h <= 0) return;
        double cx = w / 2.0;
        double cy = h / 2.0;
        double radius = Math.min(w, h) * 0.4;

        g.setStroke(Color.web("#E4E4E7"));
        g.setLineWidth(1);
        for (int ring = 1; ring <= 4; ring++) {
            double r = radius * (ring / 4.0);
            drawPolygon(g, cx, cy, r, n, false);
        }
        for (int i = 0; i < n; i++) {
            double a = angle(i, n);
            g.strokeLine(cx, cy, cx + Math.cos(a) * radius, cy + Math.sin(a) * radius);
        }

        for (Series s : seriesList) {
            double[] pts = s.values;
            if (pts == null || pts.length < n) continue;
            g.beginPath();
            for (int i = 0; i < n; i++) {
                double pct = Math.max(0, Math.min(1, pts[i] / Math.max(1e-9, max)));
                double r = radius * pct;
                double a = angle(i, n);
                double x = cx + Math.cos(a) * r;
                double y = cy + Math.sin(a) * r;
                if (i == 0) g.moveTo(x, y); else g.lineTo(x, y);
            }
            g.closePath();
            Color base = s.color == null ? Color.web("#4F46E5") : s.color;
            g.setFill(Color.color(base.getRed(), base.getGreen(), base.getBlue(), 0.25));
            g.fill();
            g.setStroke(base);
            g.setLineWidth(1.5);
            g.stroke();
        }

        g.setFill(Color.web("#71717A"));
        g.setFont(javafx.scene.text.Font.font("System", 11));
        g.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
        for (int i = 0; i < n; i++) {
            double a = angle(i, n);
            double lx = cx + Math.cos(a) * (radius + 12);
            double ly = cy + Math.sin(a) * (radius + 12) + 4;
            g.fillText(categories.get(i), lx, ly);
        }
    }

    private void drawPolygon(GraphicsContext g, double cx, double cy, double r, int n, boolean fill) {
        g.beginPath();
        for (int i = 0; i < n; i++) {
            double a = angle(i, n);
            double x = cx + Math.cos(a) * r;
            double y = cy + Math.sin(a) * r;
            if (i == 0) g.moveTo(x, y); else g.lineTo(x, y);
        }
        g.closePath();
        if (fill) g.fill(); else g.stroke();
    }

    private double angle(int i, int n) {
        return -Math.PI / 2 + i * 2 * Math.PI / n;
    }

    /** Sets the axis categories. */
    public void setCategories(List<String> categories) {
        this.categories.clear();
        if (categories != null) this.categories.addAll(categories);
        redraw();
    }

    /** Returns the axis categories. */
    public List<String> getCategories() { return Collections.unmodifiableList(categories); }

    /** Adds a series with values matching categories order. */
    public void addSeries(double[] values, Color color) {
        seriesList.add(new Series(values, color));
        redraw();
    }

    /** Clears all series. */
    public void clearSeries() { seriesList.clear(); redraw(); }

    /** Sets the max value used for radius normalization. */
    public void setMax(double v) { this.max = v; redraw(); }
    /** Returns the current max. */
    public double getMax() { return max; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxRadarChart}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    private static final class Series { final double[] values; final Color color; Series(double[] v, Color c) { this.values = v; this.color = c; } }

    /**
     * Fluent builder for constructing an {@link FxRadarChart}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>categories — empty</li>
     *   <li>series — empty</li>
     *   <li>max — {@code 10}</li>
     *   <li>width / height — {@code 240} / {@code 240}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxRadarChart, Builder> {

        private List<String> categories = Collections.emptyList();
        private List<Series> seriesList = new ArrayList<>();
        private double max = 10;
        private double width = 240;
        private double height = 240;

        public Builder categories(String... categories) { this.categories = categories == null ? Collections.<String>emptyList() : java.util.Arrays.asList(categories); return this; }
        public Builder categories(List<String> categories) { this.categories = categories == null ? Collections.<String>emptyList() : categories; return this; }
        public Builder series(double[] values, Color color) { seriesList.add(new Series(values, color)); return this; }
        public Builder max(double max) { this.max = max; return this; }
        public Builder width(double width) { this.width = width; return this; }
        public Builder height(double height) { this.height = height; return this; }

        @Override
        public FxRadarChart build() {
            FxRadarChart r = new FxRadarChart();
            r.setMax(max);
            r.setPrefSize(width, height);
            r.setCategories(categories);
            for (Series s : seriesList) r.addSeries(s.values, s.color);
            applyBase(r);
            return r;
        }
    }
}
