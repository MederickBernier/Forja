package io.forja.components;

import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxMenuButton.FxMenuButton;
import io.forja.components.buttonsAndActions.fxSplitButton.FxSplitButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.atomic.AtomicInteger;

import static io.forja.testsupport.ForjaTestSupport.assertHasPseudoClass;
import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseJSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void splitButton_builderDefaults() {
        FxSplitButton b = onFx(() -> FxSplitButton.builder().text("Save").build());
        assertEquals("Save", b.getText());
        assertEquals(ButtonVariant.PRIMARY, b.getVariant());
        assertTrue(b.getStyleClass().contains("forja-split-button"));
    }

    @Test
    void splitButton_variantPseudoClass() {
        for (ButtonVariant v : ButtonVariant.values()) {
            FxSplitButton b = onFx(() -> FxSplitButton.builder().variant(v).build());
            assertHasPseudoClass(b, v.name().toLowerCase());
        }
    }

    @Test
    void splitButton_itemsAndAction() {
        AtomicInteger n = new AtomicInteger();
        FxSplitButton b = onFx(() -> FxSplitButton.builder()
                .text("Do")
                .items(new MenuItem("Save as"), new MenuItem("Export"))
                .onAction(e -> n.incrementAndGet())
                .build());
        assertEquals(2, b.getItems().size());
        onFx(() -> { b.fire(); return null; });
        assertEquals(1, n.get());
    }

    @Test
    void splitButton_iconGraphic() {
        FxSplitButton b = onFx(() -> FxSplitButton.builder().text("Go").icon("fth-save").build());
        assertNotNull(b.getGraphic());
    }

    @Test
    void menuButton_builderDefaults() {
        FxMenuButton b = onFx(() -> FxMenuButton.builder().build());
        assertEquals(ButtonVariant.GHOST, b.getVariant());
        assertNotNull(b.getGraphic(), "kebab icon by default");
        assertTrue(b.getStyleClass().contains("forja-menu-button"));
    }

    @Test
    void menuButton_variantPseudoClass() {
        for (ButtonVariant v : ButtonVariant.values()) {
            FxMenuButton b = onFx(() -> FxMenuButton.builder().variant(v).build());
            assertHasPseudoClass(b, v.name().toLowerCase());
        }
    }

    @Test
    void menuButton_holdsItems() {
        FxMenuButton b = onFx(() -> FxMenuButton.builder()
                .items(new MenuItem("Rename"), new MenuItem("Delete"))
                .build());
        assertEquals(2, b.getItems().size());
    }
}
