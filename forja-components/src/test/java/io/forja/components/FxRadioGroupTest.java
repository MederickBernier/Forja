package io.forja.components;

import io.forja.components.selection.fxRadioGroup.FxRadioGroup;
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
class FxRadioGroupTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxRadioGroup g = onFx(() -> FxRadioGroup.builder().build());
        assertTrue(g.getItems().isEmpty());
        assertNull(g.getValue());
        assertEquals(Orientation.VERTICAL, g.getOrientation());
        assertFalse(g.getHeadingLabel().isVisible());
        assertTrue(g.getStyleClass().contains("forja-radio-group"));
    }

    @Test
    void itemsBuildButtons() {
        FxRadioGroup g = onFx(() -> FxRadioGroup.builder().items("A", "B", "C").build());
        assertEquals(3, g.getRadioButtons().size());
        assertEquals("A", g.getRadioButtons().get(0).getText());
    }

    @Test
    void initialValueSelectsButton() {
        FxRadioGroup g = onFx(() -> FxRadioGroup.builder()
                .items("A", "B", "C")
                .value("B")
                .build());
        assertTrue(g.getRadioButtons().get(1).isSelected());
        assertEquals("B", g.getValue());
    }

    @Test
    void togglingButtonUpdatesValue() {
        FxRadioGroup g = onFx(() -> FxRadioGroup.builder().items("A", "B").build());
        onFx(() -> { g.getRadioButtons().get(1).setSelected(true); return null; });
        assertEquals("B", g.getValue());
    }

    @Test
    void settingValueUpdatesButtons() {
        FxRadioGroup g = onFx(() -> FxRadioGroup.builder().items("A", "B").build());
        onFx(() -> { g.setValue("A"); return null; });
        assertTrue(g.getRadioButtons().get(0).isSelected());
        assertFalse(g.getRadioButtons().get(1).isSelected());
    }

    @Test
    void clearingValueDeselectsAll() {
        FxRadioGroup g = onFx(() -> FxRadioGroup.builder()
                .items("A", "B")
                .value("A")
                .build());
        onFx(() -> { g.setValue(null); return null; });
        assertFalse(g.getRadioButtons().get(0).isSelected());
        assertFalse(g.getRadioButtons().get(1).isSelected());
    }

    @Test
    void headingShowsWhenLabelSet() {
        FxRadioGroup g = onFx(() -> FxRadioGroup.builder().label("Size").build());
        assertTrue(g.getHeadingLabel().isVisible());
        assertEquals("Size", g.getHeadingLabel().getText());
    }
}
