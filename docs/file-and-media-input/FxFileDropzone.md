# FxFileDropzone

Drag-and-drop target for files — a dashed-border region with icon + prompt. Toggles a `:dragging` pseudo-class while a valid drop is hovering.

## Usage

```java
FxFileDropzone dz = FxFileDropzone.builder()
    .promptText("Drop images here")
    .icon("fth-upload-cloud")
    .onDrop(files -> attach(files))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `promptText(String)` | `String` | `"Drop files here"` | Prompt text. |
| `icon(String)` | `String` | `"fth-upload-cloud"` | Ikonli icon literal. |
| `onDrop(OnDrop)` | callback | none | Fires with the dropped `List<File>`. |
