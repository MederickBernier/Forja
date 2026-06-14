package io.forja.components;

import io.forja.skin.FxBadgeSkin;
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
        assertEquals(BadgeVariant.DEFAULT, badge.getVariant());
        assertTrue(badge.getStyleClass().contains("forja-badge"));
    }

    @Test
    void builderSetsAllProperties() {
        FxBadge badge = onFx(() -> FxBadge.builder()
                .text("Active")
                .variant(BadgeVariant.SUCCESS)
                .build());

        assertEquals("Active", badge.getText());
        assertEquals(BadgeVariant.SUCCESS, badge.getVariant());
    }

    @Test
    void constructorVariants() {
        FxBadge empty = onFx(() -> new FxBadge());
        FxBadge withText = onFx(() -> new FxBadge("Beta"));
        FxBadge withVariant = onFx(() -> new FxBadge("Pro", BadgeVariant.ACCENT));

        assertEquals("", empty.getText());
        assertEquals(BadgeVariant.DEFAULT, empty.getVariant());
        assertEquals("Beta", withText.getText());
        assertEquals(BadgeVariant.DEFAULT, withText.getVariant());
        assertEquals("Pro", withVariant.getText());
        assertEquals(BadgeVariant.ACCENT, withVariant.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxBadge badge = onFx(() -> {
            FxBadge b = FxBadge.builder().variant(BadgeVariant.WARNING).build();
            b.setSkin(new FxBadgeSkin(b));
            return b;
        });

        assertHasPseudoClass(badge, "warning");
        assertLacksPseudoClass(badge, "default");
        assertLacksPseudoClass(badge, "success");

        onFx(() -> { badge.setVariant(BadgeVariant.DANGER); return null; });

        assertHasPseudoClass(badge, "danger");
        assertLacksPseudoClass(badge, "warning");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (BadgeVariant variant : BadgeVariant.values()) {
            FxBadge badge = onFx(() -> {
                FxBadge b = FxBadge.builder().variant(variant).build();
                b.setSkin(new FxBadgeSkin(b));
                return b;
            });
            assertHasPseudoClass(badge, variant.name().toLowerCase());
        }
    }

    private static <T> T onFx(java.util.concurrent.Callable<T> body) {
        try {
            T result = WaitForAsyncUtils.asyncFx(body).get();
            WaitForAsyncUtils.waitForFxEvents();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void assertHasPseudoClass(FxBadge badge, String name) {
        assertTrue(
                badge.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Expected pseudo-class :" + name + " on " + badge
        );
    }

    private static void assertLacksPseudoClass(FxBadge badge, String name) {
        assertFalse(
                badge.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Did not expect pseudo-class :" + name + " on " + badge
        );
    }
}
