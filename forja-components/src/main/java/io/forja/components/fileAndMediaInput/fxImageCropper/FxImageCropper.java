package io.forja.components.fileAndMediaInput.fxImageCropper;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * A crop-selection overlay for a source {@link Image}.
 *
 * <p>{@code FxImageCropper} paints the source image scaled-to-fit into a
 * {@link Canvas}, with a semi-transparent scrim outside the current
 * selection rectangle. Drag anywhere on the canvas to define a new
 * selection; call {@link #exportCrop} to get a {@link WritableImage} of the
 * cropped region at source resolution.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxImageCropper cropper = FxImageCropper.builder()
 *          .image(new Image("file:/photo.jpg"))
 *          .aspectRatio(1.0)
 *          .build();
 *      WritableImage result = cropper.exportCrop();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxImageCropper extends Region {

    private final Canvas canvas = new Canvas(320, 240);
    private final ObjectProperty<Image> image = new SimpleObjectProperty<>(this, "image");
    private final ObjectProperty<Rectangle2D> selection = new SimpleObjectProperty<>(this, "selection");
    private double aspectRatio = 0; // 0 = free
    private double dragStartX, dragStartY;

    /**
     * Creates an empty {@code FxImageCropper}.
     */
    public FxImageCropper() {
        super();
        getStyleClass().add("forja-image-cropper");
        getChildren().add(canvas);
        setPrefSize(320, 240);
        widthProperty().addListener((obs, o, v) -> { canvas.setWidth(v.doubleValue()); redraw(); });
        heightProperty().addListener((obs, o, v) -> { canvas.setHeight(v.doubleValue()); redraw(); });
        image.addListener((obs, o, v) -> { selection.set(null); redraw(); });
        selection.addListener((obs, o, v) -> redraw());

        canvas.setOnMousePressed(e -> { dragStartX = e.getX(); dragStartY = e.getY(); selection.set(new Rectangle2D(e.getX(), e.getY(), 0, 0)); });
        canvas.setOnMouseDragged(e -> updateDrag(e.getX(), e.getY()));
    }

    private void updateDrag(double x, double y) {
        double sx = Math.min(dragStartX, x);
        double sy = Math.min(dragStartY, y);
        double w = Math.abs(x - dragStartX);
        double h = Math.abs(y - dragStartY);
        if (aspectRatio > 0) {
            if (w / aspectRatio > h) h = w / aspectRatio;
            else w = h * aspectRatio;
        }
        selection.set(new Rectangle2D(sx, sy, w, h));
    }

    private void redraw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        g.clearRect(0, 0, w, h);
        Image img = getImage();
        if (img == null || w <= 0 || h <= 0) return;

        double scale = Math.min(w / img.getWidth(), h / img.getHeight());
        double drawW = img.getWidth() * scale;
        double drawH = img.getHeight() * scale;
        double dx = (w - drawW) / 2;
        double dy = (h - drawH) / 2;
        g.drawImage(img, dx, dy, drawW, drawH);

        Rectangle2D sel = getSelection();
        if (sel != null && sel.getWidth() > 0 && sel.getHeight() > 0) {
            g.setFill(Color.color(0, 0, 0, 0.5));
            g.fillRect(0, 0, w, sel.getMinY());
            g.fillRect(0, sel.getMaxY(), w, h - sel.getMaxY());
            g.fillRect(0, sel.getMinY(), sel.getMinX(), sel.getHeight());
            g.fillRect(sel.getMaxX(), sel.getMinY(), w - sel.getMaxX(), sel.getHeight());
            g.setStroke(Color.web("#4F46E5"));
            g.setLineWidth(1.5);
            g.strokeRect(sel.getMinX(), sel.getMinY(), sel.getWidth(), sel.getHeight());
        }
    }

    /**
     * Exports the current selection as a new {@link WritableImage} at
     * source resolution.
     *
     * @return cropped image, or {@code null} if no image / no selection
     */
    public WritableImage exportCrop() {
        Image img = getImage();
        Rectangle2D sel = getSelection();
        if (img == null || sel == null || sel.getWidth() <= 0 || sel.getHeight() <= 0) return null;
        double cw = canvas.getWidth();
        double ch = canvas.getHeight();
        double scale = Math.min(cw / img.getWidth(), ch / img.getHeight());
        double drawW = img.getWidth() * scale;
        double drawH = img.getHeight() * scale;
        double dx = (cw - drawW) / 2;
        double dy = (ch - drawH) / 2;
        double srcX = Math.max(0, (sel.getMinX() - dx) / scale);
        double srcY = Math.max(0, (sel.getMinY() - dy) / scale);
        double srcW = Math.min(img.getWidth() - srcX, sel.getWidth() / scale);
        double srcH = Math.min(img.getHeight() - srcY, sel.getHeight() / scale);
        if (srcW <= 0 || srcH <= 0) return null;
        int iw = (int) Math.round(srcW);
        int ih = (int) Math.round(srcH);
        WritableImage out = new WritableImage(iw, ih);
        out.getPixelWriter().setPixels(0, 0, iw, ih, img.getPixelReader(), (int) srcX, (int) srcY);
        return out;
    }

    /** Returns the image property. */
    public ObjectProperty<Image> imageProperty() { return image; }
    /** Returns the current image. */
    public Image getImage() { return image.get(); }
    /** Sets the source image. */
    public void setImage(Image v) { image.set(v); }

    /** Returns the selection property. */
    public ObjectProperty<Rectangle2D> selectionProperty() { return selection; }
    /** Returns the current selection rectangle (canvas coords). */
    public Rectangle2D getSelection() { return selection.get(); }
    /** Sets the selection rectangle (canvas coords); {@code null} clears. */
    public void setSelection(Rectangle2D v) { selection.set(v); }

    /** Sets the fixed aspect ratio ({@code 0} = free). */
    public void setAspectRatio(double v) { this.aspectRatio = Math.max(0, v); }
    /** Returns the aspect ratio. */
    public double getAspectRatio() { return aspectRatio; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxImageCropper}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxImageCropper}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>image — {@code null}</li>
     *   <li>aspectRatio — {@code 0} (free)</li>
     *   <li>width / height — {@code 320} / {@code 240}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxImageCropper, Builder> {

        private Image image;
        private double aspectRatio = 0;
        private double width = 320;
        private double height = 240;

        public Builder image(Image image) { this.image = image; return this; }
        public Builder aspectRatio(double aspectRatio) { this.aspectRatio = Math.max(0, aspectRatio); return this; }
        public Builder width(double width) { this.width = width; return this; }
        public Builder height(double height) { this.height = height; return this; }

        @Override
        public FxImageCropper build() {
            FxImageCropper c = new FxImageCropper();
            c.setAspectRatio(aspectRatio);
            c.setPrefSize(width, height);
            c.setImage(image);
            applyBase(c);
            return c;
        }
    }
}
