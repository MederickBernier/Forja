# FxScrollSpy

Non-visual controller that tracks which of several registered "section" nodes is currently in view inside a `ScrollPane`. Exposes the active section's key via `activeKeyProperty()`.

## Usage

```java
FxScrollSpy spy = FxScrollSpy.builder()
    .scrollPane(scrollPane)
    .section("intro",     intro)
    .section("install",   install)
    .section("api",       api)
    .build();
spy.activeKeyProperty().addListener((obs, o, key) -> sidebar.highlight(key));
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `scrollPane(ScrollPane)` | `ScrollPane` | none | Host scroll pane. |
| `section(String, Node)` | key + node | — | Register a section. |
