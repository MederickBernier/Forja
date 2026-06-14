package io.forja.builder;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base builder for Forja component builders whose target is a
 * {@link Node} but not a {@link javafx.scene.control.Control}.
 *
 * <p>Mirrors {@link FxComponentBuilder} but with a relaxed bound. Use this for
 * components such as {@code FxIcon}, {@code FxStatusDot}, {@code FxSpacer},
 * and the layout primitives ({@code FxStack}, {@code FxRow},
 * {@code FxContainer}, {@code FxSection}) which extend {@link Node}
 * subclasses ({@link javafx.scene.shape.Shape}, {@link javafx.scene.layout.Region})
 * that are not {@link javafx.scene.control.Control}s.
 *
 * <p>The base exposes the shared property surface common to every Forja
 * non-Control component — id, visibility, CSS style classes, and user data —
 * but omits the Control-only properties ({@code disabled}, {@code tooltip},
 * inline {@link io.forja.styling.FxStyle} overrides) that
 * {@link FxComponentBuilder} provides.
 *
 * <p>The double generic pattern ensures fluent chaining works correctly
 * across the inheritance hierarchy. {@code T} is the component type being
 * built, {@code B} is the concrete builder subclass:
 * <pre>{@code
 * public static class Builder extends FxNodeBuilder<FxIcon, Builder> {
 *     ...
 * }
 * }</pre>
 *
 * <p>Every subclass must override {@link #build()} and call
 * {@link #applyBase(Node)} on the constructed node before returning it:
 * <pre>{@code
 * public FxIcon build() {
 *     FxIcon icon = new FxIcon(literal);
 *     applyBase(icon);
 *     return icon;
 * }
 * }</pre>
 *
 * @param <T> the type of component this builder produces
 * @param <B> the concrete builder subclass type
 *
 * @see FxComponentBuilder
 */
public abstract class FxNodeBuilder<T extends Node, B extends FxNodeBuilder<T, B>> {

    private String id;
    private boolean visible = true;
    private Object userData;
    private final List<String> styleClasses = new ArrayList<>();

    /**
     * Sets the CSS id of the component.
     *
     * @param id the CSS id, must not be {@code null}
     * @return this builder
     */
    @SuppressWarnings("unchecked")
    public B id(String id) {
        this.id = id;
        return (B) this;
    }

    /**
     * Sets the visibility of the component. Defaults to {@code true}.
     *
     * @param visible {@code false} to hide the component
     * @return this builder
     */
    @SuppressWarnings("unchecked")
    public B visible(boolean visible) {
        this.visible = visible;
        return (B) this;
    }

    /**
     * Adds one or more CSS style classes to the component.
     *
     * <p>Classes are appended to the component's existing style classes —
     * they never replace the component's own Forja style class.
     *
     * @param classes one or more CSS class names to add
     * @return this builder
     */
    @SuppressWarnings("unchecked")
    public B styleClass(String... classes) {
        for (String c : classes) {
            if (c != null && !c.isEmpty()) {
                styleClasses.add(c);
            }
        }
        return (B) this;
    }

    /**
     * Attaches arbitrary user data to the component.
     *
     * <p>Accessible later via {@link Node#getUserData()}.
     *
     * @param userData the object to attach, or {@code null}
     * @return this builder
     */
    @SuppressWarnings("unchecked")
    public B userData(Object userData) {
        this.userData = userData;
        return (B) this;
    }

    /**
     * Applies all shared base properties to the given node.
     *
     * <p>Must be called by every subclass {@link #build()} implementation
     * before returning the constructed node. Properties are applied in
     * the following order:
     * <ol>
     *   <li>CSS id</li>
     *   <li>Visibility</li>
     *   <li>Style classes</li>
     *   <li>User data</li>
     * </ol>
     *
     * @param node the node to configure, must not be {@code null}
     */
    protected void applyBase(T node) {
        if (id != null) {
            node.setId(id);
        }

        node.setVisible(visible);

        if (!styleClasses.isEmpty()) {
            node.getStyleClass().addAll(styleClasses);
        }

        if (userData != null) {
            node.setUserData(userData);
        }
    }

    /**
     * Constructs and returns the configured component.
     *
     * <p>Implementations must call {@link #applyBase(Node)} on the
     * constructed node before returning it.
     *
     * @return the built component
     */
    public abstract T build();
}
