# FxMarkdownEditor

Split source-editor + rendered-preview markdown editor. Preview renders a compact subset: `#`/`##`/`###` headings, blank-line paragraphs, `-` bullets, and inline `**bold**` / `*italic*` markers (strip-only).

## Usage

```java
FxMarkdownEditor md = FxMarkdownEditor.builder()
    .text("# Hello\n\nSome **bold** text.")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial markdown source. |
| `dividerPosition(double)` | `double` | `0.5` | Split divider position in `[0, 1]`. |
