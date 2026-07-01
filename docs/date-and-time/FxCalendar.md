# FxCalendar

Month-grid calendar with prev/next navigation and single-day selection. Weekday header + 6 × 7 day cells. Pseudo-classes: `:selected`, `:today`, `:out-month`.

## Usage

```java
FxCalendar c = FxCalendar.builder()
    .value(LocalDate.now())
    .weekStartsOn(DayOfWeek.MONDAY)
    .onSelect(d -> logger.info("picked {}", d))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(LocalDate)` | `LocalDate` | `null` | Initially selected date. |
| `month(YearMonth)` | `YearMonth` | current | Initially displayed month. |
| `weekStartsOn(DayOfWeek)` | `DayOfWeek` | `MONDAY` | Week start day. |
| `todayRef(LocalDate)` | `LocalDate` | `now()` | Reference date treated as today. |
| `onSelect(OnSelect)` | callback | none | Fires when a day is picked. |
