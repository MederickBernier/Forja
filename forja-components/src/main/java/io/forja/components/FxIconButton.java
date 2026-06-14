package io.forja.components;

import io.forja.builder.FxComponentBuilder;
import io.forja.skin.FxIconButtonSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Skin;

/**
 * A styled icon button built on top of JavaFX {@link Button}.
 *
 * <p>{@code FxIconButton} mirrors the variant and loading API of
 * {@link FxButton} and adds an icon glyph (rendered via {@link FxIcon}) plus
 * a placement option. Position {@link IconPosition#ONLY} produces a square
 * icon-only button; {@code LEFT} and {@code RIGHT} produce a labeled button
 * with an icon adjacent to the text.
 *
 * <p>The preferred way to construct an {@code FxIconButton} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxIconButton save = FxIconButton.builder()
 *          .text("Save")
 *          .icon("fth-save")
 *          .iconPosition(IconPosition.LEFT)
 *          .variant(ButtonVariant.PRIMARY)
 *          .onAction(e -> handleSave())
 *          .build();
 *     }
 * </pre>
 *
 * <p>Constructor and direct instantiation are also supported:</p>
 * <pre>
 *     {@code
 *      FxIconButton close = new FxIconButton("fth-x", IconPosition.ONLY);
 *     }
 * </pre>
 *
 * <p>{@code FxIconButton} does not extend {@link FxButton} — Java's
 * static-method-hiding rule blocks an inheriting subclass from declaring its
 * own typed {@code builder()} factory. The variant and loading APIs are
 * duplicated here intentionally to keep the fluent builder ergonomics intact.
 *
 * @see FxButton
 * @see IconPosition
 * @see FxIcon
 * @see Builder
 */
public class FxIconButton extends Button {

    private static final int DEFAULT_ICON_SIZE = 16;

    private final ObjectProperty<ButtonVariant> variant = new SimpleObjectProperty<>(this, "variant", ButtonVariant.PRIMARY);
    private final BooleanProperty loading = new SimpleBooleanProperty(this, "loading", false);
    private final StringProperty iconLiteral = new SimpleStringProperty(this, "iconLiteral");
    private final ObjectProperty<IconPosition> iconPosition = new SimpleObjectProperty<>(this, "iconPosition", IconPosition.LEFT);
    private final IntegerProperty iconSize = new SimpleIntegerProperty(this, "iconSize", DEFAULT_ICON_SIZE);

    /**
     * Creates an {@code FxIconButton} with no icon, no text, and default
     * variant ({@link ButtonVariant#PRIMARY}).
     */
    public FxIconButton() {
        super();
        init();
    }

    /**
     * Creates an {@code FxIconButton} with the given icon literal and the
     * default position ({@link IconPosition#LEFT}).
     *
     * @param iconLiteral the Ikonli icon literal, e.g. {@code "fth-save"}
     */
    public FxIconButton(String iconLiteral) {
        super();
        init();
        setIconLiteral(iconLiteral);
    }

    /**
     * Creates an {@code FxIconButton} with the given icon literal and position.
     *
     * @param iconLiteral the Ikonli icon literal
     * @param position the icon placement - see {@link IconPosition}
     */
    public FxIconButton(String iconLiteral, IconPosition position) {
        super();
        init();
        setIconLiteral(iconLiteral);
        setIconPosition(position);
    }

    /**
     * Creates an {@code FxIconButton} with text and an icon on the left.
     *
     * @param text the button label
     * @param iconLiteral the Ikonli icon literal
     */
    public FxIconButton(String text, String iconLiteral) {
        super(text);
        init();
        setIconLiteral(iconLiteral);
    }

    private void init() {
        getStyleClass().addAll("forja-button", "forja-icon-button");

        applyContentDisplay();
        rebuildGraphic();

        iconPosition.addListener((obs, old, val) -> applyContentDisplay());
        iconLiteral.addListener((obs, old, val) -> rebuildGraphic());
        iconSize.addListener((obs, old, val) -> rebuildGraphic());
    }

