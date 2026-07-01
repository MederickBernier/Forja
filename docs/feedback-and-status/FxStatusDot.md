# FxStatusDot

Colored dot for at-a-glance status (online, offline, busy, etc.).

## Usage

```java
FxStatusDot online = FxStatusDot.builder()
    .variant(SemanticVariant.SUCCESS)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `variant(SemanticVariant)` | `SemanticVariant` | `DEFAULT` | Color variant. |
| `size(double)` | `double` | `8` | Dot diameter in px. |
