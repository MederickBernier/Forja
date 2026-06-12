package io.forja.components;

import io.forja.builder.FxComponentBuilder;
import io.forja.skin.FxLabelSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;

/**
 * A styled label component built on top of {@link Label}.
 *
 * <p>{@code FxLabel} extends the standard JavaFX {@code Label} - all native
 * JavaFX properties, bindings, and event APIs remain fully accessible. Forja
 * adds a typography variant system, a muted (de-emphasized) state, and a
 * fluent builder API.
 *
 * <p>The preferred way to construct an {@code FxLabel} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxLabel title = FxLabel.builder()
 *          .text("Settings")
 *          .variant(LabelVariant.HEADING)
 *          .build();
 *     }
 * </pre>
 *
 * <p>Constructor and direct instantiation are also supported for simpler cases:</p>
 * <pre>
 *     {@code
 *      FxLabel label = new FxLabel("Settings", LabelVariant.HEADING);
 *     }
 * </pre>
 *
 * <p>Since {@code FxLabel} is a standard {@code Label}, it is fully
 * compatible with FXML, SceneBuilder, and all existing JavaFX APIs.</p>
 *
 * @see LabelVariant
 * @see Builder
 */
public class FxLabel extends Label {

    private final ObjectProperty<LabelVariant> variant = new SimpleObjectProperty<>(this, "variant", LabelVariant.BODY);
    private final BooleanProperty muted = new SimpleBooleanProperty(this, "muted", false);

    /**
     * Creates an {@code FxLabel} with no text and the default variant
     * ({@link LabelVariant#BODY}).
     */
    public FxLabel() {
        super();
        getStyleClass().add("forja-label");
    }

    /**
     * Creates an {@code FxLabel} with the given text and the default variant
     * ({@link LabelVariant#BODY}).
     *
     * @param text the text displayed by the label
     */
    public FxLabel(String text) {
        super(text);
        getStyleClass().add("forja-label");
    }

    /**
     * Creates an {@code FxLabel} with the given text and variant.
     *
     * @param text the text displayed by the label
     * @param variant the visual variant - see {@link LabelVariant}
     */
    public FxLabel(String text, LabelVariant variant) {
        this(text);
        setVariant(variant);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns the Forja label skin which drives variant-based typography
     * and the muted-state pseudo-class.</p>
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new FxLabelSkin(this);
    }

    /**
     * Returns the variant property of this label.
     *
     * <p>The variant controls the typography style of the label. It can be
     * observed or bound like any standard JavaFX property:</p>
     * <pre>
     *     {@code
     *      label.variantProperty().addListener((obs, old, val) -> ...)
     *     }
     * </pre>
     *
     * @return the {@link ObjectProperty} holding the current {@link LabelVariant}
     */
    public ObjectProperty<LabelVariant> variantProperty() { return variant; }

    /**
     * Returns the current variant of the label.
     *
     * @return the current {@link LabelVariant}, never {@code null}
     */
    public LabelVariant getVariant() { return variant.get(); }

    /**
     * Sets the variant of this label.
     *
     * @param v the desired {@link LabelVariant}, must not be {@code null}
     */
    public void setVariant(LabelVariant v) { variant.set(v); }

    /**
     * Returns the muted property of this label.
     *
     * <p>When {@code true}, the label is rendered using the muted text color
     * defined by the active theme. Useful for hints, captions, and secondary
     * information. Bind this to an observable to drive muted state reactively:</p>
     * <pre>
     *     {@code
     *      label.mutedProperty().bind(viewModel.isOptional());
     *     }
     * </pre>
     *
     * @return the {@link BooleanProperty} controlling the muted state
     */
    public BooleanProperty mutedProperty() { return muted; }

    /**
     * Returns whether this label is currently rendered in the muted state.
     *
     * @return {@code true} if the label is muted, {@code false} otherwise
     */
    public boolean isMuted() { return muted.get(); }

    /**
     * Sets the muted state of this label.
     *
     * @param v {@code true} to render the label muted, {@code false} otherwise
     */
    public void setMuted(boolean v) { muted.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxLabel}
     * with a fluent API.
     *
     * <pre>{@code
     *  FxLabel hint = FxLabel.builder()
     *      .text("Optional")
     *      .variant(LabelVariant.SMALL)
     *      .muted(true)
     *      .build();
     * }</pre>
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxLabel}.
     *
     * <p>All builder methods return {@code this} to support chaining.
     * Call {@link #build()} to produce the configured {@link FxLabel}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty string</li>
     *   <li>variant — {@link LabelVariant#BODY}</li>
     *   <li>muted — {@code false}</li>
     *   <li>graphic — none</li>
     * </ul>
     *
     * <p>Inherited from {@link io.forja.builder.FxComponentBuilder}:
     * <ul>
     *   <li>id, disabled, visible, styleClass, style, tooltip, userData</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxLabel, Builder> {

        private String text = "";
        private LabelVariant variant = LabelVariant.BODY;
        private boolean muted = false;
        private Node graphic;

        /**
         * Sets the text displayed by the label.
         *
         * @param text the label text, must not be {@code null}
         * @return this builder
         */
        public Builder text(String text) {
            this.text = text;
            return this;
        }

        /**
         * Sets the typography variant of the label.
         *
         * <p>Defaults to {@link LabelVariant#BODY} if not specified.
         *
         * @param variant the desired variant, must not be {@code null}
         * @return this builder
         */
        public Builder variant(LabelVariant variant) {
            this.variant = variant;
            return this;
        }

        /**
         * Sets the initial muted state of the label.
         *
         * <p>When {@code true}, the label uses the muted text color.
         * Defaults to {@code false}.
         *
         * @param muted {@code true} to render the label muted
         * @return this builder
         */
        public Builder muted(boolean muted) {
            this.muted = muted;
            return this;
        }

        /**
         * Sets a graphic node (typically an icon) to display alongside the text.
         *
         * @param graphic the graphic node, or {@code null} for no graphic
         * @return this builder
         */
        public Builder graphic(Node graphic) {
            this.graphic = graphic;
            return this;
        }

        /**
         * Constructs and returns the configured {@link FxLabel}.
         *
         * @return a new {@link FxLabel} with the properties set on this builder
         */
        @Override
        public FxLabel build() {
            FxLabel label = new FxLabel(text, variant);
            label.setMuted(muted);
            if (graphic != null) {
                label.setGraphic(graphic);
            }
            applyBase(label);
            return label;
        }
    }
}
