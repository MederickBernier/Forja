package io.forja.components.overlays.fxBottomSheet;

import io.forja.components.overlays.OverlayHost;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * A bottom-anchored sliding sheet. Slides up from the scene bottom on
 * {@link #open}, slides back down on {@link #close}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxBottomSheet sheet = FxBottomSheet.builder()
 *          .content(sheetBody)
 *          .height(280)
 *          .build();
 *      sheet.open(scene);
 *     }
 * </pre>
 *
 * @see OverlayHost
 * @see Builder
 */
public class FxBottomSheet extends StackPane {

    private final Region scrim = new Region();
    private final VBox panel = new VBox();
    private final Region grabber = new Region();

    private final ObjectProperty<Node> content = new SimpleObjectProperty<>(this, "content");
    private final BooleanProperty open = new SimpleBooleanProperty(this, "open", false);
    private double height = 280;

    /**
     * Creates an empty closed {@code FxBottomSheet}.
     */
    public FxBottomSheet() {
        super();
        getStyleClass().add("forja-bottom-sheet");
        setPickOnBounds(true);
        scrim.getStyleClass().add("forja-bottom-sheet-scrim");
        scrim.setOnMouseClicked(e -> { close(); e.consume(); });

        grabber.getStyleClass().add("forja-bottom-sheet-grabber");
        grabber.setPrefSize(40, 4);
        grabber.setMaxSize(40, 4);

        panel.getStyleClass().add("forja-bottom-sheet-panel");
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setSpacing(8);
        panel.getChildren().add(grabber);
        panel.setOnMouseClicked(e -> e.consume());
        StackPane.setAlignment(panel, Pos.BOTTOM_CENTER);
        panel.setPrefHeight(height);
        panel.setMaxHeight(height);

        getChildren().addAll(scrim, panel);

        content.addListener((obs, o, v) -> {
            panel.getChildren().setAll(grabber);
            if (v != null) panel.getChildren().add(v);
        });
        setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ESCAPE) close(); });
    }

    /** Slides the sheet up onto the scene. */
    public void open(Scene scene) {
        if (open.get()) return;
        OverlayHost.show(scene, this);
        panel.setTranslateY(height);
        Timeline t = new Timeline(new KeyFrame(Duration.millis(240),
                new KeyValue(panel.translateYProperty(), 0)));
        t.play();
        open.set(true);
        requestFocus();
    }

    /** Slides the sheet down and removes it. */
    public void close() {
        if (!open.get()) return;
        Timeline t = new Timeline(new KeyFrame(Duration.millis(200),
                new KeyValue(panel.translateYProperty(), height)));
        t.setOnFinished(e -> OverlayHost.dismiss(this));
        t.play();
        open.set(false);
    }

    /** Returns the scrim. */
    public Region getScrim() { return scrim; }

    /** Returns the panel. */
    public VBox getPanel() { return panel; }

    /** Returns the grabber handle. */
    public Region getGrabber() { return grabber; }

    /** Returns the content property. */
    public ObjectProperty<Node> contentProperty() { return content; }
    /** Returns the content. */
    public Node getContent() { return content.get(); }
    /** Sets the content. */
    public void setContent(Node v) { content.set(v); }

    /** Returns the open property. */
    public BooleanProperty openProperty() { return open; }
    /** Returns whether the sheet is open. */
    public boolean isOpen() { return open.get(); }

    /** Returns the height. */
    public double getSheetHeight() { return height; }

    /** Sets the height (px). */
    public void setSheetHeight(double v) {
        this.height = Math.max(0, v);
        panel.setPrefHeight(height);
        panel.setMaxHeight(height);
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxBottomSheet}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxBottomSheet}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>content — {@code null}</li>
     *   <li>height — {@code 280}</li>
     * </ul>
     */
    public static class Builder {

        private Node content;
        private double height = 280;

        public Builder content(Node content) { this.content = content; return this; }
        public Builder height(double height) { this.height = height; return this; }

        public FxBottomSheet build() {
            FxBottomSheet s = new FxBottomSheet();
            s.setSheetHeight(height);
            s.setContent(content);
            return s;
        }
    }
}
