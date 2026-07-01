package io.forja.components.fileAndMediaInput.fxFileChooser;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A file-chooser control — a button + label showing the chosen file (or
 * {@code "N files"} when multiple).
 *
 * <p>Clicking the button opens the native {@link FileChooser}. The chosen
 * result is exposed via {@link #valueProperty()} (last-selected file) and
 * {@link #getFiles()} (full selection when multi-select).
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxFileChooser fc = FxFileChooser.builder()
 *          .buttonText("Attach file")
 *          .title("Pick attachment")
 *          .extensionFilter("Images", "*.png", "*.jpg")
 *          .multiSelect(false)
 *          .onChoose(f -> attach(f))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxFileChooser extends HBox {

    private final FxButton button = FxButton.builder().text("Choose file…").variant(ButtonVariant.SECONDARY).build();
    private final FxLabel label = new FxLabel("", LabelVariant.SMALL);

    private final ObjectProperty<File> value = new SimpleObjectProperty<>(this, "value");
    private final ObservableList<File> files = FXCollections.observableArrayList();
    private final StringProperty title = new SimpleStringProperty(this, "title", "Open");
    private boolean multiSelect = false;
    private FileChooser.ExtensionFilter extensionFilter;
    private OnChoose onChoose;

    /**
     * Creates an empty {@code FxFileChooser}.
     */
    public FxFileChooser() {
        super();
        getStyleClass().add("forja-file-chooser");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(10);
        label.setMuted(true);
        HBox.setHgrow(label, Priority.ALWAYS);
        getChildren().addAll(button, label);

        button.setOnAction(e -> openChooser());
        value.addListener((obs, o, v) -> refreshLabel());
        files.addListener((javafx.collections.ListChangeListener<File>) c -> refreshLabel());
    }

    private void openChooser() {
        FileChooser fc = new FileChooser();
        fc.setTitle(getTitle());
        if (extensionFilter != null) fc.getExtensionFilters().add(extensionFilter);
        Window win = getScene() == null ? null : getScene().getWindow();
        if (multiSelect) {
            List<File> picked = fc.showOpenMultipleDialog(win);
            if (picked != null && !picked.isEmpty()) {
                files.setAll(picked);
                value.set(picked.get(picked.size() - 1));
                if (onChoose != null) onChoose.accept(picked);
            }
        } else {
            File picked = fc.showOpenDialog(win);
            if (picked != null) {
                files.setAll(Collections.singletonList(picked));
                value.set(picked);
                if (onChoose != null) onChoose.accept(Collections.singletonList(picked));
            }
        }
    }

    private void refreshLabel() {
        if (files.isEmpty()) label.setText("No file selected");
        else if (files.size() == 1) label.setText(files.get(0).getName());
        else label.setText(files.size() + " files");
    }

    /** Returns the button. */
    public FxButton getButton() { return button; }

    /** Returns the summary label. */
    public FxLabel getLabel() { return label; }

    /** Returns the last-chosen file property. */
    public ObjectProperty<File> valueProperty() { return value; }

    /** Returns the last-chosen file. */
    public File getValue() { return value.get(); }

    /** Sets the last-chosen file. */
    public void setValue(File v) { value.set(v); }

    /** Returns the full chosen-files list. */
    public ObservableList<File> getFiles() { return files; }

    /** Returns the chooser-title property. */
    public StringProperty titleProperty() { return title; }

    /** Returns the chooser title. */
    public String getTitle() { return title.get(); }

    /** Sets the chooser title. */
    public void setTitle(String v) { title.set(v == null ? "Open" : v); }

    /** Sets multi-select mode. */
    public void setMultiSelect(boolean v) { this.multiSelect = v; }

    /** Sets the extension filter (single filter for simplicity). */
    public void setExtensionFilter(FileChooser.ExtensionFilter filter) { this.extensionFilter = filter; }

    /** Sets the on-choose callback. */
    public void setOnChoose(OnChoose onChoose) { this.onChoose = onChoose; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxFileChooser}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when files are chosen. */
    @FunctionalInterface
    public interface OnChoose { void accept(List<File> files); }

    /**
     * Fluent builder for constructing an {@link FxFileChooser}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>buttonText — {@code "Choose file…"}</li>
     *   <li>title — {@code "Open"}</li>
     *   <li>multiSelect — {@code false}</li>
     *   <li>extensionFilter — none</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxFileChooser, Builder> {

        private String buttonText = "Choose file…";
        private String title = "Open";
        private boolean multiSelect = false;
        private FileChooser.ExtensionFilter extensionFilter;
        private OnChoose onChoose;

        public Builder buttonText(String buttonText) { this.buttonText = buttonText == null ? "" : buttonText; return this; }
        public Builder title(String title) { this.title = title == null ? "Open" : title; return this; }
        public Builder multiSelect(boolean multiSelect) { this.multiSelect = multiSelect; return this; }
        public Builder extensionFilter(String description, String... globs) {
            this.extensionFilter = new FileChooser.ExtensionFilter(description == null ? "Files" : description,
                    globs == null ? Collections.<String>emptyList() : Arrays.asList(globs));
            return this;
        }
        public Builder onChoose(OnChoose onChoose) { this.onChoose = onChoose; return this; }

        @Override
        public FxFileChooser build() {
            FxFileChooser fc = new FxFileChooser();
            fc.getButton().setText(buttonText);
            fc.setTitle(title);
            fc.setMultiSelect(multiSelect);
            fc.setExtensionFilter(extensionFilter);
            fc.setOnChoose(onChoose);
            applyBase(fc);
            fc.refreshLabel();
            return fc;
        }
    }
}
