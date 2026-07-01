# FxAutocomplete

Text input with a suggestion popup — `FxTextField` plus a `ListView` in a `Popup` anchored below. Filters candidates against the field's text via a stringifier (default: `Object::toString`).

## Usage

```java
FxAutocomplete<String> ac = FxAutocomplete.<String>builder()
    .items("apple", "apricot", "banana")
    .promptText("Fruit")
    .onSelect(fruit -> logger.info("chose {}", fruit))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` / `items(T...)` | items | empty | Candidate items. |
| `text(String)` | `String` | `""` | Initial text. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |
| `stringifier(Function<T, String>)` | fn | `Object::toString` | Renders + filters items. |
| `onSelect(OnSelect<T>)` | callback | none | Fires when a suggestion is chosen. |
