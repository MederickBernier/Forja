package io.forja.components;

import io.forja.components.dateAndTime.fxCalendar.FxCalendar;
import io.forja.components.dateAndTime.fxDateRangePicker.FxDateRangePicker;
import io.forja.components.dateAndTime.fxDateTimePicker.FxDateTimePicker;
import io.forja.components.dateAndTime.fxMiniCalendar.FxMiniCalendar;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseDSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void dateTimePicker_composesValue() {
        FxDateTimePicker dt = onFx(() -> FxDateTimePicker.builder()
                .value(LocalDateTime.of(2026, 6, 30, 9, 30))
                .build());
        assertEquals(LocalDate.of(2026, 6, 30), dt.getDatePicker().getValue());
        assertEquals(LocalTime.of(9, 30), dt.getTimePicker().getValue());
    }

    @Test
    void dateTimePicker_recomputesOnChildChange() {
        FxDateTimePicker dt = onFx(() -> FxDateTimePicker.builder()
                .value(LocalDateTime.of(2026, 1, 1, 0, 0))
                .build());
        onFx(() -> { dt.getTimePicker().setValue(LocalTime.of(14, 15)); return null; });
        assertEquals(LocalDateTime.of(2026, 1, 1, 14, 15), dt.getValue());
    }

    @Test
    void dateTimePicker_nullWhenChildEmpty() {
        FxDateTimePicker dt = onFx(() -> FxDateTimePicker.builder()
                .value(LocalDateTime.of(2026, 1, 1, 0, 0))
                .build());
        onFx(() -> { dt.getDatePicker().setValue(null); return null; });
        assertNull(dt.getValue());
    }

    @Test
    void dateRangePicker_clampsEndForward() {
        FxDateRangePicker r = onFx(() -> FxDateRangePicker.builder()
                .start(LocalDate.of(2026, 6, 1))
                .end(LocalDate.of(2026, 6, 10))
                .build());
        onFx(() -> { r.setStart(LocalDate.of(2026, 6, 15)); return null; });
        assertEquals(LocalDate.of(2026, 6, 15), r.getEnd(), "end auto-advanced");
    }

    @Test
    void dateRangePicker_clampsStartBackward() {
        FxDateRangePicker r = onFx(() -> FxDateRangePicker.builder()
                .start(LocalDate.of(2026, 6, 10))
                .end(LocalDate.of(2026, 6, 20))
                .build());
        onFx(() -> { r.setEnd(LocalDate.of(2026, 6, 5)); return null; });
        assertEquals(LocalDate.of(2026, 6, 5), r.getStart(), "start auto-retreated");
    }

    @Test
    void calendar_setsMonthFromValue() {
        FxCalendar c = onFx(() -> FxCalendar.builder()
                .value(LocalDate.of(2026, 3, 15))
                .build());
        assertEquals(YearMonth.of(2026, 3), c.getMonth());
        assertEquals(LocalDate.of(2026, 3, 15), c.getValue());
    }

    @Test
    void calendar_navigatesMonths() {
        FxCalendar c = onFx(() -> FxCalendar.builder()
                .month(YearMonth.of(2026, 6))
                .build());
        onFx(() -> { c.setMonth(c.getMonth().plusMonths(1)); return null; });
        assertEquals(YearMonth.of(2026, 7), c.getMonth());
    }

    @Test
    void calendar_gridHas7WeekdaysPlus42Cells() {
        FxCalendar c = onFx(() -> FxCalendar.builder().month(YearMonth.of(2026, 6)).build());
        assertEquals(49, c.getGrid().getChildren().size(), "7 weekday headers + 6*7 day cells");
    }

    @Test
    void miniCalendar_hasCompactStyleClass() {
        FxMiniCalendar mc = onFx(() -> FxMiniCalendar.builder()
                .value(LocalDate.of(2026, 1, 1))
                .build());
        assertTrue(mc.getStyleClass().contains("forja-mini-calendar"));
        assertTrue(mc.getStyleClass().contains("forja-calendar-compact"));
        assertEquals(LocalDate.of(2026, 1, 1), mc.getValue());
    }
}
