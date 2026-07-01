package io.forja.components.feedbackAndStatus.fxAlert;

import io.forja.builder.FxNodeBuilder;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * An inline colored notice.
 *
 * <p>{@code FxAlert} is a framed {@link HBox} carrying an icon (auto-selected
 * per {@link SemanticVariant}), a stacked title + description, and an
 * optional dismiss (×) icon that hides the alert when clicked.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxAlert saved = FxAlert.builder()
 *          .variant(SemanticVariant.SUCCESS)
 *          .title("Saved")
 *          .description("Your changes have been synced.")
 *          .dismissible(true)
 *          .build();
 *     }
 * </pre>
 *
 * @see SemanticVariant
 * @see Builder
 */
public class FxAlert extends HBox {

    private static final PseudoClass DEFAULT_PC = PseudoClass.getPseudoClass("default");
    private static final PseudoClass MUTED_PC   = PseudoClass.getPseudoClass("muted");
    private static final PseudoClass ACCENT_PC  = PseudoClass.getPseudoClass("accent");
    private static final PseudoClass SUCCESS_PC = PseudoClass.getPseudoClass("success");
    private static final PseudoClass WARNING_PC = PseudoClass.getPseudoClass("warning");
    private static final PseudoClass DANGER_PC  = PseudoClass.getPseudoClass("danger");
    private static final PseudoClass INFO_PC    = PseudoClass.getPseudoClass("info");

    private final FxIcon leadingIcon = new FxIcon("fth-info");
    private final FxLabel titleLabel = new FxLabel("", LabelVariant.BODY);
    private final FxLabel descLabel = new FxLabel("", LabelVariant.SMALL);
    private final VBox textStack = new VBox(titleLabel, descLabel);
    private final FxIcon dismissIcon = new FxIcon("fth-x");

    private final ObjectProperty<SemanticVariant> variant = new SimpleObjectProperty<>(this, "variant", SemanticVariant.INFO);
    private final StringProperty title = new SimpleStringProperty(this, "title", "");
    private final StringProperty description = new SimpleStringProperty(this, "description", "");
    private final BooleanProperty dismissible = new SimpleBooleanProperty(this, "dismissible", false);
    private final BooleanProperty dismissed = new SimpleBooleanProperty(this, "dismissed", false);

    /**
     * Creates an empty {@code FxAlert} with the {@link SemanticVariant#INFO}
     * variant.
     */
    public FxAlert() {
        super();
        getStyleClass().add("forja-alert");
        setAlignment(Pos.TOP_LEFT);
        setSpacing(10);

        leadingIcon.getStyleClass().add("forja-alert-icon");
        titleLabel.getStyleClass().add("forja-alert-title");
        descLabel.getStyleClass().add("forja-alert-description");
        descLabel.setMuted(true);
        descLabel.setVisible(false);
        descLabel.setManaged(false);
        textStack.setSpacing(2);
        HBox.setHgrow(textStack, Priority.ALWAYS);

        dismissIcon.getStyleClass().add("forja-alert-dismiss");
        dismissIcon.setOnMouseClicked(e -> { dismiss(); e.consume(); });

        getChildren().addAll(leadingIcon, textStack);

        variant.addListener((obs, o, v) -> {
            applyVariantPseudoClass();
            leadingIcon.setIconLiteral(iconLiteralFor(v));
        });
        title.addListener((obs, o, v) -> {
            titleLabel.setText(v == null ? "" : v);
            boolean vis = v != null && !v.isEmpty();
            titleLabel.setVisible(vis);
            titleLabel.setManaged(vis);
        });
        description.addListener((obs, o, v) -> {
            descLabel.setText(v == null ? "" : v);
            boolean vis = v != null && !v.isEmpty();
            descLabel.setVisible(vis);
            descLabel.setManaged(vis);
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

    /** Hides the alert (equivalent to {@code setDismissed(true)}). */
    public void dismiss() { dismissed.set(true); }

    /** Un-hides a previously dismissed alert. */
    public void restore() { dismissed.set(false); }

    /** Returns the leading-icon node. */
    public FxIcon getLeadingIcon() { return leadingIcon; }

    /** Returns the title label node. */
    public FxLabel getTitleLabel() { return titleLabel; }

    /** Returns the description label node. */
    public FxLabel getDescriptionLabel() { return descLabel; }

    /** Returns the dismiss-icon node. */
    public FxIcon getDismissIcon() { return dismissIcon; }

    /** Returns the variant property. */
    public ObjectProperty<SemanticVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public SemanticVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(SemanticVariant v) { variant.set(v == null ? SemanticVariant.INFO : v); }

    /** Returns the title property. */
    public StringProperty titleProperty() { return title; }

    /** Returns the current title. */
    public String getTitle() { return title.get(); }

    /** Sets the title text. */
    public void setTitle(String v) { title.set(v == null ? "" : v); }

    /** Returns the description property. */
    public StringProperty descriptionProperty() { return description; }

    /** Returns the current description. */
    public String getDescription() { return description.get(); }

    /** Sets the description text. */
    public void setDescription(String v) { description.set(v == null ? "" : v); }

    /** Returns the dismissible property. */
    public BooleanProperty dismissibleProperty() { return dismissible; }

    /** Returns whether a dismiss (×) icon is shown. */
    public boolean isDismissible() { return dismissible.get(); }

    /** Sets whether a dismiss (×) icon is shown. */
    public void setDismissible(boolean v) { dismissible.set(v); }

    /** Returns the dismissed property. */
    public BooleanProperty dismissedProperty() { return dismissed; }

    /** Returns whether the alert is currently hidden. */
    public boolean isDismissed() { return dismissed.get(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxAlert}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when the alert is dismissed via the × icon. */
    @FunctionalInterface
    public interface OnDismiss { void accept(FxAlert alert); }

    /**
     * Fluent builder for constructing an {@link FxAlert}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>variant — {@link SemanticVariant#INFO}</li>
     *   <li>title / description — empty</li>
     *   <li>dismissible — {@code false}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxAlert, Builder> {

        private SemanticVariant variant = SemanticVariant.INFO;
        private String title = "";
        private String description = "";
        private boolean dismissible = false;
        private Node leadingIconOverride;
        private OnDismiss onDismiss;

        public Builder variant(SemanticVariant variant) { this.variant = variant == null ? SemanticVariant.INFO : variant; return this; }
        public Builder title(String title) { this.title = title == null ? "" : title; return this; }
        public Builder description(String description) { this.description = description == null ? "" : description; return this; }
        public Builder dismissible(boolean dismissible) { this.dismissible = dismissible; return this; }
        public Builder leadingIcon(Node node) { this.leadingIconOverride = node; return this; }
        public Builder leadingIcon(String iconLiteral) { this.leadingIconOverride = new FxIcon(iconLiteral); return this; }
        public Builder onDismiss(OnDismiss onDismiss) { this.onDismiss = onDismiss; return this; }

        @Override
        public FxAlert build() {
            FxAlert a = new FxAlert();
            a.setVariant(variant);
            a.setTitle(title);
            a.setDescription(description);
            a.setDismissible(dismissible);
            if (leadingIconOverride instanceof FxIcon) {
                a.getLeadingIcon().setIconLiteral(((FxIcon) leadingIconOverride).getIconLiteral());
            }
            if (onDismiss != null) {
                a.dismissedProperty().addListener((obs, o, v) -> { if (v) onDismiss.accept(a); });
            }
            applyBase(a);
            return a;
        }
    }
}
