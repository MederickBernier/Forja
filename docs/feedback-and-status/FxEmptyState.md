# FxEmptyState

Centered "nothing here" panel — icon + heading + description + optional action node.

## Usage

```java
FxEmptyState empty = FxEmptyState.builder()
    .icon("fth-inbox")
    .heading("No projects yet")
    .description("Create your first project to get started.")
    .action(newProjectButton)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `icon(String)` | `String` | `"fth-inbox"` | Ikonli icon literal. |
| `heading(String)` | `String` | `""` | Heading text. |
| `description(String)` | `String` | `""` | Descriptive line. |
| `action(Node...)` | `Node[]` | empty | Action-slot children. |
