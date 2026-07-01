package io.forja.components.overlays.fxHoverCard;

import io.forja.components.overlays.fxPopover.FxPopover;
import javafx.animation.PauseTransition;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * A hover-triggered rich card. Wraps an {@link FxPopover} whose show/hide
 * is driven by the target node's {@code hoverProperty} with a configurable
 * open/close delay.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxHoverCard hc = FxHoverCard.builder()
 *          .target(avatar)
 *          .content(profileCard)
 *          .openDelayMs(400)
 *          .closeDelayMs(200)
 *          .build();
 *      hc.install();
 *     }
 * </pre>
 *
 * @see FxPopover
 * @see Builder
 */
public class FxHoverCard {

    private final FxPopover popover;
    private Node target;
    private long openDelayMs = 400;
    private long closeDelayMs = 200;
    private final PauseTransition openTimer = new PauseTransition();
    private final PauseTransition closeTimer = new PauseTransition();

    private FxHoverCard(Node target, Node content, Side side) {
        this.target = target;
        this.popover = FxPopover.builder().anchor(target).content(content).side(side).autoHide(false).build();
        openTimer.setOnFinished(e -> popover.show());
        closeTimer.setOnFinished(e -> popover.hide());
    }

    /** Attaches hover-listeners to the target node. */
    public void install() {
        if (target == null) return;
        target.hoverProperty().addListener((obs, o, v) -> {
            closeTimer.stop();
            openTimer.stop();
            if (v) {
                openTimer.setDuration(Duration.millis(openDelayMs));
                openTimer.playFromStart();
            } else {
                closeTimer.setDuration(Duration.millis(closeDelayMs));
                closeTimer.playFromStart();
            }
        });
    }

    /** Returns the wrapped popover. */
    public FxPopover getPopover() { return popover; }

    /** Returns the target node. */
    public Node getTarget() { return target; }

    /** Returns the open delay (ms). */
    public long getOpenDelayMs() { return openDelayMs; }
    /** Sets the open delay (ms). */
    public void setOpenDelayMs(long v) { this.openDelayMs = Math.max(0, v); }

    /** Returns the close delay (ms). */
    public long getCloseDelayMs() { return closeDelayMs; }
    /** Sets the close delay (ms). */
    public void setCloseDelayMs(long v) { this.closeDelayMs = Math.max(0, v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxHoverCard}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxHoverCard}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>target / content — {@code null}</li>
     *   <li>side — {@link Side#BOTTOM}</li>
     *   <li>openDelayMs — {@code 400}</li>
     *   <li>closeDelayMs — {@code 200}</li>
     *   <li>autoInstall — {@code true}</li>
     * </ul>
     */
    public static class Builder {

        private Node target;
        private Node content;
        private Side side = Side.BOTTOM;
        private long openDelayMs = 400;
        private long closeDelayMs = 200;
        private boolean autoInstall = true;

        public Builder target(Node target) { this.target = target; return this; }
        public Builder content(Node content) { this.content = content; return this; }
        public Builder side(Side side) { this.side = side == null ? Side.BOTTOM : side; return this; }
        public Builder openDelayMs(long openDelayMs) { this.openDelayMs = Math.max(0, openDelayMs); return this; }
        public Builder closeDelayMs(long closeDelayMs) { this.closeDelayMs = Math.max(0, closeDelayMs); return this; }
        public Builder autoInstall(boolean autoInstall) { this.autoInstall = autoInstall; return this; }

        public FxHoverCard build() {
            FxHoverCard hc = new FxHoverCard(target, content, side);
            hc.setOpenDelayMs(openDelayMs);
            hc.setCloseDelayMs(closeDelayMs);
            if (autoInstall) hc.install();
            return hc;
        }
    }
}
