package io.forja.components.charts.fxGauge;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * A speedometer-style gauge — semicircular arc + needle + numeric value
 * label. Value maps to angle {@code [180°, 0°]} across the arc.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxGauge cpu = FxGauge.builder()
 *          .min(0).max(100).value(42)
 *          .arcColor(Color.web("#4F46E5"))
 *          .suffix("%")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxGauge extends Region {

    private final DoubleProperty min = new SimpleDoubleProperty(this, "min", 0);
    private final DoubleProperty max = new SimpleDoubleProperty(this, "max", 100);
    private final DoubleProperty value = new SimpleDoubleProperty(this, "value", 0);
    private final ObjectProperty<Color> arcColor = new SimpleObjectProperty<>(this, "arcColor", Color.web("#4F46E5"));
    private final ObjectProperty<Color> trackColor = new SimpleObjectProperty<>(this, "trackColor", Color.web("#E4E4E7"));
    private final StringProperty suffix = new SimpleStringProperty(this, "suffix", "");
    private final Canvas canvas = new Canvas(160, 100);

    /**
     * Creates a 0..100 {@code FxGauge} at value 0.
     */
    public FxGauge() {
        super();
        getStyleClass().add("forja-gauge");
        getChildren().add(canvas);
        setPrefSize(160, 100);
        widthProperty().addListener((obs, o, v) -> { canvas.setWidth(v.doubleValue()); redraw(); });
        heightProperty().addListener((obs, o, v) -> { canvas.setHeight(v.doubleValue()); redraw(); });
        min.addListener((obs, o, v) -> redraw());
        max.addListener((obs, o, v) -> redraw());
        value.addListener((obs, o, v) -> redraw());
        arcColor.addListener((obs, o, v) -> redraw());
        trackColor.addListener((obs, o, v) -> redraw());
        suffix.addListener((obs, o, v) -> redraw());
    }

    private void redraw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        g.clearRect(0, 0, w, h);
        if (w <= 0 || h <= 0) return;
        double stroke = Math.max(4, Math.min(w, h) * 0.08);
        double pad = stroke / 2 + 4;
        double arcW = w - pad * 2;
        double arcH = h * 2 - pad * 2;
        double cx = pad;
        double cy = pad;
        double range = Math.max(1e-9, getMax() - getMin());
        double pct = Math.max(0, Math.min(1, (getValue() - getMin()) / range));

        g.setStroke(getTrackColor());
        g.setLineWidth(stroke);
        g.strokeArc(cx, cy, arcW, arcH, 0, 180, ArcType.OPEN);

        g.setStroke(getArcColor());
        double sweep = 180 * pct;
        g.strokeArc(cx, cy, arcW, arcH, 180 - sweep, sweep, ArcType.OPEN);

        g.setFill(Color.web("#18181B"));
        g.setTextAlign(TextAlignment.CENTER);
        g.setFont(Font.font("System", 14));
        String label = String.format("%.0f%s", getValue(), getSuffix() == null ? "" : getSuffix());
        g.fillText(label, w / 2.0, h - 6);
    }

    /** Returns the min property. */
    public DoubleProperty minProperty() { return min; }
    /** Returns the current min. */
    public double getMin() { return min.get(); }
    /** Sets the min. */
    public void setMin(double v) { min.set(v); }

    /** Returns the max property. */
    public DoubleProperty maxProperty() { return max; }
    /** Returns the current max. */
    public double getMax() { return max.get(); }
    /** Sets the max. */
    public void setMax(double v) { max.set(v); }

    /** Returns the value property. */
    public DoubleProperty valueProperty() { return value; }
    /** Returns the current value. */
    public double getValue() { return value.get(); }
    /** Sets the current value. */
    public void setValue(double v) { value.set(v); }

    /** Returns the arc-color property. */
    public ObjectProperty<Color> arcColorProperty() { return arcColor; }
    /** Returns the arc color. */
    public Color getArcColor() { return arcColor.get(); }
    /** Sets the arc color. */
    public void setArcColor(Color v) { arcColor.set(v == null ? Color.web("#4F46E5") : v); }

    /** Returns the track-color property. */
    public ObjectProperty<Color> trackColorProperty() { return trackColor; }
    /** Returns the track color. */
    public Color getTrackColor() { return trackColor.get(); }
    /** Sets the track color. */
    public void setTrackColor(Color v) { trackColor.set(v == null ? Color.web("#E4E4E7") : v); }

    /** Returns the suffix property. */
    public StringProperty suffixProperty() { return suffix; }
    /** Returns the value suffix. */
    public String getSuffix() { return suffix.get(); }
    /** Sets the value suffix. */
    public void setSuffix(String v) { suffix.set(v == null ? "" : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxGauge}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxGauge}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>min / max — {@code 0} / {@code 100}</li>
     *   <li>value — {@code 0}</li>
     *   <li>arcColor — indigo accent</li>
     *   <li>trackColor — zinc-200</li>
     *   <li>suffix — empty</li>
     *   <li>width / height — {@code 160} / {@code 100}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxGauge, Builder> {

        private double min = 0;
        private double max = 100;
        private double value = 0;
        private Color arcColor = Color.web("#4F46E5");
        private Color trackColor = Color.web("#E4E4E7");
        private String suffix = "";
        private double width = 160;
        private double height = 100;

        public Builder min(double min) { this.min = min; return this; }
        public Builder max(double max) { this.max = max; return this; }
        public Builder value(double value) { this.value = value; return this; }
        public Builder arcColor(Color arcColor) { this.arcColor = arcColor == null ? Color.web("#4F46E5") : arcColor; return this; }
        public Builder trackColor(Color trackColor) { this.trackColor = trackColor == null ? Color.web("#E4E4E7") : trackColor; return this; }
        public Builder suffix(String suffix) { this.suffix = suffix == null ? "" : suffix; return this; }
        public Builder width(double width) { this.width = width; return this; }
        public Builder height(double height) { this.height = height; return this; }

        @Override
        public FxGauge build() {
            FxGauge gg = new FxGauge();
            gg.setMin(min);
            gg.setMax(max);
            gg.setArcColor(arcColor);
            gg.setTrackColor(trackColor);
            gg.setSuffix(suffix);
            gg.setPrefSize(width, height);
            gg.setValue(value);
            applyBase(gg);
            return gg;
        }
    }
}
