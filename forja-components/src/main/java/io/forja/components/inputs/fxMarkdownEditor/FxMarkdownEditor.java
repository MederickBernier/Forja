package io.forja.components.inputs.fxMarkdownEditor;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.inputs.fxCodeEditor.FxCodeEditor;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * A split source-editor + rendered-preview markdown editor.
 *
 * <p>{@code FxMarkdownEditor} shows a {@link FxCodeEditor} on the left and a
 * minimal preview on the right. The preview renders a compact subset of
 * markdown ({@code #} / {@code ##} / {@code ###} headings, blank-line
 * paragraphs, {@code -} bullets, and {@code **bold**} / {@code *italic*}
 * inline styling via {@link FxLabel} nodes). Users needing full CommonMark
 * should swap the {@code renderer} for a WebView or plug in {@code flexmark-java}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxMarkdownEditor md = FxMarkdownEditor.builder()
 *          .text("# Hello\n\nSome **bold** text.")
 *          .build();
 *     }
 * </pre>
 *
 * @see FxCodeEditor
 * @see Builder
 */
public class FxMarkdownEditor extends VBox {

    private final FxCodeEditor sourceEditor = FxCodeEditor.builder().showLineNumbers(false).build();
    private final VBox previewBox = new VBox();
    private final ScrollPane previewScroll = new ScrollPane(previewBox);
    private final SplitPane split = new SplitPane();

    private final StringProperty text = new SimpleStringProperty(this, "text", "");
    private boolean syncing = false;

    /**
     * Creates an empty {@code FxMarkdownEditor}.
     */
    public FxMarkdownEditor() {
        super();
        getStyleClass().add("forja-markdown-editor");
        previewBox.getStyleClass().add("forja-markdown-editor-preview");
        previewBox.setSpacing(6);
        previewBox.setPadding(new javafx.geometry.Insets(10));
        previewScroll.setFitToWidth(true);
        split.getItems().addAll(sourceEditor, previewScroll);
        split.setDividerPositions(0.5);
        getChildren().add(split);
        setFillWidth(true);
        VBox.setVgrow(split, javafx.scene.layout.Priority.ALWAYS);

        text.bindBidirectional(sourceEditor.textProperty());
        text.addListener((obs, o, v) -> refreshPreview());
        refreshPreview();
    }

    private void refreshPreview() {
        if (syncing) return;
        syncing = true;
        try {
            previewBox.getChildren().clear();
            String src = getText();
            if (src == null || src.isEmpty()) return;
            for (String block : src.split("\n\n")) {
                renderBlock(block);
            }
        } finally { syncing = false; }
    }

    private void renderBlock(String block) {
        String trimmed = block.trim();
        if (trimmed.isEmpty()) return;
        if (trimmed.startsWith("### ")) previewBox.getChildren().add(labelOf(trimmed.substring(4), LabelVariant.SUBHEADING));
        else if (trimmed.startsWith("## ")) previewBox.getChildren().add(labelOf(trimmed.substring(3), LabelVariant.HEADING));
        else if (trimmed.startsWith("# ")) previewBox.getChildren().add(labelOf(trimmed.substring(2), LabelVariant.DISPLAY));
        else if (trimmed.startsWith("- ")) {
            for (String line : trimmed.split("\n")) {
                if (line.startsWith("- ")) previewBox.getChildren().add(labelOf("• " + inlineStrip(line.substring(2)), LabelVariant.BODY));
            }
        } else {
            previewBox.getChildren().add(labelOf(inlineStrip(trimmed), LabelVariant.BODY));
        }
    }

    private static FxLabel labelOf(String text, LabelVariant variant) {
        FxLabel l = FxLabel.builder().text(text).variant(variant).build();
        l.setWrapText(true);
        return l;
    }

    // Very light inline markup strip — removes ** and * markers. Real
    // formatting is deferred to the source editor; preview is text-only.
    private static String inlineStrip(String s) {
        return s.replace("**", "").replace("*", "");
    }

    /** Returns the source editor. */
    public FxCodeEditor getSourceEditor() { return sourceEditor; }
    /** Returns the preview VBox. */
    public VBox getPreviewBox() { return previewBox; }
    /** Returns the split pane. */
    public SplitPane getSplit() { return split; }

    /** Returns the text property. */
    public StringProperty textProperty() { return text; }
    /** Returns the current markdown source. */
    public String getText() { return text.get(); }
    /** Sets the markdown source. */
    public void setText(String v) { text.set(v == null ? "" : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxMarkdownEditor}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxMarkdownEditor}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     *   <li>dividerPosition — {@code 0.5}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxMarkdownEditor, Builder> {

        private String text = "";
        private double dividerPosition = 0.5;

        public Builder text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder dividerPosition(double dividerPosition) { this.dividerPosition = dividerPosition; return this; }

        @Override
        public FxMarkdownEditor build() {
            FxMarkdownEditor md = new FxMarkdownEditor();
            md.getSplit().setDividerPositions(dividerPosition);
            md.setText(text);
            applyBase(md);
            return md;
        }
    }
}
