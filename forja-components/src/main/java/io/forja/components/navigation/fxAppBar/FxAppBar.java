package io.forja.components.navigation.fxAppBar;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * A top app bar with a leading slot (menu/back), a title, and a trailing
 * action slot.
 *
 * <p>Aliased as {@code FxNavbar} in docs — same component.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxAppBar bar = FxAppBar.builder()
 *          .leading(menuButton)
 *          .title("Forja Demo")
 *          .actions(themeToggle, settingsButton)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxAppBar extends HBox {

    private final FxLabel titleLabel = new FxLabel("", LabelVariant.HEADING);
    private final HBox leading = new HBox();
    private final HBox actions = new HBox();
    private final Region spacer = new Region();

    /**
     * Creates an empty {@code FxAppBar}.
     */
    public FxAppBar() {
        super();
        getStyleClass().add("forja-app-bar");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(10);
        leading.setAlignment(Pos.CENTER_LEFT);
        leading.setSpacing(8);
        actions.setAlignment(Pos.CENTER_RIGHT);
        actions.setSpacing(8);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        titleLabel.getStyleClass().add("forja-app-bar-title");
        getChildren().addAll(leading, titleLabel, spacer, actions);
    }

    /** Returns the leading (start) row. */
    public HBox getLeading() { return leading; }

    /** Returns the title label. */
    public FxLabel getTitleLabel() { return titleLabel; }

    /** Returns the actions (end) row. */
    public HBox getActions() { return actions; }

    /** Sets the title text. */
    public void setTitle(String v) { titleLabel.setText(v == null ? "" : v); }

    /** Returns the current title. */
    public String getTitle() { return titleLabel.getText(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxAppBar}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxAppBar}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>leading / actions — empty</li>
     *   <li>title — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxAppBar, Builder> {

        private Node[] leading = new Node[0];
        private Node[] actions = new Node[0];
        private String title = "";

        public Builder leading(Node... leading) { this.leading = leading == null ? new Node[0] : leading; return this; }
        public Builder actions(Node... actions) { this.actions = actions == null ? new Node[0] : actions; return this; }
        public Builder title(String title) { this.title = title == null ? "" : title; return this; }

        @Override
        public FxAppBar build() {
            FxAppBar b = new FxAppBar();
            b.setTitle(title);
            b.getLeading().getChildren().addAll(leading);
            b.getActions().getChildren().addAll(actions);
            applyBase(b);
            return b;
        }
    }
}
