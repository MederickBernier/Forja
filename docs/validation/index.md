# Validation

Declarative form binding and a rule engine. `FxValidator` is pure logic — no scene dependencies — and can be reused outside of a form.

| Component | Summary |
|---|---|
| [FxValidator](#fxvalidator) | Sequential rule engine with built-in `required` / `email` / `range` / `matches` factories. |
| [FxFormField](#fxformfield) | Label + control + error slot with `:invalid` / `:required` pseudo-classes. |
| [FxErrorSummary](#fxerrorsummary) | Aggregate danger-colored list of validation errors. |
| [FxForm](#fxform) | Declarative form container that validates every field in one call. |

## FxValidator

Sequential rule engine for validating a single value against a chain of `Rule<T>`s. Runs rules in order, returns on the first non-`null` error message. Pure logic — no scene dependencies — safe to unit test.

Built-in rule factories: `required()`, `minLength()`, `maxLength()`, `matches()`, `email()`, `range()`.

### Usage

```java
FxValidator<String> emailValidator = FxValidator.<String>builder()
    .rule(FxValidator.required())
    .rule(FxValidator.email("Must be a valid email"))
    .build();
String err = emailValidator.validate("bad"); // "Must be a valid email"

// Or convenience
FxValidator<String> v = FxValidator.of(FxValidator.required(), FxValidator.email("Invalid"));
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `rule(Rule<T>)` | `Rule<T>` | — | Append a single rule. |
| `rules(Rule<T>...)` | `Rule<T>[]` | empty | Append multiple rules. |

### Static factories

| Factory | Returns | Rejects |
|---|---|---|
| `required()` | `Rule<String>` | null / blank |
| `required(String)` | `Rule<String>` | null / blank (custom message) |
| `minLength(int, String)` | `Rule<String>` | shorter than min |
| `maxLength(int, String)` | `Rule<String>` | longer than max |
| `matches(String, String)` | `Rule<String>` | doesn't match regex |
| `email(String)` | `Rule<String>` | doesn't look like email |
| `range(double, double, String)` | `Rule<Double>` | outside `[min, max]` |

## FxFormField

Form-field row — label + control + error slot. Set a validator to plug an `FxValidator` into the field; call `validate()` to compute the error against a value read from the supplier. Applies `:invalid` / `:required` pseudo-classes.

### Usage

```java
FxFormField<String> emailField = FxFormField.<String>builder()
    .label("Email")
    .control(emailInput)
    .validator(FxValidator.<String>of(FxValidator.required(), FxValidator.email("Invalid email")))
    .valueSupplier(emailInput::getText)
    .required(true)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `label(String)` | `String` | `""` | Field label. |
| `control(Node)` | `Node` | none | Wrapped control. |
| `validator(FxValidator<T>)` | validator | none | Rule engine. |
| `valueSupplier(Supplier<T>)` | supplier | none | Reads current value on `validate()`. |
| `required(boolean)` | `boolean` | `false` | Adds `*` marker + `:required` pseudo-class. |

## FxErrorSummary

Aggregate list of validation errors, framed as a danger-colored panel. Auto-hides when the errors list is empty.

### Usage

```java
FxErrorSummary summary = FxErrorSummary.builder()
    .title("Please fix the following:")
    .errors("Email is required", "Password too short")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `"There were errors"` | Header title. |
| `errors(List<String>)` / `errors(String...)` | items | empty | Error messages. |

## FxForm

Declarative form container — vertical stack of `FxFormField`s with an optional `FxErrorSummary` at the top. `validate()` runs every field's validator, populates each field's error, and fills the summary with all currently-invalid messages.

### Usage

```java
FxForm form = FxForm.builder()
    .field(emailField)
    .field(passwordField)
    .showSummary(true)
    .build();
if (form.validate()) submit();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `field(FxFormField<?>)` | field | — | Add a single field. |
| `fields(FxFormField<?>...)` | fields | empty | Add multiple fields. |
| `showSummary(boolean)` | `boolean` | `true` | Include the `FxErrorSummary` at top. |
| `summaryTitle(String)` | `String` | `"Please fix the following:"` | Summary panel title. |
