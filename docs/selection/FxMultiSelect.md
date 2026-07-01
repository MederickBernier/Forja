# FxMultiSelect

Multi-select combo — a summary row + a dropdown popup of checkable items. Summary shows the prompt when empty, a single value when one is picked, or `N selected` for multiple.

## Usage

```java
FxMultiSelect ms = FxMultiSelect.builder()
    .items("bug", "docs", "enhancement")
    .selected("docs")
    .promptText("Labels")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<String>)` / `items(String...)` | items | empty | Options. |
| `selected(List<String>)` / `selected(String...)` | items | empty | Initially selected values. |
| `promptText(String)` | `String` | `""` | Summary shown when nothing selected. |
