package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.components.layout.fxContainer.ContainerWidth;
import io.forja.components.layout.fxContainer.FxContainer;
import io.forja.tokens.SpacingSize;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxContainerTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxContainer container = onFx(() -> FxContainer.builder().build());

        assertEquals(ContainerWidth.LG, container.getContainerWidth());
        assertEquals(1024.0, container.getMaxWidth());
        assertEquals(SpacingSize.LG, container.getContentPadding());
        assertEquals(new Insets(16), container.getPadding());
        assertEquals(Pos.TOP_CENTER, container.getAlignment());
        assertTrue(container.getStyleClass().contains("forja-container"));
    }

    @Test
    void builderSetsAllProperties() {
        Label child = new Label("content");
        FxContainer container = onFx(() -> FxContainer.builder()
                .width(ContainerWidth.SM)
                .padding(SpacingSize.XL)
                .child(child)
                .alignment(Pos.CENTER)
                .id("container-id")
                .styleClass("custom")
                .userData("payload")
                .build());

        assertEquals(ContainerWidth.SM, container.getContainerWidth());
        assertEquals(640.0, container.getMaxWidth());
        assertEquals(SpacingSize.XL, container.getContentPadding());
        assertEquals(new Insets(24), container.getPadding());
        assertEquals(1, container.getChildren().size());
        assertEquals(Pos.CENTER, container.getAlignment());
        assertEquals("container-id", container.getId());
        assertTrue(container.getStyleClass().contains("custom"));
        assertEquals("payload", container.getUserData());
    }

    @Test
    void allWidthsMapToCorrectMaxWidth() {
        for (ContainerWidth width : ContainerWidth.values()) {
            FxContainer container = onFx(() -> FxContainer.builder().width(width).build());
            assertEquals(width.pixels(), container.getMaxWidth(),
                    "width " + width + " should map to its pixel max");
        }
    }

    @Test
    void allPaddingTokensMapToInsets() {
        for (SpacingSize size : SpacingSize.values()) {
            FxContainer container = onFx(() -> FxContainer.builder().padding(size).build());
            assertEquals(new Insets(size.pixels()), container.getPadding());
        }
    }

    @Test
    void changingPropertiesReappliesValues() {
        FxContainer container = onFx(() -> FxContainer.builder().build());

        onFx(() -> { container.setContainerWidth(ContainerWidth.XL); return null; });
        assertEquals(1280.0, container.getMaxWidth());

        onFx(() -> { container.setContentPadding(SpacingSize.XS); return null; });
        assertEquals(new Insets(4), container.getPadding());

        onFx(() -> { container.setContainerWidth(ContainerWidth.FLUID); return null; });
        assertEquals(Region.USE_COMPUTED_SIZE, container.getMaxWidth());
    }

    @Test
    void constructorVariants() {
        FxContainer empty = onFx(() -> new FxContainer());
        FxContainer withWidth = onFx(() -> new FxContainer(ContainerWidth.MD));
        FxContainer withChildren = onFx(() -> new FxContainer(ContainerWidth.SM, new Label("a"), new Label("b")));

        assertEquals(ContainerWidth.LG, empty.getContainerWidth());
        assertEquals(ContainerWidth.MD, withWidth.getContainerWidth());
        assertEquals(768.0, withWidth.getMaxWidth());
        assertEquals(ContainerWidth.SM, withChildren.getContainerWidth());
        assertEquals(2, withChildren.getChildren().size());
    }
}
