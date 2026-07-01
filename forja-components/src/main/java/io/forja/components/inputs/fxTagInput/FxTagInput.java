package io.forja.components.inputs.fxTagInput;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.feedbackAndStatus.fxChip.FxChip;
import io.forja.components.inputs.InputVariant;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A tag entry field — a wrapping row of {@link FxChip} tags plus an inline
 * {@link TextField} for adding new ones.
 *
 * <p>Type text, press {@code ENTER} to commit as a tag. Press {@code BACKSPACE}
 * on an empty field to remove the last tag. Click the × on any chip to
 * remove it.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxTagInput tags = FxTagInput.builder()
 *          .tags("java", "javafx")
 *          .promptText("Add tag…")
 *          .onChange(list -> viewModel.setTags(list))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxTagInput extends VBox {

    private static final PseudoClass DEFAULT_PC = PseudoClass.getPseudoClass("default");
    private static final PseudoClass ERROR_PC   = PseudoClass.getPseudoClass("error");
    private static final PseudoClass SUCCESS_PC = PseudoClass.getPseudoClass("success");
    private static final PseudoClass FOCUSED_PC = PseudoClass.getPseudoClass("focused");

    private final FlowPane row = new FlowPane();
    private final TextField editor = new TextField();
    private final FxLabel helperLabel = new FxLabel("", LabelVariant.SMALL);

    private final ObservableList<String> tags = FXCollections.observableArrayList();
    private final ObjectProperty<InputVariant> variant = new SimpleObjectProperty<>(this, "variant", InputVariant.DEFAULT);
    private final StringProperty helperText = new SimpleStringProperty(this, "helperText", "");
    private final StringProperty errorText = new SimpleStringProperty(this, "errorText", "");
    private OnChange onChange;

    /**
     * Creates an empty {@code FxTagInput}.
     */
    public FxTagInput() {
        super();
        getStyleClass().add("forja-tag-input");
        setSpacing(4);

        row.getStyleClass().add("forja-tag-input-row");
        row.setHgap(4);
        row.setVgap(4);
        row.setAlignment(Pos.CENTER_LEFT);
        editor.getStyleClass().add("forja-tag-input-editor");
        editor.setPrefColumnCount(8);

        helperLabel.getStyleClass().add("forja-tag-input-helper");
        helperLabel.setMuted(true);
        helperLabel.setVisible(false);
        helperLabel.setManaged(false);

        row.getChildren().add(editor);
        getChildren().addAll(row, helperLabel);

        tags.addListener((javafx.collections.ListChangeListener<String>) c -> {
            rebuildChips();
            if (onChange != null) onChange.accept(Collections.unmodifiableList(new ArrayList<>(tags)));
        });
        variant.addListener((obs, o, v) -> applyVariantPseudoClass());
        helperText.addListener((obs, o, v) -> refreshHelper());
        errorText.addListener((obs, o, v) -> {
            if (v != null && !v.isEmpty()) setVariant(InputVariant.ERROR);
            refreshHelper();
        });

        editor.focusedProperty().addListener((obs, o, v) -> pseudoClassStateChanged(FOCUSED_PC, v));
        editor.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) commitEditor();
            else if (e.getCode() == KeyCode.BACK_SPACE && editor.getText().isEmpty() && !tags.isEmpty()) {
                tags.remove(tags.size() - 1);
            }
        });

        applyVariantPseudoClass();
        refreshHelper();
    }

    private void commitEditor() {
        String t = editor.getText();
        if (t == null) return;
        t = t.trim();
        if (t.isEmpty()) return;
        if (!tags.contains(t)) tags.add(t);
        editor.clear();
    }

    private void rebuildChips() {
        row.getChildren().clear();
        for (final String t : tags) {
            FxChip chip = FxChip.builder().text(t).removable(true).build();
            chip.setOnClose(e -> tags.remove(t));
            row.getChildren().add(chip);
        }
        row.getChildren().add(editor);
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
        if (hasError) helperLabel.setText(err);
        else if (hasHelper) helperLabel.setText(help);
        else helperLabel.setText("");
        boolean vis = hasError || hasHelper;
        helperLabel.setVisible(vis);
        helperLabel.setManaged(vis);
        helperLabel.setMuted(!hasError);
    }

    /** Returns the tags list. */
    public ObservableList<String> getTags() { return tags; }

    /** Returns the editor {@link TextField}. */
    public TextField getEditor() { return editor; }

    /** Returns the row {@link FlowPane}. */
    public FlowPane getRow() { return row; }

    /** Returns the helper label. */
    public FxLabel getHelperLabel() { return helperLabel; }

    /** Returns the variant property. */
    public ObjectProperty<InputVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public InputVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(InputVariant v) { variant.set(v); }

    /** Returns the prompt-text property (delegates to the editor). */
    public StringProperty promptTextProperty() { return editor.promptTextProperty(); }

    /** Returns the current prompt text. */
    public String getPromptText() { return editor.getPromptText(); }

    /** Sets the prompt text. */
    public void setPromptText(String v) { editor.setPromptText(v); }

    /** Returns the helper-text property. */
    public StringProperty helperTextProperty() { return helperText; }

    /** Returns the current helper text. */
    public String getHelperText() { return helperText.get(); }

    /** Sets the helper text. */
    public void setHelperText(String v) { helperText.set(v == null ? "" : v); }

    /** Returns the error-text property. */
    public StringProperty errorTextProperty() { return errorText; }

    /** Returns the current error text. */
    public String getErrorText() { return errorText.get(); }

    /** Sets the error text. */
    public void setErrorText(String v) { errorText.set(v == null ? "" : v); }

    /** Sets the change callback. */
    public void setOnChange(OnChange onChange) { this.onChange = onChange; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxTagInput}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when the tags list changes. */
    @FunctionalInterface
    public interface OnChange { void accept(List<String> tags); }

    /**
     * Fluent builder for constructing an {@link FxTagInput}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>tags — empty</li>
     *   <li>promptText — empty</li>
     *   <li>variant — {@link InputVariant#DEFAULT}</li>
     *   <li>helperText / errorText — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxTagInput, Builder> {

        private List<String> tags = Collections.emptyList();
        private String promptText = "";
        private InputVariant variant = InputVariant.DEFAULT;
        private String helperText = "";
        private String errorText = "";
        private OnChange onChange;

        public Builder tags(List<String> tags) { this.tags = tags == null ? Collections.<String>emptyList() : tags; return this; }
        public Builder tags(String... tags) { return tags(tags == null ? Collections.<String>emptyList() : Arrays.asList(tags)); }
        public Builder promptText(String promptText) { this.promptText = promptText == null ? "" : promptText; return this; }
        public Builder variant(InputVariant variant) { this.variant = variant; return this; }
        public Builder helperText(String helperText) { this.helperText = helperText == null ? "" : helperText; return this; }
        public Builder errorText(String errorText) { this.errorText = errorText == null ? "" : errorText; return this; }
        public Builder onChange(OnChange onChange) { this.onChange = onChange; return this; }

        @Override
        public FxTagInput build() {
            FxTagInput t = new FxTagInput();
            t.setPromptText(promptText);
            t.setVariant(variant);
            t.setHelperText(helperText);
            t.setErrorText(errorText);
            t.setOnChange(onChange);
            t.getTags().setAll(tags);
            applyBase(t);
            return t;
        }
    }
}
