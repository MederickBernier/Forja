package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.components.typography.fxText.TextVariant;
import io.forja.components.typography.fxText.FxText;

import io.forja.components.typography.fxText.FxTextSkin;
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
class FxTextTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxText text = onFx(() -> FxText.builder().build());

        assertEquals("", text.getText());
        assertEquals(TextVariant.BODY, text.getVariant());
        assertFalse(text.isMuted());
        assertTrue(text.isWrapText(), "FxText defaults wrapText to true");
        assertTrue(text.getStyleClass().contains("forja-text"));
    }

    @Test
    void builderSetsAllProperties() {
        FxText text = onFx(() -> FxText.builder()
                .text("Hello world")
                .variant(TextVariant.LEAD)
                .muted(true)
                .maxWidth(400)
                .wrapText(false)
                .build());

        assertEquals("Hello world", text.getText());
        assertEquals(TextVariant.LEAD, text.getVariant());
        assertTrue(text.isMuted());
        assertEquals(400, text.getMaxWidth());
        assertFalse(text.isWrapText());
    }

    @Test
    void constructorVariants() {
        FxText empty = onFx(() -> new FxText());
        FxText withText = onFx(() -> new FxText("Hi"));
        FxText withVariant = onFx(() -> new FxText("Hi", TextVariant.LEAD));

        assertEquals("", empty.getText());
        assertEquals(TextVariant.BODY, empty.getVariant());
        assertTrue(empty.isWrapText());
        assertEquals("Hi", withText.getText());
        assertEquals(TextVariant.BODY, withText.getVariant());
        assertEquals(TextVariant.LEAD, withVariant.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxText text = onFx(() -> {
            FxText t = FxText.builder().variant(TextVariant.LEAD).build();
            t.setSkin(new FxTextSkin(t));
            return t;
        });

        assertHasPseudoClass(text, "lead");
        assertLacksPseudoClass(text, "body");

        onFx(() -> {
            text.setVariant(TextVariant.BODY);
            return null;
        });

        assertHasPseudoClass(text, "body");
        assertLacksPseudoClass(text, "lead");
    }

    @Test
    void mutedPseudoClassToggles() {
        FxText text = onFx(() -> {
            FxText t = FxText.builder().build();
            t.setSkin(new FxTextSkin(t));
            return t;
        });

        assertLacksPseudoClass(text, "muted");

        onFx(() -> { text.setMuted(true); return null; });
        assertHasPseudoClass(text, "muted");

        onFx(() -> { text.setMuted(false); return null; });
        assertLacksPseudoClass(text, "muted");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (TextVariant variant : TextVariant.values()) {
            FxText text = onFx(() -> {
                FxText t = FxText.builder().variant(variant).build();
                t.setSkin(new FxTextSkin(t));
                return t;
            });
            assertHasPseudoClass(text, variant.name().toLowerCase());
        }
    }
}
