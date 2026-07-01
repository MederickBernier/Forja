# FxResultPage

Full-page success / failure / pending / warning summary — large icon + title + description + action row. Status picks the icon color and glyph and exposes matching pseudo-classes.

## Usage

```java
FxResultPage done = FxResultPage.builder()
    .status(FxResultPage.Status.SUCCESS)
    .title("Payment received")
    .description("Order #12345 is on its way.")
    .action(backButton)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `status(Status)` | `Status` | `SUCCESS` | `SUCCESS` / `FAILURE` / `PENDING` / `WARNING`. |
| `title(String)` | `String` | `""` | Result title. |
| `description(String)` | `String` | `""` | Descriptive line. |
| `action(Node...)` | `Node[]` | empty | Action row children. |
