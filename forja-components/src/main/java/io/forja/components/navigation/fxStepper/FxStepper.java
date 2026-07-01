package io.forja.components.navigation.fxStepper;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A horizontal multi-step progress indicator: numbered dots + optional
 * labels connected by lines. The active + completed steps get the
 * {@code :active} / {@code :done} pseudo-classes on their dot node.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxStepper wizard = FxStepper.builder()
 *          .steps("Account", "Profile", "Review")
 *          .currentStep(1)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxStepper extends HBox {

    private static final PseudoClass ACTIVE_PC = PseudoClass.getPseudoClass("active");
    private static final PseudoClass DONE_PC   = PseudoClass.getPseudoClass("done");

    private final List<String> steps = new ArrayList<>();
    private final List<StackPane> dots = new ArrayList<>();
    private final IntegerProperty currentStep = new SimpleIntegerProperty(this, "currentStep", 0);

    /**
     * Creates an empty {@code FxStepper}.
     */
    public FxStepper() {
        super();
        getStyleClass().add("forja-stepper");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(0);
        currentStep.addListener((obs, o, v) -> repaint());
    }

    /** Sets the steps and rebuilds. */
    public void setSteps(List<String> steps) {
        this.steps.clear();
        if (steps != null) this.steps.addAll(steps);
        rebuild();
    }

    /** Returns an unmodifiable view of the steps. */
    public List<String> getSteps() { return Collections.unmodifiableList(steps); }

    private void rebuild() {
        getChildren().clear();
        dots.clear();
        for (int i = 0; i < steps.size(); i++) {
            if (i > 0) {
                Region line = new Region();
                line.getStyleClass().add("forja-stepper-line");
                line.setPrefHeight(2);
                line.setMinHeight(2);
                line.setMaxHeight(2);
                line.setPrefWidth(48);
                getChildren().add(line);
            }
            StackPane dot = new StackPane(new FxLabel(String.valueOf(i + 1), LabelVariant.SMALL));
            dot.getStyleClass().add("forja-stepper-dot");
            dot.setPrefSize(24, 24);
            dot.setMinSize(24, 24);
            dot.setMaxSize(24, 24);
            FxLabel caption = FxLabel.builder().text(steps.get(i)).variant(LabelVariant.SMALL).muted(true).build();
            caption.getStyleClass().add("forja-stepper-caption");
            VBox slot = new VBox(2, dot, caption);
            slot.setAlignment(Pos.CENTER);
            dots.add(dot);
            getChildren().add(slot);
        }
        repaint();
    }

    private void repaint() {
        int cur = Math.max(0, Math.min(getCurrentStep(), Math.max(0, dots.size() - 1)));
        for (int i = 0; i < dots.size(); i++) {
            StackPane d = dots.get(i);
            d.pseudoClassStateChanged(DONE_PC, i < cur);
            d.pseudoClassStateChanged(ACTIVE_PC, i == cur);
        }
    }

    /** Returns the current-step property. */
    public IntegerProperty currentStepProperty() { return currentStep; }

    /** Returns the current step index. */
    public int getCurrentStep() { return currentStep.get(); }

    /** Sets the current step index. */
    public void setCurrentStep(int v) { currentStep.set(v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxStepper}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxStepper}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>steps — empty</li>
     *   <li>currentStep — {@code 0}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxStepper, Builder> {

        private List<String> steps = Collections.emptyList();
        private int currentStep = 0;

        public Builder steps(List<String> steps) { this.steps = steps == null ? Collections.<String>emptyList() : steps; return this; }
        public Builder steps(String... steps) { return steps(steps == null ? Collections.<String>emptyList() : Arrays.asList(steps)); }
        public Builder currentStep(int currentStep) { this.currentStep = currentStep; return this; }

        @Override
        public FxStepper build() {
            FxStepper s = new FxStepper();
            s.setSteps(steps);
            s.setCurrentStep(currentStep);
            applyBase(s);
            return s;
        }
    }
}
