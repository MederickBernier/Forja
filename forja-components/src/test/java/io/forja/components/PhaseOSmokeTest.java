package io.forja.components;

import io.forja.components.fileAndMediaInput.fxImageCropper.FxImageCropper;
import io.forja.components.navigation.fxCommandPalette.FxCommandPalette;
import io.forja.components.overlays.OverlayHost;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.atomic.AtomicInteger;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseOSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void imageCropper_selectionExports() {
        WritableImage src = new WritableImage(100, 100);
        FxImageCropper c = onFx(() -> FxImageCropper.builder().image(src).width(100).height(100).build());
        onFx(() -> { c.setSelection(new Rectangle2D(10, 10, 40, 40)); return null; });
        WritableImage out = onFx(c::exportCrop);
        assertNotNull(out);
        assertTrue(out.getWidth() > 0);
        assertTrue(out.getHeight() > 0);
    }

    @Test
    void imageCropper_noImageReturnsNullCrop() {
        FxImageCropper c = onFx(() -> FxImageCropper.builder().build());
        assertNull(onFx(c::exportCrop));
    }

    @Test
    void imageCropper_aspectRatioClamps() {
        WritableImage src = new WritableImage(200, 200);
        FxImageCropper c = onFx(() -> FxImageCropper.builder().image(src).aspectRatio(1.0).build());
        assertEquals(1.0, c.getAspectRatio());
    }

    @Test
    void commandPalette_openAndCloseUsesOverlayHost() {
        Scene scene = onFx(() -> new Scene(new VBox(), 400, 300));
        AtomicInteger runs = new AtomicInteger();
        FxCommandPalette p = onFx(() -> FxCommandPalette.builder()
                .command(new FxCommandPalette.Command("hi", "Hello", "fth-star", c -> runs.incrementAndGet()))
                .buildPalette());
        onFx(() -> { p.open(scene); return null; });
        StackPane layer = onFx(() -> OverlayHost.getLayer(scene));
        assertTrue(layer.getChildren().contains(p.getRoot()));
        onFx(() -> { p.close(); return null; });
        assertFalse(layer.getChildren().contains(p.getRoot()));
    }

    @Test
    void commandPalette_acceleratorInstalls() {
        Scene scene = onFx(() -> new Scene(new VBox(), 400, 300));
        KeyCombination k = new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN);
        FxCommandPalette p = onFx(() -> FxCommandPalette.builder().accelerator(k).buildPalette());
        onFx(() -> { p.install(scene); return null; });
        assertNotNull(scene.getAccelerators().get(k));
    }

    @Test
    void commandPalette_commandsPopulateAutocomplete() {
        FxCommandPalette p = onFx(() -> FxCommandPalette.builder()
                .command(new FxCommandPalette.Command("a", "A", "fth-a", c -> {}))
                .command(new FxCommandPalette.Command("b", "B", "fth-b", c -> {}))
                .buildPalette());
        assertEquals(2, p.getAutocomplete().getItems().size());
    }
}
