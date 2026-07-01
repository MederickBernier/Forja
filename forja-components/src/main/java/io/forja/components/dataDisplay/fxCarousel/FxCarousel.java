package io.forja.components.dataDisplay.fxCarousel;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.buttonsAndActions.fxIconButton.FxIconButton;
import javafx.animation.PauseTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A single-slide-at-a-time carousel — next/prev icon buttons + dots
 * indicator + optional auto-advance.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxCarousel c = FxCarousel.builder()
 *          .slides(imgA, imgB, imgC)
 *          .autoAdvance(3000)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxCarousel extends StackPane {

    private static final PseudoClass DOT_ACTIVE_PC = PseudoClass.getPseudoClass("active");

    private final ObservableList<Node> slides = FXCollections.observableArrayList();
    private final IntegerProperty index = new SimpleIntegerProperty(this, "index", 0);
    private final BooleanProperty autoAdvancing = new SimpleBooleanProperty(this, "autoAdvancing", false);
    private long autoAdvanceMs = 0;

    private final StackPane slideHost = new StackPane();
    private final FxIconButton prevBtn = FxIconButton.builder().icon("fth-chevron-left").build();
    private final FxIconButton nextBtn = FxIconButton.builder().icon("fth-chevron-right").build();
    private final HBox dotsRow = new HBox(6);
    private final List<Region> dots = new ArrayList<>();
    private final PauseTransition autoTimer = new PauseTransition();

    /**
     * Creates an empty {@code FxCarousel}.
     */
    public FxCarousel() {
        super();
        getStyleClass().add("forja-carousel");
        slideHost.getStyleClass().add("forja-carousel-slide-host");
        dotsRow.getStyleClass().add("forja-carousel-dots");
        dotsRow.setAlignment(Pos.CENTER);
        StackPane.setAlignment(prevBtn, Pos.CENTER_LEFT);
        StackPane.setAlignment(nextBtn, Pos.CENTER_RIGHT);
        StackPane.setAlignment(dotsRow, Pos.BOTTOM_CENTER);
        getChildren().addAll(slideHost, prevBtn, nextBtn, dotsRow);

        prevBtn.setOnAction(e -> prev());
        nextBtn.setOnAction(e -> next());

        slides.addListener((javafx.collections.ListChangeListener<Node>) c -> { rebuildDots(); refreshSlide(); });
        index.addListener((obs, o, v) -> refreshSlide());
        autoTimer.setOnFinished(e -> { next(); if (autoAdvancing.get()) autoTimer.playFromStart(); });
    }

    private void rebuildDots() {
        dotsRow.getChildren().clear();
        dots.clear();
        for (int i = 0; i < slides.size(); i++) {
            final int idx = i;
            Region dot = new Region();
            dot.getStyleClass().add("forja-carousel-dot");
            dot.setMinSize(8, 8);
            dot.setMaxSize(8, 8);
            dot.setOnMouseClicked(e -> setIndex(idx));
            dots.add(dot);
            dotsRow.getChildren().add(dot);
        }
        paintDots();
    }

    private void refreshSlide() {
        slideHost.getChildren().clear();
        if (slides.isEmpty()) return;
        int i = Math.max(0, Math.min(getIndex(), slides.size() - 1));
        slideHost.getChildren().add(slides.get(i));
        paintDots();
    }

    private void paintDots() {
        int cur = getIndex();
        for (int i = 0; i < dots.size(); i++) dots.get(i).pseudoClassStateChanged(DOT_ACTIVE_PC, i == cur);
    }

    /** Advances to the next slide (wraps). */
    public void next() {
        if (slides.isEmpty()) return;
        index.set((getIndex() + 1) % slides.size());
    }

    /** Retreats to the previous slide (wraps). */
    public void prev() {
        if (slides.isEmpty()) return;
        int n = slides.size();
        index.set((getIndex() - 1 + n) % n);
    }

    /** Enables auto-advance at the given interval; {@code 0} disables. */
    public void setAutoAdvanceMs(long ms) {
        this.autoAdvanceMs = Math.max(0, ms);
        autoTimer.stop();
        if (autoAdvanceMs > 0) {
            autoTimer.setDuration(Duration.millis(autoAdvanceMs));
            autoAdvancing.set(true);
            autoTimer.playFromStart();
        } else {
            autoAdvancing.set(false);
        }
    }

    /** Stops auto-advance. */
    public void stopAutoAdvance() { autoAdvancing.set(false); autoTimer.stop(); }

    /** Returns the slides list. */
    public ObservableList<Node> getSlides() { return slides; }
    /** Returns the index property. */
    public IntegerProperty indexProperty() { return index; }
    /** Returns the current index. */
    public int getIndex() { return index.get(); }
    /** Sets the current index. */
    public void setIndex(int v) { index.set(v); }
    /** Returns whether auto-advance is running. */
    public boolean isAutoAdvancing() { return autoAdvancing.get(); }
    /** Returns the prev button. */
    public FxIconButton getPrevButton() { return prevBtn; }
    /** Returns the next button. */
    public FxIconButton getNextButton() { return nextBtn; }
    /** Returns the dots row. */
    public HBox getDotsRow() { return dotsRow; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxCarousel}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxCarousel}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>slides — empty</li>
     *   <li>index — {@code 0}</li>
     *   <li>autoAdvanceMs — {@code 0} (off)</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxCarousel, Builder> {

        private Node[] slides = new Node[0];
        private int index = 0;
        private long autoAdvanceMs = 0;

        public Builder slides(Node... slides) { this.slides = slides == null ? new Node[0] : slides; return this; }
        public Builder index(int index) { this.index = index; return this; }
        public Builder autoAdvance(long ms) { this.autoAdvanceMs = Math.max(0, ms); return this; }

        @Override
        public FxCarousel build() {
            FxCarousel c = new FxCarousel();
            c.getSlides().setAll(Arrays.asList(slides));
            c.setIndex(index);
            if (autoAdvanceMs > 0) c.setAutoAdvanceMs(autoAdvanceMs);
            applyBase(c);
            return c;
        }
    }
}
