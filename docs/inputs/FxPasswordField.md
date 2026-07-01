# FxPasswordField

Styled password input with an optional reveal-toggle that swaps between the masked `PasswordField` and a plain `TextField` view sharing the same text.

## Usage

```java
FxPasswordField password = FxPasswordField.builder()
    .promptText("••••••••")
    .leadingIcon("fth-lock")
    .revealable(true)
    .helperText("8+ characters with a number and symbol.")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial value. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `leadingIcon(Node)` / `leadingIcon(String)` | node / literal | none | Leading icon slot. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line; flips variant to `ERROR`. |
| `revealable(boolean)` | `boolean` | `false` | Show the eye toggle. |
| `revealed(boolean)` | `boolean` | `false` | Initially revealed (only honored when `revealable`). |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |
