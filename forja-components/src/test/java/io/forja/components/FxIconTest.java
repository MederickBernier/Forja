package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.components.utilities.fxIcon.FxIcon;
import io.forja.tokens.SemanticVariant;

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

        assertEquals(SemanticVariant.DEFAULT, icon.getVariant());
        assertTrue(icon.isVisible());
        assertTrue(icon.getStyleClass().contains("forja-icon"));
    }

    @Test
    void builderSetsAllProperties() {
        FxIcon icon = onFx(() -> FxIcon.builder()
                .literal("fth-save")
                .size(24)
                .variant(SemanticVariant.ACCENT)
                .id("save-icon")
                .styleClass("custom-icon")
                .userData("payload")
                .build());

        assertEquals("fth-save", icon.getIconLiteral());
        assertEquals(24, icon.getIconSize());
        assertEquals(SemanticVariant.ACCENT, icon.getVariant());
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
        FxIcon withVariant = onFx(() -> new FxIcon("fth-save", SemanticVariant.DANGER));

        assertEquals(SemanticVariant.DEFAULT, empty.getVariant());
        assertEquals("fth-save", withLiteral.getIconLiteral());
        assertEquals(SemanticVariant.DEFAULT, withLiteral.getVariant());
        assertEquals("fth-save", withVariant.getIconLiteral());
        assertEquals(SemanticVariant.DANGER, withVariant.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxIcon icon = onFx(() -> FxIcon.builder().literal("fth-save").variant(SemanticVariant.ACCENT).build());

        assertHasPseudoClass(icon, "accent");
        assertLacksPseudoClass(icon, "default");
        assertLacksPseudoClass(icon, "danger");

        onFx(() -> {
            icon.setVariant(SemanticVariant.DANGER);
            return null;
        });

        assertHasPseudoClass(icon, "danger");
        assertLacksPseudoClass(icon, "accent");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (SemanticVariant variant : SemanticVariant.values()) {
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
}
