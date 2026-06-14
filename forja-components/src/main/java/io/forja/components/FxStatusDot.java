package io.forja.components;

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
 *          .variant(StatusDotVariant.SUCCESS)
 *          .radius(5)
 *          .build();
 *     }
 * </pre>
 *
 * <p>Constructor and direct instantiation are also supported:</p>
 * <pre>
 *     {@code
 *      FxStatusDot dot = new FxStatusDot(StatusDotVariant.WARNING);
 *     }
 * </pre>
 *
 * <p>Like {@link FxIcon}, {@code FxStatusDot} is not a JavaFX
 * {@link javafx.scene.control.Control} — its builder is standalone and does
 * not extend {@link io.forja.builder.FxComponentBuilder}. Pseudo-class wiring
 * is inlined in the component since {@link Circle} has no skin layer.
 *
 * @see StatusDotVariant
 * @see Builder
 */
public class FxStatusDot extends Circle {

    private static final double DEFAULT_RADIUS = 5.0;

    private static final PseudoClass DEFAULT = PseudoClass.getPseudoClass("default");
    private static final PseudoClass MUTED   = PseudoClass.getPseudoClass("muted");
    private static final PseudoClass ACCENT  = PseudoClass.getPseudoClass("accent");
    private static final PseudoClass SUCCESS = PseudoClass.getPseudoClass("success");
    private static final PseudoClass WARNING = PseudoClass.getPseudoClass("warning");
    private static final PseudoClass DANGER  = PseudoClass.getPseudoClass("danger");
    private static final PseudoClass INFO    = PseudoClass.getPseudoClass("info");

    private final ObjectProperty<StatusDotVariant> variant = new SimpleObjectProperty<>(this, "variant", StatusDotVariant.DEFAULT);

    /**
     * Creates an {@code FxStatusDot} with the default radius (5px) and
     * default variant ({@link StatusDotVariant#DEFAULT}).
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
    public FxStatusDot(StatusDotVariant variant) {
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
    public FxStatusDot(double radius, StatusDotVariant variant) {
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

    /** Returns the variant property. */
    public ObjectProperty<StatusDotVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public StatusDotVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(StatusDotVariant v) { variant.set(v); }

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
     *   <li>variant — {@link StatusDotVariant#DEFAULT}</li>
     *   <li>radius — 5px</li>
     * </ul>
     */
    public static class Builder {

        private double radius = DEFAULT_RADIUS;
        private StatusDotVariant variant = StatusDotVariant.DEFAULT;
        private String id;
        private boolean visible = true;
        private final java.util.List<String> styleClasses = new java.util.ArrayList<>();
        private Object userData;

        public Builder radius(double radius) {
            this.radius = radius;
            return this;
        }

        public Builder variant(StatusDotVariant variant) {
            this.variant = variant;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder styleClass(String... classes) {
            for (String c : classes) {
                if (c != null && !c.isEmpty()) {
                    styleClasses.add(c);
                }
            }
            return this;
        }

        public Builder userData(Object userData) {
            this.userData = userData;
            return this;
        }

        public FxStatusDot build() {
            FxStatusDot dot = new FxStatusDot(radius, variant);
            if (id != null) {
                dot.setId(id);
            }
            dot.setVisible(visible);
            if (!styleClasses.isEmpty()) {
                dot.getStyleClass().addAll(styleClasses);
            }
            if (userData != null) {
                dot.setUserData(userData);
            }
            return dot;
        }
    }
}
