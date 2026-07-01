package io.forja.components;

import io.forja.components.buttonsAndActions.fxButtonGroup.FxButtonGroup;
import javafx.geometry.Orientation;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxButtonGroupTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxButtonGroup g = onFx(() -> FxButtonGroup.builder().build());
        assertTrue(g.getItems().isEmpty());
        assertNull(g.getValue());
        assertEquals(Orientation.HORIZONTAL, g.getOrientation());
        assertFalse(g.isAllowEmpty());
        assertTrue(g.getStyleClass().contains("forja-button-group"));
    }

    @Test
    void itemsBuildToggleButtons() {
        FxButtonGroup g = onFx(() -> FxButtonGroup.builder()
                .items("Left", "Center", "Right")
                .build());
        assertEquals(3, g.getButtons().size());
    }

    @Test
    void initialValueSelectsButton() {
        FxButtonGroup g = onFx(() -> FxButtonGroup.builder()
                .items("A", "B", "C")
                .value("B")
                .build());
        assertTrue(g.getButtons().get(1).isSelected());
        assertEquals("B", g.getValue());
    }

    @Test
    void clickingButtonUpdatesValue() {
        FxButtonGroup g = onFx(() -> FxButtonGroup.builder()
                .items("A", "B")
                .value("A")
                .build());
        onFx(() -> { g.getButtons().get(1).setSelected(true); return null; });
        assertEquals("B", g.getValue());
    }

    @Test
    void deselectionForbiddenByDefault() {
        FxButtonGroup g = onFx(() -> FxButtonGroup.builder()
                .items("A", "B")
                .value("A")
                .build());
        onFx(() -> { g.getButtons().get(0).setSelected(false); return null; });
        assertEquals("A", g.getValue(), "Selection should not clear when allowEmpty=false");
    }

    @Test
    void allowEmptyPermitsNullValue() {
        FxButtonGroup g = onFx(() -> FxButtonGroup.builder()
                .items("A", "B")
                .value("A")
                .allowEmpty(true)
                .build());
        onFx(() -> { g.setValue(null); return null; });
        assertNull(g.getValue());
    }
}
