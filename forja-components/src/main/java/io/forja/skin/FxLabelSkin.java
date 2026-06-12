package io.forja.skin;

import io.forja.components.FxLabel;
import javafx.css.PseudoClass;
import javafx.scene.control.skin.LabelSkin;

/**
 * Skin for {@link FxLabel}.
 *
 * <p>Manages variant and muted pseudo-class state. Listeners on the label's
 * {@code variantProperty} and {@code mutedProperty} keep the corresponding
 * pseudo-classes in sync at all times, so CSS rules like
 * {@code .forja-label:heading} apply automatically.
 *
 * @see FxLabel
 */
public class FxLabelSkin extends LabelSkin {

    private static final PseudoClass DISPLAY    = PseudoClass.getPseudoClass("display");
    private static final PseudoClass HEADING    = PseudoClass.getPseudoClass("heading");
    private static final PseudoClass SUBHEADING = PseudoClass.getPseudoClass("subheading");
    private static final PseudoClass BODY       = PseudoClass.getPseudoClass("body");
    private static final PseudoClass SMALL      = PseudoClass.getPseudoClass("small");
    private static final PseudoClass MONO       = PseudoClass.getPseudoClass("mono");
    private static final PseudoClass MUTED      = PseudoClass.getPseudoClass("muted");

    public FxLabelSkin(FxLabel label) {
        super(label);
        updateVariant(label);
        updateMuted(label);

        label.variantProperty().addListener((obs, old, val) -> updateVariant(label));
        label.mutedProperty().addListener((obs, old, val) -> updateMuted(label));
    }

    private void updateVariant(FxLabel label) {
        label.pseudoClassStateChanged(DISPLAY,    false);
        label.pseudoClassStateChanged(HEADING,    false);
        label.pseudoClassStateChanged(SUBHEADING, false);
        label.pseudoClassStateChanged(BODY,       false);
        label.pseudoClassStateChanged(SMALL,      false);
        label.pseudoClassStateChanged(MONO,       false);

        switch (label.getVariant()) {
            case DISPLAY:
                label.pseudoClassStateChanged(DISPLAY, true);
                break;
            case HEADING:
                label.pseudoClassStateChanged(HEADING, true);
                break;
            case SUBHEADING:
                label.pseudoClassStateChanged(SUBHEADING, true);
                break;
            case BODY:
                label.pseudoClassStateChanged(BODY, true);
                break;
            case SMALL:
                label.pseudoClassStateChanged(SMALL, true);
                break;
            case MONO:
                label.pseudoClassStateChanged(MONO, true);
                break;
        }
    }

    private void updateMuted(FxLabel label) {
        label.pseudoClassStateChanged(MUTED, label.isMuted());
    }
}
