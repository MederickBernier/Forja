package io.forja.components.fileAndMediaInput.fxFileDropzone;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A drag-and-drop target for files — a dashed-border {@link VBox} with an
 * icon + prompt. Toggles a {@code :dragging} pseudo-class while a valid
 * drop is hovering.
 *
 * <p>The {@link OnDrop} callback fires when the user drops one or more files
 * on the zone.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxFileDropzone dz = FxFileDropzone.builder()
 *          .promptText("Drop images here")
 *          .icon("fth-upload-cloud")
 *          .onDrop(files -> attach(files))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxFileDropzone extends VBox {

    private static final PseudoClass DRAGGING_PC = PseudoClass.getPseudoClass("dragging");

    private final FxIcon icon = new FxIcon("fth-upload-cloud");
    private final FxLabel promptLabel = new FxLabel("Drop files here", LabelVariant.BODY);
    private final StringProperty promptText = new SimpleStringProperty(this, "promptText", "Drop files here");
    private final BooleanProperty dragging = new SimpleBooleanProperty(this, "dragging", false);
    private OnDrop onDrop;

    /**
     * Creates an empty {@code FxFileDropzone}.
     */
    public FxFileDropzone() {
        super();
        getStyleClass().add("forja-file-dropzone");
        setAlignment(Pos.CENTER);
        setSpacing(8);

        icon.getStyleClass().add("forja-file-dropzone-icon");
        promptLabel.getStyleClass().add("forja-file-dropzone-prompt");
        promptLabel.setMuted(true);
        getChildren().addAll(icon, promptLabel);

        promptText.addListener((obs, o, v) -> promptLabel.setText(v == null ? "" : v));
        dragging.addListener((obs, o, v) -> pseudoClassStateChanged(DRAGGING_PC, v));

        setOnDragOver((DragEvent e) -> {
            Dragboard db = e.getDragboard();
            if (db.hasFiles()) {
                e.acceptTransferModes(TransferMode.COPY);
                dragging.set(true);
            }
            e.consume();
        });
        setOnDragExited(e -> { dragging.set(false); e.consume(); });
        setOnDragDropped((DragEvent e) -> {
            Dragboard db = e.getDragboard();
            boolean ok = false;
            if (db.hasFiles()) {
                List<File> files = new ArrayList<>(db.getFiles());
                if (onDrop != null) onDrop.accept(files);
                ok = true;
            }
            e.setDropCompleted(ok);
            dragging.set(false);
            e.consume();
        });
    }

    /** Returns the icon. */
    public FxIcon getIcon() { return icon; }

    /** Returns the prompt label. */
    public FxLabel getPromptLabel() { return promptLabel; }

    /** Returns the prompt-text property. */
    public StringProperty promptTextProperty() { return promptText; }

    /** Returns the current prompt text. */
    public String getPromptText() { return promptText.get(); }

    /** Sets the prompt text. */
    public void setPromptText(String v) { promptText.set(v == null ? "" : v); }

    /** Returns the dragging property. */
    public BooleanProperty draggingProperty() { return dragging; }

    /** Returns whether a valid drop is currently hovering. */
    public boolean isDragging() { return dragging.get(); }

    /** Sets the on-drop callback. */
    public void setOnDrop(OnDrop onDrop) { this.onDrop = onDrop; }

    /** Test/manual entry — invokes the drop callback with the given files. */
    public void fireDrop(List<File> files) {
        if (onDrop != null) onDrop.accept(files == null ? Collections.<File>emptyList() : files);
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxFileDropzone}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when files are dropped. */
    @FunctionalInterface
    public interface OnDrop { void accept(List<File> files); }

    /**
     * Fluent builder for constructing an {@link FxFileDropzone}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>promptText — {@code "Drop files here"}</li>
     *   <li>icon — {@code "fth-upload-cloud"}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxFileDropzone, Builder> {

        private String promptText = "Drop files here";
        private String iconLiteral = "fth-upload-cloud";
        private OnDrop onDrop;

        public Builder promptText(String promptText) { this.promptText = promptText == null ? "" : promptText; return this; }
        public Builder icon(String iconLiteral) { this.iconLiteral = iconLiteral == null ? "" : iconLiteral; return this; }
        public Builder onDrop(OnDrop onDrop) { this.onDrop = onDrop; return this; }

        @Override
        public FxFileDropzone build() {
            FxFileDropzone dz = new FxFileDropzone();
            dz.setPromptText(promptText);
            dz.getIcon().setIconLiteral(iconLiteral);
            dz.setOnDrop(onDrop);
            applyBase(dz);
            return dz;
        }
    }
}
