package io.forja.components.feedbackAndStatus.fxBadge;

import io.forja.components.feedbackAndStatus.fxBadge.FxBadge;
import io.forja.tokens.SemanticVariant;
import javafx.scene.control.skin.LabelSkin;

/**
 * Skin for {@link FxBadge}.
 *
 * <p>Toggles the seven {@link SemanticVariant} pseudo-classes (default,
 * muted, accent, success, warning, danger, info) based on the badge's
 * variant property.
 *
 * @see FxBadge
 */
public class FxBadgeSkin extends LabelSkin {

    public FxBadgeSkin(FxBadge badge) {
        super(badge);
        SemanticVariant.applyTo(badge, badge.getVariant());

        badge.variantProperty().addListener((obs, old, val) ->
                SemanticVariant.applyTo(badge, val));
    }
}
