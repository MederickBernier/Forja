package io.forja.components.buttonsAndActions.fxButtonGroup;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.buttonsAndActions.fxToggleButton.FxToggleButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A single-select segmented control: a row (or column) of
 * {@link FxToggleButton} items sharing a {@link ToggleGroup}. Exposes an
 * observable {@link #valueProperty() value} for the currently selected item.
 *
 * <p>{@code FxButtonGroup} is the {@code FxSegmentedControl} shape — one item
 * always selected (when {@link #isAllowEmpty} is {@code false}); clicking an
 * already-selected item does not deselect it. Enable
 * {@link #setAllowEmpty allowEmpty(true)} to allow no selection.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxButtonGroup align = FxButtonGroup.builder()
 *          .items("Left", "Center", "Right")
 *          .value("Left")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxButtonGroup extends HBox {

    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final ObservableList<String> items = FXCollections.observableArrayList();
    private final ObjectProperty<String> value = new SimpleObjectProperty<>(this, "value");
    private final ObjectProperty<Orientation> orientation = new SimpleObjectProperty<>(this, "orientation", Orientation.HORIZONTAL);

    private final HBox horizontalHolder = this;
    private final VBox verticalHolder = new VBox();

    private final List<FxToggleButton> buttons = new ArrayList<>();
    private boolean allowEmpty = false;
    private boolean syncing = false;

    /**
     * Creates an empty {@code FxButtonGroup} in horizontal orientation.
     */
    public FxButtonGroup() {
        super();
        getStyleClass().add("forja-button-group");
        setSpacing(0);
        verticalHolder.getStyleClass().add("forja-button-group-vertical");

        items.addListener((javafx.collections.ListChangeListener<String>) c -> rebuildButtons());
        value.addListener((obs, o, v) -> syncButtonsFromValue());
        orientation.addListener((obs, o, v) -> layoutHolder());

        toggleGroup.selectedToggleProperty().addListener((obs, o, sel) -> {
            if (syncing) return;
            if (sel == null && !allowEmpty && o != null) {
                syncing = true;
                try { toggleGroup.selectToggle(o); } finally { syncing = false; }
                return;
            }
            syncing = true;
            try { value.set(sel == null ? null : (String) sel.getUserData()); } finally { syncing = false; }
        });
    }

    private void layoutHolder() {
        boolean vertical = getOrientation() == Orientation.VERTICAL;
        if (vertical) {
            horizontalHolder.getChildren().clear();
            if (!horizontalHolder.getChildren().contains(verticalHolder)) horizontalHolder.getChildren().add(verticalHolder);
            verticalHolder.getChildren().setAll(buttons);
        } else {
            verticalHolder.getChildren().clear();
            horizontalHolder.getChildren().setAll(buttons);
        }
    }

    private void rebuildButtons() {
        buttons.clear();
        for (String item : items) {
            FxToggleButton tb = new FxToggleButton(item);
            tb.getStyleClass().add("forja-button-group-item");
            tb.setUserData(item);
            tb.setToggleGroup(toggleGroup);
            if (item != null && item.equals(getValue())) tb.setSelected(true);
            buttons.add(tb);
        }
        layoutHolder();
    }

    private void syncButtonsFromValue() {
        if (syncing) return;
        syncing = true;
        try {
            String v = getValue();
            for (FxToggleButton tb : buttons) {
                boolean match = v != null && v.equals(tb.getUserData());
                if (tb.isSelected() != match) tb.setSelected(match);
            }
            if (v == null && allowEmpty) toggleGroup.selectToggle(null);
        } finally { syncing = false; }
    }

    /** Returns the observable list of item labels. */
    public ObservableList<String> getItems() { return items; }

    /** Returns the value property. */
    public ObjectProperty<String> valueProperty() { return value; }

    /** Returns the currently selected item, or {@code null}. */
    public String getValue() { return value.get(); }

    /** Sets the currently selected item; {@code null} clears if allowEmpty. */
    public void setValue(String v) { value.set(v); }

    /** Returns the underlying toggle group. */
    public ToggleGroup getToggleGroup() { return toggleGroup; }

    /** Returns the underlying toggle-button list. */
    public List<FxToggleButton> getButtons() { return Collections.unmodifiableList(buttons); }

    /** Returns the orientation property. */
    public ObjectProperty<Orientation> orientationProperty() { return orientation; }

    /** Returns the current orientation. */
    public Orientation getOrientation() { return orientation.get(); }

    /** Sets the orientation. */
    public void setOrientation(Orientation v) { orientation.set(v); }

    /** Returns whether the group can have no selection. */
    public boolean isAllowEmpty() { return allowEmpty; }

    /** Sets whether the group can have no selection. */
    public void setAllowEmpty(boolean v) { this.allowEmpty = v; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxButtonGroup}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxButtonGroup}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     *   <li>value — {@code null}</li>
     *   <li>orientation — {@link Orientation#HORIZONTAL}</li>
     *   <li>allowEmpty — {@code false}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxButtonGroup, Builder> {

        private final List<String> items = new ArrayList<>();
        private String value;
        private Orientation orientation = Orientation.HORIZONTAL;
        private boolean allowEmpty = false;

        public Builder items(List<String> items) {
            this.items.clear();
            if (items != null) this.items.addAll(items);
            return this;
        }

        public Builder items(String... items) {
            return items(items == null ? Collections.<String>emptyList() : Arrays.asList(items));
        }

        public Builder value(String value) { this.value = value; return this; }

        public Builder orientation(Orientation orientation) {
            this.orientation = orientation == null ? Orientation.HORIZONTAL : orientation;
            return this;
        }

        public Builder allowEmpty(boolean allowEmpty) { this.allowEmpty = allowEmpty; return this; }

        @Override
        public FxButtonGroup build() {
            FxButtonGroup g = new FxButtonGroup();
            g.setAllowEmpty(allowEmpty);
            g.setOrientation(orientation);
            g.getItems().setAll(items);
            g.setValue(value);
            applyBase(g);
            return g;
        }
    }
}
