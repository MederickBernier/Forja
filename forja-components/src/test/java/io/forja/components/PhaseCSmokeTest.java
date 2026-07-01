package io.forja.components;

import io.forja.components.inputs.fxAutocomplete.FxAutocomplete;
import io.forja.components.inputs.fxMaskedInput.FxMaskedInput;
import io.forja.components.inputs.fxOTPInput.FxOTPInput;
import io.forja.components.inputs.fxTagInput.FxTagInput;
import io.forja.components.selection.fxColorPicker.FxColorPicker;
import io.forja.components.selection.fxMultiSelect.FxMultiSelect;
import io.forja.components.selection.fxRangeSlider.FxRangeSlider;
import io.forja.components.selection.fxRating.FxRating;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseCSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void autocomplete_filtersOnText() {
        FxAutocomplete<String> ac = onFx(() -> FxAutocomplete.<String>builder()
                .items("apple", "apricot", "banana")
                .build());
        onFx(() -> { ac.setText("ap"); return null; });
        assertEquals(2, ac.getFilteredItems().size());
    }

    @Test
    void autocomplete_manuallyChoose() {
        AtomicReference<String> got = new AtomicReference<>();
        FxAutocomplete<String> ac = onFx(() -> FxAutocomplete.<String>builder()
                .items("apple", "apricot")
                .onSelect(got::set)
                .build());
        onFx(() -> { ac.valueProperty().set("apricot"); return null; });
        assertEquals("apricot", ac.getValue());
    }

    @Test
    void tagInput_addAndRemove() {
        FxTagInput t = onFx(() -> FxTagInput.builder().tags("java", "javafx").build());
        assertEquals(2, t.getTags().size());
        onFx(() -> { t.getTags().add("kotlin"); return null; });
        assertEquals(3, t.getTags().size());
        onFx(() -> { t.getTags().remove("java"); return null; });
        assertEquals(2, t.getTags().size());
    }

    @Test
    void otpInput_buildsBoxesAndReadsCode() {
        FxOTPInput otp = onFx(() -> FxOTPInput.builder().length(4).digitsOnly(true).build());
        assertEquals(4, otp.getBoxes().size());
        onFx(() -> { otp.setCode("1234"); return null; });
        assertEquals("1234", otp.getCode());
    }

    @Test
    void otpInput_onCompleteFires() {
        AtomicReference<String> got = new AtomicReference<>();
        FxOTPInput otp = onFx(() -> FxOTPInput.builder().length(3).onComplete(got::set).build());
        onFx(() -> { otp.setCode("789"); return null; });
        assertEquals("789", got.get());
    }

    @Test
    void maskedInput_acceptsMatchingDigits() {
        FxMaskedInput m = onFx(() -> FxMaskedInput.builder().mask("###-###").build());
        onFx(() -> { m.setText("123-456"); return null; });
        assertEquals("123-456", m.getText());
    }

    @Test
    void maskedInput_rejectsBadChar() {
        FxMaskedInput m = onFx(() -> FxMaskedInput.builder().mask("###").build());
        onFx(() -> { m.setText("abc"); return null; });
        assertEquals("", m.getText());
    }

    @Test
    void multiSelect_updatesSummary() {
        FxMultiSelect ms = onFx(() -> FxMultiSelect.builder()
                .items("A", "B", "C")
                .selected("A", "B")
                .promptText("Pick")
                .build());
        assertEquals("2 selected", ms.getSummary().getText());
        onFx(() -> { ms.getSelected().remove("A"); return null; });
        assertEquals("B", ms.getSummary().getText());
        onFx(() -> { ms.getSelected().clear(); return null; });
        assertEquals("Pick", ms.getSummary().getText());
    }

    @Test
    void rangeSlider_clampsCrossing() {
        FxRangeSlider r = onFx(() -> FxRangeSlider.builder()
                .min(0).max(100).lowValue(30).highValue(70).build());
        onFx(() -> { r.setLowValue(90); return null; });
        assertEquals(70.0, r.getLowValue(), "clamped at highValue");
        onFx(() -> { r.setHighValue(10); return null; });
        assertEquals(70.0, r.getHighValue(), "clamped at lowValue");
    }

    @Test
    void rating_setValueClamps() {
        FxRating r = onFx(() -> FxRating.builder().max(5).value(3).build());
        assertEquals(3, r.getValue());
        onFx(() -> { r.setValue(10); return null; });
        assertEquals(5, r.getValue());
        onFx(() -> { r.setValue(-1); return null; });
        assertEquals(0, r.getValue());
    }

    @Test
    void rating_onChangeFires() {
        AtomicInteger got = new AtomicInteger();
        FxRating r = onFx(() -> FxRating.builder().max(5).onChange(got::set).build());
        onFx(() -> { r.setValue(4); return null; });
        assertEquals(4, got.get());
    }

    @Test
    void rating_readonlyBlocksMouseHandlers() {
        FxRating r = onFx(() -> FxRating.builder().max(3).readonly(true).build());
        assertTrue(r.isReadonly());
        assertFalse(r.getStars().isEmpty());
    }

    @Test
    void colorPicker_setsValue() {
        FxColorPicker cp = onFx(() -> FxColorPicker.builder().value(Color.RED).build());
        assertEquals(Color.RED, cp.getValue());
    }
}
