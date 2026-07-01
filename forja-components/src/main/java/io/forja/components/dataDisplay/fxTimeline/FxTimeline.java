package io.forja.components.dataDisplay.fxTimeline;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.tokens.SemanticVariant;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A vertical event timeline — colored dot + connector line + title/subtitle
 * card per entry.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxTimeline t = FxTimeline.builder()
 *          .entry("Deploy", "Pushed to prod at 09:32", SemanticVariant.SUCCESS)
 *          .entry("Rollback", "Reverted at 09:45", SemanticVariant.DANGER)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxTimeline extends VBox {

    private final List<Entry> entries = new ArrayList<>();

    /**
     * Creates an empty {@code FxTimeline}.
     */
    public FxTimeline() {
        super();
        getStyleClass().add("forja-timeline");
        setSpacing(0);
    }

    /** Adds an entry. */
    public void addEntry(String title, String subtitle, SemanticVariant variant) {
        entries.add(new Entry(title, subtitle, variant == null ? SemanticVariant.ACCENT : variant));
        rebuild();
    }

    /** Clears all entries. */
    public void clearEntries() { entries.clear(); rebuild(); }

    /** Returns the entries (read-only). */
    public List<Entry> getEntries() { return Collections.unmodifiableList(entries); }

    private void rebuild() {
        getChildren().clear();
        for (int i = 0; i < entries.size(); i++) {
            boolean isLast = i == entries.size() - 1;
            Entry e = entries.get(i);
            getChildren().add(buildRow(e, isLast));
        }
    }

    private HBox buildRow(Entry e, boolean isLast) {
        Region dot = new Region();
        dot.getStyleClass().addAll("forja-timeline-dot", "forja-timeline-dot-" + e.variant.name().toLowerCase());
        dot.setMinSize(12, 12);
        dot.setMaxSize(12, 12);
        Region line = new Region();
        line.getStyleClass().add("forja-timeline-line");
        line.setMinWidth(2); line.setMaxWidth(2); line.setPrefWidth(2);
        VBox axis = new VBox(4, dot, line);
        axis.setAlignment(Pos.TOP_CENTER);
        if (isLast) axis.getChildren().remove(line);
        StackPane axisWrap = new StackPane(axis);
        axisWrap.setMinWidth(24); axisWrap.setMaxWidth(24);
        VBox card = new VBox(2);
        card.getStyleClass().add("forja-timeline-card");
        FxLabel titleLabel = FxLabel.builder().text(e.title).variant(LabelVariant.BODY).build();
        titleLabel.getStyleClass().add("forja-timeline-title");
        card.getChildren().add(titleLabel);
        if (e.subtitle != null && !e.subtitle.isEmpty()) {
            FxLabel sub = FxLabel.builder().text(e.subtitle).variant(LabelVariant.SMALL).muted(true).build();
            sub.getStyleClass().add("forja-timeline-subtitle");
            card.getChildren().add(sub);
        }
        HBox row = new HBox(8, axisWrap, card);
        row.setAlignment(Pos.TOP_LEFT);
        row.getStyleClass().add("forja-timeline-row");
        return row;
    }

    /** An entry — public for iteration. */
    public static final class Entry {
        public final String title;
        public final String subtitle;
        public final SemanticVariant variant;
        Entry(String title, String subtitle, SemanticVariant variant) { this.title = title; this.subtitle = subtitle; this.variant = variant; }
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxTimeline}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxTimeline}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>entries — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxTimeline, Builder> {

        private final List<Entry> entries = new ArrayList<>();

        public Builder entry(String title, String subtitle, SemanticVariant variant) {
            entries.add(new Entry(title, subtitle, variant == null ? SemanticVariant.ACCENT : variant));
            return this;
        }

        @Override
        public FxTimeline build() {
            FxTimeline t = new FxTimeline();
            for (Entry e : entries) t.addEntry(e.title, e.subtitle, e.variant);
            applyBase(t);
            return t;
        }
    }
}
