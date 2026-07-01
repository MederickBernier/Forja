package io.forja.components.dateAndTime.fxDateRangePicker;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.dateAndTime.fxDatePicker.FxDatePicker;
import io.forja.components.inputs.InputVariant;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

/**
 * A date-range input — two {@link FxDatePicker}s with an
 * {@code end ≥ start} clamp.
 *
 * <p>Changing the start date forward past the end auto-advances the end
 * date; changing the end date backward past the start auto-adjusts the
 * start. Set {@link Builder#min} / {@link Builder#max} to bound both.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxDateRangePicker r = FxDateRangePicker.builder()
 *          .start(LocalDate.of(2026, 6, 1))
 *          .end(LocalDate.of(2026, 6, 30))
 *          .build();
 *     }
 * </pre>
 *
 * @see FxDatePicker
 * @see Builder
 */
public class FxDateRangePicker extends VBox {

    private final FxDatePicker startPicker = new FxDatePicker();
    private final FxDatePicker endPicker = new FxDatePicker();
    private final FxLabel toLabel = new FxLabel("–", LabelVariant.BODY);
    private final HBox row = new HBox();
    private final FxLabel helperLabel = new FxLabel("", LabelVariant.SMALL);

    private final ObjectProperty<LocalDate> start = new SimpleObjectProperty<>(this, "start");
    private final ObjectProperty<LocalDate> end = new SimpleObjectProperty<>(this, "end");
    private final StringProperty helperText = new SimpleStringProperty(this, "helperText", "");
    private final StringProperty errorText = new SimpleStringProperty(this, "errorText", "");
    private boolean syncing = false;

    /**
     * Creates an empty {@code FxDateRangePicker}.
     */
    public FxDateRangePicker() {
        super();
        getStyleClass().add("forja-date-range-picker");
        setSpacing(4);

        row.getStyleClass().add("forja-date-range-picker-row");
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(8);
        toLabel.setMuted(true);
        HBox.setHgrow(startPicker, Priority.ALWAYS);
        HBox.setHgrow(endPicker, Priority.ALWAYS);
        row.getChildren().addAll(startPicker, toLabel, endPicker);

        helperLabel.getStyleClass().add("forja-date-range-picker-helper");
        helperLabel.setMuted(true);
        helperLabel.setVisible(false);
        helperLabel.setManaged(false);

        getChildren().addAll(row, helperLabel);

        startPicker.valueProperty().addListener((obs, o, v) -> {
            if (syncing) return;
            syncing = true;
            try {
                start.set(v);
                if (v != null && endPicker.getValue() != null && v.isAfter(endPicker.getValue())) {
                    endPicker.setValue(v);
                    end.set(v);
                }
            } finally { syncing = false; }
        });
        endPicker.valueProperty().addListener((obs, o, v) -> {
            if (syncing) return;
            syncing = true;
            try {
                end.set(v);
                if (v != null && startPicker.getValue() != null && v.isBefore(startPicker.getValue())) {
                    startPicker.setValue(v);
                    start.set(v);
                }
            } finally { syncing = false; }
        });
        start.addListener((obs, o, v) -> { if (!syncing) startPicker.setValue(v); });
        end.addListener((obs, o, v) -> { if (!syncing) endPicker.setValue(v); });

        helperText.addListener((obs, o, v) -> refreshHelper());
        errorText.addListener((obs, o, v) -> {
            if (v != null && !v.isEmpty()) {
                startPicker.setVariant(InputVariant.ERROR);
                endPicker.setVariant(InputVariant.ERROR);
            }
            refreshHelper();
        });
    }

    private void refreshHelper() {
        String err = getErrorText();
        String help = getHelperText();
        boolean hasError = err != null && !err.isEmpty();
        boolean hasHelper = help != null && !help.isEmpty();
        if (hasError) { helperLabel.setText(err); helperLabel.setMuted(false); }
        else if (hasHelper) { helperLabel.setText(help); helperLabel.setMuted(true); }
        else helperLabel.setText("");
        boolean vis = hasError || hasHelper;
        helperLabel.setVisible(vis);
        helperLabel.setManaged(vis);
    }

    /** Returns the start picker. */
    public FxDatePicker getStartPicker() { return startPicker; }

    /** Returns the end picker. */
    public FxDatePicker getEndPicker() { return endPicker; }

    /** Returns the start property. */
    public ObjectProperty<LocalDate> startProperty() { return start; }

    /** Returns the current start date. */
    public LocalDate getStart() { return start.get(); }

    /** Sets the start date. */
    public void setStart(LocalDate v) { start.set(v); }

    /** Returns the end property. */
    public ObjectProperty<LocalDate> endProperty() { return end; }

    /** Returns the current end date. */
    public LocalDate getEnd() { return end.get(); }

    /** Sets the end date. */
    public void setEnd(LocalDate v) { end.set(v); }

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

    /**
     * Returns a new {@link Builder} for constructing an {@code FxDateRangePicker}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxDateRangePicker}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>start / end — {@code null}</li>
     *   <li>min / max — {@code null} (unbounded)</li>
     *   <li>formatPattern — {@code "yyyy-MM-dd"}</li>
     *   <li>helperText / errorText — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxDateRangePicker, Builder> {

        private LocalDate start;
        private LocalDate end;
        private LocalDate min;
        private LocalDate max;
        private String formatPattern = "yyyy-MM-dd";
        private String helperText = "";
        private String errorText = "";

        public Builder start(LocalDate start) { this.start = start; return this; }
        public Builder end(LocalDate end) { this.end = end; return this; }
        public Builder min(LocalDate min) { this.min = min; return this; }
        public Builder max(LocalDate max) { this.max = max; return this; }
        public Builder formatPattern(String pattern) { this.formatPattern = pattern == null || pattern.isEmpty() ? "yyyy-MM-dd" : pattern; return this; }
        public Builder helperText(String helperText) { this.helperText = helperText == null ? "" : helperText; return this; }
        public Builder errorText(String errorText) { this.errorText = errorText == null ? "" : errorText; return this; }

        @Override
        public FxDateRangePicker build() {
            FxDateRangePicker r = new FxDateRangePicker();
            r.getStartPicker().setFormatPattern(formatPattern);
            r.getEndPicker().setFormatPattern(formatPattern);
            r.getStartPicker().setMin(min);
            r.getStartPicker().setMax(max);
            r.getEndPicker().setMin(min);
            r.getEndPicker().setMax(max);
            r.setHelperText(helperText);
            r.setStart(start);
            r.setEnd(end);
            r.setErrorText(errorText);
            applyBase(r);
            return r;
        }
    }
}
