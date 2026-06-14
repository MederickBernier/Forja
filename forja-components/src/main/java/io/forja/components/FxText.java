package io.forja.components;

import io.forja.builder.FxComponentBuilder;
import io.forja.skin.FxTextSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;

/**
 * A multi-line paragraph text component built on top of {@link Label}.
 *
 * <p>{@code FxText} extends the standard JavaFX {@code Label} - all native
 * JavaFX properties, bindings, and event APIs remain fully accessible.
 * Compared to {@link FxLabel}, {@code FxText} is tuned for paragraph
 * content: it defaults {@code wrapText} to {@code true}, uses a relaxed
 * line-height, and exposes two paragraph variants (body, lead).
 *
 * <p>Use {@link FxLabel} for headings, captions, single-line strings, or
 * inline text that needs the typography-token variants
 * (DISPLAY/HEADING/SUBHEADING/BODY/SMALL/MONO). Use {@code FxText} for
 * paragraphs of content.
 *
 * <p>The preferred way to construct an {@code FxText} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxText intro = FxText.builder()
 *          .text("Forja sits on top of JavaFX — not around it.")
 *          .variant(TextVariant.LEAD)
 *          .maxWidth(640)
 *          .build();
 *     }
 * </pre>
 *
 * <p>Constructor and direct instantiation are also supported:</p>
 * <pre>
 *     {@code
 *      FxText paragraph = new FxText("Some prose...", TextVariant.BODY);
 *     }
 * </pre>
 *
 * @see TextVariant
 * @see FxLabel
 * @see Builder
 */
public class FxText extends Label {

    private final ObjectProperty<TextVariant> variant = new SimpleObjectProperty<>(this, "variant", TextVariant.BODY);
    private final BooleanProperty muted = new SimpleBooleanProperty(this, "muted", false);

    /**
     * Creates an {@code FxText} with no text and the default variant
     * ({@link TextVariant#BODY}).
     */
    public FxText() {
        super();
        init();
    }

    /**
     * Creates an {@code FxText} with the given text and the default variant.
     *
     * @param text the paragraph text
     */
    public FxText(String text) {
        super(text);
        init();
    }

    /**
     * Creates an {@code FxText} with the given text and variant.
     *
     * @param text the paragraph text
     * @param variant the visual variant - see {@link TextVariant}
     */
    public FxText(String text, TextVariant variant) {
        super(text);
        init();
        setVariant(variant);
    }

    private void init() {
        getStyleClass().add("forja-text");
        setWrapText(true);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns the Forja text skin which drives variant + muted styling.</p>
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new FxTextSkin(this);
    }

    /** Returns the variant property. */
    public ObjectProperty<TextVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public TextVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(TextVariant v) { variant.set(v); }

    /** Returns the muted property. */
    public BooleanProperty mutedProperty() { return muted; }

    /** Returns whether this text is rendered muted. */
    public boolean isMuted() { return muted.get(); }

    /** Sets the muted state. */
    public void setMuted(boolean v) { muted.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxText}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxText}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty string</li>
     *   <li>variant — {@link TextVariant#BODY}</li>
     *   <li>muted — {@code false}</li>
     *   <li>maxWidth — unbounded (Region.USE_COMPUTED_SIZE)</li>
     *   <li>wrapText — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxText, Builder> {

        private String text = "";
        private TextVariant variant = TextVariant.BODY;
        private boolean muted = false;
        private Double maxWidth;
        private boolean wrapText = true;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder variant(TextVariant variant) {
            this.variant = variant;
            return this;
        }

        public Builder muted(boolean muted) {
            this.muted = muted;
            return this;
        }

        /**
         * Caps the rendered width to the given value in pixels. Useful for
         * keeping long paragraphs at a readable measure (~60–80ch).
         */
        public Builder maxWidth(double maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public Builder wrapText(boolean wrapText) {
            this.wrapText = wrapText;
            return this;
        }

        @Override
        public FxText build() {
            FxText text = new FxText(this.text, variant);
            text.setMuted(muted);
            text.setWrapText(wrapText);
            if (maxWidth != null) {
                text.setMaxWidth(maxWidth);
            }
            applyBase(text);
            return text;
        }
    }
}
