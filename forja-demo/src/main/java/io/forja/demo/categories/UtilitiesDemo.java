package io.forja.demo.categories;

import io.forja.FxTheme;
import io.forja.components.layout.fxScrollArea.FxScrollArea;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.utilities.fxFocusTrap.FxFocusTrap;
import io.forja.components.utilities.fxIcon.FxIcon;
import io.forja.components.utilities.fxKeybindingHint.FxKeybindingHint;
import io.forja.components.utilities.fxPortal.FxPortal;
import io.forja.components.utilities.fxScrollSpy.FxScrollSpy;
import io.forja.components.utilities.fxSearchHighlight.FxSearchHighlight;
import io.forja.components.utilities.fxThemeToggle.FxThemeToggle;
import io.forja.tokens.SemanticVariant;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UtilitiesDemo implements CategoryDemo {

    @Override public String key() { return "utilities"; }
    @Override public String title() { return "Utilities"; }
    @Override public String icon() { return "fth-tool"; }

    @Override
    public Node build(Scene scene) {
        // FxFocusTrap — behavioral: traps Tab focus within a small VBox of two fields.
        VBox trapRoot = new VBox(8, new TextField("First field"), new TextField("Second field"));
        FxFocusTrap.builder().root(trapRoot).build();

        // FxScrollSpy — behavioral: tracks which section is visible in a scroll area.
        FxScrollArea spyArea = FxScrollArea.builder()
                .content(new VBox(8,
                        new FxLabel("Section one"),
                        new FxLabel("Section two"))).build();
        FxScrollSpy.builder().scrollPane(spyArea)
                .section("one", spyArea.getContent()).build();

        return Demo.category(title(),
                Demo.block("FxIcon", "Ikonli glyph with a size and a semantic color variant.",
                        FxIcon.builder().literal("fth-home").size(24).variant(SemanticVariant.DEFAULT).build(),
                        FxIcon.builder().literal("fth-check-circle").size(24).variant(SemanticVariant.SUCCESS).build(),
                        FxIcon.builder().literal("fth-alert-triangle").size(24).variant(SemanticVariant.WARNING).build(),
                        FxIcon.builder().literal("fth-x-circle").size(24).variant(SemanticVariant.DANGER).build()),

                Demo.block("FxKeybindingHint", "Renders a keyboard shortcut as styled key caps.",
                        FxKeybindingHint.builder().keys("Ctrl", "K").build(),
                        FxKeybindingHint.builder().keys("Cmd", "Shift", "P").build()),

                Demo.block("FxSearchHighlight", "Highlights matches of a query within a run of text.",
                        FxSearchHighlight.builder().text("The quick brown fox jumps over").query("o").build()),

                Demo.block("FxThemeToggle", "Button that flips the scene between light and dark themes.",
                        FxThemeToggle.builder().scene(scene).initial(FxTheme.LIGHT).build()),

                // ponytail: minimal — behavioral helper, not a visible widget
                Demo.block("FxFocusTrap", "behavioral helper — keeps Tab focus inside its root", trapRoot),

                // ponytail: minimal — behavioral helper, not a visible widget
                Demo.block("FxPortal", "behavioral helper — renders content elsewhere",
                        makePortal(scene)),

                // ponytail: minimal — behavioral helper, not a visible widget
                Demo.block("FxScrollSpy", "behavioral helper — tracks the active section", spyArea));
    }

    private Node makePortal(Scene scene) {
        FxLabel content = new FxLabel("see docs");
        FxPortal.builder().scene(scene).node(content).build();
        return new FxLabel("see docs");
    }
}
