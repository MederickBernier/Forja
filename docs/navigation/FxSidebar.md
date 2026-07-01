# FxSidebar

Vertical container for navigation content — a styled `VBox` with a fixed default width.

## Usage

```java
FxSidebar side = FxSidebar.builder()
    .width(240)
    .children(sidebarNav, footer)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `width(double)` | `double` | `240` | Preferred width. |
| `spacing(double)` | `double` | `6` | Gap between children. |
| `children(Node...)` | `Node[]` | empty | Sidebar content. |
