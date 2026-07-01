package io.forja.components.feedbackAndStatus.fxResultPage;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * A full-page success or failure summary — large icon + title + description
 * + action row.
 *
 * <p>{@code FxResultPage} chooses an icon color and glyph based on a
 * {@link Status} (success/failure/pending/warning) and exposes a
 * {@code :success}/{@code :failure}/{@code :pending}/{@code :warning}
 * pseudo-class for theming.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxResultPage done = FxResultPage.builder()
 *          .status(FxResultPage.Status.SUCCESS)
 *          .title("Payment received")
 *          .description("Order #12345 is on its way.")
 *          .action(backButton)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxResultPage extends VBox {

    /** Result status variants. */
    public enum Status { SUCCESS, FAILURE, PENDING, WARNING }

    private static final PseudoClass SUCCESS_PC = PseudoClass.getPseudoClass("success");
    private static final PseudoClass FAILURE_PC = PseudoClass.getPseudoClass("failure");
    private static final PseudoClass PENDING_PC = PseudoClass.getPseudoClass("pending");
    private static final PseudoClass WARNING_PC = PseudoClass.getPseudoClass("warning");

    private final FxIcon icon = new FxIcon("fth-check-circle");
    private final FxLabel titleLabel = new FxLabel("", LabelVariant.DISPLAY);
    private final FxLabel descLabel = new FxLabel("", LabelVariant.BODY);
    private final VBox actionRow = new VBox();

    private final ObjectProperty<Status> status = new SimpleObjectProperty<>(this, "status", Status.SUCCESS);

    /**
     * Creates an empty {@code FxResultPage} in the success state.
     */
    public FxResultPage() {
        super();
        getStyleClass().add("forja-result-page");
        setSpacing(12);
        setAlignment(Pos.CENTER);
        icon.setIconSize(64);
        icon.getStyleClass().add("forja-result-page-icon");
        titleLabel.getStyleClass().add("forja-result-page-title");
        descLabel.getStyleClass().add("forja-result-page-description");
        descLabel.setMuted(true);
        actionRow.setSpacing(8);
        actionRow.setAlignment(Pos.CENTER);

        getChildren().addAll(icon, titleLabel, descLabel, actionRow);

        status.addListener((obs, o, v) -> applyStatus());
        applyStatus();
    }

    private void applyStatus() {
        pseudoClassStateChanged(SUCCESS_PC, false);
        pseudoClassStateChanged(FAILURE_PC, false);
        pseudoClassStateChanged(PENDING_PC, false);
        pseudoClassStateChanged(WARNING_PC, false);
        switch (getStatus()) {
            case SUCCESS: pseudoClassStateChanged(SUCCESS_PC, true); icon.setIconLiteral("fth-check-circle"); break;
            case FAILURE: pseudoClassStateChanged(FAILURE_PC, true); icon.setIconLiteral("fth-x-circle"); break;
            case PENDING: pseudoClassStateChanged(PENDING_PC, true); icon.setIconLiteral("fth-clock"); break;
            case WARNING: pseudoClassStateChanged(WARNING_PC, true); icon.setIconLiteral("fth-alert-triangle"); break;
        }
    }

    /** Returns the icon node. */
    public FxIcon getIcon() { return icon; }

    /** Returns the title label. */
    public FxLabel getTitleLabel() { return titleLabel; }

    /** Returns the description label. */
    public FxLabel getDescriptionLabel() { return descLabel; }

    /** Returns the action row. */
    public VBox getActionRow() { return actionRow; }

    /** Returns the status property. */
    public ObjectProperty<Status> statusProperty() { return status; }
    /** Returns the current status. */
    public Status getStatus() { return status.get(); }
    /** Sets the status. */
    public void setStatus(Status v) { status.set(v == null ? Status.SUCCESS : v); }

    /** Sets the title. */
    public void setTitle(String v) { titleLabel.setText(v == null ? "" : v); }
    /** Sets the description. */
    public void setDescription(String v) { descLabel.setText(v == null ? "" : v); }
    /** Replaces the action row children. */
    public void setAction(Node... nodes) {
        actionRow.getChildren().clear();
        if (nodes != null) for (Node n : nodes) if (n != null) actionRow.getChildren().add(n);
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxResultPage}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxResultPage}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>status — {@link Status#SUCCESS}</li>
     *   <li>title / description — empty</li>
     *   <li>action — none</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxResultPage, Builder> {

        private Status status = Status.SUCCESS;
        private String title = "";
        private String description = "";
        private Node[] action = new Node[0];

        public Builder status(Status status) { this.status = status == null ? Status.SUCCESS : status; return this; }
        public Builder title(String title) { this.title = title == null ? "" : title; return this; }
        public Builder description(String description) { this.description = description == null ? "" : description; return this; }
        public Builder action(Node... action) { this.action = action == null ? new Node[0] : action; return this; }

        @Override
        public FxResultPage build() {
            FxResultPage p = new FxResultPage();
            p.setStatus(status);
            p.setTitle(title);
            p.setDescription(description);
            p.setAction(action);
            applyBase(p);
            return p;
        }
    }
}
