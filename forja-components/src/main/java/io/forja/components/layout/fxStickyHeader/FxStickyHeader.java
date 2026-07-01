package io.forja.components.layout.fxStickyHeader;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * A container that pins its first child (the header) to the top of a host
 * {@link ScrollPane}'s viewport while the rest of the content scrolls
 * underneath.
 *
 * <p>{@code FxStickyHeader} is a {@link VBox} of {@code header + body}.
 * When set on a {@link ScrollPane} via {@link #attachTo}, the header's
 * translateY tracks the scroll offset so it stays fixed at the top of the
 * viewport.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxStickyHeader sticky = FxStickyHeader.builder()
 *          .header(sectionTitle)
 *          .body(longContent)
 *          .build();
 *      scrollPane.setContent(sticky);
 *      sticky.attachTo(scrollPane);
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxStickyHeader extends VBox {

    private final ObjectProperty<Node> header = new SimpleObjectProperty<>(this, "header");
    private final ObjectProperty<Node> body = new SimpleObjectProperty<>(this, "body");
    private ScrollPane host;

    /**
     * Creates an empty {@code FxStickyHeader}.
     */
    public FxStickyHeader() {
        super();
        getStyleClass().add("forja-sticky-header");

        header.addListener((obs, o, v) -> rebuild());
        body.addListener((obs, o, v) -> rebuild());
    }

    private void rebuild() {
        getChildren().clear();
        Node h = getHeader();
        Node b = getBody();
        if (h != null) getChildren().add(h);
        if (b != null) getChildren().add(b);
    }

    /**
     * Wires the header's translateY to track the given scroll pane's vvalue
     * so the header appears pinned to the top of its viewport.
     *
     * @param scrollPane the host scroll pane
     */
    public void attachTo(ScrollPane scrollPane) {
        this.host = scrollPane;
        if (scrollPane == null) return;
        scrollPane.vvalueProperty().addListener((obs, o, v) -> updateHeaderOffset());
        scrollPane.viewportBoundsProperty().addListener((obs, o, v) -> updateHeaderOffset());
        boundsInLocalProperty().addListener((obs, o, v) -> updateHeaderOffset());
        updateHeaderOffset();
    }

    private void updateHeaderOffset() {
        Node h = getHeader();
        if (h == null || host == null) return;
        double vp = host.getViewportBounds().getHeight();
        double contentH = getBoundsInLocal().getHeight();
        if (contentH <= vp) { h.setTranslateY(0); return; }
        double offset = (contentH - vp) * host.getVvalue();
        h.setTranslateY(offset);
    }

    /** Returns the header property. */
    public ObjectProperty<Node> headerProperty() { return header; }

    /** Returns the current header node. */
    public Node getHeader() { return header.get(); }

    /** Sets the header node. */
    public void setHeader(Node v) { header.set(v); }

    /** Returns the body property. */
    public ObjectProperty<Node> bodyProperty() { return body; }

    /** Returns the current body node. */
    public Node getBody() { return body.get(); }

    /** Sets the body node. */
    public void setBody(Node v) { body.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxStickyHeader}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxStickyHeader}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>header / body — {@code null}</li>
     *   <li>attachTo — {@code null}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxStickyHeader, Builder> {

        private Node header;
        private Node body;
        private ScrollPane attachTo;

        public Builder header(Node header) { this.header = header; return this; }
        public Builder body(Node body) { this.body = body; return this; }
        public Builder attachTo(ScrollPane attachTo) { this.attachTo = attachTo; return this; }

        @Override
        public FxStickyHeader build() {
            FxStickyHeader s = new FxStickyHeader();
            s.setHeader(header);
            s.setBody(body);
            if (attachTo != null) s.attachTo(attachTo);
            applyBase(s);
            return s;
        }
    }
}
