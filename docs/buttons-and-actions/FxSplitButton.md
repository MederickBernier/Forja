# FxSplitButton

Split button — primary click surface plus a chevron that opens a menu of `MenuItem`s. Extends `javafx.scene.control.SplitMenuButton`.

## Usage

```java
FxSplitButton save = FxSplitButton.builder()
    .text("Save")
    .variant(ButtonVariant.PRIMARY)
    .onAction(e -> handleSave())
    .items(saveAsItem, exportItem)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Label. |
| `variant(ButtonVariant)` | `ButtonVariant` | `PRIMARY` | Visual variant. |
| `items(MenuItem...)` | `MenuItem[]` | empty | Dropdown menu items. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Primary click callback. |
| `icon(String)` | `String` | none | Optional Ikonli icon literal. |
