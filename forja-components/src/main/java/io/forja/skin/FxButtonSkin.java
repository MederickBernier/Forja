package io.forja.skin;

import io.forja.components.FxButton;
import javafx.scene.control.skin.ButtonSkin;
import javafx.css.PseudoClass;

public class FxButtonSkin extends ButtonSkin {

    private static final PseudoClass PRIMARY   = PseudoClass.getPseudoClass("primary");
    private static final PseudoClass SECONDARY = PseudoClass.getPseudoClass("secondary");
    private static final PseudoClass GHOST     = PseudoClass.getPseudoClass("ghost");
    private static final PseudoClass DANGER    = PseudoClass.getPseudoClass("danger");
    private static final PseudoClass LOADING   = PseudoClass.getPseudoClass("loading");

    public FxButtonSkin(FxButton button) {
        super(button);
        updateVariant(button);
        updateLoading(button);

        button.variantProperty().addListener((obs, old, val) -> updateVariant(button));
        button.loadingProperty().addListener((obs, old, val) -> updateLoading(button));
    }

    private void updateVariant(FxButton button) {
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

    private void updateLoading(FxButton button) {
        button.pseudoClassStateChanged(LOADING, button.isLoading());
        button.setDisable(button.isLoading());
    }
}