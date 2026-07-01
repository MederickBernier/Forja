package io.forja.components.navigation.fxAnchorNav;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLink.FxLink;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A vertical list of in-page jump links backed by a {@link ScrollPane} +
 * target-node map.
 *
 * <p>Each entry is an {@link FxLink}; clicking one scrolls the paired scroll
 * pane so the target node's top aligns with the viewport top. Register the
 * active key via {@link #setActiveKey} (or set from an
 * {@link io.forja.components.utilities.fxScrollSpy.FxScrollSpy}) to highlight
 * the current section.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxAnchorNav toc = FxAnchorNav.builder()
 *          .scrollPane(scrollPane)
 *          .section("intro",   "Intro",   introNode)
 *          .section("install", "Install", installNode)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxAnchorNav extends VBox {

    private static final PseudoClass ACTIVE_PC = PseudoClass.getPseudoClass("active");

    private final Map<String, Node> targets = new LinkedHashMap<>();
    private final Map<String, FxLink> links = new LinkedHashMap<>();
    private final StringProperty activeKey = new SimpleStringProperty(this, "activeKey", null);
    private ScrollPane scrollPane;

    /**
     * Creates an empty {@code FxAnchorNav}.
     */
    public FxAnchorNav() {
        super();
        getStyleClass().add("forja-anchor-nav");
        setSpacing(4);
        activeKey.addListener((obs, o, v) -> repaint());
    }

    /** Registers the host scroll pane. */
    public void setScrollPane(ScrollPane scrollPane) { this.scrollPane = scrollPane; }

    /** Registers a section. */
    public void addSection(final String key, String label, final Node target) {
        if (key == null || target == null) return;
        targets.put(key, target);
        FxLink link = FxLink.builder().text(label == null ? key : label)
                .onAction(e -> { scrollTo(target); setActiveKey(key); }).build();
        link.getStyleClass().add("forja-anchor-nav-link");
        links.put(key, link);
        getChildren().add(link);
        repaint();
    }

    private void scrollTo(Node target) {
        if (scrollPane == null || target == null) return;
        Node content = scrollPane.getContent();
        if (content == null) return;
        double contentH = content.getBoundsInLocal().getHeight();
        double vp = scrollPane.getViewportBounds() == null ? 0 : scrollPane.getViewportBounds().getHeight();
        double y = target.getBoundsInParent().getMinY();
        double denom = Math.max(1, contentH - vp);
        scrollPane.setVvalue(Math.max(0, Math.min(1, y / denom)));
    }

    private void repaint() {
        String cur = getActiveKey();
        for (Map.Entry<String, FxLink> e : links.entrySet()) {
            e.getValue().pseudoClassStateChanged(ACTIVE_PC, e.getKey().equals(cur));
        }
    }

    /** Returns the active-key property. */
    public StringProperty activeKeyProperty() { return activeKey; }

    /** Returns the active key. */
    public String getActiveKey() { return activeKey.get(); }

    /** Sets the active key. */
    public void setActiveKey(String v) { activeKey.set(v); }

    /** Returns registered section keys. */
    public List<String> getKeys() { return new ArrayList<>(links.keySet()); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxAnchorNav}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    private static final class Entry { final String key, label; final Node target; Entry(String key, String label, Node target) { this.key = key; this.label = label; this.target = target; } }

    /**
     * Fluent builder for constructing an {@link FxAnchorNav}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>scrollPane — {@code null}</li>
     *   <li>sections — empty</li>
     *   <li>active — {@code null}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxAnchorNav, Builder> {

        private ScrollPane scrollPane;
        private final List<Entry> sections = new ArrayList<>();
        private String active;

        public Builder scrollPane(ScrollPane scrollPane) { this.scrollPane = scrollPane; return this; }
        public Builder section(String key, String label, Node target) { sections.add(new Entry(key, label, target)); return this; }
        public Builder active(String active) { this.active = active; return this; }

        @Override
        public FxAnchorNav build() {
            FxAnchorNav n = new FxAnchorNav();
            n.setScrollPane(scrollPane);
            for (Entry e : sections) n.addSection(e.key, e.label, e.target);
            n.setActiveKey(active);
            applyBase(n);
            return n;
        }
    }
}
