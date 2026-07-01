# FxLightbox

Full-scrim media viewer for one or more image URLs. Navigation arrows step through the images; ESC closes; LEFT/RIGHT navigate.

## Usage

```java
FxLightbox lb = FxLightbox.builder()
    .images("file:/a.png", "file:/b.png", "file:/c.png")
    .index(0)
    .build();
lb.open(scene);
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `images(List<String>)` / `images(String...)` | items | empty | Image URLs. |
| `index(int)` | `int` | `0` | Initial image index. |
