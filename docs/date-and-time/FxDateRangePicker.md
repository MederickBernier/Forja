# FxDateRangePicker

Date-range input — two `FxDatePicker`s with an `end ≥ start` clamp. Changing the start past the end auto-advances the end; changing the end before the start auto-retreats the start.

## Usage

```java
FxDateRangePicker r = FxDateRangePicker.builder()
    .start(LocalDate.of(2026, 6, 1))
    .end(LocalDate.of(2026, 6, 30))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `start(LocalDate)` | `LocalDate` | `null` | Initial start date. |
| `end(LocalDate)` | `LocalDate` | `null` | Initial end date. |
| `min(LocalDate)` | `LocalDate` | `null` | Inclusive minimum for both pickers. |
| `max(LocalDate)` | `LocalDate` | `null` | Inclusive maximum for both pickers. |
| `formatPattern(String)` | `String` | `"yyyy-MM-dd"` | Display / parse pattern. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |
