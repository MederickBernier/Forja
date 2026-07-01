package io.forja.components;

import io.forja.components.navigation.fxAnchorNav.FxAnchorNav;
import io.forja.components.navigation.fxAppBar.FxAppBar;
import io.forja.components.navigation.fxBreadcrumbs.FxBreadcrumbs;
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
import javafx.geometry.Side;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseGSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void tabs_holdsAllTabs() {
        FxTabs t = onFx(() -> FxTabs.builder()
                .tabs(new Tab("A"), new Tab("B"))
                .side(Side.TOP)
                .build());
        assertEquals(2, t.getTabs().size());
        assertEquals(Side.TOP, t.getSide());
    }

    @Test
    void verticalTabs_leftSideByDefault() {
        FxVerticalTabs vt = onFx(() -> FxVerticalTabs.builder().tabs(new Tab("A")).build());
        assertEquals(Side.LEFT, vt.getSide());
        assertTrue(vt.getStyleClass().contains("forja-vertical-tabs"));
    }

    @Test
    void appBar_titleAndActions() {
        FxAppBar bar = onFx(() -> FxAppBar.builder()
                .title("Forja")
                .actions(new FxLabel("A"))
                .build());
        assertEquals("Forja", bar.getTitle());
        assertEquals(1, bar.getActions().getChildren().size());
    }

    @Test
    void sidebar_setsWidth() {
        FxSidebar s = onFx(() -> FxSidebar.builder().width(280).children(new FxLabel("nav")).build());
        assertEquals(280.0, s.getPrefWidth());
        assertEquals(1, s.getChildren().size());
    }

    @Test
    void sidebarNav_selectsFiresCallback() {
        AtomicReference<String> got = new AtomicReference<>();
        FxSidebarNav n = onFx(() -> FxSidebarNav.builder()
                .item("home", "Home", "fth-home")
                .item("docs", "Docs", "fth-book")
                .onSelect(got::set)
                .active("home")
                .build());
        onFx(() -> { n.setActiveKey("docs"); return null; });
        assertEquals("docs", got.get());
    }

    @Test
    void breadcrumbs_lastIsCurrent() {
        AtomicReference<Integer> clicked = new AtomicReference<>();
        FxBreadcrumbs bc = onFx(() -> FxBreadcrumbs.builder()
                .segments("A", "B", "C")
                .onSelect(clicked::set)
                .build());
        // A + / + B + / + C  → 5 children
        assertEquals(5, bc.getChildren().size());
    }

    @Test
    void stepper_currentActive() {
        FxStepper s = onFx(() -> FxStepper.builder().steps("One", "Two", "Three").currentStep(1).build());
        assertEquals(3, s.getSteps().size());
        assertEquals(1, s.getCurrentStep());
    }

    @Test
    void pagination_clampsCurrent() {
        FxPagination p = onFx(() -> FxPagination.builder().totalPages(10).currentPage(0).build());
        onFx(() -> { p.setCurrentPage(99); return null; });
        assertEquals(9, p.getCurrentPage());
    }

    @Test
    void pagination_pageChangeFires() {
        AtomicInteger got = new AtomicInteger(-1);
        FxPagination p = onFx(() -> FxPagination.builder().totalPages(5).onPageChange(got::set).build());
        onFx(() -> { p.setCurrentPage(3); return null; });
        assertEquals(3, got.get());
    }

    @Test
    void menuBar_holdsMenus() {
        FxMenuBar mb = onFx(() -> FxMenuBar.builder().menus(new Menu("File"), new Menu("Edit")).build());
        assertEquals(2, mb.getMenus().size());
    }

    @Test
    void contextMenu_holdsItems() {
        FxContextMenu cm = onFx(() -> FxContextMenu.builder().items(new MenuItem("Copy"), new MenuItem("Paste")).build());
        assertEquals(2, cm.getItems().size());
    }

    @Test
    void dropdownMenu_setsTextAndItems() {
        FxDropdownMenu d = onFx(() -> FxDropdownMenu.builder().text("Actions").items(new MenuItem("Do")).build());
        assertEquals("Actions", d.getText());
        assertEquals(1, d.getItems().size());
    }

    @Test
    void anchorNav_registersSections() {
        FxAnchorNav a = onFx(() -> FxAnchorNav.builder()
                .section("a", "A", new FxLabel("a"))
                .section("b", "B", new FxLabel("b"))
                .build());
        assertEquals(2, a.getKeys().size());
        assertNull(a.getActiveKey());
        onFx(() -> { a.setActiveKey("b"); return null; });
        assertEquals("b", a.getActiveKey());
    }
}
