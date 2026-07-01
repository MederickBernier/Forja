package io.forja.components;

import io.forja.components.feedbackAndStatus.fxProgressBar.FxProgressBar;
import io.forja.tokens.SemanticVariant;
import javafx.scene.control.ProgressBar;
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
class FxProgressBarTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxProgressBar b = onFx(() -> FxProgressBar.builder().build());
        assertEquals(SemanticVariant.ACCENT, b.getVariant());
        assertEquals(0.0, b.getProgress());
        assertTrue(b.getStyleClass().contains("forja-progress-bar"));
    }

    @Test
    void progressApplied() {
        FxProgressBar b = onFx(() -> FxProgressBar.builder().progress(0.5).build());
        assertEquals(0.5, b.getProgress());
    }

    @Test
    void indeterminateSetsSentinel() {
        FxProgressBar b = onFx(() -> FxProgressBar.builder().indeterminate(true).build());
        assertEquals(ProgressBar.INDETERMINATE_PROGRESS, b.getProgress());
    }

    @Test
    void allVariantsHavePseudoClass() {
        for (SemanticVariant v : SemanticVariant.values()) {
            FxProgressBar b = onFx(() -> FxProgressBar.builder().variant(v).build());
            assertHasPseudoClass(b, v.name().toLowerCase());
        }
    }
}
