package io.forja.components.utilities.fxIcon;

import io.forja.tokens.SemanticVariant;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * A styled icon component built on top of Ikonli's {@link FontIcon}.
 *
 * <p>{@code FxIcon} renders glyphs from any Ikonli icon pack on the classpath
 * (Feather is bundled by default — add others like {@code ikonli-material-pack}
 * to use additional sets). Forja adds a semantic color variant system that
 * binds to the active theme, and a fluent builder API.
 *
 * <p>The preferred way to construct an {@code FxIcon} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxIcon save = FxIcon.builder()
 *          .literal("fth-save")
 *          .size(20)
 *          .variant(SemanticVariant.ACCENT)
 *          .build();
 *     }
 * </pre>
 *
 * <p>Constructor and direct instantiation are also supported for simpler cases:</p>
 * <pre>
 *     {@code
 *      FxIcon save = new FxIcon("fth-save");
 *     }
 * </pre>
 *
 * <p>Since {@code FxIcon} extends Ikonli's {@code FontIcon}, every Ikonli
 * API remains accessible — {@link #setIconColor(javafx.scene.paint.Paint)},
 * {@link #setIconSize(int)}, and so on can still be used directly if you
 * need to escape the Forja variant system.</p>
 *
 * <p>Unlike {@link FxButton} and {@link FxLabel}, {@code FxIcon} does not
 * extend a JavaFX {@link javafx.scene.control.Control} — it's a {@code Text}
 * subclass. Tooltips and {@code disabled} are not exposed on the builder
 * because they don't apply to text nodes; wrap the icon in a Forja control
 * if you need those behaviors.</p>
 *
 * @see SemanticVariant
 * @see Builder
 */
public class FxIcon extends FontIcon {

    private final ObjectProperty<SemanticVariant> variant = new SimpleObjectProperty<>(this, "variant", SemanticVariant.DEFAULT);

    /**
     * Creates an {@code FxIcon} with no glyph and the default variant
     * ({@link SemanticVariant#DEFAULT}).
     */
    public FxIcon() {
        super();
        getStyleClass().add("forja-icon");
        wireVariantPseudoClass();
        applyVariantPseudoClass();
    }

    /**
     * Creates an {@code FxIcon} with the given Ikonli icon literal and the
     * default variant ({@link SemanticVariant#DEFAULT}).
     *
     * @param iconLiteral the Ikonli icon literal, e.g. {@code "fth-save"}
     */
    public FxIcon(String iconLiteral) {
        super(iconLiteral);
        getStyleClass().add("forja-icon");
        wireVariantPseudoClass();
        applyVariantPseudoClass();
    }

    /**
     * Creates an {@code FxIcon} with the given Ikonli literal and variant.
     *
     * @param iconLiteral the Ikonli icon literal, e.g. {@code "fth-save"}
     * @param variant the semantic color variant - see {@link SemanticVariant}
     */
    public FxIcon(String iconLiteral, SemanticVariant variant) {
        this(iconLiteral);
        setVariant(variant);
    }

    /**
     * Returns the variant property of this icon.
     *
     * <p>The variant controls the color of the icon via the active theme.
     * It can be observed or bound like any standard JavaFX property:</p>
     * <pre>
     *     {@code
     *      icon.variantProperty().addListener((obs, old, val) -> ...)
     *     }
     * </pre>
     *
     * @return the {@link ObjectProperty} holding the current {@link SemanticVariant}
     */
    public ObjectProperty<SemanticVariant> variantProperty() { return variant; }

    /**
     * Returns the current variant of the icon.
     *
     * @return the current {@link SemanticVariant}, never {@code null}
     */
    public SemanticVariant getVariant() { return variant.get(); }

    /**
     * Sets the variant of this icon.
     *
     * @param v the desired {@link SemanticVariant}, must not be {@code null}
     */
    public void setVariant(SemanticVariant v) { variant.set(v); }

    private void wireVariantPseudoClass() {
        variant.addListener((obs, old, val) -> applyVariantPseudoClass());
    }

    private void applyVariantPseudoClass() {
        SemanticVariant.applyTo(this, getVariant());
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxIcon}
     * with a fluent API.
     *
     * <pre>{@code
     *  FxIcon save = FxIcon.builder()
     *      .literal("fth-save")
     *      .size(20)
     *      .variant(SemanticVariant.ACCENT)
     *      .build();
     * }</pre>
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxIcon}.
     *
     * <p>All builder methods return {@code this} to support chaining.
     * Call {@link #build()} to produce the configured {@link FxIcon}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>literal — {@code null} (must be set, or the icon renders empty)</li>
     *   <li>size — inherited from the {@code .forja-icon} CSS rule (16px)</li>
     *   <li>variant — {@link SemanticVariant#DEFAULT}</li>
     * </ul>
     *
     * <p>{@code FxIcon} is not a JavaFX {@link javafx.scene.control.Control}, so
     * this builder does not extend {@link io.forja.builder.FxComponentBuilder}.
     * The control-only properties ({@code disabled}, {@code tooltip},
     * {@link io.forja.styling.FxStyle}) are not exposed here; the
     * available shared properties are {@code id}, {@code visible},
     * {@code styleClass}, and {@code userData}.
     */
    public static class Builder extends FxNodeBuilder<FxIcon, Builder> {

        private String iconLiteral;
        private Integer size;
        private SemanticVariant variant = SemanticVariant.DEFAULT;

        /**
         * Sets the Ikonli icon literal to render.
         *
         * @param iconLiteral the Ikonli literal, e.g. {@code "fth-save"}
         * @return this builder
         */
        public Builder literal(String iconLiteral) {
            this.iconLiteral = iconLiteral;
            return this;
        }

        /**
         * Sets the rendered icon size in pixels.
         *
         * <p>If not set, the icon uses the default size declared in the
         * {@code .forja-icon} CSS rule (16px).
         *
         * @param size the icon size in pixels
         * @return this builder
         */
        public Builder size(int size) {
            this.size = size;
            return this;
        }

        /**
         * Sets the semantic color variant of the icon.
         *
         * <p>Defaults to {@link SemanticVariant#DEFAULT} if not specified.
         *
         * @param variant the desired variant, must not be {@code null}
         * @return this builder
         */
        public Builder variant(SemanticVariant variant) {
            this.variant = variant;
            return this;
        }

        /**
         * Sets the CSS id of the icon.
         *
         * @param id the CSS id
         * @return this builder
         */
        /**
         * Sets the visibility of the icon. Defaults to {@code true}.
         *
         * @param visible {@code false} to hide the icon
         * @return this builder
         */
        /**
         * Adds one or more CSS style classes to the icon.
         *
         * @param classes one or more CSS class names to add
         * @return this builder
         */
        /**
         * Attaches arbitrary user data to the icon.
         *
         * @param userData the object to attach, or {@code null}
         * @return this builder
         */
        /**
         * Constructs and returns the configured {@link FxIcon}.
         *
         * @return a new {@link FxIcon} with the properties set on this builder
         */
        public FxIcon build() {
            FxIcon icon = iconLiteral == null ? new FxIcon() : new FxIcon(iconLiteral, variant);
            if (iconLiteral == null) {
                icon.setVariant(variant);
            }
            if (size != null) {
                icon.setIconSize(size);
            }
            applyBase(icon);
            return icon;
        }
    }
}
