# FxAppBar

Top app bar with a leading slot (menu/back), a title, and a trailing action slot. Aliased as `FxNavbar`.

## Usage

```java
FxAppBar bar = FxAppBar.builder()
    .leading(menuButton)
    .title("Forja Demo")
    .actions(themeToggle, settingsButton)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `leading(Node...)` | `Node[]` | empty | Leading-slot children. |
| `title(String)` | `String` | `""` | Title text. |
| `actions(Node...)` | `Node[]` | empty | Trailing-actions children. |
