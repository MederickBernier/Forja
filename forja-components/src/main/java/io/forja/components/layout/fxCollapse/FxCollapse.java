package io.forja.components.layout.fxCollapse;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * A single-section collapsible: header + expandable content.
 *
 * <p>{@code FxCollapse} is a {@link VBox} of a clickable header row (title +
 * chevron icon) and a body slot that shows/hides on toggle. The chevron
 * rotates via the {@code :expanded} pseudo-class on the outer node.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxCollapse details = FxCollapse.builder()
 *          .title("Details")
 *          .content(detailsBody)
 *          .expanded(false)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxCollapse extends VBox {

    private static final PseudoClass EXPANDED_PC = PseudoClass.getPseudoClass("expanded");

    private final HBox header = new HBox();
    private final FxLabel titleLabel = new FxLabel("", LabelVariant.BODY);
    private final FxIcon chevron = new FxIcon("fth-chevron-right");
    private final VBox body = new VBox();

    private final StringProperty title = new SimpleStringProperty(this, "title", "");
    private final BooleanProperty expanded = new SimpleBooleanProperty(this, "expanded", false);
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>(this, "content");

    /**
     * Creates a collapsed {@code FxCollapse} with no title or content.
     */
    public FxCollapse() {
        super();
        getStyleClass().add("forja-collapse");
        setSpacing(0);

        header.getStyleClass().add("forja-collapse-header");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(8);
        titleLabel.getStyleClass().add("forja-collapse-title");
        chevron.getStyleClass().add("forja-collapse-chevron");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(titleLabel, spacer, chevron);
        header.setOnMouseClicked(e -> { toggle(); e.consume(); });

        body.getStyleClass().add("forja-collapse-body");
        body.setVisible(false);
        body.setManaged(false);

        getChildren().addAll(header, body);

        title.addListener((obs, o, v) -> titleLabel.setText(v == null ? "" : v));
        expanded.addListener((obs, o, v) -> {
            pseudoClassStateChanged(EXPANDED_PC, v);
            body.setVisible(v);
            body.setManaged(v);
            chevron.setRotate(v ? 90 : 0);
        });
        content.addListener((obs, o, v) -> {
            body.getChildren().clear();
            if (v != null) body.getChildren().add(v);
        });
    }

    /** Flips the expanded state. */
    public void toggle() { setExpanded(!isExpanded()); }

    /** Returns the header row. */
    public HBox getHeader() { return header; }

    /** Returns the title label. */
    public FxLabel getTitleLabel() { return titleLabel; }

    /** Returns the chevron icon. */
    public FxIcon getChevron() { return chevron; }

    /** Returns the body VBox. */
    public VBox getBody() { return body; }

    /** Returns the title property. */
    public StringProperty titleProperty() { return title; }

    /** Returns the current title. */
    public String getTitle() { return title.get(); }

    /** Sets the title. */
    public void setTitle(String v) { title.set(v == null ? "" : v); }

    /** Returns the expanded property. */
    public BooleanProperty expandedProperty() { return expanded; }

    /** Returns whether the section is expanded. */
    public boolean isExpanded() { return expanded.get(); }

    /** Sets whether the section is expanded. */
    public void setExpanded(boolean v) { expanded.set(v); }

    /** Returns the content property. */
    public ObjectProperty<Node> contentProperty() { return content; }

    /** Returns the current content node. */
    public Node getContent() { return content.get(); }

    /** Sets the content node. */
    public void setContent(Node v) { content.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxCollapse}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxCollapse}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>title — empty</li>
     *   <li>content — {@code null}</li>
     *   <li>expanded — {@code false}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxCollapse, Builder> {

        private String title = "";
        private Node content;
        private boolean expanded = false;

        public Builder title(String title) { this.title = title == null ? "" : title; return this; }
        public Builder content(Node content) { this.content = content; return this; }
        public Builder expanded(boolean expanded) { this.expanded = expanded; return this; }

        @Override
        public FxCollapse build() {
            FxCollapse c = new FxCollapse();
            c.setTitle(title);
            c.setContent(content);
            c.setExpanded(expanded);
            applyBase(c);
            return c;
        }
    }
}
