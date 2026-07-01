package io.forja.components.selection.fxSlider;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * A styled numeric range slider with optional value label + prefix/suffix.
 *
 * <p>{@code FxSlider} is a composite {@link VBox} of a framed row containing
 * the JavaFX {@link Slider} and (optionally) a value {@link FxLabel} on the
 * trailing end. The value label auto-formats with the configured
 * {@link #getDecimals} + prefix/suffix.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxSlider volume = FxSlider.builder()
 *          .min(0).max(100)
 *          .value(60)
 *          .step(5)
 *          .suffix("%")
 *          .showValue(true)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxSlider extends VBox {

    private final Slider slider = new Slider();
    private final FxLabel valueLabel = new FxLabel("", LabelVariant.SMALL);
    private final HBox row = new HBox();

    private final DoubleProperty min = new SimpleDoubleProperty(this, "min", 0);
    private final DoubleProperty max = new SimpleDoubleProperty(this, "max", 100);
    private final DoubleProperty value = new SimpleDoubleProperty(this, "value", 0);
    private final DoubleProperty step = new SimpleDoubleProperty(this, "step", 1);
    private final BooleanProperty showValue = new SimpleBooleanProperty(this, "showValue", false);
    private final SimpleDoubleProperty decimals = new SimpleDoubleProperty(this, "decimals", 0);
    private final StringProperty prefix = new SimpleStringProperty(this, "prefix", "");
    private final StringProperty suffix = new SimpleStringProperty(this, "suffix", "");

    /**
     * Creates an {@code FxSlider} with the default {@code 0..100} range.
     */
    public FxSlider() {
        super();
        getStyleClass().add("forja-slider");

        row.getStyleClass().add("forja-slider-row");
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(8);

        slider.getStyleClass().add("forja-slider-inner");
        HBox.setHgrow(slider, Priority.ALWAYS);
        slider.setMin(min.get());
        slider.setMax(max.get());
        slider.setValue(value.get());
        slider.setBlockIncrement(step.get());

        valueLabel.getStyleClass().add("forja-slider-value");
        valueLabel.setMuted(true);
        valueLabel.setVisible(false);
        valueLabel.setManaged(false);

        row.getChildren().addAll(slider, valueLabel);
        getChildren().add(row);

        min.addListener((obs, o, v) -> slider.setMin(v.doubleValue()));
        max.addListener((obs, o, v) -> slider.setMax(v.doubleValue()));
        step.addListener((obs, o, v) -> slider.setBlockIncrement(v.doubleValue()));
        slider.valueProperty().addListener((obs, o, v) -> {
            value.set(v.doubleValue());
            refreshValueLabel();
        });
        value.addListener((obs, o, v) -> {
            if (slider.getValue() != v.doubleValue()) slider.setValue(v.doubleValue());
            refreshValueLabel();
        });
        showValue.addListener((obs, o, v) -> refreshValueLabel());
        prefix.addListener((obs, o, v) -> refreshValueLabel());
        suffix.addListener((obs, o, v) -> refreshValueLabel());
        decimals.addListener((obs, o, v) -> refreshValueLabel());

        refreshValueLabel();
    }

    private void refreshValueLabel() {
        boolean show = isShowValue();
        valueLabel.setVisible(show);
        valueLabel.setManaged(show);
        if (show) {
            int d = Math.max(0, (int) decimals.get());
            String formatted = String.format(java.util.Locale.US, "%." + d + "f", getValue());
            valueLabel.setText(getPrefix() + formatted + getSuffix());
        }
    }

    /** Returns the underlying {@link Slider} for advanced binding. */
    public Slider getSlider() { return slider; }

    /** Returns the value-label node. */
    public FxLabel getValueLabel() { return valueLabel; }

    /** Returns the row {@link HBox} containing slider + value label. */
    public HBox getRow() { return row; }

    /** Returns the min property. */
    public DoubleProperty minProperty() { return min; }

    /** Returns the current min value. */
    public double getMin() { return min.get(); }

    /** Sets the min value. */
    public void setMin(double v) { min.set(v); }

    /** Returns the max property. */
    public DoubleProperty maxProperty() { return max; }

    /** Returns the current max value. */
    public double getMax() { return max.get(); }

    /** Sets the max value. */
    public void setMax(double v) { max.set(v); }

    /** Returns the value property. */
    public DoubleProperty valueProperty() { return value; }

    /** Returns the current value. */
    public double getValue() { return value.get(); }

    /** Sets the current value. */
    public void setValue(double v) { value.set(v); }

    /** Returns the step (block-increment) property. */
    public DoubleProperty stepProperty() { return step; }

    /** Returns the current step. */
    public double getStep() { return step.get(); }

    /** Sets the block-increment step. */
    public void setStep(double v) { step.set(v); }

    /** Returns the show-value property. */
    public BooleanProperty showValueProperty() { return showValue; }

    /** Returns whether the trailing value label is visible. */
    public boolean isShowValue() { return showValue.get(); }

    /** Sets whether the trailing value label is visible. */
    public void setShowValue(boolean v) { showValue.set(v); }

    /** Returns the decimals-for-display property. */
    public SimpleDoubleProperty decimalsProperty() { return decimals; }

    /** Returns the current fractional-digit count for the value label. */
    public int getDecimals() { return (int) decimals.get(); }

    /** Sets the fractional-digit count for the value label. */
    public void setDecimals(int v) { decimals.set(Math.max(0, v)); }

    /** Returns the prefix property. */
    public StringProperty prefixProperty() { return prefix; }

    /** Returns the current prefix. */
    public String getPrefix() { return prefix.get(); }

    /** Sets the value-label prefix (e.g. "$"). */
    public void setPrefix(String v) { prefix.set(v == null ? "" : v); }

    /** Returns the suffix property. */
    public StringProperty suffixProperty() { return suffix; }

    /** Returns the current suffix. */
    public String getSuffix() { return suffix.get(); }

    /** Sets the value-label suffix (e.g. "%"). */
    public void setSuffix(String v) { suffix.set(v == null ? "" : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSlider}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxSlider}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>min / max — {@code 0} / {@code 100}</li>
     *   <li>value — {@code 0}</li>
     *   <li>step — {@code 1}</li>
     *   <li>decimals — {@code 0}</li>
     *   <li>prefix / suffix — empty</li>
     *   <li>showValue — {@code false}</li>
     *   <li>showTickMarks / showTickLabels — {@code false}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxSlider, Builder> {

        private double min = 0;
        private double max = 100;
        private double value = 0;
        private double step = 1;
        private int decimals = 0;
        private String prefix = "";
        private String suffix = "";
        private boolean showValue = false;
        private boolean showTickMarks = false;
        private boolean showTickLabels = false;
        private double majorTickUnit = 25;

        public Builder min(double min) { this.min = min; return this; }
        public Builder max(double max) { this.max = max; return this; }
        public Builder value(double value) { this.value = value; return this; }
        public Builder step(double step) { this.step = step; return this; }
        public Builder decimals(int decimals) { this.decimals = Math.max(0, decimals); return this; }
        public Builder prefix(String prefix) { this.prefix = prefix == null ? "" : prefix; return this; }
        public Builder suffix(String suffix) { this.suffix = suffix == null ? "" : suffix; return this; }
        public Builder showValue(boolean showValue) { this.showValue = showValue; return this; }
        public Builder showTickMarks(boolean showTickMarks) { this.showTickMarks = showTickMarks; return this; }
        public Builder showTickLabels(boolean showTickLabels) { this.showTickLabels = showTickLabels; return this; }
        public Builder majorTickUnit(double majorTickUnit) { this.majorTickUnit = majorTickUnit; return this; }

        @Override
        public FxSlider build() {
            FxSlider s = new FxSlider();
            s.setMin(min);
            s.setMax(max);
            s.setStep(step);
            s.setDecimals(decimals);
            s.setPrefix(prefix);
            s.setSuffix(suffix);
            s.setShowValue(showValue);
            s.setValue(value);
            s.getSlider().setShowTickMarks(showTickMarks);
            s.getSlider().setShowTickLabels(showTickLabels);
            s.getSlider().setMajorTickUnit(majorTickUnit);
            applyBase(s);
            return s;
        }
    }
}
