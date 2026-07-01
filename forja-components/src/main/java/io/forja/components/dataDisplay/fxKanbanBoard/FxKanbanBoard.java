package io.forja.components.dataDisplay.fxKanbanBoard;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A kanban board — horizontal HBox of vertical columns, each holding a
 * {@link VBox} of draggable card nodes.
 *
 * <p>Drag-and-drop is wired between columns automatically: cards can be
 * moved from any column to any other; the {@link OnCardMoved} callback
 * fires with source + target column keys + the moved node.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxKanbanBoard board = FxKanbanBoard.builder()
 *          .column("todo", "To Do")
 *          .column("doing", "In Progress")
 *          .column("done", "Done")
 *          .card("todo", myCardNode)
 *          .onCardMoved((from, to, node) -> logger.info("{}: {} -> {}", node, from, to))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxKanbanBoard extends HBox {

    private final Map<String, Column> columns = new LinkedHashMap<>();
    private OnCardMoved onCardMoved;

    /**
     * Creates an empty {@code FxKanbanBoard}.
     */
    public FxKanbanBoard() {
        super();
        getStyleClass().add("forja-kanban-board");
        setSpacing(12);
        setFillHeight(true);
    }

    /** Adds a column with the given key + display title. */
    public Column addColumn(String key, String title) {
        Column col = new Column(key, title);
        columns.put(key, col);
        getChildren().add(col.getRoot());
        return col;
    }

    /** Adds a card node to a column. */
    public void addCard(String columnKey, Node card) {
        Column col = columns.get(columnKey);
        if (col != null && card != null) col.addCard(card);
    }

    /** Sets the on-card-moved callback. */
    public void setOnCardMoved(OnCardMoved onCardMoved) { this.onCardMoved = onCardMoved; }
    /** Returns registered columns. */
    public Map<String, Column> getColumns() { return java.util.Collections.unmodifiableMap(columns); }

    /** Callback fired when a card moves between columns. */
    @FunctionalInterface
    public interface OnCardMoved { void accept(String from, String to, Node card); }

    /** A single kanban column. */
    public final class Column {
        private final String key;
        private final VBox root = new VBox();
        private final FxLabel title = FxLabel.builder().text("").variant(LabelVariant.SUBHEADING).build();
        private final VBox cardsBox = new VBox(6);
        private final ScrollPane scroll = new ScrollPane(cardsBox);
        private final ObservableList<Node> cardList = FXCollections.observableArrayList();

        Column(String key, String titleText) {
            this.key = key;
            root.getStyleClass().add("forja-kanban-column");
            root.setSpacing(8);
            root.setMinWidth(220);
            root.setPrefWidth(240);
            title.setText(titleText == null ? key : titleText);
            title.getStyleClass().add("forja-kanban-column-title");
            cardsBox.getStyleClass().add("forja-kanban-column-cards");
            cardsBox.setPadding(new javafx.geometry.Insets(4));
            scroll.setFitToWidth(true);
            scroll.getStyleClass().add("forja-kanban-column-scroll");
            VBox.setVgrow(scroll, javafx.scene.layout.Priority.ALWAYS);
            root.getChildren().addAll(title, scroll);

            cardsBox.setOnDragOver(this::onDragOver);
            cardsBox.setOnDragDropped(this::onDragDropped);
            cardsBox.setOnDragEntered(e -> root.pseudoClassStateChanged(javafx.css.PseudoClass.getPseudoClass("drop"), true));
            cardsBox.setOnDragExited(e -> root.pseudoClassStateChanged(javafx.css.PseudoClass.getPseudoClass("drop"), false));
        }

        void addCard(Node card) {
            cardList.add(card);
            cardsBox.getChildren().add(card);
            card.setOnDragDetected(evt -> {
                Dragboard db = card.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent cc = new ClipboardContent();
                cc.putString(key + "/" + System.identityHashCode(card));
                db.setContent(cc);
                card.setUserData(key);
                evt.consume();
            });
        }

        private void onDragOver(DragEvent e) {
            if (e.getGestureSource() != null && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        }

        private void onDragDropped(DragEvent e) {
            Node src = e.getGestureSource() instanceof Node ? (Node) e.getGestureSource() : null;
            if (src == null || src.getUserData() == null) { e.setDropCompleted(false); e.consume(); return; }
            String fromKey = String.valueOf(src.getUserData());
            Column fromCol = columns.get(fromKey);
            if (fromCol == null) { e.setDropCompleted(false); e.consume(); return; }
            fromCol.cardsBox.getChildren().remove(src);
            fromCol.cardList.remove(src);
            cardsBox.getChildren().add(src);
            cardList.add(src);
            src.setUserData(key);
            if (onCardMoved != null) onCardMoved.accept(fromKey, key, src);
            e.setDropCompleted(true);
            e.consume();
        }

        /** Returns the column key. */
        public String getKey() { return key; }
        /** Returns the column root VBox. */
        public VBox getRoot() { return root; }
        /** Returns the cards VBox. */
        public VBox getCardsBox() { return cardsBox; }
        /** Returns the observable card list. */
        public ObservableList<Node> getCards() { return cardList; }
        /** Returns the title label. */
        public FxLabel getTitleLabel() { return title; }
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxKanbanBoard}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxKanbanBoard}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>columns — empty</li>
     *   <li>onCardMoved — none</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxKanbanBoard, Builder> {

        private final java.util.List<String[]> cols = new java.util.ArrayList<>();
        private final java.util.List<Object[]> cards = new java.util.ArrayList<>();
        private OnCardMoved onCardMoved;

        public Builder column(String key, String title) { cols.add(new String[]{key, title}); return this; }
        public Builder card(String columnKey, Node card) { cards.add(new Object[]{columnKey, card}); return this; }
        public Builder onCardMoved(OnCardMoved onCardMoved) { this.onCardMoved = onCardMoved; return this; }

        @Override
        public FxKanbanBoard build() {
            FxKanbanBoard b = new FxKanbanBoard();
            for (String[] c : cols) b.addColumn(c[0], c[1]);
            for (Object[] card : cards) b.addCard((String) card[0], (Node) card[1]);
            b.setOnCardMoved(onCardMoved);
            applyBase(b);
            return b;
        }
    }
}
