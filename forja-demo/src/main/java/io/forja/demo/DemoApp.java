package io.forja.demo;

import io.forja.Forja;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DemoApp extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(16);
        root.setPadding(new Insets(32));

        Label heading = new Label("Forja Demo");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label subtitle = new Label("Components will appear here as they are built.");
        subtitle.setStyle("-fx-font-size: 13px;");

        Button placeholder = new Button("Placeholder button");

        root.getChildren().addAll(heading, subtitle, placeholder);

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