package io.forja.components.navigation.fxContextMenu;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.Arrays;

/**
 * A styled context menu built on {@link ContextMenu}.
 *
 * <p>Extends the standard JavaFX {@code ContextMenu} — install on a node
 * via {@link Node#setOnContextMenuRequested(javafx.event.EventHandler)}.
 * Forja adds the {@code forja-context-menu} style class and a fluent
 * builder.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxContextMenu cm = FxContextMenu.builder()
 *          .items(copy, paste, delete)
 *          .build();
 *      target.setOnContextMenuRequested(e -> cm.show(target, e.getScreenX(), e.getScreenY()));
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxContextMenu extends ContextMenu {

    /**
     * Creates an empty {@code FxContextMenu}.
     */
    public FxContextMenu() {
        super();
        getStyleClass().add("forja-context-menu");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxContextMenu}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxContextMenu}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>items — empty</li>
     * </ul>
     */
    public static class Builder {

        private MenuItem[] items = new MenuItem[0];

        public Builder items(MenuItem... items) { this.items = items == null ? new MenuItem[0] : items; return this; }

        public FxContextMenu build() {
            FxContextMenu cm = new FxContextMenu();
            cm.getItems().addAll(Arrays.asList(items));
            return cm;
        }
    }
}
