# Data Display

Structured views over data.

| Component | Summary |
|---|---|
| [FxAvatar](#fxavatar) | Circular avatar with image, initials fallback, size, and status ring. |
| [FxAvatarGroup](#fxavatargroup) | Stacked avatar row with overflow badge. |
| [FxList](#fxlist) | Styled `ListView` wrapper. |
| [FxTable](#fxtable) | Styled `TableView` wrapper. |
| [FxTree](#fxtree) | Styled `TreeView` wrapper. |
| [FxTreeTable](#fxtreetable) | Styled `TreeTableView` wrapper. |
| [FxImage](#fximage) | ImageView with spinner-while-loading and fallback icon on error. |
| [FxStat](#fxstat) | KPI card with label, value, and colored trend delta. |
| [FxDescriptionList](#fxdescriptionlist) | Term / description grid for key-value data. |
| [FxDataGrid](#fxdatagrid) | Sortable, filterable, paginated table. |
| [FxVirtualList](#fxvirtuallist) | Fixed-height virtual scroller for large lists. |
| [FxTimeline](#fxtimeline) | Vertical event timeline with colored dots and cards. |
| [FxKanbanBoard](#fxkanbanboard) | Drag-and-drop kanban board with configurable columns. |
| [FxMasonry](#fxmasonry) | Greedy column-packing layout. |
| [FxCarousel](#fxcarousel) | Single-slide-at-a-time carousel with dots and optional auto-advance. |

## FxAvatar

Circular avatar with an image, an initials fallback, a size preset, and an optional status ring.

### Usage

```java
FxAvatar user = FxAvatar.builder()
    .imageUrl("https://…/me.jpg")
    .initials("MB")
    .size(AvatarSize.MD)
    .statusVariant(SemanticVariant.SUCCESS)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `imageUrl(String)` | `String` | none | Image URL. |
| `initials(String)` | `String` | `""` | Fallback initials when no image. |
| `size(AvatarSize)` | `AvatarSize` | `MD` | Size preset (`SM` / `MD` / `LG` / `XL`). |
| `statusVariant(SemanticVariant)` | `SemanticVariant` | none | Status ring color. |

## FxAvatarGroup

Stacked avatar row with an overflow badge showing the hidden count.

### Usage

```java
FxAvatarGroup team = FxAvatarGroup.builder()
    .avatars(a1, a2, a3, a4, a5, a6)
    .maxVisible(3)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `avatars(FxAvatar...)` | `FxAvatar[]` | empty | Avatars to stack. |
| `maxVisible(int)` | `int` | `4` | Show at most N avatars; the rest become the `+N` badge. |

## FxList

Styled `ListView` wrapper. All native cell-factory, selection, and binding APIs remain accessible.

### Usage

```java
FxList<String> tags = FxList.<String>builder()
    .items("bug", "enhancement", "docs")
    .selectionMode(SelectionMode.MULTIPLE)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` / `items(T...)` | items | empty | Cell items. |
| `selectionMode(SelectionMode)` | `SelectionMode` | `SINGLE` | Selection mode. |
| `editable(boolean)` | `boolean` | `false` | Editable flag. |

## FxTable

Styled `TableView` wrapper. Column construction, sorting, filtering, and selection APIs remain fully accessible.

### Usage

```java
TableColumn<User, String> nameCol = new TableColumn<>("Name");
nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

FxTable<User> users = FxTable.<User>builder()
    .items(userList)
    .columns(nameCol, emailCol)
    .selectionMode(SelectionMode.MULTIPLE)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` / `items(T...)` | items | empty | Row items. |
| `columns(TableColumn<T, ?>...)` | columns | empty | Table columns. |
| `selectionMode(SelectionMode)` | `SelectionMode` | `SINGLE` | Selection mode. |
| `editable(boolean)` | `boolean` | `false` | Editable flag. |

## FxTree

Styled `TreeView` wrapper.

### Usage

```java
TreeItem<String> root = new TreeItem<>("Files");
root.getChildren().add(new TreeItem<>("README.md"));

FxTree<String> tree = FxTree.<String>builder()
    .root(root)
    .showRoot(true)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `root(TreeItem<T>)` | `TreeItem<T>` | `null` | Root item. |
| `showRoot(boolean)` | `boolean` | `true` | Render the root. |
| `selectionMode(SelectionMode)` | `SelectionMode` | `SINGLE` | Selection mode. |
| `editable(boolean)` | `boolean` | `false` | Editable flag. |

## FxTreeTable

Styled `TreeTableView` wrapper.

### Usage

```java
FxTreeTable<File> tt = FxTreeTable.<File>builder()
    .root(rootItem)
    .columns(nameCol, sizeCol)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `root(TreeItem<T>)` | `TreeItem<T>` | `null` | Root item. |
| `showRoot(boolean)` | `boolean` | `true` | Render the root. |
| `columns(TreeTableColumn<T, ?>...)` | columns | empty | Table columns. |
| `selectionMode(SelectionMode)` | `SelectionMode` | `SINGLE` | Selection mode. |
| `editable(boolean)` | `boolean` | `false` | Editable flag. |

## FxImage

Styled `ImageView` wrapper that shows a spinner while the source loads and swaps to a fallback icon on error.

### Usage

```java
FxImage avatar = FxImage.builder()
    .url("https://…/avatar.png")
    .fitWidth(64).fitHeight(64)
    .fallbackIcon("fth-user")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `url(String)` | `String` | `""` | Image URL. |
| `fitWidth(double)` | `double` | `0` | Fit width. |
| `fitHeight(double)` | `double` | `0` | Fit height. |
| `preserveRatio(boolean)` | `boolean` | `true` | Preserve aspect ratio. |
| `fallbackIcon(String)` | `String` | `"fth-image"` | Icon shown on load failure. |

## FxStat

KPI card — muted label + large value + optional colored trend delta (`UP` / `DOWN` / `FLAT`).

### Usage

```java
FxStat mrr = FxStat.builder()
    .label("Monthly recurring revenue")
    .value("$42,180")
    .trend("+12.4% MoM", FxStat.Trend.UP)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `label(String)` | `String` | `""` | Muted label. |
| `value(String)` | `String` | `""` | Display value. |
| `trend(String, Trend)` | message + trend | none | Trend message + direction. |
| `trend(String, SemanticVariant)` | message + variant | none | Convenience that maps variant to trend (`SUCCESS` = `UP`, `DANGER` = `DOWN`, else `FLAT`). |

## FxDescriptionList

Term / description grid for key-value data. Two-column horizontal layout by default; vertical stacks term on top of description.

### Usage

```java
FxDescriptionList dl = FxDescriptionList.builder()
    .item("Name", "Mederick Bernier")
    .item("Email", "m@example.com")
    .item("Role", "Owner")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `orientation(Orientation)` | `Orientation` | `HORIZONTAL` | Column layout. |
| `item(String, String)` | term + description | — | Add a single row. |
| `items(Map<String, String>)` | map | empty | Replace items in one call. |

## FxDataGrid

Sortable, filterable, paginated table. Composes an `FxTable` with an `FxSearchField` filter and `FxPagination`. Filtering uses a caller-provided `BiPredicate<T, String>`.

### Usage

```java
FxDataGrid<User> grid = FxDataGrid.<User>builder()
    .items(users)
    .columns(nameCol, emailCol)
    .pageSize(20)
    .filter((row, q) -> row.name().toLowerCase().contains(q.toLowerCase()))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` / `items(T...)` | items | empty | Source rows. |
| `columns(TableColumn<T, ?>...)` | columns | empty | Table columns. |
| `pageSize(int)` | `int` | `20` | Rows per page. |
| `filter(BiPredicate<T, String>)` | fn | none | Filter predicate `(row, query) -> matches`. |

## FxVirtualList

Fixed-height virtual scroller. Renders only the visible slice of a large `ObservableList<T>` — a spacer keeps the `ScrollPane` sized to total item count while a moving `VBox` paints only the on-screen rows.

### Usage

```java
FxVirtualList<String> list = FxVirtualList.<String>builder()
    .items(oneMillionStrings)
    .itemHeight(28)
    .cellFactory(s -> new FxLabel(s))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` | items | empty | Backing list. |
| `itemHeight(int)` | `int` | `28` | Fixed row height in px. |
| `cellFactory(Function<T, Node>)` | fn | returns `null` | Renders one item. |

## FxTimeline

Vertical event timeline — each entry is a colored dot + connector line + title/subtitle card.

### Usage

```java
FxTimeline t = FxTimeline.builder()
    .entry("Deploy", "Pushed to prod at 09:32", SemanticVariant.SUCCESS)
    .entry("Rollback", "Reverted at 09:45", SemanticVariant.DANGER)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `entry(String, String, SemanticVariant)` | title + subtitle + variant | — | Add an entry. |

## FxKanbanBoard

Horizontal HBox of vertical columns holding draggable card nodes. Drag-and-drop is wired between columns automatically; the `onCardMoved` callback fires with source + target column keys + the moved node.

### Usage

```java
FxKanbanBoard board = FxKanbanBoard.builder()
    .column("todo", "To Do")
    .column("doing", "In Progress")
    .column("done", "Done")
    .card("todo", myCardNode)
    .onCardMoved((from, to, node) -> logger.info("{}: {} -> {}", node, from, to))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `column(String, String)` | key + title | — | Add a column. |
| `card(String, Node)` | columnKey + card | — | Add a card to a column. |
| `onCardMoved(OnCardMoved)` | callback | none | Fires on cross-column card move. |

## FxMasonry

Masonry layout — packs children into equal-width columns using a greedy "shortest column" algorithm. Each child's height is respected; columns grow independently.

### Usage

```java
FxMasonry m = FxMasonry.builder()
    .columns(3)
    .gap(8)
    .children(card1, card2, card3, card4, card5)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `columns(int)` | `int` | `3` | Column count. |
| `gap(double)` | `double` | `8` | Gap in px. |
| `children(Node...)` | `Node[]` | empty | Children to pack. |

## FxCarousel

Single-slide-at-a-time carousel — next/prev icon buttons + dot indicators + optional auto-advance.

### Usage

```java
FxCarousel c = FxCarousel.builder()
    .slides(imgA, imgB, imgC)
    .autoAdvance(3000)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `slides(Node...)` | `Node[]` | empty | Slide nodes. |
| `index(int)` | `int` | `0` | Initial slide index. |
| `autoAdvance(long)` | `long` | `0` | Auto-advance interval in ms (`0` = off). |
