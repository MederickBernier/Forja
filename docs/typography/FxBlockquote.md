# FxBlockquote

Pulled-out prose block with a left accent bar. Wraps a `Label` inside a bordered container.

## Usage

```java
FxBlockquote quote = FxBlockquote.builder()
    .text("Shape what already works.")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Quoted text. |
| `attribution(String)` | `String` | `""` | Optional attribution line. |
