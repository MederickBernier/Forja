# FxDialog

Modal dialog rendered on the `OverlayHost` overlay layer. Contains a translucent scrim and a centered panel with title, body, and footer slots.

## Usage

```java
FxDialog d = FxDialog.builder()
    .title("Rename project")
    .body(nameField)
    .footer(cancelButton, saveButton)
    .build();
d.show(scene);
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `""` | Header title. |
| `body(Node...)` | `Node[]` | empty | Body content. |
| `footer(Node...)` | `Node[]` | empty | Footer children (usually buttons). |
| `dismissOnScrimClick(boolean)` | `boolean` | `true` | Close on scrim click. |
| `dismissOnEscape(boolean)` | `boolean` | `true` | Close on ESC. |
| `showCloseIcon(boolean)` | `boolean` | `true` | Show the header × icon. |
| `okOnly(String)` | `String` | — | Sugar: footer becomes one PRIMARY button that closes the dialog. |
