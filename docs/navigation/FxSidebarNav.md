# FxSidebarNav

Vertical list of keyed nav items with icon + label and an active state. The currently-active item's row gets the `:active` pseudo-class.

## Usage

```java
FxSidebarNav nav = FxSidebarNav.builder()
    .item("home",     "Home",     "fth-home")
    .item("projects", "Projects", "fth-folder")
    .item("settings", "Settings", "fth-settings")
    .active("projects")
    .onSelect(key -> router.go(key))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `item(String, String, String)` | key + label + iconLiteral | — | Add a nav item. |
| `active(String)` | `String` | `null` | Initial active key. |
| `onSelect(OnSelect)` | callback | none | Fires when active key changes. |
