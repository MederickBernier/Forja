package io.forja.components.media.fxImageGallery;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.dataDisplay.fxImage.FxImage;
import io.forja.components.overlays.fxLightbox.FxLightbox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A grid of thumbnails that opens a full-screen {@link FxLightbox} on
 * click.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxImageGallery gallery = FxImageGallery.builder()
 *          .images("file:/a.jpg", "file:/b.jpg", "file:/c.jpg")
 *          .thumbnailSize(120)
 *          .build();
 *     }
 * </pre>
 *
 * @see FxLightbox
 * @see Builder
 */
public class FxImageGallery extends FlowPane {

    private final ObservableList<String> images = FXCollections.observableArrayList();
    private final FxLightbox lightbox = FxLightbox.builder().build();
    private double thumbnailSize = 120;

    /**
     * Creates an empty {@code FxImageGallery}.
     */
    public FxImageGallery() {
        super();
        getStyleClass().add("forja-image-gallery");
        setHgap(8);
        setVgap(8);
        setAlignment(Pos.TOP_LEFT);

        images.addListener((javafx.collections.ListChangeListener<String>) c -> { rebuild(); lightbox.getImages().setAll(images); });
    }

    private void rebuild() {
        getChildren().clear();
        for (int i = 0; i < images.size(); i++) {
            final int idx = i;
            FxImage thumb = FxImage.builder()
                    .url(images.get(i))
                    .fitWidth(thumbnailSize)
                    .fitHeight(thumbnailSize)
                    .preserveRatio(true)
                    .build();
            thumb.getStyleClass().add("forja-image-gallery-thumb");
            thumb.setOnMouseClicked(e -> openLightbox(idx));
            getChildren().add(thumb);
        }
    }

    /** Opens the lightbox at the given image index. */
    public void openLightbox(int index) {
        Scene scene = getScene();
        if (scene == null) return;
        lightbox.setIndex(Math.max(0, Math.min(index, images.size() - 1)));
        lightbox.open(scene);
    }

    /** Returns the images list. */
    public ObservableList<String> getImages() { return images; }
    /** Returns the wrapped lightbox. */
    public FxLightbox getLightbox() { return lightbox; }

    /** Sets the thumbnail size (px). */
    public void setThumbnailSize(double v) { this.thumbnailSize = Math.max(16, v); rebuild(); }
    /** Returns the thumbnail size. */
    public double getThumbnailSize() { return thumbnailSize; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxImageGallery}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxImageGallery}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>images — empty</li>
     *   <li>thumbnailSize — {@code 120}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxImageGallery, Builder> {

        private List<String> images = Collections.emptyList();
        private double thumbnailSize = 120;

        public Builder images(List<String> images) { this.images = images == null ? Collections.<String>emptyList() : images; return this; }
        public Builder images(String... images) { return images(images == null ? Collections.<String>emptyList() : Arrays.asList(images)); }
        public Builder thumbnailSize(double thumbnailSize) { this.thumbnailSize = Math.max(16, thumbnailSize); return this; }

        @Override
        public FxImageGallery build() {
            FxImageGallery g = new FxImageGallery();
            g.setThumbnailSize(thumbnailSize);
            g.getImages().setAll(images);
            applyBase(g);
            return g;
        }
    }
}
