package io.forja.components.utilities.fxThemeToggle;

import io.forja.Forja;
import io.forja.FxTheme;
import io.forja.builder.FxComponentBuilder;
import io.forja.components.utilities.fxIcon.FxIcon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * A button that toggles between {@link FxTheme#LIGHT} and {@link FxTheme#DARK}
 * on the target {@link Scene} via {@link Forja#applyTheme}.
 *
 * <p>The icon reflects the current theme — sun in dark mode, moon in light
 * mode.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxThemeToggle toggle = FxThemeToggle.builder()
 *          .scene(scene)
 *          .initial(FxTheme.LIGHT)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxThemeToggle extends Button {

    private static final String LIGHT_ICON = "fth-moon";
    private static final String DARK_ICON  = "fth-sun";

    private final ObjectProperty<FxTheme> theme = new SimpleObjectProperty<>(this, "theme", FxTheme.LIGHT);
    private Scene scene;

    /**
     * Creates an {@code FxThemeToggle} in {@link FxTheme#LIGHT} mode.
     */
    public FxThemeToggle() {
        super();
        getStyleClass().addAll("forja-button", "forja-theme-toggle");
        setGraphic(new FxIcon(LIGHT_ICON));
        theme.addListener((obs, o, v) -> refreshGraphicAndApply());
        setOnAction(e -> toggle());
    }

    /** Flips the theme and applies it to the target scene. */
    public void toggle() {
        setTheme(getTheme() == FxTheme.DARK ? FxTheme.LIGHT : FxTheme.DARK);
    }

    private void refreshGraphicAndApply() {
        setGraphic(new FxIcon(getTheme() == FxTheme.DARK ? DARK_ICON : LIGHT_ICON));
        if (scene != null) Forja.applyTheme(scene, getTheme());
    }

    /** Returns the theme property. */
    public ObjectProperty<FxTheme> themeProperty() { return theme; }

    /** Returns the current theme. */
    public FxTheme getTheme() { return theme.get(); }

    /** Sets the current theme; applies to the bound scene if any. */
    public void setTheme(FxTheme v) { theme.set(v == null ? FxTheme.LIGHT : v); }

    /** Returns the target scene, or {@code null}. */
    public Scene getTargetScene() { return scene; }

    /** Sets the target scene the toggle applies its theme to. */
    public void setTargetScene(Scene scene) {
        this.scene = scene;
        if (scene != null) Forja.applyTheme(scene, getTheme());
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxThemeToggle}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxThemeToggle}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>scene — {@code null}</li>
     *   <li>initial — {@link FxTheme#LIGHT}</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxThemeToggle, Builder> {

        private Scene scene;
        private FxTheme initial = FxTheme.LIGHT;

        public Builder scene(Scene scene) { this.scene = scene; return this; }
        public Builder initial(FxTheme initial) { this.initial = initial == null ? FxTheme.LIGHT : initial; return this; }

        @Override
        public FxThemeToggle build() {
            FxThemeToggle t = new FxThemeToggle();
            t.setTheme(initial);
            t.setTargetScene(scene);
            applyBase(t);
            return t;
        }
    }
}
