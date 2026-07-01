# FxTimeline

Vertical event timeline — each entry is a colored dot + connector line + title/subtitle card.

## Usage

```java
FxTimeline t = FxTimeline.builder()
    .entry("Deploy", "Pushed to prod at 09:32", SemanticVariant.SUCCESS)
    .entry("Rollback", "Reverted at 09:45", SemanticVariant.DANGER)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `entry(String, String, SemanticVariant)` | title + subtitle + variant | — | Add an entry. |
