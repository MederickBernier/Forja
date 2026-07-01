# FxContextMenu

Styled `ContextMenu` wrapper. Install on a node via `setOnContextMenuRequested`.

## Usage

```java
FxContextMenu cm = FxContextMenu.builder()
    .items(copy, paste, delete)
    .build();
target.setOnContextMenuRequested(e -> cm.show(target, e.getScreenX(), e.getScreenY()));
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(MenuItem...)` | `MenuItem[]` | empty | Menu items. |
