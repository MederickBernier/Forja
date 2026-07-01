# Feedback & Status

User-facing state signals. Most components accept a `SemanticVariant` (`INFO` / `SUCCESS` / `WARNING` / `DANGER` / `ACCENT` / `MUTED` / `DEFAULT`).

| Component | Summary |
|---|---|
| [FxBadge](#fxbadge) | Small labeled pill for counts and status tags. |
| [FxChip](#fxchip) | Removable labeled chip. |
| [FxStatusDot](#fxstatusdot) | Colored dot for at-a-glance status. |
| [FxProgressBar](#fxprogressbar) | Horizontal progress bar with determinate + indeterminate modes. |
| [FxProgressCircle](#fxprogresscircle) | Circular progress indicator. |
| [FxSkeleton](#fxskeleton) | Shimmering placeholder shape for loading states. |
| [FxAlert](#fxalert) | Inline colored notice with icon and dismiss action. |
| [FxBanner](#fxbanner) | Full-width top-of-page notice with CTA and dismiss. |
| [FxToast](#fxtoast) | Transient bottom-right notification on the overlay layer. |
| [FxNotificationCenter](#fxnotificationcenter) | Stack of dismissable notifications anchored to a scene corner. |
| [FxEmptyState](#fxemptystate) | Centered "nothing here" panel with icon, heading, action. |
| [FxErrorState](#fxerrorstate) | Error-flavored empty state with danger tint. |
| [FxResultPage](#fxresultpage) | Full-page success / failure / pending / warning summary. |

## FxBadge

Small labeled pill for counts and status tags.

### Usage

```java
FxBadge unread = FxBadge.builder()
    .text("3")
    .variant(SemanticVariant.DANGER)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Badge text. |
| `variant(SemanticVariant)` | `SemanticVariant` | `DEFAULT` | Color variant. |

## FxChip

Removable labeled chip. Use for tags, filter facets, or selected items in a picker.

### Usage

```java
FxChip tag = FxChip.builder()
    .text("javafx")
    .variant(SemanticVariant.ACCENT)
    .removable(true)
    .onClose(e -> removeTag("javafx"))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Chip label. |
| `variant(SemanticVariant)` | `SemanticVariant` | `DEFAULT` | Color variant. |
| `removable(boolean)` | `boolean` | `false` | Show the × close affordance. |
| `onClose(EventHandler<ActionEvent>)` | handler | none | Fired when × is clicked. |

## FxStatusDot

Colored dot for at-a-glance status (online, offline, busy, etc.).

### Usage

```java
FxStatusDot online = FxStatusDot.builder()
    .variant(SemanticVariant.SUCCESS)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `variant(SemanticVariant)` | `SemanticVariant` | `DEFAULT` | Color variant. |
| `size(double)` | `double` | `8` | Dot diameter in px. |

## FxProgressBar

Horizontal progress bar built on `javafx.scene.control.ProgressBar`. Supports both determinate progress and indeterminate mode.

### Usage

```java
FxProgressBar loading = FxProgressBar.builder()
    .progress(0.42)
    .variant(SemanticVariant.ACCENT)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `progress(double)` | `double` | `0` | Progress value in `[0, 1]`. |
| `indeterminate(boolean)` | `boolean` | `false` | Set indeterminate mode (`INDETERMINATE_PROGRESS`). |
| `variant(SemanticVariant)` | `SemanticVariant` | `ACCENT` | Color variant. |

## FxProgressCircle

Circular progress indicator built on `javafx.scene.control.ProgressIndicator`.

### Usage

```java
FxProgressCircle spinner = FxProgressCircle.builder()
    .indeterminate(true)
    .size(24)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `progress(double)` | `double` | `0` | Progress value in `[0, 1]`. |
| `indeterminate(boolean)` | `boolean` | `false` | Set indeterminate mode. |
| `size(double)` | `double` | inherit | Sets pref/min/max width + height. |
| `variant(SemanticVariant)` | `SemanticVariant` | `ACCENT` | Color variant. |

## FxSkeleton

Shimmering placeholder shape for loading states. Three shape variants: `RECT`, `TEXT` (rounded pill), `CIRCLE`.

### Usage

```java
FxSkeleton avatar = FxSkeleton.builder()
    .shape(FxSkeleton.Shape.CIRCLE)
    .width(48).height(48)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `shape(Shape)` | `Shape` | `RECT` | `RECT` / `TEXT` / `CIRCLE`. |
| `width(double)` | `double` | inherit | Fixed width. |
| `height(double)` | `double` | inherit | Fixed height. |
| `animating(boolean)` | `boolean` | `true` | Toggle shimmer animation. |

## FxAlert

Inline colored notice with icon and dismiss action. Icon glyph is auto-selected per variant.

### Usage

```java
FxAlert saved = FxAlert.builder()
    .variant(SemanticVariant.SUCCESS)
    .title("Saved")
    .description("Your changes have been synced.")
    .dismissible(true)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `variant(SemanticVariant)` | `SemanticVariant` | `INFO` | Color + auto-icon. |
| `title(String)` | `String` | `""` | Alert title. |
| `description(String)` | `String` | `""` | Optional description line. |
| `dismissible(boolean)` | `boolean` | `false` | Show the × close icon. |
| `leadingIcon(Node)` | `Node` | auto | Override the auto-selected icon. |
| `leadingIcon(String)` | `String` | auto | Sugar taking an Ikonli literal. |
| `onDismiss(OnDismiss)` | callback | none | Fired when the alert is dismissed. |

## FxBanner

Full-width top-of-page notice with an optional CTA button and a dismiss (×) icon.

### Usage

```java
FxBanner announce = FxBanner.builder()
    .variant(SemanticVariant.ACCENT)
    .message("Version 1.2 is available.")
    .actionText("Update")
    .onAction(e -> updater.start())
    .dismissible(true)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `variant(SemanticVariant)` | `SemanticVariant` | `INFO` | Color + auto-icon. |
| `message(String)` | `String` | `""` | Message text. |
| `actionText(String)` | `String` | `""` | CTA button label; empty hides button. |
| `dismissible(boolean)` | `boolean` | `false` | Show the × close icon. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | CTA click callback. |

## FxToast

Transient bottom-right notification on the overlay layer. Auto-fades in, holds, fades out.

### Usage

```java
// Static shortcut
FxToast.show(scene, "Saved!", SemanticVariant.SUCCESS);

// Or builder for advanced control
FxToast t = FxToast.builder()
    .message("Uploading…")
    .variant(SemanticVariant.INFO)
    .durationMs(4000)
    .build();
t.postTo(scene);
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `message(String)` | `String` | `""` | Text content. |
| `variant(SemanticVariant)` | `SemanticVariant` | `INFO` | Color + auto-icon. |
| `durationMs(long)` | `long` | `3000` | On-screen duration in ms. |
| `position(Pos)` | `Pos` | `BOTTOM_RIGHT` | Corner position. |

## FxNotificationCenter

Stack of dismissible `FxAlert` notifications anchored to a scene corner via `OverlayHost`. Older notifications trim off the bottom once `maxVisible` fills.

### Usage

```java
FxNotificationCenter nc = FxNotificationCenter.builder()
    .position(Pos.TOP_RIGHT)
    .maxVisible(5)
    .build();
nc.install(scene);
nc.post("Saved", SemanticVariant.SUCCESS);

// Static shortcut
FxNotificationCenter.show(scene, "New message", SemanticVariant.INFO);
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `position(Pos)` | `Pos` | `TOP_RIGHT` | Corner anchor. |
| `maxVisible(int)` | `int` | `5` | Cap on visible notifications. |

## FxEmptyState

Centered "nothing here" panel — icon + heading + description + optional action node.

### Usage

```java
FxEmptyState empty = FxEmptyState.builder()
    .icon("fth-inbox")
    .heading("No projects yet")
    .description("Create your first project to get started.")
    .action(newProjectButton)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `icon(String)` | `String` | `"fth-inbox"` | Ikonli icon literal. |
| `heading(String)` | `String` | `""` | Heading text. |
| `description(String)` | `String` | `""` | Descriptive line. |
| `action(Node...)` | `Node[]` | empty | Action-slot children. |

## FxErrorState

Error-flavored `FxEmptyState`. Ships an alert-triangle icon by default and picks up the `forja-error-state` style class so CSS tints headings + icons red.

### Usage

```java
FxErrorState err = FxErrorState.builder()
    .heading("Something went wrong")
    .description("Please retry in a few moments.")
    .action(retryButton)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `icon(String)` | `String` | `"fth-alert-triangle"` | Ikonli icon literal. |
| `heading(String)` | `String` | `""` | Heading text. |
| `description(String)` | `String` | `""` | Descriptive line. |
| `action(Node...)` | `Node[]` | empty | Action-slot children. |

## FxResultPage

Full-page success / failure / pending / warning summary — large icon + title + description + action row. Status picks the icon color and glyph and exposes matching pseudo-classes.

### Usage

```java
FxResultPage done = FxResultPage.builder()
    .status(FxResultPage.Status.SUCCESS)
    .title("Payment received")
    .description("Order #12345 is on its way.")
    .action(backButton)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `status(Status)` | `Status` | `SUCCESS` | `SUCCESS` / `FAILURE` / `PENDING` / `WARNING`. |
| `title(String)` | `String` | `""` | Result title. |
| `description(String)` | `String` | `""` | Descriptive line. |
| `action(Node...)` | `Node[]` | empty | Action row children. |
