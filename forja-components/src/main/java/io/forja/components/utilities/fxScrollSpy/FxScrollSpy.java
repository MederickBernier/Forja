package io.forja.components.utilities.fxScrollSpy;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Non-visual controller that tracks which of several registered "section"
 * nodes is currently in view inside a {@link ScrollPane}, exposing the
 * active section's key via {@link #activeKeyProperty()}.
 *
 * <p>Callers register sections by key + node via {@link Builder#section} or
 * {@link #addSection}. On every scroll or layout change, {@code FxScrollSpy}
 * finds the topmost section whose bounds intersect the viewport and updates
 * {@code activeKey}. Bind this to a navigation control (e.g. a sidebar) to
 * highlight the current section.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxScrollSpy spy = FxScrollSpy.builder()
 *          .scrollPane(scrollPane)
 *          .section("intro",     intro)
 *          .section("install",   install)
 *          .section("api",       api)
 *          .build();
 *      spy.activeKeyProperty().addListener((obs, o, key) -> sidebar.highlight(key));
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxScrollSpy {

    private final Map<String, Node> sections = new LinkedHashMap<>();
    private final ObjectProperty<String> activeKey = new SimpleObjectProperty<>(this, "activeKey", null);
    private ScrollPane scrollPane;

    /**
     * Creates an unattached {@code FxScrollSpy}.
     */
    public FxScrollSpy() {}

    /** Sets the host scroll pane and wires listeners. */
    public void setScrollPane(ScrollPane sp) {
        this.scrollPane = sp;
        if (sp == null) return;
        sp.vvalueProperty().addListener((obs, o, v) -> recomputeActive());
        sp.hvalueProperty().addListener((obs, o, v) -> recomputeActive());
        sp.viewportBoundsProperty().addListener((obs, o, v) -> recomputeActive());
        recomputeActive();
    }

    /** Returns the current host scroll pane, or {@code null}. */
    public ScrollPane getScrollPane() { return scrollPane; }

    /** Adds a section entry. */
    public void addSection(String key, Node node) {
        if (key != null && node != null) {
            sections.put(key, node);
            recomputeActive();
        }
    }

    /** Returns an unmodifiable view of the section map. */
    public Map<String, Node> getSections() { return Collections.unmodifiableMap(sections); }

    /** Returns the active-key property. */
    public ObjectProperty<String> activeKeyProperty() { return activeKey; }

    /** Returns the currently-active section key, or {@code null}. */
    public String getActiveKey() { return activeKey.get(); }

    private void recomputeActive() {
        if (scrollPane == null || sections.isEmpty()) return;
        Bounds viewport = scrollPane.localToScene(scrollPane.getBoundsInLocal());
        String bestKey = null;
        double bestY = Double.POSITIVE_INFINITY;
        List<Map.Entry<String, Node>> entries = new ArrayList<>(sections.entrySet());
        for (Map.Entry<String, Node> e : entries) {
            Node n = e.getValue();
            if (n.getScene() == null) continue;
            Bounds b = n.localToScene(n.getBoundsInLocal());
            if (b.getMaxY() < viewport.getMinY() || b.getMinY() > viewport.getMaxY()) continue;
            double y = Math.abs(b.getMinY() - viewport.getMinY());
            if (y < bestY) { bestY = y; bestKey = e.getKey(); }
        }
        activeKey.set(bestKey);
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxScrollSpy}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxScrollSpy}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>scrollPane — {@code null}</li>
     *   <li>sections — empty</li>
     * </ul>
     */
    public static class Builder {

        private ScrollPane scrollPane;
        private final Map<String, Node> sections = new LinkedHashMap<>();

        public Builder scrollPane(ScrollPane scrollPane) { this.scrollPane = scrollPane; return this; }
        public Builder section(String key, Node node) {
            if (key != null && node != null) sections.put(key, node);
            return this;
        }

        public FxScrollSpy build() {
            FxScrollSpy s = new FxScrollSpy();
            for (Map.Entry<String, Node> e : sections.entrySet()) s.addSection(e.getKey(), e.getValue());
            if (scrollPane != null) s.setScrollPane(scrollPane);
            return s;
        }
    }
}
