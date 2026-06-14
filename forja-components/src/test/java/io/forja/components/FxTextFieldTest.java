package io.forja.components;

import io.forja.components.inputs.fxTextField.FxTextField;
import io.forja.components.inputs.fxTextField.TextFieldVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
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
class FxTextFieldTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxTextField field = onFx(() -> FxTextField.builder().build());

        assertEquals("", field.getText());
        assertEquals("", field.getPromptText());
        assertEquals(TextFieldVariant.DEFAULT, field.getVariant());
        assertNull(field.getLeadingIcon());
        assertNull(field.getTrailingIcon());
        assertEquals("", field.getHelperText());
        assertEquals("", field.getErrorText());
        assertFalse(field.isShowingError());
        assertFalse(field.getHelperLabel().isVisible());
        assertTrue(field.getStyleClass().contains("forja-text-field"));
        assertNotNull(field.getTextField());
        assertEquals(1, field.getFieldRow().getChildren().size(), "Row contains just the inner TextField when no icons");
    }

    @Test
    void textAndPromptTextDelegateToInnerField() {
        FxTextField field = onFx(() -> FxTextField.builder().build());

        onFx(() -> { field.setText("hello"); return null; });
        assertEquals("hello", field.getText());
        assertEquals("hello", field.getTextField().getText());

        onFx(() -> { field.setPromptText("prompt"); return null; });
        assertEquals("prompt", field.getPromptText());
        assertEquals("prompt", field.getTextField().getPromptText());
    }

    @Test
    void leadingAndTrailingIconsInsertedAroundField() {
        FxIcon lead = onFx(() -> new FxIcon("fth-search"));
        FxIcon trail = onFx(() -> new FxIcon("fth-x"));
        FxTextField field = onFx(() -> FxTextField.builder()
                .leadingIcon(lead)
                .trailingIcon(trail)
                .build());

        assertEquals(3, field.getFieldRow().getChildren().size());
        assertEquals(lead, field.getFieldRow().getChildren().get(0));
        assertEquals(field.getTextField(), field.getFieldRow().getChildren().get(1));
        assertEquals(trail, field.getFieldRow().getChildren().get(2));
    }

    @Test
    void mutatingIconSlotsRebuildsRow() {
        FxTextField field = onFx(() -> FxTextField.builder().build());
        assertEquals(1, field.getFieldRow().getChildren().size());

        FxIcon lead = onFx(() -> new FxIcon("fth-mail"));
        onFx(() -> { field.setLeadingIcon(lead); return null; });
        assertEquals(2, field.getFieldRow().getChildren().size());
        assertEquals(lead, field.getFieldRow().getChildren().get(0));

        onFx(() -> { field.setLeadingIcon(null); return null; });
        assertEquals(1, field.getFieldRow().getChildren().size());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxTextField field = onFx(() -> FxTextField.builder().variant(TextFieldVariant.SUCCESS).build());

        assertHasPseudoClass(field, "success");
        assertLacksPseudoClass(field, "default");
        assertLacksPseudoClass(field, "error");

        onFx(() -> { field.setVariant(TextFieldVariant.ERROR); return null; });

        assertHasPseudoClass(field, "error");
        assertLacksPseudoClass(field, "success");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (TextFieldVariant variant : TextFieldVariant.values()) {
            FxTextField field = onFx(() -> FxTextField.builder().variant(variant).build());
            assertHasPseudoClass(field, variant.name().toLowerCase());
        }
    }

    @Test
    void helperTextTogglesHelperLabelVisibility() {
        FxTextField field = onFx(() -> FxTextField.builder().build());
        assertFalse(field.getHelperLabel().isVisible());

        onFx(() -> { field.setHelperText("Optional"); return null; });
        assertTrue(field.getHelperLabel().isVisible());
        assertTrue(field.getHelperLabel().isManaged());
        assertEquals("Optional", field.getHelperLabel().getText());

        onFx(() -> { field.setHelperText(""); return null; });
        assertFalse(field.getHelperLabel().isVisible());
        assertFalse(field.getHelperLabel().isManaged());
    }

    @Test
    void errorTextAutoFlipsVariantAndShowsError() {
        FxTextField field = onFx(() -> FxTextField.builder()
                .helperText("Optional")
                .build());

        assertEquals(TextFieldVariant.DEFAULT, field.getVariant());

        onFx(() -> { field.setErrorText("Required"); return null; });

        assertEquals(TextFieldVariant.ERROR, field.getVariant());
        assertTrue(field.isShowingError());
        assertEquals("Required", field.getHelperLabel().getText());
    }

    @Test
    void errorTakesPrecedenceOverHelperText() {
        FxTextField field = onFx(() -> FxTextField.builder()
                .helperText("Helper")
                .errorText("Boom")
                .build());

        assertTrue(field.isShowingError());
        assertEquals("Boom", field.getHelperLabel().getText());
    }

    @Test
    void clearingErrorRevertsToHelperWithoutChangingVariant() {
        FxTextField field = onFx(() -> FxTextField.builder()
                .helperText("Helper")
                .errorText("Boom")
                .build());

        assertEquals(TextFieldVariant.ERROR, field.getVariant());

        onFx(() -> { field.setErrorText(""); return null; });

        assertFalse(field.isShowingError());
        assertEquals("Helper", field.getHelperLabel().getText());
        // Variant intentionally NOT auto-reverted
        assertEquals(TextFieldVariant.ERROR, field.getVariant());
    }

    @Test
    void leadingIconLiteralSugarCreatesFxIcon() {
        FxTextField field = onFx(() -> FxTextField.builder()
                .leadingIcon("fth-search")
                .build());

        assertNotNull(field.getLeadingIcon());
        assertTrue(field.getLeadingIcon() instanceof FxIcon);
        assertEquals("fth-search", ((FxIcon) field.getLeadingIcon()).getIconLiteral());
    }
}
