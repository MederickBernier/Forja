package io.forja.components.selection.fxRadioGroup;

import io.forja.builder.FxNodeBuilder;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A single-select radio group with an observable {@link #valueProperty()}.
 *
 * <p>{@code FxRadioGroup} is a container ({@link VBox} or {@link HBox} based on
 * {@link Orientation}) holding one {@link RadioButton} per item, wired through
 * a shared {@link ToggleGroup}. The current selection is exposed as a
 * {@code String} value ({@code null} when nothing is selected).
 *
 * <p>An optional heading label is shown above the buttons when
 * {@link #setLabel} is non-empty.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxRadioGroup size = FxRadioGroup.builder()
 *          .label("Size")
 *          .items("S", "M", "L")
 *          .value("M")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxRadioGroup extends VBox {

    private final VBox verticalHolder = new VBox(6);
    private final HBox horizontalHolder = new HBox(12);
    private final FxLabel headingLabel = new FxLabel("", LabelVariant.SMALL);
    private final ToggleGroup toggleGroup = new ToggleGroup();

    private final ObservableList<String> items = FXCollections.observableArrayList();
    private final ObjectProperty<String> value = new SimpleObjectProperty<>(this, "value");
    private final ObjectProperty<Orientation> orientation = new SimpleObjectProperty<>(this, "orientation", Orientation.VERTICAL);
    private final StringProperty label = new SimpleStringProperty(this, "label", "");

    private final List<RadioButton> buttons = new ArrayList<>();
    private boolean syncing = false;

    /**
     * Creates an empty {@code FxRadioGroup}.
     */
    public FxRadioGroup() {
        super();
        getStyleClass().add("forja-radio-group");
        setSpacing(6);

        headingLabel.getStyleClass().add("forja-radio-group-heading");
        headingLabel.setMuted(true);
        headingLabel.setVisible(false);
        headingLabel.setManaged(false);

        getChildren().addAll(headingLabel, verticalHolder);

        items.addListener((javafx.collections.ListChangeListener<String>) c -> rebuildButtons());
        value.addListener((obs, o, v) -> syncButtonsFromValue());
        orientation.addListener((obs, o, v) -> layoutHolder());
        label.addListener((obs, o, v) -> {
            String s = v == null ? "" : v;
            headingLabel.setText(s);
            boolean vis = !s.isEmpty();
            headingLabel.setVisible(vis);
            headingLabel.setManaged(vis);
        });

        toggleGroup.selectedToggleProperty().addListener((obs, o, sel) -> {
            if (syncing) return;
            syncing = true;
            try {
                value.set(sel == null ? null : (String) sel.getUserData());
            } finally { syncing = false; }
        });
    }

    private void layoutHolder() {
        Node holder = getOrientation() == Orientation.HORIZONTAL ? horizontalHolder : verticalHolder;
        getChildren().setAll(headingLabel, holder);
        moveButtonsTo(holder);
    }

    private void moveButtonsTo(Node holder) {
        if (holder == verticalHolder) verticalHolder.getChildren().setAll(buttons);
        else if (holder == horizontalHolder) horizontalHolder.getChildren().setAll(buttons);
    }

    private void rebuildButtons() {
        buttons.clear();
        for (String item : items) {
            RadioButton rb = new RadioButton(item);
            rb.getStyleClass().add("forja-radio");
            rb.setUserData(item);
            rb.setToggleGroup(toggleGroup);
            if (item != null && item.equals(getValue())) rb.setSelected(true);
            buttons.add(rb);
        }
        layoutHolder();
    }

    private void syncButtonsFromValue() {
        if (syncing) return;
        syncing = true;
        try {
            String v = getValue();
            for (RadioButton rb : buttons) {
                boolean match = v != null && v.equals(rb.getUserData());
                if (rb.isSelected() != match) rb.setSelected(match);
            }
            if (v == null) toggleGroup.selectToggle(null);
        } finally { syncing = false; }
    }

    /** Returns the observable list of item labels. */
    public ObservableList<String> getItems() { return items; }

    /** Returns the value property. {@code null} when nothing selected. */
    public ObjectProperty<String> valueProperty() { return value; }

    /** Returns the current selected value. */
    public String getValue() { return value.get(); }

    /** Sets the current selected value; {@code null} clears. */
    public void setValue(String v) { value.set(v); }

    /** Returns the underlying button list — for advanced styling. */
    public List<RadioButton> getRadioButtons() { return Collections.unmodifiableList(buttons); }

    /** Returns the shared toggle group. */
    public ToggleGroup getToggleGroup() { return toggleGroup; }

    /** Returns the orientation property. */
    public ObjectProperty<Orientation> orientationProperty() { return orientation; }

    /** Returns the current orientation. */
    public Orientation getOrientation() { return orientation.get(); }

    /** Sets the orientation. */
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
     * Returns a new {@link Builder} for constructing an {@code FxRadioGroup}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxRadioGroup}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     *   <li>value — {@code null}</li>
     *   <li>orientation — {@link Orientation#VERTICAL}</li>
     *   <li>label — empty (hidden)</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxRadioGroup, Builder> {

        private final List<String> items = new ArrayList<>();
        private String value;
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

        public Builder value(String value) {
            this.value = value;
            return this;
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
        public FxRadioGroup build() {
            FxRadioGroup group = new FxRadioGroup();
            group.setOrientation(orientation);
            group.setLabel(label);
            group.getItems().setAll(items);
            group.setValue(value);
            applyBase(group);
            return group;
        }
    }
}
