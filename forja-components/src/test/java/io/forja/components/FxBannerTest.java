package io.forja.components;

import io.forja.components.feedbackAndStatus.fxBanner.FxBanner;
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
class FxBannerTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxBanner b = onFx(() -> FxBanner.builder().build());
        assertEquals(SemanticVariant.INFO, b.getVariant());
        assertEquals("", b.getMessage());
        assertEquals("", b.getActionText());
        assertFalse(b.isDismissible());
        assertFalse(b.isDismissed());
        assertFalse(b.getActionButton().isVisible());
        assertTrue(b.getStyleClass().contains("forja-banner"));
    }

    @Test
    void allVariantsHavePseudoClass() {
        for (SemanticVariant v : SemanticVariant.values()) {
            FxBanner b = onFx(() -> FxBanner.builder().variant(v).build());
            assertHasPseudoClass(b, v.name().toLowerCase());
        }
    }

    @Test
    void actionButtonShowsWhenActionTextSet() {
        FxBanner b = onFx(() -> FxBanner.builder().actionText("Update").build());
        assertTrue(b.getActionButton().isVisible());
        assertEquals("Update", b.getActionButton().getText());
    }

    @Test
    void onActionFiresWhenActionButtonClicked() {
        AtomicInteger n = new AtomicInteger();
        FxBanner b = onFx(() -> FxBanner.builder()
                .actionText("Go")
                .onAction(e -> n.incrementAndGet())
                .build());
        onFx(() -> { b.getActionButton().fire(); return null; });
        assertEquals(1, n.get());
    }

    @Test
    void dismissHidesBanner() {
        FxBanner b = onFx(() -> FxBanner.builder().dismissible(true).build());
        onFx(() -> { b.dismiss(); return null; });
        assertTrue(b.isDismissed());
        assertFalse(b.isVisible());
    }
}
