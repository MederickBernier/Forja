# FxSparkline

Compact inline polyline chart drawn on a `Canvas`. Sized to fit its parent. Ideal for tables/lists where a numeric trend needs to appear inline.

## Usage

```java
FxSparkline s = FxSparkline.builder()
    .data(1, 3, 2, 6, 5, 8, 7)
    .stroke(Color.web("#4F46E5"))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `data(Number...)` | `Number[]` | empty | Data points. |
| `stroke(Color)` | `Color` | indigo | Line color. |
| `strokeWidth(double)` | `double` | `1.5` | Line width. |
| `width(double)` | `double` | `120` | Preferred width. |
| `height(double)` | `double` | `32` | Preferred height. |
