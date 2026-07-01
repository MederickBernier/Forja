package io.forja.components.navigation.fxTabs;

import io.forja.builder.FxComponentBuilder;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A styled tabbed pane built on {@link TabPane}.
 *
 * <p>Extends the standard JavaFX {@code TabPane} — Tab construction,
 * selection, and drag/close behaviors remain fully accessible.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxTabs tabs = FxTabs.builder()
 *          .tabs(new Tab("Overview", overview), new Tab("Docs", docs))
 *          .side(Side.TOP)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxTabs extends TabPane {

    /**
     * Creates an empty {@code FxTabs}.
     */
    public FxTabs() {
        super();
        getStyleClass().add("forja-tabs");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxTabs}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxTabs}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>tabs — empty</li>
     *   <li>side — {@link Side#TOP}</li>
     *   <li>closingPolicy — {@link TabPane.TabClosingPolicy#UNAVAILABLE}</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxTabs, Builder> {

        private List<Tab> tabs = Collections.emptyList();
        private Side side = Side.TOP;
        private TabPane.TabClosingPolicy closingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE;

        public Builder tabs(Tab... tabs) { this.tabs = tabs == null ? Collections.<Tab>emptyList() : Arrays.asList(tabs); return this; }
        public Builder tabs(List<Tab> tabs) { this.tabs = tabs == null ? Collections.<Tab>emptyList() : tabs; return this; }
        public Builder side(Side side) { this.side = side == null ? Side.TOP : side; return this; }
        public Builder closingPolicy(TabPane.TabClosingPolicy closingPolicy) { this.closingPolicy = closingPolicy == null ? TabPane.TabClosingPolicy.UNAVAILABLE : closingPolicy; return this; }

        @Override
        public FxTabs build() {
            FxTabs t = new FxTabs();
            t.setSide(side);
            t.setTabClosingPolicy(closingPolicy);
            t.getTabs().addAll(tabs);
            applyBase(t);
            return t;
        }
    }
}
