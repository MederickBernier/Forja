package io.forja.components;

import io.forja.components.overlays.OverlayHost;
import io.forja.components.overlays.fxConfirmDialog.FxConfirmDialog;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.atomic.AtomicReference;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxConfirmDialogTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxConfirmDialog d = onFx(() -> FxConfirmDialog.builder().build());
        assertEquals("Cancel", d.getCancelButton().getText());
        assertEquals("Confirm", d.getConfirmButton().getText());
        assertEquals("Confirm", d.getDialog().getTitle());
    }

    @Test
    void confirmFiresCallbackTrue() {
        AtomicReference<Boolean> got = new AtomicReference<>();
        Scene scene = onFx(() -> new Scene(new VBox(), 400, 300));
        FxConfirmDialog d = onFx(() -> FxConfirmDialog.builder()
                .title("Delete?")
                .message("Are you sure?")
                .onResult(got::set)
                .build());
        onFx(() -> { d.show(scene); return null; });
        onFx(() -> { d.getConfirmButton().fire(); return null; });
        assertTrue(got.get());
        assertTrue(d.isConfirmed());
        StackPane layer = onFx(() -> OverlayHost.getLayer(scene));
        assertFalse(layer.getChildren().contains(d.getDialog()));
    }

    @Test
    void cancelFiresCallbackFalse() {
        AtomicReference<Boolean> got = new AtomicReference<>();
        Scene scene = onFx(() -> new Scene(new VBox(), 400, 300));
        FxConfirmDialog d = onFx(() -> FxConfirmDialog.builder()
                .onResult(got::set)
                .build());
        onFx(() -> { d.show(scene); return null; });
        onFx(() -> { d.getCancelButton().fire(); return null; });
        assertFalse(got.get());
        assertFalse(d.isConfirmed());
    }
}
