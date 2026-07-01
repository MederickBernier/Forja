# FxSearchField

Search-flavored text field — `FxTextField` preset with a leading search icon and an optional clear (×) button that appears while the field has text.

## Usage

```java
FxSearchField search = FxSearchField.builder()
    .promptText("Search users")
    .clearable(true)
    .onSearch(q -> viewModel.query(q))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial text. |
| `promptText(String)` | `String` | `"Search"` | Placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |
| `clearable(boolean)` | `boolean` | `false` | Show × when text is present. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |
| `onSearch(OnSearch)` | callback | none | Fires on ENTER with the current text. |
