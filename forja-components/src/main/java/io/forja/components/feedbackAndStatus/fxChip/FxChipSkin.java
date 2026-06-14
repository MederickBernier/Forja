package io.forja.components.feedbackAndStatus.fxChip;

import io.forja.components.feedbackAndStatus.fxChip.FxChip;
import io.forja.tokens.SemanticVariant;
import javafx.css.PseudoClass;
import javafx.scene.control.skin.LabelSkin;

/**
 * Skin for {@link FxChip}.
 *
 * <p>Toggles the seven {@link SemanticVariant} pseudo-classes and the
 * {@code :removable} pseudo-class based on the chip's properties.
 *
 * @see FxChip
 */
public class FxChipSkin extends LabelSkin {

    private static final PseudoClass REMOVABLE = PseudoClass.getPseudoClass("removable");

    public FxChipSkin(FxChip chip) {
        super(chip);
        SemanticVariant.applyTo(chip, chip.getVariant());
        updateRemovable(chip);

        chip.variantProperty().addListener((obs, old, val) ->
                SemanticVariant.applyTo(chip, val));
        chip.removableProperty().addListener((obs, old, val) -> updateRemovable(chip));
    }

    private void updateRemovable(FxChip chip) {
        chip.pseudoClassStateChanged(REMOVABLE, chip.isRemovable());
    }
}
