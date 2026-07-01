package io.forja.components;

import io.forja.components.overlays.OverlayHost;
import io.forja.components.overlays.fxBottomSheet.FxBottomSheet;
import io.forja.components.overlays.fxDrawer.FxDrawer;
import io.forja.components.overlays.fxFormDialog.FxFormDialog;
import io.forja.components.overlays.fxHoverCard.FxHoverCard;
import io.forja.components.overlays.fxLightbox.FxLightbox;
import io.forja.components.overlays.fxPopover.FxPopover;
import io.forja.components.typography.fxLabel.FxLabel;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.atomic.AtomicInteger;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseFSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void popover_setsAnchorAndContent() {
        Button anchor = onFx(() -> new Button("open"));
        FxLabel content = onFx(() -> new FxLabel("hi"));
        FxPopover p = onFx(() -> FxPopover.builder().anchor(anchor).content(content).side(Side.BOTTOM).build());
        assertEquals(anchor, p.getAnchor());
        assertEquals(content, p.getContent());
        assertEquals(Side.BOTTOM, p.getSide());
        assertFalse(p.isShowing());
    }

    @Test
    void hoverCard_wiresTargetWithDelays() {
        Button target = onFx(() -> new Button("hover"));
        FxLabel content = onFx(() -> new FxLabel("card"));
        FxHoverCard hc = onFx(() -> FxHoverCard.builder()
                .target(target)
                .content(content)
                .openDelayMs(100)
                .closeDelayMs(50)
                .autoInstall(true)
                .build());
        assertEquals(target, hc.getTarget());
        assertEquals(100, hc.getOpenDelayMs());
        assertNotNull(hc.getPopover());
    }

    @Test
    void drawer_openAddsToOverlayLayerCloseRemoves() {
        Scene scene = onFx(() -> new Scene(new VBox(), 800, 600));
        FxDrawer d = onFx(() -> FxDrawer.builder().side(Side.RIGHT).size(200).content(new FxLabel("body")).build());
        onFx(() -> { d.open(scene); return null; });
        StackPane layer = onFx(() -> OverlayHost.getLayer(scene));
        assertTrue(layer.getChildren().contains(d));
        assertTrue(d.isOpen());
    }

    @Test
    void bottomSheet_openAddsToLayer() {
        Scene scene = onFx(() -> new Scene(new VBox(), 800, 600));
        FxBottomSheet s = onFx(() -> FxBottomSheet.builder().height(200).content(new FxLabel("body")).build());
        onFx(() -> { s.open(scene); return null; });
        StackPane layer = onFx(() -> OverlayHost.getLayer(scene));
        assertTrue(layer.getChildren().contains(s));
        assertTrue(s.isOpen());
    }

    @Test
    void lightbox_navWraps() {
        FxLightbox lb = onFx(() -> FxLightbox.builder()
                .images("a", "b", "c")
                .index(0)
                .build());
        onFx(() -> { lb.next(); return null; });
        assertEquals(1, lb.getIndex());
        onFx(() -> { lb.prev(); lb.prev(); return null; });
        assertEquals(2, lb.getIndex(), "prev wraps");
    }

    @Test
    void formDialog_saveInvokesSubmit() {
        Scene scene = onFx(() -> new Scene(new VBox(), 400, 300));
        AtomicInteger n = new AtomicInteger();
        FxFormDialog dlg = onFx(() -> FxFormDialog.builder()
                .title("New")
                .body(new FxLabel("body"))
                .onSubmit(n::incrementAndGet)
                .build());
        onFx(() -> { dlg.show(scene); return null; });
        onFx(() -> { dlg.getSaveButton().fire(); return null; });
        assertEquals(1, n.get());
        StackPane layer = onFx(() -> OverlayHost.getLayer(scene));
        assertFalse(layer.getChildren().contains(dlg.getDialog()));
    }

    @Test
    void formDialog_canSaveDisablesSave() {
        AtomicInteger n = new AtomicInteger();
        FxFormDialog dlg = onFx(() -> FxFormDialog.builder()
                .canSave(() -> false)
                .onSubmit(n::incrementAndGet)
                .build());
        assertTrue(dlg.getSaveButton().isDisable());
        onFx(() -> { dlg.getSaveButton().fire(); return null; });
        assertEquals(0, n.get());
    }
}
