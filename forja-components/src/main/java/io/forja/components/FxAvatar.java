package io.forja.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

/**
 * A circular avatar that renders an image with an initials fallback.
 *
 * <p>{@code FxAvatar} composes an {@link ImageView} and a {@link Label} inside
 * a {@link StackPane} with a circular clip. When {@link #setSource} is given
 * a valid URL and the image loads, it covers the initials label. When the
 * source is null/empty or the image fails to load, the initials remain
 * visible.
 *
 * <p>The preferred way to construct an {@code FxAvatar} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxAvatar user = FxAvatar.builder()
 *          .source("https://example.com/avatar.png")
 *          .initials("MB")
 *          .size(AvatarSize.DEFAULT)
 *          .build();
 *     }
 * </pre>
 *
 * <p>Like {@link FxIcon}, {@code FxAvatar} is not a JavaFX
 * {@link javafx.scene.control.Control} — its builder is standalone.
 *
 * @see AvatarSize
 * @see Builder
 */
public class FxAvatar extends StackPane {

    private static final PseudoClass COMPACT     = PseudoClass.getPseudoClass("compact");
    private static final PseudoClass DEFAULT_PC  = PseudoClass.getPseudoClass("default");
    private static final PseudoClass COMFORTABLE = PseudoClass.getPseudoClass("comfortable");

    private final StringProperty source = new SimpleStringProperty(this, "source");
    private final StringProperty initials = new SimpleStringProperty(this, "initials", "");
    private final ObjectProperty<AvatarSize> size = new SimpleObjectProperty<>(this, "size", AvatarSize.DEFAULT);

    private final ImageView imageView;
    private final Label initialsLabel;
    private final Circle clip;

    /**
     * Creates an empty avatar at the default size with no source or initials.
     */
    public FxAvatar() {
        getStyleClass().add("forja-avatar");

        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.getStyleClass().add("forja-avatar-image");

        initialsLabel = new Label();
        initialsLabel.getStyleClass().add("forja-avatar-initials");

        clip = new Circle();
        clip.radiusProperty().bind(widthProperty().divide(2));
        clip.centerXProperty().bind(widthProperty().divide(2));
        clip.centerYProperty().bind(heightProperty().divide(2));
        setClip(clip);

        getChildren().addAll(initialsLabel, imageView);

        source.addListener((obs, old, val) -> refreshImage());
        initials.addListener((obs, old, val) -> refreshInitials());
        size.addListener((obs, old, val) -> applySize());

        applySize();
        refreshImage();
        refreshInitials();
    }

    /**
     * Creates an avatar with the given initials at the default size.
     *
     * @param initials short initials string, e.g. {@code "MB"}
     */
    public FxAvatar(String initials) {
        this();
        setInitials(initials);
    }

    /**
     * Creates an avatar with the given initials and size.
     *
     * @param initials short initials string
     * @param size avatar diameter variant
     */
    public FxAvatar(String initials, AvatarSize size) {
        this();
        setInitials(initials);
        setSize(size);
    }

    private void applySize() {
        double diameter = getSize().diameter();
        setMinSize(diameter, diameter);
        setPrefSize(diameter, diameter);
        setMaxSize(diameter, diameter);
        imageView.setFitWidth(diameter);
        imageView.setFitHeight(diameter);

        pseudoClassStateChanged(COMPACT,     false);
        pseudoClassStateChanged(DEFAULT_PC,  false);
        pseudoClassStateChanged(COMFORTABLE, false);
        switch (getSize()) {
            case COMPACT:     pseudoClassStateChanged(COMPACT,     true); break;
            case DEFAULT:     pseudoClassStateChanged(DEFAULT_PC,  true); break;
            case COMFORTABLE: pseudoClassStateChanged(COMFORTABLE, true); break;
        }
    }

    private void refreshImage() {
        String src = getSource();
        if (src == null || src.isEmpty()) {
            imageView.setImage(null);
            imageView.setVisible(false);
            return;
        }
        Image img = new Image(src, true);
        imageView.setImage(img);
        imageView.setVisible(true);
        img.errorProperty().addListener((obs, old, hadError) -> {
            if (Boolean.TRUE.equals(hadError) && imageView.getImage() == img) {
                imageView.setVisible(false);
            }
        });
        if (img.isError()) {
            imageView.setVisible(false);
        }
    }

    private void refreshInitials() {
        String value = getInitials();
        initialsLabel.setText(value == null ? "" : value);
    }

    /** Returns the source property (Image URL string). */
    public StringProperty sourceProperty() { return source; }

    /** Returns the current image source URL, or {@code null}. */
    public String getSource() { return source.get(); }

    /** Sets the image source URL. {@code null} or empty clears the image. */
    public void setSource(String v) { source.set(v); }

    /** Returns the initials property (fallback text). */
    public StringProperty initialsProperty() { return initials; }

    /** Returns the current initials string. */
    public String getInitials() { return initials.get(); }

    /** Sets the initials shown when no image is loaded. */
    public void setInitials(String v) { initials.set(v); }

    /** Returns the size property. */
    public ObjectProperty<AvatarSize> sizeProperty() { return size; }

    /** Returns the current size variant. */
    public AvatarSize getSize() { return size.get(); }

    /** Sets the size variant. */
    public void setSize(AvatarSize v) { size.set(v); }

    /** Returns the inner ImageView — for advanced styling/binding. */
    public ImageView getImageView() { return imageView; }

    /** Returns the inner initials Label — for advanced styling/binding. */
    public Label getInitialsLabel() { return initialsLabel; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxAvatar}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxAvatar}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>source — {@code null}</li>
     *   <li>initials — empty string</li>
     *   <li>size — {@link AvatarSize#DEFAULT}</li>
     * </ul>
     */
    public static class Builder {

        private String source;
        private String initials = "";
        private AvatarSize size = AvatarSize.DEFAULT;
        private String id;
        private boolean visible = true;
        private final java.util.List<String> styleClasses = new java.util.ArrayList<>();
        private Object userData;

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder initials(String initials) {
            this.initials = initials;
            return this;
        }

        public Builder size(AvatarSize size) {
            this.size = size;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder styleClass(String... classes) {
            for (String c : classes) {
                if (c != null && !c.isEmpty()) {
                    styleClasses.add(c);
                }
            }
            return this;
        }

        public Builder userData(Object userData) {
            this.userData = userData;
            return this;
        }

        public FxAvatar build() {
            FxAvatar avatar = new FxAvatar();
            avatar.setInitials(initials);
            avatar.setSize(size);
            if (source != null) {
                avatar.setSource(source);
            }
            if (id != null) {
                avatar.setId(id);
            }
            avatar.setVisible(visible);
            if (!styleClasses.isEmpty()) {
                avatar.getStyleClass().addAll(styleClasses);
            }
            if (userData != null) {
                avatar.setUserData(userData);
            }
            return avatar;
        }
    }
}
