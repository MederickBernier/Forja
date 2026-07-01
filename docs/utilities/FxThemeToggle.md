# FxThemeToggle

Button that toggles between `FxTheme.LIGHT` and `FxTheme.DARK` on a target `Scene` via `Forja.applyTheme`. Icon reflects current theme — sun in dark mode, moon in light mode.

## Usage

```java
FxThemeToggle toggle = FxThemeToggle.builder()
    .scene(scene)
    .initial(FxTheme.LIGHT)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `scene(Scene)` | `Scene` | `null` | Target scene the toggle applies to. |
| `initial(FxTheme)` | `FxTheme` | `LIGHT` | Starting theme. |
