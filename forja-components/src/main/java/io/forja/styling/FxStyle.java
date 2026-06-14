package io.forja.styling;

import io.forja.util.ForjaStylesheets;
import javafx.scene.control.Control;

import java.util.UUID;

/**
 * An immutable style override object for Forja components.
 *
 * <p>{@code FxStyle} holds a set of visual property overrides that are
 * applied to a component as a scoped CSS stylesheet. It sits at the top
 * of the Forja cascade hierarchy — above the token layer and any
 * consumer-defined style classes — but applies through the JavaFX CSS
 * engine rather than bypassing it via inline styles.
 *
 * <p>Construct instances via {@link FxStyleBuilder} using the
 * {@link #of()} factory method:
 * <pre>{@code
 * FxStyle style = FxStyle.of()
 *     .background("#FFFFFF")
 *     .backgroundHover("#F5F5F5")
 *     .borderColor("#D1D5DB")
 *     .borderColorFocus("#4F46E5")
 *     .borderRadius(8)
 *     .build();
 * }</pre>
 *
 * <p>{@code FxStyle} is immutable once built and cannot be updated
 * after being applied to a control.
 *
 * @see FxStyleBuilder
 */
public final class FxStyle {

    // ─── Color and text ──────────────────────────────────────────────────────
    final String color;
    final String colorHover;
    final String colorFocus;
    final String colorDisabled;
    final String colorError;

    // ─── Background ──────────────────────────────────────────────────────────
    final String background;
    final String backgroundHover;
    final String backgroundFocus;
    final String backgroundDisabled;

    // ─── Border ──────────────────────────────────────────────────────────────
    final String borderColor;
    final String borderColorHover;
    final String borderColorFocus;
    final String borderColorError;
    final Double borderWidth;
    final Double borderRadius;

    // ─── Typography ──────────────────────────────────────────────────────────
    final Double fontSize;
    final String fontWeight;

    // ─── Spacing ─────────────────────────────────────────────────────────────
    final String padding;

    /**
     * Package-private constructor — instances are created exclusively
     * by {@link FxStyleBuilder#build()}.
     *
     * @param builder the builder containing the configured properties
     */
    FxStyle(FxStyleBuilder builder) {
        this.color              = builder.color;
        this.colorHover         = builder.colorHover;
        this.colorFocus         = builder.colorFocus;
        this.colorDisabled      = builder.colorDisabled;
        this.colorError         = builder.colorError;
        this.background         = builder.background;
        this.backgroundHover    = builder.backgroundHover;
        this.backgroundFocus    = builder.backgroundFocus;
        this.backgroundDisabled = builder.backgroundDisabled;
        this.borderColor        = builder.borderColor;
        this.borderColorHover   = builder.borderColorHover;
        this.borderColorFocus   = builder.borderColorFocus;
        this.borderColorError   = builder.borderColorError;
        this.borderWidth        = builder.borderWidth;
        this.borderRadius       = builder.borderRadius;
        this.fontSize           = builder.fontSize;
        this.fontWeight         = builder.fontWeight;
        this.padding            = builder.padding;
    }

    /**
     * Returns a new {@link FxStyleBuilder} for constructing an
     * {@code FxStyle}.
     *
     * @return a new {@link FxStyleBuilder} instance
     */
    public static FxStyleBuilder of() {
        return new FxStyleBuilder();
    }

    /**
     * Applies this style to the given control by generating a scoped
     * CSS stylesheet and registering it on the control.
     *
     * <p>If the control does not already have an id, a unique one is
     * generated and assigned to scope the stylesheet correctly. If the
     * control already has a consumer-defined id, that id is used instead.
     *
     * <p>This method is called automatically by
     * {@link io.forja.builder.FxComponentBuilder#applyBase(Control)}
     * when an {@code FxStyle} has been set on the builder.
     *
     * @param control the control to apply this style to,
     *                must not be {@code null}
     */
    public void applyTo(Control control) {
        String id = control.getId();
        if (id == null || id.isEmpty()) {
            id = "forja-s-" + UUID.randomUUID().toString().substring(0, 8);
            control.setId(id);
        }

        String css = generateCss(id);
        String uri = ForjaStylesheets.register(css);
        control.getStylesheets().add(uri);
    }

    /**
     * Generates a scoped CSS string for the given id selector,
     * including only the properties that were explicitly set.
     *
     * @param id the CSS id to scope the generated rules to
     * @return the generated CSS string
     */
    private String generateCss(String id) {
        StringBuilder css = new StringBuilder();
        String sel = "#" + id;

        // ─── Default state ────────────────────────────────────────────────
        StringBuilder base = new StringBuilder();
        if (color != null)        append(base, "-fx-text-fill", color);
        if (background != null)   append(base, "-fx-background-color", background);
        if (borderColor != null)  append(base, "-fx-border-color", borderColor);
        if (borderWidth != null)  append(base, "-fx-border-width", borderWidth + "px");
        if (borderRadius != null) {
            append(base, "-fx-border-radius", borderRadius + "px");
            append(base, "-fx-background-radius", borderRadius + "px");
        }
        if (fontSize != null)     append(base, "-fx-font-size", fontSize + "px");
        if (fontWeight != null)   append(base, "-fx-font-weight", fontWeight);
        if (padding != null)      append(base, "-fx-padding", padding);
        if (base.length() > 0)    block(css, sel, base);

        // ─── Hover state ──────────────────────────────────────────────────
        StringBuilder hover = new StringBuilder();
        if (colorHover != null)       append(hover, "-fx-text-fill", colorHover);
        if (backgroundHover != null)  append(hover, "-fx-background-color", backgroundHover);
        if (borderColorHover != null) append(hover, "-fx-border-color", borderColorHover);
        if (hover.length() > 0)       block(css, sel + ":hover", hover);

        // ─── Focus state ──────────────────────────────────────────────────
        StringBuilder focus = new StringBuilder();
        if (colorFocus != null)       append(focus, "-fx-text-fill", colorFocus);
        if (backgroundFocus != null)  append(focus, "-fx-background-color", backgroundFocus);
        if (borderColorFocus != null) append(focus, "-fx-border-color", borderColorFocus);
        if (focus.length() > 0)       block(css, sel + ":focused", focus);

        // ─── Disabled state ───────────────────────────────────────────────
        StringBuilder dis = new StringBuilder();
        if (colorDisabled != null)      append(dis, "-fx-text-fill", colorDisabled);
        if (backgroundDisabled != null) append(dis, "-fx-background-color", backgroundDisabled);
        if (dis.length() > 0)           block(css, sel + ":disabled", dis);

        // ─── Error state (Forja custom pseudo-class) ──────────────────────
        StringBuilder error = new StringBuilder();
        if (colorError != null)       append(error, "-fx-text-fill", colorError);
        if (borderColorError != null) append(error, "-fx-border-color", borderColorError);
        if (error.length() > 0)       block(css, sel + ":error", error);

        return css.toString();
    }

    private void append(StringBuilder sb, String property, String value) {
        sb.append("    ").append(property).append(": ").append(value).append(";\n");
    }

    private void block(StringBuilder css, String selector, StringBuilder rules) {
        css.append(selector).append(" {\n");
        css.append(rules);
        css.append("}\n\n");
    }
}
