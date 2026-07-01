# FxBreadcrumbs

Trail of clickable segments separated by `/`. Each non-last segment is an `FxLink`; the last is a muted label representing the current location.

## Usage

```java
FxBreadcrumbs bc = FxBreadcrumbs.builder()
    .segments("Projects", "Forja", "Docs")
    .onSelect(idx -> router.upTo(idx))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `segments(List<String>)` / `segments(String...)` | items | empty | Trail segments. |
| `onSelect(OnSelect)` | callback | none | Fires when a segment is clicked. |
