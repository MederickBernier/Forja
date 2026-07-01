# FxAccordion

Vertical stack of `FxCollapse` sections. When `singleOpen` is `true`, expanding one section collapses the others.

## Usage

```java
FxAccordion faq = FxAccordion.builder()
    .sections(shipping, returns, contact)
    .singleOpen(true)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `sections(FxCollapse...)` | `FxCollapse[]` | empty | Section list. |
| `singleOpen(boolean)` | `boolean` | `true` | Enforce one-open-at-a-time. |
