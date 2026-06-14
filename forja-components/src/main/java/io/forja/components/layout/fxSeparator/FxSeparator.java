package io.forja.components.layout.fxSeparator;

import io.forja.builder.FxComponentBuilder;
import io.forja.components.layout.fxSeparator.FxSeparatorSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.control.Skin;

/**
 * A styled separator component built on top of {@link Separator}.
 *
 * <p>{@code FxSeparator} extends the standard JavaFX {@code Separator} - all
 * native JavaFX properties, bindings, and event APIs remain fully accessible.
 * Forja adds a thickness variant system and a fluent builder API.
 *
 * <p>The preferred way to construct an {@code FxSeparator} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxSeparator divider = FxSeparator.builder()
 *          .orientation(Orientation.HORIZONTAL)
 *          .variant(SeparatorVariant.HAIRLINE)
 *          .build();
 *     }
 * </pre>
 *
 * <p>Constructor and direct instantiation are also supported for simpler cases:</p>
 * <pre>
 *     {@code
 *      FxSeparator divider = new FxSeparator(Orientation.HORIZONTAL, SeparatorVariant.STRONG);
 *     }
 * </pre>
 *
 * <p>Since {@code FxSeparator} is a standard {@code Separator}, it is fully
 * compatible with FXML, SceneBuilder, and all existing JavaFX APIs.</p>
 *
 * @see SeparatorVariant
 * @see Builder
 */
public class FxSeparator extends Separator {

    private final ObjectProperty<SeparatorVariant> variant = new SimpleObjectProperty<>(this, "variant", SeparatorVariant.DEFAULT);

    /**
     * Creates an {@code FxSeparator} with horizontal orientation and the
     * default variant ({@link SeparatorVariant#DEFAULT}).
     */
    public FxSeparator() {
        super();
        getStyleClass().add("forja-separator");
    }

    /**
     * Creates an {@code FxSeparator} with the given orientation and the
     * default variant ({@link SeparatorVariant#DEFAULT}).
     *
     * @param orientation the orientation of the separator
     */
    public FxSeparator(Orientation orientation) {
        super(orientation);
        getStyleClass().add("forja-separator");
    }

    /**
     * Creates an {@code FxSeparator} with the given orientation and variant.
     *
     * @param orientation the orientation of the separator
     * @param variant the visual variant - see {@link SeparatorVariant}
     */
    public FxSeparator(Orientation orientation, SeparatorVariant variant) {
        this(orientation);
        setVariant(variant);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns the Forja separator skin which drives variant-based styling.</p>
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new FxSeparatorSkin(this);
    }

    /**
     * Returns the variant property of this separator.
     *
     * <p>The variant controls the visible thickness of the separator. It can
     * be observed or bound like any standard JavaFX property:</p>
     * <pre>
     *     {@code
     *      separator.variantProperty().addListener((obs, old, val) -> ...)
     *     }
     * </pre>
     *
     * @return the {@link ObjectProperty} holding the current {@link SeparatorVariant}
     */
    public ObjectProperty<SeparatorVariant> variantProperty() { return variant; }

    /**
     * Returns the current variant of the separator.
     *
     * @return the current {@link SeparatorVariant}, never {@code null}
     */
    public SeparatorVariant getVariant() { return variant.get(); }

    /**
     * Sets the variant of this separator.
     *
     * @param v the desired {@link SeparatorVariant}, must not be {@code null}
     */
    public void setVariant(SeparatorVariant v) { variant.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSeparator}
     * with a fluent API.
     *
     * <pre>{@code
     *  FxSeparator divider = FxSeparator.builder()
     *      .orientation(Orientation.VERTICAL)
     *      .variant(SeparatorVariant.STRONG)
     *      .build();
     * }</pre>
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxSeparator}.
     *
     * <p>All builder methods return {@code this} to support chaining.
     * Call {@link #build()} to produce the configured {@link FxSeparator}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>orientation — {@link Orientation#HORIZONTAL}</li>
     *   <li>variant — {@link SeparatorVariant#DEFAULT}</li>
     * </ul>
     *
     * <p>Inherited from {@link io.forja.builder.FxComponentBuilder}:
     * <ul>
     *   <li>id, disabled, visible, styleClass, style, tooltip, userData</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxSeparator, Builder> {

        private Orientation orientation = Orientation.HORIZONTAL;
        private SeparatorVariant variant = SeparatorVariant.DEFAULT;

        /**
         * Sets the orientation of the separator.
         *
         * <p>Defaults to {@link Orientation#HORIZONTAL} if not specified.
         *
         * @param orientation the desired orientation, must not be {@code null}
         * @return this builder
         */
        public Builder orientation(Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        /**
         * Sets the visual variant of the separator.
         *
         * <p>Defaults to {@link SeparatorVariant#DEFAULT} if not specified.
         *
         * @param variant the desired variant, must not be {@code null}
         * @return this builder
         */
        public Builder variant(SeparatorVariant variant) {
            this.variant = variant;
            return this;
        }

        /**
         * Constructs and returns the configured {@link FxSeparator}.
         *
         * @return a new {@link FxSeparator} with the properties set on this builder
         */
        @Override
        public FxSeparator build() {
            FxSeparator separator = new FxSeparator(orientation, variant);
            applyBase(separator);
            return separator;
        }
    }
}
