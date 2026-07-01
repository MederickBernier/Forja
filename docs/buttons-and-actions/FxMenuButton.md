# FxMenuButton

Menu-button preset for icon-only action clusters. Distinct from `FxDropdownMenu` — that one is text-first. This one is the classic kebab (three-dot) icon that reveals a menu.

## Usage

```java
FxMenuButton kebab = FxMenuButton.builder()
    .icon("fth-more-vertical")
    .items(renameItem, deleteItem)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `icon(String)` | `String` | `"fth-more-vertical"` | Ikonli icon literal. |
| `text(String)` | `String` | `""` | Optional label alongside icon. |
| `variant(ButtonVariant)` | `ButtonVariant` | `GHOST` | Visual variant. |
| `items(MenuItem...)` | `MenuItem[]` | empty | Menu items. |
