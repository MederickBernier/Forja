# FxDropdownMenu

Text-first dropdown menu built on `javafx.scene.control.MenuButton`. Use `FxMenuButton` for the icon-only kebab variant.

## Usage

```java
FxDropdownMenu m = FxDropdownMenu.builder()
    .text("Actions")
    .items(rename, duplicate, delete)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Button label. |
| `items(MenuItem...)` | `MenuItem[]` | empty | Menu items. |
