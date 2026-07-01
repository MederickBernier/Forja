# FxAspectRatio

Container that constrains its single child to a fixed width:height ratio. Takes the available width from its parent, computes `height = width / ratio`, and lays out the child.

## Usage

```java
FxAspectRatio banner = FxAspectRatio.builder()
    .ratio(16.0 / 9.0)
    .child(imageView)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `ratio(double)` | `double` | `1.0` | Width / height ratio. |
| `child(Node)` | `Node` | `null` | Single child. |
