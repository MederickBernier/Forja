package io.forja.components.buttonsAndActions.fxSplitButton;

import io.forja.builder.FxComponentBuilder;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

import java.util.Arrays;

/**
 * A split button built on {@link SplitMenuButton} — a primary click surface
 * plus a chevron that opens a menu of {@link MenuItem}s.
 *
 * <p>Extends {@code SplitMenuButton} directly — all native APIs remain
 * accessible. Forja adds a {@link ButtonVariant} pseudo-class and a fluent
 * builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxSplitButton save = FxSplitButton.builder()
 *          .text("Save")
 *          .variant(ButtonVariant.PRIMARY)
 *          .onAction(e -> handleSave())
 *          .items(saveAsItem, exportItem)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxSplitButton extends SplitMenuButton {

    private static final PseudoClass PRIMARY_PC   = PseudoClass.getPseudoClass("primary");
    private static final PseudoClass SECONDARY_PC = PseudoClass.getPseudoClass("secondary");
    private static final PseudoClass GHOST_PC     = PseudoClass.getPseudoClass("ghost");
    private static final PseudoClass DANGER_PC    = PseudoClass.getPseudoClass("danger");

    private final ObjectProperty<ButtonVariant> variant = new SimpleObjectProperty<>(this, "variant", ButtonVariant.PRIMARY);

    /**
     * Creates an empty {@code FxSplitButton} with the default variant.
     */
    public FxSplitButton() {
        super();
        getStyleClass().add("forja-split-button");
        variant.addListener((obs, o, v) -> applyVariantPseudoClass());
        applyVariantPseudoClass();
    }

    /**
     * Creates an {@code FxSplitButton} with the given label.
     *
     * @param text button label
     */
    public FxSplitButton(String text) {
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
    public void setVariant(ButtonVariant v) { variant.set(v == null ? ButtonVariant.PRIMARY : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSplitButton}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxSplitButton}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     *   <li>variant — {@link ButtonVariant#PRIMARY}</li>
     *   <li>items — empty</li>
     *   <li>onAction — none</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxSplitButton, Builder> {

        private String text = "";
        private ButtonVariant variant = ButtonVariant.PRIMARY;
        private MenuItem[] items = new MenuItem[0];
        private EventHandler<ActionEvent> onAction;
        private String iconLiteral;

        public Builder text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder variant(ButtonVariant variant) { this.variant = variant == null ? ButtonVariant.PRIMARY : variant; return this; }
        public Builder items(MenuItem... items) { this.items = items == null ? new MenuItem[0] : items; return this; }
        public Builder onAction(EventHandler<ActionEvent> onAction) { this.onAction = onAction; return this; }
        public Builder icon(String iconLiteral) { this.iconLiteral = iconLiteral; return this; }

        @Override
        public FxSplitButton build() {
            FxSplitButton b = new FxSplitButton(text);
            b.setVariant(variant);
            b.getItems().addAll(Arrays.asList(items));
            if (onAction != null) b.setOnAction(onAction);
            if (iconLiteral != null && !iconLiteral.isEmpty()) b.setGraphic(new FxIcon(iconLiteral));
            applyBase(b);
            return b;
        }
    }
}
