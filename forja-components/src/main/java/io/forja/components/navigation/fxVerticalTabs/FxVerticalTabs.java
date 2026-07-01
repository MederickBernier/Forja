package io.forja.components.navigation.fxVerticalTabs;

import io.forja.builder.FxComponentBuilder;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A vertical (LEFT-side) tabbed pane built on {@link TabPane}.
 *
 * <p>Same as {@link javafx.scene.control.TabPane} with {@code side} defaulted
 * to {@link Side#LEFT} and rotate-graphic on so tab titles read horizontally.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxVerticalTabs settings = FxVerticalTabs.builder()
 *          .tabs(new Tab("Profile", profile), new Tab("Billing", billing))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxVerticalTabs extends TabPane {

    /**
     * Creates an empty vertical {@code FxVerticalTabs}.
     */
    public FxVerticalTabs() {
        super();
        getStyleClass().addAll("forja-tabs", "forja-vertical-tabs");
        setSide(Side.LEFT);
        setRotateGraphic(true);
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxVerticalTabs}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxVerticalTabs}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>tabs — empty</li>
     *   <li>side — {@link Side#LEFT}</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxVerticalTabs, Builder> {

        private List<Tab> tabs = Collections.emptyList();
        private Side side = Side.LEFT;

        public Builder tabs(Tab... tabs) { this.tabs = tabs == null ? Collections.<Tab>emptyList() : Arrays.asList(tabs); return this; }
        public Builder side(Side side) { this.side = side == null ? Side.LEFT : side; return this; }

        @Override
        public FxVerticalTabs build() {
            FxVerticalTabs t = new FxVerticalTabs();
            t.setSide(side);
            t.getTabs().addAll(tabs);
            applyBase(t);
            return t;
        }
    }
}
