# FxTimePicker

Time input with hour and minute spinners, optional seconds, and an optional 12-hour AM/PM toggle. Hour range is `0..23` for 24-hour mode or `1..12` for 12-hour mode.

## Usage

```java
FxTimePicker t = FxTimePicker.builder()
    .value(LocalTime.of(9, 30))
    .use24Hour(false)
    .stepMinutes(5)
    .leadingIcon("fth-clock")
    .helperText("Meeting time.")
    .build();
```

## Builder methods

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
