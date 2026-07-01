# FxBanner

Full-width top-of-page notice with an optional CTA button and a dismiss (×) icon.

## Usage

```java
FxBanner announce = FxBanner.builder()
    .variant(SemanticVariant.ACCENT)
    .message("Version 1.2 is available.")
    .actionText("Update")
    .onAction(e -> updater.start())
    .dismissible(true)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `variant(SemanticVariant)` | `SemanticVariant` | `INFO` | Color + auto-icon. |
| `message(String)` | `String` | `""` | Message text. |
| `actionText(String)` | `String` | `""` | CTA button label; empty hides button. |
| `dismissible(boolean)` | `boolean` | `false` | Show the × close icon. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | CTA click callback. |
