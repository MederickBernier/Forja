package io.forja.components;

import io.forja.components.feedbackAndStatus.fxSkeleton.FxSkeleton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.assertHasPseudoClass;
import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxSkeletonTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxSkeleton s = onFx(() -> FxSkeleton.builder().build());
        assertEquals(FxSkeleton.Shape.RECT, s.getSkeletonShape());
        assertTrue(s.isAnimating());
        assertTrue(s.getStyleClass().contains("forja-skeleton"));
    }

    @Test
    void widthHeightApplied() {
        FxSkeleton s = onFx(() -> FxSkeleton.builder().width(120).height(24).build());
        assertEquals(120.0, s.getPrefWidth());
        assertEquals(24.0, s.getPrefHeight());
    }

    @Test
    void allShapesHavePseudoClass() {
        for (FxSkeleton.Shape sh : FxSkeleton.Shape.values()) {
            FxSkeleton s = onFx(() -> FxSkeleton.builder().shape(sh).build());
            assertHasPseudoClass(s, sh.name().toLowerCase());
        }
    }

    @Test
    void togglingAnimatingRestoresOpacity() {
        FxSkeleton s = onFx(() -> FxSkeleton.builder().animating(true).build());
        onFx(() -> { s.setAnimating(false); return null; });
        assertFalse(s.isAnimating());
        assertEquals(1.0, s.getOpacity());
    }
}
