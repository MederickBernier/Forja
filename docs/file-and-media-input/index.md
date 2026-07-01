# File Input

File pickers, drop targets, image tools.

| Component | Summary |
|---|---|
| [FxFileChooser](#fxfilechooser) | Button + label that opens the native `FileChooser` dialog. |
| [FxFileDropzone](#fxfiledropzone) | Dashed drop-target region with drag hover state. |
| [FxImagePicker](#fximagepicker) | Preview + choose-file for image selection. |
| [FxImageCropper](#fximagecropper) | Canvas overlay with drag-to-select crop rectangle. |

## FxFileChooser

Button + label showing the chosen file (or `N files` when multiple). Clicking the button opens the native `javafx.stage.FileChooser`.

### Usage

```java
FxFileChooser fc = FxFileChooser.builder()
    .buttonText("Attach file")
    .title("Pick attachment")
    .extensionFilter("Images", "*.png", "*.jpg")
    .multiSelect(false)
    .onChoose(files -> attach(files))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `buttonText(String)` | `String` | `"Choose file…"` | Button label. |
| `title(String)` | `String` | `"Open"` | Chooser dialog title. |
| `multiSelect(boolean)` | `boolean` | `false` | Enable multi-file selection. |
| `extensionFilter(String, String...)` | description + globs | none | Add a single extension filter. |
| `onChoose(OnChoose)` | callback | none | Fires with the chosen `List<File>`. |

## FxFileDropzone

Drag-and-drop target for files — a dashed-border region with icon + prompt. Toggles a `:dragging` pseudo-class while a valid drop is hovering.

### Usage

```java
FxFileDropzone dz = FxFileDropzone.builder()
    .promptText("Drop images here")
    .icon("fth-upload-cloud")
    .onDrop(files -> attach(files))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `promptText(String)` | `String` | `"Drop files here"` | Prompt text. |
| `icon(String)` | `String` | `"fth-upload-cloud"` | Ikonli icon literal. |
| `onDrop(OnDrop)` | callback | none | Fires with the dropped `List<File>`. |

## FxImagePicker

Image picker — preview `FxImage` + choose/clear buttons. Native chooser filtered to png/jpg/gif/bmp.

### Usage

```java
FxImagePicker picker = FxImagePicker.builder()
    .previewWidth(200)
    .previewHeight(200)
    .buttonText("Upload avatar")
    .onChoose(file -> viewModel.setAvatar(file))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `previewWidth(double)` | `double` | `160` | Preview width. |
| `previewHeight(double)` | `double` | `160` | Preview height. |
| `buttonText(String)` | `String` | `"Choose image…"` | Choose button label. |
| `clearButtonText(String)` | `String` | `"Clear"` | Clear button label. |
| `onChoose(OnChoose)` | callback | none | Fires with the chosen `File`. |

## FxImageCropper

Canvas overlay with drag-to-select crop rectangle over a source `Image`. Semi-transparent scrim outside the selection. Optional fixed aspect-ratio clamp. `exportCrop()` returns a `WritableImage` of the cropped region at source resolution.

### Usage

```java
FxImageCropper cropper = FxImageCropper.builder()
    .image(new Image("file:/photo.jpg"))
    .aspectRatio(1.0)
    .build();
WritableImage result = cropper.exportCrop();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `image(Image)` | `Image` | none | Source image. |
| `aspectRatio(double)` | `double` | `0` (free) | Fixed width:height ratio for the selection. |
| `width(double)` | `double` | `320` | Preferred width. |
| `height(double)` | `double` | `240` | Preferred height. |
