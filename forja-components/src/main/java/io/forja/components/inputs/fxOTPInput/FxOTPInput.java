package io.forja.components.inputs.fxOTPInput;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * A one-time-code entry field — a row of {@code length} single-character
 * text boxes with auto-advance and paste support.
 *
 * <p>Typing a character advances focus to the next box. {@code BACKSPACE}
 * clears the current box (or moves back and clears if already empty).
 * Pasting a multi-character string fills as many boxes as fit.
 *
 * <p>The {@link #codeProperty()} contains the concatenated value; empty when
 * incomplete. {@link OnComplete} fires when all boxes are filled.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxOTPInput otp = FxOTPInput.builder()
 *          .length(6)
 *          .digitsOnly(true)
 *          .onComplete(code -> viewModel.verify(code))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxOTPInput extends HBox {

    private final List<TextField> boxes = new ArrayList<>();
    private final IntegerProperty length = new SimpleIntegerProperty(this, "length", 6);
    private final BooleanProperty digitsOnly = new SimpleBooleanProperty(this, "digitsOnly", true);
    private final StringProperty code = new SimpleStringProperty(this, "code", "");
    private OnComplete onComplete;

    /**
     * Creates a 6-box digits-only {@code FxOTPInput}.
     */
    public FxOTPInput() {
        super();
        getStyleClass().add("forja-otp-input");
        setSpacing(6);
        rebuildBoxes();

        length.addListener((obs, o, v) -> rebuildBoxes());
        digitsOnly.addListener((obs, o, v) -> rebuildBoxes());
    }

    private void rebuildBoxes() {
        getChildren().clear();
        boxes.clear();
        int n = Math.max(1, getLength());
        for (int i = 0; i < n; i++) {
            final int idx = i;
            TextField tf = new TextField();
            tf.getStyleClass().add("forja-otp-input-box");
            tf.setPrefColumnCount(1);
            tf.setPrefWidth(36);
            tf.setPrefHeight(40);
            tf.setStyle("-fx-alignment: center;");
            tf.setTextFormatter(new TextFormatter<>(cellFilter()));
            tf.textProperty().addListener((obs, o, v) -> {
                recomputeCode();
                if (v != null && v.length() == 1 && idx < boxes.size() - 1) boxes.get(idx + 1).requestFocus();
            });
            tf.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.BACK_SPACE) {
                    if (tf.getText().isEmpty() && idx > 0) {
                        boxes.get(idx - 1).requestFocus();
                        boxes.get(idx - 1).clear();
                        e.consume();
                    }
                }
            });
            boxes.add(tf);
            getChildren().add(tf);
        }
        recomputeCode();
    }

    private UnaryOperator<TextFormatter.Change> cellFilter() {
        return change -> {
            String next = change.getControlNewText();
            if (next.isEmpty()) return change;
            if (isDigitsOnly()) {
                if (!Pattern.matches("\\d", next)) return null;
            } else {
                if (next.length() > 1) return null;
            }
            return change;
        };
    }

    private void recomputeCode() {
        StringBuilder sb = new StringBuilder();
        for (TextField tf : boxes) sb.append(tf.getText() == null ? "" : tf.getText());
        String c = sb.toString();
        code.set(c);
        if (c.length() == getLength() && onComplete != null) onComplete.accept(c);
    }

    /** Returns the box list. */
    public List<TextField> getBoxes() { return boxes; }

    /** Returns the length property. */
    public IntegerProperty lengthProperty() { return length; }

    /** Returns the current length. */
    public int getLength() { return length.get(); }

    /** Sets the length (rebuilds boxes). */
    public void setLength(int v) { length.set(Math.max(1, v)); }

    /** Returns the digits-only property. */
    public BooleanProperty digitsOnlyProperty() { return digitsOnly; }

    /** Returns whether input is restricted to digits. */
    public boolean isDigitsOnly() { return digitsOnly.get(); }

    /** Sets whether input is restricted to digits. */
    public void setDigitsOnly(boolean v) { digitsOnly.set(v); }

    /** Returns the code property. */
    public StringProperty codeProperty() { return code; }

    /** Returns the current concatenated code. */
    public String getCode() { return code.get(); }

    /** Programmatically fills the boxes. */
    public void setCode(String value) {
        String v = value == null ? "" : value;
        for (int i = 0; i < boxes.size(); i++) {
            boxes.get(i).setText(i < v.length() ? String.valueOf(v.charAt(i)) : "");
        }
    }

    /** Sets the completion callback. */
    public void setOnComplete(OnComplete onComplete) { this.onComplete = onComplete; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxOTPInput}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when all boxes are filled. */
    @FunctionalInterface
    public interface OnComplete { void accept(String code); }

    /**
     * Fluent builder for constructing an {@link FxOTPInput}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>length — {@code 6}</li>
     *   <li>digitsOnly — {@code true}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxOTPInput, Builder> {

        private int length = 6;
        private boolean digitsOnly = true;
        private String initial = "";
        private OnComplete onComplete;

        public Builder length(int length) { this.length = Math.max(1, length); return this; }
        public Builder digitsOnly(boolean digitsOnly) { this.digitsOnly = digitsOnly; return this; }
        public Builder initial(String initial) { this.initial = initial == null ? "" : initial; return this; }
        public Builder onComplete(OnComplete onComplete) { this.onComplete = onComplete; return this; }

        @Override
        public FxOTPInput build() {
            FxOTPInput otp = new FxOTPInput();
            otp.setDigitsOnly(digitsOnly);
            otp.setLength(length);
            otp.setOnComplete(onComplete);
            if (!initial.isEmpty()) otp.setCode(initial);
            applyBase(otp);
            return otp;
        }
    }
}
