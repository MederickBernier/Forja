package io.forja.components.dataDisplay.fxDescriptionList;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A key/value description list rendered as a two-column grid (horizontal)
 * or a stacked pair per row (vertical).
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxDescriptionList dl = FxDescriptionList.builder()
 *          .item("Name", "Mederick Bernier")
 *          .item("Email", "m@example.com")
 *          .item("Role", "Owner")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxDescriptionList extends GridPane {

    private final ObjectProperty<Orientation> orientation = new SimpleObjectProperty<>(this, "orientation", Orientation.HORIZONTAL);
    private final List<Entry> entries = new ArrayList<>();

    /**
     * Creates an empty {@code FxDescriptionList}.
     */
    public FxDescriptionList() {
        super();
        getStyleClass().add("forja-description-list");
        setHgap(16);
        setVgap(6);
        orientation.addListener((obs, o, v) -> rebuild());
        configureColumns();
    }

    private void configureColumns() {
        getColumnConstraints().clear();
        if (getOrientation() == Orientation.HORIZONTAL) {
            ColumnConstraints term = new ColumnConstraints();
            term.setHalignment(HPos.LEFT);
            ColumnConstraints desc = new ColumnConstraints();
            desc.setHgrow(Priority.ALWAYS);
            getColumnConstraints().addAll(term, desc);
        }
    }

    /** Sets the collection of items to render. */
    public void setItems(Map<String, String> items) {
        entries.clear();
        if (items != null) {
            for (Map.Entry<String, String> e : items.entrySet()) {
                entries.add(new Entry(e.getKey(), e.getValue()));
            }
        }
        rebuild();
    }

    /** Adds a single term/description pair. */
    public void addItem(String term, String description) {
        entries.add(new Entry(term == null ? "" : term, description == null ? "" : description));
        rebuild();
    }

    /** Returns the orientation property. */
    public ObjectProperty<Orientation> orientationProperty() { return orientation; }

    /** Returns the current orientation. */
    public Orientation getOrientation() { return orientation.get(); }

    /** Sets the orientation. */
    public void setOrientation(Orientation v) { orientation.set(v == null ? Orientation.HORIZONTAL : v); }

    private void rebuild() {
        getChildren().clear();
        configureColumns();
        int row = 0;
        for (Entry e : entries) {
            FxLabel term = FxLabel.builder().text(e.term).variant(LabelVariant.SMALL).muted(true).build();
            term.getStyleClass().add("forja-description-list-term");
            FxLabel desc = FxLabel.builder().text(e.description).variant(LabelVariant.BODY).build();
            desc.getStyleClass().add("forja-description-list-description");
            if (getOrientation() == Orientation.HORIZONTAL) {
                add(term, 0, row);
                add(desc, 1, row);
                GridPane.setValignment(term, VPos.TOP);
                row++;
            } else {
                add(term, 0, row++);
                add(desc, 0, row++);
            }
        }
    }

    private static final class Entry {
        final String term;
        final String description;
        Entry(String term, String description) { this.term = term; this.description = description; }
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxDescriptionList}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxDescriptionList}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>orientation — {@link Orientation#HORIZONTAL}</li>
     *   <li>items — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxDescriptionList, Builder> {

        private final Map<String, String> items = new LinkedHashMap<>();
        private Orientation orientation = Orientation.HORIZONTAL;

        public Builder orientation(Orientation orientation) { this.orientation = orientation == null ? Orientation.HORIZONTAL : orientation; return this; }
        public Builder item(String term, String description) {
            if (term != null) items.put(term, description == null ? "" : description);
            return this;
        }
        public Builder items(Map<String, String> items) {
            this.items.clear();
            if (items != null) this.items.putAll(items);
            return this;
        }

        @Override
        public FxDescriptionList build() {
            FxDescriptionList dl = new FxDescriptionList();
            dl.setOrientation(orientation);
            dl.setItems(items);
            applyBase(dl);
            return dl;
        }
    }
}
