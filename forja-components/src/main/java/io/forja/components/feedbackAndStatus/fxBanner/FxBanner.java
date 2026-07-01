package io.forja.components.feedbackAndStatus.fxBanner;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import io.forja.tokens.SemanticVariant;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * A full-width top-of-page notice with an optional call-to-action button and
 * a dismiss (×) icon.
 *
 * <p>{@code FxBanner} is a framed {@link HBox} carrying a leading icon
 * (auto-selected per variant), the message {@link FxLabel}, an optional
 * action {@link FxButton}, and an optional dismiss icon. Set
 * {@link #setActionText} + {@link #setActionText onAction} to show the CTA.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxBanner announce = FxBanner.builder()
 *          .variant(SemanticVariant.ACCENT)
 *          .message("Version 1.2 is available.")
 *          .actionText("Update")
 *          .onAction(e -> updater.start())
 *          .dismissible(true)
 *          .build();
 *     }
 * </pre>
 *
 * @see SemanticVariant
 * @see Builder
 */
public class FxBanner extends HBox {

    private static final PseudoClass DEFAULT_PC = PseudoClass.getPseudoClass("default");
    private static final PseudoClass MUTED_PC   = PseudoClass.getPseudoClass("muted");
    private static final PseudoClass ACCENT_PC  = PseudoClass.getPseudoClass("accent");
    private static final PseudoClass SUCCESS_PC = PseudoClass.getPseudoClass("success");
    private static final PseudoClass WARNING_PC = PseudoClass.getPseudoClass("warning");
    private static final PseudoClass DANGER_PC  = PseudoClass.getPseudoClass("danger");
    private static final PseudoClass INFO_PC    = PseudoClass.getPseudoClass("info");

    private final FxIcon leadingIcon = new FxIcon("fth-info");
    private final FxLabel messageLabel = new FxLabel("", LabelVariant.BODY);
    private final Region spacer = new Region();
    private final FxButton actionButton = new FxButton();
    private final FxIcon dismissIcon = new FxIcon("fth-x");

    private final ObjectProperty<SemanticVariant> variant = new SimpleObjectProperty<>(this, "variant", SemanticVariant.INFO);
    private final StringProperty message = new SimpleStringProperty(this, "message", "");
    private final StringProperty actionText = new SimpleStringProperty(this, "actionText", "");
    private final BooleanProperty dismissible = new SimpleBooleanProperty(this, "dismissible", false);
    private final BooleanProperty dismissed = new SimpleBooleanProperty(this, "dismissed", false);

    /**
     * Creates an empty {@code FxBanner} with the {@link SemanticVariant#INFO}
     * variant.
     */
    public FxBanner() {
        super();
        getStyleClass().add("forja-banner");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(10);
        setFillHeight(true);

        leadingIcon.getStyleClass().add("forja-banner-icon");
        messageLabel.getStyleClass().add("forja-banner-message");
        HBox.setHgrow(spacer, Priority.ALWAYS);

        actionButton.getStyleClass().add("forja-banner-action");
        actionButton.setVariant(ButtonVariant.GHOST);
        actionButton.setVisible(false);
        actionButton.setManaged(false);

        dismissIcon.getStyleClass().add("forja-banner-dismiss");
        dismissIcon.setOnMouseClicked(e -> { dismiss(); e.consume(); });

        getChildren().addAll(leadingIcon, messageLabel, spacer, actionButton);

        variant.addListener((obs, o, v) -> {
            applyVariantPseudoClass();
            leadingIcon.setIconLiteral(iconLiteralFor(v));
        });
        message.addListener((obs, o, v) -> messageLabel.setText(v == null ? "" : v));
        actionText.addListener((obs, o, v) -> {
            String s = v == null ? "" : v;
            actionButton.setText(s);
            boolean vis = !s.isEmpty();
            actionButton.setVisible(vis);
            actionButton.setManaged(vis);
        });
        dismissible.addListener((obs, o, v) -> {
            if (v && !getChildren().contains(dismissIcon)) getChildren().add(dismissIcon);
            else if (!v) getChildren().remove(dismissIcon);
        });
        dismissed.addListener((obs, o, v) -> {
            setVisible(!v);
            setManaged(!v);
        });

        applyVariantPseudoClass();
        leadingIcon.setIconLiteral(iconLiteralFor(getVariant()));
    }

    private static String iconLiteralFor(SemanticVariant v) {
        if (v == null) return "fth-info";
        switch (v) {
            case SUCCESS: return "fth-check-circle";
            case WARNING: return "fth-alert-triangle";
            case DANGER:  return "fth-alert-octagon";
            case INFO:    return "fth-info";
            case ACCENT:  return "fth-star";
            case MUTED:   return "fth-message-circle";
            case DEFAULT:
            default:      return "fth-info";
        }
    }

