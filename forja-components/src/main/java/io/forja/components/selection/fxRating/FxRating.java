package io.forja.components.selection.fxRating;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

/**
 * A star-rating input from {@code 0} to {@link #getMax}.
 *
 * <p>Click a star to set the rating. Hovering previews highlight up to that
 * position. Set {@link #setReadonly readonly(true)} to lock interaction.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxRating stars = FxRating.builder()
 *          .max(5)
 *          .value(3)
 *          .onChange(v -> logger.info("rated {}", v))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxRating extends HBox {

    private static final PseudoClass FILLED_PC = PseudoClass.getPseudoClass("filled");
    private static final PseudoClass HOVER_PC  = PseudoClass.getPseudoClass("preview");

    private final IntegerProperty max = new SimpleIntegerProperty(this, "max", 5);
    private final IntegerProperty value = new SimpleIntegerProperty(this, "value", 0);
    private final BooleanProperty readonly = new SimpleBooleanProperty(this, "readonly", false);

    private final List<FxIcon> stars = new ArrayList<>();
    private OnChange onChange;

    /**
     * Creates a 5-star {@code FxRating} at rating 0.
     */
    public FxRating() {
        super();
        getStyleClass().add("forja-rating");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(2);
        rebuildStars();

        max.addListener((obs, o, v) -> rebuildStars());
        value.addListener((obs, o, v) -> { paintStars(v.intValue()); if (onChange != null) onChange.accept(v.intValue()); });
        readonly.addListener((obs, o, v) -> rebuildStars());
    }

    private void rebuildStars() {
        getChildren().clear();
        stars.clear();
        int n = Math.max(0, getMax());
        for (int i = 0; i < n; i++) {
            final int idx = i + 1;
            FxIcon star = new FxIcon("fth-star");
            star.getStyleClass().add("forja-rating-star");
            if (!isReadonly()) {
                star.setOnMouseClicked(e -> { setValue(idx); e.consume(); });
                star.setOnMouseEntered(e -> paintPreview(idx));
                star.setOnMouseExited(e -> paintStars(getValue()));
            }
            stars.add(star);
            getChildren().add(star);
        }
        paintStars(getValue());
    }

    private void paintStars(int filled) {
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).pseudoClassStateChanged(FILLED_PC, i < filled);
            stars.get(i).pseudoClassStateChanged(HOVER_PC, false);
        }
    }

    private void paintPreview(int upTo) {
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).pseudoClassStateChanged(HOVER_PC, i < upTo);
        }
    }

    /** Returns the underlying star icons. */
    public List<FxIcon> getStars() { return stars; }

    /** Returns the max property. */
    public IntegerProperty maxProperty() { return max; }

    /** Returns the current maximum. */
    public int getMax() { return max.get(); }

    /** Sets the maximum star count. */
    public void setMax(int v) { max.set(Math.max(0, v)); }

    /** Returns the value property. */
    public IntegerProperty valueProperty() { return value; }

    /** Returns the current rating. */
    public int getValue() { return value.get(); }

    /** Sets the current rating, clamped to {@code [0, max]}. */
    public void setValue(int v) { value.set(Math.max(0, Math.min(getMax(), v))); }

    /** Returns the readonly property. */
    public BooleanProperty readonlyProperty() { return readonly; }

    /** Returns whether the rating is interactive. */
    public boolean isReadonly() { return readonly.get(); }

    /** Sets whether the rating is interactive. */
    public void setReadonly(boolean v) { readonly.set(v); }

    /** Sets the change callback. */
    public void setOnChange(OnChange onChange) { this.onChange = onChange; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxRating}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when the rating changes. */
    @FunctionalInterface
    public interface OnChange { void accept(int value); }

    /**
     * Fluent builder for constructing an {@link FxRating}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>max — {@code 5}</li>
     *   <li>value — {@code 0}</li>
     *   <li>readonly — {@code false}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxRating, Builder> {

        private int max = 5;
        private int value = 0;
        private boolean readonly = false;
        private OnChange onChange;

        public Builder max(int max) { this.max = Math.max(0, max); return this; }
        public Builder value(int value) { this.value = value; return this; }
        public Builder readonly(boolean readonly) { this.readonly = readonly; return this; }
        public Builder onChange(OnChange onChange) { this.onChange = onChange; return this; }

        @Override
        public FxRating build() {
            FxRating r = new FxRating();
            r.setMax(max);
            r.setReadonly(readonly);
            r.setValue(value);
            r.setOnChange(onChange);
            applyBase(r);
            return r;
        }
    }
}
