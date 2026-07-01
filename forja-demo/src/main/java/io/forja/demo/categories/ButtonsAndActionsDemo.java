package io.forja.demo.categories;

import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.buttonsAndActions.fxButtonGroup.FxButtonGroup;
import io.forja.components.buttonsAndActions.fxCopyButton.FxCopyButton;
import io.forja.components.buttonsAndActions.fxIconButton.FxIconButton;
import io.forja.components.buttonsAndActions.fxIconButton.IconPosition;
import io.forja.components.buttonsAndActions.fxMenuButton.FxMenuButton;
import io.forja.components.buttonsAndActions.fxSplitButton.FxSplitButton;
import io.forja.components.buttonsAndActions.fxToggleButton.FxToggleButton;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;

public class ButtonsAndActionsDemo implements CategoryDemo {

    @Override public String key() { return "buttons-and-actions"; }
    @Override public String title() { return "Buttons & Actions"; }
    @Override public String icon() { return "fth-mouse-pointer"; }

    @Override
    public Node build(Scene scene) {
        return Demo.category(title(),
                Demo.block("FxButton", "Text button with variant, loading state, and action handler.",
                        FxButton.builder().text("Primary").variant(ButtonVariant.PRIMARY).build(),
                        FxButton.builder().text("Secondary").variant(ButtonVariant.SECONDARY).build(),
                        FxButton.builder().text("Ghost").variant(ButtonVariant.GHOST).build(),
                        FxButton.builder().text("Danger").variant(ButtonVariant.DANGER).build(),
                        FxButton.builder().text("Loading...").variant(ButtonVariant.PRIMARY).loading(true).build()),

                Demo.block("FxIconButton", "Icon-first button with optional label position.",
                        FxIconButton.builder().icon("fth-heart").iconPosition(IconPosition.ONLY).build(),
                        FxIconButton.builder().text("Like").icon("fth-heart").iconPosition(IconPosition.LEFT).build(),
                        FxIconButton.builder().text("Next").icon("fth-arrow-right").iconPosition(IconPosition.RIGHT).build()),

                Demo.block("FxToggleButton", "Stateful button that stays pressed.",
                        FxToggleButton.builder().text("Off").build(),
                        FxToggleButton.builder().text("On").selected(true).build()),

                Demo.block("FxButtonGroup", "Segmented row of toggle buttons with single-select behavior.",
                        FxButtonGroup.builder().items("Day", "Week", "Month").value("Week").build()),

                Demo.block("FxCopyButton", "Copies a value to the clipboard and flashes confirmation.",
                        FxCopyButton.builder().text("Copy token").value("forja-abc-123").confirmText("Copied!").build()),

                Demo.block("FxSplitButton", "Primary click surface plus a chevron menu.",
                        FxSplitButton.builder().text("Save")
                                .items(new MenuItem("Save as draft"), new MenuItem("Save and close")).build()),

                Demo.block("FxMenuButton", "Icon-only kebab-style menu trigger.",
                        FxMenuButton.builder().icon("fth-more-vertical")
                                .items(new MenuItem("Edit"), new MenuItem("Duplicate"), new MenuItem("Delete")).build()));
    }
}
