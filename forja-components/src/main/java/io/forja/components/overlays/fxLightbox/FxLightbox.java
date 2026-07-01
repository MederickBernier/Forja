package io.forja.components.overlays.fxLightbox;

import io.forja.components.buttonsAndActions.fxIconButton.FxIconButton;
import io.forja.components.dataDisplay.fxImage.FxImage;
import io.forja.components.overlays.OverlayHost;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A full-scrim media viewer for one or more image URLs.
 *
 * <p>{@code FxLightbox} is a {@link StackPane} scrim + a centered
 * {@link FxImage}. Navigation arrows step through the {@link #getImages}
 * list; a close (×) button dismisses. {@link javafx.scene.input.KeyCode#ESCAPE}
 * also closes; {@code LEFT}/{@code RIGHT} navigate.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxLightbox lb = FxLightbox.builder()
 *          .images("file:/a.png", "file:/b.png", "file:/c.png")
 *          .index(0)
 *          .build();
 *      lb.open(scene);
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxLightbox extends StackPane {

    private final Region scrim = new Region();
    private final FxImage image = new FxImage();
    private final FxIconButton prevBtn = FxIconButton.builder().icon("fth-chevron-left").build();
    private final FxIconButton nextBtn = FxIconButton.builder().icon("fth-chevron-right").build();
    private final FxIconButton closeBtn = FxIconButton.builder().icon("fth-x").build();
    private final HBox topBar = new HBox(closeBtn);
    private final BorderPane frame = new BorderPane();

    private final ObservableList<String> images = FXCollections.observableArrayList();
    private final IntegerProperty index = new SimpleIntegerProperty(this, "index", 0);

    /**
     * Creates an empty {@code FxLightbox}.
     */
    public FxLightbox() {
        super();
        getStyleClass().add("forja-lightbox");
        scrim.getStyleClass().add("forja-lightbox-scrim");

        image.setFitWidth(800);
        image.setFitHeight(600);
        image.setPreserveRatio(true);

        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.getStyleClass().add("forja-lightbox-top-bar");
        HBox navRow = new HBox(prevBtn, nextBtn);
        navRow.setAlignment(Pos.CENTER);
        navRow.setSpacing(16);
        navRow.getStyleClass().add("forja-lightbox-nav");

        frame.setPickOnBounds(false);
        frame.setTop(topBar);
        frame.setCenter(image);
        frame.setBottom(navRow);
        BorderPane.setAlignment(image, Pos.CENTER);

        getChildren().addAll(scrim, frame);

        closeBtn.setOnAction(e -> close());
        prevBtn.setOnAction(e -> prev());
        nextBtn.setOnAction(e -> next());
        scrim.setOnMouseClicked(e -> { close(); e.consume(); });

        images.addListener((javafx.collections.ListChangeListener<String>) c -> refresh());
        index.addListener((obs, o, v) -> refresh());
        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) close();
            else if (e.getCode() == KeyCode.LEFT) prev();
            else if (e.getCode() == KeyCode.RIGHT) next();
        });
    }

    private void refresh() {
        if (images.isEmpty()) { image.setUrl(""); return; }
        int i = Math.max(0, Math.min(getIndex(), images.size() - 1));
        image.setUrl(images.get(i));
        prevBtn.setVisible(images.size() > 1);
        nextBtn.setVisible(images.size() > 1);
        prevBtn.setManaged(images.size() > 1);
        nextBtn.setManaged(images.size() > 1);
    }

    /** Advances to the next image (wraps). */
    public void next() {
        if (images.isEmpty()) return;
        index.set((getIndex() + 1) % images.size());
    }

    /** Retreats to the previous image (wraps). */
    public void prev() {
        if (images.isEmpty()) return;
        int n = images.size();
        index.set((getIndex() - 1 + n) % n);
    }

    /** Shows the lightbox on the scene. */
    public void open(Scene scene) {
        OverlayHost.show(scene, this);
        requestFocus();
    }

    /** Removes the lightbox. */
    public void close() { OverlayHost.dismiss(this); }

    /** Returns the images list. */
    public ObservableList<String> getImages() { return images; }

    /** Returns the current-index property. */
    public IntegerProperty indexProperty() { return index; }
    /** Returns the current index. */
    public int getIndex() { return index.get(); }
    /** Sets the current index. */
    public void setIndex(int v) { index.set(v); }

    /** Returns the inner image view. */
    public FxImage getImage() { return image; }

    /** Returns the close button. */
    public FxIconButton getCloseButton() { return closeBtn; }

    /** Returns the previous button. */
    public FxIconButton getPrevButton() { return prevBtn; }

    /** Returns the next button. */
    public FxIconButton getNextButton() { return nextBtn; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxLightbox}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxLightbox}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>images — empty</li>
     *   <li>index — {@code 0}</li>
     * </ul>
     */
    public static class Builder {

        private List<String> images = Collections.emptyList();
        private int index = 0;

        public Builder images(List<String> images) { this.images = images == null ? Collections.<String>emptyList() : images; return this; }
        public Builder images(String... images) { return images(images == null ? Collections.<String>emptyList() : Arrays.asList(images)); }
        public Builder index(int index) { this.index = index; return this; }

        public FxLightbox build() {
            FxLightbox lb = new FxLightbox();
            lb.getImages().setAll(images);
            lb.setIndex(index);
            return lb;
        }
    }
}
