package io.forja.demo.categories;

import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * One category page in the demo. Implementations build a scrollable node showing
 * every component in their category. The {@link Scene} is passed so overlay demos
 * (dialogs, drawers, ...) can call {@code .show(scene)}.
 */
public interface CategoryDemo {

    /** Stable key used by the sidebar nav + content map. */
    String key();

    /** Human title shown in the sidebar and as the page heading. */
    String title();

    /** Ikonli literal for the sidebar item (e.g. {@code "fth-square"}). */
    String icon();

    /** Builds the category page. */
    Node build(Scene scene);
}
