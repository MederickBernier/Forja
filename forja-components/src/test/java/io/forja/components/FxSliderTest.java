package io.forja.components;

import io.forja.components.selection.fxSlider.FxSlider;
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
class FxSliderTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxSlider s = onFx(() -> FxSlider.builder().build());
        assertEquals(0.0, s.getMin());
        assertEquals(100.0, s.getMax());
        assertEquals(0.0, s.getValue());
        assertEquals(1.0, s.getStep());
        assertFalse(s.isShowValue());
        assertFalse(s.getValueLabel().isVisible());
        assertTrue(s.getStyleClass().contains("forja-slider"));
    }

    @Test
    void valueSyncsToInnerSlider() {
        FxSlider s = onFx(() -> FxSlider.builder().value(42).build());
        assertEquals(42.0, s.getSlider().getValue());
    }

    @Test
    void innerSliderSyncsBackToValue() {
        FxSlider s = onFx(() -> FxSlider.builder().build());
        onFx(() -> { s.getSlider().setValue(75); return null; });
        assertEquals(75.0, s.getValue());
    }

    @Test
    void showValueRendersFormattedLabel() {
        FxSlider s = onFx(() -> FxSlider.builder()
                .min(0).max(1)
                .value(0.5)
                .decimals(2)
                .suffix("x")
                .showValue(true)
                .build());
        assertTrue(s.getValueLabel().isVisible());
        assertEquals("0.50x", s.getValueLabel().getText());
    }

    @Test
    void prefixAndSuffixAffectLabel() {
        FxSlider s = onFx(() -> FxSlider.builder()
                .value(50)
                .prefix("$")
                .showValue(true)
                .build());
        assertEquals("$50", s.getValueLabel().getText());
    }

    @Test
    void stepUpdatesBlockIncrement() {
        FxSlider s = onFx(() -> FxSlider.builder().step(5).build());
        assertEquals(5.0, s.getSlider().getBlockIncrement());
    }
}
