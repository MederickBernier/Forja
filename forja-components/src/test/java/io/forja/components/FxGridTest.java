package io.forja.components;

import io.forja.components.layout.fxGrid.FxGrid;
import io.forja.components.typography.fxLabel.FxLabel;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxGridTest {

    @Start
    void start(Stage stage) {}

    @Test
    void builderDefaults() {
        FxGrid g = onFx(() -> FxGrid.builder().build());
        assertEquals(0.0, g.getHgap());
        assertEquals(0.0, g.getVgap());
        assertEquals(Pos.TOP_LEFT, g.getAlignment());
        assertTrue(g.getStyleClass().contains("forja-grid"));
    }

    @Test
    void addsChildrenAtCoords() {
        FxLabel a = onFx(() -> new FxLabel("A"));
        FxLabel b = onFx(() -> new FxLabel("B"));
        FxGrid g = onFx(() -> FxGrid.builder()
                .hgap(12).vgap(8)
                .add(a, 0, 0)
                .add(b, 1, 0)
                .build());
        assertEquals(12.0, g.getHgap());
        assertEquals(8.0, g.getVgap());
        assertTrue(g.getChildren().contains(a));
        assertTrue(g.getChildren().contains(b));
    }
}
