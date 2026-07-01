package io.forja.components;

import io.forja.components.inputs.InputVariant;
import io.forja.components.inputs.fxNumberField.FxNumberField;
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
class FxNumberFieldTest {

    @Start
    void start(Stage stage) {
        // ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxNumberField field = onFx(() -> FxNumberField.builder().build());

        assertNull(field.getValue());
        assertNull(field.getMin());
        assertNull(field.getMax());
        assertEquals(1.0, field.getStep());
        assertEquals(0, field.getDecimals());
        assertEquals("", field.getPrefix());
        assertEquals("", field.getSuffix());
        assertFalse(field.isShowSteppers());
        assertEquals(InputVariant.DEFAULT, field.getVariant());
        assertNull(field.getLeadingIcon());
        assertEquals("", field.getHelperText());
        assertEquals("", field.getErrorText());
        assertFalse(field.isShowingError());
        assertFalse(field.getHelperLabel().isVisible());
        assertTrue(field.getStyleClass().contains("forja-number-field"));
        assertNotNull(field.getTextField());
        assertEquals(1, field.getFieldRow().getChildren().size());
    }

    @Test
    void valueSyncsToTextWithDecimalsFormatting() {
        FxNumberField field = onFx(() -> FxNumberField.builder()
                .decimals(2)
                .value(3.5)
                .build());

        assertEquals("3.50", field.getTextField().getText());

        onFx(() -> { field.setValue(1.0); return null; });
        assertEquals("1.00", field.getTextField().getText());

        onFx(() -> { field.setValue(null); return null; });
        assertEquals("", field.getTextField().getText());
    }

    @Test
    void setValueClampsToBounds() {
        FxNumberField field = onFx(() -> FxNumberField.builder()
                .min(0.0)
                .max(10.0)
                .build());

        onFx(() -> { field.setValue(50.0); return null; });
        assertEquals(10.0, field.getValue());

        onFx(() -> { field.setValue(-5.0); return null; });
        assertEquals(0.0, field.getValue());
    }

    @Test
    void incrementAndDecrementApplyStepAndClamp() {
        FxNumberField field = onFx(() -> FxNumberField.builder()
                .value(0.0)
                .step(0.5)
                .max(1.0)
                .min(-1.0)
                .decimals(1)
                .build());

        onFx(() -> { field.increment(); return null; });
        assertEquals(0.5, field.getValue());

        onFx(() -> { field.increment(); return null; });
        onFx(() -> { field.increment(); return null; });
        assertEquals(1.0, field.getValue(), "Clamped at max");

        onFx(() -> { field.setValue(-1.0); field.decrement(); return null; });
        assertEquals(-1.0, field.getValue(), "Clamped at min");
    }

    @Test
    void incrementFromNullUsesZeroDefault() {
        FxNumberField field = onFx(() -> FxNumberField.builder().step(2.0).build());

        onFx(() -> { field.increment(); return null; });
        assertEquals(2.0, field.getValue());
    }

    @Test
    void prefixSuffixAndSteppersInsertedIntoRow() {
        FxNumberField field = onFx(() -> FxNumberField.builder()
                .prefix("$")
                .suffix("kg")
                .showSteppers(true)
                .build());

        // prefix, textfield, suffix, steppers → 4
        assertEquals(4, field.getFieldRow().getChildren().size());
        assertEquals(field.getPrefixLabel(), field.getFieldRow().getChildren().get(0));
        assertEquals(field.getTextField(), field.getFieldRow().getChildren().get(1));
        assertEquals(field.getSuffixLabel(), field.getFieldRow().getChildren().get(2));
        assertEquals(field.getStepperColumn(), field.getFieldRow().getChildren().get(3));
    }

    @Test
    void leadingIconInserted() {
        FxIcon icon = onFx(() -> new FxIcon("fth-hash"));
        FxNumberField field = onFx(() -> FxNumberField.builder()
                .leadingIcon(icon)
                .build());

        assertEquals(2, field.getFieldRow().getChildren().size());
        assertEquals(icon, field.getFieldRow().getChildren().get(0));
    }

