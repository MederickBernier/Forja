# FxImage

Styled `ImageView` wrapper that shows a spinner while the source loads and swaps to a fallback icon on error.

## Usage

```java
FxImage avatar = FxImage.builder()
    .url("https://…/avatar.png")
    .fitWidth(64).fitHeight(64)
    .fallbackIcon("fth-user")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `url(String)` | `String` | `""` | Image URL. |
| `fitWidth(double)` | `double` | `0` | Fit width. |
| `fitHeight(double)` | `double` | `0` | Fit height. |
| `preserveRatio(boolean)` | `boolean` | `true` | Preserve aspect ratio. |
| `fallbackIcon(String)` | `String` | `"fth-image"` | Icon shown on load failure. |
