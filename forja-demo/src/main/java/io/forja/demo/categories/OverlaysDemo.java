package io.forja.demo.categories;

import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.overlays.fxBottomSheet.FxBottomSheet;
import io.forja.components.overlays.fxConfirmDialog.FxConfirmDialog;
import io.forja.components.overlays.fxDialog.FxDialog;
import io.forja.components.overlays.fxDrawer.FxDrawer;
import io.forja.components.overlays.fxFormDialog.FxFormDialog;
import io.forja.components.overlays.fxHoverCard.FxHoverCard;
import io.forja.components.overlays.fxLightbox.FxLightbox;
import io.forja.components.overlays.fxPopover.FxPopover;
import io.forja.components.overlays.fxTooltip.FxTooltip;
import io.forja.components.typography.fxLabel.FxLabel;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;

public class OverlaysDemo implements CategoryDemo {

    @Override public String key() { return "overlays"; }
    @Override public String title() { return "Overlays"; }
    @Override public String icon() { return "fth-layers"; }

    @Override
    public Node build(Scene scene) {
        // FxTooltip extends javafx.scene.control.Tooltip — install it onto a button.
        FxButton tooltipBtn = FxButton.builder().text("Hover me").build();
        FxTooltip.install(tooltipBtn, "I am a Forja-styled tooltip.");

        // FxHoverCard attaches to a target node; autoInstall wires the hover listeners.
        FxButton hoverCardBtn = FxButton.builder().text("Hover for card").build();
        FxHoverCard.builder()
                .target(hoverCardBtn)
                .content(FxLabel.builder().text("Rich hover content shown after a short delay.").build())
                .autoInstall(true)
                .build();

        // FxPopover anchors to a node and is shown on demand.
        FxButton popoverBtn = FxButton.builder().text("Show popover").build();
        FxPopover popover = FxPopover.builder()
                .anchor(popoverBtn)
                .content(FxLabel.builder().text("Popover anchored to the button.").build())
                .side(Side.BOTTOM)
                .build();
        popoverBtn.setOnAction(e -> popover.show());

        return Demo.category(title(),
                Demo.block("FxDialog", "Modal dialog with title, body, and footer actions.",
                        Demo.trigger("Open dialog", () -> FxDialog.builder()
                                .title("Dialog title")
                                .body(FxLabel.builder().text("This is a modal dialog body.").build())
                                .okOnly("Got it")
                                .build().show(scene))),

                Demo.block("FxConfirmDialog", "Confirm/cancel prompt with a result callback.",
                        Demo.trigger("Open confirm", () -> FxConfirmDialog.builder()
                                .title("Delete item?")
                                .message("This action cannot be undone.")
                                .confirmText("Delete")
                                .build().show(scene))),

                Demo.block("FxFormDialog", "Dialog wrapping a form body with save/cancel.",
                        Demo.trigger("Open form", () -> FxFormDialog.builder()
                                .title("Edit profile")
                                .body(FxLabel.builder().text("Form fields go here.").build())
                                .saveText("Save")
                                .build().show(scene))),

                Demo.block("FxDrawer", "Panel that slides in from a screen edge.",
                        Demo.trigger("Open drawer", () -> FxDrawer.builder()
                                .side(Side.RIGHT)
                                .size(320)
                                .content(FxLabel.builder().text("Drawer content.").build())
                                .build().open(scene))),

                Demo.block("FxBottomSheet", "Sheet that slides up from the bottom.",
                        Demo.trigger("Open sheet", () -> FxBottomSheet.builder()
                                .height(240)
                                .content(FxLabel.builder().text("Bottom sheet content.").build())
                                .build().open(scene))),

                Demo.block("FxLightbox", "Fullscreen image viewer with navigation.",
                        Demo.trigger("Open lightbox", () -> FxLightbox.builder()
                                .images("https://picsum.photos/id/1015/900/600",
                                        "https://picsum.photos/id/1025/900/600",
                                        "https://picsum.photos/id/1035/900/600")
                                .index(0)
                                .build().open(scene))),

                Demo.block("FxPopover", "Lightweight popup anchored to a node.", popoverBtn),

                Demo.block("FxHoverCard", "Rich card revealed on hover over a target.", hoverCardBtn),

                Demo.block("FxTooltip", "Styled tooltip installed on any node.", tooltipBtn));
    }
}
