package io.forja.components.overlays.fxPopover;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

/**
 * A floating panel anchored to a target {@link Node}.
 *
 * <p>{@code FxPopover} wraps a {@link Popup} sized to arbitrary content and
 * positioned on any {@link Side} of its anchor. Auto-hides on outside click
 * by default.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxPopover pop = FxPopover.builder()
 *          .anchor(button)
 *          .content(menuBox)
 *          .side(Side.BOTTOM)
 *          .build();
 *      pop.show();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxPopover {

    private final Popup popup = new Popup();
    private final VBox panel = new VBox();

    private final ObjectProperty<Node> anchor = new SimpleObjectProperty<>(this, "anchor");
    private final ObjectProperty<Side> side = new SimpleObjectProperty<>(this, "side", Side.BOTTOM);
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>(this, "content");
    private final BooleanProperty showing = new SimpleBooleanProperty(this, "showing", false);

    /**
     * Creates an empty {@code FxPopover}.
     */
    public FxPopover() {
        panel.getStyleClass().add("forja-popover");
        popup.setAutoHide(true);
        popup.getContent().add(panel);
        popup.setOnHidden(e -> showing.set(false));
        content.addListener((obs, o, v) -> {
            panel.getChildren().clear();
            if (v != null) panel.getChildren().add(v);
        });
    }

    /** Shows the popover anchored to {@link #getAnchor}. */
    public void show() {
        Node a = getAnchor();
        if (a == null || a.getScene() == null || a.getScene().getWindow() == null) return;
        Bounds b = a.localToScreen(a.getBoundsInLocal());
        if (b == null) return;
        double x = b.getMinX();
        double y = b.getMaxY();
        switch (getSide()) {
            case TOP:    y = b.getMinY() - panel.getHeight(); break;
            case LEFT:   x = b.getMinX() - panel.getWidth(); y = b.getMinY(); break;
            case RIGHT:  x = b.getMaxX(); y = b.getMinY(); break;
            case BOTTOM:
            default:     break;
        }
        popup.show(a.getScene().getWindow(), x, y);
        showing.set(true);
    }

    /** Hides the popover. */
    public void hide() { popup.hide(); showing.set(false); }

    /** Toggles show/hide. */
    public void toggle() { if (isShowing()) hide(); else show(); }

    /** Returns the underlying {@link Popup}. */
    public Popup getPopup() { return popup; }

    /** Returns the content-wrapper panel. */
    public VBox getPanel() { return panel; }

    /** Returns the anchor property. */
    public ObjectProperty<Node> anchorProperty() { return anchor; }
    /** Returns the anchor node. */
    public Node getAnchor() { return anchor.get(); }
    /** Sets the anchor node. */
    public void setAnchor(Node v) { anchor.set(v); }

    /** Returns the side property. */
    public ObjectProperty<Side> sideProperty() { return side; }
    /** Returns the current side. */
    public Side getSide() { return side.get(); }
    /** Sets the side the popover appears on. */
    public void setSide(Side v) { side.set(v == null ? Side.BOTTOM : v); }

    /** Returns the content property. */
    public ObjectProperty<Node> contentProperty() { return content; }
    /** Returns the content. */
    public Node getContent() { return content.get(); }
    /** Sets the content node. */
    public void setContent(Node v) { content.set(v); }

    /** Returns the showing property. */
    public BooleanProperty showingProperty() { return showing; }
    /** Returns whether the popover is showing. */
    public boolean isShowing() { return showing.get(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxPopover}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxPopover}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>anchor / content — {@code null}</li>
     *   <li>side — {@link Side#BOTTOM}</li>
     *   <li>autoHide — {@code true}</li>
     * </ul>
     */
    public static class Builder {

        private Node anchor;
        private Node content;
        private Side side = Side.BOTTOM;
        private boolean autoHide = true;

        public Builder anchor(Node anchor) { this.anchor = anchor; return this; }
        public Builder content(Node content) { this.content = content; return this; }
        public Builder side(Side side) { this.side = side == null ? Side.BOTTOM : side; return this; }
        public Builder autoHide(boolean autoHide) { this.autoHide = autoHide; return this; }

        /** Builds and returns the {@link FxPopover}. */
        public FxPopover build() {
            FxPopover p = new FxPopover();
            p.setAnchor(anchor);
            p.setSide(side);
            p.setContent(content);
            p.getPopup().setAutoHide(autoHide);
            return p;
        }
    }
}
