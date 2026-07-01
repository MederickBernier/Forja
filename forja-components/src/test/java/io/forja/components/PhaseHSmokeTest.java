package io.forja.components;

import io.forja.components.feedbackAndStatus.fxEmptyState.FxEmptyState;
import io.forja.components.feedbackAndStatus.fxErrorState.FxErrorState;
import io.forja.components.feedbackAndStatus.fxNotificationCenter.FxNotificationCenter;
import io.forja.components.feedbackAndStatus.fxResultPage.FxResultPage;
import io.forja.components.inputs.fxTextField.FxTextField;
import io.forja.components.overlays.OverlayHost;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.validation.fxErrorSummary.FxErrorSummary;
import io.forja.components.validation.fxForm.FxForm;
import io.forja.components.validation.fxFormField.FxFormField;
import io.forja.components.validation.fxValidator.FxValidator;
import io.forja.tokens.SemanticVariant;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.assertHasPseudoClass;
import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseHSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void notificationCenter_postAppendsAndClamps() {
        Scene scene = onFx(() -> new Scene(new VBox(), 800, 600));
        FxNotificationCenter nc = onFx(() -> FxNotificationCenter.builder().maxVisible(3).position(Pos.TOP_RIGHT).build());
        onFx(() -> { nc.install(scene); return null; });
        onFx(() -> { nc.post("A", SemanticVariant.INFO); nc.post("B", SemanticVariant.SUCCESS); nc.post("C", SemanticVariant.WARNING); nc.post("D", SemanticVariant.DANGER); return null; });
        assertEquals(3, nc.getNotifications().size(), "trimmed to maxVisible");
        StackPane layer = onFx(() -> OverlayHost.getLayer(scene));
        assertTrue(layer.getChildren().contains(nc));
    }

    @Test
    void emptyState_setsHeadingDescription() {
        FxEmptyState e = onFx(() -> FxEmptyState.builder().icon("fth-inbox").heading("Empty").description("No data").build());
        assertEquals("Empty", e.getHeadingLabel().getText());
        assertEquals("No data", e.getDescriptionLabel().getText());
    }

    @Test
    void errorState_defaultsIconAndClass() {
        FxErrorState e = onFx(() -> FxErrorState.builder().heading("Bad").build());
        assertTrue(e.getStyleClass().contains("forja-error-state"));
        assertEquals("Bad", e.getInner().getHeadingLabel().getText());
    }

    @Test
    void resultPage_statusUpdatesPseudoClass() {
        for (FxResultPage.Status s : FxResultPage.Status.values()) {
            FxResultPage rp = onFx(() -> FxResultPage.builder().status(s).title("t").build());
            assertHasPseudoClass(rp, s.name().toLowerCase());
        }
    }

    @Test
    void validator_shortCircuitsOnFirstError() {
        FxValidator<String> v = FxValidator.<String>of(
                FxValidator.required(),
                FxValidator.email("Bad email")
        );
        assertEquals("Required", v.validate(""));
        assertEquals("Bad email", v.validate("nope"));
        assertNull(v.validate("a@b.co"));
    }

    @Test
    void validator_rangeRule() {
        FxValidator<Double> v = FxValidator.<Double>of(FxValidator.range(0.0, 100.0, "Out of range"));
        assertNull(v.validate(50.0));
        assertEquals("Out of range", v.validate(-1.0));
    }

    @Test
    void formField_validateRunsValidator() {
        FxTextField tf = onFx(() -> FxTextField.builder().build());
        FxFormField<String> ff = onFx(() -> FxFormField.<String>builder()
                .label("Name")
                .control(tf)
                .validator(FxValidator.<String>of(FxValidator.required()))
                .valueSupplier(tf::getText)
                .required(true)
                .build());
        assertFalse(ff.validate());
        assertEquals("Required", ff.getErrorText());

        onFx(() -> { tf.setText("Med"); return null; });
        assertTrue(ff.validate());
        assertEquals("", ff.getErrorText());
    }

    @Test
    void errorSummary_hidesWhenEmpty() {
        FxErrorSummary s = onFx(() -> FxErrorSummary.builder().build());
        assertFalse(s.isVisible());
        onFx(() -> { s.getErrors().setAll("a", "b"); return null; });
        assertTrue(s.isVisible());
        assertEquals(2, s.getListBox().getChildren().size());
    }

    @Test
    void form_validateAggregatesErrors() {
        FxTextField email = onFx(() -> new FxTextField(""));
        FxTextField password = onFx(() -> new FxTextField(""));
        FxFormField<String> emailField = onFx(() -> FxFormField.<String>builder()
                .label("Email")
                .control(email)
                .validator(FxValidator.<String>of(FxValidator.required(), FxValidator.email("Bad email")))
                .valueSupplier(email::getText)
                .required(true)
                .build());
        FxFormField<String> pwField = onFx(() -> FxFormField.<String>builder()
                .label("Password")
                .control(password)
                .validator(FxValidator.<String>of(FxValidator.required(), FxValidator.minLength(8, "Too short")))
                .valueSupplier(password::getText)
                .required(true)
                .build());
        FxForm form = onFx(() -> FxForm.builder().fields(emailField, pwField).showSummary(true).build());

        assertFalse(form.validate());
        assertEquals(2, form.getErrorSummary().getErrors().size());
        assertTrue(form.getErrorSummary().isVisible());

        onFx(() -> { email.setText("a@b.co"); password.setText("verystrong"); return null; });
        assertTrue(form.validate());
        assertEquals(0, form.getErrorSummary().getErrors().size());
    }

    @Test
    void form_addExtraNodeAppended() {
        FxForm f = onFx(() -> FxForm.builder().build());
        onFx(() -> { f.addExtra(new FxLabel("extra")); return null; });
        assertEquals(1, f.getFieldsBox().getChildren().size());
    }
}
