package io.forja.components;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxCodeTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxCode code = onFx(() -> FxCode.builder().build());

        assertEquals("", code.getText());
        assertTrue(code.getStyleClass().contains("forja-code"));
    }

    @Test
    void builderSetsText() {
        FxCode code = onFx(() -> FxCode.builder().text("Forja.install(scene)").build());

        assertEquals("Forja.install(scene)", code.getText());
    }

    @Test
    void builderAppliesInheritedBaseProperties() {
        FxCode code = onFx(() -> FxCode.builder()
                .text("variant")
                .id("inline-code")
                .styleClass("custom")
                .userData("payload")
                .tooltip("the variant property")
                .build());

        assertEquals("inline-code", code.getId());
        assertTrue(code.getStyleClass().contains("custom"));
        assertEquals("payload", code.getUserData());
    }

    @Test
    void constructorVariants() {
        FxCode empty = onFx(() -> new FxCode());
        FxCode withText = onFx(() -> new FxCode("println(\"hi\")"));

        assertEquals("", empty.getText());
        assertTrue(empty.getStyleClass().contains("forja-code"));
        assertEquals("println(\"hi\")", withText.getText());
        assertTrue(withText.getStyleClass().contains("forja-code"));
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
}
