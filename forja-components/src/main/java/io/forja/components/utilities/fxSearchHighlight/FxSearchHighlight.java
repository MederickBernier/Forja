package io.forja.components.utilities.fxSearchHighlight;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A {@link TextFlow} that renders a source string with occurrences of a
 * search query wrapped in bold {@link Text} nodes so matches stand out.
 *
 * <p>Matching is substring-based (not regex). {@link #isCaseSensitive} controls
 * case-fold behavior; the default is case-insensitive.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxSearchHighlight hl = FxSearchHighlight.builder()
 *          .text("Deploy the pipeline to production")
 *          .query("pipe")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxSearchHighlight extends TextFlow {

    private final StringProperty text = new SimpleStringProperty(this, "text", "");
    private final StringProperty query = new SimpleStringProperty(this, "query", "");
    private final BooleanProperty caseSensitive = new SimpleBooleanProperty(this, "caseSensitive", false);

    /**
     * Creates an empty {@code FxSearchHighlight}.
     */
    public FxSearchHighlight() {
        super();
        getStyleClass().add("forja-search-highlight");
        text.addListener((obs, o, v) -> refresh());
        query.addListener((obs, o, v) -> refresh());
        caseSensitive.addListener((obs, o, v) -> refresh());
        refresh();
    }

    private void refresh() {
        getChildren().clear();
        String src = getText() == null ? "" : getText();
        String q = getQuery() == null ? "" : getQuery();
        if (q.isEmpty() || src.isEmpty()) {
            Text plain = new Text(src);
            plain.getStyleClass().add("forja-search-highlight-plain");
            getChildren().add(plain);
            return;
        }
        String hay = isCaseSensitive() ? src : src.toLowerCase();
        String needle = isCaseSensitive() ? q : q.toLowerCase();
        int start = 0;
        int idx;
        while ((idx = hay.indexOf(needle, start)) >= 0) {
            if (idx > start) {
                Text pre = new Text(src.substring(start, idx));
                pre.getStyleClass().add("forja-search-highlight-plain");
                getChildren().add(pre);
            }
            Text hit = new Text(src.substring(idx, idx + needle.length()));
            hit.getStyleClass().add("forja-search-highlight-match");
            getChildren().add(hit);
            start = idx + needle.length();
        }
        if (start < src.length()) {
            Text tail = new Text(src.substring(start));
            tail.getStyleClass().add("forja-search-highlight-plain");
            getChildren().add(tail);
        }
    }

    /** Returns the source-text property. */
    public StringProperty textProperty() { return text; }

    /** Returns the current source text. */
    public String getText() { return text.get(); }

    /** Sets the source text. */
    public void setText(String v) { text.set(v == null ? "" : v); }

    /** Returns the query property. */
    public StringProperty queryProperty() { return query; }

    /** Returns the current query string. */
    public String getQuery() { return query.get(); }

    /** Sets the query string; empty clears highlighting. */
    public void setQuery(String v) { query.set(v == null ? "" : v); }

    /** Returns the case-sensitive property. */
    public BooleanProperty caseSensitiveProperty() { return caseSensitive; }

    /** Returns whether matching is case-sensitive. */
    public boolean isCaseSensitive() { return caseSensitive.get(); }

    /** Sets whether matching is case-sensitive. */
    public void setCaseSensitive(boolean v) { caseSensitive.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSearchHighlight}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxSearchHighlight}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text / query — empty</li>
     *   <li>caseSensitive — {@code false}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxSearchHighlight, Builder> {

        private String text = "";
        private String query = "";
        private boolean caseSensitive = false;

        public Builder text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder query(String query) { this.query = query == null ? "" : query; return this; }
        public Builder caseSensitive(boolean caseSensitive) { this.caseSensitive = caseSensitive; return this; }

        @Override
        public FxSearchHighlight build() {
            FxSearchHighlight h = new FxSearchHighlight();
            h.setCaseSensitive(caseSensitive);
            h.setText(text);
            h.setQuery(query);
            applyBase(h);
            return h;
        }
    }
}
