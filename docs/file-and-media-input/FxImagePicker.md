# FxImagePicker

Image picker — preview `FxImage` + choose/clear buttons. Native chooser filtered to png/jpg/gif/bmp.

## Usage

```java
FxImagePicker picker = FxImagePicker.builder()
    .previewWidth(200)
    .previewHeight(200)
    .buttonText("Upload avatar")
    .onChoose(file -> viewModel.setAvatar(file))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `previewWidth(double)` | `double` | `160` | Preview width. |
| `previewHeight(double)` | `double` | `160` | Preview height. |
| `buttonText(String)` | `String` | `"Choose image…"` | Choose button label. |
| `clearButtonText(String)` | `String` | `"Clear"` | Clear button label. |
| `onChoose(OnChoose)` | callback | none | Fires with the chosen `File`. |
