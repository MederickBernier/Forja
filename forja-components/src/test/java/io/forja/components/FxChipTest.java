package io.forja.components;

import io.forja.skin.FxChipSkin;
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
        assertEquals(ChipVariant.DEFAULT, chip.getVariant());
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
                .variant(ChipVariant.ACCENT)
                .removable(true)
                .onClose(e -> fired.set(true))
                .build());

        assertEquals("javafx", chip.getText());
        assertEquals(ChipVariant.ACCENT, chip.getVariant());
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
        FxChip withVariant = onFx(() -> new FxChip("tag", ChipVariant.DANGER));

        assertEquals("", empty.getText());
        assertEquals(ChipVariant.DEFAULT, empty.getVariant());
        assertEquals("tag", withText.getText());
        assertEquals("tag", withVariant.getText());
        assertEquals(ChipVariant.DANGER, withVariant.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxChip chip = onFx(() -> {
            FxChip c = FxChip.builder().variant(ChipVariant.SUCCESS).build();
            c.setSkin(new FxChipSkin(c));
            return c;
        });

        assertHasPseudoClass(chip, "success");
        assertLacksPseudoClass(chip, "default");

        onFx(() -> { chip.setVariant(ChipVariant.INFO); return null; });

        assertHasPseudoClass(chip, "info");
        assertLacksPseudoClass(chip, "success");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (ChipVariant variant : ChipVariant.values()) {
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

    private static <T> T onFx(java.util.concurrent.Callable<T> body) {
        try {
            T result = WaitForAsyncUtils.asyncFx(body).get();
            WaitForAsyncUtils.waitForFxEvents();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void assertHasPseudoClass(FxChip chip, String name) {
        assertTrue(
                chip.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Expected pseudo-class :" + name + " on " + chip
        );
    }

    private static void assertLacksPseudoClass(FxChip chip, String name) {
        assertFalse(
                chip.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Did not expect pseudo-class :" + name + " on " + chip
        );
    }
}
