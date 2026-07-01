# FxJsonEditor

JSON-flavored `FxCodeEditor` — wraps a code editor preset with a regex-driven JSON tokenizer emitting CSS classes `json-key`, `json-string`, `json-number`, `json-boolean`, `json-null`, `json-punctuation`.

## Usage

```java
FxJsonEditor ed = FxJsonEditor.builder()
    .text("{\"name\":\"Forja\",\"version\":0.1}")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial JSON source. |
