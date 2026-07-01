package io.forja.components.dataDisplay.fxList;

import io.forja.builder.FxComponentBuilder;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A styled list view built on {@link ListView}.
 *
 * <p>Extends the standard JavaFX {@code ListView<T>} — all native properties,
 * cell factories, and selection APIs remain fully accessible. Forja only
 * adds the {@code forja-list} style class and a fluent builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxList<String> tags = FxList.<String>builder()
 *          .items("bug", "enhancement", "docs")
 *          .selectionMode(SelectionMode.MULTIPLE)
 *          .build();
 *     }
 * </pre>
 *
 * @param <T> the item type
 * @see Builder
 */
public class FxList<T> extends ListView<T> {

    /**
     * Creates an empty {@code FxList}.
     */
    public FxList() {
        super();
        getStyleClass().add("forja-list");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxList}.
     *
     * @param <T> item type
     * @return a new {@link Builder} instance
     */
    public static <T> Builder<T> builder() { return new Builder<T>(); }

    /**
     * Fluent builder for constructing an {@link FxList}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     *   <li>selectionMode — {@link SelectionMode#SINGLE}</li>
     *   <li>editable — {@code false}</li>
     * </ul>
     *
     * @param <T> item type
     */
    public static class Builder<T> extends FxComponentBuilder<FxList<T>, Builder<T>> {

        private List<T> items = Collections.emptyList();
        private SelectionMode selectionMode = SelectionMode.SINGLE;
        private boolean editable = false;

        public Builder<T> items(List<T> items) { this.items = items == null ? Collections.<T>emptyList() : items; return this; }

        @SafeVarargs
        public final Builder<T> items(T... items) { return items(items == null ? Collections.<T>emptyList() : Arrays.asList(items)); }

        public Builder<T> selectionMode(SelectionMode selectionMode) { this.selectionMode = selectionMode == null ? SelectionMode.SINGLE : selectionMode; return this; }

        public Builder<T> editable(boolean editable) { this.editable = editable; return this; }

        @Override
        public FxList<T> build() {
            FxList<T> l = new FxList<T>();
            l.setItems(FXCollections.observableArrayList(items));
            l.getSelectionModel().setSelectionMode(selectionMode);
            l.setEditable(editable);
            applyBase(l);
            return l;
        }
    }
}
