# FxRating

Star-rating input. Click a star to set the rating; hover previews highlight up to that position. Set `readonly` to display without interaction.

## Usage

```java
FxRating stars = FxRating.builder()
    .max(5)
    .value(3)
    .onChange(v -> logger.info("rated {}", v))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `max(int)` | `int` | `5` | Star count. |
| `value(int)` | `int` | `0` | Initial rating, clamped to `[0, max]`. |
| `readonly(boolean)` | `boolean` | `false` | Disable interaction. |
| `onChange(OnChange)` | callback | none | Fires when value changes. |
