# FxRadioGroup

Single-select radio group. Radio buttons share a `ToggleGroup`; the current selection is exposed as a `String` value.

## Usage

```java
FxRadioGroup size = FxRadioGroup.builder()
    .label("Size")
    .items("S", "M", "L")
    .value("M")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<String>)` / `items(String...)` | items | empty | Option labels. |
| `value(String)` | `String` | `null` | Initially selected value. |
| `orientation(Orientation)` | `Orientation` | `VERTICAL` | Row or column. |
| `label(String)` | `String` | `""` | Optional heading label. |
