package io.forja.components.charts.fxSparkline;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * A compact inline chart — a polyline drawn on a {@link Canvas} sized to fit
 * its parent. Ideal for tables/lists where a numeric trend needs to appear
 * inline.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxSparkline s = FxSparkline.builder()
 *          .data(1, 3, 2, 6, 5, 8, 7)
 *          .stroke(Color.web("#4F46E5"))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxSparkline extends Region {

    private final ObservableList<Number> data = FXCollections.observableArrayList();
    private final ObjectProperty<Color> stroke = new SimpleObjectProperty<>(this, "stroke", Color.web("#4F46E5"));
    private double strokeWidth = 1.5;
    private final Canvas canvas = new Canvas(120, 32);

    /**
     * Creates an empty {@code FxSparkline}.
     */
    public FxSparkline() {
        super();
        getStyleClass().add("forja-sparkline");
        getChildren().add(canvas);
        setPrefSize(120, 32);
        widthProperty().addListener((obs, o, v) -> { canvas.setWidth(v.doubleValue()); redraw(); });
        heightProperty().addListener((obs, o, v) -> { canvas.setHeight(v.doubleValue()); redraw(); });
        data.addListener((javafx.collections.ListChangeListener<Number>) c -> redraw());
        stroke.addListener((obs, o, v) -> redraw());
    }

    private void redraw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        g.clearRect(0, 0, w, h);
        int n = data.size();
        if (n < 2 || w <= 0 || h <= 0) return;
        double min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
        for (Number num : data) { double d = num.doubleValue(); if (d < min) min = d; if (d > max) max = d; }
        double range = Math.max(1e-9, max - min);
        double step = w / (n - 1);
        g.setStroke(getStroke());
        g.setLineWidth(strokeWidth);
        g.beginPath();
        for (int i = 0; i < n; i++) {
            double x = i * step;
            double y = h - ((data.get(i).doubleValue() - min) / range) * h;
            if (i == 0) g.moveTo(x, y); else g.lineTo(x, y);
        }
        g.stroke();
    }

    /** Returns the data list. */
    public ObservableList<Number> getData() { return data; }

    /** Returns the stroke property. */
    public ObjectProperty<Color> strokeProperty() { return stroke; }
    /** Returns the stroke color. */
    public Color getStroke() { return stroke.get(); }
    /** Sets the stroke color. */
    public void setStroke(Color v) { stroke.set(v == null ? Color.web("#4F46E5") : v); }

    /** Returns the stroke width. */
    public double getStrokeWidth() { return strokeWidth; }
    /** Sets the stroke width. */
    public void setStrokeWidth(double v) { this.strokeWidth = Math.max(0, v); redraw(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSparkline}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxSparkline}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>data — empty</li>
     *   <li>stroke — accent indigo</li>
     *   <li>strokeWidth — {@code 1.5}</li>
     *   <li>width / height — {@code 120} / {@code 32}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxSparkline, Builder> {

        private Number[] data = new Number[0];
        private Color stroke = Color.web("#4F46E5");
        private double strokeWidth = 1.5;
        private double width = 120;
        private double height = 32;

        public Builder data(Number... data) { this.data = data == null ? new Number[0] : data; return this; }
        public Builder stroke(Color stroke) { this.stroke = stroke == null ? Color.web("#4F46E5") : stroke; return this; }
        public Builder strokeWidth(double strokeWidth) { this.strokeWidth = Math.max(0, strokeWidth); return this; }
        public Builder width(double width) { this.width = width; return this; }
        public Builder height(double height) { this.height = height; return this; }

        @Override
        public FxSparkline build() {
            FxSparkline s = new FxSparkline();
            s.setStroke(stroke);
            s.setStrokeWidth(strokeWidth);
            s.setPrefSize(width, height);
            for (Number n : data) s.getData().add(n);
            applyBase(s);
            return s;
        }
    }
}
