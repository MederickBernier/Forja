# FxSection

Titled section wrapper. Renders a section-heading label above a body slot.

## Usage

```java
FxSection settings = FxSection.builder()
    .title("Preferences")
    .children(themeToggle, densityToggle)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `""` | Section heading text. |
| `description(String)` | `String` | `""` | Optional descriptive line under title. |
| `children(Node...)` | `Node[]` | empty | Body content. |
