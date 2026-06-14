package io.forja.components.feedbackAndStatus.fxChip;

import io.forja.tokens.SemanticVariant;

import io.forja.builder.FxComponentBuilder;
import io.forja.components.utilities.fxIcon.FxIcon;
import io.forja.components.feedbackAndStatus.fxChip.FxChipSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;

/**
 * A styled removable chip built on top of {@link Label}.
 *
 * <p>{@code FxChip} renders a short label inside a pill, with an optional
 * trailing close button (an {@link FxIcon}-style {@code x}). Variants mirror
 * the seven Forja semantic tokens; setting {@code removable=true} adds the
 * close affordance and exposes an {@code onClose} handler.
 *
 * <p>The preferred way to construct an {@code FxChip} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxChip tag = FxChip.builder()
 *          .text("javafx")
 *          .variant(SemanticVariant.ACCENT)
 *          .removable(true)
 *          .onClose(e -> removeTag("javafx"))
 *          .build();
 *     }
 * </pre>
 *
 * @see SemanticVariant
 * @see Builder
 */
public class FxChip extends Label {

    private static final String CLOSE_ICON_LITERAL = "fth-x";
    private static final int CLOSE_ICON_SIZE = 12;

    private final ObjectProperty<SemanticVariant> variant = new SimpleObjectProperty<>(this, "variant", SemanticVariant.DEFAULT);
    private final BooleanProperty removable = new SimpleBooleanProperty(this, "removable", false);
    private final ObjectProperty<EventHandler<ActionEvent>> onClose = new SimpleObjectProperty<>(this, "onClose");

    /** Icon node we own; never clear a user-supplied graphic. */
    private FxIcon managedCloseIcon;

    /**
     * Creates an empty {@code FxChip} with the default variant.
     */
    public FxChip() {
        super();
        init();
    }

    /**
     * Creates an {@code FxChip} with the given text and default variant.
     *
     * @param text the chip label
     */
    public FxChip(String text) {
        super(text);
        init();
    }

    /**
     * Creates an {@code FxChip} with the given text and variant.
     *
     * @param text the chip label
     * @param variant the semantic color variant
     */
    public FxChip(String text, SemanticVariant variant) {
        this(text);
        setVariant(variant);
    }

    private void init() {
        getStyleClass().add("forja-chip");
        applyCloseIcon();

        removable.addListener((obs, old, val) -> applyCloseIcon());
    }

    private void applyCloseIcon() {
        if (isRemovable()) {
            if (managedCloseIcon == null) {
                managedCloseIcon = new FxIcon(CLOSE_ICON_LITERAL);
                managedCloseIcon.setIconSize(CLOSE_ICON_SIZE);
                managedCloseIcon.getStyleClass().add("forja-chip-close");
                managedCloseIcon.setOnMouseClicked(e -> {
                    EventHandler<ActionEvent> handler = getOnClose();
                    if (handler != null) {
                        handler.handle(new ActionEvent(this, this));
                    }
                    e.consume();
                });
            }
            setGraphic(managedCloseIcon);
            setContentDisplay(ContentDisplay.RIGHT);
        } else if (getGraphic() == managedCloseIcon && managedCloseIcon != null) {
            setGraphic(null);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns the Forja chip skin which drives variant + removable
     * pseudo-class wiring.</p>
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new FxChipSkin(this);
    }

    /** Returns the variant property. */
    public ObjectProperty<SemanticVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public SemanticVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(SemanticVariant v) { variant.set(v); }

    /** Returns the removable property. */
    public BooleanProperty removableProperty() { return removable; }

    /** Returns whether this chip shows a close button. */
    public boolean isRemovable() { return removable.get(); }

    /** Sets whether the chip is removable (shows close button). */
    public void setRemovable(boolean v) { removable.set(v); }

    /** Returns the onClose handler property. */
    public ObjectProperty<EventHandler<ActionEvent>> onCloseProperty() { return onClose; }

    /** Returns the current onClose handler, or {@code null}. */
    public EventHandler<ActionEvent> getOnClose() { return onClose.get(); }

    /**
     * Sets the close handler. Fires when the user clicks the close button.
     * The {@link ActionEvent#getSource()} is this chip — callers can use it
     * to identify which chip should be removed.
     */
    public void setOnClose(EventHandler<ActionEvent> handler) { onClose.set(handler); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxChip}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxChip}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty string</li>
     *   <li>variant — {@link SemanticVariant#DEFAULT}</li>
     *   <li>removable — {@code false}</li>
     *   <li>onClose — none</li>
     * </ul>
     *
     * <p>Inherited from {@link io.forja.builder.FxComponentBuilder}:
     * <ul>
     *   <li>id, disabled, visible, styleClass, style, tooltip, userData</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxChip, Builder> {

        private String text = "";
        private SemanticVariant variant = SemanticVariant.DEFAULT;
        private boolean removable = false;
        private EventHandler<ActionEvent> onClose;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder variant(SemanticVariant variant) {
            this.variant = variant;
            return this;
        }

        public Builder removable(boolean removable) {
            this.removable = removable;
            return this;
        }

        public Builder onClose(EventHandler<ActionEvent> handler) {
            this.onClose = handler;
            return this;
        }

        @Override
        public FxChip build() {
            FxChip chip = new FxChip(text, variant);
            if (onClose != null) {
                chip.setOnClose(onClose);
            }
            chip.setRemovable(removable);
            applyBase(chip);
            return chip;
        }
    }
}
