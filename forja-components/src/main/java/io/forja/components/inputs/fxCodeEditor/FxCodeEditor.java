package io.forja.components.inputs.fxCodeEditor;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;

import java.util.function.Function;

/**
 * A styled code editor built on RichTextFX {@link CodeArea}. Adds Forja
 * styling, an optional syntax highlighter hook, and a fluent builder.
 *
 * <p>Provide a highlighter as a {@code Function<String, StyleSpans<Collection<String>>>}
 * (RichTextFX pattern) via {@link #setHighlighter}; the editor re-tokenizes
 * on text change.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxCodeEditor ed = FxCodeEditor.builder()
 *          .text("int x = 42;")
 *          .showLineNumbers(true)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxCodeEditor extends VBox {

    private final CodeArea codeArea = new CodeArea();
    private final StringProperty text = new SimpleStringProperty(this, "text", "");
    private Function<String, StyleSpans<java.util.Collection<String>>> highlighter;
    private boolean syncing = false;

    /**
     * Creates an empty {@code FxCodeEditor}.
     */
    public FxCodeEditor() {
        super();
        getStyleClass().add("forja-code-editor");
        codeArea.getStyleClass().add("forja-code-editor-inner");
        getChildren().add(codeArea);
        VBox.setVgrow(codeArea, javafx.scene.layout.Priority.ALWAYS);

        codeArea.textProperty().addListener((obs, o, v) -> {
            if (syncing) return;
            syncing = true;
            try { text.set(v == null ? "" : v); } finally { syncing = false; }
            applyHighlight();
        });
        text.addListener((obs, o, v) -> {
            if (syncing) return;
            syncing = true;
            try { codeArea.replaceText(v == null ? "" : v); } finally { syncing = false; }
        });
    }

    private void applyHighlight() {
        if (highlighter == null) return;
        String src = codeArea.getText();
        if (src == null) return;
        try {
            StyleSpans<java.util.Collection<String>> spans = highlighter.apply(src);
            if (spans != null) codeArea.setStyleSpans(0, spans);
        } catch (Exception ignored) {}
    }

    /** Returns the underlying {@link CodeArea}. */
    public CodeArea getCodeArea() { return codeArea; }

    /** Returns the text property. */
    public StringProperty textProperty() { return text; }
    /** Returns the current text. */
    public String getText() { return text.get(); }
    /** Sets the text. */
    public void setText(String v) { text.set(v == null ? "" : v); }

    /** Toggles the line-number gutter. */
    public void setShowLineNumbers(boolean v) {
        codeArea.setParagraphGraphicFactory(v ? LineNumberFactory.get(codeArea) : null);
    }

    /** Sets the syntax highlighter callback. */
    public void setHighlighter(Function<String, StyleSpans<java.util.Collection<String>>> highlighter) {
        this.highlighter = highlighter;
        applyHighlight();
    }

    /** Returns the highlighter. */
    public Function<String, StyleSpans<java.util.Collection<String>>> getHighlighter() { return highlighter; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxCodeEditor}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxCodeEditor}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     *   <li>showLineNumbers — {@code true}</li>
     *   <li>editable — {@code true}</li>
     *   <li>highlighter — none</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxCodeEditor, Builder> {

        private String text = "";
        private boolean showLineNumbers = true;
        private boolean editable = true;
        private Function<String, StyleSpans<java.util.Collection<String>>> highlighter;

        public Builder text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder showLineNumbers(boolean showLineNumbers) { this.showLineNumbers = showLineNumbers; return this; }
        public Builder editable(boolean editable) { this.editable = editable; return this; }
        public Builder highlighter(Function<String, StyleSpans<java.util.Collection<String>>> highlighter) { this.highlighter = highlighter; return this; }

        @Override
        public FxCodeEditor build() {
            FxCodeEditor e = new FxCodeEditor();
            e.setShowLineNumbers(showLineNumbers);
            e.getCodeArea().setEditable(editable);
            e.setHighlighter(highlighter);
            e.setText(text);
            applyBase(e);
            return e;
        }
    }
}
