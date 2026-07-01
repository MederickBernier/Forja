# FxAvatar

Circular avatar with an image, an initials fallback, a size preset, and an optional status ring.

## Usage

```java
FxAvatar user = FxAvatar.builder()
    .imageUrl("https://…/me.jpg")
    .initials("MB")
    .size(AvatarSize.MD)
    .statusVariant(SemanticVariant.SUCCESS)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `imageUrl(String)` | `String` | none | Image URL. |
| `initials(String)` | `String` | `""` | Fallback initials when no image. |
| `size(AvatarSize)` | `AvatarSize` | `MD` | Size preset (`SM` / `MD` / `LG` / `XL`). |
| `statusVariant(SemanticVariant)` | `SemanticVariant` | none | Status ring color. |
