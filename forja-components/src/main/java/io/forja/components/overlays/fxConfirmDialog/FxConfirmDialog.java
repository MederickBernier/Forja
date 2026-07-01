package io.forja.components.overlays.fxConfirmDialog;

import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.overlays.fxDialog.FxDialog;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.scene.Scene;

import java.util.function.Consumer;

/**
 * A confirmation dialog — an {@link FxDialog} preset with title, message,
 * cancel + confirm buttons, and a boolean callback.
 *
 * <p>Static usage:</p>
 * <pre>
 *     {@code
 *      FxConfirmDialog.ask(scene, "Delete project?",
 *          "This action cannot be undone.",
 *          confirmed -> { if (confirmed) doDelete(); });
 *     }
 * </pre>
 *
 * <p>Builder usage — for custom labels or variant tweaks:</p>
 * <pre>
 *     {@code
 *      FxConfirmDialog d = FxConfirmDialog.builder()
 *          .title("Delete project?")
 *          .message("This action cannot be undone.")
 *          .cancelText("Keep it")
 *          .confirmText("Delete")
 *          .confirmVariant(ButtonVariant.DANGER)
 *          .onResult(confirmed -> ...)
 *          .build();
 *      d.show(scene);
 *     }
 * </pre>
 *
 * @see FxDialog
 * @see Builder
 */
public class FxConfirmDialog {

    private final FxDialog dialog;
    private final FxButton cancelButton;
    private final FxButton confirmButton;
    private final FxLabel messageLabel;
    private Consumer<Boolean> onResult;
    private boolean result = false;

    private FxConfirmDialog(String title, String message, String cancelText, String confirmText,
                            ButtonVariant confirmVariant, Consumer<Boolean> onResult) {
        this.onResult = onResult;
        this.messageLabel = FxLabel.builder().text(message).variant(LabelVariant.BODY).build();
        this.cancelButton = FxButton.builder()
                .text(cancelText)
                .variant(ButtonVariant.SECONDARY)
                .onAction(e -> resolveAndClose(false))
                .build();
        this.confirmButton = FxButton.builder()
                .text(confirmText)
                .variant(confirmVariant)
                .onAction(e -> resolveAndClose(true))
                .build();

        this.dialog = FxDialog.builder()
                .title(title)
                .body(messageLabel)
                .footer(cancelButton, confirmButton)
                .build();
        this.dialog.getStyleClass().add("forja-confirm-dialog");
    }

    private void resolveAndClose(boolean confirmed) {
        result = confirmed;
        if (onResult != null) onResult.accept(confirmed);
        dialog.close();
    }

    /** Shows this confirm dialog on the scene's overlay layer. */
    public void show(Scene scene) { dialog.show(scene); }

    /** Removes this confirm dialog from any overlay layer. */
    public void close() { dialog.close(); }

    /** Returns the wrapped {@link FxDialog}. */
    public FxDialog getDialog() { return dialog; }

    /** Returns the cancel button. */
    public FxButton getCancelButton() { return cancelButton; }

    /** Returns the confirm button. */
    public FxButton getConfirmButton() { return confirmButton; }

    /** Returns the message label. */
    public FxLabel getMessageLabel() { return messageLabel; }

    /** Returns whether {@link #resolveAndClose confirm} was clicked. */
    public boolean isConfirmed() { return result; }

    /**
     * Convenience — builds and shows a confirm dialog in one call.
     *
     * @param scene    target scene
     * @param title    dialog title
     * @param message  body text
     * @param onResult callback (true = confirm, false = cancel)
     */
    public static void ask(Scene scene, String title, String message, Consumer<Boolean> onResult) {
        FxConfirmDialog.builder()
                .title(title)
                .message(message)
                .onResult(onResult)
                .build()
                .show(scene);
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxConfirmDialog}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxConfirmDialog}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>title — {@code "Confirm"}</li>
     *   <li>message — empty</li>
     *   <li>cancelText — {@code "Cancel"}</li>
     *   <li>confirmText — {@code "Confirm"}</li>
     *   <li>confirmVariant — {@link ButtonVariant#PRIMARY}</li>
     *   <li>onResult — none</li>
     * </ul>
     */
    public static class Builder {

        private String title = "Confirm";
        private String message = "";
        private String cancelText = "Cancel";
        private String confirmText = "Confirm";
        private ButtonVariant confirmVariant = ButtonVariant.PRIMARY;
        private Consumer<Boolean> onResult;

        public Builder title(String title) { this.title = title == null ? "" : title; return this; }
        public Builder message(String message) { this.message = message == null ? "" : message; return this; }
        public Builder cancelText(String cancelText) { this.cancelText = cancelText == null ? "Cancel" : cancelText; return this; }
        public Builder confirmText(String confirmText) { this.confirmText = confirmText == null ? "Confirm" : confirmText; return this; }
        public Builder confirmVariant(ButtonVariant confirmVariant) { this.confirmVariant = confirmVariant == null ? ButtonVariant.PRIMARY : confirmVariant; return this; }
        public Builder onResult(Consumer<Boolean> onResult) { this.onResult = onResult; return this; }

        /** Builds and returns a fully-wired {@link FxConfirmDialog}. */
        public FxConfirmDialog build() {
            return new FxConfirmDialog(title, message, cancelText, confirmText, confirmVariant, onResult);
        }
    }
}
