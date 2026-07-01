package io.forja.components.overlays.fxFormDialog;

import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.overlays.fxDialog.FxDialog;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A dialog preset for form entry — wraps an {@link FxDialog} with a body
 * area, cancel button, and a save button whose enabled state is optionally
 * driven by a {@link Supplier} validator.
 *
 * <p>On save, the {@link OnSubmit} callback fires and the dialog closes; on
 * cancel, only closes.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxFormDialog dlg = FxFormDialog.builder()
 *          .title("Rename")
 *          .body(nameField)
 *          .saveText("Save")
 *          .canSave(() -> !nameField.getText().isEmpty())
 *          .onSubmit(() -> project.rename(nameField.getText()))
 *          .build();
 *      dlg.show(scene);
 *     }
 * </pre>
 *
 * @see FxDialog
 * @see Builder
 */
public class FxFormDialog {

    private final FxDialog dialog;
    private final FxButton cancelButton;
    private final FxButton saveButton;
    private OnSubmit onSubmit;
    private Supplier<Boolean> canSave;

    private FxFormDialog(String title, Node body, String cancelText, String saveText,
                         ButtonVariant saveVariant, OnSubmit onSubmit, Supplier<Boolean> canSave) {
        this.onSubmit = onSubmit;
        this.canSave = canSave;
        this.cancelButton = FxButton.builder()
                .text(cancelText).variant(ButtonVariant.SECONDARY)
                .build();
        this.saveButton = FxButton.builder()
                .text(saveText).variant(saveVariant)
                .build();
        this.dialog = FxDialog.builder()
                .title(title)
                .body(body == null ? new javafx.scene.layout.Region() : body)
                .footer(cancelButton, saveButton)
                .build();
        this.cancelButton.setOnAction(e -> dialog.close());
        this.saveButton.setOnAction(e -> submit());
        this.dialog.getStyleClass().add("forja-form-dialog");
        refreshSaveEnabled();
    }

    private void submit() {
        if (canSave != null && !Boolean.TRUE.equals(canSave.get())) return;
        if (onSubmit != null) onSubmit.accept();
        dialog.close();
    }

    /** Re-evaluates {@link #canSave} to enable/disable the save button. */
    public void refreshSaveEnabled() {
        boolean enabled = canSave == null || Boolean.TRUE.equals(canSave.get());
        saveButton.setDisable(!enabled);
    }

    /** Shows this dialog on the given scene. */
    public void show(Scene scene) { dialog.show(scene); }

    /** Closes this dialog. */
    public void close() { dialog.close(); }

    /** Returns the wrapped {@link FxDialog}. */
    public FxDialog getDialog() { return dialog; }

    /** Returns the cancel button. */
    public FxButton getCancelButton() { return cancelButton; }

    /** Returns the save button. */
    public FxButton getSaveButton() { return saveButton; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxFormDialog}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired on submit. */
    @FunctionalInterface
    public interface OnSubmit { void accept(); }

    /**
     * Fluent builder for constructing an {@link FxFormDialog}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>title — {@code "Form"}</li>
     *   <li>body — empty region</li>
     *   <li>cancelText — {@code "Cancel"}</li>
     *   <li>saveText — {@code "Save"}</li>
     *   <li>saveVariant — {@link ButtonVariant#PRIMARY}</li>
     *   <li>canSave — {@code null} (always enabled)</li>
     *   <li>onSubmit — none</li>
     * </ul>
     */
    public static class Builder {

        private String title = "Form";
        private Node body;
        private String cancelText = "Cancel";
        private String saveText = "Save";
        private ButtonVariant saveVariant = ButtonVariant.PRIMARY;
        private OnSubmit onSubmit;
        private Supplier<Boolean> canSave;

        public Builder title(String title) { this.title = title == null ? "" : title; return this; }
        public Builder body(Node body) { this.body = body; return this; }
        public Builder cancelText(String cancelText) { this.cancelText = cancelText == null ? "Cancel" : cancelText; return this; }
        public Builder saveText(String saveText) { this.saveText = saveText == null ? "Save" : saveText; return this; }
        public Builder saveVariant(ButtonVariant saveVariant) { this.saveVariant = saveVariant == null ? ButtonVariant.PRIMARY : saveVariant; return this; }
        public Builder canSave(Supplier<Boolean> canSave) { this.canSave = canSave; return this; }
        public Builder onSubmit(OnSubmit onSubmit) { this.onSubmit = onSubmit; return this; }

        public Consumer<Boolean> onSubmitConsumer(Consumer<Boolean> c) { return c; }

        public FxFormDialog build() {
            return new FxFormDialog(title, body, cancelText, saveText, saveVariant, onSubmit, canSave);
        }
    }
}
