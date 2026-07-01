# FxSwitch

Pill-style on/off toggle switch. Clicking the track flips the state; the knob slides via CSS transitions on the `:selected` pseudo-class. Keyboard: SPACE toggles when focused.

## Usage

```java
FxSwitch dark = FxSwitch.builder()
    .text("Dark mode")
    .selected(true)
    .onChange(v -> theme.setDark(v))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Optional label. |
| `selected(boolean)` | `boolean` | `false` | Initial state. |
| `onChange(OnChange)` | callback | none | Fires when selected changes. |
