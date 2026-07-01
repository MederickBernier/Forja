# FxDateTimePicker

Composite `FxDatePicker` + `FxTimePicker` side-by-side, exposing a single `LocalDateTime` value. Setting either child updates the composite value; setting the composite value updates both children.

## Usage

```java
FxDateTimePicker dt = FxDateTimePicker.builder()
    .value(LocalDateTime.now())
    .use24Hour(true)
    .helperText("Kickoff time")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(LocalDateTime)` | `LocalDateTime` | `null` | Initial value. |
| `use24Hour(boolean)` | `boolean` | `true` | Time picker mode. |
| `showSeconds(boolean)` | `boolean` | `false` | Show seconds spinner. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |
