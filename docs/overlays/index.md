# Overlays

Anything that renders above the app. Most ride on [`OverlayHost`](../foundation.md#overlay-layer) — a per-scene overlay layer installed on first use.

| Component | Summary |
|---|---|
| [FxTooltip](#fxtooltip) | Styled `Tooltip` with fluent builder and `install(node, text)` shortcut. |
| [FxDialog](#fxdialog) | Modal dialog on the overlay layer with title, body, and footer slots. |
| [FxConfirmDialog](#fxconfirmdialog) | Preset dialog with cancel + confirm buttons and boolean callback. |
| [FxFormDialog](#fxformdialog) | Preset dialog with save + cancel and optional canSave gate. |
| [FxPopover](#fxpopover) | Floating panel anchored to a target node. |
| [FxHoverCard](#fxhovercard) | Popover driven by target hover with open/close delays. |
| [FxDrawer](#fxdrawer) | Side-anchored sliding panel with scrim. |
| [FxBottomSheet](#fxbottomsheet) | Bottom-anchored slide-up sheet with grabber handle. |
| [FxLightbox](#fxlightbox) | Full-scrim media viewer with prev/next navigation. |

## FxTooltip

Styled `Tooltip` with a fluent builder. Attach via `FxTooltip.install(node, text)` for any `Node`, or `install(control, tooltip)` for a `Control`.

### Usage

```java
FxTooltip help = FxTooltip.builder()
    .text("Deletes this project permanently")
    .showDelayMs(300)
    .build();
FxTooltip.install(deleteButton, help);

// Or shortcut on any Node
FxTooltip.install(myNode, "Info");
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Tooltip text. |
| `showDelayMs(long)` | `long` | `400` | Delay before showing. |
| `showDurationMs(long)` | `long` | `5000` | Max on-screen duration. |
| `hideDelayMs(long)` | `long` | `200` | Delay before hiding. |

## FxDialog

Modal dialog rendered on the `OverlayHost` overlay layer. Contains a translucent scrim and a centered panel with title, body, and footer slots.

### Usage

```java
FxDialog d = FxDialog.builder()
    .title("Rename project")
    .body(nameField)
    .footer(cancelButton, saveButton)
    .build();
d.show(scene);
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `""` | Header title. |
| `body(Node...)` | `Node[]` | empty | Body content. |
| `footer(Node...)` | `Node[]` | empty | Footer children (usually buttons). |
| `dismissOnScrimClick(boolean)` | `boolean` | `true` | Close on scrim click. |
| `dismissOnEscape(boolean)` | `boolean` | `true` | Close on ESC. |
| `showCloseIcon(boolean)` | `boolean` | `true` | Show the header × icon. |
| `okOnly(String)` | `String` | — | Sugar: footer becomes one PRIMARY button that closes the dialog. |

## FxConfirmDialog

Confirmation dialog — an `FxDialog` preset with title, message, cancel + confirm buttons, and a boolean callback.

### Usage

```java
// Static shortcut
FxConfirmDialog.ask(scene, "Delete project?",
    "This action cannot be undone.",
    confirmed -> { if (confirmed) doDelete(); });

// Or builder for custom labels + variant
FxConfirmDialog d = FxConfirmDialog.builder()
    .title("Delete project?")
    .message("This action cannot be undone.")
    .cancelText("Keep it")
    .confirmText("Delete")
    .confirmVariant(ButtonVariant.DANGER)
    .onResult(confirmed -> ...)
    .build();
d.show(scene);
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `"Confirm"` | Dialog title. |
| `message(String)` | `String` | `""` | Body message. |
| `cancelText(String)` | `String` | `"Cancel"` | Cancel button label. |
| `confirmText(String)` | `String` | `"Confirm"` | Confirm button label. |
| `confirmVariant(ButtonVariant)` | `ButtonVariant` | `PRIMARY` | Confirm button variant. |
| `onResult(Consumer<Boolean>)` | callback | none | Fires with `true` = confirm, `false` = cancel. |

## FxFormDialog

Form-entry dialog — wraps an `FxDialog` with a body area, cancel button, and a save button whose enabled state is optionally driven by a validator `Supplier<Boolean>`.

### Usage

```java
FxFormDialog dlg = FxFormDialog.builder()
    .title("Rename")
    .body(nameField)
    .saveText("Save")
    .canSave(() -> !nameField.getText().isEmpty())
    .onSubmit(() -> project.rename(nameField.getText()))
    .build();
dlg.show(scene);
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `"Form"` | Dialog title. |
| `body(Node)` | `Node` | empty region | Form body. |
| `cancelText(String)` | `String` | `"Cancel"` | Cancel button label. |
| `saveText(String)` | `String` | `"Save"` | Save button label. |
| `saveVariant(ButtonVariant)` | `ButtonVariant` | `PRIMARY` | Save button variant. |
| `canSave(Supplier<Boolean>)` | supplier | none | Gates save-button enabled state. |
| `onSubmit(OnSubmit)` | callback | none | Fires on save when `canSave` passes. |

## FxPopover

Floating panel anchored to a target `Node`. Wraps a `Popup` sized to arbitrary content and positioned on any `Side` of its anchor. Auto-hides on outside click.

### Usage

```java
FxPopover pop = FxPopover.builder()
    .anchor(button)
    .content(menuBox)
    .side(Side.BOTTOM)
    .build();
pop.show();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `anchor(Node)` | `Node` | none | Anchor node. |
| `content(Node)` | `Node` | none | Popover content. |
| `side(Side)` | `Side` | `BOTTOM` | Side to appear on. |
| `autoHide(boolean)` | `boolean` | `true` | Hide on outside click. |

## FxHoverCard

Hover-triggered rich card. Wraps an `FxPopover` whose show/hide is driven by the target node's `hoverProperty` with configurable open/close delays.

### Usage

```java
FxHoverCard hc = FxHoverCard.builder()
    .target(avatar)
    .content(profileCard)
    .openDelayMs(400)
    .closeDelayMs(200)
    .build();
// autoInstall=true wires listeners immediately; pass autoInstall(false) + call hc.install() later.
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `target(Node)` | `Node` | none | Node whose hover drives the card. |
| `content(Node)` | `Node` | none | Card content. |
| `side(Side)` | `Side` | `BOTTOM` | Side to appear on. |
| `openDelayMs(long)` | `long` | `400` | Delay before showing. |
| `closeDelayMs(long)` | `long` | `200` | Delay before hiding. |
| `autoInstall(boolean)` | `boolean` | `true` | Install hover listeners at build time. |

## FxDrawer

Side-anchored sliding panel rendered on `OverlayHost`. Opening slides the panel in via a translate transition; closing slides it back out. Scrim absorbs clicks; ESC closes.

### Usage

```java
FxDrawer settings = FxDrawer.builder()
    .content(settingsPanel)
    .side(Side.RIGHT)
    .size(320)
    .build();
settings.open(scene);
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `side(Side)` | `Side` | `RIGHT` | Anchor side. |
| `size(double)` | `double` | `320` | Panel size along the drawer's axis. |
| `content(Node)` | `Node` | none | Panel content. |

## FxBottomSheet

Bottom-anchored slide-up sheet with a grabber handle. Slides up from the scene bottom on `open`, slides back down on `close`.

### Usage

```java
FxBottomSheet sheet = FxBottomSheet.builder()
    .content(sheetBody)
    .height(280)
    .build();
sheet.open(scene);
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `content(Node)` | `Node` | none | Sheet content. |
| `height(double)` | `double` | `280` | Sheet height in px. |

## FxLightbox

Full-scrim media viewer for one or more image URLs. Navigation arrows step through the images; ESC closes; LEFT/RIGHT navigate.

### Usage

```java
FxLightbox lb = FxLightbox.builder()
    .images("file:/a.png", "file:/b.png", "file:/c.png")
    .index(0)
    .build();
lb.open(scene);
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `images(List<String>)` / `images(String...)` | items | empty | Image URLs. |
| `index(int)` | `int` | `0` | Initial image index. |
