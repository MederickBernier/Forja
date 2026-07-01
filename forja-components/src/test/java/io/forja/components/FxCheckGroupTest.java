package io.forja.components;

import io.forja.components.selection.fxCheckGroup.FxCheckGroup;
import javafx.geometry.Orientation;
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
class FxCheckGroupTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxCheckGroup g = onFx(() -> FxCheckGroup.builder().build());
        assertTrue(g.getItems().isEmpty());
        assertTrue(g.getSelectedValues().isEmpty());
        assertEquals(Orientation.VERTICAL, g.getOrientation());
        assertEquals("", g.getLabel());
        assertFalse(g.getHeadingLabel().isVisible());
        assertTrue(g.getStyleClass().contains("forja-check-group"));
    }

    @Test
    void itemsBuildBoxes() {
        FxCheckGroup g = onFx(() -> FxCheckGroup.builder()
                .items("A", "B", "C")
                .build());
        assertEquals(3, g.getCheckBoxes().size());
        assertEquals("A", g.getCheckBoxes().get(0).getText());
    }

    @Test
    void preselectedFlagsBoxes() {
        FxCheckGroup g = onFx(() -> FxCheckGroup.builder()
                .items("A", "B", "C")
                .selected("A", "C")
                .build());
        assertTrue(g.getCheckBoxes().get(0).isSelected());
        assertFalse(g.getCheckBoxes().get(1).isSelected());
        assertTrue(g.getCheckBoxes().get(2).isSelected());
    }

    @Test
    void togglingBoxUpdatesSelection() {
        FxCheckGroup g = onFx(() -> FxCheckGroup.builder().items("A", "B").build());
        onFx(() -> { g.getCheckBoxes().get(1).setSelected(true); return null; });
        assertEquals(1, g.getSelectedValues().size());
        assertTrue(g.getSelectedValues().contains("B"));
    }

    @Test
    void selectionListUpdatesBoxes() {
        FxCheckGroup g = onFx(() -> FxCheckGroup.builder().items("A", "B").build());
        onFx(() -> { g.getSelectedValues().add("A"); return null; });
        assertTrue(g.getCheckBoxes().get(0).isSelected());
    }

    @Test
    void headingShowsWhenLabelSet() {
        FxCheckGroup g = onFx(() -> FxCheckGroup.builder().label("Toppings").build());
        assertTrue(g.getHeadingLabel().isVisible());
        assertEquals("Toppings", g.getHeadingLabel().getText());
    }

    @Test
    void horizontalOrientationUsesHBox() {
        FxCheckGroup g = onFx(() -> FxCheckGroup.builder()
                .items("A", "B")
                .orientation(Orientation.HORIZONTAL)
                .build());
        assertEquals(Orientation.HORIZONTAL, g.getOrientation());
    }
}
