# FxKbd

Rendered keyboard-key chip. Use inside prose or a `FxKeybindingHint` row to reference specific keys.

## Usage

```java
FxKbd cmd = FxKbd.builder().text("⌘").build();
FxKbd k   = FxKbd.builder().text("K").build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Key label. |
