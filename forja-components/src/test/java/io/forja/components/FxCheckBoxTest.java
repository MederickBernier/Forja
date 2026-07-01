package io.forja.components;

import io.forja.components.selection.fxCheckBox.FxCheckBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.atomic.AtomicInteger;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxCheckBoxTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxCheckBox b = onFx(() -> FxCheckBox.builder().build());
        assertEquals("", b.getText());
        assertFalse(b.isSelected());
        assertFalse(b.isIndeterminate());
        assertFalse(b.isAllowIndeterminate());
        assertTrue(b.getStyleClass().contains("forja-checkbox"));
    }

    @Test
    void textAndSelectedApplied() {
        FxCheckBox b = onFx(() -> FxCheckBox.builder()
                .text("Agree")
                .selected(true)
                .build());
        assertEquals("Agree", b.getText());
        assertTrue(b.isSelected());
    }

    @Test
    void indeterminateRequiresAllowFlag() {
        FxCheckBox b = onFx(() -> FxCheckBox.builder()
                .allowIndeterminate(true)
                .indeterminate(true)
                .build());
        assertTrue(b.isAllowIndeterminate());
        assertTrue(b.isIndeterminate());
    }

    @Test
    void onActionFires() {
        AtomicInteger n = new AtomicInteger();
        FxCheckBox b = onFx(() -> FxCheckBox.builder()
                .onAction(e -> n.incrementAndGet())
                .build());
        onFx(() -> { b.fire(); return null; });
        assertEquals(1, n.get());
    }

    @Test
    void inheritedBaseAppliesDisabledAndId() {
        FxCheckBox b = onFx(() -> FxCheckBox.builder()
                .id("agree")
                .disabled(true)
                .build());
        assertEquals("agree", b.getId());
        assertTrue(b.isDisabled());
    }
}
