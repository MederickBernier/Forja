package io.forja.components.typography.fxLink;

import io.forja.builder.FxComponentBuilder;
import io.forja.components.utilities.fxIcon.FxIcon;
import io.forja.components.typography.fxLink.FxLinkSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Skin;

/**
 * A styled hyperlink component built on top of {@link Hyperlink}.
 *
 * <p>{@code FxLink} extends the standard JavaFX {@code Hyperlink} - all native
 * JavaFX properties, bindings, and event APIs remain fully accessible. Forja
 * adds a visual variant system, an automatic external-link icon, and a fluent
 * builder API.
 *
 * <p>The preferred way to construct an {@code FxLink} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxLink docs = FxLink.builder()
 *          .text("Open docs")
 *          .variant(LinkVariant.EXTERNAL)
 *          .onAction(e -> openDocs())
 *          .build();
 *     }
 * </pre>
 *
 * <p>Constructor and direct instantiation are also supported:</p>
 * <pre>
 *     {@code
 *      FxLink link = new FxLink("Read more", LinkVariant.DEFAULT);
 *     }
 * </pre>
 *
 * <p>The {@link LinkVariant#EXTERNAL} variant automatically appends a trailing
 * external-link icon (via {@link FxIcon}). Setting the variant away from
 * {@code EXTERNAL} clears the managed icon but leaves any caller-supplied
 * {@code graphic} untouched.
 *
 * @see LinkVariant
 * @see Builder
 */
public class FxLink extends Hyperlink {

    private static final String EXTERNAL_ICON_LITERAL = "fth-external-link";
    private static final int EXTERNAL_ICON_SIZE = 14;

    private final ObjectProperty<LinkVariant> variant = new SimpleObjectProperty<>(this, "variant", LinkVariant.DEFAULT);

    /** Icon node we own; never clear a user-supplied graphic. */
    private FxIcon managedExternalIcon;

    /**
     * Creates an {@code FxLink} with no text and the default variant
     * ({@link LinkVariant#DEFAULT}).
     */
    public FxLink() {
        super();
        init();
    }

    /**
     * Creates an {@code FxLink} with the given text and the default variant.
     *
     * @param text the text displayed by the link
     */
    public FxLink(String text) {
        super(text);
        init();
    }

    /**
     * Creates an {@code FxLink} with the given text and variant.
     *
     * @param text the text displayed by the link
     * @param variant the visual variant - see {@link LinkVariant}
     */
    public FxLink(String text, LinkVariant variant) {
        super(text);
        init();
        setVariant(variant);
    }

    private void init() {
        getStyleClass().add("forja-link");
        applyExternalIcon();

        variant.addListener((obs, old, val) -> applyExternalIcon());
    }

    private void applyExternalIcon() {
        if (getVariant() == LinkVariant.EXTERNAL) {
            if (managedExternalIcon == null) {
                managedExternalIcon = new FxIcon(EXTERNAL_ICON_LITERAL);
                managedExternalIcon.setIconSize(EXTERNAL_ICON_SIZE);
            }
            setGraphic(managedExternalIcon);
            setContentDisplay(ContentDisplay.RIGHT);
        } else if (getGraphic() == managedExternalIcon && managedExternalIcon != null) {
            setGraphic(null);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns the Forja link skin which drives variant-based styling.</p>
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new FxLinkSkin(this);
    }

    /**
     * Returns the variant property of this link.
     *
     * @return the {@link ObjectProperty} holding the current {@link LinkVariant}
     */
    public ObjectProperty<LinkVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public LinkVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(LinkVariant v) { variant.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxLink}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxLink}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty string</li>
     *   <li>variant — {@link LinkVariant#DEFAULT}</li>
     *   <li>graphic — none (overridden when variant = EXTERNAL)</li>
     *   <li>onAction — none</li>
     * </ul>
     *
     * <p>Inherited from {@link io.forja.builder.FxComponentBuilder}:
     * <ul>
     *   <li>id, disabled, visible, styleClass, style, tooltip, userData</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxLink, Builder> {

        private String text = "";
        private LinkVariant variant = LinkVariant.DEFAULT;
        private Node graphic;
        private EventHandler<ActionEvent> onAction;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder variant(LinkVariant variant) {
            this.variant = variant;
            return this;
        }

        /**
         * Sets a caller-supplied graphic node. Has no effect when the variant
         * is {@link LinkVariant#EXTERNAL} (the external-link icon takes the
         * graphic slot in that case).
         */
        public Builder graphic(Node graphic) {
            this.graphic = graphic;
            return this;
        }

        public Builder onAction(EventHandler<ActionEvent> handler) {
            this.onAction = handler;
            return this;
        }

        @Override
        public FxLink build() {
            FxLink link = new FxLink(text, variant);
            if (variant != LinkVariant.EXTERNAL && graphic != null) {
                link.setGraphic(graphic);
            }
            if (onAction != null) {
                link.setOnAction(onAction);
            }
            applyBase(link);
            return link;
        }
    }
}
