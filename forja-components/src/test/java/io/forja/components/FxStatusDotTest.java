package io.forja.components;

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
class FxStatusDotTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxStatusDot dot = onFx(() -> FxStatusDot.builder().build());

        assertEquals(StatusDotVariant.DEFAULT, dot.getVariant());
        assertEquals(5.0, dot.getRadius());
        assertTrue(dot.getStyleClass().contains("forja-status-dot"));
    }

    @Test
    void builderSetsAllProperties() {
        FxStatusDot dot = onFx(() -> FxStatusDot.builder()
                .variant(StatusDotVariant.SUCCESS)
                .radius(8)
                .id("status")
                .styleClass("custom")
                .userData("payload")
                .build());

        assertEquals(StatusDotVariant.SUCCESS, dot.getVariant());
        assertEquals(8, dot.getRadius());
        assertEquals("status", dot.getId());
        assertTrue(dot.getStyleClass().contains("custom"));
        assertEquals("payload", dot.getUserData());
    }

    @Test
    void constructorVariants() {
        FxStatusDot empty = onFx(() -> new FxStatusDot());
        FxStatusDot withVariant = onFx(() -> new FxStatusDot(StatusDotVariant.WARNING));
        FxStatusDot withRadius = onFx(() -> new FxStatusDot(7, StatusDotVariant.DANGER));

        assertEquals(StatusDotVariant.DEFAULT, empty.getVariant());
        assertEquals(5.0, empty.getRadius());
        assertEquals(StatusDotVariant.WARNING, withVariant.getVariant());
        assertEquals(7.0, withRadius.getRadius());
        assertEquals(StatusDotVariant.DANGER, withRadius.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxStatusDot dot = onFx(() -> FxStatusDot.builder().variant(StatusDotVariant.ACCENT).build());

        assertHasPseudoClass(dot, "accent");
        assertLacksPseudoClass(dot, "default");

        onFx(() -> { dot.setVariant(StatusDotVariant.SUCCESS); return null; });

        assertHasPseudoClass(dot, "success");
        assertLacksPseudoClass(dot, "accent");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (StatusDotVariant variant : StatusDotVariant.values()) {
            FxStatusDot dot = onFx(() -> FxStatusDot.builder().variant(variant).build());
            assertHasPseudoClass(dot, variant.name().toLowerCase());
        }
    }

    @Test
    void defaultVariantPseudoClassAppliesAtConstruction() {
        FxStatusDot dot = onFx(() -> new FxStatusDot());

        assertHasPseudoClass(dot, "default");
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

    private static void assertHasPseudoClass(FxStatusDot dot, String name) {
        assertTrue(
                dot.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Expected pseudo-class :" + name + " on " + dot
        );
    }

    private static void assertLacksPseudoClass(FxStatusDot dot, String name) {
        assertFalse(
                dot.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Did not expect pseudo-class :" + name + " on " + dot
        );
    }
}
