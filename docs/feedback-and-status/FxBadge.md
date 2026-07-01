# FxBadge

Small labeled pill for counts and status tags.

## Usage

```java
FxBadge unread = FxBadge.builder()
    .text("3")
    .variant(SemanticVariant.DANGER)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Badge text. |
| `variant(SemanticVariant)` | `SemanticVariant` | `DEFAULT` | Color variant. |
