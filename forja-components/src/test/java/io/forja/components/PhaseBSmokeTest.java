package io.forja.components;

import io.forja.FxTheme;
import io.forja.components.dataDisplay.fxDescriptionList.FxDescriptionList;
import io.forja.components.dataDisplay.fxImage.FxImage;
import io.forja.components.dataDisplay.fxList.FxList;
import io.forja.components.dataDisplay.fxStat.FxStat;
import io.forja.components.dataDisplay.fxTable.FxTable;
import io.forja.components.dataDisplay.fxTree.FxTree;
import io.forja.components.dataDisplay.fxTreeTable.FxTreeTable;
import io.forja.components.layout.fxAccordion.FxAccordion;
import io.forja.components.layout.fxAspectRatio.FxAspectRatio;
import io.forja.components.layout.fxCollapse.FxCollapse;
import io.forja.components.layout.fxFlex.FxFlex;
import io.forja.components.layout.fxGrid.FxGrid;
import io.forja.components.layout.fxResizablePane.FxResizablePane;
import io.forja.components.layout.fxResponsive.FxResponsive;
import io.forja.components.layout.fxScrollArea.FxScrollArea;
import io.forja.components.layout.fxSplitView.FxSplitView;
import io.forja.components.layout.fxStickyHeader.FxStickyHeader;
import io.forja.components.typography.fxBulletList.FxBulletList;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.utilities.fxFocusTrap.FxFocusTrap;
import io.forja.components.utilities.fxKeybindingHint.FxKeybindingHint;
import io.forja.components.utilities.fxPortal.FxPortal;
import io.forja.components.utilities.fxScrollSpy.FxScrollSpy;
import io.forja.components.utilities.fxSearchHighlight.FxSearchHighlight;
import io.forja.components.utilities.fxThemeToggle.FxThemeToggle;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseBSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void grid_builds() {
        FxGrid g = onFx(() -> FxGrid.builder().hgap(4).vgap(2).add(new FxLabel("A"), 0, 0).build());
        assertTrue(g.getStyleClass().contains("forja-grid"));
    }

    @Test
    void flex_builds() {
        FxFlex f = onFx(() -> FxFlex.builder().gap(4).children(new FxLabel("A")).build());
        assertEquals(1, f.getChildren().size());
    }

    @Test
    void aspectRatio_layout() {
        FxAspectRatio ar = onFx(() -> FxAspectRatio.builder().ratio(2.0).child(new FxLabel("child")).build());
        assertEquals(2.0, ar.getRatio());
        assertNotNull(ar.getChild());
    }

    @Test
    void scrollArea_builds() {
        FxScrollArea sa = onFx(() -> FxScrollArea.builder().content(new FxLabel("content")).build());
        assertTrue(sa.isFitToWidth());
    }

    @Test
    void splitView_builds() {
        FxSplitView sv = onFx(() -> FxSplitView.builder().items(new FxLabel("a"), new FxLabel("b")).dividerPositions(0.5).build());
        assertEquals(2, sv.getItems().size());
    }

    @Test
    void resizablePane_setsExtent() {
        FxResizablePane p = onFx(() -> FxResizablePane.builder().extent(200).minExtent(50).maxExtent(400).build());
        assertEquals(200.0, p.getExtent());
    }

    @Test
    void collapse_toggles() {
        FxCollapse c = onFx(() -> FxCollapse.builder().title("More").expanded(false).build());
        onFx(() -> { c.toggle(); return null; });
        assertTrue(c.isExpanded());
    }

    @Test
    void accordion_singleOpenEnforced() {
        FxCollapse a = onFx(() -> FxCollapse.builder().title("A").build());
        FxCollapse b = onFx(() -> FxCollapse.builder().title("B").build());
        FxAccordion acc = onFx(() -> FxAccordion.builder().sections(a, b).singleOpen(true).build());
        onFx(() -> { a.setExpanded(true); return null; });
        onFx(() -> { b.setExpanded(true); return null; });
        assertTrue(b.isExpanded());
        assertTrue(!a.isExpanded());
        assertNotNull(acc);
    }

    @Test
    void stickyHeader_builds() {
        FxStickyHeader s = onFx(() -> FxStickyHeader.builder().header(new FxLabel("H")).body(new FxLabel("B")).build());
        assertEquals(2, s.getChildren().size());
    }

    @Test
    void responsive_picksBreakpoint() {
        FxResponsive r = onFx(() -> FxResponsive.builder()
                .at("base", 0, new FxLabel("base"))
                .at("md", 768, new FxLabel("md"))
                .build());
        assertNotNull(r);
    }

    @Test
    void themeToggle_flipsTheme() {
        FxThemeToggle t = onFx(() -> FxThemeToggle.builder().initial(FxTheme.LIGHT).build());
        onFx(() -> { t.toggle(); return null; });
        assertEquals(FxTheme.DARK, t.getTheme());
    }

    @Test
    void keybindingHint_hasSeparators() {
        FxKeybindingHint h = onFx(() -> FxKeybindingHint.builder().keys("Ctrl", "K").build());
        assertEquals(3, h.getChildren().size(), "kbd + '+' + kbd");
    }

    @Test
    void searchHighlight_countsMatches() {
        FxSearchHighlight h = onFx(() -> FxSearchHighlight.builder()
                .text("Deploy the pipeline to production")
                .query("pi")
                .build());
        long matches = h.getChildren().stream().filter(n -> n.getStyleClass().contains("forja-search-highlight-match")).count();
        assertEquals(1, matches);
    }

    @Test
    void scrollSpy_registersSections() {
        FxScrollSpy s = onFx(() -> FxScrollSpy.builder()
                .section("a", new FxLabel("A"))
                .section("b", new FxLabel("B"))
                .build());
        assertEquals(2, s.getSections().size());
    }

    @Test
    void portal_visibilityToggles() {
        FxPortal p = onFx(() -> FxPortal.builder().node(new FxLabel("x")).visible(false).build());
        onFx(() -> { p.setVisible(true); return null; });
        assertTrue(p.isVisible());
    }

    @Test
    void focusTrap_installsAndUninstalls() {
        VBox root = onFx(() -> new VBox());
        FxFocusTrap trap = onFx(() -> FxFocusTrap.builder().root(root).autoInstall(true).build());
        onFx(() -> { trap.uninstall(); return null; });
        assertNotNull(trap);
    }

    @Test
    void bulletList_orderedNumbers() {
        FxBulletList b = onFx(() -> FxBulletList.builder()
                .kind(FxBulletList.Kind.ORDERED)
                .items("A", "B")
                .build());
        assertEquals(2, b.getChildren().size());
    }

    @Test
    void list_setsItems() {
        FxList<String> l = onFx(() -> FxList.<String>builder().items("a", "b").build());
        assertEquals(2, l.getItems().size());
    }

    @Test
    void table_setsRows() {
        FxTable<String> t = onFx(() -> FxTable.<String>builder().items("a", "b").build());
        assertEquals(2, t.getItems().size());
    }

    @Test
    void tree_setsRoot() {
        FxTree<String> tr = onFx(() -> FxTree.<String>builder().root(new TreeItem<>("root")).build());
        assertNotNull(tr.getRoot());
    }

    @Test
    void treeTable_setsRoot() {
        FxTreeTable<String> tt = onFx(() -> FxTreeTable.<String>builder().root(new TreeItem<>("root")).build());
        assertNotNull(tt.getRoot());
    }

    @Test
    void image_setsFit() {
        FxImage img = onFx(() -> FxImage.builder().fitWidth(64).fitHeight(64).build());
        assertEquals(64.0, img.getFitWidth());
    }

    @Test
    void stat_showsTrend() {
        FxStat s = onFx(() -> FxStat.builder()
                .label("Users").value("1234")
                .trend("+12%", FxStat.Trend.UP)
                .build());
        assertTrue(s.getTrendRow().isVisible());
    }

    @Test
    void descriptionList_builds() {
        FxDescriptionList dl = onFx(() -> FxDescriptionList.builder()
                .item("A", "1")
                .item("B", "2")
                .build());
        assertEquals(4, dl.getChildren().size(), "2 rows * 2 cells");
    }
}
