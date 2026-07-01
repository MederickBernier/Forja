# FxProgressCircle

Circular progress indicator built on `javafx.scene.control.ProgressIndicator`.

## Usage

```java
FxProgressCircle spinner = FxProgressCircle.builder()
    .indeterminate(true)
    .size(24)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `progress(double)` | `double` | `0` | Progress value in `[0, 1]`. |
| `indeterminate(boolean)` | `boolean` | `false` | Set indeterminate mode. |
| `size(double)` | `double` | inherit | Sets pref/min/max width + height. |
| `variant(SemanticVariant)` | `SemanticVariant` | `ACCENT` | Color variant. |
