package io.forja.builder;

import io.forja.styling.FxStyle;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base builder for all Forja component builders.
 *
 * <p>Provides the shared property surface common to every Forja component —
 * id, disabled state, visibility, CSS style classes, inline style overrides,
 * tooltip, and user data. Component builders extend this class and inherit
 * all of these properties automatically.
 *
 * <p>The double generic pattern ensures fluent chaining works correctly
 * across the inheritance hierarchy. {@code T} is the component type being
 * built, {@code B} is the concrete builder subclass:
 * <pre>{@code
 * public static class Builder extends FxComponentBuilder<FxButton, Builder> {
 *     ...
 * }
 * }</pre>
 *
 * <p>Every subclass must override {@link #build()} and call
 * {@link #applyBase(Control)} on the constructed control before returning it:
 * <pre>{@code
 * public FxButton build() {
 *     FxButton button = new FxButton(text, variant);
 *     applyBase(button);
 *     return button;
 * }
 * }</pre>
 *
 * @param <T> the type of component this builder produces
 * @param <B> the concrete builder subclass type
 *
 * @see io.forja.components.FxButton.Builder
 */
public abstract class FxComponentBuilder<T extends Control, B extends FxComponentBuilder<T, B>> {

    private String id;
    private boolean disabled = false;
    private boolean visible = true;
    private FxStyle style;
    private String tooltip;
    private Object userData;
    private final List<String> styleClasses = new ArrayList<>();

    /**
     * Sets the CSS id of the component.
     *
     * <p>The id is used to uniquely identify the component in CSS selectors.
     * If an {@link FxStyle} is also set, it will scope its generated
     * stylesheet to this id rather than generating a new one.
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
     * Sets the disabled state of the component.
     *
     * <p>Defaults to {@code false}. When {@code true} the component
     * is visually faded and non-interactive.
     *
     * @param disabled {@code true} to disable the component
     * @return this builder
     */
    @SuppressWarnings("unchecked")
    public B disabled(boolean disabled) {
        this.disabled = disabled;
        return (B) this;
    }

    /**
     * Sets the visibility of the component.
     *
     * <p>Defaults to {@code true}. When {@code false} the component
     * is hidden but still occupies space in the layout.
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
     * they never replace the component's own Forja style class. Classes
     * are applied in the order they are declared, after the token layer
     * and before any inline {@link FxStyle} override.
     *
     * <pre>{@code
     * FxButton.builder()
     *     .text("Save")
     *     .styleClass("my-button", "compact")
     *     .build();
     * }</pre>
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
     * Sets the inline style override for the component.
     *
     * <p>The {@link FxStyle} is applied as a scoped stylesheet at the
     * top of the Forja cascade hierarchy. Only properties explicitly set
     * on the {@link FxStyle} are overridden — all other properties fall
     * through to the token layer defaults.
     *
     * <pre>{@code
     * FxButton.builder()
     *     .text("Save")
     *     .style(FxStyle.of()
     *         .background("#FF0000")
     *         .borderRadius(4)
     *         .build())
     *     .build();
     * }</pre>
     *
     * @param style the style override, or {@code null} to use defaults
     * @return this builder
     */
    @SuppressWarnings("unchecked")
    public B style(FxStyle style) {
        this.style = style;
        return (B) this;
    }

    /**
     * Sets the tooltip text shown when the user hovers over the component.
     *
     * @param text the tooltip text, or {@code null} for no tooltip
     * @return this builder
     */
    @SuppressWarnings("unchecked")
    public B tooltip(String text) {
        this.tooltip = text;
        return (B) this;
    }

    /**
     * Attaches arbitrary user data to the component.
     *
     * <p>Accessible later via {@link javafx.scene.Node#getUserData()}.
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
     * Applies all shared base properties to the given control.
     *
     * <p>Must be called by every subclass {@link #build()} implementation
     * before returning the constructed control. Properties are applied in
     * the following order:
     * <ol>
     *   <li>CSS id</li>
     *   <li>Disabled state</li>
     *   <li>Visibility</li>
     *   <li>Style classes</li>
     *   <li>Tooltip</li>
     *   <li>User data</li>
     *   <li>FxStyle — applied last to respect cascade precedence</li>
     * </ol>
     *
     * @param control the control to configure, must not be {@code null}
     */
    protected void applyBase(T control) {
        if (id != null) {
            control.setId(id);
        }

        control.setDisable(disabled);
        control.setVisible(visible);

        if (!styleClasses.isEmpty()) {
            control.getStyleClass().addAll(styleClasses);
        }

        if (tooltip != null) {
            control.setTooltip(new Tooltip(tooltip));
        }

        if (userData != null) {
            control.setUserData(userData);
        }

        if (style != null) {
            style.applyTo(control);
        }
    }

    /**
     * Constructs and returns the configured component.
     *
     * <p>Implementations must call {@link #applyBase(Control)} on the
     * constructed control before returning it.
     *
     * @return the built component
     */
    public abstract T build();
}