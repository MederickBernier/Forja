# FxConfirmDialog

Confirmation dialog — an `FxDialog` preset with title, message, cancel + confirm buttons, and a boolean callback.

## Usage

```java
// Static shortcut
FxConfirmDialog.ask(scene, "Delete project?",
    "This action cannot be undone.",
    confirmed -> { if (confirmed) doDelete(); });

// Or builder for custom labels + variant
FxConfirmDialog d = FxConfirmDialog.builder()
    .title("Delete project?")
    .message("This action cannot be undone.")
    .cancelText("Keep it")
    .confirmText("Delete")
    .confirmVariant(ButtonVariant.DANGER)
    .onResult(confirmed -> ...)
    .build();
d.show(scene);
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `"Confirm"` | Dialog title. |
| `message(String)` | `String` | `""` | Body message. |
| `cancelText(String)` | `String` | `"Cancel"` | Cancel button label. |
| `confirmText(String)` | `String` | `"Confirm"` | Confirm button label. |
| `confirmVariant(ButtonVariant)` | `ButtonVariant` | `PRIMARY` | Confirm button variant. |
| `onResult(Consumer<Boolean>)` | callback | none | Fires with `true` = confirm, `false` = cancel. |
