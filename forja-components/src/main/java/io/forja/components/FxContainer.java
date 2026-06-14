package io.forja.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * A max-width-capped, centered content container.
 *
 * <p>{@code FxContainer} extends {@link StackPane} and caps its width at a
 * token-aligned {@link ContainerWidth} breakpoint. Token-aligned padding is
 * applied on all sides via a {@link SpacingSize}. Place the container in a
 * parent that centers its children (e.g. a {@link javafx.scene.layout.VBox}
 * with {@link Pos#CENTER} alignment, or a
 * {@link javafx.scene.layout.BorderPane#setCenter}) to get the canonical
 * centered-content layout.
 *
 * <p>The preferred way to construct an {@code FxContainer} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxContainer page = FxContainer.builder()
 *          .width(ContainerWidth.MD)
 *          .padding(SpacingSize.XL)
 *          .child(content)
 *          .build();
 *     }
 * </pre>
 *
 * @see ContainerWidth
 * @see SpacingSize
 * @see Builder
 */
public class FxContainer extends StackPane {

    private final ObjectProperty<ContainerWidth> containerWidth = new SimpleObjectProperty<>(this, "containerWidth", ContainerWidth.LG);
    private final ObjectProperty<SpacingSize> contentPadding = new SimpleObjectProperty<>(this, "contentPadding", SpacingSize.LG);

    /**
     * Creates an empty container with default width
     * ({@link ContainerWidth#LG}) and default padding
     * ({@link SpacingSize#LG}).
     */
    public FxContainer() {
        super();
        init();
    }

    /**
     * Creates a container with the given width cap.
     *
     * @param width max-width breakpoint
     */
    public FxContainer(ContainerWidth containerWidth) {
        super();
        init();
        setContainerWidth(containerWidth);
    }

    /**
     * Creates a container with the given width cap and children.
     *
     * @param containerWidth max-width breakpoint
     * @param children initial children
     */
    public FxContainer(ContainerWidth containerWidth, Node... children) {
        super(children);
        init();
        setContainerWidth(containerWidth);
    }

    private void init() {
        getStyleClass().add("forja-container");
        setAlignment(Pos.TOP_CENTER);
        applyContainerWidth();
        applyContentPadding();
        containerWidth.addListener((obs, old, val) -> applyContainerWidth());
        contentPadding.addListener((obs, old, val) -> applyContentPadding());
    }

    private void applyContainerWidth() {
        setMaxWidth(getContainerWidth().pixels());
    }

    private void applyContentPadding() {
        double pad = getContentPadding().pixels();
        setPadding(new Insets(pad));
    }

    /** Returns the container-width property. */
    public ObjectProperty<ContainerWidth> containerWidthProperty() { return containerWidth; }

    /** Returns the current container width breakpoint. */
    public ContainerWidth getContainerWidth() { return containerWidth.get(); }

    /** Sets the container width breakpoint. */
    public void setContainerWidth(ContainerWidth v) { containerWidth.set(v); }

    /** Returns the content-padding token property. */
    public ObjectProperty<SpacingSize> contentPaddingProperty() { return contentPadding; }

    /** Returns the current content-padding token. */
    public SpacingSize getContentPadding() { return contentPadding.get(); }

    /** Sets the content-padding token. Applied as equal Insets on all sides. */
    public void setContentPadding(SpacingSize v) { contentPadding.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxContainer}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxContainer}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>width — {@link ContainerWidth#LG}</li>
     *   <li>padding — {@link SpacingSize#LG}</li>
     *   <li>children — empty</li>
     * </ul>
     */
    public static class Builder {

        private ContainerWidth containerWidth = ContainerWidth.LG;
        private SpacingSize contentPadding = SpacingSize.LG;
        private final java.util.List<Node> children = new java.util.ArrayList<>();
        private Pos alignment = Pos.TOP_CENTER;
        private String id;
        private boolean visible = true;
        private final java.util.List<String> styleClasses = new java.util.ArrayList<>();
        private Object userData;

        public Builder width(ContainerWidth width) {
            this.containerWidth = width;
            return this;
        }

        public Builder padding(SpacingSize padding) {
            this.contentPadding = padding;
            return this;
        }

        public Builder children(Node... nodes) {
            for (Node n : nodes) {
                if (n != null) {
                    children.add(n);
                }
            }
            return this;
        }

        public Builder child(Node node) {
            if (node != null) {
                children.add(node);
            }
            return this;
        }

        public Builder alignment(Pos alignment) {
            this.alignment = alignment;
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

        public FxContainer build() {
            FxContainer container = new FxContainer(containerWidth);
            container.setContentPadding(contentPadding);
            if (!children.isEmpty()) {
                container.getChildren().addAll(children);
            }
            if (alignment != null) {
                container.setAlignment(alignment);
            }
            if (id != null) {
                container.setId(id);
            }
            container.setVisible(visible);
            if (!styleClasses.isEmpty()) {
                container.getStyleClass().addAll(styleClasses);
            }
            if (userData != null) {
                container.setUserData(userData);
            }
            return container;
        }
    }
}
