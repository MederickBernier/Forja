package io.forja.components.validation.fxForm;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.validation.fxErrorSummary.FxErrorSummary;
import io.forja.components.validation.fxFormField.FxFormField;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A declarative form container: a vertical stack of {@link FxFormField}s
 * with an optional {@link FxErrorSummary} at the top and helpers to run
 * validation across all fields.
 *
 * <p>{@link #validate()} runs every field's validator, populates each
 * field's error, and (when {@link #getErrorSummary} is present) fills the
 * summary with all currently-invalid messages.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxForm form = FxForm.builder()
 *          .field(emailField)
 *          .field(passwordField)
 *          .showSummary(true)
 *          .build();
 *      if (form.validate()) submit();
 *     }
 * </pre>
 *
 * @see FxFormField
 * @see FxErrorSummary
 * @see Builder
 */
public class FxForm extends VBox {

    private final List<FxFormField<?>> fields = new ArrayList<>();
    private final FxErrorSummary errorSummary;
    private final VBox fieldsBox = new VBox();

    /**
     * Creates an empty {@code FxForm} with a hidden error summary.
     */
    public FxForm() {
        super();
        getStyleClass().add("forja-form");
        setSpacing(12);
        fieldsBox.setSpacing(12);
        errorSummary = FxErrorSummary.builder().build();
        errorSummary.setVisible(false);
        errorSummary.setManaged(false);
        getChildren().addAll(errorSummary, fieldsBox);
    }

    /** Adds a field to the form. */
    public void addField(FxFormField<?> field) {
        if (field == null) return;
        fields.add(field);
        fieldsBox.getChildren().add(field);
    }

    /**
     * Runs each field's validator, aggregates errors into the summary, and
     * returns overall validity.
     *
     * @return {@code true} if every field is valid
     */
    public boolean validate() {
        boolean ok = true;
        List<String> summary = new ArrayList<>();
        for (FxFormField<?> f : fields) {
            boolean valid = f.validate();
            if (!valid) {
                ok = false;
                String err = f.getErrorText();
                String prefix = f.getLabel() == null || f.getLabel().isEmpty() ? "" : f.getLabel() + ": ";
                summary.add(prefix + (err == null ? "" : err));
            }
        }
        errorSummary.getErrors().setAll(summary);
        return ok;
    }

    /** Adds an arbitrary node between the summary and fields. */
    public void addExtra(Node node) {
        if (node != null) fieldsBox.getChildren().add(node);
    }

    /** Returns the form's field list (read-only). */
    public List<FxFormField<?>> getFields() { return Collections.unmodifiableList(fields); }

    /** Returns the error summary. */
    public FxErrorSummary getErrorSummary() { return errorSummary; }

    /** Returns the fields container. */
    public VBox getFieldsBox() { return fieldsBox; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxForm}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxForm}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>fields — empty</li>
     *   <li>showSummary — {@code true}</li>
     *   <li>summaryTitle — {@code "Please fix the following:"}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxForm, Builder> {

        private final List<FxFormField<?>> fields = new ArrayList<>();
        private boolean showSummary = true;
        private String summaryTitle = "Please fix the following:";

        public Builder field(FxFormField<?> field) { if (field != null) fields.add(field); return this; }
        public Builder fields(FxFormField<?>... fields) { if (fields != null) this.fields.addAll(Arrays.asList(fields)); return this; }
        public Builder showSummary(boolean showSummary) { this.showSummary = showSummary; return this; }
        public Builder summaryTitle(String summaryTitle) { this.summaryTitle = summaryTitle == null ? "" : summaryTitle; return this; }

        @Override
        public FxForm build() {
            FxForm f = new FxForm();
            for (FxFormField<?> ff : fields) f.addField(ff);
            f.getErrorSummary().setTitle(summaryTitle);
            if (!showSummary) {
                f.getChildren().remove(f.getErrorSummary());
            }
            applyBase(f);
            return f;
        }
    }
}
