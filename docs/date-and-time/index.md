# Date & Time

Date, time, range, and calendar pickers.

| Component | Summary |
|---|---|
| [FxDatePicker](#fxdatepicker) | Styled date input with min/max clamp and configurable format pattern. |
| [FxTimePicker](#fxtimepicker) | Hour + minute (optional seconds) spinner picker with 12/24-hour mode. |
| [FxDateTimePicker](#fxdatetimepicker) | Composite `FxDatePicker` + `FxTimePicker` producing a `LocalDateTime`. |
| [FxDateRangePicker](#fxdaterangepicker) | Two-date range with automatic end ≥ start clamp. |
| [FxCalendar](#fxcalendar) | Month-grid calendar with prev/next navigation and single-day selection. |
| [FxMiniCalendar](#fxminicalendar) | Compact calendar variant for sidebars and popovers. |

## FxDatePicker

Styled date input wrapping `javafx.scene.control.DatePicker`. Supports min/max clamp (calendar disables out-of-range cells) and a configurable display / parse format pattern.

### Usage

```java
FxDatePicker due = FxDatePicker.builder()
    .value(LocalDate.now())
    .min(LocalDate.now())
    .formatPattern("yyyy-MM-dd")
    .leadingIcon("fth-calendar")
    .helperText("Pick a due date.")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(LocalDate)` | `LocalDate` | `null` | Initial date. |
| `min(LocalDate)` | `LocalDate` | `null` | Inclusive minimum. |
| `max(LocalDate)` | `LocalDate` | `null` | Inclusive maximum. |
| `formatPattern(String)` | `String` | `"yyyy-MM-dd"` | `DateTimeFormatter` pattern. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `leadingIcon(Node)` / `leadingIcon(String)` | node / literal | none | Leading icon slot. |
| `promptText(String)` | `String` | `""` | Editor placeholder. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |
| `showWeekNumbers(boolean)` | `boolean` | `false` | Show week numbers in calendar. |
| `editable(boolean)` | `boolean` | `true` | Editable text field. |

## FxTimePicker

Time input with hour and minute spinners, optional seconds, and an optional 12-hour AM/PM toggle. Hour range is `0..23` for 24-hour mode or `1..12` for 12-hour mode.

### Usage

```java
FxTimePicker t = FxTimePicker.builder()
    .value(LocalTime.of(9, 30))
    .use24Hour(false)
    .stepMinutes(5)
    .leadingIcon("fth-clock")
    .helperText("Meeting time.")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(LocalTime)` | `LocalTime` | `null` | Initial time. |
| `min(LocalTime)` | `LocalTime` | `null` | Inclusive minimum. |
| `max(LocalTime)` | `LocalTime` | `null` | Inclusive maximum. |
| `use24Hour(boolean)` | `boolean` | `true` | `true` = 0..23; `false` = 1..12 + AM/PM. |
| `showSeconds(boolean)` | `boolean` | `false` | Show seconds spinner. |
| `stepMinutes(int)` | `int` | `1` | Minute spinner step. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `leadingIcon(Node)` / `leadingIcon(String)` | node / literal | none | Leading icon slot. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |
| `editable(boolean)` | `boolean` | `true` | Editable spinners. |

## FxDateTimePicker

Composite `FxDatePicker` + `FxTimePicker` side-by-side, exposing a single `LocalDateTime` value. Setting either child updates the composite value; setting the composite value updates both children.

### Usage

```java
FxDateTimePicker dt = FxDateTimePicker.builder()
    .value(LocalDateTime.now())
    .use24Hour(true)
    .helperText("Kickoff time")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(LocalDateTime)` | `LocalDateTime` | `null` | Initial value. |
| `use24Hour(boolean)` | `boolean` | `true` | Time picker mode. |
| `showSeconds(boolean)` | `boolean` | `false` | Show seconds spinner. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |

## FxDateRangePicker

Date-range input — two `FxDatePicker`s with an `end ≥ start` clamp. Changing the start past the end auto-advances the end; changing the end before the start auto-retreats the start.

### Usage

```java
FxDateRangePicker r = FxDateRangePicker.builder()
    .start(LocalDate.of(2026, 6, 1))
    .end(LocalDate.of(2026, 6, 30))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `start(LocalDate)` | `LocalDate` | `null` | Initial start date. |
| `end(LocalDate)` | `LocalDate` | `null` | Initial end date. |
| `min(LocalDate)` | `LocalDate` | `null` | Inclusive minimum for both pickers. |
| `max(LocalDate)` | `LocalDate` | `null` | Inclusive maximum for both pickers. |
| `formatPattern(String)` | `String` | `"yyyy-MM-dd"` | Display / parse pattern. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |

## FxCalendar

Month-grid calendar with prev/next navigation and single-day selection. Weekday header + 6 × 7 day cells. Pseudo-classes: `:selected`, `:today`, `:out-month`.

### Usage

```java
FxCalendar c = FxCalendar.builder()
    .value(LocalDate.now())
    .weekStartsOn(DayOfWeek.MONDAY)
    .onSelect(d -> logger.info("picked {}", d))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(LocalDate)` | `LocalDate` | `null` | Initially selected date. |
| `month(YearMonth)` | `YearMonth` | current | Initially displayed month. |
| `weekStartsOn(DayOfWeek)` | `DayOfWeek` | `MONDAY` | Week start day. |
| `todayRef(LocalDate)` | `LocalDate` | `now()` | Reference date treated as today. |
| `onSelect(OnSelect)` | callback | none | Fires when a day is picked. |

## FxMiniCalendar

Compact calendar variant — wraps `FxCalendar` with the `forja-mini-calendar` and `forja-calendar-compact` style classes so CSS can shrink cell size and tighten padding.

### Usage

```java
FxMiniCalendar mini = FxMiniCalendar.builder()
    .value(LocalDate.now())
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(LocalDate)` | `LocalDate` | `null` | Initially selected date. |
| `month(YearMonth)` | `YearMonth` | current | Initially displayed month. |
| `weekStartsOn(DayOfWeek)` | `DayOfWeek` | `MONDAY` | Week start day. |
| `onSelect(OnSelect)` | callback | none | Fires when a day is picked. |
