package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.components.layout.fxSpacer.FxSpacer;

import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxSpacerTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void flexDefaultsSetHgrowAndVgrowAlways() {
        FxSpacer spacer = onFx(() -> FxSpacer.builder().build());

        assertEquals(Priority.ALWAYS, HBox.getHgrow(spacer));
        assertEquals(Priority.ALWAYS, VBox.getVgrow(spacer));
        assertTrue(spacer.getStyleClass().contains("forja-spacer"));
    }

    @Test
    void fixedSizeBothAxesPinsMinPrefMax() {
        FxSpacer spacer = onFx(() -> FxSpacer.builder().size(24).build());

        assertEquals(24, spacer.getMinWidth());
        assertEquals(24, spacer.getPrefWidth());
        assertEquals(24, spacer.getMaxWidth());
        assertEquals(24, spacer.getMinHeight());
        assertEquals(24, spacer.getPrefHeight());
        assertEquals(24, spacer.getMaxHeight());
    }

    @Test
    void fixedSizeHorizontalOnlyPinsWidthAxis() {
        FxSpacer spacer = onFx(() -> FxSpacer.builder().size(48, Orientation.HORIZONTAL).build());

        assertEquals(48, spacer.getMinWidth());
        assertEquals(48, spacer.getPrefWidth());
        assertEquals(48, spacer.getMaxWidth());
        assertEquals(Region.USE_COMPUTED_SIZE, spacer.getMinHeight());
        assertEquals(Region.USE_COMPUTED_SIZE, spacer.getPrefHeight());
        assertEquals(Region.USE_COMPUTED_SIZE, spacer.getMaxHeight());
    }

    @Test
    void fixedSizeVerticalOnlyPinsHeightAxis() {
        FxSpacer spacer = onFx(() -> FxSpacer.builder().size(32, Orientation.VERTICAL).build());

        assertEquals(32, spacer.getMinHeight());
        assertEquals(32, spacer.getPrefHeight());
        assertEquals(32, spacer.getMaxHeight());
        assertEquals(Region.USE_COMPUTED_SIZE, spacer.getMinWidth());
    }

    @Test
    void constructorVariants() {
        FxSpacer flex = onFx(() -> new FxSpacer());
        FxSpacer square = onFx(() -> new FxSpacer(16));
        FxSpacer horizontal = onFx(() -> new FxSpacer(20, Orientation.HORIZONTAL));

        assertEquals(Priority.ALWAYS, HBox.getHgrow(flex));
        assertEquals(16, square.getPrefWidth());
        assertEquals(16, square.getPrefHeight());
        assertEquals(20, horizontal.getPrefWidth());
        assertEquals(Region.USE_COMPUTED_SIZE, horizontal.getPrefHeight());
    }

    @Test
    void builderAppliesBaseProperties() {
        FxSpacer spacer = onFx(() -> FxSpacer.builder()
                .id("hero-spacer")
                .styleClass("custom")
                .userData("payload")
                .visible(false)
                .build());

        assertEquals("hero-spacer", spacer.getId());
        assertTrue(spacer.getStyleClass().contains("custom"));
        assertEquals("payload", spacer.getUserData());
        assertEquals(false, spacer.isVisible());
    }
}
