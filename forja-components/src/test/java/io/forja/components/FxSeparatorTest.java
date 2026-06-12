package io.forja.components;

import io.forja.skin.FxSeparatorSkin;
import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
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
class FxSeparatorTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxSeparator separator = onFx(() -> FxSeparator.builder().build());

        assertEquals(Orientation.HORIZONTAL, separator.getOrientation());
        assertEquals(SeparatorVariant.DEFAULT, separator.getVariant());
        assertTrue(separator.getStyleClass().contains("forja-separator"));
    }

    @Test
    void builderSetsAllProperties() {
        FxSeparator separator = onFx(() -> FxSeparator.builder()
                .orientation(Orientation.VERTICAL)
                .variant(SeparatorVariant.STRONG)
                .build());

        assertEquals(Orientation.VERTICAL, separator.getOrientation());
        assertEquals(SeparatorVariant.STRONG, separator.getVariant());
    }

    @Test
    void constructorVariants() {
        FxSeparator empty = onFx(() -> new FxSeparator());
        FxSeparator withOrientation = onFx(() -> new FxSeparator(Orientation.VERTICAL));
        FxSeparator withVariant = onFx(() -> new FxSeparator(Orientation.HORIZONTAL, SeparatorVariant.HAIRLINE));

        assertEquals(Orientation.HORIZONTAL, empty.getOrientation());
        assertEquals(SeparatorVariant.DEFAULT, empty.getVariant());
        assertEquals(Orientation.VERTICAL, withOrientation.getOrientation());
        assertEquals(SeparatorVariant.DEFAULT, withOrientation.getVariant());
        assertEquals(Orientation.HORIZONTAL, withVariant.getOrientation());
        assertEquals(SeparatorVariant.HAIRLINE, withVariant.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxSeparator separator = onFx(() -> {
            FxSeparator s = FxSeparator.builder().variant(SeparatorVariant.HAIRLINE).build();
            s.setSkin(new FxSeparatorSkin(s));
            return s;
        });

        assertHasPseudoClass(separator, "hairline");
        assertLacksPseudoClass(separator, "default");
        assertLacksPseudoClass(separator, "strong");

        onFx(() -> {
            separator.setVariant(SeparatorVariant.STRONG);
            return null;
        });

        assertHasPseudoClass(separator, "strong");
        assertLacksPseudoClass(separator, "hairline");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (SeparatorVariant variant : SeparatorVariant.values()) {
            FxSeparator separator = onFx(() -> {
                FxSeparator s = FxSeparator.builder().variant(variant).build();
                s.setSkin(new FxSeparatorSkin(s));
                return s;
            });

            assertHasPseudoClass(separator, variant.name().toLowerCase());
        }
    }

    @Test
    void skinAttachesViaSceneRendering() {
        FxSeparator separator = onFx(() -> {
            FxSeparator s = FxSeparator.builder()
                    .orientation(Orientation.VERTICAL)
                    .variant(SeparatorVariant.STRONG)
                    .build();
            Scene scene = new Scene(new StackPane(s), 200, 100);
            s.applyCss();
            return s;
        });

        assertHasPseudoClass(separator, "strong");
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

    private static void assertHasPseudoClass(FxSeparator separator, String name) {
        assertTrue(
                separator.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Expected pseudo-class :" + name + " on " + separator
        );
    }

    private static void assertLacksPseudoClass(FxSeparator separator, String name) {
        assertFalse(
                separator.getPseudoClassStates().contains(PseudoClass.getPseudoClass(name)),
                "Did not expect pseudo-class :" + name + " on " + separator
        );
    }
}
