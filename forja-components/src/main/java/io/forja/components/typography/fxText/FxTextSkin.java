package io.forja.components.typography.fxText;

import io.forja.components.typography.fxText.FxText;
import javafx.css.PseudoClass;
import javafx.scene.control.skin.LabelSkin;

/**
 * Skin for {@link FxText}.
 *
 * <p>Manages variant and muted pseudo-class state via listeners on the
 * text's {@code variantProperty} and {@code mutedProperty}.
 *
 * @see FxText
 */
public class FxTextSkin extends LabelSkin {

    private static final PseudoClass BODY  = PseudoClass.getPseudoClass("body");
    private static final PseudoClass LEAD  = PseudoClass.getPseudoClass("lead");
    private static final PseudoClass MUTED = PseudoClass.getPseudoClass("muted");

    public FxTextSkin(FxText text) {
        super(text);
        updateVariant(text);
        updateMuted(text);

        text.variantProperty().addListener((obs, old, val) -> updateVariant(text));
        text.mutedProperty().addListener((obs, old, val) -> updateMuted(text));
    }

    private void updateVariant(FxText text) {
        text.pseudoClassStateChanged(BODY, false);
        text.pseudoClassStateChanged(LEAD, false);

        switch (text.getVariant()) {
            case BODY:
                text.pseudoClassStateChanged(BODY, true);
                break;
            case LEAD:
                text.pseudoClassStateChanged(LEAD, true);
                break;
        }
    }

    private void updateMuted(FxText text) {
        text.pseudoClassStateChanged(MUTED, text.isMuted());
    }
}
