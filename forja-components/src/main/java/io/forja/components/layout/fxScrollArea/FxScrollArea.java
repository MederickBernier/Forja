package io.forja.components.layout.fxScrollArea;

import io.forja.builder.FxComponentBuilder;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

/**
 * A styled scroll area — a {@link ScrollPane} wrapper.
 *
 * <p>Extends the standard JavaFX {@code ScrollPane} — content, viewport, and
 * scrollbar APIs remain fully accessible.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxScrollArea sa = FxScrollArea.builder()
 *          .content(longVBox)
 *          .fitToWidth(true)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxScrollArea extends ScrollPane {

    /**
     * Creates an empty {@code FxScrollArea}.
     */
    public FxScrollArea() {
        super();
        getStyleClass().add("forja-scroll-area");
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxScrollArea}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxScrollArea}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>content — {@code null}</li>
     *   <li>fitToWidth — {@code true}</li>
     *   <li>fitToHeight — {@code false}</li>
     *   <li>hbarPolicy — {@link ScrollPane.ScrollBarPolicy#AS_NEEDED}</li>
     *   <li>vbarPolicy — {@link ScrollPane.ScrollBarPolicy#AS_NEEDED}</li>
     * </ul>
     */
    public static class Builder extends FxComponentBuilder<FxScrollArea, Builder> {

        private Node content;
        private boolean fitToWidth = true;
        private boolean fitToHeight = false;
        private ScrollPane.ScrollBarPolicy hbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED;
        private ScrollPane.ScrollBarPolicy vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED;

        public Builder content(Node content) { this.content = content; return this; }
        public Builder fitToWidth(boolean fitToWidth) { this.fitToWidth = fitToWidth; return this; }
        public Builder fitToHeight(boolean fitToHeight) { this.fitToHeight = fitToHeight; return this; }
        public Builder hbarPolicy(ScrollPane.ScrollBarPolicy hbarPolicy) { this.hbarPolicy = hbarPolicy == null ? ScrollPane.ScrollBarPolicy.AS_NEEDED : hbarPolicy; return this; }
        public Builder vbarPolicy(ScrollPane.ScrollBarPolicy vbarPolicy) { this.vbarPolicy = vbarPolicy == null ? ScrollPane.ScrollBarPolicy.AS_NEEDED : vbarPolicy; return this; }

        @Override
        public FxScrollArea build() {
            FxScrollArea a = new FxScrollArea();
            a.setContent(content);
            a.setFitToWidth(fitToWidth);
            a.setFitToHeight(fitToHeight);
            a.setHbarPolicy(hbarPolicy);
            a.setVbarPolicy(vbarPolicy);
            applyBase(a);
            return a;
        }
    }
}
