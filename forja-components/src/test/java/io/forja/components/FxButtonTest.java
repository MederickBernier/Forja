package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxButton.FxButton;

import io.forja.components.buttonsAndActions.fxButton.FxButtonSkin;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxButtonTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxButton button = onFx(() -> FxButton.builder().build());

        assertEquals("", button.getText());
        assertEquals(ButtonVariant.PRIMARY, button.getVariant());
        assertFalse(button.isLoading());
        assertTrue(button.getStyleClass().contains("forja-button"));
    }

    @Test
    void builderSetsAllProperties() {
        AtomicBoolean fired = new AtomicBoolean(false);
        EventHandler<ActionEvent> handler = e -> fired.set(true);

        FxButton button = onFx(() -> FxButton.builder()
                .text("Save")
                .variant(ButtonVariant.DANGER)
                .loading(true)
                .onAction(handler)
                .build());

        assertEquals("Save", button.getText());
        assertEquals(ButtonVariant.DANGER, button.getVariant());
        assertTrue(button.isLoading());

        onFx(() -> {
            button.getOnAction().handle(new ActionEvent());
            return null;
        });
        assertTrue(fired.get());
    }

    @Test
    void constructorVariants() {
        FxButton empty = onFx(() -> new FxButton());
        FxButton withText = onFx(() -> new FxButton("Hi"));
        FxButton withVariant = onFx(() -> new FxButton("Hi", ButtonVariant.GHOST));

        assertEquals("", empty.getText());
        assertEquals(ButtonVariant.PRIMARY, empty.getVariant());
        assertEquals("Hi", withText.getText());
        assertEquals(ButtonVariant.PRIMARY, withText.getVariant());
        assertEquals("Hi", withVariant.getText());
        assertEquals(ButtonVariant.GHOST, withVariant.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxButton button = onFx(() -> {
            FxButton b = FxButton.builder().variant(ButtonVariant.SECONDARY).build();
            b.setSkin(new FxButtonSkin(b));
            return b;
        });

        assertHasPseudoClass(button, "secondary");
        assertLacksPseudoClass(button, "primary");
        assertLacksPseudoClass(button, "ghost");
        assertLacksPseudoClass(button, "danger");

        onFx(() -> {
            button.setVariant(ButtonVariant.DANGER);
            return null;
        });

        assertHasPseudoClass(button, "danger");
        assertLacksPseudoClass(button, "secondary");
    }

    @Test
    void loadingDisablesButtonAndSetsPseudoClass() {
        FxButton button = onFx(() -> {
            FxButton b = FxButton.builder().build();
            b.setSkin(new FxButtonSkin(b));
            return b;
        });

        assertFalse(button.isDisabled());
        assertLacksPseudoClass(button, "loading");

        onFx(() -> {
            button.setLoading(true);
            return null;
        });

        assertTrue(button.isLoading());
        assertTrue(button.isDisabled());
        assertHasPseudoClass(button, "loading");

        onFx(() -> {
            button.setLoading(false);
            return null;
        });

        assertFalse(button.isLoading());
        assertFalse(button.isDisabled());
        assertLacksPseudoClass(button, "loading");
    }

    @Test
    void skinAttachesViaSceneRendering() {
        FxButton button = onFx(() -> {
            FxButton b = FxButton.builder().variant(ButtonVariant.GHOST).build();
            Scene scene = new Scene(new StackPane(b), 200, 100);
            b.applyCss();
            return b;
        });

        assertHasPseudoClass(button, "ghost");
    }
}
