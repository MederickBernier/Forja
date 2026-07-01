# FxTagInput

Chip-based tag entry — a wrapping row of `FxChip` tags plus an inline text editor. Press `ENTER` to commit, `BACKSPACE` on empty to remove the last tag, click × on any chip to remove it.

## Usage

```java
FxTagInput tags = FxTagInput.builder()
    .tags("java", "javafx")
    .promptText("Add tag…")
    .onChange(list -> viewModel.setTags(list))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `tags(List<String>)` / `tags(String...)` | tags | empty | Initial tag list. |
| `promptText(String)` | `String` | `""` | Editor placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |
| `onChange(OnChange)` | callback | none | Fires when tag list changes. |
