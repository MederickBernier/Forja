package io.forja.components;

import io.forja.components.inputs.fxSearchField.FxSearchField;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.atomic.AtomicReference;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxSearchFieldTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxSearchField f = onFx(() -> FxSearchField.builder().build());
        assertEquals("", f.getText());
        assertEquals("Search", f.getPromptText());
        assertFalse(f.isClearable());
        assertNotNull(f.getInnerField().getLeadingIcon());
        assertTrue(f.getInnerField().getLeadingIcon() instanceof FxIcon);
        assertEquals("fth-search", ((FxIcon) f.getInnerField().getLeadingIcon()).getIconLiteral());
        assertNull(f.getInnerField().getTrailingIcon());
        assertTrue(f.getStyleClass().contains("forja-search-field"));
        assertTrue(f.getInnerField().getStyleClass().contains("forja-text-field"));
    }

    @Test
    void clearIconShownWhenTextPresent() {
        FxSearchField f = onFx(() -> FxSearchField.builder().clearable(true).build());
        assertNull(f.getInnerField().getTrailingIcon(), "No trailing when empty");
        onFx(() -> { f.setText("query"); return null; });
        assertNotNull(f.getInnerField().getTrailingIcon());
        assertEquals(f.getClearIcon(), f.getInnerField().getTrailingIcon());
    }

    @Test
    void clickingClearWipesText() {
        FxSearchField f = onFx(() -> FxSearchField.builder()
                .clearable(true)
                .text("hello")
                .build());
        assertNotNull(f.getInnerField().getTrailingIcon());
        onFx(() -> { f.getClearIcon().getOnMouseClicked().handle(
                new javafx.scene.input.MouseEvent(
                        javafx.scene.input.MouseEvent.MOUSE_CLICKED,
                        0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY,
                        1, false, false, false, false,
                        true, false, false, true, false, false, null)); return null; });
        assertEquals("", f.getText());
        assertNull(f.getInnerField().getTrailingIcon());
    }

    @Test
    void onSearchFiresOnEnter() {
        AtomicReference<String> got = new AtomicReference<>();
        FxSearchField f = onFx(() -> FxSearchField.builder()
                .onSearch(got::set)
                .build());
        onFx(() -> { f.setText("hi"); f.getInnerField().getTextField().fireEvent(
                new javafx.event.ActionEvent()); return null; });
        assertEquals("hi", got.get());
    }
}
