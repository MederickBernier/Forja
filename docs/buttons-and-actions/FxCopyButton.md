# FxCopyButton

Copies a configured string to the system clipboard on click, briefly flashes a check icon + confirm label, then reverts.

## Usage

```java
FxCopyButton copyToken = FxCopyButton.builder()
    .text("Copy")
    .value("api-key-abc123")
    .confirmText("Copied!")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `"Copy"` | Idle label. |
| `value(String)` | `String` | `""` | Clipboard payload. |
| `confirmText(String)` | `String` | `"Copied"` | Label shown during confirm flash. |
| `confirmDurationMs(long)` | `long` | `1500` | Confirm flash duration in ms. |
| `variant(ButtonVariant)` | `ButtonVariant` | `SECONDARY` | Visual variant. |
