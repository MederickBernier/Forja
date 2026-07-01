# FxToggleButton

Stateful button that stays pressed. Extends `javafx.scene.control.ToggleButton` so it participates in a shared `ToggleGroup` if you set one.

## Usage

```java
FxToggleButton bold = FxToggleButton.builder()
    .text("B")
    .selected(false)
    .variant(ButtonVariant.GHOST)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Label. |
| `selected(boolean)` | `boolean` | `false` | Initial selected state. |
| `variant(ButtonVariant)` | `ButtonVariant` | `SECONDARY` | Visual variant. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Click callback. |
| `toggleGroupUserData(Object)` | `Object` | none | Optional user-data attached to the toggle for group tracking. |
