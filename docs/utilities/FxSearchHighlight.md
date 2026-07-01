# FxSearchHighlight

`TextFlow` that renders a source string with occurrences of a search query wrapped in bold `Text` nodes so matches stand out. Substring match (not regex).

## Usage

```java
FxSearchHighlight hl = FxSearchHighlight.builder()
    .text("Deploy the pipeline to production")
    .query("pipe")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Source text. |
| `query(String)` | `String` | `""` | Query to highlight. |
| `caseSensitive(boolean)` | `boolean` | `false` | Case-sensitive matching. |
