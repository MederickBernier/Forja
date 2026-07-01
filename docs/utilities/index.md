# Utilities

Cross-cutting helpers that don't fit in a category.

| Component | Summary |
|---|---|
| [FxIcon](#fxicon) | Styled icon glyph built on Ikonli (bundles the Feather pack). |
| [FxThemeToggle](#fxthemetoggle) | Button that toggles a scene between light and dark themes. |
| [FxKeybindingHint](#fxkeybindinghint) | Row of `FxKbd` chips separated by `+` (e.g. ⌘ + K). |
| [FxSearchHighlight](#fxsearchhighlight) | `TextFlow` that bolds occurrences of a query in a source string. |
| [FxScrollSpy](#fxscrollspy) | Tracks which registered section is visible inside a `ScrollPane`. |
| [FxPortal](#fxportal) | Renders a node into the overlay layer on demand. |
| [FxFocusTrap](#fxfocustrap) | Cycles keyboard focus among a subtree's descendants. |

## FxIcon

Styled icon glyph built on Ikonli's `FontIcon`. Renders glyphs from any Ikonli pack on the classpath (Feather is bundled by default). Adds a semantic color variant system tied to the active theme.

### Usage

```java
FxIcon save = FxIcon.builder()
    .literal("fth-save")
    .size(20)
    .variant(SemanticVariant.ACCENT)
    .build();

// Or plain constructor
FxIcon save2 = new FxIcon("fth-save");
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `literal(String)` | `String` | `""` | Ikonli icon literal (e.g. `fth-save`). |
| `size(int)` | `int` | `16` | Icon size in px. |
| `variant(SemanticVariant)` | `SemanticVariant` | `DEFAULT` | Color variant. |

## FxThemeToggle

Button that toggles between `FxTheme.LIGHT` and `FxTheme.DARK` on a target `Scene` via `Forja.applyTheme`. Icon reflects current theme — sun in dark mode, moon in light mode.

### Usage

```java
FxThemeToggle toggle = FxThemeToggle.builder()
    .scene(scene)
    .initial(FxTheme.LIGHT)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `scene(Scene)` | `Scene` | `null` | Target scene the toggle applies to. |
| `initial(FxTheme)` | `FxTheme` | `LIGHT` | Starting theme. |

## FxKeybindingHint

Row of `FxKbd` chips separated by a small `+` glyph — a compact way to display a keyboard shortcut like `⌘ + K`.

### Usage

```java
FxKeybindingHint hint = FxKeybindingHint.builder()
    .keys("⌘", "K")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `keys(List<String>)` / `keys(String...)` | items | empty | Key labels. |

## FxSearchHighlight

`TextFlow` that renders a source string with occurrences of a search query wrapped in bold `Text` nodes so matches stand out. Substring match (not regex).

### Usage

```java
FxSearchHighlight hl = FxSearchHighlight.builder()
    .text("Deploy the pipeline to production")
    .query("pipe")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Source text. |
| `query(String)` | `String` | `""` | Query to highlight. |
| `caseSensitive(boolean)` | `boolean` | `false` | Case-sensitive matching. |

## FxScrollSpy

Non-visual controller that tracks which of several registered "section" nodes is currently in view inside a `ScrollPane`. Exposes the active section's key via `activeKeyProperty()`.

### Usage

```java
FxScrollSpy spy = FxScrollSpy.builder()
    .scrollPane(scrollPane)
    .section("intro",     intro)
    .section("install",   install)
    .section("api",       api)
    .build();
spy.activeKeyProperty().addListener((obs, o, key) -> sidebar.highlight(key));
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `scrollPane(ScrollPane)` | `ScrollPane` | none | Host scroll pane. |
| `section(String, Node)` | key + node | — | Register a section. |

## FxPortal

"Render-elsewhere" helper — attaches a target node to the `OverlayHost` overlay layer of a given `Scene` whenever `visible` is `true`, and removes it when `false`.

Useful for lightbox, dropdown-menu, or popup content that logically belongs to a component but should render on top of the whole scene.

### Usage

```java
FxPortal portal = FxPortal.builder()
    .scene(scene)
    .node(mySnackbar)
    .position(Pos.BOTTOM_CENTER)
    .visible(true)
    .build();

portal.setVisible(false); // detaches from overlay
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `scene(Scene)` | `Scene` | `null` | Target scene. |
| `node(Node)` | `Node` | `null` | Node to portal into the overlay. |
| `position(Pos)` | `Pos` | `CENTER` | Alignment on the overlay layer. |
| `visible(boolean)` | `boolean` | `false` | Initial attach state. |

## FxFocusTrap

Non-visual utility that traps keyboard focus inside a root node's subtree. Intercepts `TAB` / `SHIFT+TAB` and cycles focus among focusable descendants.

Typical use: overlay dialogs — install on the dialog's panel so users can't tab out into the underlying app.

### Usage

```java
FxFocusTrap trap = FxFocusTrap.builder()
    .root(dialogPanel)
    .build();
// autoInstall=true (default) wires the listener immediately.

// later
trap.uninstall();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `root(Parent)` | `Parent` | `null` | Root subtree to trap focus in. |
| `autoInstall(boolean)` | `boolean` | `true` | Attach key-listener at build time. |
