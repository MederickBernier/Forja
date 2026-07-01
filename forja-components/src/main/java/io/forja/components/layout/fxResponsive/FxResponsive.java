package io.forja.components.layout.fxResponsive;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A responsive container that swaps its visible child based on the current
 * width and a set of named breakpoints.
 *
 * <p>Breakpoints are registered with a minimum width (px). At layout time,
 * {@code FxResponsive} picks the entry with the greatest {@code minWidth ≤ getWidth()}
 * and displays its node. The chosen breakpoint's name is exposed through
 * {@link #currentBreakpointProperty()}.
 *
 * <p>Predefined defaults follow Tailwind-style breakpoints ({@code sm=640},
 * {@code md=768}, {@code lg=1024}, {@code xl=1280}). Callers register the
 * child for each breakpoint they care about via
 * {@link Builder#at(String, double, Node) at(name, minWidth, node)}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxResponsive layout = FxResponsive.builder()
 *          .at("base", 0, mobileLayout)
 *          .at("md", 768, tabletLayout)
 *          .at("lg", 1024, desktopLayout)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxResponsive extends StackPane {

    private final Map<String, Entry> entries = new LinkedHashMap<>();
    private final StringProperty currentBreakpoint = new SimpleStringProperty(this, "currentBreakpoint", "");
    private final ObjectProperty<Node> currentNode = new SimpleObjectProperty<>(this, "currentNode");

    /**
     * Creates an empty {@code FxResponsive}.
     */
    public FxResponsive() {
        super();
        getStyleClass().add("forja-responsive");
        widthProperty().addListener((obs, o, v) -> updateSelection());
    }

    /**
     * Registers a node for the given breakpoint name and minimum width.
     *
     * @param name       breakpoint name
     * @param minWidth   minimum width (px) at which the node becomes active
     * @param node       node to show
     */
    public void addBreakpoint(String name, double minWidth, Node node) {
        entries.put(name, new Entry(name, minWidth, node));
        updateSelection();
    }

    private void updateSelection() {
        double w = getWidth();
        Entry pick = null;
        for (Entry e : entries.values()) {
            if (e.minWidth <= w && (pick == null || e.minWidth > pick.minWidth)) pick = e;
        }
        if (pick == null && !entries.isEmpty()) pick = entries.values().iterator().next();
        Node desired = pick == null ? null : pick.node;
        if (currentNode.get() != desired) {
            currentNode.set(desired);
            getChildren().clear();
            if (desired != null) getChildren().add(desired);
        }
        currentBreakpoint.set(pick == null ? "" : pick.name);
    }

    /** Returns the current-breakpoint property. */
    public StringProperty currentBreakpointProperty() { return currentBreakpoint; }

    /** Returns the current active breakpoint name, or empty. */
    public String getCurrentBreakpoint() { return currentBreakpoint.get(); }

    /** Returns the current active node, or {@code null}. */
    public Node getCurrentNode() { return currentNode.get(); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxResponsive}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    private static final class Entry {
        final String name;
        final double minWidth;
        final Node node;
        Entry(String name, double minWidth, Node node) { this.name = name; this.minWidth = minWidth; this.node = node; }
    }

    /**
     * Fluent builder for constructing an {@link FxResponsive}.
     *
     * <p>Defaults: no breakpoints registered.
     */
    public static class Builder extends FxNodeBuilder<FxResponsive, Builder> {

        private final Map<String, Entry> entries = new LinkedHashMap<>();

        public Builder at(String name, double minWidth, Node node) {
            if (name != null && node != null) entries.put(name, new Entry(name, Math.max(0, minWidth), node));
            return this;
        }

        @Override
        public FxResponsive build() {
            FxResponsive r = new FxResponsive();
            for (Entry e : entries.values()) r.addBreakpoint(e.name, e.minWidth, e.node);
            applyBase(r);
            return r;
        }
    }
}
