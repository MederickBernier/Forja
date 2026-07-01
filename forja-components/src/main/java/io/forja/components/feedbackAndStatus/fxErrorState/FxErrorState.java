package io.forja.components.feedbackAndStatus.fxErrorState;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.feedbackAndStatus.fxEmptyState.FxEmptyState;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * An error-flavored empty state — wraps {@link FxEmptyState} with an
 * alert-triangle icon and the {@code forja-error-state} style class so CSS
 * can tint headings + icons red.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxErrorState err = FxErrorState.builder()
 *          .heading("Something went wrong")
 *          .description("Please retry in a few moments.")
 *          .action(retryButton)
 *          .build();
 *     }
 * </pre>
 *
 * @see FxEmptyState
 * @see Builder
 */
public class FxErrorState extends VBox {

    private final FxEmptyState inner;

    /**
     * Creates an empty {@code FxErrorState}.
     */
    public FxErrorState() {
        super();
        getStyleClass().add("forja-error-state");
        inner = FxEmptyState.builder().icon("fth-alert-triangle").build();
        inner.getStyleClass().add("forja-error-state-inner");
        getChildren().add(inner);
    }

    /** Returns the underlying empty-state. */
    public FxEmptyState getInner() { return inner; }

    /** Sets the icon literal. */
    public void setIcon(String literal) { inner.setIcon(literal); }
    /** Sets the heading. */
    public void setHeading(String v) { inner.setHeading(v); }
    /** Sets the description. */
    public void setDescription(String v) { inner.setDescription(v); }
    /** Sets the action nodes. */
    public void setAction(Node... nodes) { inner.setAction(nodes); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxErrorState}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxErrorState}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>icon — {@code "fth-alert-triangle"}</li>
     *   <li>heading / description — empty</li>
     *   <li>action — none</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxErrorState, Builder> {

        private String icon = "fth-alert-triangle";
        private String heading = "";
        private String description = "";
        private Node[] action = new Node[0];

        public Builder icon(String icon) { this.icon = icon == null ? "" : icon; return this; }
        public Builder heading(String heading) { this.heading = heading == null ? "" : heading; return this; }
        public Builder description(String description) { this.description = description == null ? "" : description; return this; }
        public Builder action(Node... action) { this.action = action == null ? new Node[0] : action; return this; }

        @Override
        public FxErrorState build() {
            FxErrorState e = new FxErrorState();
            e.setIcon(icon);
            e.setHeading(heading);
            e.setDescription(description);
            e.setAction(action);
            applyBase(e);
            return e;
        }
    }
}
