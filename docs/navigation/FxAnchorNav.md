# FxAnchorNav

Vertical list of in-page jump links backed by a `ScrollPane` + target-node map. Clicking a link scrolls the paired scroll pane so the target node's top aligns with the viewport top. Register the active key to highlight the current section.

## Usage

```java
FxAnchorNav toc = FxAnchorNav.builder()
    .scrollPane(scrollPane)
    .section("intro",   "Intro",   introNode)
    .section("install", "Install", installNode)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `scrollPane(ScrollPane)` | `ScrollPane` | none | Host scroll pane. |
| `section(String, String, Node)` | key + label + target | — | Register a jump target. |
| `active(String)` | `String` | `null` | Initial active key. |
