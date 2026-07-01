package io.forja.components;

import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.overlays.OverlayHost;
import io.forja.components.overlays.fxDialog.FxDialog;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxDialogTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxDialog d = onFx(() -> FxDialog.builder().build());
        assertEquals("", d.getTitle());
        assertTrue(d.isDismissOnScrimClick());
        assertTrue(d.isDismissOnEscape());
        assertTrue(d.isShowCloseIcon());
        assertFalse(d.getTitleLabel().isVisible());
        assertFalse(d.getFooter().isVisible());
        assertTrue(d.getStyleClass().contains("forja-dialog"));
    }

    @Test
    void titleAndBodyApplied() {
        FxButton btn = onFx(() -> new FxButton("ok"));
        FxDialog d = onFx(() -> FxDialog.builder()
                .title("Confirm")
                .body(btn)
                .build());
        assertEquals("Confirm", d.getTitleLabel().getText());
        assertTrue(d.getTitleLabel().isVisible());
        assertTrue(d.getBodyBox().getChildren().contains(btn));
    }

    @Test
    void footerShowsWhenSet() {
        FxButton cancel = onFx(() -> new FxButton("Cancel"));
        FxButton ok = onFx(() -> new FxButton("OK"));
        FxDialog d = onFx(() -> FxDialog.builder().footer(cancel, ok).build());
        assertTrue(d.getFooter().isVisible());
        assertEquals(2, d.getFooter().getChildren().size());
    }

    @Test
    void showAndCloseUsesOverlayHost() {
        Scene scene = onFx(() -> new Scene(new VBox(), 400, 300));
        FxDialog d = onFx(() -> FxDialog.builder().title("Hi").build());
        onFx(() -> { d.show(scene); return null; });
        StackPane layer = onFx(() -> OverlayHost.getLayer(scene));
        assertTrue(layer.getChildren().contains(d));

        onFx(() -> { d.close(); return null; });
        assertFalse(layer.getChildren().contains(d));
    }

    @Test
    void footerButtonWithoutHandlerAutoClosesDialog() {
        Scene scene = onFx(() -> new Scene(new VBox(), 400, 300));
        FxButton ok = onFx(() -> new FxButton("OK"));
        FxDialog d = onFx(() -> FxDialog.builder().footer(ok).build());
        onFx(() -> { d.show(scene); return null; });
        onFx(() -> { ok.fire(); return null; });
        StackPane layer = onFx(() -> OverlayHost.getLayer(scene));
        assertFalse(layer.getChildren().contains(d));
    }
}
