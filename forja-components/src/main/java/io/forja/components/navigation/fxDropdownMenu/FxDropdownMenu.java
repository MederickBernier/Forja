package io.forja.components.navigation.fxDropdownMenu;

import io.forja.builder.FxComponentBuilder;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.util.Arrays;

/**
 * A styled dropdown-menu button built on {@link MenuButton}.
 *
 * <p>Extends the standard JavaFX {@code MenuButton} — items and popup APIs
 * remain fully accessible.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxDropdownMenu m = FxDropdownMenu.builder()
 *          .text("Actions")
 *          .items(rename, duplicate, delete)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxDropdownMenu extends MenuButton {

    /**
     * Creates an empty {@code FxDropdownMenu}.
     */
    public FxDropdownMenu() {
        super();
        getStyleClass().add("forja-dropdown-menu");
    }

    /**
     * Creates an {@code FxDropdownMenu} with the given label.
     *
     * @param text button label
     */
    public FxDropdownMenu(String text) {
        super(text);
        getStyleClass().add("forja-dropdown-menu");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxDropdownMenu}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxDropdownMenu}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty</li>
     *   <li>items — empty</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxDropdownMenu, Builder> {

        private String text = "";
        private MenuItem[] items = new MenuItem[0];

        public Builder text(String text) { this.text = text == null ? "" : text; return this; }
        public Builder items(MenuItem... items) { this.items = items == null ? new MenuItem[0] : items; return this; }

        @Override
        public FxDropdownMenu build() {
            FxDropdownMenu m = new FxDropdownMenu(text);
            m.getItems().addAll(Arrays.asList(items));
            applyBase(m);
            return m;
        }
    }
}
