# FxMiniCalendar

Compact calendar variant — wraps `FxCalendar` with the `forja-mini-calendar` and `forja-calendar-compact` style classes so CSS can shrink cell size and tighten padding.

## Usage

```java
FxMiniCalendar mini = FxMiniCalendar.builder()
    .value(LocalDate.now())
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(LocalDate)` | `LocalDate` | `null` | Initially selected date. |
| `month(YearMonth)` | `YearMonth` | current | Initially displayed month. |
| `weekStartsOn(DayOfWeek)` | `DayOfWeek` | `MONDAY` | Week start day. |
| `onSelect(OnSelect)` | callback | none | Fires when a day is picked. |
