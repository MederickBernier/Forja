package io.forja.components.selection.fxMultiSelect;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.selection.fxCheckBox.FxCheckBox;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A multi-select combo — a summary row + a dropdown popup of checkable
 * items.
 *
 * <p>Clicking the summary row toggles a {@link Popup} of {@link FxCheckBox}
 * items. The summary displays either the {@link #getPromptText} (nothing
 * selected), a single value, or {@code "N selected"} (multiple).
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxMultiSelect ms = FxMultiSelect.builder()
 *          .items("bug", "docs", "enhancement")
 *          .selected("docs")
 *          .promptText("Labels")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxMultiSelect extends HBox {

    private final ObservableList<String> items = FXCollections.observableArrayList();
    private final ObservableList<String> selected = FXCollections.observableArrayList();
    private final StringProperty promptText = new SimpleStringProperty(this, "promptText", "");
    private final BooleanProperty open = new SimpleBooleanProperty(this, "open", false);

    private final FxLabel summary = new FxLabel("", LabelVariant.BODY);
    private final FxIcon caret = new FxIcon("fth-chevron-down");
    private final VBox listBox = new VBox();
    private final Popup popup = new Popup();
    private boolean syncing = false;

    /**
     * Creates an empty {@code FxMultiSelect}.
     */
    public FxMultiSelect() {
        super();
        getStyleClass().add("forja-multi-select");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(6);

        summary.setMuted(true);
        HBox.setHgrow(summary, Priority.ALWAYS);
        getChildren().addAll(summary, caret);

        listBox.getStyleClass().add("forja-multi-select-list");
        listBox.setSpacing(4);
        ScrollPane sp = new ScrollPane(listBox);
        sp.setFitToWidth(true);
        sp.setPrefViewportHeight(200);
        sp.getStyleClass().add("forja-multi-select-scroll");
        popup.setAutoHide(true);
        popup.getContent().add(sp);

        setOnMouseClicked(e -> { togglePopup(); e.consume(); });

        items.addListener((javafx.collections.ListChangeListener<String>) c -> rebuildList());
        selected.addListener((javafx.collections.ListChangeListener<String>) c -> { syncCheckBoxes(); refreshSummary(); });
        promptText.addListener((obs, o, v) -> refreshSummary());

        refreshSummary();
    }

    private void rebuildList() {
        listBox.getChildren().clear();
        for (final String item : items) {
            FxCheckBox cb = new FxCheckBox(item);
            cb.setSelected(selected.contains(item));
            cb.selectedProperty().addListener((obs, o, v) -> {
                if (syncing) return;
                syncing = true;
                try {
                    if (v && !selected.contains(item)) selected.add(item);
                    else if (!v) selected.remove(item);
                } finally { syncing = false; }
            });
            listBox.getChildren().add(cb);
        }
        refreshSummary();
    }

    private void syncCheckBoxes() {
        if (syncing) return;
        syncing = true;
        try {
            Set<String> sel = new HashSet<>(selected);
            for (int i = 0; i < items.size() && i < listBox.getChildren().size(); i++) {
                FxCheckBox cb = (FxCheckBox) listBox.getChildren().get(i);
                boolean want = sel.contains(items.get(i));
                if (cb.isSelected() != want) cb.setSelected(want);
            }
        } finally { syncing = false; }
    }

    private void refreshSummary() {
        int n = selected.size();
        if (n == 0) {
            summary.setText(getPromptText());
            summary.setMuted(true);
        } else if (n == 1) {
            summary.setText(selected.get(0));
            summary.setMuted(false);
        } else {
            summary.setText(n + " selected");
            summary.setMuted(false);
        }
    }

    private void togglePopup() {
        if (open.get()) hidePopup();
        else showPopup();
    }

    private void showPopup() {
        if (getScene() == null || getScene().getWindow() == null) return;
        Bounds b = localToScreen(getBoundsInLocal());
        if (b == null) return;
        listBox.setMinWidth(b.getWidth());
        popup.show(getScene().getWindow(), b.getMinX(), b.getMaxY());
        open.set(true);
    }

    private void hidePopup() {
        popup.hide();
        open.set(false);
    }

    /** Returns the items list. */
    public ObservableList<String> getItems() { return items; }

    /** Returns the selected-values list. */
    public ObservableList<String> getSelected() { return selected; }

    /** Returns the selected values as an unmodifiable {@link Set} snapshot. */
    public Set<String> getSelectedSet() { return Collections.unmodifiableSet(new LinkedHashSet<>(selected)); }

    /** Returns the prompt-text property. */
    public StringProperty promptTextProperty() { return promptText; }

    /** Returns the current prompt. */
    public String getPromptText() { return promptText.get(); }

    /** Sets the prompt shown when nothing is selected. */
    public void setPromptText(String v) { promptText.set(v == null ? "" : v); }

    /** Returns the open property. */
    public BooleanProperty openProperty() { return open; }

    /** Returns whether the popup is open. */
    public boolean isOpen() { return open.get(); }

    /** Returns the summary label. */
    public FxLabel getSummary() { return summary; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxMultiSelect}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxMultiSelect}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     *   <li>selected — empty</li>
     *   <li>promptText — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxMultiSelect, Builder> {

        private List<String> items = Collections.emptyList();
        private List<String> selected = Collections.emptyList();
        private String promptText = "";

        public Builder items(List<String> items) { this.items = items == null ? Collections.<String>emptyList() : items; return this; }
        public Builder items(String... items) { return items(items == null ? Collections.<String>emptyList() : Arrays.asList(items)); }
        public Builder selected(List<String> selected) { this.selected = selected == null ? Collections.<String>emptyList() : selected; return this; }
        public Builder selected(String... selected) { return selected(selected == null ? Collections.<String>emptyList() : Arrays.asList(selected)); }
        public Builder promptText(String promptText) { this.promptText = promptText == null ? "" : promptText; return this; }

        @Override
        public FxMultiSelect build() {
            FxMultiSelect ms = new FxMultiSelect();
            ms.setPromptText(promptText);
            ms.getItems().setAll(items);
            ms.getSelected().setAll(new ArrayList<>(selected));
            applyBase(ms);
            return ms;
        }
    }
}
