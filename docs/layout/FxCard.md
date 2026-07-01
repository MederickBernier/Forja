# FxCard

Framed content card. Slots for header, body, and footer, plus a `CardVariant` (`DEFAULT` / `OUTLINED` / `ELEVATED`).

## Usage

```java
FxCard card = FxCard.builder()
    .variant(CardVariant.ELEVATED)
    .header(FxLabel.builder().text("Sales").variant(LabelVariant.HEADING).build())
    .body(FxText.builder().text("Q3 revenue up 12%.").build())
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `variant(CardVariant)` | `CardVariant` | `DEFAULT` | Visual variant. |
| `header(Node)` | `Node` | none | Header slot. |
| `body(Node)` | `Node` | none | Body slot. |
| `footer(Node)` | `Node` | none | Footer slot. |
