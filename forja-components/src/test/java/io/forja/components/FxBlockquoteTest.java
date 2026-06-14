package io.forja.components;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class FxBlockquoteTest {

    @Start
    void start(Stage stage) {
        // Empty: ApplicationExtension boots the JavaFX toolkit; no UI needed.
    }

    @Test
    void builderDefaults() {
        FxBlockquote blockquote = onFx(() -> FxBlockquote.builder().build());

        assertEquals("", blockquote.getQuote());
        assertNull(blockquote.getCite());
        assertTrue(blockquote.getStyleClass().contains("forja-blockquote"));
        assertFalse(blockquote.getCiteNode().isVisible(), "Cite node should be hidden when no cite set");
        assertFalse(blockquote.getCiteNode().isManaged(), "Cite node should not consume layout space when hidden");
    }

    @Test
    void builderSetsAllProperties() {
        FxBlockquote blockquote = onFx(() -> FxBlockquote.builder()
                .quote("Shape what already works.")
                .cite("— Forja motto")
                .maxWidth(640)
                .id("hero-quote")
                .styleClass("custom")
                .userData("payload")
                .build());

        assertEquals("Shape what already works.", blockquote.getQuote());
        assertEquals("— Forja motto", blockquote.getCite());
        assertEquals(640, blockquote.getMaxWidth());
        assertEquals(640, blockquote.getQuoteNode().getMaxWidth());
        assertEquals("hero-quote", blockquote.getId());
        assertTrue(blockquote.getStyleClass().contains("custom"));
        assertEquals("payload", blockquote.getUserData());
        assertTrue(blockquote.getCiteNode().isVisible());
        assertTrue(blockquote.getCiteNode().isManaged());
    }

    @Test
    void constructorVariants() {
        FxBlockquote empty = onFx(() -> new FxBlockquote());
        FxBlockquote withQuote = onFx(() -> new FxBlockquote("Hi"));
        FxBlockquote withCite = onFx(() -> new FxBlockquote("Hi", "— Anon"));

        assertEquals("", empty.getQuote());
        assertNull(empty.getCite());
        assertEquals("Hi", withQuote.getQuote());
        assertNull(withQuote.getCite());
        assertEquals("Hi", withCite.getQuote());
        assertEquals("— Anon", withCite.getCite());
    }

    @Test
    void quoteTextBindsToInnerNode() {
        FxBlockquote blockquote = onFx(() -> new FxBlockquote("Original"));

        assertEquals("Original", blockquote.getQuoteNode().getText());

        onFx(() -> { blockquote.setQuote("Updated"); return null; });

        assertEquals("Updated", blockquote.getQuoteNode().getText());
    }

    @Test
    void citeToggleShowsAndHidesCiteNode() {
        FxBlockquote blockquote = onFx(() -> new FxBlockquote("Quote"));

        assertFalse(blockquote.getCiteNode().isVisible());

        onFx(() -> { blockquote.setCite("— Source"); return null; });
        assertTrue(blockquote.getCiteNode().isVisible());
        assertTrue(blockquote.getCiteNode().isManaged());
        assertEquals("— Source", blockquote.getCiteNode().getText());

        onFx(() -> { blockquote.setCite(null); return null; });
        assertFalse(blockquote.getCiteNode().isVisible());
        assertFalse(blockquote.getCiteNode().isManaged());

        onFx(() -> { blockquote.setCite(""); return null; });
        assertFalse(blockquote.getCiteNode().isVisible(), "Empty cite should also hide the cite node");
    }

    @Test
    void nullQuoteCoercesToEmptyString() {
        FxBlockquote blockquote = onFx(() -> new FxBlockquote());

        onFx(() -> { blockquote.setQuote(null); return null; });

        assertEquals("", blockquote.getQuote());
        assertEquals("", blockquote.getQuoteNode().getText());
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
