package io.forja.components;

import io.forja.components.feedbackAndStatus.fxAlert.FxAlert;
import io.forja.tokens.SemanticVariant;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.atomic.AtomicInteger;

import static io.forja.testsupport.ForjaTestSupport.assertHasPseudoClass;
import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxAlertTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxAlert a = onFx(() -> FxAlert.builder().build());
        assertEquals(SemanticVariant.INFO, a.getVariant());
        assertEquals("", a.getTitle());
        assertEquals("", a.getDescription());
        assertFalse(a.isDismissible());
        assertFalse(a.isDismissed());
        assertTrue(a.getStyleClass().contains("forja-alert"));
    }

    @Test
    void titleAndDescriptionApplied() {
        FxAlert a = onFx(() -> FxAlert.builder()
                .title("Saved")
                .description("All good.")
                .build());
        assertEquals("Saved", a.getTitleLabel().getText());
        assertEquals("All good.", a.getDescriptionLabel().getText());
        assertTrue(a.getTitleLabel().isVisible());
        assertTrue(a.getDescriptionLabel().isVisible());
    }

    @Test
    void allVariantsHavePseudoClass() {
        for (SemanticVariant v : SemanticVariant.values()) {
            FxAlert a = onFx(() -> FxAlert.builder().variant(v).build());
            assertHasPseudoClass(a, v.name().toLowerCase());
        }
    }

    @Test
    void dismissibleAddsIcon() {
        FxAlert a = onFx(() -> FxAlert.builder().dismissible(true).build());
        assertTrue(a.getChildren().contains(a.getDismissIcon()));
    }

    @Test
    void dismissHidesAlertAndFiresCallback() {
        AtomicInteger n = new AtomicInteger();
        FxAlert a = onFx(() -> FxAlert.builder()
                .dismissible(true)
                .onDismiss(x -> n.incrementAndGet())
                .build());
        onFx(() -> { a.dismiss(); return null; });
        assertTrue(a.isDismissed());
        assertFalse(a.isVisible());
        assertEquals(1, n.get());
    }
}
