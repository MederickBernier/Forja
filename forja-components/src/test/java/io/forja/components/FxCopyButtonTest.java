package io.forja.components;

import io.forja.components.buttonsAndActions.fxCopyButton.FxCopyButton;
import javafx.scene.input.Clipboard;
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
class FxCopyButtonTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxCopyButton b = onFx(() -> FxCopyButton.builder().build());
        assertEquals("Copy", b.getText());
        assertEquals("", b.getValue());
        assertEquals("Copied", b.getConfirmText());
        assertEquals(1500L, b.getConfirmDurationMs());
        assertFalse(b.isConfirming());
        assertTrue(b.getStyleClass().contains("forja-copy-button"));
    }

    @Test
    void copyWritesValueToClipboardAndEntersConfirming() {
        FxCopyButton b = onFx(() -> FxCopyButton.builder()
                .value("hello")
                .confirmDurationMs(3000)
                .build());
        onFx(() -> { b.copy(); return null; });
        String got = onFx(() -> Clipboard.getSystemClipboard().getString());
        assertEquals("hello", got);
        assertTrue(b.isConfirming());
        assertEquals("Copied", b.getText());
    }
}
