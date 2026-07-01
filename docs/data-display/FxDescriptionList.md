# FxDescriptionList

Term / description grid for key-value data. Two-column horizontal layout by default; vertical stacks term on top of description.

## Usage

```java
FxDescriptionList dl = FxDescriptionList.builder()
    .item("Name", "Mederick Bernier")
    .item("Email", "m@example.com")
    .item("Role", "Owner")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `orientation(Orientation)` | `Orientation` | `HORIZONTAL` | Column layout. |
| `item(String, String)` | term + description | — | Add a single row. |
| `items(Map<String, String>)` | map | empty | Replace items in one call. |
