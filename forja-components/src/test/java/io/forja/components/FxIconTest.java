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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxIconTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxIcon icon = onFx(() -> FxIcon.builder().build());

        assertEquals(IconVariant.DEFAULT, icon.getVariant());
        assertTrue(icon.isVisible());
        assertTrue(icon.getStyleClass().contains("forja-icon"));
    }

    @Test
    void builderSetsAllProperties() {
        FxIcon icon = onFx(() -> FxIcon.builder()
                .literal("fth-save")
                .size(24)
                .variant(IconVariant.ACCENT)
                .id("save-icon")
                .styleClass("custom-icon")
                .userData("payload")
                .build());

        assertEquals("fth-save", icon.getIconLiteral());
        assertEquals(24, icon.getIconSize());
        assertEquals(IconVariant.ACCENT, icon.getVariant());
        assertEquals("save-icon", icon.getId());
        assertTrue(icon.getStyleClass().contains("custom-icon"));
        assertEquals("payload", icon.getUserData());
    }

    @Test
    void builderHidesWhenVisibleFalse() {
        FxIcon icon = onFx(() -> FxIcon.builder().literal("fth-save").visible(false).build());

        assertFalse(icon.isVisible());
    }

    @Test
    void constructorVariants() {
        FxIcon empty = onFx(() -> new FxIcon());
        FxIcon withLiteral = onFx(() -> new FxIcon("fth-save"));
        FxIcon withVariant = onFx(() -> new FxIcon("fth-save", IconVariant.DANGER));

        assertEquals(IconVariant.DEFAULT, empty.getVariant());
        assertEquals("fth-save", withLiteral.getIconLiteral());
        assertEquals(IconVariant.DEFAULT, withLiteral.getVariant());
        assertEquals("fth-save", withVariant.getIconLiteral());
        assertEquals(IconVariant.DANGER, withVariant.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxIcon icon = onFx(() -> FxIcon.builder().literal("fth-save").variant(IconVariant.ACCENT).build());

        assertHasPseudoClass(icon, "accent");
        assertLacksPseudoClass(icon, "default");
        assertLacksPseudoClass(icon, "danger");

        onFx(() -> {
            icon.setVariant(IconVariant.DANGER);
            return null;
        });

        assertHasPseudoClass(icon, "danger");
        assertLacksPseudoClass(icon, "accent");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (IconVariant variant : IconVariant.values()) {
            FxIcon icon = onFx(() -> FxIcon.builder().literal("fth-save").variant(variant).build());

            assertHasPseudoClass(icon, variant.name().toLowerCase());
        }
    }

    @Test
    void defaultVariantPseudoClassAppliesAtConstruction() {
        FxIcon icon = onFx(() -> new FxIcon());

        assertHasPseudoClass(icon, "default");
    }

    @Test
    void rendersFeatherGlyph() {
        FxIcon icon = onFx(() -> FxIcon.builder().literal("fth-save").size(20).build());

        assertEquals("fth-save", icon.getIconLiteral());
        assertNotNull(icon.getText(), "Expected the icon glyph text to be set by Ikonli");
        assertFalse(icon.getText().isEmpty(), "Expected the icon glyph text to be non-empty");
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

    private static void assertHasPseudoClass(FxIcon icon, String name) {
        assertTrue(
                icon.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Expected pseudo-class :" + name + " on " + icon
        );
    }

    private static void assertLacksPseudoClass(FxIcon icon, String name) {
        assertFalse(
                icon.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Did not expect pseudo-class :" + name + " on " + icon
        );
    }
}
