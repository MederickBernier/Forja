package io.forja.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.VBox;

/**
 * A styled blockquote built as a composite {@link VBox}.
 *
 * <p>{@code FxBlockquote} renders a quoted passage (via {@link FxText}) with a
 * left accent border and optional citation line (via {@link FxLabel}) below
 * the quote. The cite line is hidden when no cite text is set.
 *
 * <p>The preferred way to construct an {@code FxBlockquote} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxBlockquote q = FxBlockquote.builder()
 *          .quote("Shape what already works.")
 *          .cite("Forja motto")
 *          .maxWidth(640)
 *          .build();
 *     }
 * </pre>
 *
 * <p>Unlike {@link FxLabel} and {@link FxText}, {@code FxBlockquote} extends
 * {@link VBox} rather than a JavaFX {@link javafx.scene.control.Control}. The
 * builder is standalone — it does not extend
 * {@link io.forja.builder.FxComponentBuilder}, which requires a Control bound.
 *
 * @see Builder
 */
public class FxBlockquote extends VBox {

    private final StringProperty quote = new SimpleStringProperty(this, "quote", "");
    private final StringProperty cite  = new SimpleStringProperty(this, "cite", null);

    private final FxText quoteNode;
    private final FxLabel citeNode;

    /**
     * Creates an empty {@code FxBlockquote}.
     */
    public FxBlockquote() {
        super();
        getStyleClass().add("forja-blockquote");

        quoteNode = new FxText();
        quoteNode.getStyleClass().add("forja-blockquote-quote");

        citeNode = new FxLabel("", LabelVariant.SMALL);
        citeNode.setMuted(true);
        citeNode.getStyleClass().add("forja-blockquote-cite");
        citeNode.setVisible(false);
        citeNode.setManaged(false);

        getChildren().addAll(quoteNode, citeNode);

        quoteNode.textProperty().bind(quote);
        cite.addListener((obs, old, val) -> refreshCite());
        refreshCite();
    }

    /**
     * Creates an {@code FxBlockquote} with the given quote text and no cite.
     *
     * @param quote the quoted passage
     */
    public FxBlockquote(String quote) {
        this();
        setQuote(quote);
    }

    /**
     * Creates an {@code FxBlockquote} with the given quote text and cite.
     *
     * @param quote the quoted passage
     * @param cite the citation line (e.g. {@code "— Author"})
     */
    public FxBlockquote(String quote, String cite) {
        this();
        setQuote(quote);
        setCite(cite);
    }

    private void refreshCite() {
        String value = getCite();
        boolean hasCite = value != null && !value.isEmpty();
        citeNode.setText(hasCite ? value : "");
        citeNode.setVisible(hasCite);
        citeNode.setManaged(hasCite);
    }

    /** Returns the quote property. */
    public StringProperty quoteProperty() { return quote; }

    /** Returns the current quote text. */
    public String getQuote() { return quote.get(); }

    /** Sets the quote text. */
    public void setQuote(String v) { quote.set(v == null ? "" : v); }

    /** Returns the cite property (nullable). */
    public StringProperty citeProperty() { return cite; }

    /** Returns the current cite text, or {@code null} if none. */
    public String getCite() { return cite.get(); }

    /** Sets the cite text. Pass {@code null} or empty to hide the cite line. */
    public void setCite(String v) { cite.set(v); }

    /**
     * Caps the rendered width of the inner quote text. Pairs well with
     * blockquotes used for long-form readability.
     */
    public void setMaxQuoteWidth(double maxWidth) {
        quoteNode.setMaxWidth(maxWidth);
    }

    /** Returns the internal quote text node — for advanced styling cases. */
    public FxText getQuoteNode() { return quoteNode; }

    /** Returns the internal cite label node — for advanced styling cases. */
    public FxLabel getCiteNode() { return citeNode; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxBlockquote}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxBlockquote}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>quote — empty string</li>
     *   <li>cite — {@code null} (cite line hidden)</li>
     *   <li>maxWidth — unbounded</li>
     * </ul>
     *
     * <p>This builder does not extend
     * {@link io.forja.builder.FxComponentBuilder} since {@link FxBlockquote}
     * is a {@link VBox}, not a {@link javafx.scene.control.Control}. The
     * available shared properties are {@code id}, {@code visible},
     * {@code styleClass}, and {@code userData}.
     */
    public static class Builder {

        private String quote = "";
        private String cite;
        private Double maxWidth;
        private String id;
        private boolean visible = true;
        private final java.util.List<String> styleClasses = new java.util.ArrayList<>();
        private Object userData;

        public Builder quote(String quote) {
            this.quote = quote;
            return this;
        }

        public Builder cite(String cite) {
            this.cite = cite;
            return this;
        }

        public Builder maxWidth(double maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder styleClass(String... classes) {
            for (String c : classes) {
                if (c != null && !c.isEmpty()) {
                    styleClasses.add(c);
                }
            }
            return this;
        }

        public Builder userData(Object userData) {
            this.userData = userData;
            return this;
        }

        public FxBlockquote build() {
            FxBlockquote blockquote = new FxBlockquote(quote, cite);
            if (maxWidth != null) {
                blockquote.setMaxQuoteWidth(maxWidth);
                blockquote.setMaxWidth(maxWidth);
            }
            if (id != null) {
                blockquote.setId(id);
            }
            blockquote.setVisible(visible);
            if (!styleClasses.isEmpty()) {
                blockquote.getStyleClass().addAll(styleClasses);
            }
            if (userData != null) {
                blockquote.setUserData(userData);
            }
            return blockquote;
        }
    }
}
