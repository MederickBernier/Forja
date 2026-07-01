package io.forja.demo;

import io.forja.Forja;
import io.forja.components.layout.fxScrollArea.FxScrollArea;
import io.forja.components.navigation.fxSidebarNav.FxSidebarNav;
import io.forja.demo.categories.ButtonsAndActionsDemo;
import io.forja.demo.categories.CategoryDemo;
import io.forja.demo.categories.ChartsDemo;
import io.forja.demo.categories.DataDisplayDemo;
import io.forja.demo.categories.DateAndTimeDemo;
import io.forja.demo.categories.FeedbackAndStatusDemo;
import io.forja.demo.categories.FileAndMediaInputDemo;
import io.forja.demo.categories.InputsDemo;
import io.forja.demo.categories.LayoutDemo;
import io.forja.demo.categories.MediaDemo;
import io.forja.demo.categories.NavigationDemo;
import io.forja.demo.categories.OverlaysDemo;
import io.forja.demo.categories.SelectionDemo;
import io.forja.demo.categories.TypographyDemo;
import io.forja.demo.categories.UtilitiesDemo;
import io.forja.demo.categories.ValidationDemo;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Forja component gallery. The sidebar (dogfooding {@link FxSidebarNav}) lists every
 * category; selecting one swaps the scroll pane to that category's component demos.
 * Add a category by dropping a {@link CategoryDemo} into {@link #CATEGORIES}.
 */
public class DemoApp extends Application {

    private static final List<CategoryDemo> CATEGORIES = Arrays.asList(
            new ButtonsAndActionsDemo(),
            new TypographyDemo(),
            new InputsDemo(),
            new SelectionDemo(),
            new DateAndTimeDemo(),
            new LayoutDemo(),
            new FeedbackAndStatusDemo(),
            new OverlaysDemo(),
            new DataDisplayDemo(),
            new ChartsDemo(),
            new MediaDemo(),
            new FileAndMediaInputDemo(),
            new NavigationDemo(),
            new UtilitiesDemo(),
            new ValidationDemo()
    );

    @Override
    public void start(Stage stage) {
        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane, 1100, 760);

        // Build every category page once, keyed for instant swapping.
        Map<String, Node> pages = new LinkedHashMap<>();
        for (CategoryDemo cd : CATEGORIES) {
            pages.put(cd.key(), cd.build(scene));
        }

        FxScrollArea content = FxScrollArea.builder().fitToWidth(true).build();

        FxSidebarNav.Builder nav = FxSidebarNav.builder();
        for (CategoryDemo cd : CATEGORIES) {
            nav.item(cd.key(), cd.title(), cd.icon());
        }
        String first = CATEGORIES.get(0).key();
        FxSidebarNav sidebar = nav.active(first)
                .onSelect(key -> content.setContent(pages.get(key)))
                .build();

        content.setContent(pages.get(first));
        sidebar.setMinWidth(240);
        sidebar.setPrefWidth(240);

        rootPane.setLeft(sidebar);
        rootPane.setCenter(content);

        Forja.install(scene);
        stage.setTitle("Forja — Component Gallery");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
