package io.forja.components.navigation.fxSidebarNav;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A vertical list of navigation items with a single-select model.
 *
 * <p>Each item is a {@link Button} row with an optional leading icon + label.
 * The currently-active item's outer row gets the {@code :active} pseudo-class.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxSidebarNav nav = FxSidebarNav.builder()
 *          .item("home",     "Home",     "fth-home")
 *          .item("projects", "Projects", "fth-folder")
 *          .item("settings", "Settings", "fth-settings")
 *          .active("projects")
 *          .onSelect(key -> router.go(key))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxSidebarNav extends VBox {

    private static final PseudoClass ACTIVE_PC = PseudoClass.getPseudoClass("active");

    private final Map<String, Button> buttonsByKey = new LinkedHashMap<>();
    private final StringProperty activeKey = new SimpleStringProperty(this, "activeKey", null);
    private OnSelect onSelect;

    /**
     * Creates an empty {@code FxSidebarNav}.
     */
    public FxSidebarNav() {
        super();
        getStyleClass().add("forja-sidebar-nav");
        setSpacing(2);
        activeKey.addListener((obs, o, v) -> repaint());
    }

    /** Adds a navigation item. */
    public void addItem(final String key, String label, String iconLiteral) {
        Button btn = new Button();
        btn.getStyleClass().add("forja-sidebar-nav-item");
        HBox row = new HBox(8);
        row.setAlignment(Pos.CENTER_LEFT);
        if (iconLiteral != null && !iconLiteral.isEmpty()) row.getChildren().add(new FxIcon(iconLiteral));
        row.getChildren().add(new Label(label == null ? "" : label));
        btn.setGraphic(row);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setOnAction(e -> setActiveKey(key));
        buttonsByKey.put(key, btn);
        getChildren().add(btn);
        repaint();
    }

    private void repaint() {
        String cur = getActiveKey();
        for (Map.Entry<String, Button> e : buttonsByKey.entrySet()) {
            boolean active = e.getKey().equals(cur);
            e.getValue().pseudoClassStateChanged(ACTIVE_PC, active);
        }
        if (onSelect != null && cur != null) onSelect.accept(cur);
    }

    /** Returns the active-key property. */
    public StringProperty activeKeyProperty() { return activeKey; }

    /** Returns the current active key. */
    public String getActiveKey() { return activeKey.get(); }

    /** Sets the current active key. */
    public void setActiveKey(String v) { activeKey.set(v); }

    /** Returns the button map (read-only). */
    public Map<String, Button> getButtonsByKey() { return Collections.unmodifiableMap(buttonsByKey); }

    /** Sets the on-select callback. */
    public void setOnSelect(OnSelect onSelect) { this.onSelect = onSelect; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxSidebarNav}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when the active item changes. */
    @FunctionalInterface
    public interface OnSelect { void accept(String key); }

    private static final class Entry { final String key, label, icon; Entry(String key, String label, String icon) { this.key = key; this.label = label; this.icon = icon; } }

    /**
     * Fluent builder for constructing an {@link FxSidebarNav}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     *   <li>active — {@code null}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxSidebarNav, Builder> {

        private final List<Entry> items = new ArrayList<>();
        private String active;
        private OnSelect onSelect;

        public Builder item(String key, String label, String iconLiteral) { items.add(new Entry(key, label, iconLiteral)); return this; }
        public Builder items(List<String[]> triples) {
            if (triples != null) for (String[] t : triples) if (t != null && t.length >= 2) items.add(new Entry(t[0], t[1], t.length > 2 ? t[2] : null));
            return this;
        }
        public Builder active(String active) { this.active = active; return this; }
        public Builder onSelect(OnSelect onSelect) { this.onSelect = onSelect; return this; }

        @Override
        public FxSidebarNav build() {
            FxSidebarNav n = new FxSidebarNav();
            for (Entry e : items) n.addItem(e.key, e.label, e.icon);
            n.setOnSelect(onSelect);
            n.setActiveKey(active);
            applyBase(n);
            return n;
        }
    }
}
