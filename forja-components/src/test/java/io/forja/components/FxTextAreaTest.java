package io.forja.components;

import io.forja.components.inputs.fxTextArea.FxTextArea;
import io.forja.components.inputs.InputVariant;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxTextAreaTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxTextArea area = onFx(() -> FxTextArea.builder().build());

        assertEquals("", area.getText());
        assertEquals("", area.getPromptText());
        assertEquals(InputVariant.DEFAULT, area.getVariant());
        assertEquals(4, area.getRows());
        assertEquals(12, area.getMaxRows());
        assertFalse(area.isAutoResize());
        assertEquals("", area.getHelperText());
        assertEquals("", area.getErrorText());
        assertFalse(area.isShowingError());
        assertFalse(area.getHelperLabel().isVisible());
        assertTrue(area.getStyleClass().contains("forja-text-area"));
        assertNotNull(area.getTextArea());
        assertTrue(area.getTextArea().isWrapText());
        assertEquals(4, area.getTextArea().getPrefRowCount());
    }

    @Test
    void textAndPromptTextDelegateToInnerArea() {
        FxTextArea area = onFx(() -> FxTextArea.builder().build());

        onFx(() -> { area.setText("hello\nworld"); return null; });
        assertEquals("hello\nworld", area.getText());
        assertEquals("hello\nworld", area.getTextArea().getText());

        onFx(() -> { area.setPromptText("prompt"); return null; });
        assertEquals("prompt", area.getPromptText());
        assertEquals("prompt", area.getTextArea().getPromptText());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxTextArea area = onFx(() -> FxTextArea.builder().variant(InputVariant.SUCCESS).build());

        assertHasPseudoClass(area, "success");
        assertLacksPseudoClass(area, "default");

        onFx(() -> { area.setVariant(InputVariant.ERROR); return null; });

        assertHasPseudoClass(area, "error");
        assertLacksPseudoClass(area, "success");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (InputVariant variant : InputVariant.values()) {
            FxTextArea area = onFx(() -> FxTextArea.builder().variant(variant).build());
            assertHasPseudoClass(area, variant.name().toLowerCase());
        }
    }

    @Test
    void helperTextTogglesHelperLabelVisibility() {
        FxTextArea area = onFx(() -> FxTextArea.builder().build());
        assertFalse(area.getHelperLabel().isVisible());

        onFx(() -> { area.setHelperText("Optional"); return null; });
        assertTrue(area.getHelperLabel().isVisible());
        assertEquals("Optional", area.getHelperLabel().getText());

        onFx(() -> { area.setHelperText(""); return null; });
        assertFalse(area.getHelperLabel().isVisible());
    }

    @Test
    void errorTextAutoFlipsVariant() {
        FxTextArea area = onFx(() -> FxTextArea.builder().build());

        onFx(() -> { area.setErrorText("Required"); return null; });

        assertEquals(InputVariant.ERROR, area.getVariant());
        assertTrue(area.isShowingError());
        assertEquals("Required", area.getHelperLabel().getText());
    }

    @Test
    void errorTakesPrecedenceOverHelperText() {
        FxTextArea area = onFx(() -> FxTextArea.builder()
                .helperText("Helper")
                .errorText("Boom")
                .build());

        assertTrue(area.isShowingError());
        assertEquals("Boom", area.getHelperLabel().getText());
        assertEquals(InputVariant.ERROR, area.getVariant());
    }

    @Test
    void clearingErrorRevertsToHelperWithoutChangingVariant() {
        FxTextArea area = onFx(() -> FxTextArea.builder()
                .helperText("Helper")
                .errorText("Boom")
                .build());

        onFx(() -> { area.setErrorText(""); return null; });

        assertFalse(area.isShowingError());
        assertEquals("Helper", area.getHelperLabel().getText());
        assertEquals(InputVariant.ERROR, area.getVariant());
    }

    @Test
    void autoResizeUpdatesPrefRowCountToLineCount() {
        FxTextArea area = onFx(() -> FxTextArea.builder()
                .rows(2)
                .maxRows(8)
                .autoResize(true)
                .build());

        assertEquals(2, area.getTextArea().getPrefRowCount(), "Starts at min rows for empty text");

        onFx(() -> { area.setText("one\ntwo\nthree\nfour"); return null; });
        assertEquals(4, area.getTextArea().getPrefRowCount(), "Grows to line count");

        onFx(() -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 20; i++) sb.append("L").append(i).append("\n");
            area.setText(sb.toString());
            return null;
        });
        assertEquals(8, area.getTextArea().getPrefRowCount(), "Capped at maxRows");
    }

    @Test
    void autoResizeDisabledKeepsPrefRowCount() {
        FxTextArea area = onFx(() -> FxTextArea.builder()
                .rows(4)
                .autoResize(false)
                .build());

        onFx(() -> { area.setText("a\nb\nc\nd\ne\nf\ng\nh\ni\nj\nk"); return null; });
        assertEquals(4, area.getTextArea().getPrefRowCount(), "Should not auto-grow when disabled");
    }

    @Test
    void enablingAutoResizeForcesWrapText() {
        FxTextArea area = onFx(() -> FxTextArea.builder().wrapText(false).build());

        onFx(() -> { area.setAutoResize(true); return null; });

        assertTrue(area.getTextArea().isWrapText());
    }
}
