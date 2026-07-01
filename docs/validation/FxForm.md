# FxForm

Declarative form container — vertical stack of `FxFormField`s with an optional `FxErrorSummary` at the top. `validate()` runs every field's validator, populates each field's error, and fills the summary with all currently-invalid messages.

## Usage

```java
FxForm form = FxForm.builder()
    .field(emailField)
    .field(passwordField)
    .showSummary(true)
    .build();
if (form.validate()) submit();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `field(FxFormField<?>)` | field | — | Add a single field. |
| `fields(FxFormField<?>...)` | fields | empty | Add multiple fields. |
| `showSummary(boolean)` | `boolean` | `true` | Include the `FxErrorSummary` at top. |
| `summaryTitle(String)` | `String` | `"Please fix the following:"` | Summary panel title. |
