package io.forja.components.buttonsAndActions.fxIconButton;

import io.forja.components.buttonsAndActions.fxIconButton.FxIconButton;
import javafx.css.PseudoClass;
import javafx.scene.control.skin.ButtonSkin;

/**
 * Skin for {@link FxIconButton}.
 *
 * <p>Manages four pseudo-class groups: variant (primary/secondary/ghost/danger),
 * loading state, and icon position (icon-left/icon-right/icon-only). Mirrors
 * {@link FxButtonSkin} for the variant + loading wiring since
 * {@link FxIconButton} does not extend {@link io.forja.components.buttonsAndActions.fxButton.FxButton}
 * (Java's static-method-hiding rule blocks an inheriting subclass from
 * declaring its own typed {@code builder()} factory).
 *
 * @see FxIconButton
 */
public class FxIconButtonSkin extends ButtonSkin {

    private static final PseudoClass PRIMARY    = PseudoClass.getPseudoClass("primary");
    private static final PseudoClass SECONDARY  = PseudoClass.getPseudoClass("secondary");
    private static final PseudoClass GHOST      = PseudoClass.getPseudoClass("ghost");
    private static final PseudoClass DANGER     = PseudoClass.getPseudoClass("danger");
    private static final PseudoClass LOADING    = PseudoClass.getPseudoClass("loading");
    private static final PseudoClass ICON_LEFT  = PseudoClass.getPseudoClass("icon-left");
    private static final PseudoClass ICON_RIGHT = PseudoClass.getPseudoClass("icon-right");
    private static final PseudoClass ICON_ONLY  = PseudoClass.getPseudoClass("icon-only");

    public FxIconButtonSkin(FxIconButton button) {
        super(button);
        updateVariant(button);
        updateLoading(button);
        updateIconPosition(button);

        button.variantProperty().addListener((obs, old, val) -> updateVariant(button));
        button.loadingProperty().addListener((obs, old, val) -> updateLoading(button));
        button.iconPositionProperty().addListener((obs, old, val) -> updateIconPosition(button));
    }

    private void updateVariant(FxIconButton button) {
        button.pseudoClassStateChanged(PRIMARY,   false);
        button.pseudoClassStateChanged(SECONDARY, false);
        button.pseudoClassStateChanged(GHOST,     false);
        button.pseudoClassStateChanged(DANGER,    false);

        switch (button.getVariant()) {
            case PRIMARY:
                button.pseudoClassStateChanged(PRIMARY, true);
                break;
            case SECONDARY:
                button.pseudoClassStateChanged(SECONDARY, true);
                break;
            case GHOST:
                button.pseudoClassStateChanged(GHOST, true);
                break;
            case DANGER:
                button.pseudoClassStateChanged(DANGER, true);
                break;
        }
    }

    private void updateLoading(FxIconButton button) {
        button.pseudoClassStateChanged(LOADING, button.isLoading());
        button.setDisable(button.isLoading());
    }

    private void updateIconPosition(FxIconButton button) {
        button.pseudoClassStateChanged(ICON_LEFT,  false);
        button.pseudoClassStateChanged(ICON_RIGHT, false);
        button.pseudoClassStateChanged(ICON_ONLY,  false);

        switch (button.getIconPosition()) {
            case LEFT:
                button.pseudoClassStateChanged(ICON_LEFT, true);
                break;
            case RIGHT:
                button.pseudoClassStateChanged(ICON_RIGHT, true);
                break;
            case ONLY:
                button.pseudoClassStateChanged(ICON_ONLY, true);
                break;
        }
    }
}
