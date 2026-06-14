package io.forja.components;

import io.forja.builder.FxComponentBuilder;
import javafx.scene.control.Label;

/**
 * A styled inline code span built on top of {@link Label}.
 *
 * <p>{@code FxCode} renders a short identifier, command, or path in a
 * monospace font over a subtle background tint. Use it for inline references
 * to code (e.g. {@code "set FxButton.variant() to PRIMARY"}).
 *
 * <p>Companion to {@link FxKbd}, which renders keyboard-shortcut chips. Use
 * {@code FxCode} for code identifiers, {@code FxKbd} for keys.
 *
 * <p>The preferred way to construct an {@code FxCode} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxCode method = FxCode.builder().text("Forja.install(scene)").build();
 *     }
 * </pre>
 *
 * @see FxKbd
 * @see Builder
 */
public class FxCode extends Label {

    /**
     * Creates an empty {@code FxCode}.
     */
    public FxCode() {
        super();
        getStyleClass().add("forja-code");
    }

    /**
     * Creates an {@code FxCode} with the given inline code text.
     *
     * @param text the code identifier or snippet
     */
    public FxCode(String text) {
        super(text);
        getStyleClass().add("forja-code");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxCode}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxCode}.
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
    public static class Builder extends FxComponentBuilder<FxCode, Builder> {

        private String text = "";

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        @Override
        public FxCode build() {
            FxCode code = new FxCode(text);
            applyBase(code);
            return code;
        }
    }
}
