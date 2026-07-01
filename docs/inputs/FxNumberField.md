# FxNumberField

Numeric input with min/max clamp, up/down steppers, and optional prefix/suffix affixes. Typing is filtered by a regex admitting sign, digits, and configurable decimal places.

## Usage

```java
FxNumberField price = FxNumberField.builder()
    .value(9.99)
    .min(0.0).max(1000.0)
    .step(0.25).decimals(2)
    .prefix("$")
    .showSteppers(true)
    .helperText("Retail price, taxes excluded.")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(Double)` / `value(double)` | number | `null` | Initial value (`null` = empty). |
| `min(Double)` / `min(double)` | number | `null` | Inclusive minimum. |
| `max(Double)` / `max(double)` | number | `null` | Inclusive maximum. |
| `step(double)` | `double` | `1.0` | Increment/decrement step. |
| `decimals(int)` | `int` | `0` | Fractional-digit count for display + input filter. |
| `prefix(String)` | `String` | `""` | Prefix affix (e.g. `"$"`). |
| `suffix(String)` | `String` | `""` | Suffix affix (e.g. `"kg"`). |
| `showSteppers(boolean)` | `boolean` | `false` | Show up/down stepper column. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `leadingIcon(Node)` / `leadingIcon(String)` | node / literal | none | Leading icon slot. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line; flips variant to `ERROR`. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |
