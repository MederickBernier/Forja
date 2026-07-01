# FxCheckBox

Styled checkbox built on `javafx.scene.control.CheckBox`. Optional indeterminate state.

## Usage

```java
FxCheckBox agree = FxCheckBox.builder()
    .text("I agree to the terms")
    .selected(false)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Label. |
| `selected(boolean)` | `boolean` | `false` | Initial selected state. |
| `indeterminate(boolean)` | `boolean` | `false` | Initial indeterminate state. |
| `allowIndeterminate(boolean)` | `boolean` | `false` | Allow the indeterminate state. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Click callback. |
