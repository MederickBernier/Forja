package io.forja.components;

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
 *          .variant(IconVariant.ACCENT)
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
 * @see IconVariant
 * @see Builder
 */
public class FxIcon extends FontIcon {

    private static final PseudoClass DEFAULT = PseudoClass.getPseudoClass("default");
    private static final PseudoClass MUTED   = PseudoClass.getPseudoClass("muted");
    private static final PseudoClass ACCENT  = PseudoClass.getPseudoClass("accent");
    private static final PseudoClass SUCCESS = PseudoClass.getPseudoClass("success");
    private static final PseudoClass WARNING = PseudoClass.getPseudoClass("warning");
    private static final PseudoClass DANGER  = PseudoClass.getPseudoClass("danger");
    private static final PseudoClass INFO    = PseudoClass.getPseudoClass("info");

    private final ObjectProperty<IconVariant> variant = new SimpleObjectProperty<>(this, "variant", IconVariant.DEFAULT);

    /**
     * Creates an {@code FxIcon} with no glyph and the default variant
     * ({@link IconVariant#DEFAULT}).
     */
    public FxIcon() {
        super();
        getStyleClass().add("forja-icon");
        wireVariantPseudoClass();
        applyVariantPseudoClass();
    }

    /**
     * Creates an {@code FxIcon} with the given Ikonli icon literal and the
     * default variant ({@link IconVariant#DEFAULT}).
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
     * @param variant the semantic color variant - see {@link IconVariant}
     */
    public FxIcon(String iconLiteral, IconVariant variant) {
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
     * @return the {@link ObjectProperty} holding the current {@link IconVariant}
     */
    public ObjectProperty<IconVariant> variantProperty() { return variant; }

    /**
     * Returns the current variant of the icon.
     *
     * @return the current {@link IconVariant}, never {@code null}
     */
    public IconVariant getVariant() { return variant.get(); }

    /**
     * Sets the variant of this icon.
     *
     * @param v the desired {@link IconVariant}, must not be {@code null}
     */
    public void setVariant(IconVariant v) { variant.set(v); }

    private void wireVariantPseudoClass() {
        variant.addListener((obs, old, val) -> applyVariantPseudoClass());
    }

    private void applyVariantPseudoClass() {
        pseudoClassStateChanged(DEFAULT, false);
        pseudoClassStateChanged(MUTED,   false);
        pseudoClassStateChanged(ACCENT,  false);
        pseudoClassStateChanged(SUCCESS, false);
        pseudoClassStateChanged(WARNING, false);
        pseudoClassStateChanged(DANGER,  false);
        pseudoClassStateChanged(INFO,    false);

        switch (getVariant()) {
            case DEFAULT: pseudoClassStateChanged(DEFAULT, true); break;
            case MUTED:   pseudoClassStateChanged(MUTED,   true); break;
            case ACCENT:  pseudoClassStateChanged(ACCENT,  true); break;
            case SUCCESS: pseudoClassStateChanged(SUCCESS, true); break;
            case WARNING: pseudoClassStateChanged(WARNING, true); break;
            case DANGER:  pseudoClassStateChanged(DANGER,  true); break;
            case INFO:    pseudoClassStateChanged(INFO,    true); break;
        }
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxIcon}
     * with a fluent API.
     *
     * <pre>{@code
     *  FxIcon save = FxIcon.builder()
     *      .literal("fth-save")
     *      .size(20)
     *      .variant(IconVariant.ACCENT)
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
     *   <li>variant — {@link IconVariant#DEFAULT}</li>
     * </ul>
     *
     * <p>{@code FxIcon} is not a JavaFX {@link javafx.scene.control.Control}, so
     * this builder does not extend {@link io.forja.builder.FxComponentBuilder}.
     * The control-only properties ({@code disabled}, {@code tooltip},
     * {@link io.forja.styling.FxStyle}) are not exposed here; the
     * available shared properties are {@code id}, {@code visible},
     * {@code styleClass}, and {@code userData}.
     */
    public static class Builder {

        private String iconLiteral;
        private Integer size;
        private IconVariant variant = IconVariant.DEFAULT;
        private String id;
        private boolean visible = true;
        private final java.util.List<String> styleClasses = new java.util.ArrayList<>();
        private Object userData;

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
         * <p>Defaults to {@link IconVariant#DEFAULT} if not specified.
         *
         * @param variant the desired variant, must not be {@code null}
         * @return this builder
         */
        public Builder variant(IconVariant variant) {
            this.variant = variant;
            return this;
        }

        /**
         * Sets the CSS id of the icon.
         *
         * @param id the CSS id
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the visibility of the icon. Defaults to {@code true}.
         *
         * @param visible {@code false} to hide the icon
         * @return this builder
         */
        public Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        /**
         * Adds one or more CSS style classes to the icon.
         *
         * @param classes one or more CSS class names to add
         * @return this builder
         */
        public Builder styleClass(String... classes) {
            for (String c : classes) {
                if (c != null && !c.isEmpty()) {
                    styleClasses.add(c);
                }
            }
            return this;
        }

        /**
         * Attaches arbitrary user data to the icon.
         *
         * @param userData the object to attach, or {@code null}
         * @return this builder
         */
        public Builder userData(Object userData) {
            this.userData = userData;
            return this;
        }

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
            if (id != null) {
                icon.setId(id);
            }
            icon.setVisible(visible);
            if (!styleClasses.isEmpty()) {
                icon.getStyleClass().addAll(styleClasses);
            }
            if (userData != null) {
                icon.setUserData(userData);
            }
            return icon;
        }
    }
}
