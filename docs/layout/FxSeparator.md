# FxSeparator

Divider line. Horizontal or vertical, thin or bold.

## Usage

```java
FxSeparator hr = FxSeparator.builder()
    .orientation(Orientation.HORIZONTAL)
    .variant(SeparatorVariant.DEFAULT)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `orientation(Orientation)` | `Orientation` | `HORIZONTAL` | Line direction. |
| `variant(SeparatorVariant)` | `SeparatorVariant` | `DEFAULT` | Style variant. |
