# FxComboBox

Styled dropdown combo box built on `javafx.scene.control.ComboBox<T>`.

## Usage

```java
FxComboBox<String> country = FxComboBox.<String>builder()
    .items("Canada", "USA", "Mexico")
    .value("Canada")
    .promptText("Select country")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` / `items(T...)` | items | empty | Options. |
| `value(T)` | `T` | `null` | Initial value. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `editable(boolean)` | `boolean` | `false` | Editable text field. |
| `visibleRowCount(int)` | `int` | `10` | Dropdown row cap. |
