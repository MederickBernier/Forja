package io.forja;

import javafx.scene.Scene;
import javafx.scene.text.Font;

public final class Forja{
    private Forja(){}

    public static void install(Scene scene){
        install(scene, FxTheme.SYSTEM);
    }

    public static void install(Scene scene, FxTheme theme){
        loadFonts();
        applyBase(scene);
        applyTheme(scene, theme);
    }

    public static void applyTheme(Scene scene, FxTheme theme){
        scene.getStylesheets().removeIf(s->s.contains("light.css") || s.contains("dark.css"));

        switch(resolveTheme(theme)){
            case LIGHT:
                scene.getStylesheets().add(stylesheet("light.css"));
                break;
            case DARK:
                scene.getStylesheets().add(stylesheet("dark.css"));
                break;
            default:
                break;
        }
    }

    public static void applyBase(Scene scene){
        String base = stylesheet("base.css");
        if(!scene.getStylesheets().contains(base)){
            scene.getStylesheets().add(0,base);
        }
    }

    private static FxTheme resolveTheme(FxTheme theme){
        if(theme != FxTheme.SYSTEM) return theme;
        return FxTheme.LIGHT;
    }

    private static void loadFonts() {
        loadFont("/io/forja/fonts/inter/Inter-Regular.ttf");
        loadFont("/io/forja/fonts/inter/Inter-Medium.ttf");
        loadFont("/io/forja/fonts/jetbrains-mono/JetBrainsMono-Regular.ttf");
        loadFont("/io/forja/fonts/noto-sans/NotoSans-Regular.ttf");
    }

    private static void loadFont(String path){
        try{
            Font.loadFont(Forja.class.getResourceAsStream(path),13);
        }catch(Exception ignored){
            // Font not bundled yet - silently skipped
        }
    }

    private static String stylesheet(String name) {
        String path = "/io/forja/css/" + name;
        java.net.URL url = Forja.class.getResource(path);
        if (url == null) {
            url = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("io/forja/css/" + name);
        }
        if (url == null) {
            throw new RuntimeException("Forja: stylesheet not found: " + path);
        }
        return url.toExternalForm();
    }
}