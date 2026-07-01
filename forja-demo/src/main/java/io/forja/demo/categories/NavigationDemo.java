package io.forja.demo.categories;

import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.navigation.fxAnchorNav.FxAnchorNav;
import io.forja.components.navigation.fxAppBar.FxAppBar;
import io.forja.components.navigation.fxBreadcrumbs.FxBreadcrumbs;
import io.forja.components.navigation.fxCommandPalette.FxCommandPalette;
import io.forja.components.navigation.fxContextMenu.FxContextMenu;
import io.forja.components.navigation.fxDropdownMenu.FxDropdownMenu;
import io.forja.components.navigation.fxMenuBar.FxMenuBar;
import io.forja.components.navigation.fxPagination.FxPagination;
import io.forja.components.navigation.fxSidebar.FxSidebar;
import io.forja.components.navigation.fxSidebarNav.FxSidebarNav;
import io.forja.components.navigation.fxStepper.FxStepper;
import io.forja.components.navigation.fxTabs.FxTabs;
import io.forja.components.navigation.fxVerticalTabs.FxVerticalTabs;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

public class NavigationDemo implements CategoryDemo {

    @Override public String key() { return "navigation"; }
    @Override public String title() { return "Navigation"; }
    @Override public String icon() { return "fth-compass"; }

    private static Node label(String text) {
        return FxLabel.builder().text(text).variant(LabelVariant.BODY).build();
    }

    @Override
    public Node build(final Scene scene) {
        // ponytail: build palette once, trigger opens it via OverlayHost.
        final FxCommandPalette palette = FxCommandPalette.builder()
                .command(new FxCommandPalette.Command("open", "Open file", "fth-folder", null))
                .command(new FxCommandPalette.Command("save", "Save file", "fth-save", null))
                .command(new FxCommandPalette.Command("settings", "Settings", "fth-settings", null))
                .buildPalette();

        FxContextMenu contextMenu = FxContextMenu.builder()
                .items(new MenuItem("Cut"), new MenuItem("Copy"), new MenuItem("Paste")).build();
        FxButton contextButton = FxButton.builder().text("Right-click me").build();
        contextButton.setContextMenu(contextMenu);

        return Demo.category(title(),
                Demo.block("FxAppBar", "Top application bar with title, leading, and trailing actions.",
                        FxAppBar.builder().title("Forja").leading(label("Menu")).actions(label("Profile")).build()),

                Demo.block("FxBreadcrumbs", "Path of clickable segments showing hierarchy.",
                        FxBreadcrumbs.builder().segments("Home", "Components", "Navigation").build()),

                Demo.block("FxTabs", "Horizontal tab pane with switchable content.",
                        FxTabs.builder().tabs(
                                new Tab("Overview", label("Overview content")),
                                new Tab("Docs", label("Docs content")),
                                new Tab("Usage", label("Usage content"))).build()),

                Demo.block("FxVerticalTabs", "Tab pane with tabs stacked on the side.",
                        FxVerticalTabs.builder().tabs(
                                new Tab("General", label("General settings")),
                                new Tab("Account", label("Account settings"))).build()),

                Demo.block("FxMenuBar", "Classic menu bar with drop-down menus.",
                        FxMenuBar.builder().menus(
                                new Menu("File", null, new MenuItem("New"), new MenuItem("Open")),
                                new Menu("Edit", null, new MenuItem("Undo"), new MenuItem("Redo"))).build()),

                Demo.block("FxDropdownMenu", "Button that opens a menu of items.",
                        FxDropdownMenu.builder().text("Options")
                                .items(new MenuItem("Rename"), new MenuItem("Duplicate"), new MenuItem("Delete")).build()),

                Demo.block("FxContextMenu", "Right-click menu attached to a control.",
                        contextButton),

                Demo.block("FxCommandPalette", "Fuzzy command launcher opened as an overlay.",
                        Demo.trigger("Open palette", new Runnable() {
                            @Override public void run() { palette.open(scene); }
                        })),

                Demo.block("FxPagination", "Page selector with previous/next and page numbers.",
                        FxPagination.builder().totalPages(10).currentPage(3).build()),

                Demo.block("FxStepper", "Horizontal progress through ordered steps.",
                        FxStepper.builder().steps("Cart", "Shipping", "Payment", "Review").currentStep(1).build()),

                Demo.block("FxSidebarNav", "Vertical navigation list with icons and active state.",
                        FxSidebarNav.builder()
                                .item("home", "Home", "fth-home")
                                .item("search", "Search", "fth-search")
                                .item("settings", "Settings", "fth-settings")
                                .active("search").build()),

                Demo.block("FxSidebar", "Container panel for grouping navigation content.",
                        FxSidebar.builder().children(label("Section A"), label("Section B")).build()),

                Demo.block("FxAnchorNav", "Scroll-spy links that jump to page sections.",
                        FxAnchorNav.builder()
                                .section("intro", "Introduction", label("Intro"))
                                .section("api", "API", label("API"))
                                .section("faq", "FAQ", label("FAQ"))
                                .active("intro").build()));
    }
}
