# FxVerticalTabs

Vertical (LEFT-side) tabbed pane. Same as `FxTabs` with `side` defaulted to `Side.LEFT` and `rotateGraphic` on so tab titles read horizontally.

## Usage

```java
FxVerticalTabs settings = FxVerticalTabs.builder()
    .tabs(new Tab("Profile", profile), new Tab("Billing", billing))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `tabs(Tab...)` | `Tab[]` | empty | Tabs. |
| `side(Side)` | `Side` | `LEFT` | Tab-header side. |
