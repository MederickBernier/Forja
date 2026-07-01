# FxContainer

Max-width centered container. Use for page-level content wrappers that shouldn't stretch full-width on wide screens.

## Usage

```java
FxContainer page = FxContainer.builder()
    .width(ContainerWidth.LG)
    .children(headerRow, mainSection)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `width(ContainerWidth)` | `ContainerWidth` | `MD` | Max width preset. |
| `children(Node...)` | `Node[]` | empty | Contained children. |
