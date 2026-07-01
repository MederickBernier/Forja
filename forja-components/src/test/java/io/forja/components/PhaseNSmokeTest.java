package io.forja.components;

import io.forja.components.dataDisplay.fxCarousel.FxCarousel;
import io.forja.components.dataDisplay.fxDataGrid.FxDataGrid;
import io.forja.components.dataDisplay.fxKanbanBoard.FxKanbanBoard;
import io.forja.components.dataDisplay.fxMasonry.FxMasonry;
import io.forja.components.dataDisplay.fxTimeline.FxTimeline;
import io.forja.components.dataDisplay.fxVirtualList.FxVirtualList;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.tokens.SemanticVariant;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;
import java.util.List;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseNSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void dataGrid_pageSizeAndFilterAffectVisibleRows() {
        List<String> src = new ArrayList<>();
        for (int i = 0; i < 50; i++) src.add("row-" + i);
        FxDataGrid<String> grid = onFx(() -> FxDataGrid.<String>builder()
                .items(src)
                .pageSize(10)
                .filter((row, q) -> row.contains(q))
                .build());
        assertEquals(50, grid.getItems().size());
        assertEquals(10, grid.getTable().getItems().size(), "first page shows 10");
        onFx(() -> { grid.getSearchField().setText("row-1"); return null; });
        assertTrue(grid.getTable().getItems().size() <= 10);
    }

    @Test
    void virtualList_itemsAndCellFactoryWired() {
        List<Integer> src = new ArrayList<>();
        for (int i = 0; i < 1000; i++) src.add(i);
        FxVirtualList<Integer> vl = onFx(() -> FxVirtualList.<Integer>builder()
                .items(src)
                .itemHeight(20)
                .cellFactory(n -> new FxLabel(String.valueOf(n)))
                .build());
        assertEquals(1000, vl.getItems().size());
        assertEquals(20, vl.getItemHeight());
    }

    @Test
    void timeline_buildsEntries() {
        FxTimeline t = onFx(() -> FxTimeline.builder()
                .entry("Deploy", "09:32", SemanticVariant.SUCCESS)
                .entry("Alert",  "09:45", SemanticVariant.DANGER)
                .entry("Restored","09:52", SemanticVariant.INFO)
                .build());
        assertEquals(3, t.getEntries().size());
        assertEquals(3, t.getChildren().size());
    }

    @Test
    void kanban_addColumnsAndCards() {
        FxLabel card = onFx(() -> new FxLabel("ticket-1"));
        FxKanbanBoard b = onFx(() -> FxKanbanBoard.builder()
                .column("todo", "To Do")
                .column("done", "Done")
                .card("todo", card)
                .build());
        assertEquals(2, b.getColumns().size());
        assertEquals(1, b.getColumns().get("todo").getCards().size());
    }

    @Test
    void masonry_configuresColumns() {
        FxMasonry m = onFx(() -> FxMasonry.builder()
                .columns(4).gap(12)
                .children(new FxLabel("a"), new FxLabel("b"))
                .build());
        assertEquals(4, m.getColumns());
        assertEquals(12.0, m.getGap());
        assertEquals(2, m.getChildrenUnmodifiable().size());
    }

    @Test
    void carousel_navWraps() {
        FxCarousel c = onFx(() -> FxCarousel.builder()
                .slides(new FxLabel("A"), new FxLabel("B"), new FxLabel("C"))
                .index(0)
                .build());
        onFx(() -> { c.next(); return null; });
        assertEquals(1, c.getIndex());
        onFx(() -> { c.prev(); c.prev(); return null; });
        assertEquals(2, c.getIndex(), "wraps");
    }

    @Test
    void carousel_hasNavDotsPerSlide() {
        FxCarousel c = onFx(() -> FxCarousel.builder()
                .slides(new FxLabel("A"), new FxLabel("B"), new FxLabel("C"))
                .build());
        assertEquals(3, c.getDotsRow().getChildren().size());
        assertNotNull(c.getPrevButton());
        assertNotNull(c.getNextButton());
    }
}
