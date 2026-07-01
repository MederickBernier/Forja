# FxButton

Styled button built on `javafx.scene.control.Button`. Adds a visual variant system, a loading state, and a fluent builder. All native JavaFX properties, bindings, and events remain accessible.

## Usage

```java
FxButton save = FxButton.builder()
    .text("Save")
    .variant(ButtonVariant.PRIMARY)
    .onAction(e -> handleSave())
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Button label. |
| `variant(ButtonVariant)` | `ButtonVariant` | `PRIMARY` | Visual variant: `PRIMARY` / `SECONDARY` / `GHOST` / `DANGER`. |
| `loading(boolean)` | `boolean` | `false` | Faded + non-interactive loading state. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Click callback. |
