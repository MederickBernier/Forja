package io.forja.components.feedbackAndStatus.fxNotificationCenter;

import io.forja.components.feedbackAndStatus.fxAlert.FxAlert;
import io.forja.components.overlays.OverlayHost;
import io.forja.tokens.SemanticVariant;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A stack of dismissible notifications anchored to a scene corner via the
 * {@link OverlayHost} overlay layer.
 *
 * <p>{@code FxNotificationCenter} is a {@link VBox} that renders one
 * {@link FxAlert} per entry. Call {@link #post} to add a notification; each
 * is dismissible via its ×. The center is installed on a {@link Scene} via
 * {@link #install}.
 *
 * <p>Static convenience: {@link #show(Scene, String, SemanticVariant)} adds a
 * notification to the scene's center (creating it on first use).
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxNotificationCenter nc = FxNotificationCenter.builder()
 *          .position(Pos.TOP_RIGHT)
 *          .maxVisible(5)
 *          .build();
 *      nc.install(scene);
 *      nc.post("Saved", SemanticVariant.SUCCESS);
 *     }
 * </pre>
 *
 * @see FxAlert
 * @see OverlayHost
 * @see Builder
 */
public class FxNotificationCenter extends VBox {

    private static final String SCENE_KEY = "forja-notification-center";

    private final ObservableList<FxAlert> notifications = FXCollections.observableArrayList();
    private final IntegerProperty maxVisible = new SimpleIntegerProperty(this, "maxVisible", 5);
    private Pos position = Pos.TOP_RIGHT;

    /**
     * Creates an empty {@code FxNotificationCenter} in the top-right position.
     */
    public FxNotificationCenter() {
        super();
        getStyleClass().add("forja-notification-center");
        setSpacing(8);
        setPickOnBounds(false);
        setFillWidth(false);
    }

    /**
     * Installs this center on the given scene's overlay layer.
     *
     * @param scene target scene
     */
    public void install(Scene scene) {
        if (scene == null) return;
        StackPane.setAlignment(this, position);
        StackPane.setMargin(this, new Insets(20));
        OverlayHost.show(scene, this);
        scene.getProperties().put(SCENE_KEY, this);
    }

    /**
     * Posts a notification.
     *
     * @param message text
     * @param variant color variant
     * @return the newly-created {@link FxAlert}
     */
    public FxAlert post(String message, SemanticVariant variant) {
        FxAlert a = FxAlert.builder()
                .title(message)
                .variant(variant == null ? SemanticVariant.INFO : variant)
                .dismissible(true)
                .onDismiss(x -> notifications.remove(x))
                .build();
        notifications.add(0, a);
        getChildren().add(0, a);
        trimIfNeeded();
        return a;
    }

    private void trimIfNeeded() {
        int max = Math.max(1, getMaxVisible());
        while (notifications.size() > max) {
            FxAlert last = notifications.remove(notifications.size() - 1);
            getChildren().remove(last);
        }
    }

    /** Clears all notifications. */
    public void clear() {
        notifications.clear();
        getChildren().clear();
    }

    /** Returns the observable notification list. */
    public ObservableList<FxAlert> getNotifications() { return notifications; }

    /** Returns the max-visible property. */
    public IntegerProperty maxVisibleProperty() { return maxVisible; }
    /** Returns the current max-visible count. */
    public int getMaxVisible() { return maxVisible.get(); }
    /** Sets the max-visible count. */
    public void setMaxVisible(int v) { maxVisible.set(Math.max(1, v)); }

    /** Returns the position. */
    public Pos getPosition() { return position; }
    /** Sets the position. */
    public void setPosition(Pos position) {
        this.position = position == null ? Pos.TOP_RIGHT : position;
        StackPane.setAlignment(this, this.position);
    }

    /**
     * Convenience — posts a notification to the scene, installing a default
     * center if none is registered yet.
     *
     * @param scene   target scene
     * @param message text
     * @param variant color variant
     */
    public static void show(Scene scene, String message, SemanticVariant variant) {
        if (scene == null) return;
        FxNotificationCenter nc = (FxNotificationCenter) scene.getProperties().get(SCENE_KEY);
        if (nc == null) {
            nc = FxNotificationCenter.builder().build();
            nc.install(scene);
        }
        nc.post(message, variant);
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxNotificationCenter}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxNotificationCenter}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>position — {@link Pos#TOP_RIGHT}</li>
     *   <li>maxVisible — {@code 5}</li>
     * </ul>
     */
    public static class Builder {

        private Pos position = Pos.TOP_RIGHT;
        private int maxVisible = 5;

        public Builder position(Pos position) { this.position = position == null ? Pos.TOP_RIGHT : position; return this; }
        public Builder maxVisible(int maxVisible) { this.maxVisible = Math.max(1, maxVisible); return this; }

        public FxNotificationCenter build() {
            FxNotificationCenter nc = new FxNotificationCenter();
            nc.setPosition(position);
            nc.setMaxVisible(maxVisible);
            return nc;
        }
    }
}
