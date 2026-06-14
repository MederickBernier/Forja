package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.tokens.SemanticVariant;
import io.forja.components.feedbackAndStatus.fxChip.FxChip;

import io.forja.components.feedbackAndStatus.fxChip.FxChipSkin;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxChipTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxChip chip = onFx(() -> FxChip.builder().build());

        assertEquals("", chip.getText());
        assertEquals(SemanticVariant.DEFAULT, chip.getVariant());
        assertFalse(chip.isRemovable());
        assertNull(chip.getOnClose());
        assertNull(chip.getGraphic());
        assertTrue(chip.getStyleClass().contains("forja-chip"));
    }

    @Test
    void builderSetsAllProperties() {
        AtomicBoolean fired = new AtomicBoolean(false);
        FxChip chip = onFx(() -> FxChip.builder()
                .text("javafx")
                .variant(SemanticVariant.ACCENT)
                .removable(true)
                .onClose(e -> fired.set(true))
                .build());

        assertEquals("javafx", chip.getText());
        assertEquals(SemanticVariant.ACCENT, chip.getVariant());
        assertTrue(chip.isRemovable());
        assertNotNull(chip.getOnClose());
        assertNotNull(chip.getGraphic(), "Removable chip should have a close-icon graphic");

        onFx(() -> {
            chip.getOnClose().handle(new ActionEvent());
            return null;
        });
        assertTrue(fired.get());
    }

    @Test
    void constructorVariants() {
        FxChip empty = onFx(() -> new FxChip());
        FxChip withText = onFx(() -> new FxChip("tag"));
        FxChip withVariant = onFx(() -> new FxChip("tag", SemanticVariant.DANGER));

        assertEquals("", empty.getText());
        assertEquals(SemanticVariant.DEFAULT, empty.getVariant());
        assertEquals("tag", withText.getText());
        assertEquals("tag", withVariant.getText());
        assertEquals(SemanticVariant.DANGER, withVariant.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxChip chip = onFx(() -> {
            FxChip c = FxChip.builder().variant(SemanticVariant.SUCCESS).build();
            c.setSkin(new FxChipSkin(c));
            return c;
        });

        assertHasPseudoClass(chip, "success");
        assertLacksPseudoClass(chip, "default");

        onFx(() -> { chip.setVariant(SemanticVariant.INFO); return null; });

        assertHasPseudoClass(chip, "info");
        assertLacksPseudoClass(chip, "success");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (SemanticVariant variant : SemanticVariant.values()) {
            FxChip chip = onFx(() -> {
                FxChip c = FxChip.builder().variant(variant).build();
                c.setSkin(new FxChipSkin(c));
                return c;
            });
            assertHasPseudoClass(chip, variant.name().toLowerCase());
        }
    }

    @Test
    void removableTogglesGraphicAndPseudoClass() {
        FxChip chip = onFx(() -> {
            FxChip c = FxChip.builder().text("tag").build();
            c.setSkin(new FxChipSkin(c));
            return c;
        });

        assertNull(chip.getGraphic());
        assertLacksPseudoClass(chip, "removable");

        onFx(() -> { chip.setRemovable(true); return null; });
        assertNotNull(chip.getGraphic());
        assertHasPseudoClass(chip, "removable");

        onFx(() -> { chip.setRemovable(false); return null; });
        assertNull(chip.getGraphic());
        assertLacksPseudoClass(chip, "removable");
    }

    @Test
    void closeIconClickFiresOnCloseWithChipAsSource() {
        AtomicReference<Object> capturedSource = new AtomicReference<>();
        FxChip chip = onFx(() -> FxChip.builder()
                .text("tag")
                .removable(true)
                .onClose(e -> capturedSource.set(e.getSource()))
                .build());

        onFx(() -> {
            chip.getGraphic().fireEvent(new MouseEvent(
                    MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0,
                    MouseButton.PRIMARY, 1,
                    false, false, false, false,
                    true, false, false, false, false, false, null
            ));
            return null;
        });

        assertEquals(chip, capturedSource.get());
    }
}
