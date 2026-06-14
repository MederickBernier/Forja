package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.components.typography.fxKbd.FxKbd;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxKbdTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxKbd kbd = onFx(() -> FxKbd.builder().build());

        assertEquals("", kbd.getText());
        assertTrue(kbd.getStyleClass().contains("forja-kbd"));
    }

    @Test
    void builderSetsText() {
        FxKbd kbd = onFx(() -> FxKbd.builder().text("⌘").build());

        assertEquals("⌘", kbd.getText());
    }

    @Test
    void builderAppliesInheritedBaseProperties() {
        FxKbd kbd = onFx(() -> FxKbd.builder()
                .text("K")
                .id("kbd-k")
                .styleClass("custom")
                .userData("payload")
                .tooltip("Press K")
                .disabled(true)
                .build());

        assertEquals("kbd-k", kbd.getId());
        assertTrue(kbd.getStyleClass().contains("custom"));
        assertEquals("payload", kbd.getUserData());
        assertTrue(kbd.isDisabled());
    }

    @Test
    void constructorVariants() {
        FxKbd empty = onFx(() -> new FxKbd());
        FxKbd withText = onFx(() -> new FxKbd("Esc"));

        assertEquals("", empty.getText());
        assertTrue(empty.getStyleClass().contains("forja-kbd"));
        assertEquals("Esc", withText.getText());
        assertTrue(withText.getStyleClass().contains("forja-kbd"));
    }
}
