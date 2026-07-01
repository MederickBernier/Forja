# FxLabel

Themed label built on `javafx.scene.control.Label`. Selects a size + weight combo via `LabelVariant` (`DISPLAY` / `HEADING` / `SUBHEADING` / `BODY` / `SMALL` / `MONO`) and adds a muted-color pseudo-class.

## Usage

```java
FxLabel title = FxLabel.builder()
    .text("Projects")
    .variant(LabelVariant.HEADING)
    .build();

FxLabel helper = FxLabel.builder()
    .text("Optional")
    .variant(LabelVariant.SMALL)
    .muted(true)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Label text. |
| `variant(LabelVariant)` | `LabelVariant` | `BODY` | Typography variant. |
| `muted(boolean)` | `boolean` | `false` | Applies the muted color pseudo-class. |
| `wrapText(boolean)` | `boolean` | `false` | Enable text wrap. |
