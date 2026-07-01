# FxLink

Text hyperlink. Extends `javafx.scene.control.Hyperlink` with a variant enum and an optional leading/trailing graphic.

## Usage

```java
FxLink docs = FxLink.builder()
    .text("Read the docs")
    .variant(LinkVariant.BODY)
    .onAction(e -> openDocs())
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Link text. |
| `variant(LinkVariant)` | `LinkVariant` | `BODY` | Size variant. |
| `graphic(Node)` | `Node` | none | Optional graphic (typically an `FxIcon`). |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Click callback. |
