package io.forja.components.inputs.fxAutocomplete;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.inputs.InputVariant;
import io.forja.components.inputs.fxTextField.FxTextField;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * A text input with a suggestion popup — an {@link FxTextField} plus a
 * {@link ListView} rendered in a {@link Popup} anchored below the field.
 *
 * <p>The popup shows the current filtered candidates (matched against the
 * field's text via {@link Function#apply(Object) filter} — default is a
 * case-insensitive substring match on the string representation). Choosing a
 * suggestion sets the field's text and fires {@link OnSelect}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxAutocomplete<String> ac = FxAutocomplete.<String>builder()
 *          .items("apple", "apricot", "banana")
 *          .promptText("Fruit")
 *          .onSelect(fruit -> logger.info("chose {}", fruit))
 *          .build();
 *     }
 * </pre>
 *
 * @param <T> the suggestion item type
 * @see Builder
 */
public class FxAutocomplete<T> extends VBox {

    private final FxTextField field = new FxTextField();
    private final ListView<T> suggestions = new ListView<>();
    private final Popup popup = new Popup();

    private final ObservableList<T> items = FXCollections.observableArrayList();
    private final ObservableList<T> filtered = FXCollections.observableArrayList();
    private final StringProperty text = new SimpleStringProperty(this, "text", "");
    private final BooleanProperty open = new SimpleBooleanProperty(this, "open", false);
    private final ObjectProperty<T> value = new SimpleObjectProperty<>(this, "value");

    private Function<T, String> stringifier = Object::toString;
    private OnSelect<T> onSelect;

