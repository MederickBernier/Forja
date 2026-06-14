package io.forja.skin;

import io.forja.components.FxBadge;
import javafx.css.PseudoClass;
import javafx.scene.control.skin.LabelSkin;

/**
 * Skin for {@link FxBadge}.
 *
 * <p>Toggles the {@code :default}, {@code :muted}, {@code :accent},
 * {@code :success}, {@code :warning}, {@code :danger}, {@code :info}
 * pseudo-classes based on the badge's variant property.
 *
 * @see FxBadge
 */
public class FxBadgeSkin extends LabelSkin {

    private static final PseudoClass DEFAULT = PseudoClass.getPseudoClass("default");
    private static final PseudoClass MUTED   = PseudoClass.getPseudoClass("muted");
    private static final PseudoClass ACCENT  = PseudoClass.getPseudoClass("accent");
    private static final PseudoClass SUCCESS = PseudoClass.getPseudoClass("success");
    private static final PseudoClass WARNING = PseudoClass.getPseudoClass("warning");
    private static final PseudoClass DANGER  = PseudoClass.getPseudoClass("danger");
    private static final PseudoClass INFO    = PseudoClass.getPseudoClass("info");

    public FxBadgeSkin(FxBadge badge) {
        super(badge);
        updateVariant(badge);

        badge.variantProperty().addListener((obs, old, val) -> updateVariant(badge));
    }

    private void updateVariant(FxBadge badge) {
        badge.pseudoClassStateChanged(DEFAULT, false);
        badge.pseudoClassStateChanged(MUTED,   false);
        badge.pseudoClassStateChanged(ACCENT,  false);
        badge.pseudoClassStateChanged(SUCCESS, false);
        badge.pseudoClassStateChanged(WARNING, false);
        badge.pseudoClassStateChanged(DANGER,  false);
        badge.pseudoClassStateChanged(INFO,    false);

        switch (badge.getVariant()) {
            case DEFAULT: badge.pseudoClassStateChanged(DEFAULT, true); break;
            case MUTED:   badge.pseudoClassStateChanged(MUTED,   true); break;
            case ACCENT:  badge.pseudoClassStateChanged(ACCENT,  true); break;
            case SUCCESS: badge.pseudoClassStateChanged(SUCCESS, true); break;
            case WARNING: badge.pseudoClassStateChanged(WARNING, true); break;
            case DANGER:  badge.pseudoClassStateChanged(DANGER,  true); break;
            case INFO:    badge.pseudoClassStateChanged(INFO,    true); break;
        }
    }
}
