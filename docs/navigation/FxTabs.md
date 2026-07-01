# FxTabs

Styled tabbed pane built on `javafx.scene.control.TabPane`. Tab construction, selection, and drag/close behaviors remain fully accessible.

## Usage

```java
FxTabs tabs = FxTabs.builder()
    .tabs(new Tab("Overview", overview), new Tab("Docs", docs))
    .side(Side.TOP)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `tabs(Tab...)` | `Tab[]` | empty | Tabs. |
| `side(Side)` | `Side` | `TOP` | Tab-header side. |
| `closingPolicy(TabClosingPolicy)` | policy | `UNAVAILABLE` | Tab close policy. |
