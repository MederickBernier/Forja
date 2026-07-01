# Buttons & Actions

Clickable surfaces that carry out a command. Every button honors the shared `ButtonVariant` enum (`PRIMARY` / `SECONDARY` / `GHOST` / `DANGER`).

| Component | Summary |
|---|---|
| [FxButton](#fxbutton) | Text button with variant, loading state, and action handler. |
| [FxIconButton](#fxiconbutton) | Icon-first button with optional label position. |
| [FxToggleButton](#fxtogglebutton) | Stateful button that stays pressed. |
| [FxButtonGroup](#fxbuttongroup) | Segmented row of toggle buttons with single-select behavior. |
| [FxCopyButton](#fxcopybutton) | Copies a value to the clipboard and flashes confirmation. |
| [FxSplitButton](#fxsplitbutton) | Primary click surface plus a chevron menu. |
| [FxMenuButton](#fxmenubutton) | Icon-only kebab-style menu trigger. |

## FxButton

Styled button built on `javafx.scene.control.Button`. Adds a visual variant system, a loading state, and a fluent builder. All native JavaFX properties, bindings, and events remain accessible.

### Usage

```java
FxButton save = FxButton.builder()
    .text("Save")
    .variant(ButtonVariant.PRIMARY)
    .onAction(e -> handleSave())
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Button label. |
| `variant(ButtonVariant)` | `ButtonVariant` | `PRIMARY` | Visual variant: `PRIMARY` / `SECONDARY` / `GHOST` / `DANGER`. |
| `loading(boolean)` | `boolean` | `false` | Faded + non-interactive loading state. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Click callback. |

## FxIconButton

Icon-first button. Renders an Ikonli glyph as the primary content with an optional text label positioned before or after the icon.

### Usage

```java
FxIconButton save = FxIconButton.builder()
    .icon("fth-save")
    .text("Save")
    .iconPosition(IconPosition.LEADING)
    .variant(ButtonVariant.PRIMARY)
    .onAction(e -> handleSave())
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Optional button label alongside the icon. |
| `icon(String)` | `String` | none | Ikonli icon literal (e.g. `fth-save`). |
| `iconPosition(IconPosition)` | `IconPosition` | `LEADING` | `LEADING` / `TRAILING` / `ONLY`. |
| `iconSize(int)` | `int` | `16` | Icon size in px. |
| `variant(ButtonVariant)` | `ButtonVariant` | `SECONDARY` | Visual variant. |
| `loading(boolean)` | `boolean` | `false` | Loading state. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Click callback. |

## FxToggleButton

Stateful button that stays pressed. Extends `javafx.scene.control.ToggleButton` so it participates in a shared `ToggleGroup` if you set one.

### Usage

```java
FxToggleButton bold = FxToggleButton.builder()
    .text("B")
    .selected(false)
    .variant(ButtonVariant.GHOST)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Label. |
| `selected(boolean)` | `boolean` | `false` | Initial selected state. |
| `variant(ButtonVariant)` | `ButtonVariant` | `SECONDARY` | Visual variant. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Click callback. |
| `toggleGroupUserData(Object)` | `Object` | none | Optional user-data attached to the toggle for group tracking. |

## FxButtonGroup

Segmented row of `FxToggleButton` items sharing a `ToggleGroup`. Exposes an observable value for the currently-selected item. Also aliased as `FxSegmentedControl`.

### Usage

```java
FxButtonGroup align = FxButtonGroup.builder()
    .items("Left", "Center", "Right")
    .value("Left")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<String>)` | `List<String>` | empty | Segment labels. |
| `items(String...)` | `String[]` | empty | Segment labels varargs form. |
| `value(String)` | `String` | `null` | Initially selected item. |
| `orientation(Orientation)` | `Orientation` | `HORIZONTAL` | Row or column layout. |
| `allowEmpty(boolean)` | `boolean` | `false` | Whether the group can have no selection. |

## FxCopyButton

Copies a configured string to the system clipboard on click, briefly flashes a check icon + confirm label, then reverts.

### Usage

```java
FxCopyButton copyToken = FxCopyButton.builder()
    .text("Copy")
    .value("api-key-abc123")
    .confirmText("Copied!")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `"Copy"` | Idle label. |
| `value(String)` | `String` | `""` | Clipboard payload. |
| `confirmText(String)` | `String` | `"Copied"` | Label shown during confirm flash. |
| `confirmDurationMs(long)` | `long` | `1500` | Confirm flash duration in ms. |
| `variant(ButtonVariant)` | `ButtonVariant` | `SECONDARY` | Visual variant. |

## FxSplitButton

Split button — primary click surface plus a chevron that opens a menu of `MenuItem`s. Extends `javafx.scene.control.SplitMenuButton`.

### Usage

```java
FxSplitButton save = FxSplitButton.builder()
    .text("Save")
    .variant(ButtonVariant.PRIMARY)
    .onAction(e -> handleSave())
    .items(saveAsItem, exportItem)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Label. |
| `variant(ButtonVariant)` | `ButtonVariant` | `PRIMARY` | Visual variant. |
| `items(MenuItem...)` | `MenuItem[]` | empty | Dropdown menu items. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Primary click callback. |
| `icon(String)` | `String` | none | Optional Ikonli icon literal. |

## FxMenuButton

Menu-button preset for icon-only action clusters. Distinct from `FxDropdownMenu` — that one is text-first. This one is the classic kebab (three-dot) icon that reveals a menu.

### Usage

```java
FxMenuButton kebab = FxMenuButton.builder()
    .icon("fth-more-vertical")
    .items(renameItem, deleteItem)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `icon(String)` | `String` | `"fth-more-vertical"` | Ikonli icon literal. |
| `text(String)` | `String` | `""` | Optional label alongside icon. |
| `variant(ButtonVariant)` | `ButtonVariant` | `GHOST` | Visual variant. |
| `items(MenuItem...)` | `MenuItem[]` | empty | Menu items. |
