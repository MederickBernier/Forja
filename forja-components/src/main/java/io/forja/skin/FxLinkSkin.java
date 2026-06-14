package io.forja.skin;

import io.forja.components.FxLink;
import javafx.css.PseudoClass;
import javafx.scene.control.skin.HyperlinkSkin;

/**
 * Skin for {@link FxLink}.
 *
 * <p>Manages variant pseudo-class state. The listener on the link's
 * {@code variantProperty} keeps the corresponding pseudo-class in sync at
 * all times, so CSS rules like {@code .forja-link:external} apply
 * automatically.
 *
 * @see FxLink
 */
public class FxLinkSkin extends HyperlinkSkin {

    private static final PseudoClass DEFAULT  = PseudoClass.getPseudoClass("default");
    private static final PseudoClass MUTED    = PseudoClass.getPseudoClass("muted");
    private static final PseudoClass EXTERNAL = PseudoClass.getPseudoClass("external");

    public FxLinkSkin(FxLink link) {
        super(link);
        updateVariant(link);

        link.variantProperty().addListener((obs, old, val) -> updateVariant(link));
    }

    private void updateVariant(FxLink link) {
        link.pseudoClassStateChanged(DEFAULT,  false);
        link.pseudoClassStateChanged(MUTED,    false);
        link.pseudoClassStateChanged(EXTERNAL, false);

        switch (link.getVariant()) {
            case DEFAULT:
                link.pseudoClassStateChanged(DEFAULT, true);
                break;
            case MUTED:
                link.pseudoClassStateChanged(MUTED, true);
                break;
            case EXTERNAL:
                link.pseudoClassStateChanged(EXTERNAL, true);
                break;
        }
    }
}
