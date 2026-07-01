# FxChip

Removable labeled chip. Use for tags, filter facets, or selected items in a picker.

## Usage

```java
FxChip tag = FxChip.builder()
    .text("javafx")
    .variant(SemanticVariant.ACCENT)
    .removable(true)
    .onClose(e -> removeTag("javafx"))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Chip label. |
| `variant(SemanticVariant)` | `SemanticVariant` | `DEFAULT` | Color variant. |
| `removable(boolean)` | `boolean` | `false` | Show the × close affordance. |
| `onClose(EventHandler<ActionEvent>)` | handler | none | Fired when × is clicked. |
