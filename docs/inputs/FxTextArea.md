# FxTextArea

Multi-line text input with validation-state variants and an optional auto-resize behavior. When `autoResize` is `true`, `prefRowCount` grows to fit the line count, clamped between `rows` and `maxRows`.

## Usage

```java
FxTextArea bio = FxTextArea.builder()
    .promptText("Tell us about yourself")
    .rows(4).maxRows(12)
    .autoResize(true)
    .helperText("Max 500 characters.")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial text. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line; non-empty flips variant to `ERROR`. |
| `autoResize(boolean)` | `boolean` | `false` | Grow/shrink `prefRowCount` with content. |
| `rows(int)` | `int` | `4` | Minimum row count. |
| `maxRows(int)` | `int` | `12` | Maximum row count. |
| `wrapText(boolean)` | `boolean` | `true` | Wrap long lines. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |
