package io.forja.components.buttonsAndActions.fxMenuButton;

import io.forja.builder.FxComponentBuilder;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.util.Arrays;

/**
 * A menu-button preset for icon-only action clusters.
 *
 * <p>Distinct from {@code FxDropdownMenu} — that one is text-first (a labeled
 * button that reveals a menu). {@code FxMenuButton} is the classic
 * three-dot / kebab icon that reveals a menu of {@link MenuItem}s.
 *
 * <p>Extends {@link MenuButton} directly — all native APIs remain accessible.
 * Forja adds a {@link ButtonVariant} pseudo-class and a fluent builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxMenuButton kebab = FxMenuButton.builder()
 *          .icon("fth-more-vertical")
 *          .items(renameItem, deleteItem)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxMenuButton extends MenuButton {

    private static final PseudoClass PRIMARY_PC   = PseudoClass.getPseudoClass("primary");
    private static final PseudoClass SECONDARY_PC = PseudoClass.getPseudoClass("secondary");
    private static final PseudoClass GHOST_PC     = PseudoClass.getPseudoClass("ghost");
    private static final PseudoClass DANGER_PC    = PseudoClass.getPseudoClass("danger");

    private final ObjectProperty<ButtonVariant> variant = new SimpleObjectProperty<>(this, "variant", ButtonVariant.GHOST);

    /**
     * Creates an empty {@code FxMenuButton} with the ghost variant.
     */
    public FxMenuButton() {
        super();
        getStyleClass().add("forja-menu-button");
        variant.addListener((obs, o, v) -> applyVariantPseudoClass());
        applyVariantPseudoClass();
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
    public void setVariant(ButtonVariant v) { variant.set(v == null ? ButtonVariant.GHOST : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxMenuButton}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxMenuButton}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>icon — {@code "fth-more-vertical"} (kebab)</li>
     *   <li>text — empty</li>
     *   <li>variant — {@link ButtonVariant#GHOST}</li>
     *   <li>items — empty</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxMenuButton, Builder> {

        private String iconLiteral = "fth-more-vertical";
        private String text = "";
        private ButtonVariant variant = ButtonVariant.GHOST;
        private MenuItem[] items = new MenuItem[0];

        public Builder icon(String iconLiteral) { this.iconLiteral = iconLiteral == null ? "" : iconLiteral; return this; }
        public Builder text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder variant(ButtonVariant variant) { this.variant = variant == null ? ButtonVariant.GHOST : variant; return this; }
        public Builder items(MenuItem... items) { this.items = items == null ? new MenuItem[0] : items; return this; }

        @Override
        public FxMenuButton build() {
            FxMenuButton b = new FxMenuButton();
            b.setVariant(variant);
            b.setText(text);
            if (iconLiteral != null && !iconLiteral.isEmpty()) b.setGraphic(new FxIcon(iconLiteral));
            b.getItems().addAll(Arrays.asList(items));
            applyBase(b);
            return b;
        }
    }
}
