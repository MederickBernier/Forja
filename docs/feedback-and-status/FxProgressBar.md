# FxProgressBar

Horizontal progress bar built on `javafx.scene.control.ProgressBar`. Supports both determinate progress and indeterminate mode.

## Usage

```java
FxProgressBar loading = FxProgressBar.builder()
    .progress(0.42)
    .variant(SemanticVariant.ACCENT)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `progress(double)` | `double` | `0` | Progress value in `[0, 1]`. |
| `indeterminate(boolean)` | `boolean` | `false` | Set indeterminate mode (`INDETERMINATE_PROGRESS`). |
| `variant(SemanticVariant)` | `SemanticVariant` | `ACCENT` | Color variant. |