    @Test
    void togglingSteppersRebuildsRow() {
        FxNumberField field = onFx(() -> FxNumberField.builder().build());
        assertEquals(1, field.getFieldRow().getChildren().size());

        onFx(() -> { field.setShowSteppers(true); return null; });
        assertEquals(2, field.getFieldRow().getChildren().size());

        onFx(() -> { field.setShowSteppers(false); return null; });
        assertEquals(1, field.getFieldRow().getChildren().size());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxNumberField field = onFx(() -> FxNumberField.builder().variant(InputVariant.SUCCESS).build());

        assertHasPseudoClass(field, "success");
        assertLacksPseudoClass(field, "default");
        assertLacksPseudoClass(field, "error");

        onFx(() -> { field.setVariant(InputVariant.ERROR); return null; });

        assertHasPseudoClass(field, "error");
        assertLacksPseudoClass(field, "success");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (InputVariant variant : InputVariant.values()) {
            FxNumberField field = onFx(() -> FxNumberField.builder().variant(variant).build());
            assertHasPseudoClass(field, variant.name().toLowerCase());
        }
    }

    @Test
    void helperTextTogglesHelperLabelVisibility() {
        FxNumberField field = onFx(() -> FxNumberField.builder().build());
        assertFalse(field.getHelperLabel().isVisible());

        onFx(() -> { field.setHelperText("Enter a number"); return null; });
        assertTrue(field.getHelperLabel().isVisible());
        assertEquals("Enter a number", field.getHelperLabel().getText());

        onFx(() -> { field.setHelperText(""); return null; });
        assertFalse(field.getHelperLabel().isVisible());
    }

    @Test
    void errorTextAutoFlipsVariantAndShowsError() {
        FxNumberField field = onFx(() -> FxNumberField.builder()
                .helperText("Optional")
                .build());

        assertEquals(InputVariant.DEFAULT, field.getVariant());

        onFx(() -> { field.setErrorText("Bad number"); return null; });

        assertEquals(InputVariant.ERROR, field.getVariant());
        assertTrue(field.isShowingError());
        assertEquals("Bad number", field.getHelperLabel().getText());
    }

    @Test
    void errorTakesPrecedenceOverHelperText() {
        FxNumberField field = onFx(() -> FxNumberField.builder()
                .helperText("Helper")
                .errorText("Boom")
                .build());

        assertTrue(field.isShowingError());
        assertEquals("Boom", field.getHelperLabel().getText());
    }

    @Test
    void inputFilterRejectsNonNumericInsertion() {
        FxNumberField field = onFx(() -> FxNumberField.builder().build());

        onFx(() -> { field.getTextField().setText("abc"); return null; });
        assertEquals("", field.getTextField().getText());

        onFx(() -> { field.getTextField().setText("42"); return null; });
        assertEquals("42", field.getTextField().getText());
    }

    @Test
    void inputFilterHonorsDecimalsCap() {
        FxNumberField field = onFx(() -> FxNumberField.builder().decimals(2).build());

        onFx(() -> { field.getTextField().setText("3.14"); return null; });
        assertEquals("3.14", field.getTextField().getText());

        onFx(() -> { field.getTextField().setText("3.141"); return null; });
        // Rejected — text unchanged
        assertEquals("3.14", field.getTextField().getText());
    }

    @Test
    void changingDecimalsReformatsText() {
        FxNumberField field = onFx(() -> FxNumberField.builder()
                .value(7.0)
                .build());
        assertEquals("7", field.getTextField().getText());

        onFx(() -> { field.setDecimals(3); return null; });
        assertEquals("7.000", field.getTextField().getText());
    }

    @Test
    void leadingIconLiteralSugarCreatesFxIcon() {
        FxNumberField field = onFx(() -> FxNumberField.builder()
                .leadingIcon("fth-hash")
                .build());

        assertNotNull(field.getLeadingIcon());
        assertTrue(field.getLeadingIcon() instanceof FxIcon);
        assertEquals("fth-hash", ((FxIcon) field.getLeadingIcon()).getIconLiteral());
    }
}
