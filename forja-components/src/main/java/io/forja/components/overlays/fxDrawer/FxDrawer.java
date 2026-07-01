package io.forja.components.overlays.fxDrawer;

import io.forja.components.overlays.OverlayHost;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * A side-anchored sliding panel rendered on the {@link OverlayHost} overlay
 * layer.
 *
 * <p>{@code FxDrawer} is a {@link StackPane} containing a translucent scrim
 * and a panel {@link Region} anchored to a {@link Side}. Opening slides the
 * panel in via a translate transition; closing slides it back out.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxDrawer settings = FxDrawer.builder()
 *          .content(settingsPanel)
 *          .side(Side.RIGHT)
 *          .size(320)
 *          .build();
 *      settings.open(scene);
 *     }
 * </pre>
 *
 * @see OverlayHost
 * @see Builder
 */
public class FxDrawer extends StackPane {

    private final Region scrim = new Region();
    private final StackPane panel = new StackPane();

    private final ObjectProperty<Side> side = new SimpleObjectProperty<>(this, "side", Side.RIGHT);
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>(this, "content");
    private final BooleanProperty open = new SimpleBooleanProperty(this, "open", false);
    private double size = 320;

    /**
     * Creates a closed right-side {@code FxDrawer}.
     */
    public FxDrawer() {
        super();
        getStyleClass().add("forja-drawer");
        setPickOnBounds(true);
        scrim.getStyleClass().add("forja-drawer-scrim");
        scrim.setOnMouseClicked(e -> { close(); e.consume(); });
        panel.getStyleClass().add("forja-drawer-panel");
        panel.setOnMouseClicked(e -> e.consume());
        getChildren().addAll(scrim, panel);

        side.addListener((obs, o, v) -> applySide());
        content.addListener((obs, o, v) -> {
            panel.getChildren().clear();
            if (v != null) panel.getChildren().add(v);
        });
        applySide();
        setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ESCAPE) close(); });
    }

    private void applySide() {
        StackPane.setAlignment(panel, alignmentFor(getSide()));
        boolean horizontal = getSide() == Side.LEFT || getSide() == Side.RIGHT;
        if (horizontal) { panel.setPrefWidth(size); panel.setMaxWidth(size); }
        else { panel.setPrefHeight(size); panel.setMaxHeight(size); }
    }

    private static Pos alignmentFor(Side s) {
        switch (s) {
            case LEFT:   return Pos.CENTER_LEFT;
            case RIGHT:  return Pos.CENTER_RIGHT;
            case TOP:    return Pos.TOP_CENTER;
            case BOTTOM: return Pos.BOTTOM_CENTER;
            default:     return Pos.CENTER_RIGHT;
        }
    }

    /** Shows the drawer on the scene and slides in. */
    public void open(Scene scene) {
        if (open.get()) return;
        OverlayHost.show(scene, this);
        panel.setTranslateX(offX());
        panel.setTranslateY(offY());
        Timeline t = new Timeline(new KeyFrame(Duration.millis(220),
                new KeyValue(panel.translateXProperty(), 0),
                new KeyValue(panel.translateYProperty(), 0)));
        t.play();
        open.set(true);
        requestFocus();
    }

    /** Slides the drawer out and removes it from the overlay. */
    public void close() {
        if (!open.get()) return;
        Timeline t = new Timeline(new KeyFrame(Duration.millis(180),
                new KeyValue(panel.translateXProperty(), offX()),
                new KeyValue(panel.translateYProperty(), offY())));
        t.setOnFinished(e -> OverlayHost.dismiss(this));
        t.play();
        open.set(false);
    }

    private double offX() {
        switch (getSide()) {
            case LEFT:  return -size;
            case RIGHT: return  size;
            default:    return 0;
        }
    }

    private double offY() {
        switch (getSide()) {
            case TOP:    return -size;
            case BOTTOM: return  size;
            default:     return 0;
        }
    }

    /** Returns the scrim node. */
    public Region getScrim() { return scrim; }

    /** Returns the panel node. */
    public StackPane getPanel() { return panel; }

    /** Returns the side property. */
    public ObjectProperty<Side> sideProperty() { return side; }
    /** Returns the current side. */
    public Side getSide() { return side.get(); }
    /** Sets the anchor side. */
    public void setSide(Side v) { side.set(v == null ? Side.RIGHT : v); }

    /** Returns the content property. */
    public ObjectProperty<Node> contentProperty() { return content; }
    /** Returns the content. */
    public Node getContent() { return content.get(); }
    /** Sets the content. */
    public void setContent(Node v) { content.set(v); }

    /** Returns whether the drawer is open. */
    public boolean isOpen() { return open.get(); }
    /** Returns the open property. */
    public BooleanProperty openProperty() { return open; }

    /** Returns the size (px, along the drawer's axis). */
    public double getSize() { return size; }
    /** Sets the drawer's axis size. */
    public void setSize(double v) { this.size = Math.max(0, v); applySide(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxDrawer}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxDrawer}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>side — {@link Side#RIGHT}</li>
     *   <li>size — {@code 320}</li>
     *   <li>content — {@code null}</li>
     * </ul>
     */
    public static class Builder {

        private Side side = Side.RIGHT;
        private double size = 320;
        private Node content;

        public Builder side(Side side) { this.side = side == null ? Side.RIGHT : side; return this; }
        public Builder size(double size) { this.size = size; return this; }
        public Builder content(Node content) { this.content = content; return this; }

        public FxDrawer build() {
            FxDrawer d = new FxDrawer();
            d.setSize(size);
            d.setSide(side);
            d.setContent(content);
            return d;
        }
    }
}
