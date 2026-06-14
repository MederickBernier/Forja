package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.components.feedbackAndStatus.fxBadge.FxBadge;
import io.forja.tokens.SemanticVariant;

import io.forja.components.feedbackAndStatus.fxBadge.FxBadgeSkin;
import javafx.css.PseudoClass;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxBadgeTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxBadge badge = onFx(() -> FxBadge.builder().build());

        assertEquals("", badge.getText());
        assertEquals(SemanticVariant.DEFAULT, badge.getVariant());
        assertTrue(badge.getStyleClass().contains("forja-badge"));
    }

    @Test
    void builderSetsAllProperties() {
        FxBadge badge = onFx(() -> FxBadge.builder()
                .text("Active")
                .variant(SemanticVariant.SUCCESS)
                .build());

        assertEquals("Active", badge.getText());
        assertEquals(SemanticVariant.SUCCESS, badge.getVariant());
    }

    @Test
    void constructorVariants() {
        FxBadge empty = onFx(() -> new FxBadge());
        FxBadge withText = onFx(() -> new FxBadge("Beta"));
        FxBadge withVariant = onFx(() -> new FxBadge("Pro", SemanticVariant.ACCENT));

        assertEquals("", empty.getText());
        assertEquals(SemanticVariant.DEFAULT, empty.getVariant());
        assertEquals("Beta", withText.getText());
        assertEquals(SemanticVariant.DEFAULT, withText.getVariant());
        assertEquals("Pro", withVariant.getText());
        assertEquals(SemanticVariant.ACCENT, withVariant.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxBadge badge = onFx(() -> {
            FxBadge b = FxBadge.builder().variant(SemanticVariant.WARNING).build();
            b.setSkin(new FxBadgeSkin(b));
            return b;
        });

        assertHasPseudoClass(badge, "warning");
        assertLacksPseudoClass(badge, "default");
        assertLacksPseudoClass(badge, "success");

        onFx(() -> { badge.setVariant(SemanticVariant.DANGER); return null; });

        assertHasPseudoClass(badge, "danger");
        assertLacksPseudoClass(badge, "warning");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (SemanticVariant variant : SemanticVariant.values()) {
            FxBadge badge = onFx(() -> {
                FxBadge b = FxBadge.builder().variant(variant).build();
                b.setSkin(new FxBadgeSkin(b));
                return b;
            });
            assertHasPseudoClass(badge, variant.name().toLowerCase());
        }
    }
}
