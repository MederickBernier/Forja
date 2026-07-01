package io.forja.components.inputs.fxJsonEditor;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.inputs.fxCodeEditor.FxCodeEditor;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A JSON-flavored {@link FxCodeEditor} — wraps a code editor preset with a
 * regex-driven JSON tokenizer that emits CSS classes {@code json-key},
 * {@code json-string}, {@code json-number}, {@code json-boolean},
 * {@code json-null}, {@code json-punctuation}.
 *
 * <p>Style the classes via `base.css` (defaults ship in Forja's stylesheet).
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxJsonEditor ed = FxJsonEditor.builder()
 *          .text("{\"name\":\"Forja\",\"version\":0.1}")
 *          .build();
 *     }
 * </pre>
 *
 * @see FxCodeEditor
 * @see Builder
 */
public class FxJsonEditor extends VBox {

    private static final Pattern JSON_PATTERN = Pattern.compile(
            "(?<KEY>\"(?:[^\"\\\\]|\\\\.)*\"(?=\\s*:))" +
            "|(?<STRING>\"(?:[^\"\\\\]|\\\\.)*\")" +
            "|(?<NUMBER>-?\\b\\d+(?:\\.\\d+)?(?:[eE][+-]?\\d+)?\\b)" +
            "|(?<BOOLEAN>\\btrue\\b|\\bfalse\\b)" +
            "|(?<NULL>\\bnull\\b)" +
            "|(?<PUNCTUATION>[{}\\[\\],:])");

    private final FxCodeEditor codeEditor;
    private final StringProperty text = new SimpleStringProperty(this, "text", "");

    /**
     * Creates an empty {@code FxJsonEditor}.
     */
    public FxJsonEditor() {
        super();
        getStyleClass().add("forja-json-editor");
        codeEditor = FxCodeEditor.builder().showLineNumbers(true).highlighter(FxJsonEditor::computeHighlight).build();
        codeEditor.getStyleClass().add("forja-json-editor-inner");
        getChildren().add(codeEditor);
        setFillWidth(true);
        VBox.setVgrow(codeEditor, javafx.scene.layout.Priority.ALWAYS);

        text.bindBidirectional(codeEditor.textProperty());
    }

    private static StyleSpans<Collection<String>> computeHighlight(String source) {
        Matcher m = JSON_PATTERN.matcher(source == null ? "" : source);
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        int last = 0;
        while (m.find()) {
            String styleClass =
                    m.group("KEY") != null ? "json-key"
                    : m.group("STRING") != null ? "json-string"
                    : m.group("NUMBER") != null ? "json-number"
                    : m.group("BOOLEAN") != null ? "json-boolean"
                    : m.group("NULL") != null ? "json-null"
                    : m.group("PUNCTUATION") != null ? "json-punctuation"
                    : null;
            if (styleClass == null) continue;
            spansBuilder.add(Collections.<String>emptyList(), m.start() - last);
            spansBuilder.add(Collections.singletonList(styleClass), m.end() - m.start());
            last = m.end();
        }
        int total = source == null ? 0 : source.length();
        spansBuilder.add(Collections.<String>emptyList(), Math.max(0, total - last));
        return spansBuilder.create();
    }

    /** Returns the underlying code editor. */
    public FxCodeEditor getCodeEditor() { return codeEditor; }

    /** Returns the text property. */
    public StringProperty textProperty() { return text; }
    /** Returns the current JSON source. */
    public String getText() { return text.get(); }
    /** Sets the JSON source. */
    public void setText(String v) { text.set(v == null ? "" : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxJsonEditor}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxJsonEditor}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxJsonEditor, Builder> {

        private String text = "";

        public Builder text(String text) { this.text = text == null ? "" : text; return this; }

        @Override
        public FxJsonEditor build() {
            FxJsonEditor e = new FxJsonEditor();
            e.setText(text);
            applyBase(e);
            return e;
        }
    }
}
