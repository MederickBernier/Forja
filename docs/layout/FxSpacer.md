# FxSpacer

Absorbs available space between siblings inside an `HBox` / `VBox`. Ships as a `Region` with `Priority.ALWAYS` grow already set.

## Usage

```java
HBox row = new HBox(leftNode, FxSpacer.builder().build(), rightNode);
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `size(SpacingSize)` | `SpacingSize` | `NONE` | Optional minimum size along the axis. |
