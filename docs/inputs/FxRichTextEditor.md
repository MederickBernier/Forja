# FxRichTextEditor

Rich-text editor built on RichTextFX `InlineCssTextArea`. Ships a bold / italic / underline toolbar that applies inline CSS to the current selection.

## Usage

```java
FxRichTextEditor ed = FxRichTextEditor.builder()
    .text("Type here…")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial plain text. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |
