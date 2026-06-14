package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.typography.fxLabel.FxLabel;

import io.forja.components.typography.fxLabel.FxLabelSkin;
import javafx.css.PseudoClass;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxLabelTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxLabel label = onFx(() -> FxLabel.builder().build());

        assertEquals("", label.getText());
        assertEquals(LabelVariant.BODY, label.getVariant());
        assertFalse(label.isMuted());
        assertTrue(label.getStyleClass().contains("forja-label"));
    }

    @Test
    void builderSetsAllProperties() {
        FxLabel label = onFx(() -> FxLabel.builder()
                .text("Optional")
                .variant(LabelVariant.SMALL)
                .muted(true)
                .build());

        assertEquals("Optional", label.getText());
        assertEquals(LabelVariant.SMALL, label.getVariant());
        assertTrue(label.isMuted());
    }

    @Test
    void constructorVariants() {
        FxLabel empty = onFx(() -> new FxLabel());
        FxLabel withText = onFx(() -> new FxLabel("Hi"));
        FxLabel withVariant = onFx(() -> new FxLabel("Hi", LabelVariant.HEADING));

        assertEquals("", empty.getText());
        assertEquals(LabelVariant.BODY, empty.getVariant());
        assertEquals("Hi", withText.getText());
        assertEquals(LabelVariant.BODY, withText.getVariant());
        assertEquals("Hi", withVariant.getText());
        assertEquals(LabelVariant.HEADING, withVariant.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxLabel label = onFx(() -> {
            FxLabel l = FxLabel.builder().variant(LabelVariant.HEADING).build();
            l.setSkin(new FxLabelSkin(l));
            return l;
        });

        assertHasPseudoClass(label, "heading");
        assertLacksPseudoClass(label, "body");
        assertLacksPseudoClass(label, "display");
        assertLacksPseudoClass(label, "small");
        assertLacksPseudoClass(label, "mono");

        onFx(() -> {
            label.setVariant(LabelVariant.MONO);
            return null;
        });

        assertHasPseudoClass(label, "mono");
        assertLacksPseudoClass(label, "heading");
    }

    @Test
    void mutedPseudoClassToggles() {
        FxLabel label = onFx(() -> {
            FxLabel l = FxLabel.builder().build();
            l.setSkin(new FxLabelSkin(l));
            return l;
        });

        assertLacksPseudoClass(label, "muted");

        onFx(() -> {
            label.setMuted(true);
            return null;
        });

        assertHasPseudoClass(label, "muted");

        onFx(() -> {
            label.setMuted(false);
            return null;
        });

        assertLacksPseudoClass(label, "muted");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (LabelVariant variant : LabelVariant.values()) {
            FxLabel label = onFx(() -> {
                FxLabel l = FxLabel.builder().variant(variant).build();
                l.setSkin(new FxLabelSkin(l));
                return l;
            });

            assertHasPseudoClass(label, variant.name().toLowerCase());
        }
    }

    @Test
    void skinAttachesViaSceneRendering() {
        FxLabel label = onFx(() -> {
            FxLabel l = FxLabel.builder().variant(LabelVariant.SUBHEADING).muted(true).build();
            Scene scene = new Scene(new StackPane(l), 200, 100);
            l.applyCss();
            return l;
        });

        assertHasPseudoClass(label, "subheading");
        assertHasPseudoClass(label, "muted");
    }
}
