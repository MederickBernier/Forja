# FxTooltip

Styled `Tooltip` with a fluent builder. Attach via `FxTooltip.install(node, text)` for any `Node`, or `install(control, tooltip)` for a `Control`.

## Usage

```java
FxTooltip help = FxTooltip.builder()
    .text("Deletes this project permanently")
    .showDelayMs(300)
    .build();
FxTooltip.install(deleteButton, help);

// Or shortcut on any Node
FxTooltip.install(myNode, "Info");
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Tooltip text. |
| `showDelayMs(long)` | `long` | `400` | Delay before showing. |
| `showDurationMs(long)` | `long` | `5000` | Max on-screen duration. |
| `hideDelayMs(long)` | `long` | `200` | Delay before hiding. |
