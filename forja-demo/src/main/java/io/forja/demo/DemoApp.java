package io.forja.demo;

import io.forja.Forja;
import io.forja.components.ButtonVariant;
import io.forja.components.FxButton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DemoApp extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(24);
        root.setPadding(new Insets(32));

        Label heading = new Label("Forja Demo");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label buttonLabel = new Label("Buttons");
        buttonLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #71717a;");

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

        root.getChildren().addAll(heading, buttonLabel, buttons, disabledButtons);

        Scene scene = new Scene(root, 800, 600);
        Forja.install(scene);

        stage.setTitle("Forja Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}