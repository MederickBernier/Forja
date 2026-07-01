# FxFormDialog

Form-entry dialog — wraps an `FxDialog` with a body area, cancel button, and a save button whose enabled state is optionally driven by a validator `Supplier<Boolean>`.

## Usage

```java
FxFormDialog dlg = FxFormDialog.builder()
    .title("Rename")
    .body(nameField)
    .saveText("Save")
    .canSave(() -> !nameField.getText().isEmpty())
    .onSubmit(() -> project.rename(nameField.getText()))
    .build();
dlg.show(scene);
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `"Form"` | Dialog title. |
| `body(Node)` | `Node` | empty region | Form body. |
| `cancelText(String)` | `String` | `"Cancel"` | Cancel button label. |
| `saveText(String)` | `String` | `"Save"` | Save button label. |
| `saveVariant(ButtonVariant)` | `ButtonVariant` | `PRIMARY` | Save button variant. |
| `canSave(Supplier<Boolean>)` | supplier | none | Gates save-button enabled state. |
| `onSubmit(OnSubmit)` | callback | none | Fires on save when `canSave` passes. |
