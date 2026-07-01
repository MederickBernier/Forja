# FxNotificationCenter

Stack of dismissible `FxAlert` notifications anchored to a scene corner via `OverlayHost`. Older notifications trim off the bottom once `maxVisible` fills.

## Usage

```java
FxNotificationCenter nc = FxNotificationCenter.builder()
    .position(Pos.TOP_RIGHT)
    .maxVisible(5)
    .build();
nc.install(scene);
nc.post("Saved", SemanticVariant.SUCCESS);

// Static shortcut
FxNotificationCenter.show(scene, "New message", SemanticVariant.INFO);
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `position(Pos)` | `Pos` | `TOP_RIGHT` | Corner anchor. |
| `maxVisible(int)` | `int` | `5` | Cap on visible notifications. |
