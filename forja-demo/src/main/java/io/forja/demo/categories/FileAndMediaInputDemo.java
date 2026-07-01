package io.forja.demo.categories;

import io.forja.components.fileAndMediaInput.fxFileChooser.FxFileChooser;
import io.forja.components.fileAndMediaInput.fxFileDropzone.FxFileDropzone;
import io.forja.components.fileAndMediaInput.fxImageCropper.FxImageCropper;
import io.forja.components.fileAndMediaInput.fxImagePicker.FxImagePicker;
import javafx.scene.Node;
import javafx.scene.Scene;

public class FileAndMediaInputDemo implements CategoryDemo {

    @Override public String key() { return "file-and-media-input"; }
    @Override public String title() { return "File & Media Input"; }
    @Override public String icon() { return "fth-upload"; }

    @Override
    public Node build(Scene scene) {
        return Demo.category(title(),
                Demo.block("FxFileChooser", "Button that opens a native file dialog with extension filters.",
                        FxFileChooser.builder().buttonText("Choose file...").title("Open")
                                .extensionFilter("Images", "*.png", "*.jpg").build()),

                Demo.block("FxFileDropzone", "Drag-and-drop target with a prompt and icon.",
                        FxFileDropzone.builder().promptText("Drop files here").icon("fth-upload-cloud").build()),

                Demo.block("FxImageCropper", "Interactive crop overlay for a bundled image.",
                        // ponytail: no real file bundled
                        FxImageCropper.builder().aspectRatio(1.0).width(200).height(200).build()),

                Demo.block("FxImagePicker", "Pick and preview an image from disk.",
                        // ponytail: no real file bundled
                        FxImagePicker.builder().buttonText("Pick image").clearButtonText("Clear")
                                .previewWidth(160).previewHeight(160).build()));
    }
}
