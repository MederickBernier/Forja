# FxCodeEditor

Code editor built on RichTextFX `CodeArea`. Optional line-number gutter, plus a highlighter callback (RichTextFX `StyleSpans<Collection<String>>` pattern) that re-tokenizes on text change.

## Usage

```java
FxCodeEditor ed = FxCodeEditor.builder()
    .text("int x = 42;")
    .showLineNumbers(true)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial source. |
| `showLineNumbers(boolean)` | `boolean` | `true` | Line-number gutter. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |
| `highlighter(Function<String, StyleSpans<Collection<String>>>)` | fn | none | Syntax highlighter callback. |
