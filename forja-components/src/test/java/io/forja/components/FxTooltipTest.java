package io.forja.components;

import io.forja.components.overlays.fxTooltip.FxTooltip;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxTooltipTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxTooltip t = onFx(() -> FxTooltip.builder().build());
        assertEquals("", t.getText());
        assertEquals(400.0, t.getShowDelay().toMillis());
        assertTrue(t.getStyleClass().contains("forja-tooltip"));
    }

    @Test
    void textApplied() {
        FxTooltip t = onFx(() -> FxTooltip.builder().text("Hi").showDelayMs(100).build());
        assertEquals("Hi", t.getText());
        assertEquals(100.0, t.getShowDelay().toMillis());
    }

    @Test
    void installAttachesToButton() {
        Button b = onFx(() -> new Button("Click"));
        FxTooltip t = onFx(() -> FxTooltip.builder().text("Info").build());
        onFx(() -> { FxTooltip.install(b, t); return null; });
        assertEquals(t, b.getTooltip());
    }

    @Test
    void installShortcutCreatesAndInstalls() {
        Button b = onFx(() -> new Button("Click"));
        FxTooltip t = onFx(() -> FxTooltip.install((javafx.scene.Node) b, "Info"));
        assertNotNull(t);
        assertEquals("Info", t.getText());
    }
}
