# FxCheckGroup

Multi-select group of `FxCheckBox` items. Exposes an observable list of selected values and an optional heading label.

## Usage

```java
FxCheckGroup toppings = FxCheckGroup.builder()
    .label("Toppings")
    .items("Cheese", "Pepperoni", "Mushrooms")
    .selected("Cheese")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<String>)` / `items(String...)` | items | empty | Option labels. |
| `selected(List<String>)` / `selected(String...)` | items | empty | Initially selected values. |
| `orientation(Orientation)` | `Orientation` | `VERTICAL` | Row or column layout. |
| `label(String)` | `String` | `""` | Optional heading label. |
