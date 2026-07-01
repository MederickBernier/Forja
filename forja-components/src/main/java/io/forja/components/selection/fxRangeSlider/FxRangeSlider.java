package io.forja.components.selection.fxRangeSlider;

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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A two-thumb range slider — {@code lowValue} + {@code highValue}.
 *
 * <p>Implemented as two overlaid transparent-track {@link Slider}s. The low
 * slider is clamped to {@code ≤ highValue} and the high slider to
 * {@code ≥ lowValue}, so thumbs can't cross.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxRangeSlider price = FxRangeSlider.builder()
 *          .min(0).max(1000)
 *          .lowValue(100).highValue(500)
 *          .step(10)
 *          .prefix("$")
 *          .showValues(true)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxRangeSlider extends VBox {

    private final Slider lowSlider = new Slider();
    private final Slider highSlider = new Slider();
    private final StackPane stack = new StackPane(lowSlider, highSlider);
    private final FxLabel valueLabel = new FxLabel("", LabelVariant.SMALL);
    private final HBox row = new HBox();

    private final DoubleProperty min = new SimpleDoubleProperty(this, "min", 0);
    private final DoubleProperty max = new SimpleDoubleProperty(this, "max", 100);
    private final DoubleProperty lowValue = new SimpleDoubleProperty(this, "lowValue", 0);
    private final DoubleProperty highValue = new SimpleDoubleProperty(this, "highValue", 100);
    private final DoubleProperty step = new SimpleDoubleProperty(this, "step", 1);
    private final BooleanProperty showValues = new SimpleBooleanProperty(this, "showValues", false);
    private final StringProperty prefix = new SimpleStringProperty(this, "prefix", "");
    private final StringProperty suffix = new SimpleStringProperty(this, "suffix", "");
    private boolean syncing = false;

    /**
     * Creates a {@code FxRangeSlider} in {@code 0..100} range covering the
     * full span.
     */
    public FxRangeSlider() {
        super();
        getStyleClass().add("forja-range-slider");
        setSpacing(4);

        lowSlider.getStyleClass().add("forja-range-slider-low");
        highSlider.getStyleClass().add("forja-range-slider-high");
        lowSlider.setMin(min.get()); lowSlider.setMax(max.get());
        highSlider.setMin(min.get()); highSlider.setMax(max.get());
        lowSlider.setValue(lowValue.get());
        highSlider.setValue(highValue.get());

        HBox.setHgrow(stack, Priority.ALWAYS);
        row.getStyleClass().add("forja-range-slider-row");
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(8);
        valueLabel.getStyleClass().add("forja-range-slider-value");
        valueLabel.setMuted(true);
        valueLabel.setVisible(false);
        valueLabel.setManaged(false);
        row.getChildren().addAll(stack, valueLabel);
        getChildren().add(row);

        min.addListener((obs, o, v) -> { lowSlider.setMin(v.doubleValue()); highSlider.setMin(v.doubleValue()); });
        max.addListener((obs, o, v) -> { lowSlider.setMax(v.doubleValue()); highSlider.setMax(v.doubleValue()); });
        step.addListener((obs, o, v) -> { lowSlider.setBlockIncrement(v.doubleValue()); highSlider.setBlockIncrement(v.doubleValue()); });

        lowSlider.valueProperty().addListener((obs, o, v) -> {
            if (syncing) return;
            syncing = true;
            try {
                double lv = Math.min(v.doubleValue(), getHighValue());
                if (lv != v.doubleValue()) lowSlider.setValue(lv);
                lowValue.set(lv);
                refreshValueLabel();
            } finally { syncing = false; }
        });
        highSlider.valueProperty().addListener((obs, o, v) -> {
            if (syncing) return;
            syncing = true;
            try {
                double hv = Math.max(v.doubleValue(), getLowValue());
                if (hv != v.doubleValue()) highSlider.setValue(hv);
                highValue.set(hv);
                refreshValueLabel();
            } finally { syncing = false; }
        });
        lowValue.addListener((obs, o, v) -> { if (!syncing) lowSlider.setValue(v.doubleValue()); refreshValueLabel(); });
        highValue.addListener((obs, o, v) -> { if (!syncing) highSlider.setValue(v.doubleValue()); refreshValueLabel(); });
        showValues.addListener((obs, o, v) -> refreshValueLabel());
        prefix.addListener((obs, o, v) -> refreshValueLabel());
        suffix.addListener((obs, o, v) -> refreshValueLabel());

        refreshValueLabel();
    }

    private void refreshValueLabel() {
        boolean show = isShowValues();
        valueLabel.setVisible(show);
        valueLabel.setManaged(show);
        if (show) {
            String p = getPrefix() == null ? "" : getPrefix();
            String s = getSuffix() == null ? "" : getSuffix();
            valueLabel.setText(p + String.format("%.0f", getLowValue()) + s + " – " + p + String.format("%.0f", getHighValue()) + s);
        }
    }

    /** Returns the low-thumb slider. */
    public Slider getLowSlider() { return lowSlider; }

    /** Returns the high-thumb slider. */
    public Slider getHighSlider() { return highSlider; }

    /** Returns the value label. */
    public FxLabel getValueLabel() { return valueLabel; }

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

    /** Returns the low-value property. */
    public DoubleProperty lowValueProperty() { return lowValue; }
    /** Returns the current low value. */
    public double getLowValue() { return lowValue.get(); }
    /** Sets the low value, clamped to {@code ≤ highValue}. */
    public void setLowValue(double v) { lowValue.set(Math.min(v, getHighValue())); }

    /** Returns the high-value property. */
    public DoubleProperty highValueProperty() { return highValue; }
    /** Returns the current high value. */
    public double getHighValue() { return highValue.get(); }
    /** Sets the high value, clamped to {@code ≥ lowValue}. */
    public void setHighValue(double v) { highValue.set(Math.max(v, getLowValue())); }

    /** Returns the step property. */
    public DoubleProperty stepProperty() { return step; }
    /** Returns the current step. */
    public double getStep() { return step.get(); }
    /** Sets the step. */
    public void setStep(double v) { step.set(v); }

    /** Returns the show-values property. */
    public BooleanProperty showValuesProperty() { return showValues; }
    /** Returns whether the value label is visible. */
    public boolean isShowValues() { return showValues.get(); }
    /** Sets whether the value label is visible. */
    public void setShowValues(boolean v) { showValues.set(v); }

    /** Returns the prefix property. */
    public StringProperty prefixProperty() { return prefix; }
    /** Returns the current prefix. */
    public String getPrefix() { return prefix.get(); }
    /** Sets the prefix. */
    public void setPrefix(String v) { prefix.set(v == null ? "" : v); }

    /** Returns the suffix property. */
    public StringProperty suffixProperty() { return suffix; }
    /** Returns the current suffix. */
    public String getSuffix() { return suffix.get(); }
    /** Sets the suffix. */
    public void setSuffix(String v) { suffix.set(v == null ? "" : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxRangeSlider}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxRangeSlider}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>min / max — {@code 0} / {@code 100}</li>
     *   <li>lowValue / highValue — {@code 0} / {@code 100}</li>
     *   <li>step — {@code 1}</li>
     *   <li>showValues — {@code false}</li>
     *   <li>prefix / suffix — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxRangeSlider, Builder> {

        private double min = 0;
        private double max = 100;
        private double lowValue = 0;
        private double highValue = 100;
        private double step = 1;
        private boolean showValues = false;
        private String prefix = "";
        private String suffix = "";

        public Builder min(double min) { this.min = min; return this; }
        public Builder max(double max) { this.max = max; return this; }
        public Builder lowValue(double lowValue) { this.lowValue = lowValue; return this; }
        public Builder highValue(double highValue) { this.highValue = highValue; return this; }
        public Builder step(double step) { this.step = step; return this; }
        public Builder showValues(boolean showValues) { this.showValues = showValues; return this; }
        public Builder prefix(String prefix) { this.prefix = prefix == null ? "" : prefix; return this; }
        public Builder suffix(String suffix) { this.suffix = suffix == null ? "" : suffix; return this; }

        @Override
        public FxRangeSlider build() {
            FxRangeSlider r = new FxRangeSlider();
            r.setMin(min);
            r.setMax(max);
            r.setStep(step);
            r.setPrefix(prefix);
            r.setSuffix(suffix);
            r.setShowValues(showValues);
            r.setHighValue(highValue);
            r.setLowValue(lowValue);
            applyBase(r);
            return r;
        }
    }
}
