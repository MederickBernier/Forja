package io.forja.components.selection.fxCheckBox;

import io.forja.builder.FxComponentBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

/**
 * A styled check box built on {@link CheckBox}.
 *
 * <p>{@code FxCheckBox} extends the standard JavaFX {@code CheckBox} — all
 * native properties, bindings, and event APIs remain fully accessible. Forja
 * only adds the {@code forja-checkbox} style class and a fluent builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxCheckBox agree = FxCheckBox.builder()
 *          .text("I agree to the terms")
 *          .selected(false)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxCheckBox extends CheckBox {

    /**
     * Creates an empty {@code FxCheckBox}.
     */
    public FxCheckBox() {
        super();
        getStyleClass().add("forja-checkbox");
    }

    /**
     * Creates an {@code FxCheckBox} with the given label text.
     *
     * @param text the label displayed next to the check box
     */
    public FxCheckBox(String text) {
        super(text);
        getStyleClass().add("forja-checkbox");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxCheckBox}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxCheckBox}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     *   <li>selected — {@code false}</li>
     *   <li>indeterminate — {@code false}</li>
     *   <li>allowIndeterminate — {@code false}</li>
     *   <li>onAction — none</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxCheckBox, Builder> {

        private String text = "";
        private boolean selected = false;
        private boolean indeterminate = false;
        private boolean allowIndeterminate = false;
        private EventHandler<ActionEvent> onAction;

        public Builder text(String text) {
            this.text = text == null ? "" : text;
            return this;
        }

        public Builder selected(boolean selected) {
            this.selected = selected;
            return this;
        }

        public Builder indeterminate(boolean indeterminate) {
            this.indeterminate = indeterminate;
            return this;
        }

        public Builder allowIndeterminate(boolean allowIndeterminate) {
            this.allowIndeterminate = allowIndeterminate;
            return this;
        }

        public Builder onAction(EventHandler<ActionEvent> handler) {
            this.onAction = handler;
            return this;
        }

        @Override
        public FxCheckBox build() {
            FxCheckBox box = new FxCheckBox(text);
            box.setAllowIndeterminate(allowIndeterminate);
            box.setIndeterminate(indeterminate);
            box.setSelected(selected);
            if (onAction != null) box.setOnAction(onAction);
            applyBase(box);
            return box;
        }
    }
}
