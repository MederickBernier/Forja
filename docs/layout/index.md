# Layout

Structural primitives — arrange other components.

| Component | Summary |
|---|---|
| [FxSeparator](#fxseparator) | Horizontal or vertical divider line. |
| [FxSpacer](#fxspacer) | Absorbs available space between siblings. |
| [FxStack](#fxstack) | Vertical flow with spacing. |
| [FxRow](#fxrow) | Horizontal flow with spacing. |
| [FxContainer](#fxcontainer) | Max-width centered container. |
| [FxSection](#fxsection) | Titled section wrapper. |
| [FxCard](#fxcard) | Framed content card with variant, header, and footer slots. |
| [FxGrid](#fxgrid) | Fluent-builder `GridPane` wrapper. |
| [FxFlex](#fxflex) | Wrap-flow layout (`FlowPane`) with gap control. |
| [FxAspectRatio](#fxaspectratio) | Constrains a single child to a fixed width:height ratio. |
| [FxScrollArea](#fxscrollarea) | Styled `ScrollPane` wrapper. |
| [FxSplitView](#fxsplitview) | Styled resizable `SplitPane`. |
| [FxResizablePane](#fxresizablepane) | Container with user-draggable edge handle. |
| [FxCollapse](#fxcollapse) | Single-section collapsible with header + expandable body. |
| [FxAccordion](#fxaccordion) | Stack of `FxCollapse` sections with optional single-open enforcement. |
| [FxStickyHeader](#fxstickyheader) | Pins its header to the top of a host `ScrollPane`. |
| [FxResponsive](#fxresponsive) | Swaps children based on width breakpoints. |

## FxSeparator

Divider line. Horizontal or vertical, thin or bold.

### Usage

```java
FxSeparator hr = FxSeparator.builder()
    .orientation(Orientation.HORIZONTAL)
    .variant(SeparatorVariant.DEFAULT)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `orientation(Orientation)` | `Orientation` | `HORIZONTAL` | Line direction. |
| `variant(SeparatorVariant)` | `SeparatorVariant` | `DEFAULT` | Style variant. |

## FxSpacer

Absorbs available space between siblings inside an `HBox` / `VBox`. Ships as a `Region` with `Priority.ALWAYS` grow already set.

### Usage

```java
HBox row = new HBox(leftNode, FxSpacer.builder().build(), rightNode);
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `size(SpacingSize)` | `SpacingSize` | `NONE` | Optional minimum size along the axis. |

## FxStack

Vertical flow with spacing. Thin wrapper around `VBox` with a token-based spacing scale.

### Usage

```java
FxStack stack = FxStack.builder()
    .spacing(SpacingSize.MD)
    .children(header, body, footer)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `spacing(SpacingSize)` | `SpacingSize` | `SM` | Gap between children. |
| `alignment(Pos)` | `Pos` | `TOP_LEFT` | Content alignment. |
| `children(Node...)` | `Node[]` | empty | Child nodes. |

## FxRow

Horizontal flow with spacing. Thin wrapper around `HBox` with a token-based spacing scale.

### Usage

```java
FxRow row = FxRow.builder()
    .spacing(SpacingSize.SM)
    .children(icon, label, spacer, action)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `spacing(SpacingSize)` | `SpacingSize` | `SM` | Gap between children. |
| `alignment(Pos)` | `Pos` | `CENTER_LEFT` | Content alignment. |
| `children(Node...)` | `Node[]` | empty | Child nodes. |

## FxContainer

Max-width centered container. Use for page-level content wrappers that shouldn't stretch full-width on wide screens.

### Usage

```java
FxContainer page = FxContainer.builder()
    .width(ContainerWidth.LG)
    .children(headerRow, mainSection)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `width(ContainerWidth)` | `ContainerWidth` | `MD` | Max width preset. |
| `children(Node...)` | `Node[]` | empty | Contained children. |

## FxSection

Titled section wrapper. Renders a section-heading label above a body slot.

### Usage

```java
FxSection settings = FxSection.builder()
    .title("Preferences")
    .children(themeToggle, densityToggle)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `""` | Section heading text. |
| `description(String)` | `String` | `""` | Optional descriptive line under title. |
| `children(Node...)` | `Node[]` | empty | Body content. |

## FxCard

Framed content card. Slots for header, body, and footer, plus a `CardVariant` (`DEFAULT` / `OUTLINED` / `ELEVATED`).

### Usage

```java
FxCard card = FxCard.builder()
    .variant(CardVariant.ELEVATED)
    .header(FxLabel.builder().text("Sales").variant(LabelVariant.HEADING).build())
    .body(FxText.builder().text("Q3 revenue up 12%.").build())
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `variant(CardVariant)` | `CardVariant` | `DEFAULT` | Visual variant. |
| `header(Node)` | `Node` | none | Header slot. |
| `body(Node)` | `Node` | none | Body slot. |
| `footer(Node)` | `Node` | none | Footer slot. |

## FxGrid

Fluent-builder `GridPane` wrapper. Add children with explicit `(col, row)` coordinates plus optional span.

### Usage

```java
FxGrid form = FxGrid.builder()
    .hgap(12).vgap(8)
    .add(nameLabel, 0, 0).add(nameField, 1, 0)
    .add(emailLabel, 0, 1).add(emailField, 1, 1)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `hgap(double)` | `double` | `0` | Horizontal gap. |
| `vgap(double)` | `double` | `0` | Vertical gap. |
| `alignment(Pos)` | `Pos` | `TOP_LEFT` | Content alignment. |
| `add(Node, int, int)` | node + col + row | — | Add child at (col, row) with span 1x1. |
| `add(Node, int, int, int, int)` | node + col + row + colSpan + rowSpan | — | Add child with span. |

## FxFlex

Wrap-flow layout — a styled `FlowPane`. Children flow row-by-row (or column-by-column) and wrap to a new line when the axis fills.

### Usage

```java
FxFlex tags = FxFlex.builder()
    .hgap(6).vgap(6)
    .children(tag1, tag2, tag3, tag4)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `orientation(Orientation)` | `Orientation` | `HORIZONTAL` | Flow direction. |
| `hgap(double)` | `double` | `0` | Horizontal gap. |
| `vgap(double)` | `double` | `0` | Vertical gap. |
| `gap(double)` | `double` | `0` | Sets both `hgap` and `vgap`. |
| `alignment(Pos)` | `Pos` | `TOP_LEFT` | Content alignment. |
| `children(Node...)` | `Node[]` | empty | Child nodes. |

## FxAspectRatio

Container that constrains its single child to a fixed width:height ratio. Takes the available width from its parent, computes `height = width / ratio`, and lays out the child.

### Usage

```java
FxAspectRatio banner = FxAspectRatio.builder()
    .ratio(16.0 / 9.0)
    .child(imageView)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `ratio(double)` | `double` | `1.0` | Width / height ratio. |
| `child(Node)` | `Node` | `null` | Single child. |

## FxScrollArea

Styled `ScrollPane` wrapper. Content, viewport, and scrollbar APIs remain fully accessible.

### Usage

```java
FxScrollArea sa = FxScrollArea.builder()
    .content(longVBox)
    .fitToWidth(true)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `content(Node)` | `Node` | none | Scrolled content. |
| `fitToWidth(boolean)` | `boolean` | `true` | Match content width to viewport. |
| `fitToHeight(boolean)` | `boolean` | `false` | Match content height to viewport. |
| `hbarPolicy(ScrollBarPolicy)` | `ScrollBarPolicy` | `AS_NEEDED` | Horizontal scroll bar policy. |
| `vbarPolicy(ScrollBarPolicy)` | `ScrollBarPolicy` | `AS_NEEDED` | Vertical scroll bar policy. |

## FxSplitView

Styled resizable split view — a `SplitPane` wrapper. Divider positioning and item management remain fully accessible.

### Usage

```java
FxSplitView editor = FxSplitView.builder()
    .items(fileTree, codeArea)
    .dividerPositions(0.25)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `orientation(Orientation)` | `Orientation` | `HORIZONTAL` | Split direction. |
| `items(Node...)` | `Node[]` | empty | Split children. |
| `dividerPositions(double...)` | `double[]` | auto | Initial divider positions in `[0, 1]`. |

## FxResizablePane

Container whose single child can be resized along one edge by dragging a user-visible handle. Useful for sidebars and drawer-style panels that aren't full overlays.

### Usage

```java
FxResizablePane sidebar = FxResizablePane.builder()
    .child(sidebarContent)
    .side(Side.RIGHT)
    .extent(240).minExtent(120).maxExtent(480)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `child(Node)` | `Node` | none | Single child. |
| `side(Side)` | `Side` | `RIGHT` | Which edge hosts the drag handle. |
| `extent(double)` | `double` | `240` | Child size along the drag axis. |
| `minExtent(double)` | `double` | `80` | Minimum extent. |
| `maxExtent(double)` | `double` | `MAX_VALUE` | Maximum extent. |

## FxCollapse

Single-section collapsible — a clickable header row plus an expandable body slot. The chevron rotates via the `:expanded` pseudo-class.

### Usage

```java
FxCollapse details = FxCollapse.builder()
    .title("Details")
    .content(detailsBody)
    .expanded(false)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `title(String)` | `String` | `""` | Header text. |
| `content(Node)` | `Node` | none | Expandable body node. |
| `expanded(boolean)` | `boolean` | `false` | Initial expanded state. |

## FxAccordion

Vertical stack of `FxCollapse` sections. When `singleOpen` is `true`, expanding one section collapses the others.

### Usage

```java
FxAccordion faq = FxAccordion.builder()
    .sections(shipping, returns, contact)
    .singleOpen(true)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `sections(FxCollapse...)` | `FxCollapse[]` | empty | Section list. |
| `singleOpen(boolean)` | `boolean` | `true` | Enforce one-open-at-a-time. |

## FxStickyHeader

Pins its first child (the header) to the top of a host `ScrollPane`'s viewport while the rest of the content scrolls underneath.

### Usage

```java
FxStickyHeader sticky = FxStickyHeader.builder()
    .header(sectionTitle)
    .body(longContent)
    .build();
scrollPane.setContent(sticky);
sticky.attachTo(scrollPane);
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `header(Node)` | `Node` | none | Pinned header. |
| `body(Node)` | `Node` | none | Scrolling body. |
| `attachTo(ScrollPane)` | `ScrollPane` | none | Wires the header's translateY to the scroll pane's vvalue. |

## FxResponsive

Responsive container — swaps its visible child based on the current width and a set of named breakpoints. Picks the entry with the greatest `minWidth ≤ getWidth()`.

### Usage

```java
FxResponsive layout = FxResponsive.builder()
    .at("base", 0, mobileLayout)
    .at("md", 768, tabletLayout)
    .at("lg", 1024, desktopLayout)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `at(String, double, Node)` | name + minWidth + node | — | Register a breakpoint. |
