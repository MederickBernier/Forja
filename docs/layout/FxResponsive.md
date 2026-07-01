# FxResponsive

Responsive container — swaps its visible child based on the current width and a set of named breakpoints. Picks the entry with the greatest `minWidth ≤ getWidth()`.

## Usage

```java
FxResponsive layout = FxResponsive.builder()
    .at("base", 0, mobileLayout)
    .at("md", 768, tabletLayout)
    .at("lg", 1024, desktopLayout)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `at(String, double, Node)` | name + minWidth + node | — | Register a breakpoint. |
