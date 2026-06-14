package io.forja.tokens;

import javafx.css.PseudoClass;
import javafx.scene.Node;

/**
 * The shared semantic color palette used by Forja components.
 *
 * <p>One enum replaces what was previously four near-identical per-component
 * enums (icon, badge, chip, status-dot). Each value maps to a token-driven
 * color in {@code base.css} and to a matching CSS pseudo-class on the
 * component's style class (e.g. {@code .forja-badge:success}).
 *
 * <p>Components apply the active variant via {@link #applyTo(Node, SemanticVariant)},
 * which sets the matching pseudo-class on the node and clears the others.
 */
public enum SemanticVariant {
    /** Neutral / default visual treatment. */
    DEFAULT,
    /** De-emphasized — pairs with {@code -forja-text-muted}. */
    MUTED,
    /** Brand accent — pairs with {@code -forja-accent}. */
    ACCENT,
    /** Positive / success — pairs with {@code -forja-success}. */
    SUCCESS,
    /** Cautionary — pairs with {@code -forja-warning}. */
    WARNING,
    /** Destructive / error — pairs with {@code -forja-danger}. */
    DANGER,
    /** Informational — pairs with {@code -forja-info}. */
    INFO;

    /**
     * Returns the CSS pseudo-class matching this variant
     * (lower-case enum name).
     */
    public PseudoClass pseudoClass() {
        return PseudoClass.getPseudoClass(name().toLowerCase());
    }

    /**
     * Toggles the {@code active} variant on {@code node} and clears every
     * other semantic variant pseudo-class. Use from a component skin's
     * variant listener to keep CSS in sync.
     *
     * @param node target node — typically the component itself
     * @param active variant to activate, or {@code null} to clear all
     */
    public static void applyTo(Node node, SemanticVariant active) {
        for (SemanticVariant v : values()) {
            node.pseudoClassStateChanged(v.pseudoClass(), v == active);
        }
    }
}
