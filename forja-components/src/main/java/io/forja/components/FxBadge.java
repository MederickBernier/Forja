package io.forja.components;

import io.forja.builder.FxComponentBuilder;
import io.forja.skin.FxBadgeSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;

/**
 * A styled pill-shaped badge built on top of {@link Label}.
 *
 * <p>{@code FxBadge} renders a short label (status, count, category) inside a
 * pill with a semantic background color. Variants mirror the seven Forja
 * semantic tokens.
 *
 * <p>The preferred way to construct an {@code FxBadge} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxBadge status = FxBadge.builder()
 *          .text("Active")
 *          .variant(BadgeVariant.SUCCESS)
 *          .build();
 *     }
 * </pre>
 *
 * <p>Constructor and direct instantiation are also supported:</p>
 * <pre>
 *     {@code
 *      FxBadge tag = new FxBadge("Beta", BadgeVariant.ACCENT);
 *     }
 * </pre>
 *
 * @see BadgeVariant
 * @see Builder
 */
public class FxBadge extends Label {

    private final ObjectProperty<BadgeVariant> variant = new SimpleObjectProperty<>(this, "variant", BadgeVariant.DEFAULT);

    /**
     * Creates an empty {@code FxBadge} with the default variant
     * ({@link BadgeVariant#DEFAULT}).
     */
    public FxBadge() {
        super();
        getStyleClass().add("forja-badge");
    }

    /**
     * Creates an {@code FxBadge} with the given text and default variant.
     *
     * @param text the badge label
     */
    public FxBadge(String text) {
        super(text);
        getStyleClass().add("forja-badge");
    }

    /**
     * Creates an {@code FxBadge} with the given text and variant.
     *
     * @param text the badge label
     * @param variant the semantic color variant
     */
    public FxBadge(String text, BadgeVariant variant) {
        this(text);
        setVariant(variant);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns the Forja badge skin which drives variant-based styling.</p>
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new FxBadgeSkin(this);
    }

    /** Returns the variant property. */
    public ObjectProperty<BadgeVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public BadgeVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(BadgeVariant v) { variant.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxBadge}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxBadge}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty string</li>
     *   <li>variant — {@link BadgeVariant#DEFAULT}</li>
     * </ul>
     *
     * <p>Inherited from {@link io.forja.builder.FxComponentBuilder}:
     * <ul>
     *   <li>id, disabled, visible, styleClass, style, tooltip, userData</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxBadge, Builder> {

        private String text = "";
        private BadgeVariant variant = BadgeVariant.DEFAULT;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder variant(BadgeVariant variant) {
            this.variant = variant;
            return this;
        }

        @Override
        public FxBadge build() {
            FxBadge badge = new FxBadge(text, variant);
            applyBase(badge);
            return badge;
        }
    }
}
