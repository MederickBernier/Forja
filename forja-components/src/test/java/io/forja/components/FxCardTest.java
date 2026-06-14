package io.forja.components;

import io.forja.components.layout.fxCard.CardVariant;
import io.forja.components.layout.fxCard.FxCard;
import io.forja.tokens.SpacingSize;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxCardTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxCard card = onFx(() -> FxCard.builder().build());

        assertNull(card.getHeader());
        assertNull(card.getBody());
        assertNull(card.getFooter());
        assertEquals(CardVariant.DEFAULT, card.getVariant());
        assertEquals(SpacingSize.MD, card.getGap());
        assertEquals(12.0, card.getSpacing());
        assertEquals(0, card.getChildren().size());
        assertTrue(card.getStyleClass().contains("forja-card"));
    }

    @Test
    void builderSetsAllProperties() {
        Label h = new Label("h");
        Label b = new Label("b");
        Label f = new Label("f");
        FxCard card = onFx(() -> FxCard.builder()
                .header(h)
                .body(b)
                .footer(f)
                .variant(CardVariant.OUTLINED)
                .gap(SpacingSize.LG)
                .id("card-id")
                .styleClass("custom")
                .userData("payload")
                .build());

        assertEquals(h, card.getHeader());
        assertEquals(b, card.getBody());
        assertEquals(f, card.getFooter());
        assertEquals(CardVariant.OUTLINED, card.getVariant());
        assertEquals(SpacingSize.LG, card.getGap());
        assertEquals(16.0, card.getSpacing());
        assertEquals(3, card.getChildren().size());
        assertEquals(h, card.getChildren().get(0));
        assertEquals(b, card.getChildren().get(1));
        assertEquals(f, card.getChildren().get(2));
        assertEquals("card-id", card.getId());
        assertTrue(card.getStyleClass().contains("custom"));
        assertEquals("payload", card.getUserData());
    }

    @Test
    void constructorVariants() {
        FxCard empty = onFx(() -> new FxCard());
        FxCard withVariant = onFx(() -> new FxCard(CardVariant.ELEVATED));
        Label body = new Label("body");
        FxCard withBody = onFx(() -> new FxCard(body));

        assertEquals(CardVariant.DEFAULT, empty.getVariant());
        assertEquals(CardVariant.ELEVATED, withVariant.getVariant());
        assertEquals(body, withBody.getBody());
        assertEquals(1, withBody.getChildren().size());
    }

    @Test
    void variantPseudoClassUpdates() {
        FxCard card = onFx(() -> FxCard.builder().variant(CardVariant.OUTLINED).build());

        assertHasPseudoClass(card, "outlined");
        assertLacksPseudoClass(card, "default");
        assertLacksPseudoClass(card, "elevated");

        onFx(() -> { card.setVariant(CardVariant.ELEVATED); return null; });

        assertHasPseudoClass(card, "elevated");
        assertLacksPseudoClass(card, "outlined");
    }

    @Test
    void allVariantsHaveCorrespondingPseudoClass() {
        for (CardVariant variant : CardVariant.values()) {
            FxCard card = onFx(() -> FxCard.builder().variant(variant).build());
            assertHasPseudoClass(card, variant.name().toLowerCase());
        }
    }

    @Test
    void slotMutationRebuildsChildren() {
        FxCard card = onFx(() -> FxCard.builder().build());
        assertEquals(0, card.getChildren().size());

        Label b = new Label("b");
        onFx(() -> { card.setBody(b); return null; });
        assertEquals(1, card.getChildren().size());
        assertEquals(b, card.getChildren().get(0));

        Label h = new Label("h");
        onFx(() -> { card.setHeader(h); return null; });
        assertEquals(2, card.getChildren().size());
        assertEquals(h, card.getChildren().get(0));
        assertEquals(b, card.getChildren().get(1));

        Label f = new Label("f");
        onFx(() -> { card.setFooter(f); return null; });
        assertEquals(3, card.getChildren().size());
        assertEquals(f, card.getChildren().get(2));

        onFx(() -> { card.setHeader(null); return null; });
        assertEquals(2, card.getChildren().size());
        assertEquals(b, card.getChildren().get(0));
        assertEquals(f, card.getChildren().get(1));
    }

    @Test
    void onlyFooterIsAllowed() {
        Label f = new Label("f");
        FxCard card = onFx(() -> FxCard.builder().footer(f).build());

        assertEquals(1, card.getChildren().size());
        assertEquals(f, card.getChildren().get(0));
    }

    @Test
    void gapTokenChanges() {
        FxCard card = onFx(() -> FxCard.builder().build());
        assertEquals(12.0, card.getSpacing());

        onFx(() -> { card.setGap(SpacingSize.XL); return null; });
        assertEquals(24.0, card.getSpacing());

        onFx(() -> { card.setGap(SpacingSize.NONE); return null; });
        assertEquals(0.0, card.getSpacing());
    }
}
