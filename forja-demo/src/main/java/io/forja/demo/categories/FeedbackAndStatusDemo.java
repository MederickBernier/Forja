package io.forja.demo.categories;

import io.forja.components.feedbackAndStatus.fxAlert.FxAlert;
import io.forja.components.feedbackAndStatus.fxBadge.FxBadge;
import io.forja.components.feedbackAndStatus.fxBanner.FxBanner;
import io.forja.components.feedbackAndStatus.fxChip.FxChip;
import io.forja.components.feedbackAndStatus.fxEmptyState.FxEmptyState;
import io.forja.components.feedbackAndStatus.fxErrorState.FxErrorState;
import io.forja.components.feedbackAndStatus.fxNotificationCenter.FxNotificationCenter;
import io.forja.components.feedbackAndStatus.fxProgressBar.FxProgressBar;
import io.forja.components.feedbackAndStatus.fxProgressCircle.FxProgressCircle;
import io.forja.components.feedbackAndStatus.fxResultPage.FxResultPage;
import io.forja.components.feedbackAndStatus.fxSkeleton.FxSkeleton;
import io.forja.components.feedbackAndStatus.fxStatusDot.FxStatusDot;
import io.forja.components.feedbackAndStatus.fxToast.FxToast;
import io.forja.tokens.SemanticVariant;
import javafx.scene.Node;
import javafx.scene.Scene;

public class FeedbackAndStatusDemo implements CategoryDemo {

    @Override public String key() { return "feedback-and-status"; }
    @Override public String title() { return "Feedback & Status"; }
    @Override public String icon() { return "fth-alert-circle"; }

    @Override
    public Node build(Scene scene) {
        return Demo.category(title(),
                Demo.block("FxAlert", "Inline alert box with title, description, and semantic variant.",
                        FxAlert.builder().variant(SemanticVariant.INFO).title("Heads up").description("This is an informational alert.").build(),
                        FxAlert.builder().variant(SemanticVariant.SUCCESS).title("Saved").description("Your changes were saved.").build(),
                        FxAlert.builder().variant(SemanticVariant.WARNING).title("Careful").description("This action needs review.").build(),
                        FxAlert.builder().variant(SemanticVariant.DANGER).title("Error").description("Something went wrong.").dismissible(true).build()),

                Demo.block("FxBadge", "Small count/label pill with a semantic color.",
                        FxBadge.builder().text("Default").variant(SemanticVariant.DEFAULT).build(),
                        FxBadge.builder().text("Success").variant(SemanticVariant.SUCCESS).build(),
                        FxBadge.builder().text("Warning").variant(SemanticVariant.WARNING).build(),
                        FxBadge.builder().text("Danger").variant(SemanticVariant.DANGER).build()),

                Demo.block("FxBanner", "Full-width page banner with an action and dismiss.",
                        FxBanner.builder().variant(SemanticVariant.INFO).message("A new version is available.").actionText("Refresh").build(),
                        FxBanner.builder().variant(SemanticVariant.WARNING).message("Your trial ends soon.").actionText("Upgrade").dismissible(true).build()),

                Demo.block("FxChip", "Compact tag chip, optionally removable.",
                        FxChip.builder().text("Design").variant(SemanticVariant.DEFAULT).build(),
                        FxChip.builder().text("Active").variant(SemanticVariant.SUCCESS).build(),
                        FxChip.builder().text("Remove me").variant(SemanticVariant.ACCENT).removable(true).build()),

                Demo.block("FxEmptyState", "Placeholder for empty content areas.",
                        FxEmptyState.builder().icon("fth-inbox").heading("No messages")
                                .description("You're all caught up.").build()),

                Demo.block("FxErrorState", "Error placeholder with a retry action slot.",
                        FxErrorState.builder().icon("fth-alert-triangle").heading("Failed to load")
                                .description("Check your connection and try again.")
                                .action(Demo.trigger("Retry", () -> {})).build()),

                Demo.block("FxNotificationCenter", "Stacked scene notifications fired via the static helper.",
                        Demo.trigger("Notify info", () -> FxNotificationCenter.show(scene, "New activity in your feed.", SemanticVariant.INFO)),
                        Demo.trigger("Notify success", () -> FxNotificationCenter.show(scene, "Upload complete.", SemanticVariant.SUCCESS))),

                Demo.block("FxProgressBar", "Linear progress, determinate or indeterminate.",
                        FxProgressBar.builder().progress(0.4).variant(SemanticVariant.ACCENT).build(),
                        FxProgressBar.builder().progress(0.75).variant(SemanticVariant.SUCCESS).build(),
                        FxProgressBar.builder().indeterminate(true).variant(SemanticVariant.INFO).build()),

                Demo.block("FxProgressCircle", "Circular progress indicator.",
                        FxProgressCircle.builder().progress(0.6).size(48).variant(SemanticVariant.ACCENT).build(),
                        FxProgressCircle.builder().indeterminate(true).size(48).variant(SemanticVariant.INFO).build()),

                Demo.block("FxResultPage", "Full result screen for success/failure outcomes.",
                        FxResultPage.builder().status(FxResultPage.Status.SUCCESS).title("Payment complete")
                                .description("Your order is on its way.").build(),
                        FxResultPage.builder().status(FxResultPage.Status.FAILURE).title("Payment failed")
                                .description("Please try a different card.").build()),

                Demo.block("FxSkeleton", "Loading placeholders in rect, text, and circle shapes.",
                        FxSkeleton.builder().shape(FxSkeleton.Shape.RECT).width(160).height(24).build(),
                        FxSkeleton.builder().shape(FxSkeleton.Shape.TEXT).width(220).height(12).build(),
                        FxSkeleton.builder().shape(FxSkeleton.Shape.CIRCLE).width(40).height(40).build()),

                Demo.block("FxStatusDot", "Tiny colored dot for status indicators.",
                        FxStatusDot.builder().radius(6).variant(SemanticVariant.SUCCESS).build(),
                        FxStatusDot.builder().radius(6).variant(SemanticVariant.WARNING).build(),
                        FxStatusDot.builder().radius(6).variant(SemanticVariant.DANGER).build()),

                Demo.block("FxToast", "Transient popup toast fired via the static helper.",
                        Demo.trigger("Toast info", () -> FxToast.show(scene, "Copied to clipboard.", SemanticVariant.INFO)),
                        Demo.trigger("Toast success", () -> FxToast.show(scene, "Settings saved.", SemanticVariant.SUCCESS))));
    }
}
