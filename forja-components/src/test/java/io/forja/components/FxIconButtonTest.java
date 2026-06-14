package io.forja.components;

import io.forja.skin.FxIconButtonSkin;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxIconButtonTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxIconButton button = onFx(() -> FxIconButton.builder().build());

        assertEquals("", button.getText());
        assertEquals(ButtonVariant.PRIMARY, button.getVariant());
        assertEquals(IconPosition.LEFT, button.getIconPosition());
        assertEquals(16, button.getIconSize());
        assertFalse(button.isLoading());
        assertNull(button.getIconLiteral());
        assertNull(button.getGraphic());
        assertTrue(button.getStyleClass().contains("forja-button"));
        assertTrue(button.getStyleClass().contains("forja-icon-button"));
    }

    @Test
    void builderSetsAllProperties() {
        AtomicBoolean fired = new AtomicBoolean(false);
        EventHandler<ActionEvent> handler = e -> fired.set(true);

        FxIconButton button = onFx(() -> FxIconButton.builder()
                .text("Save")
                .icon("fth-save")
                .iconPosition(IconPosition.RIGHT)
                .iconSize(20)
                .variant(ButtonVariant.DANGER)
                .loading(true)
                .onAction(handler)
                .build());

        assertEquals("Save", button.getText());
        assertEquals("fth-save", button.getIconLiteral());
        assertEquals(IconPosition.RIGHT, button.getIconPosition());
        assertEquals(20, button.getIconSize());
        assertEquals(ButtonVariant.DANGER, button.getVariant());
        assertTrue(button.isLoading());
        assertNotNull(button.getGraphic());
        assertTrue(button.getGraphic() instanceof FxIcon);
        assertEquals("fth-save", ((FxIcon) button.getGraphic()).getIconLiteral());
        assertEquals(20, ((FxIcon) button.getGraphic()).getIconSize());

        onFx(() -> {
            button.getOnAction().handle(new ActionEvent());
            return null;
        });
        assertTrue(fired.get());
    }

    @Test
    void constructorVariants() {
        FxIconButton empty = onFx(() -> new FxIconButton());
        FxIconButton withLiteral = onFx(() -> new FxIconButton("fth-save"));
        FxIconButton withPosition = onFx(() -> new FxIconButton("fth-x", IconPosition.ONLY));
        FxIconButton withText = onFx(() -> new FxIconButton("Save", "fth-save"));

        assertNull(empty.getIconLiteral());
        assertNull(empty.getGraphic());
        assertEquals("fth-save", withLiteral.getIconLiteral());
        assertNotNull(withLiteral.getGraphic());
        assertEquals(IconPosition.ONLY, withPosition.getIconPosition());
        assertEquals("Save", withText.getText());
        assertEquals("fth-save", withText.getIconLiteral());
    }

    @Test
    void iconPositionMapsToContentDisplay() {
        FxIconButton button = onFx(() -> FxIconButton.builder().icon("fth-save").build());

        assertEquals(ContentDisplay.LEFT, button.getContentDisplay());

        onFx(() -> { button.setIconPosition(IconPosition.RIGHT); return null; });
        assertEquals(ContentDisplay.RIGHT, button.getContentDisplay());

        onFx(() -> { button.setIconPosition(IconPosition.ONLY); return null; });
        assertEquals(ContentDisplay.GRAPHIC_ONLY, button.getContentDisplay());
    }

    @Test
    void iconPositionPseudoClassUpdates() {
        FxIconButton button = onFx(() -> {
            FxIconButton b = FxIconButton.builder().icon("fth-save").build();
            b.setSkin(new FxIconButtonSkin(b));
            return b;
        });

        assertHasPseudoClass(button, "icon-left");
        assertLacksPseudoClass(button, "icon-right");
        assertLacksPseudoClass(button, "icon-only");

        onFx(() -> { button.setIconPosition(IconPosition.ONLY); return null; });

        assertHasPseudoClass(button, "icon-only");
        assertLacksPseudoClass(button, "icon-left");
    }

    @Test
    void allIconPositionsHaveCorrespondingPseudoClass() {
        for (IconPosition position : IconPosition.values()) {
            FxIconButton button = onFx(() -> {
                FxIconButton b = FxIconButton.builder().icon("fth-save").iconPosition(position).build();
                b.setSkin(new FxIconButtonSkin(b));
                return b;
            });
            String expected = "icon-" + position.name().toLowerCase();
            assertHasPseudoClass(button, expected);
        }
    }

    @Test
    void variantAndLoadingInheritFxButtonBehavior() {
        FxIconButton button = onFx(() -> {
            FxIconButton b = FxIconButton.builder().icon("fth-save").variant(ButtonVariant.GHOST).build();
            b.setSkin(new FxIconButtonSkin(b));
            return b;
        });

        assertHasPseudoClass(button, "ghost");
        assertFalse(button.isDisabled());

        onFx(() -> { button.setLoading(true); return null; });
        assertTrue(button.isDisabled());
        assertHasPseudoClass(button, "loading");

        onFx(() -> { button.setLoading(false); return null; });
        assertFalse(button.isDisabled());
        assertLacksPseudoClass(button, "loading");
    }

    @Test
    void changingIconLiteralRebuildsGraphic() {
        FxIconButton button = onFx(() -> FxIconButton.builder().icon("fth-save").build());

        assertEquals("fth-save", ((FxIcon) button.getGraphic()).getIconLiteral());

        onFx(() -> { button.setIconLiteral("fth-trash-2"); return null; });
        assertEquals("fth-trash-2", ((FxIcon) button.getGraphic()).getIconLiteral());

        onFx(() -> { button.setIconLiteral(null); return null; });
        assertNull(button.getGraphic());
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

    private static void assertHasPseudoClass(FxIconButton button, String name) {
        assertTrue(
                button.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Expected pseudo-class :" + name + " on " + button
        );
    }

    private static void assertLacksPseudoClass(FxIconButton button, String name) {
        assertFalse(
                button.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Did not expect pseudo-class :" + name + " on " + button
        );
    }
}
