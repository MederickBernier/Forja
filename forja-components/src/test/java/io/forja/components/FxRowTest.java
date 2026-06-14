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
class FxRowTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxRow row = onFx(() -> FxRow.builder().build());

        assertEquals(SpacingSize.MD, row.getGap());
        assertEquals(12.0, row.getSpacing(), "HBox.spacing should match MD token pixels");
        assertEquals(0, row.getChildren().size());
        assertTrue(row.getStyleClass().contains("forja-row"));
    }

    @Test
    void builderSetsAllProperties() {
        Label a = new Label("A");
        Label b = new Label("B");
        FxRow row = onFx(() -> FxRow.builder()
                .gap(SpacingSize.LG)
                .children(a, b)
                .alignment(Pos.CENTER_RIGHT)
                .id("row-id")
                .styleClass("custom")
                .userData("payload")
                .build());

        assertEquals(SpacingSize.LG, row.getGap());
        assertEquals(16.0, row.getSpacing());
        assertEquals(2, row.getChildren().size());
        assertEquals(Pos.CENTER_RIGHT, row.getAlignment());
        assertEquals("row-id", row.getId());
        assertTrue(row.getStyleClass().contains("custom"));
        assertEquals("payload", row.getUserData());
    }

    @Test
    void constructorVariants() {
        FxRow empty = onFx(() -> new FxRow());
        FxRow withGap = onFx(() -> new FxRow(SpacingSize.XL));
        FxRow withChildren = onFx(() -> new FxRow(SpacingSize.XS, new Label("a"), new Label("b")));

        assertEquals(SpacingSize.MD, empty.getGap());
        assertEquals(SpacingSize.XL, withGap.getGap());
        assertEquals(24.0, withGap.getSpacing());
        assertEquals(SpacingSize.XS, withChildren.getGap());
        assertEquals(4.0, withChildren.getSpacing());
        assertEquals(2, withChildren.getChildren().size());
    }

    @Test
    void changingGapUpdatesPixelSpacing() {
        FxRow row = onFx(() -> FxRow.builder().build());

        onFx(() -> { row.setGap(SpacingSize.XL3); return null; });
        assertEquals(48.0, row.getSpacing());

        onFx(() -> { row.setGap(SpacingSize.NONE); return null; });
        assertEquals(0.0, row.getSpacing());
    }

    @Test
    void allTokensMapToCorrectPixels() {
        for (SpacingSize size : SpacingSize.values()) {
            FxRow row = onFx(() -> FxRow.builder().gap(size).build());
            assertEquals(size.pixels(), row.getSpacing(), "gap " + size + " should map to its pixel value");
        }
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
