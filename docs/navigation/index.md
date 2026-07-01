# Navigation

Wayfinding — how users move between views.

| Component | Summary |
|---|---|
| [FxTabs](#fxtabs) | Styled `TabPane` wrapper. |
| [FxVerticalTabs](#fxverticaltabs) | Left-side vertical tabs. |
| [FxAppBar](#fxappbar) | Top bar with leading slot, title, and trailing actions. |
| [FxSidebar](#fxsidebar) | Fixed-width vertical container for navigation content. |
| [FxSidebarNav](#fxsidebarnav) | Keyed nav-item list with icon + label and active state. |
| [FxBreadcrumbs](#fxbreadcrumbs) | Trail of clickable segments separated by `/`. |
| [FxStepper](#fxstepper) | Numbered dots with active + done pseudo-classes and captions. |
| [FxPagination](#fxpagination) | Prev/next arrows + numbered page buttons with ellipsis window. |
| [FxMenuBar](#fxmenubar) | Styled `MenuBar` wrapper. |
| [FxContextMenu](#fxcontextmenu) | Styled `ContextMenu` wrapper. |
| [FxDropdownMenu](#fxdropdownmenu) | Text-first `MenuButton` wrapper. |
| [FxAnchorNav](#fxanchornav) | In-page jump link list backed by a `ScrollPane`. |
| [FxCommandPalette](#fxcommandpalette) | ⌘K-style command launcher on the overlay layer. |

## FxTabs

Styled tabbed pane built on `javafx.scene.control.TabPane`. Tab construction, selection, and drag/close behaviors remain fully accessible.

### Usage

```java
FxTabs tabs = FxTabs.builder()
    .tabs(new Tab("Overview", overview), new Tab("Docs", docs))
    .side(Side.TOP)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `tabs(Tab...)` | `Tab[]` | empty | Tabs. |
| `side(Side)` | `Side` | `TOP` | Tab-header side. |
| `closingPolicy(TabClosingPolicy)` | policy | `UNAVAILABLE` | Tab close policy. |

## FxVerticalTabs

Vertical (LEFT-side) tabbed pane. Same as `FxTabs` with `side` defaulted to `Side.LEFT` and `rotateGraphic` on so tab titles read horizontally.

### Usage

```java
FxVerticalTabs settings = FxVerticalTabs.builder()
    .tabs(new Tab("Profile", profile), new Tab("Billing", billing))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `tabs(Tab...)` | `Tab[]` | empty | Tabs. |
| `side(Side)` | `Side` | `LEFT` | Tab-header side. |

## FxAppBar

Top app bar with a leading slot (menu/back), a title, and a trailing action slot. Aliased as `FxNavbar`.

### Usage

```java
FxAppBar bar = FxAppBar.builder()
    .leading(menuButton)
    .title("Forja Demo")
    .actions(themeToggle, settingsButton)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `leading(Node...)` | `Node[]` | empty | Leading-slot children. |
| `title(String)` | `String` | `""` | Title text. |
| `actions(Node...)` | `Node[]` | empty | Trailing-actions children. |

## FxSidebar

Vertical container for navigation content — a styled `VBox` with a fixed default width.

### Usage

```java
FxSidebar side = FxSidebar.builder()
    .width(240)
    .children(sidebarNav, footer)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `width(double)` | `double` | `240` | Preferred width. |
| `spacing(double)` | `double` | `6` | Gap between children. |
| `children(Node...)` | `Node[]` | empty | Sidebar content. |

## FxSidebarNav

Vertical list of keyed nav items with icon + label and an active state. The currently-active item's row gets the `:active` pseudo-class.

### Usage

```java
FxSidebarNav nav = FxSidebarNav.builder()
    .item("home",     "Home",     "fth-home")
    .item("projects", "Projects", "fth-folder")
    .item("settings", "Settings", "fth-settings")
    .active("projects")
    .onSelect(key -> router.go(key))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `item(String, String, String)` | key + label + iconLiteral | — | Add a nav item. |
| `active(String)` | `String` | `null` | Initial active key. |
| `onSelect(OnSelect)` | callback | none | Fires when active key changes. |

## FxBreadcrumbs

Trail of clickable segments separated by `/`. Each non-last segment is an `FxLink`; the last is a muted label representing the current location.

### Usage

```java
FxBreadcrumbs bc = FxBreadcrumbs.builder()
    .segments("Projects", "Forja", "Docs")
    .onSelect(idx -> router.upTo(idx))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `segments(List<String>)` / `segments(String...)` | items | empty | Trail segments. |
| `onSelect(OnSelect)` | callback | none | Fires when a segment is clicked. |

## FxStepper

Horizontal multi-step progress indicator — numbered dots + optional labels connected by lines. Active and completed dots get `:active` / `:done` pseudo-classes.

### Usage

```java
FxStepper wizard = FxStepper.builder()
    .steps("Account", "Profile", "Review")
    .currentStep(1)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `steps(List<String>)` / `steps(String...)` | items | empty | Step captions. |
| `currentStep(int)` | `int` | `0` | Current step index. |

## FxPagination

Prev/next arrows + numbered page buttons. When `totalPages > visiblePages`, only a window around the current page is shown with ellipsis markers.

### Usage

```java
FxPagination pg = FxPagination.builder()
    .totalPages(20)
    .currentPage(0)
    .visiblePages(7)
    .onPageChange(p -> viewModel.loadPage(p))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `totalPages(int)` | `int` | `1` | Total pages. |
| `currentPage(int)` | `int` | `0` | Zero-based current page. |
| `visiblePages(int)` | `int` | `7` | Window size. |
| `onPageChange(OnPageChange)` | callback | none | Fires when current page changes. |

## FxMenuBar

Styled `MenuBar` wrapper.

### Usage

```java
FxMenuBar mb = FxMenuBar.builder()
    .menus(fileMenu, editMenu, viewMenu)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `menus(Menu...)` | `Menu[]` | empty | Menus. |
| `useSystemMenuBar(boolean)` | `boolean` | `false` | Use the native system menu bar on macOS. |

## FxContextMenu

Styled `ContextMenu` wrapper. Install on a node via `setOnContextMenuRequested`.

### Usage

```java
FxContextMenu cm = FxContextMenu.builder()
    .items(copy, paste, delete)
    .build();
target.setOnContextMenuRequested(e -> cm.show(target, e.getScreenX(), e.getScreenY()));
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(MenuItem...)` | `MenuItem[]` | empty | Menu items. |

## FxDropdownMenu

Text-first dropdown menu built on `javafx.scene.control.MenuButton`. Use `FxMenuButton` for the icon-only kebab variant.

### Usage

```java
FxDropdownMenu m = FxDropdownMenu.builder()
    .text("Actions")
    .items(rename, duplicate, delete)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Button label. |
| `items(MenuItem...)` | `MenuItem[]` | empty | Menu items. |

## FxAnchorNav

Vertical list of in-page jump links backed by a `ScrollPane` + target-node map. Clicking a link scrolls the paired scroll pane so the target node's top aligns with the viewport top. Register the active key to highlight the current section.

### Usage

```java
FxAnchorNav toc = FxAnchorNav.builder()
    .scrollPane(scrollPane)
    .section("intro",   "Intro",   introNode)
    .section("install", "Install", installNode)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `scrollPane(ScrollPane)` | `ScrollPane` | none | Host scroll pane. |
| `section(String, String, Node)` | key + label + target | — | Register a jump target. |
| `active(String)` | `String` | `null` | Initial active key. |

## FxCommandPalette

⌘K-style command launcher — a centered floating `FxAutocomplete` over a command list, rendered on the `OverlayHost`. Optionally installs a scene-level `KeyCombination` accelerator (default `Ctrl/Cmd+K`).

### Usage

```java
FxCommandPalette p = FxCommandPalette.builder()
    .command(new Command("newProject", "New project", "fth-plus", c -> ...))
    .command(new Command("openDocs",   "Open docs",   "fth-book", c -> ...))
    .accelerator(new KeyCodeCombination(KeyCode.K, KeyCombination.SHORTCUT_DOWN))
    .buildPalette();
p.install(scene);
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `command(Command)` | `Command` | — | Register a single command. |
| `commands(Command...)` | `Command[]` | empty | Register a batch of commands. |
| `accelerator(KeyCombination)` | key combo | `Ctrl/Cmd+K` | Scene-level open accelerator. |

Note: use `buildPalette()` to get the `FxCommandPalette` instance. The
default `build()` returns the palette's outer `StackPane` for compatibility
with the `FxNodeBuilder` contract but is deprecated.
