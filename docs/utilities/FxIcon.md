# FxIcon

Styled icon glyph built on Ikonli's `FontIcon`. Renders glyphs from any Ikonli pack on the classpath (Feather is bundled by default). Adds a semantic color variant system tied to the active theme.

## Usage

```java
FxIcon save = FxIcon.builder()
    .literal("fth-save")
    .size(20)
    .variant(SemanticVariant.ACCENT)
    .build();

// Or plain constructor
FxIcon save2 = new FxIcon("fth-save");
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `literal(String)` | `String` | `""` | Ikonli icon literal (e.g. `fth-save`). |
| `size(int)` | `int` | `16` | Icon size in px. |
| `variant(SemanticVariant)` | `SemanticVariant` | `DEFAULT` | Color variant. |
