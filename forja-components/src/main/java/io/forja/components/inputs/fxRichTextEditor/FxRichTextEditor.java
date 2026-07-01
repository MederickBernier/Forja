package io.forja.components.inputs.fxRichTextEditor;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxIconButton.FxIconButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.IndexRange;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.InlineCssTextArea;

/**
 * A styled rich-text editor built on RichTextFX {@link InlineCssTextArea}
 * with a bold/italic/underline toolbar.
 *
 * <p>Toolbar buttons apply inline CSS (font-weight, font-style,
 * text-decoration) to the current selection. Add more via
 * {@link #getToolbar}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxRichTextEditor ed = FxRichTextEditor.builder()
 *          .text("Type here…")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxRichTextEditor extends VBox {

    private final HBox toolbar = new HBox();
    private final InlineCssTextArea textArea = new InlineCssTextArea();
    private final FxIconButton boldBtn = FxIconButton.builder().icon("fth-bold").variant(ButtonVariant.GHOST).build();
    private final FxIconButton italicBtn = FxIconButton.builder().icon("fth-italic").variant(ButtonVariant.GHOST).build();
    private final FxIconButton underlineBtn = FxIconButton.builder().icon("fth-underline").variant(ButtonVariant.GHOST).build();

    private final StringProperty text = new SimpleStringProperty(this, "text", "");
    private boolean syncing = false;

    /**
     * Creates an empty {@code FxRichTextEditor}.
     */
    public FxRichTextEditor() {
        super();
        getStyleClass().add("forja-rich-text-editor");
        toolbar.getStyleClass().add("forja-rich-text-editor-toolbar");
        toolbar.setSpacing(4);
        toolbar.getChildren().addAll(boldBtn, italicBtn, underlineBtn);
        textArea.getStyleClass().add("forja-rich-text-editor-inner");
        VBox.setVgrow(textArea, Priority.ALWAYS);
        getChildren().addAll(toolbar, textArea);

        boldBtn.setOnAction(e -> applyStyle("-fx-font-weight: bold;"));
        italicBtn.setOnAction(e -> applyStyle("-fx-font-style: italic;"));
        underlineBtn.setOnAction(e -> applyStyle("-fx-underline: true;"));

        textArea.textProperty().addListener((obs, o, v) -> {
            if (syncing) return;
            syncing = true;
            try { text.set(v == null ? "" : v); } finally { syncing = false; }
        });
        text.addListener((obs, o, v) -> {
            if (syncing) return;
            syncing = true;
            try { textArea.replaceText(v == null ? "" : v); } finally { syncing = false; }
        });
    }

    private void applyStyle(String css) {
        IndexRange sel = textArea.getSelection();
        if (sel == null || sel.getLength() == 0) return;
        textArea.setStyle(sel.getStart(), sel.getEnd(), css);
    }

    /** Returns the underlying {@link InlineCssTextArea}. */
    public InlineCssTextArea getTextArea() { return textArea; }

    /** Returns the toolbar HBox — append custom buttons via {@code getChildren()}. */
    public HBox getToolbar() { return toolbar; }

    /** Returns the bold button. */
    public FxIconButton getBoldButton() { return boldBtn; }
    /** Returns the italic button. */
    public FxIconButton getItalicButton() { return italicBtn; }
    /** Returns the underline button. */
    public FxIconButton getUnderlineButton() { return underlineBtn; }

    /** Returns the text property. */
    public StringProperty textProperty() { return text; }
    /** Returns the current plain text. */
    public String getText() { return text.get(); }
    /** Sets the plain text (styling cleared). */
    public void setText(String v) { text.set(v == null ? "" : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxRichTextEditor}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxRichTextEditor}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     *   <li>editable — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxRichTextEditor, Builder> {

        private String text = "";
        private boolean editable = true;

        public Builder text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder editable(boolean editable) { this.editable = editable; return this; }

        @Override
        public FxRichTextEditor build() {
            FxRichTextEditor e = new FxRichTextEditor();
            e.getTextArea().setEditable(editable);
            e.setText(text);
            applyBase(e);
            return e;
        }
    }
}
