package io.forja.styling;

/**
 * Fluent builder for constructing an {@link io.forja.styling.FxStyle}
 *
 * <p>All properties are optional. Unset properties fall through to
 * the Forja token layer and component skin defaults. Only properties
 * explicitely set on this builder are included in the generated stylesheet.</p>
 *
 * <p>The prefered entry point is {@link io.forja.styling.FxStyle#of()}</p>:
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
 * @see io.forja.styling.FxStyle
 */

public final class FxStyleBuilder{
    //----- Color and Text
    String color;
    String colorHover;
    String colorFocus;
    String colorDisabled;
    String colorError;

    //----- Background
    String background;
    String backgroundHover;
    String backgroundFocus;
    String backgroundDisabled;

    //----- Border
    String borderColor;
    String borderColorHover;
    String borderColorFocus;
    String borderColorError;
    Double borderWidth;
    Double borderRadius;

    //----- Typography
    Double fontSize;
    String fontWeight;

    //----- Spacing
    String padding;

    /**
     * Package-accessible constructor - instances are created via
     * {@link FxStyle#of()}
     */
    FxStyleBuilder(){}

    //----- Color and Text

    /**
     * Sets the default text color.
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder color(String value){
        this.color = value;
        return this;
    }

    /**
     * Sets the text color on hover.
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder colorHover(String value){
        this.colorHover = value;
        return this;
    }

    /**
     * Sets the text color when focused
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder colorFocus(String value){
        this.colorFocus = value;
        return this;
    }

    /**
     * Sets the color when disabled
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder colorDisabled(String value){
        this.colorDisabled = value;
        return this;
    }
    /**
     * Sets the text color when in an error state
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder colorError(String value){
        this.colorError = value;
        return this;
    }

    //----- Background

    /**
     * Sets the default background color
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder background(String value){
        this.background = value;
        return this;
    }

    /**
     * Sets the background color on hover
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder backgroundHover(String value){
        this.backgroundHover = value;
        return this;
    }

    /**
     * Sets the background color when focused
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder backgroundFocus(String value){
        this.backgroundFocus = value;
        return this;
    }

    /**
     * Sets the background color when disabled
     *
     * @param value hex or CSS color
     * @return this builder
     */
    public FxStyleBuilder backgroundDisabled(String value){
        this.backgroundDisabled = value;
        return this;
    }
    //----- Border
    /**
     * Sets the default border color
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder borderColor(String value){
        this.borderColor = value;
        return this;
    }

    /**
     * Sets the border color on hover
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder borderColorHover(String value){
        this.borderColorHover = value;
        return this;
    }

    /**
     * Sets the border color when focused
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder borderColorFocus(String value){
        this.borderColorFocus = value;
        return this;
    }

    /**
     * Sets the border color when in an error state
     *
     * @param value hex or CSS color value
     * @return this builder
     */
    public FxStyleBuilder borderColorError(String value){
        this.borderColorError = value;
        return this;
    }

    /**
     * Sets the border width in pixels
     *
     * @param px border width in pixels
     * @return this builder
     */
    public FxStyleBuilder borderWidth(double px){
        this.borderWidth = px;
        return this;
    }

    /**
     * Sets the border and background radius in pixels
     *
     * @param px radius in pixels
     * @return this builder
     */
    public FxStyleBuilder borderRadius(double px){
        this.borderRadius = px;
        return this;
    }

    //----- Typography

    /**
     * Sets the font size in pixels
     *
     * @param px font size in pixels
     * @return this builder
     */
    public FxStyleBuilder fontSize(double px){
        this.fontSize = px;
        return this;
    }

    /**
     * Sets the font weight
     *
     * @param weight {@code "normal} or {@code "bold"}
     * @return this builder
     */
    public FxStyleBuilder fontWeight(String weight){
        this.fontWeight = weight;
        return this;
    }

    //----- Spacing

    /**
     * Sets uniform padding on all sizes
     *
     * @param px in pixels
     * @return this builder
     */
    public FxStyleBuilder padding(double px){
        this.padding = px+"px";
        return this;
    }

    /**
     * Sets vertical and horizontal padding independently
     *
     * @param vertical top and bottom padding in pixels
     * @param horizontal left and right padding in pixels
     * @return this builder
     */
    public FxStyleBuilder padding(double vertical, double horizontal){
        this.padding = vertical+"px"+horizontal+"px";
        return this;
    }

    //-----

    /**
     * Constructs and returns the immutable {@link FxStyle}
     *
     * @return a new {@link FxStyle} with the properties set on this builder
     */
    public FxStyle build(){
        return new FxStyle(this);
    }
}
