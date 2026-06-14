package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.tokens.SemanticVariant;
import io.forja.components.feedbackAndStatus.fxStatusDot.FxStatusDot;

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

        assertEquals(SemanticVariant.DEFAULT, dot.getVariant());
        assertEquals(5.0, dot.getRadius());
        assertTrue(dot.getStyleClass().contains("forja-status-dot"));
    }

    @Test
    void builderSetsAllProperties() {
        FxStatusDot dot = onFx(() -> FxStatusDot.builder()
                .variant(SemanticVariant.SUCCESS)
                .radius(8)
                .id("status")
                .styleClass("custom")
                .userData("payload")
                .build());

        assertEquals(SemanticVariant.SUCCESS, dot.getVariant());
        assertEquals(8, dot.getRadius());
        assertEquals("status", dot.getId());
        assertTrue(dot.getStyleClass().contains("custom"));
        assertEquals("payload", dot.getUserData());
    }

    @Test
    void constructorVariants() {
        FxStatusDot empty = onFx(() -> new FxStatusDot());
        FxStatusDot withVariant = onFx(() -> new FxStatusDot(SemanticVariant.WARNING));
        FxStatusDot withRadius = onFx(() -> new FxStatusDot(7, SemanticVariant.DANGER));

        assertEquals(SemanticVariant.DEFAULT, empty.getVariant());
        assertEquals(5.0, empty.getRadius());
        assertEquals(SemanticVariant.WARNING, withVariant.getVariant());
        assertEquals(7.0, withRadius.getRadius());
        assertEquals(SemanticVariant.DANGER, withRadius.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxStatusDot dot = onFx(() -> FxStatusDot.builder().variant(SemanticVariant.ACCENT).build());

        assertHasPseudoClass(dot, "accent");
        assertLacksPseudoClass(dot, "default");

        onFx(() -> { dot.setVariant(SemanticVariant.SUCCESS); return null; });

        assertHasPseudoClass(dot, "success");
        assertLacksPseudoClass(dot, "accent");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (SemanticVariant variant : SemanticVariant.values()) {
            FxStatusDot dot = onFx(() -> FxStatusDot.builder().variant(variant).build());
            assertHasPseudoClass(dot, variant.name().toLowerCase());
        }
    }

    @Test
    void defaultVariantPseudoClassAppliesAtConstruction() {
        FxStatusDot dot = onFx(() -> new FxStatusDot());

        assertHasPseudoClass(dot, "default");
    }
}
