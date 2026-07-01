package io.forja.components.navigation.fxMenuBar;

import io.forja.builder.FxComponentBuilder;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import java.util.Arrays;

/**
 * A styled menu bar built on {@link MenuBar}.
 *
 * <p>Extends {@code MenuBar} directly — {@link Menu}/{@link javafx.scene.control.MenuItem}
 * construction remains fully accessible.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxMenuBar mb = FxMenuBar.builder()
 *          .menus(fileMenu, editMenu, viewMenu)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxMenuBar extends MenuBar {

    /**
     * Creates an empty {@code FxMenuBar}.
     */
    public FxMenuBar() {
        super();
        getStyleClass().add("forja-menu-bar");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxMenuBar}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxMenuBar}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>menus — empty</li>
     *   <li>useSystemMenuBar — {@code false}</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxMenuBar, Builder> {

        private Menu[] menus = new Menu[0];
        private boolean useSystemMenuBar = false;

        public Builder menus(Menu... menus) { this.menus = menus == null ? new Menu[0] : menus; return this; }
        public Builder useSystemMenuBar(boolean useSystemMenuBar) { this.useSystemMenuBar = useSystemMenuBar; return this; }

        @Override
        public FxMenuBar build() {
            FxMenuBar mb = new FxMenuBar();
            mb.getMenus().addAll(Arrays.asList(menus));
            mb.setUseSystemMenuBar(useSystemMenuBar);
            applyBase(mb);
            return mb;
        }
    }
}
