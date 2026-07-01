package io.forja.components.navigation.fxPagination;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.buttonsAndActions.fxIconButton.FxIconButton;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * A page-number control with previous/next arrows.
 *
 * <p>{@code FxPagination} renders {@code totalPages} numbered buttons plus
 * previous/next icon buttons; the current page's button gets the
 * {@code :active} pseudo-class. When {@code totalPages} exceeds
 * {@link #getVisiblePages}, only a window around the current page is shown
 * with ellipsis markers.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxPagination pg = FxPagination.builder()
 *          .totalPages(20)
 *          .currentPage(0)
 *          .visiblePages(7)
 *          .onPageChange(p -> viewModel.loadPage(p))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxPagination extends HBox {

    private static final PseudoClass ACTIVE_PC = PseudoClass.getPseudoClass("active");

    private final IntegerProperty totalPages = new SimpleIntegerProperty(this, "totalPages", 1);
    private final IntegerProperty currentPage = new SimpleIntegerProperty(this, "currentPage", 0);
    private final IntegerProperty visiblePages = new SimpleIntegerProperty(this, "visiblePages", 7);
    private final FxIconButton prev = FxIconButton.builder().icon("fth-chevron-left").build();
    private final FxIconButton next = FxIconButton.builder().icon("fth-chevron-right").build();
    private OnPageChange onPageChange;

    /**
     * Creates a single-page {@code FxPagination}.
     */
    public FxPagination() {
        super();
        getStyleClass().add("forja-pagination");
        setAlignment(Pos.CENTER);
        setSpacing(4);
        prev.setOnAction(e -> setCurrentPage(getCurrentPage() - 1));
        next.setOnAction(e -> setCurrentPage(getCurrentPage() + 1));

        totalPages.addListener((obs, o, v) -> rebuild());
        currentPage.addListener((obs, o, v) -> rebuild());
        visiblePages.addListener((obs, o, v) -> rebuild());
        rebuild();
    }

    private void rebuild() {
        getChildren().clear();
        int n = Math.max(1, getTotalPages());
        int cur = Math.max(0, Math.min(getCurrentPage(), n - 1));
        int win = Math.max(3, getVisiblePages());
        prev.setDisable(cur == 0);
        next.setDisable(cur >= n - 1);
        getChildren().add(prev);
        int start = Math.max(0, cur - win / 2);
        int end = Math.min(n - 1, start + win - 1);
        start = Math.max(0, end - win + 1);
        if (start > 0) getChildren().add(pageBtn(0, cur));
        if (start > 1) getChildren().add(ellipsis());
        for (int i = start; i <= end; i++) getChildren().add(pageBtn(i, cur));
        if (end < n - 2) getChildren().add(ellipsis());
        if (end < n - 1) getChildren().add(pageBtn(n - 1, cur));
        getChildren().add(next);
        if (onPageChange != null) onPageChange.accept(cur);
    }

    private Button pageBtn(final int i, int cur) {
        Button b = new Button(String.valueOf(i + 1));
        b.getStyleClass().add("forja-pagination-page");
        b.setOnAction(e -> setCurrentPage(i));
        b.pseudoClassStateChanged(ACTIVE_PC, i == cur);
        return b;
    }

    private Button ellipsis() {
        Button b = new Button("…");
        b.getStyleClass().add("forja-pagination-ellipsis");
        b.setDisable(true);
        return b;
    }

    /** Returns the total-pages property. */
    public IntegerProperty totalPagesProperty() { return totalPages; }
    /** Returns the total pages. */
    public int getTotalPages() { return totalPages.get(); }
    /** Sets the total pages. */
    public void setTotalPages(int v) { totalPages.set(Math.max(1, v)); }

    /** Returns the current-page property. */
    public IntegerProperty currentPageProperty() { return currentPage; }
    /** Returns the current page (zero-based). */
    public int getCurrentPage() { return currentPage.get(); }
    /** Sets the current page, clamped to {@code [0, totalPages-1]}. */
    public void setCurrentPage(int v) { currentPage.set(Math.max(0, Math.min(getTotalPages() - 1, v))); }

    /** Returns the visible-pages property. */
    public IntegerProperty visiblePagesProperty() { return visiblePages; }
    /** Returns the window size. */
    public int getVisiblePages() { return visiblePages.get(); }
    /** Sets the window size. */
    public void setVisiblePages(int v) { visiblePages.set(Math.max(3, v)); }

    /** Sets the page-change callback. */
    public void setOnPageChange(OnPageChange onPageChange) { this.onPageChange = onPageChange; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxPagination}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /** Callback fired when the current page changes. */
    @FunctionalInterface
    public interface OnPageChange { void accept(int page); }

    /**
     * Fluent builder for constructing an {@link FxPagination}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>totalPages — {@code 1}</li>
     *   <li>currentPage — {@code 0}</li>
     *   <li>visiblePages — {@code 7}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxPagination, Builder> {

        private int totalPages = 1;
        private int currentPage = 0;
        private int visiblePages = 7;
        private OnPageChange onPageChange;

        public Builder totalPages(int totalPages) { this.totalPages = Math.max(1, totalPages); return this; }
        public Builder currentPage(int currentPage) { this.currentPage = currentPage; return this; }
        public Builder visiblePages(int visiblePages) { this.visiblePages = Math.max(3, visiblePages); return this; }
        public Builder onPageChange(OnPageChange onPageChange) { this.onPageChange = onPageChange; return this; }

        @Override
        public FxPagination build() {
            FxPagination p = new FxPagination();
            p.setOnPageChange(onPageChange);
            p.setTotalPages(totalPages);
            p.setVisiblePages(visiblePages);
            p.setCurrentPage(currentPage);
            applyBase(p);
            return p;
        }
    }
}
