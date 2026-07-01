# FxMaskedInput

Text input with a fixed input mask. Placeholder characters: `#` = digit, `A` = ASCII letter, `*` = any character. Any other mask character is a literal separator.

## Usage

```java
FxMaskedInput phone = FxMaskedInput.builder()
    .mask("###-###-####")
    .promptText("555-123-4567")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `mask(String)` | `String` | `""` | Mask pattern (`#`, `A`, `*`, literals). |
| `text(String)` | `String` | `""` | Initial value. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |
