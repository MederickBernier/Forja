package io.forja.components;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxSectionTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxSection section = onFx(() -> FxSection.builder().build());

        assertEquals("", section.getTitle());
        assertFalse(section.isSeparator());
        assertEquals(SpacingSize.SM, section.getGap());
        assertEquals(8.0, section.getSpacing());
        assertEquals(0, section.getChildren().size(), "Empty section with no title or separator should render no children");
        assertTrue(section.getStyleClass().contains("forja-section"));
        assertNotNull(section.getTitleLabel());
        assertNotNull(section.getTopSeparator());
    }

    @Test
    void titleOnlyRendersLabelChild() {
        FxSection section = onFx(() -> FxSection.builder().title("Buttons").build());

        assertEquals(1, section.getChildren().size());
        assertEquals(section.getTitleLabel(), section.getChildren().get(0));
        assertEquals("Buttons", section.getTitleLabel().getText());
        assertEquals(LabelVariant.SUBHEADING, section.getTitleLabel().getVariant());
    }

    @Test
    void separatorTrueAddsLeadingSeparator() {
        FxSection section = onFx(() -> FxSection.builder()
                .title("Buttons")
                .separator(true)
                .build());

        assertEquals(2, section.getChildren().size());
        assertEquals(section.getTopSeparator(), section.getChildren().get(0));
        assertEquals(section.getTitleLabel(), section.getChildren().get(1));
    }

    @Test
    void contentNodesAppendAfterTitle() {
        Label a = new Label("a");
        Label b = new Label("b");
        FxSection section = onFx(() -> FxSection.builder()
                .title("Section")
                .content(a, b)
                .build());

        assertEquals(3, section.getChildren().size());
        assertEquals(section.getTitleLabel(), section.getChildren().get(0));
        assertEquals(a, section.getChildren().get(1));
        assertEquals(b, section.getChildren().get(2));
    }

    @Test
    void separatorAndContentCombine() {
        Label a = new Label("a");
        FxSection section = onFx(() -> FxSection.builder()
                .title("Section")
                .separator(true)
                .content(a)
                .build());

        assertEquals(3, section.getChildren().size());
        assertEquals(section.getTopSeparator(), section.getChildren().get(0));
        assertEquals(section.getTitleLabel(), section.getChildren().get(1));
        assertEquals(a, section.getChildren().get(2));
    }

    @Test
    void emptyTitleHidesLabel() {
        Label a = new Label("a");
        FxSection section = onFx(() -> FxSection.builder()
                .title("")
                .separator(true)
                .content(a)
                .build());

        assertEquals(2, section.getChildren().size());
        assertEquals(section.getTopSeparator(), section.getChildren().get(0));
        assertEquals(a, section.getChildren().get(1));
    }

    @Test
    void mutatingPropertiesRebuilds() {
        FxSection section = onFx(() -> FxSection.builder().title("X").build());
        assertEquals(1, section.getChildren().size());

        onFx(() -> { section.setSeparator(true); return null; });
        assertEquals(2, section.getChildren().size());

        Label child = new Label("c");
        onFx(() -> { section.getContent().add(child); return null; });
        assertEquals(3, section.getChildren().size());

        onFx(() -> { section.setTitle(""); return null; });
        assertEquals(2, section.getChildren().size(), "Removing the title should drop the label child");
    }

    @Test
    void allGapTokensMapToPixels() {
        for (SpacingSize size : SpacingSize.values()) {
            FxSection section = onFx(() -> FxSection.builder().gap(size).build());
            assertEquals(size.pixels(), section.getSpacing(),
                    "gap " + size + " should map to its pixel value");
        }
    }

    @Test
    void constructorVariants() {
        FxSection empty = onFx(() -> new FxSection());
        FxSection withTitle = onFx(() -> new FxSection("Hi"));
        Label a = new Label("a");
        FxSection withContent = onFx(() -> new FxSection("Hi", a));

        assertEquals("", empty.getTitle());
        assertEquals("Hi", withTitle.getTitle());
        assertEquals("Hi", withContent.getTitle());
        assertEquals(1, withContent.getContent().size());
        assertEquals(a, withContent.getContent().get(0));
    }

    private static <T> T onFx(java.util.concurrent.Callable<T> body) {
        try {
            T result = WaitForAsyncUtils.asyncFx(body).get();
            WaitForAsyncUtils.waitForFxEvents();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
