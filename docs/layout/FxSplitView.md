# FxSplitView

Styled resizable split view — a `SplitPane` wrapper. Divider positioning and item management remain fully accessible.

## Usage

```java
FxSplitView editor = FxSplitView.builder()
    .items(fileTree, codeArea)
    .dividerPositions(0.25)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `orientation(Orientation)` | `Orientation` | `HORIZONTAL` | Split direction. |
| `items(Node...)` | `Node[]` | empty | Split children. |
| `dividerPositions(double...)` | `double[]` | auto | Initial divider positions in `[0, 1]`. |
