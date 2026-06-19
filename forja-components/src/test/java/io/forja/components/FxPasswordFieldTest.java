package io.forja.components;

import io.forja.components.inputs.InputVariant;
import io.forja.components.inputs.fxPasswordField.FxPasswordField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxPasswordFieldTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxPasswordField field = onFx(() -> FxPasswordField.builder().build());

        assertEquals("", field.getText());
        assertEquals("", field.getPromptText());
        assertEquals(InputVariant.DEFAULT, field.getVariant());
        assertNull(field.getLeadingIcon());
        assertEquals("", field.getHelperText());
        assertEquals("", field.getErrorText());
        assertFalse(field.isRevealable());
        assertFalse(field.isRevealed());
        assertFalse(field.isShowingError());
        assertTrue(field.getStyleClass().contains("forja-password-field"));
        assertNotNull(field.getPasswordField());
        assertNotNull(field.getRevealField());
        assertTrue(field.getPasswordField().isVisible());
        assertFalse(field.getRevealField().isVisible());
    }

    @Test
    void textIsSyncedBetweenMaskedAndPlainInputs() {
        FxPasswordField field = onFx(() -> FxPasswordField.builder().build());

        onFx(() -> { field.setText("hunter2"); return null; });

        assertEquals("hunter2", field.getText());
        assertEquals("hunter2", field.getPasswordField().getText());
        assertEquals("hunter2", field.getRevealField().getText());
    }

    @Test
    void revealableShowsToggleAndAllowsSwitch() {
        FxPasswordField field = onFx(() -> FxPasswordField.builder()
                .revealable(true)
                .build());

        assertTrue(field.getFieldRow().getChildren().contains(field.getRevealIcon()));

        onFx(() -> { field.setRevealed(true); return null; });

        assertTrue(field.getRevealField().isVisible());
        assertFalse(field.getPasswordField().isVisible());
        assertEquals("fth-eye-off", field.getRevealIcon().getIconLiteral());

        onFx(() -> { field.setRevealed(false); return null; });

        assertFalse(field.getRevealField().isVisible());
        assertTrue(field.getPasswordField().isVisible());
        assertEquals("fth-eye", field.getRevealIcon().getIconLiteral());
    }

    @Test
    void disablingRevealableForcesMaskedAndHidesToggle() {
        FxPasswordField field = onFx(() -> FxPasswordField.builder()
                .revealable(true)
                .revealed(true)
                .build());

        assertTrue(field.isRevealed());
        assertTrue(field.getFieldRow().getChildren().contains(field.getRevealIcon()));

        onFx(() -> { field.setRevealable(false); return null; });

        assertFalse(field.isRevealed(), "Disabling revealable should force revealed=false");
        assertTrue(field.getPasswordField().isVisible());
        assertFalse(field.getFieldRow().getChildren().contains(field.getRevealIcon()));
    }

    @Test
    void variantPseudoClassUpdates() {
        FxPasswordField field = onFx(() -> FxPasswordField.builder().variant(InputVariant.SUCCESS).build());

        assertHasPseudoClass(field, "success");
        assertLacksPseudoClass(field, "default");

        onFx(() -> { field.setVariant(InputVariant.ERROR); return null; });

        assertHasPseudoClass(field, "error");
        assertLacksPseudoClass(field, "success");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (InputVariant variant : InputVariant.values()) {
            FxPasswordField field = onFx(() -> FxPasswordField.builder().variant(variant).build());
            assertHasPseudoClass(field, variant.name().toLowerCase());
        }
    }

    @Test
    void errorTextAutoFlipsVariant() {
        FxPasswordField field = onFx(() -> FxPasswordField.builder().build());

        onFx(() -> { field.setErrorText("Too short"); return null; });

        assertEquals(InputVariant.ERROR, field.getVariant());
        assertTrue(field.isShowingError());
        assertEquals("Too short", field.getHelperLabel().getText());
    }

    @Test
    void errorTakesPrecedenceOverHelperText() {
        FxPasswordField field = onFx(() -> FxPasswordField.builder()
                .helperText("Helper")
                .errorText("Boom")
                .build());

        assertTrue(field.isShowingError());
        assertEquals("Boom", field.getHelperLabel().getText());
    }

    @Test
    void leadingIconLiteralSugarCreatesFxIcon() {
        FxPasswordField field = onFx(() -> FxPasswordField.builder()
                .leadingIcon("fth-lock")
                .build());

        assertNotNull(field.getLeadingIcon());
    }
}
