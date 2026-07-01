package io.forja.components.selection.fxComboBox;

import io.forja.builder.FxComponentBuilder;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A styled combo box built on {@link ComboBox}.
 *
 * <p>{@code FxComboBox} extends the standard JavaFX {@code ComboBox<T>} — all
 * native properties, bindings, and event APIs remain fully accessible. Forja
 * only adds the {@code forja-combo-box} style class and a fluent builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxComboBox<String> country = FxComboBox.<String>builder()
 *          .items("Canada", "USA", "Mexico")
 *          .value("Canada")
 *          .promptText("Select country")
 *          .build();
 *     }
 * </pre>
 *
 * @param <T> the item type
 * @see Builder
 */
public class FxComboBox<T> extends ComboBox<T> {

    /**
     * Creates an empty {@code FxComboBox}.
     */
    public FxComboBox() {
        super();
        getStyleClass().add("forja-combo-box");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxComboBox}.
     *
     * @param <T> the item type
     * @return a new {@link Builder} instance
     */
    public static <T> Builder<T> builder() { return new Builder<T>(); }

    /**
     * Fluent builder for constructing an {@link FxComboBox}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     *   <li>value — {@code null}</li>
     *   <li>promptText — empty</li>
     *   <li>editable — {@code false}</li>
     *   <li>visibleRowCount — {@code 10} (JavaFX default)</li>
     * </ul>
     *
     * @param <T> the item type
     */
    public static class Builder<T> extends FxComponentBuilder<FxComboBox<T>, Builder<T>> {

        private List<T> items = Collections.emptyList();
        private T value;
        private String promptText = "";
        private boolean editable = false;
        private Integer visibleRowCount;

        public Builder<T> items(List<T> items) {
            this.items = items == null ? Collections.<T>emptyList() : items;
            return this;
        }

        @SafeVarargs
        public final Builder<T> items(T... items) {
            return items(items == null ? Collections.<T>emptyList() : Arrays.asList(items));
        }

        public Builder<T> value(T value) {
            this.value = value;
            return this;
        }

        public Builder<T> promptText(String promptText) {
            this.promptText = promptText == null ? "" : promptText;
            return this;
        }

        public Builder<T> editable(boolean editable) {
            this.editable = editable;
            return this;
        }

        public Builder<T> visibleRowCount(int visibleRowCount) {
            this.visibleRowCount = visibleRowCount;
            return this;
        }

        @Override
        public FxComboBox<T> build() {
            FxComboBox<T> cb = new FxComboBox<T>();
            cb.setItems(FXCollections.observableArrayList(items));
            cb.setPromptText(promptText);
            cb.setEditable(editable);
            if (visibleRowCount != null) cb.setVisibleRowCount(visibleRowCount);
            if (value != null) cb.setValue(value);
            applyBase(cb);
            return cb;
        }
    }
}
