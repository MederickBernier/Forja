# FxButtonGroup

Segmented row of `FxToggleButton` items sharing a `ToggleGroup`. Exposes an observable value for the currently-selected item. Also aliased as `FxSegmentedControl`.

## Usage

```java
FxButtonGroup align = FxButtonGroup.builder()
    .items("Left", "Center", "Right")
    .value("Left")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<String>)` | `List<String>` | empty | Segment labels. |
| `items(String...)` | `String[]` | empty | Segment labels varargs form. |
| `value(String)` | `String` | `null` | Initially selected item. |
| `orientation(Orientation)` | `Orientation` | `HORIZONTAL` | Row or column layout. |
| `allowEmpty(boolean)` | `boolean` | `false` | Whether the group can have no selection. |
