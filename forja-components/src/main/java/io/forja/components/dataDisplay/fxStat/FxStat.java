package io.forja.components.dataDisplay.fxStat;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import io.forja.tokens.SemanticVariant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A KPI card — a compact label / value / optional trend delta stat.
 *
 * <p>{@code FxStat} is a {@link VBox} of a muted {@link FxLabel} label,
 * a large {@link FxLabel} value ({@link LabelVariant#DISPLAY}), and an
 * optional trend row (icon + text) colored by a {@link Trend} enum.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxStat mrr = FxStat.builder()
 *          .label("Monthly recurring revenue")
 *          .value("$42,180")
 *          .trend("+12.4% MoM", FxStat.Trend.UP)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxStat extends VBox {

    /** Trend direction — controls color + arrow icon. */
    public enum Trend { UP, DOWN, FLAT }

    private static final PseudoClass UP_PC   = PseudoClass.getPseudoClass("up");
    private static final PseudoClass DOWN_PC = PseudoClass.getPseudoClass("down");
    private static final PseudoClass FLAT_PC = PseudoClass.getPseudoClass("flat");

    private final FxLabel labelText = new FxLabel("", LabelVariant.SMALL);
    private final FxLabel valueText = new FxLabel("", LabelVariant.DISPLAY);
    private final FxIcon trendIcon = new FxIcon("fth-arrow-up");
    private final FxLabel trendText = new FxLabel("", LabelVariant.SMALL);
    private final HBox trendRow = new HBox(4, trendIcon, trendText);

    private final StringProperty label = new SimpleStringProperty(this, "label", "");
    private final StringProperty value = new SimpleStringProperty(this, "value", "");
    private final StringProperty trendMessage = new SimpleStringProperty(this, "trendMessage", "");
    private final ObjectProperty<Trend> trend = new SimpleObjectProperty<>(this, "trend");

    /**
     * Creates an empty {@code FxStat}.
     */
    public FxStat() {
        super();
        getStyleClass().add("forja-stat");
        setSpacing(4);
        setAlignment(Pos.TOP_LEFT);

        labelText.getStyleClass().add("forja-stat-label");
        labelText.setMuted(true);
        valueText.getStyleClass().add("forja-stat-value");
        trendRow.getStyleClass().add("forja-stat-trend");
        trendRow.setAlignment(Pos.CENTER_LEFT);
        trendRow.setVisible(false);
        trendRow.setManaged(false);
        trendIcon.getStyleClass().add("forja-stat-trend-icon");
        trendText.getStyleClass().add("forja-stat-trend-text");

        getChildren().addAll(labelText, valueText, trendRow);

        label.addListener((obs, o, v) -> labelText.setText(v == null ? "" : v));
        value.addListener((obs, o, v) -> valueText.setText(v == null ? "" : v));
        trendMessage.addListener((obs, o, v) -> {
            trendText.setText(v == null ? "" : v);
            refreshTrendVisibility();
        });
        trend.addListener((obs, o, v) -> {
            applyTrendPseudoClass();
            trendIcon.setIconLiteral(iconLiteralFor(v));
            refreshTrendVisibility();
        });
        applyTrendPseudoClass();
    }

    private static String iconLiteralFor(Trend t) {
        if (t == null) return "fth-minus";
        switch (t) {
            case UP:   return "fth-arrow-up";
            case DOWN: return "fth-arrow-down";
            case FLAT:
            default:   return "fth-minus";
        }
    }

    private void applyTrendPseudoClass() {
        pseudoClassStateChanged(UP_PC,   false);
        pseudoClassStateChanged(DOWN_PC, false);
        pseudoClassStateChanged(FLAT_PC, false);
        if (getTrend() == null) return;
        switch (getTrend()) {
            case UP:   pseudoClassStateChanged(UP_PC,   true); break;
            case DOWN: pseudoClassStateChanged(DOWN_PC, true); break;
            case FLAT: pseudoClassStateChanged(FLAT_PC, true); break;
        }
    }

    private void refreshTrendVisibility() {
        boolean vis = getTrend() != null
                && getTrendMessage() != null && !getTrendMessage().isEmpty();
        trendRow.setVisible(vis);
        trendRow.setManaged(vis);
    }

    /** Returns the label text node. */
    public FxLabel getLabelText() { return labelText; }

    /** Returns the value text node. */
    public FxLabel getValueText() { return valueText; }

    /** Returns the trend-icon node. */
    public FxIcon getTrendIcon() { return trendIcon; }

    /** Returns the trend-text node. */
    public FxLabel getTrendText() { return trendText; }

    /** Returns the trend row. */
    public HBox getTrendRow() { return trendRow; }

    /** Returns the label property. */
    public StringProperty labelProperty() { return label; }

    /** Returns the current label. */
    public String getLabel() { return label.get(); }

    /** Sets the label. */
    public void setLabel(String v) { label.set(v == null ? "" : v); }

    /** Returns the value property. */
    public StringProperty valueProperty() { return value; }

    /** Returns the current value. */
    public String getValue() { return value.get(); }

    /** Sets the value. */
    public void setValue(String v) { value.set(v == null ? "" : v); }

    /** Returns the trend-message property. */
    public StringProperty trendMessageProperty() { return trendMessage; }

    /** Returns the current trend message. */
    public String getTrendMessage() { return trendMessage.get(); }

    /** Sets the trend message. */
    public void setTrendMessage(String v) { trendMessage.set(v == null ? "" : v); }

    /** Returns the trend property. */
    public ObjectProperty<Trend> trendProperty() { return trend; }

    /** Returns the current trend. */
    public Trend getTrend() { return trend.get(); }

    /** Sets the trend direction. */
    public void setTrend(Trend v) { trend.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxStat}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxStat}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>label / value / trend — empty / {@code null}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxStat, Builder> {

        private String label = "";
        private String value = "";
        private String trendMessage = "";
        private Trend trend;

        public Builder label(String label) { this.label = label == null ? "" : label; return this; }
        public Builder value(String value) { this.value = value == null ? "" : value; return this; }
        public Builder trend(String trendMessage, Trend trend) {
            this.trendMessage = trendMessage == null ? "" : trendMessage;
            this.trend = trend;
            return this;
        }

        /** Convenience — sets trend with an implicit SemanticVariant color hint. Only reads variant to pick trend. */
        public Builder trend(String trendMessage, SemanticVariant variant) {
            this.trendMessage = trendMessage == null ? "" : trendMessage;
            if (variant == SemanticVariant.SUCCESS)      this.trend = Trend.UP;
            else if (variant == SemanticVariant.DANGER)  this.trend = Trend.DOWN;
            else                                         this.trend = Trend.FLAT;
            return this;
        }

        @Override
        public FxStat build() {
            FxStat s = new FxStat();
            s.setLabel(label);
            s.setValue(value);
            s.setTrend(trend);
            s.setTrendMessage(trendMessage);
            applyBase(s);
            return s;
        }
    }
}
