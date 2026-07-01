package io.forja.components.dataDisplay.fxImage;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.feedbackAndStatus.fxProgressCircle.FxProgressCircle;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * A styled {@link ImageView} wrapper that shows a spinner while the source
 * image loads and swaps to a fallback icon if loading fails.
 *
 * <p>{@code FxImage} is a {@link StackPane} with three layers: the
 * {@link ImageView}, a centered {@link FxProgressCircle} shown while
 * loading, and a fallback {@link FxIcon} shown on error.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxImage avatar = FxImage.builder()
 *          .url("https://…/avatar.png")
 *          .fitWidth(64).fitHeight(64)
 *          .fallbackIcon("fth-user")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxImage extends StackPane {

    private final ImageView view = new ImageView();
    private final FxProgressCircle spinner = new FxProgressCircle();
    private final FxIcon fallback = new FxIcon("fth-image");

    private final StringProperty url = new SimpleStringProperty(this, "url", "");
    private final DoubleProperty fitWidth = new SimpleDoubleProperty(this, "fitWidth", 0);
    private final DoubleProperty fitHeight = new SimpleDoubleProperty(this, "fitHeight", 0);
    private final BooleanProperty preserveRatio = new SimpleBooleanProperty(this, "preserveRatio", true);
    private final ObjectProperty<Image> image = new SimpleObjectProperty<>(this, "image");

    /**
     * Creates an empty {@code FxImage} with no source URL.
     */
    public FxImage() {
        super();
        getStyleClass().add("forja-image");
        setAlignment(Pos.CENTER);

        spinner.setProgress(javafx.scene.control.ProgressIndicator.INDETERMINATE_PROGRESS);
        spinner.setPrefWidth(24); spinner.setPrefHeight(24);
        spinner.setMinWidth(24);  spinner.setMinHeight(24);
        spinner.setMaxWidth(24);  spinner.setMaxHeight(24);
        spinner.setVisible(false);
        spinner.setManaged(false);

        fallback.getStyleClass().add("forja-image-fallback");
        fallback.setVisible(false);
        fallback.setManaged(false);

        getChildren().addAll(view, spinner, fallback);

        url.addListener((obs, o, v) -> loadFromUrl(v));
        fitWidth.addListener((obs, o, v) -> view.setFitWidth(v.doubleValue()));
        fitHeight.addListener((obs, o, v) -> view.setFitHeight(v.doubleValue()));
        preserveRatio.addListener((obs, o, v) -> view.setPreserveRatio(v));
        image.addListener((obs, o, v) -> view.setImage(v));
    }

    private void loadFromUrl(String u) {
        if (u == null || u.isEmpty()) {
            view.setImage(null);
            showLoading(false);
            showFallback(false);
            return;
        }
        Image img;
        try {
            img = new Image(u, true);
        } catch (Exception ex) {
            showLoading(false);
            showFallback(true);
            return;
        }
        image.set(img);
        showLoading(true);
        showFallback(false);
        img.progressProperty().addListener((obs, o, v) -> {
            if (v.doubleValue() >= 1.0) showLoading(false);
        });
        img.errorProperty().addListener((obs, o, v) -> {
            if (v) { showLoading(false); showFallback(true); }
        });
    }

    private void showLoading(boolean v) {
        spinner.setVisible(v);
        spinner.setManaged(v);
    }

    private void showFallback(boolean v) {
        fallback.setVisible(v);
        fallback.setManaged(v);
    }

    /** Returns the underlying {@link ImageView} for advanced binding. */
    public ImageView getImageView() { return view; }

    /** Returns the spinner shown while loading. */
    public FxProgressCircle getSpinner() { return spinner; }

    /** Returns the fallback-icon node. */
    public FxIcon getFallbackIcon() { return fallback; }

    /** Sets the fallback icon literal (Ikonli glyph). */
    public void setFallbackIcon(String literal) { fallback.setIconLiteral(literal); }

    /** Returns the URL property. */
    public StringProperty urlProperty() { return url; }

    /** Returns the current URL. */
    public String getUrl() { return url.get(); }

    /** Sets the image URL. */
    public void setUrl(String v) { url.set(v == null ? "" : v); }

    /** Returns the fit-width property. */
    public DoubleProperty fitWidthProperty() { return fitWidth; }

    /** Returns the current fit width. */
    public double getFitWidth() { return fitWidth.get(); }

    /** Sets the fit width. */
    public void setFitWidth(double v) { fitWidth.set(v); }

    /** Returns the fit-height property. */
    public DoubleProperty fitHeightProperty() { return fitHeight; }

    /** Returns the current fit height. */
    public double getFitHeight() { return fitHeight.get(); }

    /** Sets the fit height. */
    public void setFitHeight(double v) { fitHeight.set(v); }

    /** Returns the preserve-ratio property. */
    public BooleanProperty preserveRatioProperty() { return preserveRatio; }

    /** Returns whether the image preserves its aspect ratio. */
    public boolean isPreserveRatio() { return preserveRatio.get(); }

    /** Sets whether the image preserves its aspect ratio. */
    public void setPreserveRatio(boolean v) { preserveRatio.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxImage}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxImage}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>url — empty</li>
     *   <li>fitWidth / fitHeight — {@code 0} (unconstrained)</li>
     *   <li>preserveRatio — {@code true}</li>
     *   <li>fallbackIcon — {@code "fth-image"}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxImage, Builder> {

        private String url = "";
        private double fitWidth = 0;
        private double fitHeight = 0;
        private boolean preserveRatio = true;
        private String fallbackIcon = "fth-image";

        public Builder url(String url) { this.url = url == null ? "" : url; return this; }
        public Builder fitWidth(double fitWidth) { this.fitWidth = fitWidth; return this; }
        public Builder fitHeight(double fitHeight) { this.fitHeight = fitHeight; return this; }
        public Builder preserveRatio(boolean preserveRatio) { this.preserveRatio = preserveRatio; return this; }
        public Builder fallbackIcon(String fallbackIcon) { this.fallbackIcon = fallbackIcon == null ? "fth-image" : fallbackIcon; return this; }

        @Override
        public FxImage build() {
            FxImage img = new FxImage();
            img.setFitWidth(fitWidth);
            img.setFitHeight(fitHeight);
            img.setPreserveRatio(preserveRatio);
            img.setFallbackIcon(fallbackIcon);
            img.setUrl(url);
            applyBase(img);
            return img;
        }
    }
}
