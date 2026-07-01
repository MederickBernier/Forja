package io.forja.components.selection.fxColorPicker;

import io.forja.builder.FxComponentBuilder;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * A styled color picker built on {@link ColorPicker}.
 *
 * <p>Extends the standard JavaFX {@code ColorPicker} — all native properties
 * remain fully accessible. Forja only adds the {@code forja-color-picker}
 * style class and a fluent builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxColorPicker cp = FxColorPicker.builder()
 *          .value(Color.web("#4F46E5"))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxColorPicker extends ColorPicker {

    /**
     * Creates a black {@code FxColorPicker}.
     */
    public FxColorPicker() {
        super();
        getStyleClass().add("forja-color-picker");
    }

    /**
     * Creates an {@code FxColorPicker} initialized to the given color.
     *
     * @param color initial color
     */
    public FxColorPicker(Color color) {
        super(color == null ? Color.BLACK : color);
        getStyleClass().add("forja-color-picker");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxColorPicker}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxColorPicker}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>value — {@link Color#BLACK}</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxColorPicker, Builder> {

        private Color value = Color.BLACK;

        public Builder value(Color value) { this.value = value == null ? Color.BLACK : value; return this; }

        @Override
        public FxColorPicker build() {
            FxColorPicker p = new FxColorPicker(value);
            applyBase(p);
            return p;
        }
    }
}
