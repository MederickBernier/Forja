# FxColorPicker

Styled color picker built on `javafx.scene.control.ColorPicker`. Everything native remains accessible.

## Usage

```java
FxColorPicker cp = FxColorPicker.builder()
    .value(Color.web("#4F46E5"))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(Color)` | `Color` | `Color.BLACK` | Initial color. |
