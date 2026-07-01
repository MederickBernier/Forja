package io.forja.components.feedbackAndStatus.fxProgressCircle;

import io.forja.builder.FxComponentBuilder;
import io.forja.tokens.SemanticVariant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.ProgressIndicator;

/**
 * A styled circular progress indicator built on {@link ProgressIndicator}.
 *
 * <p>Extends the standard JavaFX {@code ProgressIndicator} — indeterminate
 * mode is set via {@link ProgressIndicator#INDETERMINATE_PROGRESS}. Forja
 * adds a {@link SemanticVariant} accent color and a fluent builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxProgressCircle spinner = FxProgressCircle.builder()
 *          .indeterminate(true)
 *          .size(24)
 *          .build();
 *     }
 * </pre>
 *
 * @see SemanticVariant
 * @see Builder
 */
public class FxProgressCircle extends ProgressIndicator {

    private final ObjectProperty<SemanticVariant> variant = new SimpleObjectProperty<>(this, "variant", SemanticVariant.ACCENT);

    /**
     * Creates a {@code FxProgressCircle} at 0% progress with accent variant.
     */
    public FxProgressCircle() {
        super();
        getStyleClass().add("forja-progress-circle");
        variant.addListener((obs, o, v) -> applyVariantPseudoClass());
        applyVariantPseudoClass();
    }

    private void applyVariantPseudoClass() {
        for (SemanticVariant v : SemanticVariant.values()) {
            pseudoClassStateChanged(PseudoClass.getPseudoClass(v.name().toLowerCase()), false);
        }
        pseudoClassStateChanged(PseudoClass.getPseudoClass(getVariant().name().toLowerCase()), true);
    }

    /** Returns the variant property. */
    public ObjectProperty<SemanticVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public SemanticVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(SemanticVariant v) { variant.set(v == null ? SemanticVariant.ACCENT : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxProgressCircle}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxProgressCircle}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>progress — {@code 0}</li>
     *   <li>indeterminate — {@code false}</li>
     *   <li>size — inherit (unset)</li>
     *   <li>variant — {@link SemanticVariant#ACCENT}</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxProgressCircle, Builder> {

        private double progress = 0;
        private boolean indeterminate = false;
        private Double size;
        private SemanticVariant variant = SemanticVariant.ACCENT;

        public Builder progress(double progress) { this.progress = progress; return this; }
        public Builder indeterminate(boolean indeterminate) { this.indeterminate = indeterminate; return this; }
        public Builder size(double size) { this.size = size; return this; }
        public Builder variant(SemanticVariant variant) { this.variant = variant == null ? SemanticVariant.ACCENT : variant; return this; }

        @Override
        public FxProgressCircle build() {
            FxProgressCircle c = new FxProgressCircle();
            c.setVariant(variant);
            c.setProgress(indeterminate ? ProgressIndicator.INDETERMINATE_PROGRESS : progress);
            if (size != null) {
                c.setPrefWidth(size);
                c.setPrefHeight(size);
                c.setMinWidth(size);
                c.setMinHeight(size);
                c.setMaxWidth(size);
                c.setMaxHeight(size);
            }
            applyBase(c);
            return c;
        }
    }
}
