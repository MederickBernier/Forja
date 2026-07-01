package io.forja.components;

import io.forja.components.inputs.fxCodeEditor.FxCodeEditor;
import io.forja.components.inputs.fxJsonEditor.FxJsonEditor;
import io.forja.components.inputs.fxMarkdownEditor.FxMarkdownEditor;
import io.forja.components.inputs.fxRichTextEditor.FxRichTextEditor;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseKSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void codeEditor_textBidiSyncsWithCodeArea() {
        FxCodeEditor ed = onFx(() -> FxCodeEditor.builder().text("int x = 1;").build());
        assertEquals("int x = 1;", ed.getText());
        assertEquals("int x = 1;", ed.getCodeArea().getText());
        onFx(() -> { ed.setText("hello"); return null; });
        assertEquals("hello", ed.getCodeArea().getText());
    }

    @Test
    void codeEditor_lineNumberFactoryTogglable() {
        FxCodeEditor ed = onFx(() -> FxCodeEditor.builder().showLineNumbers(false).build());
        onFx(() -> { ed.setShowLineNumbers(true); return null; });
        assertNotNull(ed.getCodeArea().getParagraphGraphicFactory());
    }

    @Test
    void richTextEditor_boldButtonAppliesStyle() {
        FxRichTextEditor ed = onFx(() -> FxRichTextEditor.builder().text("hello world").build());
        onFx(() -> { ed.getTextArea().selectRange(0, 5); return null; });
        onFx(() -> { ed.getBoldButton().fire(); return null; });
        String style = ed.getTextArea().getStyleOfChar(0);
        assertTrue(style != null && style.contains("bold"));
    }

    @Test
    void markdownEditor_previewRebuildsOnTextChange() {
        FxMarkdownEditor md = onFx(() -> FxMarkdownEditor.builder()
                .text("# Heading\n\nBody paragraph\n\n- bullet a\n- bullet b")
                .build());
        assertTrue(md.getPreviewBox().getChildren().size() >= 3, "heading + body + bullets");
    }

    @Test
    void markdownEditor_updatesPreviewWhenTextChanges() {
        FxMarkdownEditor md = onFx(() -> FxMarkdownEditor.builder().build());
        onFx(() -> { md.setText("## Nine"); return null; });
        assertEquals(1, md.getPreviewBox().getChildren().size());
    }

    @Test
    void jsonEditor_setsAndReadsText() {
        FxJsonEditor ed = onFx(() -> FxJsonEditor.builder().text("{\"a\":1}").build());
        assertEquals("{\"a\":1}", ed.getText());
        assertEquals("{\"a\":1}", ed.getCodeEditor().getText());
    }
}
