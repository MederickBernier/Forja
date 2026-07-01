# FxErrorState

Error-flavored `FxEmptyState`. Ships an alert-triangle icon by default and picks up the `forja-error-state` style class so CSS tints headings + icons red.

## Usage

```java
FxErrorState err = FxErrorState.builder()
    .heading("Something went wrong")
    .description("Please retry in a few moments.")
    .action(retryButton)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `icon(String)` | `String` | `"fth-alert-triangle"` | Ikonli icon literal. |
| `heading(String)` | `String` | `""` | Heading text. |
| `description(String)` | `String` | `""` | Descriptive line. |
| `action(Node...)` | `Node[]` | empty | Action-slot children. |
