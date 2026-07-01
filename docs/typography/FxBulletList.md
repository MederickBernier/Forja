# FxBulletList

Ordered or unordered vertical list. Uses `•` for `UNORDERED` and `1.`, `2.`, ... for `ORDERED`.

## Usage

```java
FxBulletList steps = FxBulletList.builder()
    .kind(FxBulletList.Kind.ORDERED)
    .items("Install", "Configure", "Deploy")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `kind(Kind)` | `Kind` | `UNORDERED` | `UNORDERED` (•) or `ORDERED` (1., 2., ...). |
| `items(List<String>)` | `List<String>` | empty | List items. |
| `items(String...)` | `String[]` | empty | Varargs form. |
