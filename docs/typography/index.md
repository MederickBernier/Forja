# Typography

Text-rendering primitives — labels, links, code blocks, keyboard chips, block quotes, bullet lists.

| Component | Summary |
|---|---|
| [FxLabel](#fxlabel) | Themed label with `LabelVariant` (display / heading / subheading / body / small / mono). |
| [FxLink](#fxlink) | Text hyperlink with hover state and optional graphic. |
| [FxText](#fxtext) | Body text node with variant control. |
| [FxBlockquote](#fxblockquote) | Left-bar block quote for pulled-out prose. |
| [FxKbd](#fxkbd) | Rendered keyboard-key chip (⌘, K, etc.). |
| [FxCode](#fxcode) | Inline monospaced code snippet. |
| [FxBulletList](#fxbulletlist) | Ordered or unordered list of text items. |

## FxLabel

Themed label built on `javafx.scene.control.Label`. Selects a size + weight combo via `LabelVariant` (`DISPLAY` / `HEADING` / `SUBHEADING` / `BODY` / `SMALL` / `MONO`) and adds a muted-color pseudo-class.

### Usage

```java
FxLabel title = FxLabel.builder()
    .text("Projects")
    .variant(LabelVariant.HEADING)
    .build();

FxLabel helper = FxLabel.builder()
    .text("Optional")
    .variant(LabelVariant.SMALL)
    .muted(true)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Label text. |
| `variant(LabelVariant)` | `LabelVariant` | `BODY` | Typography variant. |
| `muted(boolean)` | `boolean` | `false` | Applies the muted color pseudo-class. |
| `wrapText(boolean)` | `boolean` | `false` | Enable text wrap. |

## FxLink

Text hyperlink. Extends `javafx.scene.control.Hyperlink` with a variant enum and an optional leading/trailing graphic.

### Usage

```java
FxLink docs = FxLink.builder()
    .text("Read the docs")
    .variant(LinkVariant.BODY)
    .onAction(e -> openDocs())
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Link text. |
| `variant(LinkVariant)` | `LinkVariant` | `BODY` | Size variant. |
| `graphic(Node)` | `Node` | none | Optional graphic (typically an `FxIcon`). |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Click callback. |

## FxText

Body text node with variant control. Use it when you need paragraphs of text with typography controls that don't fit `FxLabel`'s single-line default.

### Usage

```java
FxText body = FxText.builder()
    .text("Forja layers a design system on top of JavaFX...")
    .variant(TextVariant.BODY)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Text content. |
| `variant(TextVariant)` | `TextVariant` | `BODY` | Size + weight variant. |
| `wrapText(boolean)` | `boolean` | `true` | Wrap long text. |

## FxBlockquote

Pulled-out prose block with a left accent bar. Wraps a `Label` inside a bordered container.

### Usage

```java
FxBlockquote quote = FxBlockquote.builder()
    .text("Shape what already works.")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Quoted text. |
| `attribution(String)` | `String` | `""` | Optional attribution line. |

## FxKbd

Rendered keyboard-key chip. Use inside prose or a `FxKeybindingHint` row to reference specific keys.

### Usage

```java
FxKbd cmd = FxKbd.builder().text("⌘").build();
FxKbd k   = FxKbd.builder().text("K").build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Key label. |

## FxCode

Inline monospaced code snippet — a chip with a JetBrains Mono font.

### Usage

```java
FxCode ref = FxCode.builder().text("Forja.install(scene)").build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Code text. |

## FxBulletList

Ordered or unordered vertical list. Uses `•` for `UNORDERED` and `1.`, `2.`, ... for `ORDERED`.

### Usage

```java
FxBulletList steps = FxBulletList.builder()
    .kind(FxBulletList.Kind.ORDERED)
    .items("Install", "Configure", "Deploy")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `kind(Kind)` | `Kind` | `UNORDERED` | `UNORDERED` (•) or `ORDERED` (1., 2., ...). |
| `items(List<String>)` | `List<String>` | empty | List items. |
| `items(String...)` | `String[]` | empty | Varargs form. |