    /**
     * Creates an empty {@code FxAutocomplete}.
     */
    public FxAutocomplete() {
        super();
        getStyleClass().add("forja-autocomplete");
        setSpacing(0);
        getChildren().add(field);

        suggestions.getStyleClass().add("forja-autocomplete-suggestions");
        suggestions.setItems(filtered);
        suggestions.setPrefHeight(160);
        popup.setAutoHide(true);
        popup.getContent().add(suggestions);

        text.bindBidirectional(field.textProperty());
        text.addListener((obs, o, v) -> refreshFiltered());
        items.addListener((javafx.collections.ListChangeListener<T>) c -> refreshFiltered());
        field.getTextField().focusedProperty().addListener((obs, o, focused) -> {
            if (focused) showPopupIfNeeded();
            else hidePopup();
        });
        field.getTextField().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DOWN) suggestions.getSelectionModel().selectNext();
            else if (e.getCode() == KeyCode.UP) suggestions.getSelectionModel().selectPrevious();
            else if (e.getCode() == KeyCode.ENTER) {
                T sel = suggestions.getSelectionModel().getSelectedItem();
                if (sel != null) chooseItem(sel);
            } else if (e.getCode() == KeyCode.ESCAPE) hidePopup();
        });
        suggestions.setOnMouseClicked(e -> {
            T sel = suggestions.getSelectionModel().getSelectedItem();
            if (sel != null) chooseItem(sel);
        });
    }

    private void refreshFiltered() {
        String q = getText() == null ? "" : getText().toLowerCase();
        filtered.clear();
        for (T item : items) {
            String label = stringifier.apply(item);
            if (label == null) continue;
            if (q.isEmpty() || label.toLowerCase().contains(q)) filtered.add(item);
        }
        if (field.getTextField().isFocused()) showPopupIfNeeded();
    }

    private void showPopupIfNeeded() {
        if (filtered.isEmpty()) { hidePopup(); return; }
        if (getScene() == null || getScene().getWindow() == null) return;
        Bounds b = field.localToScreen(field.getBoundsInLocal());
        if (b == null) return;
        suggestions.setPrefWidth(b.getWidth());
        popup.show(getScene().getWindow(), b.getMinX(), b.getMaxY());
        open.set(true);
    }

    private void hidePopup() {
        popup.hide();
        open.set(false);
    }

    private void chooseItem(T item) {
        value.set(item);
        text.set(stringifier.apply(item));
        hidePopup();
        if (onSelect != null) onSelect.accept(item);
    }

    /** Returns the inner {@link FxTextField}. */
    public FxTextField getField() { return field; }

    /** Returns the suggestion {@link ListView}. */
    public ListView<T> getSuggestions() { return suggestions; }

    /** Returns the observable items list. */
    public ObservableList<T> getItems() { return items; }

    /** Returns the observable filtered list. */
    public ObservableList<T> getFilteredItems() { return filtered; }

    /** Returns the text property. */
    public StringProperty textProperty() { return text; }

    /** Returns the current text. */
    public String getText() { return text.get(); }

    /** Sets the text. */
    public void setText(String v) { text.set(v == null ? "" : v); }

    /** Returns the open property. */
    public BooleanProperty openProperty() { return open; }

    /** Returns whether the popup is open. */
    public boolean isOpen() { return open.get(); }

    /** Returns the value property (last-chosen item). */
    public ObjectProperty<T> valueProperty() { return value; }

    /** Returns the last-chosen item, or {@code null}. */
    public T getValue() { return value.get(); }

    /** Sets the stringifier used to render + filter items. */
    public void setStringifier(Function<T, String> stringifier) {
        this.stringifier = stringifier == null ? Object::toString : stringifier;
        refreshFiltered();
    }

    /** Sets the on-select callback. */
    public void setOnSelect(OnSelect<T> onSelect) { this.onSelect = onSelect; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxAutocomplete}.
     *
     * @param <T> item type
     * @return a new {@link Builder} instance
     */
    public static <T> Builder<T> builder() { return new Builder<T>(); }

    /** Callback fired when a suggestion is chosen. */
    @FunctionalInterface
    public interface OnSelect<T> { void accept(T item); }

    /**
     * Fluent builder for constructing an {@link FxAutocomplete}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     *   <li>text / promptText — empty</li>
     *   <li>variant — {@link InputVariant#DEFAULT}</li>
     *   <li>helperText / errorText — empty</li>
     *   <li>stringifier — {@link Object#toString()}</li>
     * </ul>
     *
     * @param <T> item type
     */
    public static class Builder<T> extends FxNodeBuilder<FxAutocomplete<T>, Builder<T>> {

        private List<T> items = Collections.emptyList();
        private String text = "";
        private String promptText = "";
        private InputVariant variant = InputVariant.DEFAULT;
        private String helperText = "";
        private String errorText = "";
        private Function<T, String> stringifier = Object::toString;
        private OnSelect<T> onSelect;

        public Builder<T> items(List<T> items) { this.items = items == null ? Collections.<T>emptyList() : items; return this; }

        @SafeVarargs
        public final Builder<T> items(T... items) { return items(items == null ? Collections.<T>emptyList() : Arrays.asList(items)); }

        public Builder<T> text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder<T> promptText(String promptText) { this.promptText = promptText == null ? "" : promptText; return this; }
        public Builder<T> variant(InputVariant variant) { this.variant = variant; return this; }
        public Builder<T> helperText(String helperText) { this.helperText = helperText == null ? "" : helperText; return this; }
        public Builder<T> errorText(String errorText) { this.errorText = errorText == null ? "" : errorText; return this; }
        public Builder<T> stringifier(Function<T, String> stringifier) { this.stringifier = stringifier == null ? Object::toString : stringifier; return this; }
        public Builder<T> onSelect(OnSelect<T> onSelect) { this.onSelect = onSelect; return this; }

        @Override
        public FxAutocomplete<T> build() {
            FxAutocomplete<T> ac = new FxAutocomplete<T>();
            ac.setStringifier(stringifier);
            ac.getField().setPromptText(promptText);
            ac.getField().setVariant(variant);
            ac.getField().setHelperText(helperText);
            ac.getField().setErrorText(errorText);
            ac.getItems().setAll(items);
            ac.setText(text);
            ac.setOnSelect(onSelect);
            applyBase(ac);
            return ac;
        }
    }
}
