# Forja

> Shape what already works.

A modern JavaFX UI component library for Java 8 and above.

Forja sits on top of JavaFX — not around it. It leverages the existing scene graph, CSS engine, skin architecture, and property binding system, and adds the one thing JavaFX has always been missing: a design system, a fluent API, and components that look like they were made in this decade.

**135 components** across 14 categories.

## Docs

Full component reference, quickstart, and foundation guide:
**[https://mederick.github.io/forja/](https://mederick.github.io/forja/)**

## Install

```xml
<dependency>
    <groupId>io.forja</groupId>
    <artifactId>forja-components</artifactId>
    <version>0.1.0</version>
</dependency>
```

## First component

```java
import io.forja.Forja;
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxButton.FxButton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyApp extends Application {

    @Override
    public void start(Stage stage) {
        FxButton save = FxButton.builder()
            .text("Save")
            .variant(ButtonVariant.PRIMARY)
            .onAction(e -> System.out.println("saved"))
            .build();

        Scene scene = new Scene(new VBox(save), 300, 200);
        Forja.install(scene);
        stage.setScene(scene);
        stage.show();
    }
}
```

## Why Forja

- **Modern design** — zinc-based neutral palette, indigo accent, clean typography, subtle interaction states. No drop shadows, no gradients, no noise.
- **Fluent builder API** — construct components with a chainable, type-safe builder.
- **Zero ceremony** — one dependency, one method call. Your app looks good before you write a single style rule.
- **Java 8+** — works where your codebase already lives.
- **Still JavaFX** — every component is a real JavaFX control. FXML, SceneBuilder, accessibility tools, existing code — all compatible, no escape hatches needed.

## Categories

| Category | Count | | Category | Count |
|---|---|---|---|---|
| Buttons & Actions | 7 | | Overlays | 9 |
| Typography | 7 | | Data Display | 15 |
| Inputs | 13 | | Charts | 10 |
| Selection | 10 | | Media | 4 |
| Date & Time | 6 | | File Input | 4 |
| Layout | 17 | | Navigation | 13 |
| Feedback & Status | 13 | | Utilities | 7 |
| | | | Validation | 4 |

## Contributing

See the [Roadmap](https://mederick.github.io/forja/roadmap/) for milestone status.
Issues and PRs welcome at [github.com/mederick/forja](https://github.com/mederick/forja).

## License

MIT
