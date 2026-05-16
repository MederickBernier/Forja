package io.forja.components;

import io.forja.builder.FxComponentBuilder;
import io.forja.skin.FxButtonSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;

import static io.forja.Forja.applyBase;

/**
 * A styled button component build on top of {@link Button}.
 *
 * <p>{@code FxButton} extends the standard JavaFX {@code Button} - all native
 * JavaFX properties, bindings, and event APIs remain fully accessible. Forja
 * adds a visual variant system, a loading state, and a fluent builder API.</p>
 *
 * <p>The preferred way to construct an {@code FxButton} is via the builder:</p>
 * <pre>
 *     {@code
 *      FxButton button = FxButton.builder()
 *          .text("Save")
 *          .variant(ButtonVariant.PRIMARY)
 *          .onAction(e->handleSave())
 *          .build()
 *     }
 * </pre>
 *
 * <p>Constructor and direct instantiation are also supported for simpler cases</p>
 * <pre>
 *     {@code
 *      FxButton button = new FxButton("Save", ButtonVariant.PRIMARY);
 *     }
 * </pre>
 *
 * <p>Since {@code FxButton} is a standard {@code Button}, it is fully
 * compatible with FXML, SceneBuilder, and all existing JavaFX APIs.</p>
 *
 * @see ButtonVariant
 * @see Builder
 */
public class FxButton extends Button{

    private final ObjectProperty<ButtonVariant> variant = new SimpleObjectProperty<>(this, "variant", ButtonVariant.PRIMARY);
    private final BooleanProperty loading = new SimpleBooleanProperty(this,"loading",false);

    /**
     * Creates an {@code FxButton} with no text and the default variant
     * ({@link ButtonVariant#PRIMARY}
     */
    public FxButton(){
        super();
        getStyleClass().add("forja-button");
    }

    /**
     * Creates an {@code FxButton} with the given text and the default variant
     * ({@link ButtonVariant#PRIMARY}
     *
     * @param text the label text displayed on the button
     */
    public FxButton(String text){
        super(text);
        getStyleClass().add("forja-button");
    }

    /**
     * Creates an {@code FxButton} with the given text and variant.
     *
     * @param text the label text displayed on the button
     * @param variant the visual variant - see {@link ButtonVariant}
     */
    public FxButton(String text, ButtonVariant variant){
        this(text);
        setVariant(variant);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns the Forja button skin which drives variant-based styling
     * and loading state pseudo-class management.</p>
     * @return
     */
    @Override
    protected Skin<?> createDefaultSkin(){
        return new FxButtonSkin(this);
    }

    /**
     * Returns the variant property of this button
     * <p>The variant controls the visual style of the button. It can be
     * observed or bound like any standard JavaFX property:</p>
     * <pre>
     *     {@code
     *      button.variantProperty().addListener((obs,old,val)->...)
     *     }
     * </pre>
     *
     * @return the {@link ObjectProperty holding the current {@link ButtonVariant}}
     */
    public ObjectProperty<ButtonVariant> variantProperty(){return variant;}

    /**
     * Returns the current visual variant of the button
     *
     * @return the current {@link ButtonVariant}, never {@code null}
     */
    public ButtonVariant getVariant(){return variant.get();}

    /**
     * Sets the visual variant of this button
     *
     * @param v the desired {@link ButtonVariant}, must not be {@code null}
     */
    public void setVariant(ButtonVariant v){variant.set(v);}

    /**
     * Returns the loading property of this button
     *
     * <p>When {@code true}, the button enters a loading state: it is visually
     * faded and interaction is disabled. Bind this to an observable value to
     * drive loading state reactively:</p>
     * <pre>
     * {@code
     *  button.loadingProperty().bind(viewModel.isSaving());
     * }
     * </pre>
     * @return the {@link BooleanProperty} controlling the loading state
     */
    public BooleanProperty loadingProperty() {return loading;}

    /**
     * Returns whether this button is currently in a loading state.
     *
     * @return {@code trye} if the button is loading, {@code false} otherwise
     */
    public boolean isLoading(){return loading.get();}

    /**
     * Sets the loading state of this button
     *
     * <p>When set to {@code true}, the button is visually faded and
     * interaction is disabled until loading is set back to {@code false}</p>
     *
     * @param v {@code true} to enter loading state, {@code false} to exit
     */
    public void setLoading(boolean v){loading.set(v);}

    /**
     * Returns a new {@link Builder} for constructing an {@code FxButton}
     * with a fluent API
     *
     * <pre>{@code
     *  FXButton button = FxButton.builder()
     *      .text("Save")
     *      .variant(ButtonVariant.PRIMARY)
     *      .onAction(e->handleSave())
     *      .build();
     * }</pre>
     * @return a new {@link Builder} instance
     */
    public static Builder builder(){return new Builder();}

    /**
     * Fluent builder for constructing an {@link FxButton}.
     *
     * <p>All builder methods return {@code this} to support chaining.
     * Call {@link #build()} to produce the configured {@link FxButton}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>text — empty string</li>
     *   <li>variant — {@link ButtonVariant#PRIMARY}</li>
     *   <li>loading — {@code false}</li>
     *   <li>onAction — none</li>
     * </ul>
     *
     * <p>Inherited from {@link io.forja.builder.FxComponentBuilder}:
     * <ul>
     *   <li>id, disabled, visible, styleClass, style, tooltip, userData</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxButton, Builder> {

        private String text = "";
        private ButtonVariant variant = ButtonVariant.PRIMARY;
        private boolean loading = false;
        private EventHandler<ActionEvent> onAction;

        /**
         * Sets the label text displayed on the button.
         *
         * @param text the button label, must not be {@code null}
         * @return this builder
         */
        public Builder text(String text) {
            this.text = text;
            return this;
        }

        /**
         * Sets the visual variant of the button.
         *
         * <p>Defaults to {@link ButtonVariant#PRIMARY} if not specified.
         *
         * @param variant the desired variant, must not be {@code null}
         * @return this builder
         */
        public Builder variant(ButtonVariant variant) {
            this.variant = variant;
            return this;
        }

        /**
         * Sets the initial loading state of the button.
         *
         * <p>When {@code true}, the button is faded and non-interactive.
         * Defaults to {@code false}.
         *
         * @param loading {@code true} to start in loading state
         * @return this builder
         */
        public Builder loading(boolean loading) {
            this.loading = loading;
            return this;
        }

        /**
         * Sets the action handler fired when the button is clicked.
         *
         * @param handler the event handler, or {@code null} for no action
         * @return this builder
         */
        public Builder onAction(EventHandler<ActionEvent> handler) {
            this.onAction = handler;
            return this;
        }

        /**
         * Constructs and returns the configured {@link FxButton}.
         *
         * @return a new {@link FxButton} with the properties set on this builder
         */
        @Override
        public FxButton build() {
            FxButton button = new FxButton(text, variant);
            button.setLoading(loading);
            if (onAction != null) {
                button.setOnAction(onAction);
            }
            applyBase(button);
            return button;
        }
    }
}
