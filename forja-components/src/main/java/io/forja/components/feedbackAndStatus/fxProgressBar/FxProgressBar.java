package io.forja.components.feedbackAndStatus.fxProgressBar;

import io.forja.builder.FxComponentBuilder;
import io.forja.tokens.SemanticVariant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.ProgressBar;

/**
 * A styled progress bar built on {@link ProgressBar}.
 *
 * <p>Extends the standard JavaFX {@code ProgressBar} — all native properties
 * (including indeterminate mode via {@link ProgressBar#INDETERMINATE_PROGRESS})
 * remain fully accessible. Forja adds a {@link SemanticVariant} accent color
 * and a fluent builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxProgressBar loading = FxProgressBar.builder()
 *          .progress(0.42)
 *          .variant(SemanticVariant.ACCENT)
 *          .build();
 *     }
 * </pre>
 *
 * @see SemanticVariant
 * @see Builder
 */
public class FxProgressBar extends ProgressBar {

    private final ObjectProperty<SemanticVariant> variant = new SimpleObjectProperty<>(this, "variant", SemanticVariant.ACCENT);

    /**
     * Creates a {@code FxProgressBar} at 0% progress with accent variant.
     */
    public FxProgressBar() {
        super();
        getStyleClass().add("forja-progress-bar");
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
     * Returns a new {@link Builder} for constructing an {@code FxProgressBar}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxProgressBar}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>progress — {@code 0}</li>
     *   <li>indeterminate — {@code false}</li>
     *   <li>variant — {@link SemanticVariant#ACCENT}</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxProgressBar, Builder> {

        private double progress = 0;
        private boolean indeterminate = false;
        private SemanticVariant variant = SemanticVariant.ACCENT;

        public Builder progress(double progress) { this.progress = progress; return this; }
        public Builder indeterminate(boolean indeterminate) { this.indeterminate = indeterminate; return this; }
        public Builder variant(SemanticVariant variant) { this.variant = variant == null ? SemanticVariant.ACCENT : variant; return this; }

        @Override
        public FxProgressBar build() {
            FxProgressBar b = new FxProgressBar();
            b.setVariant(variant);
            b.setProgress(indeterminate ? ProgressBar.INDETERMINATE_PROGRESS : progress);
            applyBase(b);
            return b;
        }
    }
}
