package io.forja.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * A labeled content section composed of a title, optional leading separator,
 * and arbitrary content children.
 *
 * <p>{@code FxSection} extends {@link VBox} and renders, in order:</p>
 * <ol>
 *   <li>An {@link FxSeparator} ({@link SeparatorVariant#DEFAULT}) when
 *       {@link #setSeparator setSeparator(true)} is enabled.</li>
 *   <li>An {@link FxLabel} ({@link LabelVariant#SUBHEADING}) carrying the
 *       title.</li>
 *   <li>The user-supplied {@code content} nodes, in the order they were
 *       added.</li>
 * </ol>
 *
 * <p>The preferred way to construct an {@code FxSection} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxSection section = FxSection.builder()
 *          .title("Buttons")
 *          .separator(true)
 *          .content(primaryRow, secondaryRow)
 *          .gap(SpacingSize.MD)
 *          .build();
 *     }
 * </pre>
 *
 * @see FxLabel
 * @see FxSeparator
 * @see Builder
 */
public class FxSection extends VBox {

    private final StringProperty title = new SimpleStringProperty(this, "title", "");
    private final BooleanProperty separator = new SimpleBooleanProperty(this, "separator", false);
    private final ObjectProperty<SpacingSize> gap = new SimpleObjectProperty<>(this, "gap", SpacingSize.SM);
    private final ObservableList<Node> content = FXCollections.observableArrayList();

    private final FxLabel titleLabel;
    private final FxSeparator topSeparator;

    /**
     * Creates an empty section.
     */
    public FxSection() {
        super();
        getStyleClass().add("forja-section");

        topSeparator = new FxSeparator();
        topSeparator.getStyleClass().add("forja-section-separator");

        titleLabel = new FxLabel("", LabelVariant.SUBHEADING);
        titleLabel.getStyleClass().add("forja-section-title");

        title.addListener((obs, old, val) -> {
            refreshTitle();
            rebuild();
        });
        separator.addListener((obs, old, val) -> rebuild());
        gap.addListener((obs, old, val) -> applyGap());
        content.addListener((ListChangeListener<Node>) c -> rebuild());

        applyGap();
        refreshTitle();
        rebuild();
    }

    /**
     * Creates a section with the given title.
     *
     * @param title section heading
     */
    public FxSection(String title) {
        this();
        setTitle(title);
    }

    /**
     * Creates a section with the given title and content.
     *
     * @param title section heading
     * @param content child nodes rendered below the title
     */
    public FxSection(String title, Node... content) {
        this();
        setTitle(title);
        if (content != null) {
            this.content.addAll(content);
        }
    }

    private void applyGap() {
        super.setSpacing(getGap().pixels());
    }

    private void refreshTitle() {
        String value = getTitle();
        titleLabel.setText(value == null ? "" : value);
    }

    private void rebuild() {
        getChildren().clear();
        if (isSeparator()) {
            getChildren().add(topSeparator);
        }
        String value = getTitle();
        boolean hasTitle = value != null && !value.isEmpty();
        if (hasTitle) {
            getChildren().add(titleLabel);
        }
        getChildren().addAll(content);
    }

    /** Returns the title property. */
    public StringProperty titleProperty() { return title; }

    /** Returns the current title text. */
    public String getTitle() { return title.get(); }

    /** Sets the title text. Empty/null hides the title label. */
    public void setTitle(String v) { title.set(v); }

    /** Returns the separator-visible property. */
    public BooleanProperty separatorProperty() { return separator; }

    /** Returns whether the leading separator is shown. */
    public boolean isSeparator() { return separator.get(); }

    /** Sets whether to render a leading {@link FxSeparator} above the title. */
    public void setSeparator(boolean v) { separator.set(v); }

    /** Returns the gap token property. */
    public ObjectProperty<SpacingSize> gapProperty() { return gap; }

    /** Returns the current gap token. */
    public SpacingSize getGap() { return gap.get(); }

    /** Sets the gap token between separator, title, and content children. */
    public void setGap(SpacingSize v) { gap.set(v); }

    /**
     * Returns the mutable content list. Mutating triggers a rebuild.
     */
    public ObservableList<Node> getContent() { return content; }

    /** Returns the internal title label — for advanced styling. */
    public FxLabel getTitleLabel() { return titleLabel; }

    /** Returns the internal top separator — for advanced styling. */
    public FxSeparator getTopSeparator() { return topSeparator; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSection}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxSection}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>title — empty (title label hidden)</li>
     *   <li>separator — {@code false}</li>
     *   <li>gap — {@link SpacingSize#SM}</li>
     *   <li>content — empty</li>
     * </ul>
     */
    public static class Builder {

        private String title = "";
        private boolean separator = false;
        private SpacingSize gap = SpacingSize.SM;
        private final java.util.List<Node> content = new java.util.ArrayList<>();
        private String id;
        private boolean visible = true;
        private final java.util.List<String> styleClasses = new java.util.ArrayList<>();
        private Object userData;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder separator(boolean separator) {
            this.separator = separator;
            return this;
        }

        public Builder gap(SpacingSize gap) {
            this.gap = gap;
            return this;
        }

        public Builder content(Node... nodes) {
            for (Node n : nodes) {
                if (n != null) {
                    content.add(n);
                }
            }
            return this;
        }

        public Builder child(Node node) {
            if (node != null) {
                content.add(node);
            }
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

        public FxSection build() {
            FxSection section = new FxSection();
            section.setTitle(title);
            section.setSeparator(separator);
            section.setGap(gap);
            if (!content.isEmpty()) {
                section.getContent().addAll(content);
            }
            if (id != null) {
                section.setId(id);
            }
            section.setVisible(visible);
            if (!styleClasses.isEmpty()) {
                section.getStyleClass().addAll(styleClasses);
            }
            if (userData != null) {
                section.setUserData(userData);
            }
            return section;
        }
    }
}
