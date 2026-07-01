# FxMenuBar

Styled `MenuBar` wrapper.

## Usage

```java
FxMenuBar mb = FxMenuBar.builder()
    .menus(fileMenu, editMenu, viewMenu)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `menus(Menu...)` | `Menu[]` | empty | Menus. |
| `useSystemMenuBar(boolean)` | `boolean` | `false` | Use the native system menu bar on macOS. |
