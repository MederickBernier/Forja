package io.forja.demo.categories;

import io.forja.components.dataDisplay.fxAvatar.AvatarSize;
import io.forja.components.dataDisplay.fxAvatar.FxAvatar;
import io.forja.components.dataDisplay.fxAvatarGroup.FxAvatarGroup;
import io.forja.components.dataDisplay.fxCarousel.FxCarousel;
import io.forja.components.dataDisplay.fxDataGrid.FxDataGrid;
import io.forja.components.dataDisplay.fxDescriptionList.FxDescriptionList;
import io.forja.components.dataDisplay.fxImage.FxImage;
import io.forja.components.dataDisplay.fxKanbanBoard.FxKanbanBoard;
import io.forja.components.dataDisplay.fxList.FxList;
import io.forja.components.dataDisplay.fxMasonry.FxMasonry;
import io.forja.components.dataDisplay.fxStat.FxStat;
import io.forja.components.dataDisplay.fxTable.FxTable;
import io.forja.components.dataDisplay.fxTimeline.FxTimeline;
import io.forja.components.dataDisplay.fxTree.FxTree;
import io.forja.components.dataDisplay.fxTreeTable.FxTreeTable;
import io.forja.components.dataDisplay.fxVirtualList.FxVirtualList;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.tokens.SemanticVariant;

