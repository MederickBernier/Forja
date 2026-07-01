package io.forja.components.overlays.fxTooltip;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

/**
 * A styled tooltip built on {@link Tooltip}.
 *
 * <p>Extends the standard JavaFX {@code Tooltip} — all native show/hide,
 * positioning, and binding APIs remain fully accessible. Forja adds the
 * {@code forja-tooltip} style class and a fluent builder + convenience
 * {@link #install(Node, String)} shortcut.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxTooltip help = FxTooltip.builder()
 *          .text("Deletes this project permanently")
 *          .showDelayMs(300)
 *          .build();
 *      FxTooltip.install(deleteButton, help);
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxTooltip extends Tooltip {

    /**
     * Creates an empty {@code FxTooltip}.
     */
    public FxTooltip() {
        super();
        getStyleClass().add("forja-tooltip");
    }

    /**
     * Creates an {@code FxTooltip} with the given text.
     *
     * @param text tooltip text
     */
    public FxTooltip(String text) {
        super(text);
        getStyleClass().add("forja-tooltip");
    }

    /**
     * Attaches the tooltip to the given control.
     *
     * @param control target
     * @param tooltip tooltip instance
     */
    public static void install(Control control, FxTooltip tooltip) {
        if (control != null) control.setTooltip(tooltip);
    }

    /**
     * Attaches a Forja-styled tooltip with the given text to any {@link Node}.
     *
     * @param node target
     * @param text tooltip text
     * @return the newly created tooltip (already installed)
     */
    public static FxTooltip install(Node node, String text) {
        FxTooltip t = new FxTooltip(text);
        Tooltip.install(node, t);
        return t;
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxTooltip}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxTooltip}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     *   <li>showDelayMs — {@code 400}</li>
     *   <li>showDurationMs — {@code 5000}</li>
     *   <li>hideDelayMs — {@code 200}</li>
     * </ul>
     */
    public static class Builder {

        private String text = "";
        private long showDelayMs = 400;
        private long showDurationMs = 5000;
        private long hideDelayMs = 200;

        public Builder text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder showDelayMs(long showDelayMs) { this.showDelayMs = Math.max(0, showDelayMs); return this; }
        public Builder showDurationMs(long showDurationMs) { this.showDurationMs = Math.max(0, showDurationMs); return this; }
        public Builder hideDelayMs(long hideDelayMs) { this.hideDelayMs = Math.max(0, hideDelayMs); return this; }

        public FxTooltip build() {
            FxTooltip t = new FxTooltip(text);
            t.setShowDelay(Duration.millis(showDelayMs));
            t.setShowDuration(Duration.millis(showDurationMs));
            t.setHideDelay(Duration.millis(hideDelayMs));
            return t;
        }
    }
}
