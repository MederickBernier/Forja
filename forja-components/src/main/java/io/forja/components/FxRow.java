package io.forja.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * A horizontal layout container with token-driven spacing presets.
 *
 * <p>{@code FxRow} extends {@link HBox} and exposes a {@code gap} property
 * typed as {@link SpacingSize} instead of the underlying {@code spacing}
 * pixel value. Setting the {@code gap} updates {@link HBox#setSpacing} to
 * the matching token pixels. The raw {@code spacing} API on {@link HBox}
 * remains available if needed.
 *
 * <p>The preferred way to construct an {@code FxRow} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxRow toolbar = FxRow.builder()
 *          .gap(SpacingSize.SM)
 *          .alignment(Pos.CENTER_LEFT)
 *          .children(saveButton, cancelButton)
 *          .build();
 *     }
 * </pre>
 *
 * @see SpacingSize
 * @see FxStack
 * @see Builder
 */
public class FxRow extends HBox {

    private final ObjectProperty<SpacingSize> gap = new SimpleObjectProperty<>(this, "gap", SpacingSize.MD);

    /**
     * Creates an empty row with default gap ({@link SpacingSize#MD}).
     */
    public FxRow() {
        super();
        init();
    }

    /**
     * Creates a row with the given gap.
     *
     * @param gap the spacing between children
     */
    public FxRow(SpacingSize gap) {
        super();
        init();
        setGap(gap);
    }

    /**
     * Creates a row with the given gap and children.
     *
     * @param gap the spacing between children
     * @param children initial children
     */
    public FxRow(SpacingSize gap, Node... children) {
        super(children);
        init();
        setGap(gap);
    }

    private void init() {
        getStyleClass().add("forja-row");
        applyGap();
        gap.addListener((obs, old, val) -> applyGap());
    }

    private void applyGap() {
        super.setSpacing(getGap().pixels());
    }

    /** Returns the token-driven gap property. */
    public ObjectProperty<SpacingSize> gapProperty() { return gap; }

    /** Returns the current gap token. */
    public SpacingSize getGap() { return gap.get(); }

    /** Sets the gap token. Updates {@link HBox#setSpacing} to the matching pixel value. */
    public void setGap(SpacingSize v) { gap.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxRow}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxRow}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>gap — {@link SpacingSize#MD}</li>
     *   <li>children — empty</li>
     *   <li>alignment — {@link HBox}'s default (TOP_LEFT)</li>
     * </ul>
     */
    public static class Builder {

        private SpacingSize gap = SpacingSize.MD;
        private final java.util.List<Node> children = new java.util.ArrayList<>();
        private Pos alignment;
        private String id;
        private boolean visible = true;
        private final java.util.List<String> styleClasses = new java.util.ArrayList<>();
        private Object userData;

        public Builder gap(SpacingSize gap) {
            this.gap = gap;
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

        public FxRow build() {
            FxRow row = new FxRow(gap);
            if (!children.isEmpty()) {
                row.getChildren().addAll(children);
            }
            if (alignment != null) {
                row.setAlignment(alignment);
            }
            if (id != null) {
                row.setId(id);
            }
            row.setVisible(visible);
            if (!styleClasses.isEmpty()) {
                row.getStyleClass().addAll(styleClasses);
            }
            if (userData != null) {
                row.setUserData(userData);
            }
            return row;
        }
    }
}
