# FxKeybindingHint

Row of `FxKbd` chips separated by a small `+` glyph — a compact way to display a keyboard shortcut like `⌘ + K`.

## Usage

```java
FxKeybindingHint hint = FxKeybindingHint.builder()
    .keys("⌘", "K")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `keys(List<String>)` / `keys(String...)` | items | empty | Key labels. |
