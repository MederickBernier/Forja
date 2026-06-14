package io.forja.components;

import io.forja.builder.FxComponentBuilder;
import javafx.scene.control.Label;

/**
 * A styled keyboard-shortcut chip built on top of {@link Label}.
 *
 * <p>{@code FxKbd} renders a single key or key-combination token (e.g.
 * {@code "Ctrl"}, {@code "K"}, {@code "⌘+P"}) with a rounded border and
 * monospace font. Pair multiple instances inside an {@link FxRow} or
 * {@link javafx.scene.layout.HBox} to compose a full shortcut hint.
 *
 * <p>The preferred way to construct an {@code FxKbd} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxKbd cmd = FxKbd.builder().text("⌘").build();
 *      FxKbd k   = FxKbd.builder().text("K").build();
 *     }
 * </pre>
 *
 * <p>Constructor and direct instantiation are also supported:</p>
 * <pre>
 *     {@code
 *      FxKbd shift = new FxKbd("Shift");
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxKbd extends Label {

    /**
     * Creates an empty {@code FxKbd}.
     */
    public FxKbd() {
        super();
        getStyleClass().add("forja-kbd");
    }

    /**
     * Creates an {@code FxKbd} with the given key token.
     *
     * @param text the key label, e.g. {@code "Ctrl"}, {@code "⌘"}, {@code "K"}
     */
    public FxKbd(String text) {
        super(text);
        getStyleClass().add("forja-kbd");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxKbd}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxKbd}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty string</li>
     * </ul>
     *
     * <p>Inherited from {@link io.forja.builder.FxComponentBuilder}:
     * <ul>
     *   <li>id, disabled, visible, styleClass, style, tooltip, userData</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxKbd, Builder> {

        private String text = "";

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        @Override
        public FxKbd build() {
            FxKbd kbd = new FxKbd(text);
            applyBase(kbd);
            return kbd;
        }
    }
}
