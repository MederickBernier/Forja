# FxPagination

Prev/next arrows + numbered page buttons. When `totalPages > visiblePages`, only a window around the current page is shown with ellipsis markers.

## Usage

```java
FxPagination pg = FxPagination.builder()
    .totalPages(20)
    .currentPage(0)
    .visiblePages(7)
    .onPageChange(p -> viewModel.loadPage(p))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `totalPages(int)` | `int` | `1` | Total pages. |
| `currentPage(int)` | `int` | `0` | Zero-based current page. |
| `visiblePages(int)` | `int` | `7` | Window size. |
| `onPageChange(OnPageChange)` | callback | none | Fires when current page changes. |
