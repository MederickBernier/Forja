package io.forja.components.inputs.fxTextArea;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * A styled multi-line text input with validation-state variants and an
 * optional auto-resize behavior.
 *
 * <p>{@code FxTextArea} is a composite {@link VBox} of two children: the
 * inner {@link TextArea} wrapped in a framed style class, and a helper/error
 * {@link FxLabel} that auto-hides when both messages are empty.
 *
 * <p>The inner {@link TextArea} is accessible via {@link #getTextArea} for
 * advanced binding. {@code text} and {@code promptText} on {@code FxTextArea}
 * delegate directly to it.
 *
 * <p>When {@link #setAutoResize} is {@code true}, {@code prefRowCount} updates
 * on text change to fit the line count, clamped between {@link #getRows} and
 * {@link #getMaxRows}. {@code wrapText} is forced on so wrapped long lines
 * stay visible (wrapped lines do not count toward the resize calculation —
 * it is line-feed based).
 *
 * <p>The preferred way to construct an {@code FxTextArea} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxTextArea bio = FxTextArea.builder()
 *          .promptText("Tell us about yourself")
 *          .rows(4)
 *          .maxRows(12)
 *          .autoResize(true)
 *          .helperText("Max 500 characters.")
 *          .build();
 *     }
 * </pre>
 *
 * @see TextAreaVariant
 * @see Builder
 */
public class FxTextArea extends VBox {

    private static final PseudoClass DEFAULT_PC = PseudoClass.getPseudoClass("default");
    private static final PseudoClass ERROR_PC   = PseudoClass.getPseudoClass("error");
    private static final PseudoClass SUCCESS_PC = PseudoClass.getPseudoClass("success");
    private static final PseudoClass FOCUSED_PC = PseudoClass.getPseudoClass("focused");

    private final TextArea textArea = new TextArea();
    private final FxLabel helperLabel = new FxLabel("", LabelVariant.SMALL);

    private final ObjectProperty<TextAreaVariant> variant = new SimpleObjectProperty<>(this, "variant", TextAreaVariant.DEFAULT);
    private final StringProperty helperText = new SimpleStringProperty(this, "helperText", "");
    private final StringProperty errorText = new SimpleStringProperty(this, "errorText", "");
    private final BooleanProperty autoResize = new SimpleBooleanProperty(this, "autoResize", false);
    private final IntegerProperty rows = new SimpleIntegerProperty(this, "rows", 4);
    private final IntegerProperty maxRows = new SimpleIntegerProperty(this, "maxRows", 12);
    private final BooleanProperty showingError = new SimpleBooleanProperty(this, "showingError", false);

    /**
     * Creates an empty {@code FxTextArea} with the default variant and 4 rows.
     */
    public FxTextArea() {
        super();
        getStyleClass().add("forja-text-area");
        setSpacing(4);

        textArea.getStyleClass().add("forja-text-area-inner");
        textArea.setPrefRowCount(rows.get());

        helperLabel.getStyleClass().add("forja-text-area-helper");
        helperLabel.setMuted(true);
        helperLabel.setVisible(false);
        helperLabel.setManaged(false);

        getChildren().addAll(textArea, helperLabel);

        variant.addListener((obs, old, val) -> applyVariantPseudoClass());
        helperText.addListener((obs, old, val) -> refreshHelper());
        errorText.addListener((obs, old, val) -> {
            String e = val == null ? "" : val;
            if (!e.isEmpty()) setVariant(TextAreaVariant.ERROR);
            refreshHelper();
        });
        autoResize.addListener((obs, old, val) -> {
            if (val) {
                textArea.setWrapText(true);
                applyAutoResize();
            }
        });
        rows.addListener((obs, old, val) -> {
            textArea.setPrefRowCount(val.intValue());
            applyAutoResize();
        });
        maxRows.addListener((obs, old, val) -> applyAutoResize());
        textArea.textProperty().addListener((obs, old, val) -> applyAutoResize());
        textArea.focusedProperty().addListener((obs, old, val) ->
                pseudoClassStateChanged(FOCUSED_PC, val));

        applyVariantPseudoClass();
        refreshHelper();
    }

    /**
     * Creates an {@code FxTextArea} with the given prompt text.
     *
     * @param promptText placeholder shown when the area is empty
     */
    public FxTextArea(String promptText) {
        this();
        setPromptText(promptText);
    }

    private void applyVariantPseudoClass() {
        pseudoClassStateChanged(DEFAULT_PC, false);
        pseudoClassStateChanged(ERROR_PC,   false);
        pseudoClassStateChanged(SUCCESS_PC, false);

        switch (getVariant()) {
            case DEFAULT: pseudoClassStateChanged(DEFAULT_PC, true); break;
            case ERROR:   pseudoClassStateChanged(ERROR_PC,   true); break;
            case SUCCESS: pseudoClassStateChanged(SUCCESS_PC, true); break;
        }
    }

    private void refreshHelper() {
        String err = getErrorText();
        String help = getHelperText();
        boolean hasError = err != null && !err.isEmpty();
        boolean hasHelper = help != null && !help.isEmpty();
        if (hasError) {
            helperLabel.setText(err);
            helperLabel.setMuted(false);
            if (!helperLabel.getStyleClass().contains("forja-text-area-error")) {
                helperLabel.getStyleClass().add("forja-text-area-error");
            }
            showingError.set(true);
        } else if (hasHelper) {
            helperLabel.setText(help);
            helperLabel.setMuted(true);
            helperLabel.getStyleClass().remove("forja-text-area-error");
            showingError.set(false);
        } else {
            helperLabel.setText("");
            showingError.set(false);
        }
        boolean visible = hasError || hasHelper;
        helperLabel.setVisible(visible);
        helperLabel.setManaged(visible);
    }

    private void applyAutoResize() {
        if (!isAutoResize()) return;
        String t = textArea.getText();
        int lines = (t == null || t.isEmpty()) ? 1 : (t.split("\n", -1).length);
        int minR = Math.max(1, getRows());
        int maxR = Math.max(minR, getMaxRows());
        int target = Math.min(maxR, Math.max(minR, lines));
        textArea.setPrefRowCount(target);
    }

    /** Returns the underlying {@link TextArea} for advanced binding. */
    public TextArea getTextArea() { return textArea; }

    /** Returns the helper/error label node — for advanced styling. */
    public FxLabel getHelperLabel() { return helperLabel; }

    /** Returns the inner text property (delegates to the inner TextArea). */
    public StringProperty textProperty() { return textArea.textProperty(); }

    /** Returns the current text. */
    public String getText() { return textArea.getText(); }

    /** Sets the text. */
    public void setText(String v) { textArea.setText(v); }

    /** Returns the prompt-text property (delegates to the inner TextArea). */
    public StringProperty promptTextProperty() { return textArea.promptTextProperty(); }

    /** Returns the current prompt text. */
    public String getPromptText() { return textArea.getPromptText(); }

    /** Sets the prompt text. */
    public void setPromptText(String v) { textArea.setPromptText(v); }

    /** Returns the variant property. */
    public ObjectProperty<TextAreaVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public TextAreaVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(TextAreaVariant v) { variant.set(v); }

    /** Returns the helper-text property. */
    public StringProperty helperTextProperty() { return helperText; }

    /** Returns the current helper text. */
    public String getHelperText() { return helperText.get(); }

    /** Sets the helper text. Empty/null hides the helper line. */
    public void setHelperText(String v) { helperText.set(v == null ? "" : v); }

    /** Returns the error-text property. */
    public StringProperty errorTextProperty() { return errorText; }

    /** Returns the current error text. */
    public String getErrorText() { return errorText.get(); }

    /**
     * Sets the error text. Non-empty value auto-flips the variant to
     * {@link TextAreaVariant#ERROR}; clearing it does not auto-revert.
     */
    public void setErrorText(String v) { errorText.set(v == null ? "" : v); }

    /** Returns the auto-resize property. */
    public BooleanProperty autoResizeProperty() { return autoResize; }

    /** Returns whether auto-resize is enabled. */
    public boolean isAutoResize() { return autoResize.get(); }

    /**
     * Sets whether the area grows/shrinks with its content (based on line-feed
     * count, clamped to {@link #getRows} and {@link #getMaxRows}).
     */
    public void setAutoResize(boolean v) { autoResize.set(v); }

    /** Returns the minimum-rows property (default 4). */
    public IntegerProperty rowsProperty() { return rows; }

    /** Returns the current minimum row count. */
    public int getRows() { return rows.get(); }

    /** Sets the minimum row count (the floor for auto-resize). */
    public void setRows(int v) { rows.set(v); }

    /** Returns the maximum-rows property (default 12). */
    public IntegerProperty maxRowsProperty() { return maxRows; }

    /** Returns the current maximum row count. */
    public int getMaxRows() { return maxRows.get(); }

    /** Sets the maximum row count (the ceiling for auto-resize). */
    public void setMaxRows(int v) { maxRows.set(v); }

    /** Returns whether the helper label is currently showing the error text. */
    public boolean isShowingError() { return showingError.get(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxTextArea}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxTextArea}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     *   <li>promptText — empty</li>
     *   <li>variant — {@link TextAreaVariant#DEFAULT}</li>
     *   <li>rows / maxRows — 4 / 12</li>
     *   <li>autoResize — {@code false}</li>
     *   <li>helperText / errorText — empty (line hidden)</li>
     *   <li>wrapText — {@code true} (forced on if autoResize=true)</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxTextArea, Builder> {

        private String text = "";
        private String promptText = "";
        private TextAreaVariant variant = TextAreaVariant.DEFAULT;
        private String helperText = "";
        private String errorText = "";
        private boolean autoResize = false;
        private int rows = 4;
        private int maxRows = 12;
        private boolean wrapText = true;
        private boolean editable = true;

        public Builder text(String text) {
            this.text = text == null ? "" : text;
            return this;
        }

        public Builder promptText(String promptText) {
            this.promptText = promptText == null ? "" : promptText;
            return this;
        }

        public Builder variant(TextAreaVariant variant) {
            this.variant = variant;
            return this;
        }

        public Builder helperText(String helperText) {
            this.helperText = helperText == null ? "" : helperText;
            return this;
        }

        public Builder errorText(String errorText) {
            this.errorText = errorText == null ? "" : errorText;
            return this;
        }

        public Builder autoResize(boolean autoResize) {
            this.autoResize = autoResize;
            return this;
        }

        public Builder rows(int rows) {
            this.rows = rows;
            return this;
        }

        public Builder maxRows(int maxRows) {
            this.maxRows = maxRows;
            return this;
        }

        public Builder wrapText(boolean wrapText) {
            this.wrapText = wrapText;
            return this;
        }

        public Builder editable(boolean editable) {
            this.editable = editable;
            return this;
        }

        @Override
        public FxTextArea build() {
            FxTextArea area = new FxTextArea();
            area.setRows(rows);
            area.setMaxRows(maxRows);
            area.getTextArea().setWrapText(wrapText);
            area.getTextArea().setEditable(editable);
            area.setPromptText(promptText);
            area.setHelperText(helperText);
            // Apply variant first, then errorText so auto-flip wins when
            // a non-empty errorText is supplied. Apply autoResize last so it
            // reads the final text length when computing prefRowCount.
            area.setVariant(variant);
            area.setText(text);
            area.setErrorText(errorText);
            area.setAutoResize(autoResize);
            applyBase(area);
            return area;
        }
    }
}
