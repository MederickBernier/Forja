package io.forja.components;

import io.forja.components.feedbackAndStatus.fxToast.FxToast;
import io.forja.components.overlays.OverlayHost;
import io.forja.tokens.SemanticVariant;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.assertHasPseudoClass;
import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxToastTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxToast t = onFx(() -> FxToast.builder().build());
        assertEquals(SemanticVariant.INFO, t.getVariant());
        assertEquals("", t.getMessage());
        assertEquals(3000L, t.getDurationMs());
        assertEquals(Pos.BOTTOM_RIGHT, t.getPosition());
        assertTrue(t.getStyleClass().contains("forja-toast"));
    }

    @Test
    void allVariantsHavePseudoClass() {
        for (SemanticVariant v : SemanticVariant.values()) {
            FxToast t = onFx(() -> FxToast.builder().variant(v).build());
            assertHasPseudoClass(t, v.name().toLowerCase());
        }
    }

    @Test
    void overlayHostWrapsRootOnFirstShow() {
        Scene scene = onFx(() -> new Scene(new VBox(), 300, 200));
        FxToast t = onFx(() -> FxToast.builder().message("hi").durationMs(60000).build());
        onFx(() -> { t.postTo(scene); return null; });
        StackPane layer = onFx(() -> OverlayHost.getLayer(scene));
        assertTrue(layer.getChildren().contains(t));
    }

    @Test
    void dismissRemovesToastFromLayer() {
        Scene scene = onFx(() -> new Scene(new VBox(), 300, 200));
        FxToast t = onFx(() -> FxToast.builder().message("hi").durationMs(60000).build());
        onFx(() -> { t.postTo(scene); return null; });
        onFx(() -> { OverlayHost.dismiss(t); return null; });
        StackPane layer = onFx(() -> OverlayHost.getLayer(scene));
        assertTrue(!layer.getChildren().contains(t));
    }
}
