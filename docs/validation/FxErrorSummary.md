# FxErrorSummary

Aggregate list of validation errors, framed as a danger-colored panel. Auto-hides when the errors list is empty.

## Usage

```java
FxErrorSummary summary = FxErrorSummary.builder()
    .title("Please fix the following:")
    .errors("Email is required", "Password too short")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `"There were errors"` | Header title. |
| `errors(List<String>)` / `errors(String...)` | items | empty | Error messages. |
