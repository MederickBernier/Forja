package io.forja.components.typography.fxBulletList;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A vertical bullet or numbered list.
 *
 * <p>{@code FxBulletList} is a {@link VBox} of rows; each row is an {@link HBox}
 * of a marker label + the item text. {@link Kind#UNORDERED} uses a
 * middle-dot marker; {@link Kind#ORDERED} uses {@code 1.}, {@code 2.}, ….
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxBulletList steps = FxBulletList.builder()
 *          .kind(FxBulletList.Kind.ORDERED)
 *          .items("Install", "Configure", "Deploy")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxBulletList extends VBox {

    /** Marker style for the list. */
    public enum Kind { UNORDERED, ORDERED }

    private final ObjectProperty<Kind> kind = new SimpleObjectProperty<>(this, "kind", Kind.UNORDERED);
    private final ObservableList<String> items = FXCollections.observableArrayList();

    /**
     * Creates an empty unordered {@code FxBulletList}.
     */
    public FxBulletList() {
        super();
        getStyleClass().add("forja-bullet-list");
        setSpacing(4);
        kind.addListener((obs, o, v) -> rebuild());
        items.addListener((javafx.collections.ListChangeListener<String>) c -> rebuild());
    }

    private void rebuild() {
        getChildren().clear();
        int idx = 1;
        for (String s : items) {
            String marker = getKind() == Kind.ORDERED ? (idx++ + ".") : "•";
            FxLabel bullet = FxLabel.builder().text(marker).variant(LabelVariant.BODY).muted(true).build();
            bullet.getStyleClass().add("forja-bullet-list-marker");
            FxLabel text = FxLabel.builder().text(s).variant(LabelVariant.BODY).build();
            text.getStyleClass().add("forja-bullet-list-text");
            HBox row = new HBox(6, bullet, text);
            row.getStyleClass().add("forja-bullet-list-row");
            getChildren().add(row);
        }
    }

    /** Returns the kind property. */
    public ObjectProperty<Kind> kindProperty() { return kind; }

    /** Returns the current kind. */
    public Kind getKind() { return kind.get(); }

    /** Sets the marker kind. */
    public void setKind(Kind v) { kind.set(v == null ? Kind.UNORDERED : v); }

    /** Returns the observable items list. */
    public ObservableList<String> getItems() { return items; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxBulletList}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxBulletList}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>kind — {@link Kind#UNORDERED}</li>
     *   <li>items — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxBulletList, Builder> {

        private Kind kind = Kind.UNORDERED;
        private List<String> items = Collections.emptyList();

        public Builder kind(Kind kind) { this.kind = kind == null ? Kind.UNORDERED : kind; return this; }
        public Builder items(List<String> items) { this.items = items == null ? Collections.<String>emptyList() : items; return this; }
        public Builder items(String... items) { return items(items == null ? Collections.<String>emptyList() : Arrays.asList(items)); }

        @Override
        public FxBulletList build() {
            FxBulletList list = new FxBulletList();
            list.setKind(kind);
            list.getItems().setAll(items);
            applyBase(list);
            return list;
        }
    }
}
