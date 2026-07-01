# FxText

Body text node with variant control. Use it when you need paragraphs of text with typography controls that don't fit `FxLabel`'s single-line default.

## Usage

```java
FxText body = FxText.builder()
    .text("Forja layers a design system on top of JavaFX...")
    .variant(TextVariant.BODY)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Text content. |
| `variant(TextVariant)` | `TextVariant` | `BODY` | Size + weight variant. |
| `wrapText(boolean)` | `boolean` | `true` | Wrap long text. |
