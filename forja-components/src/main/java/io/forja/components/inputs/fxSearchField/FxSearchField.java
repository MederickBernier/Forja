package io.forja.components.inputs.fxSearchField;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.inputs.InputVariant;
import io.forja.components.inputs.fxTextField.FxTextField;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.VBox;

/**
 * A search-flavored input: wraps an {@link FxTextField} preset with a leading
 * search icon and an optional clear-button that appears while the field has
 * text.
 *
 * <p>Delegates {@code text}, {@code promptText}, {@code variant},
 * {@code helperText}, and {@code errorText} to the wrapped {@link FxTextField}
 * (available via {@link #getInnerField}).
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxSearchField search = FxSearchField.builder()
 *          .promptText("Search users")
 *          .clearable(true)
 *          .onSearch(q -> viewModel.query(q))
 *          .build();
 *     }
 * </pre>
 *
 * @see FxTextField
 * @see Builder
 */
public class FxSearchField extends VBox {

    private static final String SEARCH_ICON_LITERAL = "fth-search";
    private static final String CLEAR_ICON_LITERAL = "fth-x";

    private final FxTextField inner = new FxTextField();
    private final FxIcon clearIcon = new FxIcon(CLEAR_ICON_LITERAL);
    private final BooleanProperty clearable = new SimpleBooleanProperty(this, "clearable", false);

    /**
     * Creates an empty {@code FxSearchField}.
     */
    public FxSearchField() {
        super();
        getStyleClass().add("forja-search-field");
        setSpacing(0);

        inner.setLeadingIcon(new FxIcon(SEARCH_ICON_LITERAL));
        clearIcon.getStyleClass().add("forja-search-field-clear");
        clearIcon.setOnMouseClicked(e -> { setText(""); e.consume(); });

        getChildren().add(inner);

        clearable.addListener((obs, o, v) -> refreshClearIcon());
        inner.textProperty().addListener((obs, o, v) -> refreshClearIcon());
        refreshClearIcon();
    }

    private void refreshClearIcon() {
        boolean show = isClearable() && getText() != null && !getText().isEmpty();
        inner.setTrailingIcon(show ? clearIcon : null);
    }

    /** Returns the wrapped {@link FxTextField}. */
    public FxTextField getInnerField() { return inner; }

    /** Returns the clear-icon node. */
    public FxIcon getClearIcon() { return clearIcon; }

    /** Returns the text property. */
    public StringProperty textProperty() { return inner.textProperty(); }

    /** Returns the current text. */
    public String getText() { return inner.getText(); }

    /** Sets the text. */
    public void setText(String v) { inner.setText(v); }

    /** Returns the prompt-text property. */
    public StringProperty promptTextProperty() { return inner.promptTextProperty(); }

    /** Returns the current prompt text. */
    public String getPromptText() { return inner.getPromptText(); }

    /** Sets the prompt text. */
    public void setPromptText(String v) { inner.setPromptText(v); }

    /** Returns the helper-text property. */
    public StringProperty helperTextProperty() { return inner.helperTextProperty(); }

    /** Returns the current helper text. */
    public String getHelperText() { return inner.getHelperText(); }

    /** Sets the helper text. */
    public void setHelperText(String v) { inner.setHelperText(v); }

    /** Returns the error-text property. */
    public StringProperty errorTextProperty() { return inner.errorTextProperty(); }

    /** Returns the current error text. */
    public String getErrorText() { return inner.getErrorText(); }

    /** Sets the error text. */
    public void setErrorText(String v) { inner.setErrorText(v); }

    /** Returns the current variant. */
    public InputVariant getVariant() { return inner.getVariant(); }

    /** Sets the variant. */
    public void setVariant(InputVariant v) { inner.setVariant(v); }

    /** Returns the clearable property. */
    public BooleanProperty clearableProperty() { return clearable; }

    /** Returns whether the clear-icon shows when text is present. */
    public boolean isClearable() { return clearable.get(); }

    /** Sets whether the clear-icon shows when text is present. */
    public void setClearable(boolean v) { clearable.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSearchField}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when the user presses ENTER on the search field. */
    @FunctionalInterface
    public interface OnSearch {
        void accept(String query);
    }

    /**
     * Fluent builder for constructing an {@link FxSearchField}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     *   <li>promptText — {@code "Search"}</li>
     *   <li>clearable — {@code false}</li>
     *   <li>variant — {@link InputVariant#DEFAULT}</li>
     *   <li>helperText / errorText — empty</li>
     *   <li>editable — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxSearchField, Builder> {

        private String text = "";
        private String promptText = "Search";
        private InputVariant variant = InputVariant.DEFAULT;
        private String helperText = "";
        private String errorText = "";
        private boolean clearable = false;
        private boolean editable = true;
        private OnSearch onSearch;

        public Builder text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder promptText(String promptText) { this.promptText = promptText == null ? "" : promptText; return this; }
        public Builder variant(InputVariant variant) { this.variant = variant; return this; }
        public Builder helperText(String helperText) { this.helperText = helperText == null ? "" : helperText; return this; }
        public Builder errorText(String errorText) { this.errorText = errorText == null ? "" : errorText; return this; }
        public Builder clearable(boolean clearable) { this.clearable = clearable; return this; }
        public Builder editable(boolean editable) { this.editable = editable; return this; }
        public Builder onSearch(OnSearch onSearch) { this.onSearch = onSearch; return this; }

        @Override
        public FxSearchField build() {
            FxSearchField f = new FxSearchField();
            f.setPromptText(promptText);
            f.setHelperText(helperText);
            f.setClearable(clearable);
            f.setVariant(variant);
            f.setText(text);
            f.setErrorText(errorText);
            f.getInnerField().getTextField().setEditable(editable);
            if (onSearch != null) {
                f.getInnerField().getTextField().setOnAction(e -> onSearch.accept(f.getText()));
            }
            applyBase(f);
            return f;
        }
    }
}
