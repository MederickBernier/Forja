package io.forja.components.selection.fxCheckGroup;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.selection.fxCheckBox.FxCheckBox;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A group of {@link FxCheckBox} items with an observable {@link #selectedValuesProperty()}
 * multi-select model.
 *
 * <p>{@code FxCheckGroup} is a container ({@link VBox} or {@link HBox} based on
 * {@link Orientation}) holding one {@link FxCheckBox} per item. Selecting or
 * deselecting any box updates the group's selected-values set; setting the
 * selected values on the group updates the boxes.
 *
 * <p>An optional heading label is shown above the boxes when
 * {@link #setLabel} is non-empty.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxCheckGroup toppings = FxCheckGroup.builder()
 *          .label("Toppings")
 *          .items("Cheese", "Pepperoni", "Mushrooms")
 *          .selected("Cheese")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxCheckGroup extends VBox {

    private final VBox verticalHolder = new VBox(6);
    private final HBox horizontalHolder = new HBox(12);
    private final FxLabel headingLabel = new FxLabel("", LabelVariant.SMALL);

    private final ObservableList<String> items = FXCollections.observableArrayList();
    private final ObservableList<String> selectedValues = FXCollections.observableArrayList();
    private final ObjectProperty<Orientation> orientation = new SimpleObjectProperty<>(this, "orientation", Orientation.VERTICAL);
    private final StringProperty label = new SimpleStringProperty(this, "label", "");

    private final List<FxCheckBox> boxes = new ArrayList<>();
    private boolean syncing = false;

    /**
     * Creates an empty {@code FxCheckGroup}.
     */
    public FxCheckGroup() {
        super();
        getStyleClass().add("forja-check-group");
        setSpacing(6);

        headingLabel.getStyleClass().add("forja-check-group-heading");
        headingLabel.setMuted(true);
        headingLabel.setVisible(false);
        headingLabel.setManaged(false);

        getChildren().addAll(headingLabel, verticalHolder);

        items.addListener((javafx.collections.ListChangeListener<String>) c -> rebuildBoxes());
        selectedValues.addListener((javafx.collections.ListChangeListener<String>) c -> syncBoxesFromSelection());
        orientation.addListener((obs, o, v) -> layoutHolder());
        label.addListener((obs, o, v) -> {
            String s = v == null ? "" : v;
            headingLabel.setText(s);
            boolean vis = !s.isEmpty();
            headingLabel.setVisible(vis);
            headingLabel.setManaged(vis);
        });
    }

    private void layoutHolder() {
        Node holder = getOrientation() == Orientation.HORIZONTAL ? horizontalHolder : verticalHolder;
        getChildren().setAll(headingLabel, holder);
        moveBoxesTo(holder);
    }

    private void moveBoxesTo(Node holder) {
        VBox v = holder == verticalHolder ? verticalHolder : null;
        HBox h = holder == horizontalHolder ? horizontalHolder : null;
        if (v != null) v.getChildren().setAll(boxes);
        else if (h != null) h.getChildren().setAll(boxes);
    }

    private void rebuildBoxes() {
        boxes.clear();
        for (String item : items) {
            FxCheckBox cb = new FxCheckBox(item);
            cb.setSelected(selectedValues.contains(item));
            cb.selectedProperty().addListener((obs, o, v) -> {
                if (syncing) return;
                syncing = true;
                try {
                    if (v && !selectedValues.contains(item)) selectedValues.add(item);
                    else if (!v) selectedValues.remove(item);
                } finally { syncing = false; }
            });
            boxes.add(cb);
        }
        layoutHolder();
    }

    private void syncBoxesFromSelection() {
        if (syncing) return;
        syncing = true;
        try {
            Set<String> sel = new HashSet<>(selectedValues);
            for (int i = 0; i < items.size() && i < boxes.size(); i++) {
                boxes.get(i).setSelected(sel.contains(items.get(i)));
            }
        } finally { syncing = false; }
    }

    /** Returns the observable list of item labels. */
    public ObservableList<String> getItems() { return items; }

    /** Returns the observable selected-values list. */
    public ObservableList<String> getSelectedValues() { return selectedValues; }

    /**
     * Returns the selected values as an unmodifiable {@link Set} snapshot.
     * Use {@link #getSelectedValues()} for a live observable list.
     */
    public Set<String> getSelectedValuesSet() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(selectedValues));
    }

    /** Returns the selected-values property (an ObservableList wrapper). */
    public ObservableList<String> selectedValuesProperty() { return selectedValues; }

    /** Returns the underlying check-box list — for advanced styling. */
    public List<FxCheckBox> getCheckBoxes() { return Collections.unmodifiableList(boxes); }

    /** Returns the orientation property. */
    public ObjectProperty<Orientation> orientationProperty() { return orientation; }

    /** Returns the current orientation. */
    public Orientation getOrientation() { return orientation.get(); }

    /** Sets the orientation (default {@link Orientation#VERTICAL}). */
    public void setOrientation(Orientation v) { orientation.set(v); }

    /** Returns the group-heading label property. */
    public StringProperty labelProperty() { return label; }

    /** Returns the current heading label. */
    public String getLabel() { return label.get(); }

    /** Sets the heading label; empty hides it. */
    public void setLabel(String v) { label.set(v == null ? "" : v); }

    /** Returns the heading {@link FxLabel} node. */
    public FxLabel getHeadingLabel() { return headingLabel; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxCheckGroup}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxCheckGroup}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     *   <li>selected — empty</li>
     *   <li>orientation — {@link Orientation#VERTICAL}</li>
     *   <li>label — empty (hidden)</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxCheckGroup, Builder> {

        private final List<String> items = new ArrayList<>();
        private final List<String> selected = new ArrayList<>();
        private Orientation orientation = Orientation.VERTICAL;
        private String label = "";

        public Builder items(List<String> items) {
            this.items.clear();
            if (items != null) this.items.addAll(items);
            return this;
        }

        public Builder items(String... items) {
            return items(items == null ? Collections.<String>emptyList() : Arrays.asList(items));
        }

        public Builder selected(List<String> selected) {
            this.selected.clear();
            if (selected != null) this.selected.addAll(selected);
            return this;
        }

        public Builder selected(String... selected) {
            return selected(selected == null ? Collections.<String>emptyList() : Arrays.asList(selected));
        }

        public Builder orientation(Orientation orientation) {
            this.orientation = orientation == null ? Orientation.VERTICAL : orientation;
            return this;
        }

        public Builder label(String label) {
            this.label = label == null ? "" : label;
            return this;
        }

        @Override
        public FxCheckGroup build() {
            FxCheckGroup group = new FxCheckGroup();
            group.setOrientation(orientation);
            group.setLabel(label);
            group.getItems().setAll(items);
            group.getSelectedValues().setAll(selected);
            applyBase(group);
            return group;
        }
    }
}
