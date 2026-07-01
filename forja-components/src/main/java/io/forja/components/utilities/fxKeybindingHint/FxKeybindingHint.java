package io.forja.components.utilities.fxKeybindingHint;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxKbd.FxKbd;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A row of {@link FxKbd} chips separated by a small "+" glyph — a compact
 * way to display a keyboard shortcut like {@code ⌘ + K}.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxKeybindingHint hint = FxKeybindingHint.builder()
 *          .keys("⌘", "K")
 *          .build();
 *     }
 * </pre>
 *
 * @see FxKbd
 * @see Builder
 */
public class FxKeybindingHint extends HBox {

    /**
     * Creates an empty {@code FxKeybindingHint}.
     */
    public FxKeybindingHint() {
        super();
        getStyleClass().add("forja-keybinding-hint");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(4);
    }

    private void setKeys(List<String> keys) {
        getChildren().clear();
        for (int i = 0; i < keys.size(); i++) {
            if (i > 0) {
                FxLabel plus = FxLabel.builder().text("+").variant(LabelVariant.SMALL).muted(true).build();
                plus.getStyleClass().add("forja-keybinding-hint-sep");
                getChildren().add(plus);
            }
            FxKbd k = FxKbd.builder().text(keys.get(i)).build();
            getChildren().add(k);
        }
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxKeybindingHint}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxKeybindingHint}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>keys — empty</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxKeybindingHint, Builder> {

        private List<String> keys = Collections.emptyList();

        public Builder keys(List<String> keys) { this.keys = keys == null ? Collections.<String>emptyList() : keys; return this; }
        public Builder keys(String... keys) { return keys(keys == null ? Collections.<String>emptyList() : Arrays.asList(keys)); }

        @Override
        public FxKeybindingHint build() {
            FxKeybindingHint h = new FxKeybindingHint();
            h.setKeys(keys);
            applyBase(h);
            return h;
        }
    }
}
