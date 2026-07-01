# FxFileChooser

Button + label showing the chosen file (or `N files` when multiple). Clicking the button opens the native `javafx.stage.FileChooser`.

## Usage

```java
FxFileChooser fc = FxFileChooser.builder()
    .buttonText("Attach file")
    .title("Pick attachment")
    .extensionFilter("Images", "*.png", "*.jpg")
    .multiSelect(false)
    .onChoose(files -> attach(files))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `buttonText(String)` | `String` | `"Choose file…"` | Button label. |
| `title(String)` | `String` | `"Open"` | Chooser dialog title. |
| `multiSelect(boolean)` | `boolean` | `false` | Enable multi-file selection. |
| `extensionFilter(String, String...)` | description + globs | none | Add a single extension filter. |
| `onChoose(OnChoose)` | callback | none | Fires with the chosen `List<File>`. |
