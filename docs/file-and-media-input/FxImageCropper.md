# FxImageCropper

Canvas overlay with drag-to-select crop rectangle over a source `Image`. Semi-transparent scrim outside the selection. Optional fixed aspect-ratio clamp. `exportCrop()` returns a `WritableImage` of the cropped region at source resolution.

## Usage

```java
FxImageCropper cropper = FxImageCropper.builder()
    .image(new Image("file:/photo.jpg"))
    .aspectRatio(1.0)
    .build();
WritableImage result = cropper.exportCrop();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `image(Image)` | `Image` | none | Source image. |
| `aspectRatio(double)` | `double` | `0` (free) | Fixed width:height ratio for the selection. |
| `width(double)` | `double` | `320` | Preferred width. |
| `height(double)` | `double` | `240` | Preferred height. |
