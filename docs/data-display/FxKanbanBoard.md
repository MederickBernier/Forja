# FxKanbanBoard

Horizontal HBox of vertical columns holding draggable card nodes. Drag-and-drop is wired between columns automatically; the `onCardMoved` callback fires with source + target column keys + the moved node.

## Usage

```java
FxKanbanBoard board = FxKanbanBoard.builder()
    .column("todo", "To Do")
    .column("doing", "In Progress")
    .column("done", "Done")
    .card("todo", myCardNode)
    .onCardMoved((from, to, node) -> logger.info("{}: {} -> {}", node, from, to))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `column(String, String)` | key + title | — | Add a column. |
| `card(String, Node)` | columnKey + card | — | Add a card to a column. |
| `onCardMoved(OnCardMoved)` | callback | none | Fires on cross-column card move. |
