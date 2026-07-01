package io.forja.components;

import io.forja.components.fileAndMediaInput.fxFileChooser.FxFileChooser;
import io.forja.components.fileAndMediaInput.fxFileDropzone.FxFileDropzone;
import io.forja.components.fileAndMediaInput.fxImagePicker.FxImagePicker;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseESmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void fileChooser_defaultsLabelToNoFile() {
        FxFileChooser fc = onFx(() -> FxFileChooser.builder().buttonText("Pick").build());
        assertEquals("Pick", fc.getButton().getText());
        assertEquals("No file selected", fc.getLabel().getText());
        assertTrue(fc.getStyleClass().contains("forja-file-chooser"));
    }

    @Test
    void fileChooser_singleFileLabel() {
        FxFileChooser fc = onFx(() -> FxFileChooser.builder().build());
        onFx(() -> { fc.getFiles().setAll(Collections.singletonList(new File("hello.txt"))); return null; });
        assertEquals("hello.txt", fc.getLabel().getText());
    }

    @Test
    void fileChooser_multiFileLabel() {
        FxFileChooser fc = onFx(() -> FxFileChooser.builder().build());
        onFx(() -> { fc.getFiles().setAll(Arrays.asList(new File("a"), new File("b"), new File("c"))); return null; });
        assertEquals("3 files", fc.getLabel().getText());
    }

    @Test
    void dropzone_fireDropInvokesCallback() {
        AtomicReference<List<File>> got = new AtomicReference<>();
        FxFileDropzone dz = onFx(() -> FxFileDropzone.builder()
                .promptText("Drop here")
                .onDrop(got::set)
                .build());
        onFx(() -> { dz.fireDrop(Arrays.asList(new File("x"), new File("y"))); return null; });
        assertEquals(2, got.get().size());
        assertTrue(dz.getStyleClass().contains("forja-file-dropzone"));
    }

    @Test
    void dropzone_promptTextApplied() {
        FxFileDropzone dz = onFx(() -> FxFileDropzone.builder().promptText("Drop images").build());
        assertEquals("Drop images", dz.getPromptLabel().getText());
    }

    @Test
    void imagePicker_setsAndClearsValue() {
        FxImagePicker p = onFx(() -> FxImagePicker.builder().previewWidth(80).previewHeight(80).build());
        assertNull(p.getValue());
        File f = new File("nope.png");
        onFx(() -> { p.setValue(f); return null; });
        assertEquals(f, p.getValue());
        onFx(() -> { p.clear(); return null; });
        assertNull(p.getValue());
    }

    @Test
    void imagePicker_appliesButtonText() {
        FxImagePicker p = onFx(() -> FxImagePicker.builder().buttonText("Upload").clearButtonText("Nope").build());
        assertEquals("Upload", p.getChooseButton().getText());
        assertEquals("Nope", p.getClearButton().getText());
    }
}
