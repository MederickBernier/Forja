package io.forja.components.layout.fxAccordion;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.layout.fxCollapse.FxCollapse;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A vertical stack of {@link FxCollapse} sections. When
 * {@link #isSingleOpen} is {@code true} (the default), expanding one section
 * automatically collapses the others.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxAccordion faq = FxAccordion.builder()
 *          .sections(shipping, returns, contact)
 *          .singleOpen(true)
 *          .build();
 *     }
 * </pre>
 *
 * @see FxCollapse
 * @see Builder
 */
public class FxAccordion extends VBox {

    private final ObservableList<FxCollapse> sections = FXCollections.observableArrayList();
    private final BooleanProperty singleOpen = new SimpleBooleanProperty(this, "singleOpen", true);
    private boolean syncing = false;

    /**
     * Creates an empty {@code FxAccordion}.
     */
    public FxAccordion() {
        super();
        getStyleClass().add("forja-accordion");
        setSpacing(0);

        sections.addListener((javafx.collections.ListChangeListener<FxCollapse>) c -> rebuild());
        singleOpen.addListener((obs, o, v) -> { if (v) enforceSingleOpen(null); });
    }

    private void rebuild() {
        getChildren().setAll(sections);
        for (FxCollapse s : sections) {
            s.expandedProperty().addListener((obs, o, v) -> {
                if (v && isSingleOpen()) enforceSingleOpen(s);
            });
        }
    }

    private void enforceSingleOpen(FxCollapse keepOpen) {
        if (syncing) return;
        syncing = true;
        try {
            for (FxCollapse s : sections) {
                if (s != keepOpen && s.isExpanded()) s.setExpanded(false);
            }
        } finally { syncing = false; }
    }

    /** Returns the observable list of sections. */
    public ObservableList<FxCollapse> getSections() { return sections; }

    /** Returns an unmodifiable view of the sections list. */
    public List<FxCollapse> getSectionList() { return Collections.unmodifiableList(sections); }

    /** Returns the single-open property. */
    public BooleanProperty singleOpenProperty() { return singleOpen; }

    /** Returns whether only one section can be expanded at a time. */
    public boolean isSingleOpen() { return singleOpen.get(); }

    /** Sets whether only one section can be expanded at a time. */
    public void setSingleOpen(boolean v) { singleOpen.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxAccordion}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxAccordion}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>sections — empty</li>
     *   <li>singleOpen — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxAccordion, Builder> {

        private FxCollapse[] sections = new FxCollapse[0];
        private boolean singleOpen = true;

        public Builder sections(FxCollapse... sections) { this.sections = sections == null ? new FxCollapse[0] : sections; return this; }
        public Builder singleOpen(boolean singleOpen) { this.singleOpen = singleOpen; return this; }

        @Override
        public FxAccordion build() {
            FxAccordion a = new FxAccordion();
            a.setSingleOpen(singleOpen);
            a.getSections().addAll(Arrays.asList(sections));
            applyBase(a);
            return a;
        }
    }
}
