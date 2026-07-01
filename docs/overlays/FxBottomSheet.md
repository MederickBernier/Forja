# FxBottomSheet

Bottom-anchored slide-up sheet with a grabber handle. Slides up from the scene bottom on `open`, slides back down on `close`.

## Usage

```java
FxBottomSheet sheet = FxBottomSheet.builder()
    .content(sheetBody)
    .height(280)
    .build();
sheet.open(scene);
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `content(Node)` | `Node` | none | Sheet content. |
| `height(double)` | `double` | `280` | Sheet height in px. |
