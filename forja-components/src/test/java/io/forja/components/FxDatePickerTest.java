package io.forja.components;

import io.forja.components.dateAndTime.fxDatePicker.FxDatePicker;
import io.forja.components.inputs.InputVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;

import static io.forja.testsupport.ForjaTestSupport.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxDatePickerTest {

    @Start
    void start(Stage stage) {
        // ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxDatePicker field = onFx(() -> FxDatePicker.builder().build());

        assertNull(field.getValue());
        assertNull(field.getMin());
        assertNull(field.getMax());
        assertEquals("yyyy-MM-dd", field.getFormatPattern());
        assertEquals(InputVariant.DEFAULT, field.getVariant());
        assertNull(field.getLeadingIcon());
        assertEquals("", field.getHelperText());
        assertEquals("", field.getErrorText());
        assertFalse(field.isShowingError());
        assertFalse(field.getHelperLabel().isVisible());
        assertTrue(field.getStyleClass().contains("forja-date-picker"));
        assertNotNull(field.getDatePicker());
        assertEquals(1, field.getFieldRow().getChildren().size());
    }

    @Test
    void valueSyncsToInnerPicker() {
        LocalDate d = LocalDate.of(2026, 6, 30);
        FxDatePicker field = onFx(() -> FxDatePicker.builder().value(d).build());

        assertEquals(d, field.getValue());
        assertEquals(d, field.getDatePicker().getValue());

        LocalDate d2 = LocalDate.of(2027, 1, 15);
        onFx(() -> { field.setValue(d2); return null; });
        assertEquals(d2, field.getDatePicker().getValue());
    }

    @Test
    void innerPickerValueSyncsBackToField() {
        FxDatePicker field = onFx(() -> FxDatePicker.builder().build());
        LocalDate d = LocalDate.of(2026, 3, 3);

        onFx(() -> { field.getDatePicker().setValue(d); return null; });
        assertEquals(d, field.getValue());
    }

    @Test
    void setValueClampsToBounds() {
        LocalDate lo = LocalDate.of(2026, 1, 1);
        LocalDate hi = LocalDate.of(2026, 12, 31);
        FxDatePicker field = onFx(() -> FxDatePicker.builder().min(lo).max(hi).build());

        onFx(() -> { field.setValue(LocalDate.of(2020, 5, 5)); return null; });
        assertEquals(lo, field.getValue());

        onFx(() -> { field.setValue(LocalDate.of(2030, 5, 5)); return null; });
        assertEquals(hi, field.getValue());
    }

    @Test
    void leadingIconInserted() {
        FxIcon icon = onFx(() -> new FxIcon("fth-calendar"));
        FxDatePicker field = onFx(() -> FxDatePicker.builder().leadingIcon(icon).build());

        assertEquals(2, field.getFieldRow().getChildren().size());
        assertEquals(icon, field.getFieldRow().getChildren().get(0));
    }

    @Test
    void mutatingLeadingIconRebuildsRow() {
        FxDatePicker field = onFx(() -> FxDatePicker.builder().build());
        assertEquals(1, field.getFieldRow().getChildren().size());

        FxIcon icon = onFx(() -> new FxIcon("fth-calendar"));
        onFx(() -> { field.setLeadingIcon(icon); return null; });
        assertEquals(2, field.getFieldRow().getChildren().size());

        onFx(() -> { field.setLeadingIcon(null); return null; });
        assertEquals(1, field.getFieldRow().getChildren().size());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxDatePicker field = onFx(() -> FxDatePicker.builder().variant(InputVariant.SUCCESS).build());

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
            FxDatePicker field = onFx(() -> FxDatePicker.builder().variant(variant).build());
            assertHasPseudoClass(field, variant.name().toLowerCase());
        }
    }

    @Test
    void helperTextTogglesHelperLabelVisibility() {
        FxDatePicker field = onFx(() -> FxDatePicker.builder().build());
        assertFalse(field.getHelperLabel().isVisible());

        onFx(() -> { field.setHelperText("Pick one"); return null; });
        assertTrue(field.getHelperLabel().isVisible());
        assertEquals("Pick one", field.getHelperLabel().getText());

        onFx(() -> { field.setHelperText(""); return null; });
        assertFalse(field.getHelperLabel().isVisible());
    }

    @Test
    void errorTextAutoFlipsVariantAndShowsError() {
        FxDatePicker field = onFx(() -> FxDatePicker.builder().helperText("Optional").build());
        assertEquals(InputVariant.DEFAULT, field.getVariant());

        onFx(() -> { field.setErrorText("Bad date"); return null; });
        assertEquals(InputVariant.ERROR, field.getVariant());
        assertTrue(field.isShowingError());
        assertEquals("Bad date", field.getHelperLabel().getText());
    }

    @Test
    void errorTakesPrecedenceOverHelperText() {
        FxDatePicker field = onFx(() -> FxDatePicker.builder()
                .helperText("Helper")
                .errorText("Boom")
                .build());
        assertTrue(field.isShowingError());
        assertEquals("Boom", field.getHelperLabel().getText());
    }

    @Test
    void formatPatternAffectsConverterToString() {
        FxDatePicker field = onFx(() -> FxDatePicker.builder()
                .formatPattern("dd/MM/yyyy")
                .value(LocalDate.of(2026, 6, 30))
                .build());

        String s = onFx(() -> field.getDatePicker().getConverter().toString(field.getValue()));
        assertEquals("30/06/2026", s);
    }

    @Test
    void changingFormatPatternRebuildsConverter() {
        FxDatePicker field = onFx(() -> FxDatePicker.builder()
                .value(LocalDate.of(2026, 6, 30))
                .build());
        assertEquals("2026-06-30", onFx(() -> field.getDatePicker().getConverter().toString(field.getValue())));

        onFx(() -> { field.setFormatPattern("MM-dd-yyyy"); return null; });
        assertEquals("06-30-2026", onFx(() -> field.getDatePicker().getConverter().toString(field.getValue())));
    }

    @Test
    void leadingIconLiteralSugarCreatesFxIcon() {
        FxDatePicker field = onFx(() -> FxDatePicker.builder()
                .leadingIcon("fth-calendar")
                .build());

        assertNotNull(field.getLeadingIcon());
        assertTrue(field.getLeadingIcon() instanceof FxIcon);
        assertEquals("fth-calendar", ((FxIcon) field.getLeadingIcon()).getIconLiteral());
    }
}
