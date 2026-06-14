package io.forja.components.layout.fxStack;

import io.forja.tokens.SpacingSize;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * A vertical layout container with token-driven spacing presets.
 *
 * <p>{@code FxStack} extends {@link VBox} and exposes a {@code gap} property
 * typed as {@link SpacingSize} instead of the underlying {@code spacing}
 * pixel value. Setting the {@code gap} updates {@link VBox#setSpacing} to the
 * matching token pixels. The raw {@code spacing} API on {@link VBox} remains
 * available if needed.
 *
 * <p>The preferred way to construct an {@code FxStack} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxStack column = FxStack.builder()
 *          .gap(SpacingSize.LG)
 *          .children(heading, body, actions)
 *          .build();
 *     }
 * </pre>
 *
 * @see SpacingSize
 * @see FxRow
 * @see Builder
 */
public class FxStack extends VBox {

    private final ObjectProperty<SpacingSize> gap = new SimpleObjectProperty<>(this, "gap", SpacingSize.MD);

    /**
     * Creates an empty stack with default gap
     * ({@link SpacingSize#MD}).
     */
    public FxStack() {
        super();
        init();
    }

    /**
     * Creates a stack with the given gap.
     *
     * @param gap the spacing between children
     */
    public FxStack(SpacingSize gap) {
        super();
        init();
        setGap(gap);
    }

    /**
     * Creates a stack with the given gap and children.
     *
     * @param gap the spacing between children
     * @param children initial children
     */
    public FxStack(SpacingSize gap, Node... children) {
        super(children);
        init();
        setGap(gap);
    }

    private void init() {
        getStyleClass().add("forja-stack");
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

    /** Sets the gap token. Updates {@link VBox#setSpacing} to the matching pixel value. */
    public void setGap(SpacingSize v) { gap.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxStack}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxStack}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>gap — {@link SpacingSize#MD}</li>
     *   <li>children — empty</li>
     *   <li>alignment — {@link VBox}'s default (TOP_LEFT)</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxStack, Builder> {

        private SpacingSize gap = SpacingSize.MD;
        private final java.util.List<Node> children = new java.util.ArrayList<>();
        private Pos alignment;

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

        public FxStack build() {
            FxStack stack = new FxStack(gap);
            if (!children.isEmpty()) {
                stack.getChildren().addAll(children);
            }
            if (alignment != null) {
                stack.setAlignment(alignment);
            }
            applyBase(stack);
            return stack;
        }
    }
}
