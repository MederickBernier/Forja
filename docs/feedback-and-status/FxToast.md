# FxToast

Transient bottom-right notification on the overlay layer. Auto-fades in, holds, fades out.

## Usage

```java
// Static shortcut
FxToast.show(scene, "Saved!", SemanticVariant.SUCCESS);

// Or builder for advanced control
FxToast t = FxToast.builder()
    .message("Uploading…")
    .variant(SemanticVariant.INFO)
    .durationMs(4000)
    .build();
t.postTo(scene);
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `message(String)` | `String` | `""` | Text content. |
| `variant(SemanticVariant)` | `SemanticVariant` | `INFO` | Color + auto-icon. |
| `durationMs(long)` | `long` | `3000` | On-screen duration in ms. |
| `position(Pos)` | `Pos` | `BOTTOM_RIGHT` | Corner position. |
