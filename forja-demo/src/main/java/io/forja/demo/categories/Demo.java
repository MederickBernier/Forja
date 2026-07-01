package io.forja.demo.categories;

import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxButton.FxButton;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Small helpers so each category file stays one-line-per-component. No logic here —
 * just the header/label/wrapper boilerplate that used to be hand-rolled in DemoApp.
 */
public final class Demo {

    private Demo() {}

    /** A category page: big title followed by one block per component. */
    public static Node category(String title, Node... blocks) {
        VBox page = new VBox(28);
        page.setPadding(new Insets(32));
        page.getChildren().add(FxLabel.builder().text(title).variant(LabelVariant.DISPLAY).build());
        page.getChildren().addAll(blocks);
        return page;
    }

    /** One component block: name + description + a wrapping row of live examples. */
    public static Node block(String name, String desc, Node... examples) {
        VBox box = new VBox(8);
        box.getChildren().add(FxLabel.builder().text(name).variant(LabelVariant.SUBHEADING).build());
        if (desc != null && !desc.isEmpty()) {
            box.getChildren().add(FxLabel.builder().text(desc).variant(LabelVariant.SMALL).muted(true).build());
        }
        FlowPane examplesPane = new FlowPane(12, 12);
        examplesPane.setAlignment(Pos.CENTER_LEFT);
        examplesPane.getChildren().addAll(examples);
        box.getChildren().add(examplesPane);
        return box;
    }

    /** Horizontal row of nodes, left-aligned. */
    public static HBox row(Node... nodes) {
        HBox box = new HBox(8);
        box.setAlignment(Pos.CENTER_LEFT);
        box.getChildren().addAll(nodes);
        return box;
    }

    /** Vertical stack of nodes. */
    public static VBox col(Node... nodes) {
        VBox box = new VBox(8);
        box.getChildren().addAll(nodes);
        return box;
    }

    /** A primary button that runs an action — used to open overlays/dialogs. */
    public static FxButton trigger(String text, Runnable onClick) {
        return FxButton.builder().text(text).variant(ButtonVariant.PRIMARY)
                .onAction(e -> onClick.run()).build();
    }
}
