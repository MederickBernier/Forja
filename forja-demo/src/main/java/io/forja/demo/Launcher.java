package io.forja.demo;

/**
 * Plain entry point that does not extend {@code javafx.application.Application}.
 *
 * <p>The javafx-maven-plugin moves the JavaFX jars to the module path (and, with
 * classifier jars, drops them) when the configured mainClass extends Application.
 * Launching through this class keeps JavaFX on the classpath, so {@code mvn javafx:run}
 * and a plain {@code java -cp ... io.forja.demo.Launcher} both work.
 */
public final class Launcher {
    private Launcher() {}

    public static void main(String[] args) {
        DemoApp.main(args);
    }
}
