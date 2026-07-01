package io.forja.demo.categories;

import io.forja.components.typography.fxBlockquote.FxBlockquote;
import io.forja.components.typography.fxBulletList.FxBulletList;
import io.forja.components.typography.fxCode.FxCode;
import io.forja.components.typography.fxKbd.FxKbd;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.typography.fxLink.FxLink;
import io.forja.components.typography.fxLink.LinkVariant;
import io.forja.components.typography.fxText.FxText;
import io.forja.components.typography.fxText.TextVariant;
import javafx.scene.Node;
import javafx.scene.Scene;

public class TypographyDemo implements CategoryDemo {

    @Override public String key() { return "typography"; }
    @Override public String title() { return "Typography"; }
    @Override public String icon() { return "fth-type"; }

    @Override
    public Node build(Scene scene) {
        return Demo.category(title(),
                Demo.block("FxText", "Paragraph text with a body/lead variant and muted styling.",
                        FxText.builder().text("Body text for the bulk of your content.").variant(TextVariant.BODY).build(),
                        FxText.builder().text("Lead text stands out as an intro.").variant(TextVariant.LEAD).build(),
                        FxText.builder().text("Muted body text.").variant(TextVariant.BODY).muted(true).build()),

                Demo.block("FxLabel", "Short label with a typographic scale from display to small.",
                        FxLabel.builder().text("Display").variant(LabelVariant.DISPLAY).build(),
                        FxLabel.builder().text("Heading").variant(LabelVariant.HEADING).build(),
                        FxLabel.builder().text("Subheading").variant(LabelVariant.SUBHEADING).build(),
                        FxLabel.builder().text("Small muted").variant(LabelVariant.SMALL).muted(true).build()),

                Demo.block("FxLink", "Clickable text link with default, muted, and external variants.",
                        FxLink.builder().text("Default link").variant(LinkVariant.DEFAULT).build(),
                        FxLink.builder().text("Muted link").variant(LinkVariant.MUTED).build(),
                        FxLink.builder().text("External link").variant(LinkVariant.EXTERNAL).build()),

                Demo.block("FxBlockquote", "Pull quote with an optional citation.",
                        FxBlockquote.builder().quote("Simplicity is the ultimate sophistication.").cite("Leonardo da Vinci").build()),

                Demo.block("FxBulletList", "Ordered or unordered list of items.",
                        FxBulletList.builder().kind(FxBulletList.Kind.UNORDERED).items("Read", "Write", "Verify").build(),
                        FxBulletList.builder().kind(FxBulletList.Kind.ORDERED).items("First", "Second", "Third").build()),

                Demo.block("FxCode", "Inline monospace snippet for code fragments.",
                        FxCode.builder().text("System.out.println(\"hi\");").build()),

                Demo.block("FxKbd", "Keyboard key indicator for shortcuts.",
                        FxKbd.builder().text("Ctrl").build(),
                        FxKbd.builder().text("Shift").build(),
                        FxKbd.builder().text("K").build()));
    }
}
