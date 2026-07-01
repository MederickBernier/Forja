package io.forja.components.feedbackAndStatus.fxToast;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.overlays.OverlayHost;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import io.forja.tokens.SemanticVariant;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * A transient bottom-right notification rendered on the {@link OverlayHost}
 * overlay layer.
 *
 * <p>{@code FxToast} auto-fades in, pauses for its configured lifetime, then
 * fades out and removes itself from the overlay layer. Static {@link #show}
 * helpers post a toast in one call without keeping a reference.
 *
 * <p>Static usage:</p>
 * <pre>
 *     {@code
 *      FxToast.show(scene, "Saved!", SemanticVariant.SUCCESS);
 *     }
 * </pre>
 *
 * <p>Builder usage — for advanced control over duration/positioning:</p>
 * <pre>
 *     {@code
 *      FxToast t = FxToast.builder()
 *          .message("Uploading…")
 *          .variant(SemanticVariant.INFO)
 *          .durationMs(4000)
 *          .build();
 *      t.postTo(scene);
 *     }
 * </pre>
 *
 * @see OverlayHost
 * @see SemanticVariant
 * @see Builder
 */
public class FxToast extends HBox {

    private static final PseudoClass DEFAULT_PC = PseudoClass.getPseudoClass("default");
    private static final PseudoClass MUTED_PC   = PseudoClass.getPseudoClass("muted");
    private static final PseudoClass ACCENT_PC  = PseudoClass.getPseudoClass("accent");
    private static final PseudoClass SUCCESS_PC = PseudoClass.getPseudoClass("success");
    private static final PseudoClass WARNING_PC = PseudoClass.getPseudoClass("warning");
    private static final PseudoClass DANGER_PC  = PseudoClass.getPseudoClass("danger");
    private static final PseudoClass INFO_PC    = PseudoClass.getPseudoClass("info");

    private final FxIcon icon = new FxIcon("fth-info");
    private final FxLabel messageLabel = new FxLabel("", LabelVariant.BODY);

    private final ObjectProperty<SemanticVariant> variant = new SimpleObjectProperty<>(this, "variant", SemanticVariant.INFO);
    private final StringProperty message = new SimpleStringProperty(this, "message", "");
    private long durationMs = 3000;
    private Pos position = Pos.BOTTOM_RIGHT;

    /**
     * Creates a default-info toast with no message.
     */
    public FxToast() {
        super();
        getStyleClass().add("forja-toast");
        setSpacing(8);
        setAlignment(Pos.CENTER_LEFT);

        icon.getStyleClass().add("forja-toast-icon");
        messageLabel.getStyleClass().add("forja-toast-message");
        getChildren().addAll(icon, messageLabel);

        variant.addListener((obs, o, v) -> {
            applyVariantPseudoClass();
            icon.setIconLiteral(iconLiteralFor(v));
        });
        message.addListener((obs, o, v) -> messageLabel.setText(v == null ? "" : v));

        applyVariantPseudoClass();
        icon.setIconLiteral(iconLiteralFor(getVariant()));
    }

    private static String iconLiteralFor(SemanticVariant v) {
        if (v == null) return "fth-info";
        switch (v) {
            case SUCCESS: return "fth-check-circle";
            case WARNING: return "fth-alert-triangle";
            case DANGER:  return "fth-alert-octagon";
            case ACCENT:  return "fth-star";
            case MUTED:   return "fth-message-circle";
            case INFO:
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

    /** Returns the icon node. */
    public FxIcon getIcon() { return icon; }

    /** Returns the message label. */
    public FxLabel getMessageLabel() { return messageLabel; }

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

    /** Sets the message text. */
    public void setMessage(String v) { message.set(v == null ? "" : v); }

    /** Returns the on-screen duration in milliseconds. */
    public long getDurationMs() { return durationMs; }

    /** Sets the on-screen duration in milliseconds. */
    public void setDurationMs(long durationMs) { this.durationMs = Math.max(0, durationMs); }

    /** Returns the corner position. */
    public Pos getPosition() { return position; }

    /** Sets the corner position (defaults to {@link Pos#BOTTOM_RIGHT}). */
    public void setPosition(Pos position) { this.position = position == null ? Pos.BOTTOM_RIGHT : position; }

    /**
     * Renders this toast on the given scene's overlay layer, fades it in,
     * waits {@link #getDurationMs}, and fades it out.
     *
     * @param scene target scene
     */
    public void postTo(Scene scene) {
        StackPane.setAlignment(this, getPosition());
        StackPane.setMargin(this, new Insets(20));
        setOpacity(0);
        OverlayHost.show(scene, this);

        FadeTransition in = new FadeTransition(Duration.millis(180), this);
        in.setFromValue(0); in.setToValue(1);
        PauseTransition hold = new PauseTransition(Duration.millis(durationMs));
        FadeTransition out = new FadeTransition(Duration.millis(220), this);
        out.setFromValue(1); out.setToValue(0);
        SequentialTransition seq = new SequentialTransition(in, hold, out);
        seq.setOnFinished(e -> OverlayHost.dismiss(this));
        seq.play();
    }

    /**
     * Posts a toast with default duration + variant INFO.
     *
     * @param scene   target scene
     * @param message text
     */
    public static void show(Scene scene, String message) {
        show(scene, message, SemanticVariant.INFO);
    }

    /**
     * Posts a toast with default duration and the given variant.
     *
     * @param scene   target scene
     * @param message text
     * @param variant color variant
     */
    public static void show(Scene scene, String message, SemanticVariant variant) {
        FxToast t = FxToast.builder().message(message).variant(variant).build();
        t.postTo(scene);
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxToast}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxToast}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>message — empty</li>
     *   <li>variant — {@link SemanticVariant#INFO}</li>
     *   <li>durationMs — {@code 3000}</li>
     *   <li>position — {@link Pos#BOTTOM_RIGHT}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxToast, Builder> {

        private String message = "";
        private SemanticVariant variant = SemanticVariant.INFO;
        private long durationMs = 3000;
        private Pos position = Pos.BOTTOM_RIGHT;

        public Builder message(String message) { this.message = message == null ? "" : message; return this; }
        public Builder variant(SemanticVariant variant) { this.variant = variant == null ? SemanticVariant.INFO : variant; return this; }
        public Builder durationMs(long durationMs) { this.durationMs = Math.max(0, durationMs); return this; }
        public Builder position(Pos position) { this.position = position == null ? Pos.BOTTOM_RIGHT : position; return this; }

        @Override
        public FxToast build() {
            FxToast t = new FxToast();
            t.setVariant(variant);
            t.setMessage(message);
            t.setDurationMs(durationMs);
            t.setPosition(position);
            applyBase(t);
            return t;
        }
    }
}
