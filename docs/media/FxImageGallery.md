# FxImageGallery

Grid of thumbnails that opens a full-screen `FxLightbox` on click. Backed by `FxImage` thumbnails that share the same URL list with the lightbox.

## Usage

```java
FxImageGallery gallery = FxImageGallery.builder()
    .images("file:/a.jpg", "file:/b.jpg", "file:/c.jpg")
    .thumbnailSize(120)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `images(List<String>)` / `images(String...)` | items | empty | Image URLs. |
| `thumbnailSize(double)` | `double` | `120` | Thumbnail edge length in px. |
