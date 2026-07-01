package io.forja.components.buttonsAndActions.fxToggleButton;

import io.forja.builder.FxComponentBuilder;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

/**
 * A stateful toggle button built on {@link ToggleButton}.
 *
 * <p>{@code FxToggleButton} extends the standard JavaFX {@code ToggleButton} —
 * all native properties (including {@link javafx.scene.control.ToggleGroup}
 * membership), bindings, and event APIs remain fully accessible. Forja adds
 * the {@link ButtonVariant} variant system and a fluent builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxToggleButton bold = FxToggleButton.builder()
 *          .text("B")
 *          .selected(false)
 *          .variant(ButtonVariant.GHOST)
 *          .build();
 *     }
 * </pre>
 *
 * @see ButtonVariant
 * @see Builder
 */
public class FxToggleButton extends ToggleButton {

    private static final PseudoClass PRIMARY_PC   = PseudoClass.getPseudoClass("primary");
    private static final PseudoClass SECONDARY_PC = PseudoClass.getPseudoClass("secondary");
    private static final PseudoClass GHOST_PC     = PseudoClass.getPseudoClass("ghost");
    private static final PseudoClass DANGER_PC    = PseudoClass.getPseudoClass("danger");

    private final ObjectProperty<ButtonVariant> variant = new SimpleObjectProperty<>(this, "variant", ButtonVariant.SECONDARY);

    /**
     * Creates an unselected {@code FxToggleButton} with no text.
     */
    public FxToggleButton() {
        super();
        getStyleClass().add("forja-toggle-button");
        variant.addListener((obs, o, v) -> applyVariantPseudoClass());
        applyVariantPseudoClass();
    }

    /**
     * Creates an {@code FxToggleButton} with the given label text.
     *
     * @param text label text
     */
    public FxToggleButton(String text) {
        this();
        setText(text);
    }

    private void applyVariantPseudoClass() {
        pseudoClassStateChanged(PRIMARY_PC,   false);
        pseudoClassStateChanged(SECONDARY_PC, false);
        pseudoClassStateChanged(GHOST_PC,     false);
        pseudoClassStateChanged(DANGER_PC,    false);
        switch (getVariant()) {
            case PRIMARY:   pseudoClassStateChanged(PRIMARY_PC,   true); break;
            case SECONDARY: pseudoClassStateChanged(SECONDARY_PC, true); break;
            case GHOST:     pseudoClassStateChanged(GHOST_PC,     true); break;
            case DANGER:    pseudoClassStateChanged(DANGER_PC,    true); break;
        }
    }

    /** Returns the variant property. */
    public ObjectProperty<ButtonVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public ButtonVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(ButtonVariant v) { variant.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxToggleButton}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxToggleButton}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     *   <li>selected — {@code false}</li>
     *   <li>variant — {@link ButtonVariant#SECONDARY}</li>
     *   <li>onAction — none</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxToggleButton, Builder> {

        private String text = "";
        private boolean selected = false;
        private ButtonVariant variant = ButtonVariant.SECONDARY;
        private EventHandler<ActionEvent> onAction;
        private Object toggleGroupUserData;

        public Builder text(String text) {
            this.text = text == null ? "" : text;
            return this;
        }

        public Builder selected(boolean selected) {
            this.selected = selected;
            return this;
        }

        public Builder variant(ButtonVariant variant) {
            this.variant = variant == null ? ButtonVariant.SECONDARY : variant;
            return this;
        }

        public Builder onAction(EventHandler<ActionEvent> onAction) {
            this.onAction = onAction;
            return this;
        }

        public Builder toggleGroupUserData(Object userData) {
            this.toggleGroupUserData = userData;
            return this;
        }

        @Override
        public FxToggleButton build() {
            FxToggleButton b = new FxToggleButton(text);
            b.setVariant(variant);
            b.setSelected(selected);
            if (onAction != null) b.setOnAction(onAction);
            if (toggleGroupUserData != null) b.setUserData(toggleGroupUserData);
            applyBase(b);
            return b;
        }
    }
}
