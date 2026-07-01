# FxFormField

Form-field row — label + control + error slot. Set a validator to plug an `FxValidator` into the field; call `validate()` to compute the error against a value read from the supplier. Applies `:invalid` / `:required` pseudo-classes.

## Usage

```java
FxFormField<String> emailField = FxFormField.<String>builder()
    .label("Email")
    .control(emailInput)
    .validator(FxValidator.<String>of(FxValidator.required(), FxValidator.email("Invalid email")))
    .valueSupplier(emailInput::getText)
    .required(true)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `label(String)` | `String` | `""` | Field label. |
| `control(Node)` | `Node` | none | Wrapped control. |
| `validator(FxValidator<T>)` | validator | none | Rule engine. |
| `valueSupplier(Supplier<T>)` | supplier | none | Reads current value on `validate()`. |
| `required(boolean)` | `boolean` | `false` | Adds `*` marker + `:required` pseudo-class. |
