package io.forja.demo.categories;

import io.forja.components.dateAndTime.fxCalendar.FxCalendar;
import io.forja.components.dateAndTime.fxDatePicker.FxDatePicker;
import io.forja.components.dateAndTime.fxDateRangePicker.FxDateRangePicker;
import io.forja.components.dateAndTime.fxDateTimePicker.FxDateTimePicker;
import io.forja.components.dateAndTime.fxMiniCalendar.FxMiniCalendar;
import io.forja.components.dateAndTime.fxTimePicker.FxTimePicker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.scene.Node;
import javafx.scene.Scene;

public class DateAndTimeDemo implements CategoryDemo {

    @Override public String key() { return "date-and-time"; }
    @Override public String title() { return "Date & Time"; }
    @Override public String icon() { return "fth-calendar"; }

    @Override
    public Node build(Scene scene) {
        LocalDate today = LocalDate.of(2026, 7, 1);
        return Demo.category(title(),
                Demo.block("FxCalendar", "Full month calendar with selectable days.",
                        FxCalendar.builder().value(today).build()),

                Demo.block("FxMiniCalendar", "Compact month grid for tight layouts.",
                        FxMiniCalendar.builder().value(today).build()),

                Demo.block("FxDatePicker", "Text field with a drop-down calendar and validation.",
                        FxDatePicker.builder().value(today).promptText("Pick a date").build(),
                        FxDatePicker.builder().value(today).showWeekNumbers(true)
                                .helperText("Week numbers shown").build(),
                        FxDatePicker.builder().promptText("Required")
                                .errorText("This field is required").build()),

                Demo.block("FxDateRangePicker", "Selects a start and end date together.",
                        FxDateRangePicker.builder().start(today).end(today.plusDays(7))
                                .helperText("Trip dates").build()),

                Demo.block("FxTimePicker", "Time entry with 12h/24h and optional seconds.",
                        FxTimePicker.builder().value(LocalTime.of(9, 30)).build(),
                        FxTimePicker.builder().value(LocalTime.of(14, 15, 0)).use24Hour(true)
                                .showSeconds(true).stepMinutes(5).build()),

                Demo.block("FxDateTimePicker", "Combined date and time selection.",
                        FxDateTimePicker.builder().value(LocalDateTime.of(today, LocalTime.of(10, 0)))
                                .use24Hour(true).helperText("Meeting start").build()));
    }
}
