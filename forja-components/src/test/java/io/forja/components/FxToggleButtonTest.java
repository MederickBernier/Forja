package io.forja.components;

import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxToggleButton.FxToggleButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.assertHasPseudoClass;
import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxToggleButtonTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxToggleButton b = onFx(() -> FxToggleButton.builder().build());
        assertEquals("", b.getText());
        assertFalse(b.isSelected());
        assertEquals(ButtonVariant.SECONDARY, b.getVariant());
        assertTrue(b.getStyleClass().contains("forja-toggle-button"));
    }

    @Test
    void selectedApplied() {
        FxToggleButton b = onFx(() -> FxToggleButton.builder().text("Bold").selected(true).build());
        assertEquals("Bold", b.getText());
        assertTrue(b.isSelected());
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (ButtonVariant v : ButtonVariant.values()) {
            FxToggleButton b = onFx(() -> FxToggleButton.builder().variant(v).build());
            assertHasPseudoClass(b, v.name().toLowerCase());
        }
    }
}
