package io.forja.components.buttonsAndActions.fxIconButton;

/**
 * Icon placement options for {@link FxIconButton}.
 *
 * <p>Each position maps to a JavaFX
 * {@link javafx.scene.control.ContentDisplay} value and toggles a matching
 * {@code :icon-left}, {@code :icon-right}, {@code :icon-only} pseudo-class
 * on the {@code .forja-icon-button} style class.
 *
 * @see FxIconButton
 */
public enum IconPosition {
    /** Icon before text (leading). */
    LEFT,
    /** Icon after text (trailing). */
    RIGHT,
    /** Icon only, no text. Yields a square button via CSS. */
    ONLY
}
