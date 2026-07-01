package io.forja.components.dateAndTime.fxCalendar;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.buttonsAndActions.fxIconButton.FxIconButton;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A month-grid calendar with navigation and single-day selection.
 *
 * <p>{@code FxCalendar} displays a 7-column grid of days for the current
 * {@link #getMonth}. Header row: previous-month button, {@code Month Year}
 * label, next-month button. The selected day is exposed as
 * {@link #valueProperty()}. {@link Builder#weekStartsOn} controls whether
 * the week starts Monday (default) or Sunday.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxCalendar c = FxCalendar.builder()
 *          .value(LocalDate.now())
 *          .weekStartsOn(DayOfWeek.MONDAY)
 *          .onSelect(d -> logger.info("picked {}", d))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxCalendar extends VBox {

    private static final PseudoClass SELECTED_PC = PseudoClass.getPseudoClass("selected");
    private static final PseudoClass TODAY_PC    = PseudoClass.getPseudoClass("today");
    private static final PseudoClass OUT_MONTH_PC = PseudoClass.getPseudoClass("out-month");

    private final FxIconButton prevBtn = FxIconButton.builder().icon("fth-chevron-left").build();
    private final FxIconButton nextBtn = FxIconButton.builder().icon("fth-chevron-right").build();
    private final FxLabel monthLabel = new FxLabel("", LabelVariant.SUBHEADING);
    private final HBox header = new HBox();
    private final GridPane grid = new GridPane();

    private final ObjectProperty<LocalDate> value = new SimpleObjectProperty<>(this, "value");
    private final ObjectProperty<YearMonth> month = new SimpleObjectProperty<>(this, "month", YearMonth.now());
    private DayOfWeek weekStartsOn = DayOfWeek.MONDAY;
    private LocalDate todayRef = LocalDate.now();
    private OnSelect onSelect;

    /**
     * Creates a calendar showing the current month with no selection.
     */
    public FxCalendar() {
        super();
        getStyleClass().add("forja-calendar");
        setSpacing(6);

        Region spacerL = new Region(); Region spacerR = new Region();
        HBox.setHgrow(spacerL, Priority.ALWAYS); HBox.setHgrow(spacerR, Priority.ALWAYS);
        header.getStyleClass().add("forja-calendar-header");
        header.setAlignment(Pos.CENTER);
        header.setSpacing(8);
        header.getChildren().addAll(prevBtn, spacerL, monthLabel, spacerR, nextBtn);
        prevBtn.setOnAction(e -> setMonth(getMonth().minusMonths(1)));
        nextBtn.setOnAction(e -> setMonth(getMonth().plusMonths(1)));

        grid.getStyleClass().add("forja-calendar-grid");
        grid.setHgap(2); grid.setVgap(2);

        getChildren().addAll(header, grid);

        month.addListener((obs, o, v) -> rebuild());
        value.addListener((obs, o, v) -> paintCells());
        rebuild();
    }

    private void rebuild() {
        grid.getChildren().clear();
        monthLabel.setText(getMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())
                + " " + getMonth().getYear());
        // Weekday header
        List<DayOfWeek> order = weekdayOrder();
        for (int i = 0; i < 7; i++) {
            FxLabel wd = FxLabel.builder()
                    .text(order.get(i).getDisplayName(TextStyle.SHORT, Locale.getDefault()))
                    .variant(LabelVariant.SMALL)
                    .muted(true)
                    .build();
            wd.getStyleClass().add("forja-calendar-weekday");
            GridPane.setHalignment(wd, HPos.CENTER);
            grid.add(wd, i, 0);
        }
        // Day cells — six weeks * seven days
        LocalDate first = getMonth().atDay(1);
        int firstIdx = weekdayIndex(first.getDayOfWeek());
        LocalDate cellStart = first.minusDays(firstIdx);
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                LocalDate d = cellStart.plusDays(row * 7L + col);
                Button cell = new Button(String.valueOf(d.getDayOfMonth()));
                cell.getStyleClass().add("forja-calendar-cell");
                cell.setUserData(d);
                cell.setMaxWidth(Double.MAX_VALUE);
                cell.setOnAction(e -> {
                    LocalDate picked = (LocalDate) cell.getUserData();
                    setValue(picked);
                    setMonth(YearMonth.from(picked));
                    if (onSelect != null) onSelect.accept(picked);
                });
                grid.add(cell, col, row + 1);
                GridPane.setHgrow(cell, Priority.ALWAYS);
            }
        }
        paintCells();
    }

    private void paintCells() {
        for (javafx.scene.Node n : grid.getChildren()) {
            if (!(n instanceof Button)) continue;
            Button cell = (Button) n;
            LocalDate d = (LocalDate) cell.getUserData();
            if (d == null) continue;
            boolean outMonth = !YearMonth.from(d).equals(getMonth());
            boolean isToday = d.equals(todayRef);
            boolean isSelected = d.equals(getValue());
            cell.pseudoClassStateChanged(OUT_MONTH_PC, outMonth);
            cell.pseudoClassStateChanged(TODAY_PC, isToday);
            cell.pseudoClassStateChanged(SELECTED_PC, isSelected);
        }
    }

    private int weekdayIndex(DayOfWeek d) {
        int start = weekStartsOn.getValue();
        int cur = d.getValue();
        int diff = cur - start;
        return diff < 0 ? diff + 7 : diff;
    }

    private List<DayOfWeek> weekdayOrder() {
        List<DayOfWeek> out = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int idx = (weekStartsOn.getValue() - 1 + i) % 7 + 1;
            out.add(DayOfWeek.of(idx));
        }
        return out;
    }

    /** Returns the value (selected date) property. */
    public ObjectProperty<LocalDate> valueProperty() { return value; }

    /** Returns the currently selected date. */
    public LocalDate getValue() { return value.get(); }

    /** Sets the currently selected date. */
    public void setValue(LocalDate v) { value.set(v); }

    /** Returns the displayed-month property. */
    public ObjectProperty<YearMonth> monthProperty() { return month; }

    /** Returns the currently displayed month. */
    public YearMonth getMonth() { return month.get(); }

    /** Sets the displayed month. */
    public void setMonth(YearMonth v) { month.set(v == null ? YearMonth.now() : v); }

    /** Sets the day the week starts on ({@link DayOfWeek#MONDAY} default). */
    public void setWeekStartsOn(DayOfWeek d) {
        this.weekStartsOn = d == null ? DayOfWeek.MONDAY : d;
        rebuild();
    }

    /** Sets the reference date treated as "today". */
    public void setTodayRef(LocalDate d) {
        this.todayRef = d == null ? LocalDate.now() : d;
        paintCells();
    }

    /** Sets the selection callback. */
    public void setOnSelect(OnSelect onSelect) { this.onSelect = onSelect; }

    /** Returns the day-cells grid. */
    public GridPane getGrid() { return grid; }

    /** Returns the header row. */
    public HBox getHeader() { return header; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxCalendar}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when a date is picked. */
    @FunctionalInterface
    public interface OnSelect { void accept(LocalDate date); }

    /**
     * Fluent builder for constructing an {@link FxCalendar}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>value — {@code null}</li>
     *   <li>month — current {@link YearMonth}</li>
     *   <li>weekStartsOn — {@link DayOfWeek#MONDAY}</li>
     *   <li>onSelect — none</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxCalendar, Builder> {

        private LocalDate value;
        private YearMonth month;
        private DayOfWeek weekStartsOn = DayOfWeek.MONDAY;
        private LocalDate todayRef;
        private OnSelect onSelect;

        public Builder value(LocalDate value) { this.value = value; return this; }
        public Builder month(YearMonth month) { this.month = month; return this; }
        public Builder weekStartsOn(DayOfWeek weekStartsOn) { this.weekStartsOn = weekStartsOn == null ? DayOfWeek.MONDAY : weekStartsOn; return this; }
        public Builder todayRef(LocalDate todayRef) { this.todayRef = todayRef; return this; }
        public Builder onSelect(OnSelect onSelect) { this.onSelect = onSelect; return this; }

        @Override
        public FxCalendar build() {
            FxCalendar c = new FxCalendar();
            c.setWeekStartsOn(weekStartsOn);
            if (todayRef != null) c.setTodayRef(todayRef);
            c.setMonth(month != null ? month : (value != null ? YearMonth.from(value) : YearMonth.now()));
            c.setValue(value);
            c.setOnSelect(onSelect);
            applyBase(c);
            return c;
        }
    }
}