    private void applyVariantPseudoClass() {
        pseudoClassStateChanged(DEFAULT_PC, false);
        pseudoClassStateChanged(MUTED_PC,   false);
        pseudoClassStateChanged(ACCENT_PC,  false);
        pseudoClassStateChanged(SUCCESS_PC, false);
        pseudoClassStateChanged(WARNING_PC, false);
        pseudoClassStateChanged(DANGER_PC,  false);
        pseudoClassStateChanged(INFO_PC,    false);
        switch (getVariant()) {
            case DEFAULT: pseudoClassStateChanged(DEFAULT_PC, true); break;
            case MUTED:   pseudoClassStateChanged(MUTED_PC,   true); break;
            case ACCENT:  pseudoClassStateChanged(ACCENT_PC,  true); break;
            case SUCCESS: pseudoClassStateChanged(SUCCESS_PC, true); break;
            case WARNING: pseudoClassStateChanged(WARNING_PC, true); break;
            case DANGER:  pseudoClassStateChanged(DANGER_PC,  true); break;
            case INFO:    pseudoClassStateChanged(INFO_PC,    true); break;
        }
    }

    /** Hides the banner. */
    public void dismiss() { dismissed.set(true); }

    /** Un-hides a previously dismissed banner. */
    public void restore() { dismissed.set(false); }

    /** Returns the leading-icon node. */
    public FxIcon getLeadingIcon() { return leadingIcon; }

    /** Returns the message label node. */
    public FxLabel getMessageLabel() { return messageLabel; }

    /** Returns the action button node. */
    public FxButton getActionButton() { return actionButton; }

    /** Returns the dismiss-icon node. */
    public FxIcon getDismissIcon() { return dismissIcon; }

    /** Returns the variant property. */
    public ObjectProperty<SemanticVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public SemanticVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(SemanticVariant v) { variant.set(v == null ? SemanticVariant.INFO : v); }

    /** Returns the message property. */
    public StringProperty messageProperty() { return message; }

    /** Returns the current message. */
    public String getMessage() { return message.get(); }

    /** Sets the message. */
    public void setMessage(String v) { message.set(v == null ? "" : v); }

    /** Returns the action-text property. */
    public StringProperty actionTextProperty() { return actionText; }

    /** Returns the current action-text. */
    public String getActionText() { return actionText.get(); }

    /** Sets the action-button label; empty hides the button. */
    public void setActionText(String v) { actionText.set(v == null ? "" : v); }

    /** Convenience — sets the action button's onAction handler. */
    public void setOnAction(EventHandler<ActionEvent> handler) { actionButton.setOnAction(handler); }

    /** Returns the dismissible property. */
    public BooleanProperty dismissibleProperty() { return dismissible; }

    /** Returns whether the dismiss (×) icon is shown. */
    public boolean isDismissible() { return dismissible.get(); }

    /** Sets whether the dismiss (×) icon is shown. */
    public void setDismissible(boolean v) { dismissible.set(v); }

    /** Returns the dismissed property. */
    public BooleanProperty dismissedProperty() { return dismissed; }

    /** Returns whether the banner is currently hidden. */
    public boolean isDismissed() { return dismissed.get(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxBanner}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxBanner}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>variant — {@link SemanticVariant#INFO}</li>
     *   <li>message — empty</li>
     *   <li>actionText — empty (button hidden)</li>
     *   <li>dismissible — {@code false}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxBanner, Builder> {

        private SemanticVariant variant = SemanticVariant.INFO;
        private String message = "";
        private String actionText = "";
        private boolean dismissible = false;
        private EventHandler<ActionEvent> onAction;

        public Builder variant(SemanticVariant variant) { this.variant = variant == null ? SemanticVariant.INFO : variant; return this; }
        public Builder message(String message) { this.message = message == null ? "" : message; return this; }
        public Builder actionText(String actionText) { this.actionText = actionText == null ? "" : actionText; return this; }
        public Builder dismissible(boolean dismissible) { this.dismissible = dismissible; return this; }
        public Builder onAction(EventHandler<ActionEvent> onAction) { this.onAction = onAction; return this; }

        @Override
        public FxBanner build() {
            FxBanner b = new FxBanner();
            b.setVariant(variant);
            b.setMessage(message);
            b.setActionText(actionText);
            b.setDismissible(dismissible);
            if (onAction != null) b.setOnAction(onAction);
            applyBase(b);
            return b;
        }
    }
}
