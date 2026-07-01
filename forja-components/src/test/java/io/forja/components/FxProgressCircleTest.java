package io.forja.components;

import io.forja.components.feedbackAndStatus.fxProgressCircle.FxProgressCircle;
import io.forja.tokens.SemanticVariant;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.assertHasPseudoClass;
import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxProgressCircleTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxProgressCircle c = onFx(() -> FxProgressCircle.builder().build());
        assertEquals(SemanticVariant.ACCENT, c.getVariant());
        assertEquals(0.0, c.getProgress());
        assertTrue(c.getStyleClass().contains("forja-progress-circle"));
    }

    @Test
    void indeterminateSetsSentinel() {
        FxProgressCircle c = onFx(() -> FxProgressCircle.builder().indeterminate(true).build());
        assertEquals(ProgressIndicator.INDETERMINATE_PROGRESS, c.getProgress());
    }

    @Test
    void sizeAppliesToPref() {
        FxProgressCircle c = onFx(() -> FxProgressCircle.builder().size(32).build());
        assertEquals(32.0, c.getPrefWidth());
        assertEquals(32.0, c.getPrefHeight());
    }

    @Test
    void allVariantsHavePseudoClass() {
        for (SemanticVariant v : SemanticVariant.values()) {
            FxProgressCircle c = onFx(() -> FxProgressCircle.builder().variant(v).build());
            assertHasPseudoClass(c, v.name().toLowerCase());
        }
    }
}
