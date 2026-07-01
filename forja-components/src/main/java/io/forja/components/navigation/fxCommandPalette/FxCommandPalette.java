package io.forja.components.navigation.fxCommandPalette;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.inputs.fxAutocomplete.FxAutocomplete;
import io.forja.components.overlays.OverlayHost;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import io.forja.components.utilities.fxKeybindingHint.FxKeybindingHint;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * A ⌘K-style command palette — a centered floating input that filters a
 * command list and invokes a callback on select.
 *
 * <p>Renders on the {@link OverlayHost} scrim; {@code ESC} closes.
 * Optionally installs a scene-level {@link KeyCombination} accelerator
 * (default {@code Ctrl/Cmd+K}) to open the palette from anywhere.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxCommandPalette p = FxCommandPalette.builder()
 *          .command(new Command("newProject", "New project", "fth-plus", () -> ...))
 *          .command(new Command("openDocs",   "Open docs",   "fth-book", () -> ...))
 *          .accelerator(new KeyCodeCombination(KeyCode.K, KeyCombination.SHORTCUT_DOWN))
 *          .build();
 *      p.install(scene);
 *     }
 * </pre>
 *
 * @see Command
 * @see Builder
 */
public class FxCommandPalette {

    private final List<Command> commands = new ArrayList<>();
    private final StackPane root = new StackPane();
    private final Region scrim = new Region();
    private final VBox panel = new VBox();
    private final FxAutocomplete<Command> autocomplete;
    private KeyCombination accelerator = new KeyCodeCombination(KeyCode.K, KeyCombination.SHORTCUT_DOWN);
    private boolean open = false;

    /**
     * Creates an empty {@code FxCommandPalette}.
     */
    public FxCommandPalette() {
        root.getStyleClass().add("forja-command-palette");
        scrim.getStyleClass().add("forja-command-palette-scrim");
        scrim.setOnMouseClicked(e -> { close(); e.consume(); });
        panel.getStyleClass().add("forja-command-palette-panel");
        panel.setSpacing(0);
        panel.setMaxWidth(560);
        panel.setMaxHeight(400);
        panel.setPadding(new Insets(0));
        StackPane.setAlignment(panel, Pos.TOP_CENTER);
        StackPane.setMargin(panel, new Insets(80, 0, 0, 0));
        panel.setOnMouseClicked(e -> e.consume());

        autocomplete = FxAutocomplete.<Command>builder()
                .stringifier(Command::getTitle)
                .promptText("Type a command…")
                .onSelect(this::runAndClose)
                .build();

        autocomplete.getSuggestions().setCellFactory(list -> new ListCell<Command>() {
            @Override protected void updateItem(Command item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setGraphic(null); return; }
                HBox row = new HBox(10);
                row.setAlignment(Pos.CENTER_LEFT);
                if (item.iconLiteral != null && !item.iconLiteral.isEmpty()) {
                    row.getChildren().add(new io.forja.components.utilities.fxIcon.FxIcon(item.iconLiteral));
                }
                FxLabel title = FxLabel.builder().text(item.title).variant(LabelVariant.BODY).build();
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                row.getChildren().addAll(title, spacer);
                if (item.hint != null && !item.hint.isEmpty()) {
                    FxKeybindingHint h = FxKeybindingHint.builder().keys(item.hint.split("\\+")).build();
                    row.getChildren().add(h);
                }
                setText(null);
                setGraphic(row);
            }
        });

        panel.getChildren().add(autocomplete);
        root.getChildren().addAll(scrim, panel);
        root.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ESCAPE) close(); });
    }

    private void runAndClose(Command c) {
        close();
        if (c != null && c.action != null) c.action.accept(c);
    }

    /** Registers a command. */
    public void addCommand(Command c) {
        if (c == null) return;
        commands.add(c);
        autocomplete.getItems().setAll(commands);
    }

    /** Installs a scene-level accelerator that opens this palette. */
    public void install(Scene scene) {
        if (scene == null) return;
        scene.getAccelerators().put(accelerator, () -> open(scene));
    }

    /** Opens the palette on the given scene. */
    public void open(Scene scene) {
        if (scene == null || open) return;
        OverlayHost.show(scene, root);
        autocomplete.setText("");
        autocomplete.getItems().setAll(commands);
        autocomplete.getField().getTextField().requestFocus();
        open = true;
    }

    /** Closes the palette. */
    public void close() {
        OverlayHost.dismiss(root);
        open = false;
    }

    /** Returns the wrapped autocomplete. */
    public FxAutocomplete<Command> getAutocomplete() { return autocomplete; }
    /** Returns the panel VBox. */
    public VBox getPanel() { return panel; }
    /** Returns the outer StackPane. */
    public StackPane getRoot() { return root; }
    /** Returns the accelerator. */
    public KeyCombination getAccelerator() { return accelerator; }
    /** Sets the accelerator. */
    public void setAccelerator(KeyCombination v) { this.accelerator = v; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxCommandPalette}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * A palette command — id, human title, optional icon glyph, optional
     * hint (rendered as {@link FxKeybindingHint}), and the action to run
     * when selected.
     */
    public static final class Command {
        public final String id;
        public final String title;
        public final String iconLiteral;
        public final String hint;
        public final Consumer<Command> action;

        public Command(String id, String title, String iconLiteral, String hint, Consumer<Command> action) {
            this.id = id;
            this.title = title;
            this.iconLiteral = iconLiteral;
            this.hint = hint;
            this.action = action;
        }

        public Command(String id, String title, String iconLiteral, Consumer<Command> action) {
            this(id, title, iconLiteral, null, action);
        }

        public String getId() { return id; }
        public String getTitle() { return title; }
        @Override public String toString() { return title == null ? id : title; }
    }

    /**
     * Fluent builder for constructing an {@link FxCommandPalette}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>commands — empty</li>
     *   <li>accelerator — {@code Ctrl/Cmd+K}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<StackPane, Builder> {

        private final List<Command> commands = new ArrayList<>();
        private KeyCombination accelerator = new KeyCodeCombination(KeyCode.K, KeyCombination.SHORTCUT_DOWN);

        public Builder command(Command command) { if (command != null) commands.add(command); return this; }
        public Builder commands(Command... commands) { if (commands != null) this.commands.addAll(Arrays.asList(commands)); return this; }
        public Builder accelerator(KeyCombination accelerator) { this.accelerator = accelerator; return this; }

        /** Builds the palette. */
        public FxCommandPalette buildPalette() {
            FxCommandPalette p = new FxCommandPalette();
            p.setAccelerator(accelerator);
            for (Command c : commands) p.addCommand(c);
            return p;
        }

        /**
         * @deprecated returns the palette's outer {@link StackPane}. Prefer
         * {@link #buildPalette()} which returns the palette itself.
         */
        @Deprecated
        @Override
        public StackPane build() { return buildPalette().getRoot(); }
    }
}
