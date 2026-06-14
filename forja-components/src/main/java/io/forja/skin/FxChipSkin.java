package io.forja.skin;

import io.forja.components.FxChip;
import javafx.css.PseudoClass;
import javafx.scene.control.skin.LabelSkin;

/**
 * Skin for {@link FxChip}.
 *
 * <p>Toggles the 7 semantic variant pseudo-classes and the {@code :removable}
 * pseudo-class based on the chip's properties.
 *
 * @see FxChip
 */
public class FxChipSkin extends LabelSkin {

    private static final PseudoClass DEFAULT   = PseudoClass.getPseudoClass("default");
    private static final PseudoClass MUTED     = PseudoClass.getPseudoClass("muted");
    private static final PseudoClass ACCENT    = PseudoClass.getPseudoClass("accent");
    private static final PseudoClass SUCCESS   = PseudoClass.getPseudoClass("success");
    private static final PseudoClass WARNING   = PseudoClass.getPseudoClass("warning");
    private static final PseudoClass DANGER    = PseudoClass.getPseudoClass("danger");
    private static final PseudoClass INFO      = PseudoClass.getPseudoClass("info");
    private static final PseudoClass REMOVABLE = PseudoClass.getPseudoClass("removable");

    public FxChipSkin(FxChip chip) {
        super(chip);
        updateVariant(chip);
        updateRemovable(chip);

        chip.variantProperty().addListener((obs, old, val) -> updateVariant(chip));
        chip.removableProperty().addListener((obs, old, val) -> updateRemovable(chip));
    }

    private void updateVariant(FxChip chip) {
        chip.pseudoClassStateChanged(DEFAULT, false);
        chip.pseudoClassStateChanged(MUTED,   false);
        chip.pseudoClassStateChanged(ACCENT,  false);
        chip.pseudoClassStateChanged(SUCCESS, false);
        chip.pseudoClassStateChanged(WARNING, false);
        chip.pseudoClassStateChanged(DANGER,  false);
        chip.pseudoClassStateChanged(INFO,    false);

        switch (chip.getVariant()) {
            case DEFAULT: chip.pseudoClassStateChanged(DEFAULT, true); break;
            case MUTED:   chip.pseudoClassStateChanged(MUTED,   true); break;
            case ACCENT:  chip.pseudoClassStateChanged(ACCENT,  true); break;
            case SUCCESS: chip.pseudoClassStateChanged(SUCCESS, true); break;
            case WARNING: chip.pseudoClassStateChanged(WARNING, true); break;
            case DANGER:  chip.pseudoClassStateChanged(DANGER,  true); break;
            case INFO:    chip.pseudoClassStateChanged(INFO,    true); break;
        }
    }

    private void updateRemovable(FxChip chip) {
        chip.pseudoClassStateChanged(REMOVABLE, chip.isRemovable());
    }
}
