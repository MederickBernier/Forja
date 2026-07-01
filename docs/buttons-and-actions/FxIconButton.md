# FxIconButton

Icon-first button. Renders an Ikonli glyph as the primary content with an optional text label positioned before or after the icon.

## Usage

```java
FxIconButton save = FxIconButton.builder()
    .icon("fth-save")
    .text("Save")
    .iconPosition(IconPosition.LEADING)
    .variant(ButtonVariant.PRIMARY)
    .onAction(e -> handleSave())
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Optional button label alongside the icon. |
| `icon(String)` | `String` | none | Ikonli icon literal (e.g. `fth-save`). |
| `iconPosition(IconPosition)` | `IconPosition` | `LEADING` | `LEADING` / `TRAILING` / `ONLY`. |
| `iconSize(int)` | `int` | `16` | Icon size in px. |
| `variant(ButtonVariant)` | `ButtonVariant` | `SECONDARY` | Visual variant. |
| `loading(boolean)` | `boolean` | `false` | Loading state. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Click callback. |