import java.util.Arrays;
import java.util.function.Function;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class DataDisplayDemo implements CategoryDemo {

    @Override public String key() { return "data-display"; }
    @Override public String title() { return "Data Display"; }
    @Override public String icon() { return "fth-database"; }

    /** Tiny sample row type — records are Java 8 forbidden, so a plain class. */
    private static final class Person {
        final String name;
        final int age;
        final String role;
        Person(String name, int age, String role) { this.name = name; this.age = age; this.role = role; }
    }

    private static final Person[] PEOPLE = new Person[] {
            new Person("Ada Lovelace", 36, "Analyst"),
            new Person("Alan Turing", 41, "Engineer"),
            new Person("Grace Hopper", 52, "Admiral")
    };

    private static TableColumn<Person, String> col(String title, Function<Person, String> getter) {
        TableColumn<Person, String> c = new TableColumn<Person, String>(title);
        c.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<Person, String> d) {
                return new ReadOnlyStringWrapper(getter.apply(d.getValue()));
            }
        });
        return c;
    }

    private static TreeTableColumn<Person, String> treeCol(String title, Function<Person, String> getter) {
        TreeTableColumn<Person, String> c = new TreeTableColumn<Person, String>(title);
        c.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Person, String> d) {
                return new ReadOnlyStringWrapper(getter.apply(d.getValue().getValue()));
            }
        });
        return c;
    }

    @Override
    public Node build(Scene scene) {
        return Demo.category(title(),
                Demo.block("FxAvatar", "Circular avatar from image, initials, or icon with size variants.",
                        FxAvatar.builder().initials("AL").size(AvatarSize.COMPACT).build(),
                        FxAvatar.builder().initials("AT").size(AvatarSize.DEFAULT).build(),
                        FxAvatar.builder().initials("GH").size(AvatarSize.COMFORTABLE).build()),

                Demo.block("FxAvatarGroup", "Overlapping stack of avatars with an overflow count.",
                        FxAvatarGroup.builder().avatar("AL").avatar("AT").avatar("GH").avatar("MB")
                                .max(3).size(AvatarSize.DEFAULT).build()),

                Demo.block("FxCarousel", "Rotating slide deck with index and optional auto-advance.",
                        FxCarousel.builder().slides(
                                new FxLabel("Slide one"),
                                new FxLabel("Slide two"),
                                new FxLabel("Slide three")).index(0).build()),

                Demo.block("FxDataGrid", "Paginated table with a live search filter.",
                        FxDataGrid.<Person>builder()
                                .columns(col("Name", p -> p.name), col("Role", p -> p.role))
                                .items(PEOPLE)
                                .pageSize(2)
                                .filter((p, q) -> p.name.toLowerCase().contains(q.toLowerCase()))
                                .build()),

                Demo.block("FxDescriptionList", "Term/description pairs for key-value details.",
                        FxDescriptionList.builder()
                                .item("Name", "Ada Lovelace")
                                .item("Role", "Analyst")
                                .item("Status", "Active")
                                .build()),

                Demo.block("FxImage", "Image with fit sizing and a fallback icon on load failure.",
                        FxImage.builder().url("https://picsum.photos/160/120")
                                .fitWidth(160).preserveRatio(true).fallbackIcon("fth-image").build()),

                Demo.block("FxKanbanBoard", "Columns of draggable cards.",
                        FxKanbanBoard.builder()
                                .column("todo", "To Do")
                                .column("doing", "In Progress")
                                .column("done", "Done")
                                .card("todo", new FxLabel("Design tokens"))
                                .card("doing", new FxLabel("Build components"))
                                .card("done", new FxLabel("Write docs"))
                                .build()),

                Demo.block("FxList", "Selectable list of items.",
                        FxList.<String>builder()
                                .items("Inbox", "Drafts", "Sent", "Archive")
                                .build()),

                Demo.block("FxMasonry", "Multi-column layout that packs children by height.",
                        FxMasonry.builder().columns(3).gap(8)
                                .children(new FxLabel("One"), new FxLabel("Two"),
                                        new FxLabel("Three"), new FxLabel("Four"))
                                .build()),

                Demo.block("FxStat", "Single metric with label, value, and trend.",
                        FxStat.builder().label("Revenue").value("$12.4k")
                                .trend("+8% MoM", SemanticVariant.SUCCESS).build(),
                        FxStat.builder().label("Churn").value("2.1%")
                                .trend("-0.3%", FxStat.Trend.DOWN).build()),

                Demo.block("FxTable", "Columnar table bound to a row type.",
                        FxTable.<Person>builder()
                                .columns(col("Name", p -> p.name),
                                        col("Age", p -> String.valueOf(p.age)),
                                        col("Role", p -> p.role))
                                .items(PEOPLE)
                                .build()),

                Demo.block("FxTimeline", "Chronological entries with semantic accents.",
                        FxTimeline.builder()
                                .entry("Created", "Ticket opened", SemanticVariant.INFO)
                                .entry("In review", "Assigned to team", SemanticVariant.WARNING)
                                .entry("Resolved", "Shipped to prod", SemanticVariant.SUCCESS)
                                .build()),

                Demo.block("FxTree", "Hierarchical expandable tree.",
                        FxTree.<String>builder().root(buildStringTree()).showRoot(true).build()),

                Demo.block("FxTreeTable", "Tree of rows with columns.",
                        FxTreeTable.<Person>builder()
                                .root(buildPersonTree())
                                .columns(treeCol("Name", p -> p.name), treeCol("Role", p -> p.role))
                                .showRoot(true)
                                .build()),

                Demo.block("FxVirtualList", "Efficiently renders large lists via cell recycling.",
                        FxVirtualList.<String>builder()
                                .items(Arrays.asList("Row 1", "Row 2", "Row 3", "Row 4", "Row 5"))
                                .itemHeight(28)
                                .cellFactory(s -> new FxLabel(s))
                                .build()));
    }

    private static TreeItem<String> buildStringTree() {
        TreeItem<String> root = new TreeItem<String>("Team");
        root.setExpanded(true);
        root.getChildren().addAll(
                new TreeItem<String>("Ada Lovelace"),
                new TreeItem<String>("Alan Turing"),
                new TreeItem<String>("Grace Hopper"));
        return root;
    }

    private static TreeItem<Person> buildPersonTree() {
        TreeItem<Person> root = new TreeItem<Person>(new Person("Everyone", 0, "Root"));
        root.setExpanded(true);
        for (Person p : PEOPLE) {
            root.getChildren().add(new TreeItem<Person>(p));
        }
        return root;
    }
}
