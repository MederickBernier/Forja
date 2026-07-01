# FxCommandPalette

⌘K-style command launcher — a centered floating `FxAutocomplete` over a command list, rendered on the `OverlayHost`. Optionally installs a scene-level `KeyCombination` accelerator (default `Ctrl/Cmd+K`).

## Usage

```java
FxCommandPalette p = FxCommandPalette.builder()
    .command(new Command("newProject", "New project", "fth-plus", c -> ...))
    .command(new Command("openDocs",   "Open docs",   "fth-book", c -> ...))
    .accelerator(new KeyCodeCombination(KeyCode.K, KeyCombination.SHORTCUT_DOWN))
    .buildPalette();
p.install(scene);
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `command(Command)` | `Command` | — | Register a single command. |
| `commands(Command...)` | `Command[]` | empty | Register a batch of commands. |
| `accelerator(KeyCombination)` | key combo | `Ctrl/Cmd+K` | Scene-level open accelerator. |

Note: use `buildPalette()` to get the `FxCommandPalette` instance. The
default `build()` returns the palette's outer `StackPane` for compatibility
with the `FxNodeBuilder` contract but is deprecated.
