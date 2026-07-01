package io.forja.components;

import io.forja.components.dateAndTime.fxTimePicker.FxTimePicker;
import io.forja.components.inputs.InputVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalTime;

import static io.forja.testsupport.ForjaTestSupport.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxTimePickerTest {

    @Start
    void start(Stage stage) {
        // ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder().build());

        assertNull(field.getValue());
        assertNull(field.getMin());
        assertNull(field.getMax());
        assertTrue(field.isUse24Hour());
        assertFalse(field.isShowSeconds());
        assertEquals(1, field.getStepMinutes());
        assertEquals(InputVariant.DEFAULT, field.getVariant());
        assertNull(field.getLeadingIcon());
        assertEquals("", field.getHelperText());
        assertEquals("", field.getErrorText());
        assertFalse(field.isShowingError());
        assertFalse(field.getHelperLabel().isVisible());
        assertTrue(field.getStyleClass().contains("forja-time-picker"));
        // hour, sep, minute → 3 when 24h + no seconds + no icon
        assertEquals(3, field.getFieldRow().getChildren().size());
    }

    @Test
    void valueSyncsSpinners24Hour() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder()
                .value(LocalTime.of(14, 25))
                .build());

        assertEquals(14, field.getHourSpinner().getValue().intValue());
        assertEquals(25, field.getMinuteSpinner().getValue().intValue());
        assertEquals(LocalTime.of(14, 25), field.getValue());
    }

    @Test
    void valueSyncsSpinners12HourAmPm() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder()
                .use24Hour(false)
                .value(LocalTime.of(15, 45))
                .build());

        assertEquals(3, field.getHourSpinner().getValue().intValue());
        assertEquals(45, field.getMinuteSpinner().getValue().intValue());
        assertEquals("PM", field.getAmPmLabel().getText());

        onFx(() -> { field.setValue(LocalTime.of(0, 5)); return null; });
        assertEquals(12, field.getHourSpinner().getValue().intValue());
        assertEquals("AM", field.getAmPmLabel().getText());
    }

    @Test
    void spinnerEditsUpdateValue() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder()
                .value(LocalTime.of(0, 0))
                .build());

        onFx(() -> { field.getHourSpinner().getValueFactory().setValue(9); return null; });
        onFx(() -> { field.getMinuteSpinner().getValueFactory().setValue(30); return null; });

        assertEquals(LocalTime.of(9, 30), field.getValue());
    }

    @Test
    void showSecondsAddsSpinnerAndRespectsValue() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder()
                .showSeconds(true)
                .value(LocalTime.of(10, 20, 30))
                .build());

        // hour, :, minute, :, second → 5
        assertEquals(5, field.getFieldRow().getChildren().size());
        assertEquals(30, field.getSecondSpinner().getValue().intValue());
        assertEquals(LocalTime.of(10, 20, 30), field.getValue());
    }

    @Test
    void hidingSecondsForcesSecondsToZero() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder()
                .showSeconds(true)
                .value(LocalTime.of(10, 20, 45))
                .build());
        assertEquals(45, field.getSecondSpinner().getValue().intValue());

        onFx(() -> { field.setShowSeconds(false); return null; });
        assertEquals(0, field.getSecondSpinner().getValue().intValue());
        assertEquals(LocalTime.of(10, 20, 0), field.getValue());
    }

    @Test
    void amPmRowAppearsInTwelveHourMode() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder().use24Hour(false).build());
        // hour, :, minute, ampm → 4
        assertEquals(4, field.getFieldRow().getChildren().size());
        assertEquals(field.getAmPmLabel(), field.getFieldRow().getChildren().get(3));
    }

    @Test
    void setValueClampsToBounds() {
        LocalTime lo = LocalTime.of(9, 0);
        LocalTime hi = LocalTime.of(17, 0);
        FxTimePicker field = onFx(() -> FxTimePicker.builder().min(lo).max(hi).build());

        onFx(() -> { field.setValue(LocalTime.of(5, 0)); return null; });
        assertEquals(lo, field.getValue());

        onFx(() -> { field.setValue(LocalTime.of(22, 0)); return null; });
        assertEquals(hi, field.getValue());
    }

    @Test
    void leadingIconInserted() {
        FxIcon icon = onFx(() -> new FxIcon("fth-clock"));
        FxTimePicker field = onFx(() -> FxTimePicker.builder().leadingIcon(icon).build());
        // icon, hour, :, minute → 4
        assertEquals(4, field.getFieldRow().getChildren().size());
        assertEquals(icon, field.getFieldRow().getChildren().get(0));
    }

    @Test
    void variantPseudoClassUpdates() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder().variant(InputVariant.SUCCESS).build());

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
            FxTimePicker field = onFx(() -> FxTimePicker.builder().variant(variant).build());
            assertHasPseudoClass(field, variant.name().toLowerCase());
        }
    }

    @Test
    void helperTextTogglesVisibility() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder().build());
        assertFalse(field.getHelperLabel().isVisible());

        onFx(() -> { field.setHelperText("Pick time"); return null; });
        assertTrue(field.getHelperLabel().isVisible());
        assertEquals("Pick time", field.getHelperLabel().getText());

        onFx(() -> { field.setHelperText(""); return null; });
        assertFalse(field.getHelperLabel().isVisible());
    }

    @Test
    void errorTextAutoFlipsVariant() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder().helperText("Optional").build());
        assertEquals(InputVariant.DEFAULT, field.getVariant());

        onFx(() -> { field.setErrorText("Bad time"); return null; });
        assertEquals(InputVariant.ERROR, field.getVariant());
        assertTrue(field.isShowingError());
        assertEquals("Bad time", field.getHelperLabel().getText());
    }

    @Test
    void stepMinutesUpdatesSpinnerStep() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder().stepMinutes(15).build());
        javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory f =
                (javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory)
                        field.getMinuteSpinner().getValueFactory();
        assertEquals(15, f.getAmountToStepBy());

        onFx(() -> { field.setStepMinutes(5); return null; });
        assertEquals(5, f.getAmountToStepBy());
    }

    @Test
    void toggling24HourReconfiguresHourRange() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder().build());
        javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory f =
                (javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory)
                        field.getHourSpinner().getValueFactory();
        assertEquals(0, f.getMin());
        assertEquals(23, f.getMax());

        onFx(() -> { field.setUse24Hour(false); return null; });
        assertEquals(1, f.getMin());
        assertEquals(12, f.getMax());
    }

    @Test
    void leadingIconLiteralSugarCreatesFxIcon() {
        FxTimePicker field = onFx(() -> FxTimePicker.builder()
                .leadingIcon("fth-clock")
                .build());
        assertNotNull(field.getLeadingIcon());
        assertTrue(field.getLeadingIcon() instanceof FxIcon);
        assertEquals("fth-clock", ((FxIcon) field.getLeadingIcon()).getIconLiteral());
    }
}
