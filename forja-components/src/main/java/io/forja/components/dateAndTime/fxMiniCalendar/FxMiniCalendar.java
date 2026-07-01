package io.forja.components.dateAndTime.fxMiniCalendar;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.dateAndTime.fxCalendar.FxCalendar;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

/**
 * A compact calendar variant — an {@link FxCalendar} wrapped in a
 * {@link VBox} carrying the extra {@code forja-mini-calendar} style class
 * so CSS can shrink cell size, tighten padding, etc.
 *
 * <p>Delegates {@code value}, {@code month}, and select callback to the
 * inner calendar.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxMiniCalendar mini = FxMiniCalendar.builder()
 *          .value(LocalDate.now())
 *          .build();
 *     }
 * </pre>
 *
 * @see FxCalendar
 * @see Builder
 */
public class FxMiniCalendar extends VBox {

    private final FxCalendar inner = new FxCalendar();

    /**
     * Creates an empty {@code FxMiniCalendar} for the current month.
     */
    public FxMiniCalendar() {
        super();
        getStyleClass().addAll("forja-mini-calendar", "forja-calendar-compact");
        getChildren().add(inner);
    }

    /** Returns the inner {@link FxCalendar}. */
    public FxCalendar getInner() { return inner; }

    /** Returns the value property (delegates to the inner calendar). */
    public ObjectProperty<LocalDate> valueProperty() { return inner.valueProperty(); }

    /** Returns the currently selected date. */
    public LocalDate getValue() { return inner.getValue(); }

    /** Sets the selected date. */
    public void setValue(LocalDate v) { inner.setValue(v); }

    /** Returns the month property. */
    public ObjectProperty<YearMonth> monthProperty() { return inner.monthProperty(); }

    /** Returns the currently displayed month. */
    public YearMonth getMonth() { return inner.getMonth(); }

    /** Sets the displayed month. */
    public void setMonth(YearMonth v) { inner.setMonth(v); }

    /** Sets the day the week starts on. */
    public void setWeekStartsOn(DayOfWeek d) { inner.setWeekStartsOn(d); }

    /** Sets the select callback. */
    public void setOnSelect(FxCalendar.OnSelect onSelect) { inner.setOnSelect(onSelect); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxMiniCalendar}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxMiniCalendar}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>value — {@code null}</li>
     *   <li>month — current {@link YearMonth}</li>
     *   <li>weekStartsOn — {@link DayOfWeek#MONDAY}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxMiniCalendar, Builder> {

        private LocalDate value;
        private YearMonth month;
        private DayOfWeek weekStartsOn = DayOfWeek.MONDAY;
        private FxCalendar.OnSelect onSelect;

        public Builder value(LocalDate value) { this.value = value; return this; }
        public Builder month(YearMonth month) { this.month = month; return this; }
        public Builder weekStartsOn(DayOfWeek weekStartsOn) { this.weekStartsOn = weekStartsOn == null ? DayOfWeek.MONDAY : weekStartsOn; return this; }
        public Builder onSelect(FxCalendar.OnSelect onSelect) { this.onSelect = onSelect; return this; }

        @Override
        public FxMiniCalendar build() {
            FxMiniCalendar mc = new FxMiniCalendar();
            mc.setWeekStartsOn(weekStartsOn);
            mc.setMonth(month != null ? month : (value != null ? YearMonth.from(value) : YearMonth.now()));
            mc.setValue(value);
            mc.setOnSelect(onSelect);
            applyBase(mc);
            return mc;
        }
    }
}
