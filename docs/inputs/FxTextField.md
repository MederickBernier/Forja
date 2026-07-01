# FxTextField

Styled text input with optional leading/trailing icons, helper text, and validation-state variants. Setting `errorText` to a non-empty value automatically flips the variant to `ERROR`; clearing it does not auto-revert.

## Usage

```java
FxTextField email = FxTextField.builder()
    .promptText("name@example.com")
    .leadingIcon("fth-mail")
    .helperText("We'll never share your email.")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial text. |
| `promptText(String)` | `String` | `""` | Placeholder shown when empty. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | `DEFAULT` / `ERROR` / `SUCCESS`. |
| `leadingIcon(Node)` | `Node` | none | Leading slot node. |
| `leadingIcon(String)` | `String` | none | Sugar taking an Ikonli literal. |
| `trailingIcon(Node)` | `Node` | none | Trailing slot node. |
| `trailingIcon(String)` | `String` | none | Sugar taking an Ikonli literal. |
| `helperText(String)` | `String` | `""` | Helper line under the field. |
| `errorText(String)` | `String` | `""` | Error line; non-empty flips variant to `ERROR`. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |
