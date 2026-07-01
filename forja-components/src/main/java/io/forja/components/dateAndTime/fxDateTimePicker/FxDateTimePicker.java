package io.forja.components.dateAndTime.fxDateTimePicker;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.dateAndTime.fxDatePicker.FxDatePicker;
import io.forja.components.dateAndTime.fxTimePicker.FxTimePicker;
import io.forja.components.inputs.InputVariant;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A composite date-and-time input — a {@link FxDatePicker} and a
 * {@link FxTimePicker} side by side, exposing a single
 * {@link #valueProperty() LocalDateTime} value.
 *
 * <p>Changes to either child update the composite value; setting the
 * composite value updates both children. When either side is empty, the
 * composite value is {@code null}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxDateTimePicker dt = FxDateTimePicker.builder()
 *          .value(LocalDateTime.now())
 *          .use24Hour(true)
 *          .helperText("Kickoff time")
 *          .build();
 *     }
 * </pre>
 *
 * @see FxDatePicker
 * @see FxTimePicker
 * @see Builder
 */
public class FxDateTimePicker extends VBox {

    private final FxDatePicker datePicker = new FxDatePicker();
    private final FxTimePicker timePicker = new FxTimePicker();
    private final HBox row = new HBox();
    private final FxLabel helperLabel = new FxLabel("", LabelVariant.SMALL);

    private final ObjectProperty<LocalDateTime> value = new SimpleObjectProperty<>(this, "value");
    private final StringProperty helperText = new SimpleStringProperty(this, "helperText", "");
    private final StringProperty errorText = new SimpleStringProperty(this, "errorText", "");
    private final BooleanProperty showingError = new SimpleBooleanProperty(this, "showingError", false);
    private boolean syncing = false;

    /**
     * Creates an empty {@code FxDateTimePicker}.
     */
    public FxDateTimePicker() {
        super();
        getStyleClass().add("forja-date-time-picker");
        setSpacing(4);

        row.getStyleClass().add("forja-date-time-picker-row");
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(8);
        HBox.setHgrow(datePicker, Priority.ALWAYS);
        row.getChildren().addAll(datePicker, timePicker);

        helperLabel.getStyleClass().add("forja-date-time-picker-helper");
        helperLabel.setMuted(true);
        helperLabel.setVisible(false);
        helperLabel.setManaged(false);

        getChildren().addAll(row, helperLabel);

        datePicker.valueProperty().addListener((obs, o, v) -> recompute());
        timePicker.valueProperty().addListener((obs, o, v) -> recompute());
        value.addListener((obs, o, v) -> syncFromValue());

        helperText.addListener((obs, o, v) -> refreshHelper());
        errorText.addListener((obs, o, v) -> {
            String e = v == null ? "" : v;
            if (!e.isEmpty()) {
                datePicker.setVariant(InputVariant.ERROR);
                timePicker.setVariant(InputVariant.ERROR);
            }
            refreshHelper();
        });
    }

    private void recompute() {
        if (syncing) return;
        LocalDate d = datePicker.getValue();
        LocalTime t = timePicker.getValue();
        LocalDateTime next = (d == null || t == null) ? null : LocalDateTime.of(d, t);
        syncing = true;
        try { value.set(next); } finally { syncing = false; }
    }

    private void syncFromValue() {
        if (syncing) return;
        syncing = true;
        try {
            LocalDateTime v = getValue();
            datePicker.setValue(v == null ? null : v.toLocalDate());
            timePicker.setValue(v == null ? null : v.toLocalTime());
        } finally { syncing = false; }
    }

    private void refreshHelper() {
        String err = getErrorText();
        String help = getHelperText();
        boolean hasError = err != null && !err.isEmpty();
        boolean hasHelper = help != null && !help.isEmpty();
        if (hasError) { helperLabel.setText(err); helperLabel.setMuted(false); showingError.set(true); }
        else if (hasHelper) { helperLabel.setText(help); helperLabel.setMuted(true); showingError.set(false); }
        else { helperLabel.setText(""); showingError.set(false); }
        boolean vis = hasError || hasHelper;
        helperLabel.setVisible(vis);
        helperLabel.setManaged(vis);
    }

    /** Returns the inner date picker. */
    public FxDatePicker getDatePicker() { return datePicker; }

    /** Returns the inner time picker. */
    public FxTimePicker getTimePicker() { return timePicker; }

    /** Returns the value property. */
    public ObjectProperty<LocalDateTime> valueProperty() { return value; }

    /** Returns the current combined value, or {@code null}. */
    public LocalDateTime getValue() { return value.get(); }

    /** Sets the combined value. */
    public void setValue(LocalDateTime v) { value.set(v); }

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

    /** Returns whether the helper label is currently showing the error text. */
    public boolean isShowingError() { return showingError.get(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxDateTimePicker}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxDateTimePicker}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>value — {@code null}</li>
     *   <li>use24Hour — {@code true}</li>
     *   <li>showSeconds — {@code false}</li>
     *   <li>helperText / errorText — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxDateTimePicker, Builder> {

        private LocalDateTime value;
        private boolean use24Hour = true;
        private boolean showSeconds = false;
        private String helperText = "";
        private String errorText = "";

        public Builder value(LocalDateTime value) { this.value = value; return this; }
        public Builder use24Hour(boolean use24Hour) { this.use24Hour = use24Hour; return this; }
        public Builder showSeconds(boolean showSeconds) { this.showSeconds = showSeconds; return this; }
        public Builder helperText(String helperText) { this.helperText = helperText == null ? "" : helperText; return this; }
        public Builder errorText(String errorText) { this.errorText = errorText == null ? "" : errorText; return this; }

        @Override
        public FxDateTimePicker build() {
            FxDateTimePicker dt = new FxDateTimePicker();
            dt.getTimePicker().setUse24Hour(use24Hour);
            dt.getTimePicker().setShowSeconds(showSeconds);
            dt.setHelperText(helperText);
            dt.setValue(value);
            dt.setErrorText(errorText);
            applyBase(dt);
            return dt;
        }
    }
}
