package io.forja.components.selection.fxSwitch;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * A pill-style on/off toggle switch.
 *
 * <p>{@code FxSwitch} is an {@link HBox} of an optional label and a
 * {@link StackPane} track containing a knob {@link Region}. Clicking the
 * track flips the {@link #selectedProperty()}; the knob slides via CSS
 * transitions applied through the {@code :selected} pseudo-class.
 *
 * <p>Keyboard: {@code SPACE} toggles when the switch has focus.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxSwitch dark = FxSwitch.builder()
 *          .text("Dark mode")
 *          .selected(true)
 *          .onChange(v -> theme.setDark(v))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxSwitch extends HBox {

    private static final PseudoClass SELECTED_PC = PseudoClass.getPseudoClass("selected");
    private static final PseudoClass FOCUSED_PC = PseudoClass.getPseudoClass("focused");

    private final StackPane track = new StackPane();
    private final Region knob = new Region();
    private final FxLabel textLabel = new FxLabel("", LabelVariant.BODY);

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);
    private final StringProperty text = new SimpleStringProperty(this, "text", "");

    /**
     * Creates an unselected {@code FxSwitch} with no label.
     */
    public FxSwitch() {
        super();
        getStyleClass().add("forja-switch");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(8);
        setFocusTraversable(true);

        track.getStyleClass().add("forja-switch-track");
        knob.getStyleClass().add("forja-switch-knob");
        track.getChildren().add(knob);
        StackPane.setAlignment(knob, Pos.CENTER_LEFT);

        textLabel.getStyleClass().add("forja-switch-label");
        textLabel.setVisible(false);
        textLabel.setManaged(false);

        getChildren().addAll(track, textLabel);

        selected.addListener((obs, o, v) -> {
            pseudoClassStateChanged(SELECTED_PC, v);
            StackPane.setAlignment(knob, v ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        });
        text.addListener((obs, o, v) -> {
            String s = v == null ? "" : v;
            textLabel.setText(s);
            boolean vis = !s.isEmpty();
            textLabel.setVisible(vis);
            textLabel.setManaged(vis);
        });
        focusedProperty().addListener((obs, o, v) -> pseudoClassStateChanged(FOCUSED_PC, v));

        setOnMouseClicked(e -> { if (!isDisabled()) toggle(); e.consume(); });
        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                if (!isDisabled()) toggle();
                e.consume();
            }
        });
    }

    /**
     * Creates an {@code FxSwitch} with the given label text.
     *
     * @param text label text shown to the right of the switch
     */
    public FxSwitch(String text) {
        this();
        setText(text);
    }

    /** Flips the selected state. */
    public void toggle() { setSelected(!isSelected()); }

    /** Returns the track {@link StackPane} node. */
    public StackPane getTrack() { return track; }

    /** Returns the knob {@link Region} node. */
    public Region getKnob() { return knob; }

    /** Returns the text-label node. */
    public FxLabel getTextLabel() { return textLabel; }

    /** Returns the selected property. */
    public BooleanProperty selectedProperty() { return selected; }

    /** Returns whether the switch is on. */
    public boolean isSelected() { return selected.get(); }

    /** Sets the selected state. */
    public void setSelected(boolean v) { selected.set(v); }

    /** Returns the text property. */
    public StringProperty textProperty() { return text; }

    /** Returns the current text. */
    public String getText() { return text.get(); }

    /** Sets the text; empty/null hides the label. */
    public void setText(String v) { text.set(v == null ? "" : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSwitch}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxSwitch}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty (label hidden)</li>
     *   <li>selected — {@code false}</li>
     *   <li>onChange — none</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxSwitch, Builder> {

        private String text = "";
        private boolean selected = false;
        private OnChange onChange;

        public Builder text(String text) {
            this.text = text == null ? "" : text;
            return this;
        }

        public Builder selected(boolean selected) {
            this.selected = selected;
            return this;
        }

        public Builder onChange(OnChange onChange) {
            this.onChange = onChange;
            return this;
        }

        @Override
        public FxSwitch build() {
            FxSwitch s = new FxSwitch(text);
            s.setSelected(selected);
            if (onChange != null) {
                s.selectedProperty().addListener((obs, o, v) -> onChange.accept(v));
            }
            applyBase(s);
            return s;
        }
    }

    /** Callback fired when the switch's selected state changes. */
    @FunctionalInterface
    public interface OnChange {
        void accept(boolean selected);
    }
}
