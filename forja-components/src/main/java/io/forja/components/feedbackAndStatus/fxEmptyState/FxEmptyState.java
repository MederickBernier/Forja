package io.forja.components.feedbackAndStatus.fxEmptyState;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * A centered "nothing here" panel — icon + heading + description + optional
 * action node.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxEmptyState empty = FxEmptyState.builder()
 *          .icon("fth-inbox")
 *          .heading("No projects yet")
 *          .description("Create your first project to get started.")
 *          .action(newProjectButton)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxEmptyState extends VBox {

    private final FxIcon icon = new FxIcon("fth-inbox");
    private final FxLabel headingLabel = new FxLabel("", LabelVariant.HEADING);
    private final FxLabel descLabel = new FxLabel("", LabelVariant.BODY);
    private final VBox actionSlot = new VBox();

    /**
     * Creates an empty {@code FxEmptyState}.
     */
    public FxEmptyState() {
        super();
        getStyleClass().add("forja-empty-state");
        setSpacing(8);
        setAlignment(Pos.CENTER);
        setFillWidth(false);
        icon.getStyleClass().add("forja-empty-state-icon");
        icon.setIconSize(48);
        headingLabel.getStyleClass().add("forja-empty-state-heading");
        descLabel.getStyleClass().add("forja-empty-state-description");
        descLabel.setMuted(true);
        actionSlot.setAlignment(Pos.CENTER);
        actionSlot.setSpacing(8);
        getChildren().addAll(icon, headingLabel, descLabel, actionSlot);
    }

    /** Sets the icon literal. */
    public void setIcon(String literal) { icon.setIconLiteral(literal); }

    /** Sets the heading text. */
    public void setHeading(String v) { headingLabel.setText(v == null ? "" : v); }

    /** Sets the description text. */
    public void setDescription(String v) { descLabel.setText(v == null ? "" : v); }

    /** Replaces the action-slot children. */
    public void setAction(Node... nodes) {
        actionSlot.getChildren().clear();
        if (nodes != null) for (Node n : nodes) if (n != null) actionSlot.getChildren().add(n);
    }

    /** Returns the icon. */
    public FxIcon getIcon() { return icon; }

    /** Returns the heading label. */
    public FxLabel getHeadingLabel() { return headingLabel; }

    /** Returns the description label. */
    public FxLabel getDescriptionLabel() { return descLabel; }

    /** Returns the action slot. */
    public VBox getActionSlot() { return actionSlot; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxEmptyState}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxEmptyState}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>icon — {@code "fth-inbox"}</li>
     *   <li>heading / description — empty</li>
     *   <li>action — none</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxEmptyState, Builder> {

        private String icon = "fth-inbox";
        private String heading = "";
        private String description = "";
        private Node[] action = new Node[0];

        public Builder icon(String icon) { this.icon = icon == null ? "" : icon; return this; }
        public Builder heading(String heading) { this.heading = heading == null ? "" : heading; return this; }
        public Builder description(String description) { this.description = description == null ? "" : description; return this; }
        public Builder action(Node... action) { this.action = action == null ? new Node[0] : action; return this; }

        @Override
        public FxEmptyState build() {
            FxEmptyState e = new FxEmptyState();
            e.setIcon(icon);
            e.setHeading(heading);
            e.setDescription(description);
            e.setAction(action);
            applyBase(e);
            return e;
        }
    }
}
