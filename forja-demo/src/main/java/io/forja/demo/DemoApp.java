package io.forja.demo;

import io.forja.Forja;
import io.forja.components.ButtonVariant;
import io.forja.components.FxButton;
import io.forja.components.FxIcon;
import io.forja.components.FxLabel;
import io.forja.components.FxSeparator;
import io.forja.components.IconVariant;
import io.forja.components.LabelVariant;
import io.forja.components.SeparatorVariant;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DemoApp extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(24);
        root.setPadding(new Insets(32));

        FxLabel heading = FxLabel.builder()
                .text("Forja Demo")
                .variant(LabelVariant.DISPLAY)
                .build();

        FxLabel buttonSectionLabel = FxLabel.builder()
                .text("Buttons")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox buttons = new HBox(8);
        buttons.getChildren().addAll(
                FxButton.builder().text("Primary").variant(ButtonVariant.PRIMARY).build(),
                FxButton.builder().text("Secondary").variant(ButtonVariant.SECONDARY).build(),
                FxButton.builder().text("Ghost").variant(ButtonVariant.GHOST).build(),
                FxButton.builder().text("Danger").variant(ButtonVariant.DANGER).build()
        );

        HBox disabledButtons = new HBox(8);
        disabledButtons.getChildren().addAll(
                FxButton.builder().text("Loading...").variant(ButtonVariant.PRIMARY).loading(true).build(),
                FxButton.builder().text("Disabled").variant(ButtonVariant.SECONDARY).loading(true).build()
        );

        FxLabel typographySectionLabel = FxLabel.builder()
                .text("Typography")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        VBox typography = new VBox(8);
        typography.getChildren().addAll(
                FxLabel.builder().text("Display — 24px / bold").variant(LabelVariant.DISPLAY).build(),
                FxLabel.builder().text("Heading — 18px / bold").variant(LabelVariant.HEADING).build(),
                FxLabel.builder().text("Subheading — 14px / bold").variant(LabelVariant.SUBHEADING).build(),
                FxLabel.builder().text("Body — 13px / normal").variant(LabelVariant.BODY).build(),
                FxLabel.builder().text("Small — 11px / normal").variant(LabelVariant.SMALL).build(),
                FxLabel.builder().text("Mono — 12px / JetBrains Mono").variant(LabelVariant.MONO).build(),
                FxLabel.builder().text("Muted body — secondary information").variant(LabelVariant.BODY).muted(true).build()
        );

        FxLabel primitivesSectionLabel = FxLabel.builder()
                .text("Primitives")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        VBox separators = new VBox(12);
        separators.getChildren().addAll(
                FxLabel.builder().text("Hairline (0.5px)").variant(LabelVariant.SMALL).muted(true).build(),
                FxSeparator.builder().variant(SeparatorVariant.HAIRLINE).build(),
                FxLabel.builder().text("Default (1px)").variant(LabelVariant.SMALL).muted(true).build(),
                FxSeparator.builder().variant(SeparatorVariant.DEFAULT).build(),
                FxLabel.builder().text("Strong (2px)").variant(LabelVariant.SMALL).muted(true).build(),
                FxSeparator.builder().variant(SeparatorVariant.STRONG).build()
        );

        HBox verticalSeparatorRow = new HBox(12);
        verticalSeparatorRow.getChildren().addAll(
                FxLabel.builder().text("Left").variant(LabelVariant.BODY).build(),
                FxSeparator.builder().orientation(Orientation.VERTICAL).variant(SeparatorVariant.DEFAULT).build(),
                FxLabel.builder().text("Middle").variant(LabelVariant.BODY).build(),
                FxSeparator.builder().orientation(Orientation.VERTICAL).variant(SeparatorVariant.STRONG).build(),
                FxLabel.builder().text("Right").variant(LabelVariant.BODY).build()
        );

        VBox primitives = new VBox(16);
        primitives.getChildren().addAll(separators, verticalSeparatorRow);

        FxLabel iconsSectionLabel = FxLabel.builder()
                .text("Icons")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build();

        HBox iconVariants = new HBox(16);
        iconVariants.setAlignment(Pos.CENTER_LEFT);
        iconVariants.getChildren().addAll(
                iconWithLabel("fth-check-circle", IconVariant.DEFAULT, "default"),
                iconWithLabel("fth-circle",       IconVariant.MUTED,   "muted"),
                iconWithLabel("fth-zap",          IconVariant.ACCENT,  "accent"),
                iconWithLabel("fth-check",        IconVariant.SUCCESS, "success"),
                iconWithLabel("fth-alert-triangle", IconVariant.WARNING, "warning"),
                iconWithLabel("fth-x-octagon",    IconVariant.DANGER,  "danger"),
                iconWithLabel("fth-info",         IconVariant.INFO,    "info")
        );

        HBox iconSizes = new HBox(16);
        iconSizes.setAlignment(Pos.CENTER_LEFT);
        iconSizes.getChildren().addAll(
                FxIcon.builder().literal("fth-settings").size(12).build(),
                FxIcon.builder().literal("fth-settings").size(16).build(),
                FxIcon.builder().literal("fth-settings").size(24).build(),
                FxIcon.builder().literal("fth-settings").size(32).build(),
                FxIcon.builder().literal("fth-settings").size(48).build()
        );

        VBox icons = new VBox(16);
        icons.getChildren().addAll(iconVariants, iconSizes);

        root.getChildren().addAll(
                heading,
                buttonSectionLabel, buttons, disabledButtons,
                typographySectionLabel, typography,
                primitivesSectionLabel, primitives,
                iconsSectionLabel, icons
        );

        Scene scene = new Scene(root, 900, 900);
        Forja.install(scene);

        stage.setTitle("Forja Demo");
        stage.setScene(scene);
        stage.show();
    }

    private static VBox iconWithLabel(String literal, IconVariant variant, String label) {
        VBox box = new VBox(4);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                FxIcon.builder().literal(literal).size(24).variant(variant).build(),
                FxLabel.builder().text(label).variant(LabelVariant.SMALL).muted(true).build()
        );
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
