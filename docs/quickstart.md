# Quickstart

## Install

Add the dependency to your Maven build:

```xml
<dependency>
    <groupId>io.forja</groupId>
    <artifactId>forja-components</artifactId>
    <version>0.1.0</version>
</dependency>
```

Gradle:

```groovy
implementation 'io.forja:forja-components:0.1.0'
```

## Install the stylesheet

Call `Forja.install(scene)` once on your scene. This registers the base
stylesheet, loads bundled fonts (if present), and applies the initial theme.

```java
import io.forja.Forja;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyApp extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(buildRoot(), 900, 600);
        Forja.install(scene);
        stage.setScene(scene);
        stage.show();
    }
}
```

## Your first component

Every component ships a fluent builder plus a plain constructor. The builder
covers the full property surface; the constructor is a shortcut for the
80% cases.

```java
import io.forja.components.buttonsAndActions.fxButton.ButtonVariant;
import io.forja.components.buttonsAndActions.fxButton.FxButton;

FxButton save = FxButton.builder()
    .text("Save")
    .variant(ButtonVariant.PRIMARY)
    .onAction(e -> handleSave())
    .build();
```

## Themes

Forja ships light and dark themes plus a `SYSTEM` fallback. Switch with
`Forja.applyTheme(scene, theme)`:

```java
Forja.applyTheme(scene, FxTheme.DARK);
```

For an interactive toggle, drop an `FxThemeToggle` on your scene.

## Next steps

- Read the [Foundation](foundation.md) page for tokens, the theme system,
  and builder patterns you can extend with your own components.
- Browse the component categories in the sidebar. Each page shows the
  builder usage snippet and lists every builder method.