    private void applyContentDisplay() {
        switch (getIconPosition()) {
            case LEFT:  setContentDisplay(ContentDisplay.LEFT);          break;
            case RIGHT: setContentDisplay(ContentDisplay.RIGHT);         break;
            case ONLY:  setContentDisplay(ContentDisplay.GRAPHIC_ONLY);  break;
        }
    }

    private void rebuildGraphic() {
        String literal = getIconLiteral();
        if (literal == null || literal.isEmpty()) {
            setGraphic(null);
            return;
        }
        FxIcon icon = new FxIcon(literal);
        icon.setIconSize(getIconSize());
        setGraphic(icon);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns the Forja icon-button skin, which manages variant, loading,
     * and icon-position pseudo-classes.</p>
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new FxIconButtonSkin(this);
    }

    /** Returns the variant property. */
    public ObjectProperty<ButtonVariant> variantProperty() { return variant; }

    /** Returns the current variant. */
    public ButtonVariant getVariant() { return variant.get(); }

    /** Sets the variant. */
    public void setVariant(ButtonVariant v) { variant.set(v); }

    /** Returns the loading property. */
    public BooleanProperty loadingProperty() { return loading; }

    /** Returns whether this button is in loading state. */
    public boolean isLoading() { return loading.get(); }

    /** Sets the loading state. */
    public void setLoading(boolean v) { loading.set(v); }

    /** Returns the icon literal property. */
    public StringProperty iconLiteralProperty() { return iconLiteral; }

    /** Returns the current Ikonli icon literal, or {@code null} if none set. */
    public String getIconLiteral() { return iconLiteral.get(); }

    /** Sets the Ikonli icon literal. Pass {@code null} or empty to clear the icon. */
    public void setIconLiteral(String literal) { iconLiteral.set(literal); }

    /** Returns the icon position property. */
    public ObjectProperty<IconPosition> iconPositionProperty() { return iconPosition; }

    /** Returns the current icon position. */
    public IconPosition getIconPosition() { return iconPosition.get(); }

    /** Sets the icon position. */
    public void setIconPosition(IconPosition position) { iconPosition.set(position); }

    /** Returns the icon size property (pixels). */
    public IntegerProperty iconSizeProperty() { return iconSize; }

    /** Returns the current icon size in pixels. */
    public int getIconSize() { return iconSize.get(); }

    /** Sets the icon size in pixels. */
    public void setIconSize(int size) { iconSize.set(size); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxIconButton}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxIconButton}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty string</li>
     *   <li>variant — {@link ButtonVariant#PRIMARY}</li>
     *   <li>loading — {@code false}</li>
     *   <li>iconLiteral — {@code null} (no icon)</li>
     *   <li>iconPosition — {@link IconPosition#LEFT}</li>
     *   <li>iconSize — 16 px</li>
     *   <li>onAction — none</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxIconButton, Builder> {

        private String text = "";
        private ButtonVariant variant = ButtonVariant.PRIMARY;
        private boolean loading = false;
        private String iconLiteral;
        private IconPosition iconPosition = IconPosition.LEFT;
        private int iconSize = DEFAULT_ICON_SIZE;
        private EventHandler<ActionEvent> onAction;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder variant(ButtonVariant variant) {
            this.variant = variant;
            return this;
        }

        public Builder loading(boolean loading) {
            this.loading = loading;
            return this;
        }

        public Builder icon(String iconLiteral) {
            this.iconLiteral = iconLiteral;
            return this;
        }

        public Builder iconPosition(IconPosition position) {
            this.iconPosition = position;
            return this;
        }

        public Builder iconSize(int size) {
            this.iconSize = size;
            return this;
        }

        public Builder onAction(EventHandler<ActionEvent> handler) {
            this.onAction = handler;
            return this;
        }

        @Override
        public FxIconButton build() {
            FxIconButton button = new FxIconButton();
            button.setText(text);
            button.setVariant(variant);
            button.setIconPosition(iconPosition);
            button.setIconSize(iconSize);
            if (iconLiteral != null) {
                button.setIconLiteral(iconLiteral);
            }
            button.setLoading(loading);
            if (onAction != null) {
                button.setOnAction(onAction);
            }
            applyBase(button);
            return button;
        }
    }
}
