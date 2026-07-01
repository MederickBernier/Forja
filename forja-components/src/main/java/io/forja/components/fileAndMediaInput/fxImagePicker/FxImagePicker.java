package io.forja.components.fileAndMediaInput.fxImagePicker;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.dataDisplay.fxImage.FxImage;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

/**
 * An image picker — a preview {@link FxImage} + a "Choose image" button that
 * opens a native {@link FileChooser} filtered to image extensions.
 *
 * <p>Selecting a file loads it into the preview and updates
 * {@link #valueProperty()}. Clearing (via {@link #clear()}) removes both.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxImagePicker picker = FxImagePicker.builder()
 *          .previewWidth(200)
 *          .previewHeight(200)
 *          .buttonText("Upload avatar")
 *          .onChoose(file -> viewModel.setAvatar(file))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxImagePicker extends VBox {

    private final FxImage preview = new FxImage();
    private final FxButton chooseButton = FxButton.builder().text("Choose image…").variant(ButtonVariant.SECONDARY).build();
    private final FxButton clearButton = FxButton.builder().text("Clear").variant(ButtonVariant.GHOST).build();
    private final HBox buttonRow = new HBox(8, chooseButton, clearButton);

    private final ObjectProperty<File> value = new SimpleObjectProperty<>(this, "value");
    private OnChoose onChoose;

    /**
     * Creates an empty {@code FxImagePicker}.
     */
    public FxImagePicker() {
        super();
        getStyleClass().add("forja-image-picker");
        setSpacing(8);
        setAlignment(Pos.TOP_LEFT);

        preview.getStyleClass().add("forja-image-picker-preview");
        preview.setFallbackIcon("fth-image");
        preview.setFitWidth(160);
        preview.setFitHeight(160);

        buttonRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(chooseButton, Priority.NEVER);

        clearButton.setOnAction(e -> clear());
        chooseButton.setOnAction(e -> openChooser());
        value.addListener((obs, o, v) -> refreshPreview());

        getChildren().addAll(preview, buttonRow);
        refreshPreview();
    }

    private void openChooser() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose image");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));
        Window win = getScene() == null ? null : getScene().getWindow();
        File picked = fc.showOpenDialog(win);
        if (picked != null) {
            value.set(picked);
            if (onChoose != null) onChoose.accept(picked);
        }
    }

    private void refreshPreview() {
        File f = getValue();
        preview.setUrl(f == null ? "" : f.toURI().toString());
    }

    /** Clears the current selection. */
    public void clear() {
        value.set(null);
    }

    /** Returns the preview node. */
    public FxImage getPreview() { return preview; }

    /** Returns the choose button. */
    public FxButton getChooseButton() { return chooseButton; }

    /** Returns the clear button. */
    public FxButton getClearButton() { return clearButton; }

    /** Returns the value property. */
    public ObjectProperty<File> valueProperty() { return value; }

    /** Returns the current file. */
    public File getValue() { return value.get(); }

    /** Sets the current file. */
    public void setValue(File v) { value.set(v); }

    /** Sets the on-choose callback. */
    public void setOnChoose(OnChoose onChoose) { this.onChoose = onChoose; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxImagePicker}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when a file is chosen. */
    @FunctionalInterface
    public interface OnChoose { void accept(File file); }

    /**
     * Fluent builder for constructing an {@link FxImagePicker}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>previewWidth / previewHeight — {@code 160}</li>
     *   <li>buttonText — {@code "Choose image…"}</li>
     *   <li>clearButtonText — {@code "Clear"}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxImagePicker, Builder> {

        private double previewWidth = 160;
        private double previewHeight = 160;
        private String buttonText = "Choose image…";
        private String clearButtonText = "Clear";
        private OnChoose onChoose;

        public Builder previewWidth(double previewWidth) { this.previewWidth = previewWidth; return this; }
        public Builder previewHeight(double previewHeight) { this.previewHeight = previewHeight; return this; }
        public Builder buttonText(String buttonText) { this.buttonText = buttonText == null ? "" : buttonText; return this; }
        public Builder clearButtonText(String clearButtonText) { this.clearButtonText = clearButtonText == null ? "" : clearButtonText; return this; }
        public Builder onChoose(OnChoose onChoose) { this.onChoose = onChoose; return this; }

        @Override
        public FxImagePicker build() {
            FxImagePicker p = new FxImagePicker();
            p.getPreview().setFitWidth(previewWidth);
            p.getPreview().setFitHeight(previewHeight);
            p.getChooseButton().setText(buttonText);
            p.getClearButton().setText(clearButtonText);
            p.setOnChoose(onChoose);
            applyBase(p);
            return p;
        }
    }
}
