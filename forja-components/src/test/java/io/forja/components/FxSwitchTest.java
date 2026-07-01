package io.forja.components;

import io.forja.components.selection.fxSwitch.FxSwitch;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.atomic.AtomicInteger;

import static io.forja.testsupport.ForjaTestSupport.assertHasPseudoClass;
import static io.forja.testsupport.ForjaTestSupport.assertLacksPseudoClass;
import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxSwitchTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxSwitch s = onFx(() -> FxSwitch.builder().build());
        assertEquals("", s.getText());
        assertFalse(s.isSelected());
        assertFalse(s.getTextLabel().isVisible());
        assertTrue(s.getStyleClass().contains("forja-switch"));
    }

    @Test
    void selectedPseudoClassTracksState() {
        FxSwitch s = onFx(() -> FxSwitch.builder().selected(true).build());
        assertHasPseudoClass(s, "selected");
        onFx(() -> { s.setSelected(false); return null; });
        assertLacksPseudoClass(s, "selected");
    }

    @Test
    void toggleFlipsState() {
        FxSwitch s = onFx(() -> FxSwitch.builder().build());
        onFx(() -> { s.toggle(); return null; });
        assertTrue(s.isSelected());
        onFx(() -> { s.toggle(); return null; });
        assertFalse(s.isSelected());
    }

    @Test
    void onChangeFires() {
        AtomicInteger n = new AtomicInteger();
        FxSwitch s = onFx(() -> FxSwitch.builder()
                .onChange(v -> n.incrementAndGet())
                .build());
        onFx(() -> { s.setSelected(true); return null; });
        assertEquals(1, n.get());
    }

    @Test
    void textShowsLabel() {
        FxSwitch s = onFx(() -> FxSwitch.builder().text("Dark").build());
        assertTrue(s.getTextLabel().isVisible());
        assertEquals("Dark", s.getTextLabel().getText());
    }
}
