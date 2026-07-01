package io.forja.components.dataDisplay.fxVirtualList;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.Function;

/**
 * A lightweight virtual list — renders only the visible slice of a large
 * {@link ObservableList} into a scrollable viewport.
 *
 * <p>{@code FxVirtualList} assumes each item renders at a fixed height
 * ({@link #getItemHeight}). A spacer keeps the scroll pane's total height
 * proportional to the item count, while a moving {@link VBox} paints only
 * the visible slice.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxVirtualList<String> list = FxVirtualList.<String>builder()
 *          .items(oneMillionStrings)
 *          .itemHeight(28)
 *          .cellFactory(s -> new FxLabel(s))
 *          .build();
 *     }
 * </pre>
 *
 * @param <T> item type
 * @see Builder
 */
public class FxVirtualList<T> extends VBox {

    private final ObservableList<T> items = FXCollections.observableArrayList();
    private final IntegerProperty itemHeight = new SimpleIntegerProperty(this, "itemHeight", 28);
    private Function<T, Node> cellFactory = t -> null;

    private final Region spacer = new Region();
    private final VBox visibleBox = new VBox();
    private final StackPane content = new StackPane(spacer, visibleBox);
    private final ScrollPane scrollPane = new ScrollPane(content);

    /**
     * Creates an empty {@code FxVirtualList}.
     */
    public FxVirtualList() {
        super();
        getStyleClass().add("forja-virtual-list");
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("forja-virtual-list-scroll");
        StackPane.setAlignment(visibleBox, Pos.TOP_LEFT);
        visibleBox.getStyleClass().add("forja-virtual-list-visible");
        getChildren().add(scrollPane);
        setFillWidth(true);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        items.addListener((javafx.collections.ListChangeListener<T>) c -> refreshLayout());
        itemHeight.addListener((obs, o, v) -> refreshLayout());
        scrollPane.vvalueProperty().addListener((obs, o, v) -> repaint());
        scrollPane.viewportBoundsProperty().addListener((obs, o, v) -> repaint());
        refreshLayout();
    }

    private void refreshLayout() {
        double totalHeight = items.size() * (double) itemHeight.get();
        spacer.setMinHeight(totalHeight);
        spacer.setPrefHeight(totalHeight);
        spacer.setMaxHeight(totalHeight);
        repaint();
    }

    private void repaint() {
        double ih = itemHeight.get();
        if (ih <= 0 || items.isEmpty()) { visibleBox.getChildren().clear(); return; }
        double vp = scrollPane.getViewportBounds() == null ? 0 : scrollPane.getViewportBounds().getHeight();
        double totalHeight = items.size() * ih;
        double offset = Math.max(0, (totalHeight - vp) * scrollPane.getVvalue());
        int first = (int) Math.floor(offset / ih);
        int visibleCount = (int) Math.ceil(vp / ih) + 1;
        int last = Math.min(items.size(), first + visibleCount);
        visibleBox.getChildren().clear();
        for (int i = first; i < last; i++) {
            Node cell = cellFactory.apply(items.get(i));
            if (cell == null) continue;
            if (cell instanceof Region) {
                ((Region) cell).setPrefHeight(ih);
                ((Region) cell).setMinHeight(ih);
                ((Region) cell).setMaxHeight(ih);
            }
            visibleBox.getChildren().add(cell);
        }
        visibleBox.setTranslateY(first * ih);
    }

    /** Returns the items list. */
    public ObservableList<T> getItems() { return items; }
    /** Returns the item-height property. */
    public IntegerProperty itemHeightProperty() { return itemHeight; }
    /** Returns the current item height. */
    public int getItemHeight() { return itemHeight.get(); }
    /** Sets the item height. */
    public void setItemHeight(int v) { itemHeight.set(Math.max(1, v)); }
    /** Sets the cell factory. */
    public void setCellFactory(Function<T, Node> factory) { this.cellFactory = factory == null ? t -> null : factory; refreshLayout(); }
    /** Returns the cell factory. */
    public Function<T, Node> getCellFactory() { return cellFactory; }
    /** Returns the scroll pane. */
    public ScrollPane getScrollPane() { return scrollPane; }
    /** Returns the visible box. */
    public VBox getVisibleBox() { return visibleBox; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxVirtualList}.
     *
     * @param <T> item type
     * @return a new {@link Builder} instance
     */
    public static <T> Builder<T> builder() { return new Builder<T>(); }

    /**
     * Fluent builder for constructing an {@link FxVirtualList}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     *   <li>itemHeight — {@code 28}</li>
     *   <li>cellFactory — returns {@code null}</li>
     * </ul>
     *
     * @param <T> item type
     */
    public static class Builder<T> extends FxNodeBuilder<FxVirtualList<T>, Builder<T>> {

        private java.util.List<T> items = java.util.Collections.emptyList();
        private int itemHeight = 28;
        private Function<T, Node> cellFactory;

        public Builder<T> items(java.util.List<T> items) { this.items = items == null ? java.util.Collections.<T>emptyList() : items; return this; }
        public Builder<T> itemHeight(int itemHeight) { this.itemHeight = Math.max(1, itemHeight); return this; }
        public Builder<T> cellFactory(Function<T, Node> cellFactory) { this.cellFactory = cellFactory; return this; }

        @Override
        public FxVirtualList<T> build() {
            FxVirtualList<T> v = new FxVirtualList<T>();
            v.setItemHeight(itemHeight);
            v.setCellFactory(cellFactory);
            v.getItems().setAll(items);
            applyBase(v);
            return v;
        }
    }
}
