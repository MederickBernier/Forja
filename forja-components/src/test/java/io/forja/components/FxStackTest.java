package io.forja.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxStackTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxStack stack = onFx(() -> FxStack.builder().build());

        assertEquals(SpacingSize.MD, stack.getGap());
        assertEquals(12.0, stack.getSpacing(), "VBox.spacing should match MD token pixels");
        assertEquals(0, stack.getChildren().size());
        assertTrue(stack.getStyleClass().contains("forja-stack"));
    }

    @Test
    void builderSetsAllProperties() {
        Label a = new Label("A");
        Label b = new Label("B");
        FxStack stack = onFx(() -> FxStack.builder()
                .gap(SpacingSize.XL)
                .children(a, b)
                .alignment(Pos.CENTER)
                .id("stack-id")
                .styleClass("custom")
                .userData("payload")
                .build());

        assertEquals(SpacingSize.XL, stack.getGap());
        assertEquals(24.0, stack.getSpacing());
        assertEquals(2, stack.getChildren().size());
        assertEquals(Pos.CENTER, stack.getAlignment());
        assertEquals("stack-id", stack.getId());
        assertTrue(stack.getStyleClass().contains("custom"));
        assertEquals("payload", stack.getUserData());
    }

    @Test
    void constructorVariants() {
        FxStack empty = onFx(() -> new FxStack());
        FxStack withGap = onFx(() -> new FxStack(SpacingSize.LG));
        FxStack withChildren = onFx(() -> new FxStack(SpacingSize.SM, new Label("a"), new Label("b")));

        assertEquals(SpacingSize.MD, empty.getGap());
        assertEquals(SpacingSize.LG, withGap.getGap());
        assertEquals(16.0, withGap.getSpacing());
        assertEquals(SpacingSize.SM, withChildren.getGap());
        assertEquals(2, withChildren.getChildren().size());
    }

    @Test
    void changingGapUpdatesPixelSpacing() {
        FxStack stack = onFx(() -> FxStack.builder().gap(SpacingSize.NONE).build());

        assertEquals(0.0, stack.getSpacing());

        onFx(() -> { stack.setGap(SpacingSize.XL3); return null; });
        assertEquals(48.0, stack.getSpacing());

        onFx(() -> { stack.setGap(SpacingSize.XS); return null; });
        assertEquals(4.0, stack.getSpacing());
    }

    @Test
    void allTokensMapToCorrectPixels() {
        for (SpacingSize size : SpacingSize.values()) {
            FxStack stack = onFx(() -> FxStack.builder().gap(size).build());
            assertEquals(size.pixels(), stack.getSpacing(), "gap " + size + " should map to its pixel value");
        }
    }

    @Test
    void builderChildHelperAppendsSingleNode() {
        Label a = new Label("A");
        FxStack stack = onFx(() -> FxStack.builder().child(a).child(new Label("B")).build());

        assertEquals(2, stack.getChildren().size());
        assertEquals(a, stack.getChildren().get(0));
    }

    private static <T> T onFx(java.util.concurrent.Callable<T> body) {
        try {
            T result = WaitForAsyncUtils.asyncFx(body).get();
            WaitForAsyncUtils.waitForFxEvents();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
