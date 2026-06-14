package io.forja.components.feedbackAndStatus.fxStatusDot;

import io.forja.tokens.SemanticVariant;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.shape.Circle;

/**
 * A small colored circle used to indicate a discrete status value.
 *
 * <p>{@code FxStatusDot} extends {@link Circle}, so it composes with any
 * standard JavaFX layout. The variant property drives a token-bound fill
 * via the {@code .forja-status-dot} style class and its semantic
 * pseudo-classes.
 *
 * <p>The preferred way to construct an {@code FxStatusDot} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxStatusDot online = FxStatusDot.builder()
 *          .variant(SemanticVariant.SUCCESS)
 *          .radius(5)
 *          .build();
 *     }
 * </pre>
 *
 * <p>Constructor and direct instantiation are also supported:</p>
 * <pre>
 *     {@code
 *      FxStatusDot dot = new FxStatusDot(SemanticVariant.WARNING);
 *     }
 * </pre>
 *
 * <p>Like {@link FxIcon}, {@code FxStatusDot} is not a JavaFX
 * {@link javafx.scene.control.Control} — its builder is standalone and does
 * not extend {@link io.forja.builder.FxComponentBuilder}. Pseudo-class wiring
 * is inlined in the component since {@link Circle} has no skin layer.
 *
 * @see SemanticVariant
 * @see Builder
 */
public class FxStatusDot extends Circle {

    private static final double DEFAULT_RADIUS = 5.0;

    private final ObjectProperty<SemanticVariant> variant = new SimpleObjectProperty<>(this, "variant", SemanticVariant.DEFAULT);

    /**
     * Creates an {@code FxStatusDot} with the default radius (5px) and
     * default variant ({@link SemanticVariant#DEFAULT}).
     */
    public FxStatusDot() {
        super(DEFAULT_RADIUS);
        init();
    }

    /**
     * Creates an {@code FxStatusDot} with the default radius and the given variant.
     *
     * @param variant the semantic color variant
     */
    public FxStatusDot(SemanticVariant variant) {
        super(DEFAULT_RADIUS);
        init();
        setVariant(variant);
    }

    /**
     * Creates an {@code FxStatusDot} with the given radius and variant.
     *
     * @param radius the circle radius in pixels
     * @param variant the semantic color variant
     */
    public FxStatusDot(double radius, SemanticVariant variant) {
        super(radius);
        init();
        setVariant(variant);
    }

    private void init() {
        getStyleClass().add("forja-status-dot");
        applyVariantPseudoClass();
        variant.addListener((obs, old, val) -> applyVariantPseudoClass());
    }

    private void applyVariantPseudoClass() {
        SemanticVariant.applyTo(this, getVariant());
    }

    /** Returns the variant property. */
    public ObjectProperty<SemanticVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public SemanticVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(SemanticVariant v) { variant.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxStatusDot}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxStatusDot}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>variant — {@link SemanticVariant#DEFAULT}</li>
     *   <li>radius — 5px</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxStatusDot, Builder> {

        private double radius = DEFAULT_RADIUS;
        private SemanticVariant variant = SemanticVariant.DEFAULT;

        public Builder radius(double radius) {
            this.radius = radius;
            return this;
        }

        public Builder variant(SemanticVariant variant) {
            this.variant = variant;
            return this;
        }

        public FxStatusDot build() {
            FxStatusDot dot = new FxStatusDot(radius, variant);
            applyBase(dot);
            return dot;
        }
    }
}
