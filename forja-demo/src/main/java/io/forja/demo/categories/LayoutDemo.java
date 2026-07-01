package io.forja.demo.categories;

import io.forja.components.layout.fxAccordion.FxAccordion;
import io.forja.components.layout.fxAspectRatio.FxAspectRatio;
import io.forja.components.layout.fxCard.CardVariant;
import io.forja.components.layout.fxCard.FxCard;
import io.forja.components.layout.fxCollapse.FxCollapse;
import io.forja.components.layout.fxContainer.ContainerWidth;
import io.forja.components.layout.fxContainer.FxContainer;
import io.forja.components.layout.fxFlex.FxFlex;
import io.forja.components.layout.fxGrid.FxGrid;
import io.forja.components.layout.fxResizablePane.FxResizablePane;
import io.forja.components.layout.fxResponsive.FxResponsive;
import io.forja.components.layout.fxRow.FxRow;
import io.forja.components.layout.fxScrollArea.FxScrollArea;
import io.forja.components.layout.fxSection.FxSection;
import io.forja.components.layout.fxSeparator.FxSeparator;
import io.forja.components.layout.fxSeparator.SeparatorVariant;
import io.forja.components.layout.fxSpacer.FxSpacer;
import io.forja.components.layout.fxSplitView.FxSplitView;
import io.forja.components.layout.fxStack.FxStack;
import io.forja.components.layout.fxStickyHeader.FxStickyHeader;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.tokens.SpacingSize;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

public class LayoutDemo implements CategoryDemo {

    @Override public String key() { return "layout"; }
    @Override public String title() { return "Layout"; }
    @Override public String icon() { return "fth-layout"; }

    /** A small coloured box so empty layout containers are actually visible. */
    private static Region swatch(String color, double w, double h) {
        Region r = new Region();
        r.setMinSize(w, h);
        r.setPrefSize(w, h);
        r.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 6;");
        return r;
    }

    @Override
    public Node build(Scene scene) {
        return Demo.category(title(),
                Demo.block("FxAccordion", "Stacked collapsible sections with optional single-open behavior.",
                        FxAccordion.builder().singleOpen(true).sections(
                                FxCollapse.builder().title("Profile").content(new FxLabel("Profile settings")).expanded(true).build(),
                                FxCollapse.builder().title("Security").content(new FxLabel("Security settings")).build(),
                                FxCollapse.builder().title("Billing").content(new FxLabel("Billing settings")).build()).build()),

                Demo.block("FxAspectRatio", "Locks a child to a fixed width:height ratio.",
                        FxAspectRatio.builder().ratio(16.0 / 9.0).child(swatch("#6366f1", 160, 90)).build()),

                Demo.block("FxCard", "Container with header, body, footer and a visual variant.",
                        FxCard.builder().variant(CardVariant.DEFAULT).gap(SpacingSize.SM)
                                .header(new FxLabel("Default")).body(new FxLabel("Card body content"))
                                .footer(new FxLabel("Footer")).build(),
                        FxCard.builder().variant(CardVariant.OUTLINED)
                                .header(new FxLabel("Outlined")).body(new FxLabel("Card body content")).build(),
                        FxCard.builder().variant(CardVariant.ELEVATED)
                                .header(new FxLabel("Elevated")).body(new FxLabel("Card body content")).build()),

                Demo.block("FxCollapse", "Single collapsible panel with a title header.",
                        FxCollapse.builder().title("Show details").content(new FxLabel("Hidden details")).build(),
                        FxCollapse.builder().title("Expanded").content(new FxLabel("Visible details")).expanded(true).build()),

                Demo.block("FxContainer", "Centered content clamped to a max width.",
                        FxContainer.builder().width(ContainerWidth.SM).padding(SpacingSize.MD).alignment(Pos.CENTER)
                                .children(new FxLabel("Small container"), swatch("#22c55e", 120, 24)).build()),

                Demo.block("FxFlex", "Wrapping flow of children along one axis.",
                        FxFlex.builder().orientation(Orientation.HORIZONTAL).gap(8).alignment(Pos.TOP_LEFT)
                                .children(swatch("#f97316", 40, 40), swatch("#f97316", 40, 40), swatch("#f97316", 40, 40)).build()),

                Demo.block("FxGrid", "Places children at explicit column/row coordinates.",
                        FxGrid.builder().hgap(8).vgap(8)
                                .add(swatch("#3b82f6", 40, 40), 0, 0)
                                .add(swatch("#3b82f6", 40, 40), 1, 0)
                                .add(swatch("#3b82f6", 88, 40), 0, 1, 2, 1).build()),

                Demo.block("FxResizablePane", "Child pane the user can drag-resize from one side.",
                        FxResizablePane.builder().side(Side.RIGHT).extent(160).minExtent(80).maxExtent(240)
                                .child(swatch("#a855f7", 160, 80)).build()),

                Demo.block("FxResponsive", "Swaps the shown node based on available width.",
                        FxResponsive.builder()
                                .at("narrow", 0, new FxLabel("Narrow layout"))
                                .at("wide", 600, new FxLabel("Wide layout")).build()),

                Demo.block("FxRow", "Horizontal row with token-based gap.",
                        FxRow.builder().gap(SpacingSize.MD).alignment(Pos.CENTER_LEFT)
                                .children(swatch("#ef4444", 40, 40), swatch("#ef4444", 40, 40), swatch("#ef4444", 40, 40)).build()),

                Demo.block("FxScrollArea", "Scrollable viewport around oversized content.",
                        FxScrollArea.builder().fitToWidth(true)
                                .content(Demo.col(new FxLabel("Line 1"), new FxLabel("Line 2"), swatch("#0ea5e9", 200, 300))).build()),

                Demo.block("FxSection", "Titled content group with optional divider.",
                        FxSection.builder().title("Settings").separator(true).gap(SpacingSize.SM)
                                .content(new FxLabel("Item one"), new FxLabel("Item two")).build()),

                Demo.block("FxSeparator", "Thin rule between content, in three weights.",
                        Demo.col(
                                FxSeparator.builder().orientation(Orientation.HORIZONTAL).variant(SeparatorVariant.HAIRLINE).build(),
                                FxSeparator.builder().orientation(Orientation.HORIZONTAL).variant(SeparatorVariant.DEFAULT).build(),
                                FxSeparator.builder().orientation(Orientation.HORIZONTAL).variant(SeparatorVariant.STRONG).build())),

                Demo.block("FxSpacer", "Fixed-size gap between siblings.",
                        Demo.row(swatch("#64748b", 30, 30),
                                FxSpacer.builder().size(40, Orientation.HORIZONTAL).build(),
                                swatch("#64748b", 30, 30))),

                Demo.block("FxSplitView", "Draggable divider between two panes.",
                        FxSplitView.builder().orientation(Orientation.HORIZONTAL).dividerPositions(0.4)
                                .items(swatch("#14b8a6", 120, 100), swatch("#f43f5e", 120, 100)).build()),

                Demo.block("FxStack", "Vertical stack with token-based gap.",
                        FxStack.builder().gap(SpacingSize.SM).alignment(Pos.CENTER_LEFT)
                                .children(swatch("#8b5cf6", 120, 24), swatch("#8b5cf6", 120, 24), swatch("#8b5cf6", 120, 24)).build()),

                Demo.block("FxStickyHeader", "Header that pins above scrolling body content.",
                        FxStickyHeader.builder().header(new FxLabel("Sticky header"))
                                .body(Demo.col(new FxLabel("Body line 1"), new FxLabel("Body line 2"), swatch("#facc15", 200, 200))).build()));
    }
}
