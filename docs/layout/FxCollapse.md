# FxCollapse

Single-section collapsible — a clickable header row plus an expandable body slot. The chevron rotates via the `:expanded` pseudo-class.

## Usage

```java
FxCollapse details = FxCollapse.builder()
    .title("Details")
    .content(detailsBody)
    .expanded(false)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `""` | Header text. |
| `content(Node)` | `Node` | none | Expandable body node. |
| `expanded(boolean)` | `boolean` | `false` | Initial expanded state. |
