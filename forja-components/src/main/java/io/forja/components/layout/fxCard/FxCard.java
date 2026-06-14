package io.forja.components.layout.fxCard;

import io.forja.builder.FxNodeBuilder;
import io.forja.tokens.SpacingSize;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * A surface container with three optional slots — header, body, footer.
 *
 * <p>{@code FxCard} extends {@link VBox} and renders, in order, whichever of
 * {@code header}, {@code body}, and {@code footer} are set. Slots can be
 * mutated at any time and the children list rebuilds automatically.
 *
 * <p>The preferred way to construct an {@code FxCard} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxCard card = FxCard.builder()
 *          .header(titleRow)
 *          .body(content)
 *          .footer(actionsRow)
 *          .variant(CardVariant.OUTLINED)
 *          .gap(SpacingSize.MD)
 *          .build();
 *     }
 * </pre>
 *
 * @see CardVariant
 * @see Builder
 */
public class FxCard extends VBox {

    private static final PseudoClass DEFAULT_PC  = PseudoClass.getPseudoClass("default");
    private static final PseudoClass OUTLINED_PC = PseudoClass.getPseudoClass("outlined");
    private static final PseudoClass ELEVATED_PC = PseudoClass.getPseudoClass("elevated");

    private final ObjectProperty<Node> header = new SimpleObjectProperty<>(this, "header");
    private final ObjectProperty<Node> body = new SimpleObjectProperty<>(this, "body");
    private final ObjectProperty<Node> footer = new SimpleObjectProperty<>(this, "footer");
    private final ObjectProperty<CardVariant> variant = new SimpleObjectProperty<>(this, "variant", CardVariant.DEFAULT);
    private final ObjectProperty<SpacingSize> gap = new SimpleObjectProperty<>(this, "gap", SpacingSize.MD);

    /**
     * Creates an empty card with the default variant ({@link CardVariant#DEFAULT}).
     */
    public FxCard() {
        super();
        getStyleClass().add("forja-card");

        header.addListener((obs, old, val) -> rebuild());
        body.addListener((obs, old, val) -> rebuild());
        footer.addListener((obs, old, val) -> rebuild());
        variant.addListener((obs, old, val) -> applyVariantPseudoClass());
        gap.addListener((obs, old, val) -> applyGap());

        applyGap();
        applyVariantPseudoClass();
    }

    /**
     * Creates a card with the given variant.
     *
     * @param variant the visual variant
     */
    public FxCard(CardVariant variant) {
        this();
        setVariant(variant);
    }

    /**
     * Creates a card with the given body slot.
     *
     * @param body the body node
     */
    public FxCard(Node body) {
        this();
        setBody(body);
    }

    private void applyGap() {
        super.setSpacing(getGap().pixels());
    }

    private void applyVariantPseudoClass() {
        pseudoClassStateChanged(DEFAULT_PC,  false);
        pseudoClassStateChanged(OUTLINED_PC, false);
        pseudoClassStateChanged(ELEVATED_PC, false);

        switch (getVariant()) {
            case DEFAULT:  pseudoClassStateChanged(DEFAULT_PC,  true); break;
            case OUTLINED: pseudoClassStateChanged(OUTLINED_PC, true); break;
            case ELEVATED: pseudoClassStateChanged(ELEVATED_PC, true); break;
        }
    }

    private void rebuild() {
        getChildren().clear();
        Node h = getHeader();
        Node b = getBody();
        Node f = getFooter();
        if (h != null) getChildren().add(h);
        if (b != null) getChildren().add(b);
        if (f != null) getChildren().add(f);
    }

    /** Returns the header slot property. */
    public ObjectProperty<Node> headerProperty() { return header; }

    /** Returns the current header node, or {@code null}. */
    public Node getHeader() { return header.get(); }

    /** Sets the header node. Pass {@code null} to clear the slot. */
    public void setHeader(Node node) { header.set(node); }

    /** Returns the body slot property. */
    public ObjectProperty<Node> bodyProperty() { return body; }

    /** Returns the current body node, or {@code null}. */
    public Node getBody() { return body.get(); }

    /** Sets the body node. Pass {@code null} to clear the slot. */
    public void setBody(Node node) { body.set(node); }

    /** Returns the footer slot property. */
    public ObjectProperty<Node> footerProperty() { return footer; }

    /** Returns the current footer node, or {@code null}. */
    public Node getFooter() { return footer.get(); }

    /** Sets the footer node. Pass {@code null} to clear the slot. */
    public void setFooter(Node node) { footer.set(node); }

    /** Returns the variant property. */
    public ObjectProperty<CardVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public CardVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(CardVariant v) { variant.set(v); }

    /** Returns the gap token property (spacing between header/body/footer). */
    public ObjectProperty<SpacingSize> gapProperty() { return gap; }

    /** Returns the current gap token. */
    public SpacingSize getGap() { return gap.get(); }

    /** Sets the gap token. */
    public void setGap(SpacingSize v) { gap.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxCard}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxCard}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>header — {@code null}</li>
     *   <li>body — {@code null}</li>
     *   <li>footer — {@code null}</li>
     *   <li>variant — {@link CardVariant#DEFAULT}</li>
     *   <li>gap — {@link SpacingSize#MD}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxCard, Builder> {

        private Node header;
        private Node body;
        private Node footer;
        private CardVariant variant = CardVariant.DEFAULT;
        private SpacingSize gap = SpacingSize.MD;

        public Builder header(Node header) {
            this.header = header;
            return this;
        }

        public Builder body(Node body) {
            this.body = body;
            return this;
        }

        public Builder footer(Node footer) {
            this.footer = footer;
            return this;
        }

        public Builder variant(CardVariant variant) {
            this.variant = variant;
            return this;
        }

        public Builder gap(SpacingSize gap) {
            this.gap = gap;
            return this;
        }

        @Override
        public FxCard build() {
            FxCard card = new FxCard();
            card.setVariant(variant);
            card.setGap(gap);
            if (header != null) card.setHeader(header);
            if (body != null) card.setBody(body);
            if (footer != null) card.setFooter(footer);
            applyBase(card);
            return card;
        }
    }
}
