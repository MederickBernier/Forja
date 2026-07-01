# FxDatePicker

Styled date input wrapping `javafx.scene.control.DatePicker`. Supports min/max clamp (calendar disables out-of-range cells) and a configurable display / parse format pattern.

## Usage

```java
FxDatePicker due = FxDatePicker.builder()
    .value(LocalDate.now())
    .min(LocalDate.now())
    .formatPattern("yyyy-MM-dd")
    .leadingIcon("fth-calendar")
    .helperText("Pick a due date.")
    .build();
```

## Builder methods

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
