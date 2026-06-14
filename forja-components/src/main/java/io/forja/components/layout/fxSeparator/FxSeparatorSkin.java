package io.forja.components.layout.fxSeparator;

import io.forja.components.layout.fxSeparator.FxSeparator;
import javafx.css.PseudoClass;
import javafx.scene.control.skin.SeparatorSkin;

/**
 * Skin for {@link FxSeparator}.
 *
 * <p>Manages variant pseudo-class state. The listener on the separator's
 * {@code variantProperty} keeps the corresponding pseudo-class in sync at
 * all times, so CSS rules like {@code .forja-separator:hairline} apply
 * automatically.
 *
 * @see FxSeparator
 */
public class FxSeparatorSkin extends SeparatorSkin {

    private static final PseudoClass HAIRLINE = PseudoClass.getPseudoClass("hairline");
    private static final PseudoClass DEFAULT  = PseudoClass.getPseudoClass("default");
    private static final PseudoClass STRONG   = PseudoClass.getPseudoClass("strong");

    public FxSeparatorSkin(FxSeparator separator) {
        super(separator);
        updateVariant(separator);

        separator.variantProperty().addListener((obs, old, val) -> updateVariant(separator));
    }

    private void updateVariant(FxSeparator separator) {
        separator.pseudoClassStateChanged(HAIRLINE, false);
        separator.pseudoClassStateChanged(DEFAULT,  false);
        separator.pseudoClassStateChanged(STRONG,   false);

        switch (separator.getVariant()) {
            case HAIRLINE:
                separator.pseudoClassStateChanged(HAIRLINE, true);
                break;
            case DEFAULT:
                separator.pseudoClassStateChanged(DEFAULT, true);
                break;
            case STRONG:
                separator.pseudoClassStateChanged(STRONG, true);
                break;
        }
    }
}
