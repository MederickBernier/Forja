package io.forja.components.navigation.fxBreadcrumbs;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.typography.fxLink.FxLink;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A trail of clickable segments separated by {@code /}.
 *
 * <p>Each non-last segment is an {@link FxLink}; the last is a muted
 * {@link FxLabel} representing the current location.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxBreadcrumbs bc = FxBreadcrumbs.builder()
 *          .segments("Projects", "Forja", "Docs")
 *          .onSelect(idx -> router.upTo(idx))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxBreadcrumbs extends HBox {

    private final List<String> segments = new ArrayList<>();
    private OnSelect onSelect;

    /**
     * Creates an empty {@code FxBreadcrumbs}.
     */
    public FxBreadcrumbs() {
        super();
        getStyleClass().add("forja-breadcrumbs");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(6);
    }

    /** Sets the segments and rebuilds. */
    public void setSegments(List<String> segments) {
        this.segments.clear();
        if (segments != null) this.segments.addAll(segments);
        rebuild();
    }

    /** Returns an unmodifiable view of the segments. */
    public List<String> getSegments() { return Collections.unmodifiableList(segments); }

    /** Sets the on-select callback. */
    public void setOnSelect(OnSelect onSelect) { this.onSelect = onSelect; }

    private void rebuild() {
        getChildren().clear();
        for (int i = 0; i < segments.size(); i++) {
            final int idx = i;
            String s = segments.get(i);
            if (i < segments.size() - 1) {
                FxLink link = FxLink.builder().text(s).onAction(e -> { if (onSelect != null) onSelect.accept(idx); }).build();
                getChildren().add(link);
                FxLabel sep = FxLabel.builder().text("/").variant(LabelVariant.BODY).muted(true).build();
                sep.getStyleClass().add("forja-breadcrumbs-sep");
                getChildren().add(sep);
            } else {
                FxLabel cur = FxLabel.builder().text(s).variant(LabelVariant.BODY).build();
                cur.getStyleClass().add("forja-breadcrumbs-current");
                getChildren().add(cur);
            }
        }
    }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxBreadcrumbs}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when a segment is clicked. */
    @FunctionalInterface
    public interface OnSelect { void accept(int index); }

    /**
     * Fluent builder for constructing an {@link FxBreadcrumbs}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>segments — empty</li>
     *   <li>onSelect — none</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxBreadcrumbs, Builder> {

        private List<String> segments = Collections.emptyList();
        private OnSelect onSelect;

        public Builder segments(List<String> segments) { this.segments = segments == null ? Collections.<String>emptyList() : segments; return this; }
        public Builder segments(String... segments) { return segments(segments == null ? Collections.<String>emptyList() : Arrays.asList(segments)); }
        public Builder onSelect(OnSelect onSelect) { this.onSelect = onSelect; return this; }

        @Override
        public FxBreadcrumbs build() {
            FxBreadcrumbs bc = new FxBreadcrumbs();
            bc.setOnSelect(onSelect);
            bc.setSegments(segments);
            applyBase(bc);
            return bc;
        }
    }
}
