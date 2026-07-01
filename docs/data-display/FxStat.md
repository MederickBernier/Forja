# FxStat

KPI card — muted label + large value + optional colored trend delta (`UP` / `DOWN` / `FLAT`).

## Usage

```java
FxStat mrr = FxStat.builder()
    .label("Monthly recurring revenue")
    .value("$42,180")
    .trend("+12.4% MoM", FxStat.Trend.UP)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `label(String)` | `String` | `""` | Muted label. |
| `value(String)` | `String` | `""` | Display value. |
| `trend(String, Trend)` | message + trend | none | Trend message + direction. |
| `trend(String, SemanticVariant)` | message + variant | none | Convenience that maps variant to trend (`SUCCESS` = `UP`, `DANGER` = `DOWN`, else `FLAT`). |
