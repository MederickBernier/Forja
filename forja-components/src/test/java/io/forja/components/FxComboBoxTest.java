package io.forja.components;

import io.forja.components.selection.fxComboBox.FxComboBox;
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
class FxComboBoxTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxComboBox<String> cb = onFx(() -> FxComboBox.<String>builder().build());
        assertTrue(cb.getItems().isEmpty());
        assertNull(cb.getValue());
        assertEquals("", cb.getPromptText());
        assertFalse(cb.isEditable());
        assertTrue(cb.getStyleClass().contains("forja-combo-box"));
    }

    @Test
    void itemsAndValueApplied() {
        FxComboBox<String> cb = onFx(() -> FxComboBox.<String>builder()
                .items("A", "B", "C")
                .value("B")
                .build());
        assertEquals(3, cb.getItems().size());
        assertEquals("B", cb.getValue());
    }

    @Test
    void promptAndEditableApplied() {
        FxComboBox<String> cb = onFx(() -> FxComboBox.<String>builder()
                .promptText("Pick")
                .editable(true)
                .build());
        assertEquals("Pick", cb.getPromptText());
        assertTrue(cb.isEditable());
    }

    @Test
    void visibleRowCountApplied() {
        FxComboBox<String> cb = onFx(() -> FxComboBox.<String>builder()
                .items("A", "B", "C")
                .visibleRowCount(3)
                .build());
        assertEquals(3, cb.getVisibleRowCount());
    }
}
