# FxValidator

Sequential rule engine for validating a single value against a chain of `Rule<T>`s. Runs rules in order, returns on the first non-`null` error message. Pure logic — no scene dependencies — safe to unit test.

Built-in rule factories: `required()`, `minLength()`, `maxLength()`, `matches()`, `email()`, `range()`.

## Usage

```java
FxValidator<String> emailValidator = FxValidator.<String>builder()
    .rule(FxValidator.required())
    .rule(FxValidator.email("Must be a valid email"))
    .build();
String err = emailValidator.validate("bad"); // "Must be a valid email"

// Or convenience
FxValidator<String> v = FxValidator.of(FxValidator.required(), FxValidator.email("Invalid"));
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `rule(Rule<T>)` | `Rule<T>` | — | Append a single rule. |
| `rules(Rule<T>...)` | `Rule<T>[]` | empty | Append multiple rules. |

## Static factories

| Factory | Returns | Rejects |
|---|---|---|
| `required()` | `Rule<String>` | null / blank |
| `required(String)` | `Rule<String>` | null / blank (custom message) |
| `minLength(int, String)` | `Rule<String>` | shorter than min |
| `maxLength(int, String)` | `Rule<String>` | longer than max |
| `matches(String, String)` | `Rule<String>` | doesn't match regex |
| `email(String)` | `Rule<String>` | doesn't look like email |
| `range(double, double, String)` | `Rule<Double>` | outside `[min, max]` |
