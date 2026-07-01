package io.forja.components.buttonsAndActions.fxCopyButton;

import io.forja.builder.FxComponentBuilder;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.animation.PauseTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;

/**
 * A button that copies a configured value to the system clipboard on click
 * and briefly flashes a confirmation icon + label.
 *
 * <p>Extends {@link Button} directly — variant is handled via a
 * {@link PseudoClass} on the outer element rather than a Skin. On click,
 * writes {@link #getValue} to the system clipboard, swaps the graphic to a
 * check icon, sets the label to {@link #getConfirmText}, and reverts after
 * {@link #getConfirmDurationMs} milliseconds.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxCopyButton copyToken = FxCopyButton.builder()
 *          .text("Copy")
 *          .value("api-key-abc123")
 *          .confirmText("Copied!")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxCopyButton extends Button {

    private static final PseudoClass PRIMARY_PC   = PseudoClass.getPseudoClass("primary");
    private static final PseudoClass SECONDARY_PC = PseudoClass.getPseudoClass("secondary");
    private static final PseudoClass GHOST_PC     = PseudoClass.getPseudoClass("ghost");
    private static final PseudoClass DANGER_PC    = PseudoClass.getPseudoClass("danger");
    private static final PseudoClass CONFIRMING_PC = PseudoClass.getPseudoClass("confirming");

    private static final String COPY_ICON_LITERAL  = "fth-copy";
    private static final String CHECK_ICON_LITERAL = "fth-check";

    private final ObjectProperty<ButtonVariant> variant = new SimpleObjectProperty<>(this, "variant", ButtonVariant.SECONDARY);
    private final StringProperty value = new SimpleStringProperty(this, "value", "");
    private final StringProperty confirmText = new SimpleStringProperty(this, "confirmText", "Copied");
    private final SimpleStringProperty idleText = new SimpleStringProperty(this, "idleText", "");
    private final BooleanProperty confirming = new SimpleBooleanProperty(this, "confirming", false);
    private long confirmDurationMs = 1500;

    private final PauseTransition revertTimer = new PauseTransition(Duration.millis(confirmDurationMs));

    /**
     * Creates an {@code FxCopyButton} with empty label and value.
     */
    public FxCopyButton() {
        super();
        getStyleClass().addAll("forja-button", "forja-copy-button");
        setGraphic(new FxIcon(COPY_ICON_LITERAL));
        idleText.bind(textProperty());
        confirming.addListener((obs, o, v) -> pseudoClassStateChanged(CONFIRMING_PC, v));
        variant.addListener((obs, o, v) -> applyVariantPseudoClass());
        applyVariantPseudoClass();

        revertTimer.setOnFinished(e -> restoreIdle());
        setOnAction(e -> copy());
    }

    /**
     * Creates an {@code FxCopyButton} with the given text and clipboard value.
     *
     * @param text  button label
     * @param value clipboard value written on click
     */
    public FxCopyButton(String text, String value) {
        this();
        setText(text);
        setValue(value);
    }

    private void applyVariantPseudoClass() {
        pseudoClassStateChanged(PRIMARY_PC,   false);
        pseudoClassStateChanged(SECONDARY_PC, false);
        pseudoClassStateChanged(GHOST_PC,     false);
        pseudoClassStateChanged(DANGER_PC,    false);
        switch (getVariant()) {
            case PRIMARY:   pseudoClassStateChanged(PRIMARY_PC,   true); break;
            case SECONDARY: pseudoClassStateChanged(SECONDARY_PC, true); break;
            case GHOST:     pseudoClassStateChanged(GHOST_PC,     true); break;
            case DANGER:    pseudoClassStateChanged(DANGER_PC,    true); break;
        }
    }

    /** Fires a copy immediately as if the button were clicked. */
    public void copy() {
        String v = getValue();
        if (v == null) v = "";
        ClipboardContent content = new ClipboardContent();
        content.putString(v);
        Clipboard.getSystemClipboard().setContent(content);
        showConfirm();
    }

    private void showConfirm() {
        confirming.set(true);
        idleText.unbind();
        setGraphic(new FxIcon(CHECK_ICON_LITERAL));
        setText(getConfirmText());
        revertTimer.stop();
        revertTimer.setDuration(Duration.millis(confirmDurationMs));
        revertTimer.playFromStart();
    }

    private void restoreIdle() {
        setGraphic(new FxIcon(COPY_ICON_LITERAL));
        setText(idleText.get());
        idleText.bind(textProperty());
        confirming.set(false);
    }

    /** Returns the variant property. */
    public ObjectProperty<ButtonVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public ButtonVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(ButtonVariant v) { variant.set(v == null ? ButtonVariant.SECONDARY : v); }

    /** Returns the value property (the clipboard payload). */
    public StringProperty valueProperty() { return value; }

    /** Returns the current clipboard payload. */
    public String getValue() { return value.get(); }

    /** Sets the clipboard payload written on click. */
    public void setValue(String v) { value.set(v == null ? "" : v); }

    /** Returns the confirm-text property. */
    public StringProperty confirmTextProperty() { return confirmText; }

    /** Returns the current confirm text. */
    public String getConfirmText() { return confirmText.get(); }

    /** Sets the label shown while the confirmation flash is visible. */
    public void setConfirmText(String v) { confirmText.set(v == null ? "" : v); }

    /** Returns the confirming property. */
    public BooleanProperty confirmingProperty() { return confirming; }

    /** Returns whether the confirmation flash is currently visible. */
    public boolean isConfirming() { return confirming.get(); }

    /** Returns the confirmation-flash duration in milliseconds. */
    public long getConfirmDurationMs() { return confirmDurationMs; }

    /** Sets the confirmation-flash duration in milliseconds. */
    public void setConfirmDurationMs(long ms) {
        this.confirmDurationMs = Math.max(0, ms);
        revertTimer.setDuration(Duration.millis(this.confirmDurationMs));
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxCopyButton}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxCopyButton}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — {@code "Copy"}</li>
     *   <li>value — empty</li>
     *   <li>confirmText — {@code "Copied"}</li>
     *   <li>confirmDurationMs — {@code 1500}</li>
     *   <li>variant — {@link ButtonVariant#SECONDARY}</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxCopyButton, Builder> {

        private String text = "Copy";
        private String value = "";
        private String confirmText = "Copied";
        private long confirmDurationMs = 1500;
        private ButtonVariant variant = ButtonVariant.SECONDARY;

        public Builder text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder value(String value) { this.value = value == null ? "" : value; return this; }
        public Builder confirmText(String confirmText) { this.confirmText = confirmText == null ? "" : confirmText; return this; }
        public Builder confirmDurationMs(long confirmDurationMs) { this.confirmDurationMs = Math.max(0, confirmDurationMs); return this; }
        public Builder variant(ButtonVariant variant) { this.variant = variant == null ? ButtonVariant.SECONDARY : variant; return this; }

        @Override
        public FxCopyButton build() {
            FxCopyButton b = new FxCopyButton(text, value);
            b.setVariant(variant);
            b.setConfirmText(confirmText);
            b.setConfirmDurationMs(confirmDurationMs);
            applyBase(b);
            return b;
        }
    }
}
