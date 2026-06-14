package io.forja.components;

import static io.forja.testsupport.ForjaTestSupport.*;
import io.forja.components.typography.fxLink.LinkVariant;
import io.forja.components.utilities.fxIcon.FxIcon;
import io.forja.components.typography.fxLink.FxLink;

import io.forja.components.typography.fxLink.FxLinkSkin;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxLinkTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxLink link = onFx(() -> FxLink.builder().build());

        assertEquals("", link.getText());
        assertEquals(LinkVariant.DEFAULT, link.getVariant());
        assertNull(link.getGraphic());
        assertTrue(link.getStyleClass().contains("forja-link"));
    }

    @Test
    void builderSetsAllProperties() {
        AtomicBoolean fired = new AtomicBoolean(false);
        EventHandler<ActionEvent> handler = e -> fired.set(true);

        FxLink link = onFx(() -> FxLink.builder()
                .text("Visit docs")
                .variant(LinkVariant.EXTERNAL)
                .onAction(handler)
                .build());

        assertEquals("Visit docs", link.getText());
        assertEquals(LinkVariant.EXTERNAL, link.getVariant());

        onFx(() -> {
            link.getOnAction().handle(new ActionEvent());
            return null;
        });
        assertTrue(fired.get());
    }

    @Test
    void constructorVariants() {
        FxLink empty = onFx(() -> new FxLink());
        FxLink withText = onFx(() -> new FxLink("Hi"));
        FxLink withVariant = onFx(() -> new FxLink("Hi", LinkVariant.MUTED));

        assertEquals("", empty.getText());
        assertEquals(LinkVariant.DEFAULT, empty.getVariant());
        assertEquals("Hi", withText.getText());
        assertEquals(LinkVariant.DEFAULT, withText.getVariant());
        assertEquals("Hi", withVariant.getText());
        assertEquals(LinkVariant.MUTED, withVariant.getVariant());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxLink link = onFx(() -> {
            FxLink l = FxLink.builder().variant(LinkVariant.MUTED).build();
            l.setSkin(new FxLinkSkin(l));
            return l;
        });

        assertHasPseudoClass(link, "muted");
        assertLacksPseudoClass(link, "default");
        assertLacksPseudoClass(link, "external");

        onFx(() -> {
            link.setVariant(LinkVariant.EXTERNAL);
            return null;
        });

        assertHasPseudoClass(link, "external");
        assertLacksPseudoClass(link, "muted");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (LinkVariant variant : LinkVariant.values()) {
            FxLink link = onFx(() -> {
                FxLink l = FxLink.builder().variant(variant).build();
                l.setSkin(new FxLinkSkin(l));
                return l;
            });
            assertHasPseudoClass(link, variant.name().toLowerCase());
        }
    }

    @Test
    void externalVariantAddsTrailingIcon() {
        FxLink link = onFx(() -> FxLink.builder()
                .text("Open")
                .variant(LinkVariant.EXTERNAL)
                .build());

        assertNotNull(link.getGraphic(), "EXTERNAL variant should add a graphic icon");
        assertTrue(link.getGraphic() instanceof FxIcon, "Graphic should be an FxIcon");
        assertEquals("fth-external-link", ((FxIcon) link.getGraphic()).getIconLiteral());
        assertEquals(ContentDisplay.RIGHT, link.getContentDisplay());
    }

    @Test
    void switchingAwayFromExternalClearsManagedIcon() {
        FxLink link = onFx(() -> FxLink.builder()
                .text("Open")
                .variant(LinkVariant.EXTERNAL)
                .build());

        assertNotNull(link.getGraphic());

        onFx(() -> {
            link.setVariant(LinkVariant.DEFAULT);
            return null;
        });

        assertNull(link.getGraphic(), "Managed icon should be cleared when leaving EXTERNAL");
    }

    @Test
    void userSuppliedGraphicSurvivesNonExternalVariantSwitch() {
        FxIcon customGraphic = onFx(() -> new FxIcon("fth-check"));
        FxLink link = onFx(() -> {
            FxLink l = FxLink.builder().text("Done").variant(LinkVariant.DEFAULT).build();
            l.setGraphic(customGraphic);
            return l;
        });

        assertEquals(customGraphic, link.getGraphic());

        onFx(() -> {
            link.setVariant(LinkVariant.MUTED);
            return null;
        });

        assertEquals(customGraphic, link.getGraphic(), "User-supplied graphic should not be cleared");
    }

    @Test
    void switchingToExternalReplacesUserGraphicWithManagedIcon() {
        FxIcon customGraphic = onFx(() -> new FxIcon("fth-check"));
        FxLink link = onFx(() -> {
            FxLink l = FxLink.builder().text("Done").variant(LinkVariant.DEFAULT).build();
            l.setGraphic(customGraphic);
            return l;
        });

        onFx(() -> {
            link.setVariant(LinkVariant.EXTERNAL);
            return null;
        });

        assertNotNull(link.getGraphic());
        assertTrue(link.getGraphic() instanceof FxIcon);
        assertEquals("fth-external-link", ((FxIcon) link.getGraphic()).getIconLiteral());
        assertFalse(link.getGraphic() == customGraphic, "EXTERNAL should swap to managed icon");
    }
}
