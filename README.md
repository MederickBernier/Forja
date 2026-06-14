# Forja

> Shape what already works.

A modern JavaFX UI component library for Java 8 and above.

Forja sits on top of JavaFX — not around it. It leverages the existing scene graph,
CSS engine, skin architecture, and property binding system, and adds the one thing
JavaFX has always been missing: a design system, a fluent API, and components that
look like they were made in this decade.

## Why Forja

JavaFX is capable, battle-tested, and running in production across hospitals,
factories, and enterprise systems that will never see Electron. It doesn't need
to be replaced. It needs to be shaped.

Forja provides:
- **Modern design** — zinc-based neutral palette, indigo accent, clean typography,
  subtle interaction states. No drop shadows, no gradients, no noise.
- **Fluent builder API** — construct components with a chainable, type-safe builder.
  Static factory and constructor-style facades included for convenience.
- **Zero ceremony** — one dependency, one method call. Your app looks good before
  you write a single style rule.
- **Java 8+** — works where your codebase already lives.
- **Still JavaFX** — every component is a real JavaFX control. FXML, SceneBuilder,
  accessibility tools, existing code — all compatible, no escape hatches needed.

## Quick start

```xml
<dependency>
    <groupId>io.forja</groupId>
    <artifactId>forja-components</artifactId>
    <version>0.1.0</version>
</dependency>
```

```java
@Override
public void start(Stage stage) {
    Scene scene = new Scene(buildRoot());
    Forja.install(scene);
    stage.setScene(scene);
    stage.show();
}
```

```java
Button save = FxButton.builder()
    .text("Save")
    .variant(ButtonVariant.PRIMARY)
    .onAction(e -> handleSave())
    .build();
```

## Status

Early development. Not yet published to Maven Central.

**Status legend:** ✅ available · 🔨 in progress · ⏳ planned

**Priority legend:** `P0` trivial wrappers, build first · `P1` standard form/feedback components, build next · `P2` composites & secondary components · `P3` heavy/specialized (may need new deps or new architecture)

### Foundation

| Piece | Status |
|-------|--------|
| Token layer (palette, typography, spacing, radii, semantic colors) | ✅ |
| Theme system (light / dark / system) | ✅ |
| `Forja` installer | ✅ |
| `FxComponentBuilder` base builder | ✅ |
| `FxStyle` / `FxStyleBuilder` per-instance overrides | ✅ |
| `ForjaStylesheets` scoped stylesheet registry | ✅ |
| Bundled fonts (Inter, JetBrains Mono) | ⏳ |
| Javadoc site & Maven Central release | ⏳ |

### Build roadmap

Build order across the roster. Each tier ships as a milestone.

**v0.2-alpha — P0 (trivial wrappers, high-usage)**
FxLink · FxText · FxBlockquote · FxKbd · FxCode · FxBadge · FxChip · FxStatusDot · FxSpacer · FxAvatar · FxAvatarGroup · FxStack · FxRow · FxContainer · FxSection

**v0.2 — P1 (form basics + feedback)**
FxCard · FxTextField · FxTextArea · FxPasswordField · FxNumberField · FxSearchField · FxCheckBox · FxCheckGroup · FxRadioGroup · FxSwitch · FxComboBox · FxSlider · FxProgressBar · FxProgressCircle · FxAlert · FxBanner · FxTooltip · FxDialog · FxConfirmDialog · FxToast · FxSkeleton · FxToggleButton · FxButtonGroup / FxSegmentedControl · FxCopyButton

**v0.3 — P2 (composites: navigation, overlays, data display, layout, advanced inputs, validation, utilities)**
Navigation, overlays built on the FxToast overlay-host, data tables/lists/trees, advanced layout primitives, validation infrastructure, theme/keybinding/scroll utilities. ~60 items — see categories below.

**v1.0 candidates — P3 (heavy / specialized)**
Rich text & code editors (need RichTextFX), virtualized lists, charts (need chart lib choice), media players, complex data-display (Kanban, Masonry, Carousel, Timeline, Command palette). Opt-in by champion use case, not roadmap.

### Components

#### Typography
- ✅ FxLabel
- ✅ FxLink
- ⏳ FxText · P0
- ⏳ FxBlockquote · P0
- ⏳ FxKbd · P0
- ⏳ FxCode · P0
- ⏳ FxBulletList (ordered / unordered) · P2
- ⏳ FxHeading · P3 — overlaps FxLabel(HEADING); may be dropped

#### Buttons & Actions
- ✅ FxButton
- ✅ FxIconButton
- ⏳ FxToggleButton · P1
- ⏳ FxCopyButton · P1
- ⏳ FxButtonGroup / FxSegmentedControl · P1
- ⏳ FxSplitButton · P2
- ⏳ FxMenuButton · P2

#### Inputs
- ⏳ FxTextField · P1
- ⏳ FxTextArea · P1
- ⏳ FxPasswordField · P1
- ⏳ FxNumberField · P1
- ⏳ FxSearchField · P1
- ⏳ FxAutocomplete · P2
- ⏳ FxTagInput · P2
- ⏳ FxOTPInput · P2
- ⏳ FxMaskedInput · P2
- ⏳ FxRichTextEditor · P3
- ⏳ FxMarkdownEditor · P3
- ⏳ FxCodeEditor · P3
- ⏳ FxJsonEditor · P3

#### Selection
- ⏳ FxCheckBox · P1
- ⏳ FxCheckGroup · P1
- ⏳ FxRadioGroup · P1
- ⏳ FxSwitch · P1
- ⏳ FxComboBox · P1
- ⏳ FxSlider · P1
- ⏳ FxMultiSelect · P2
- ⏳ FxRangeSlider · P2
- ⏳ FxRating · P2
- ⏳ FxColorPicker · P2

#### Date & Time
- ⏳ FxDatePicker · P2
- ⏳ FxTimePicker · P2
- ⏳ FxDateTimePicker · P2
- ⏳ FxDateRangePicker · P2
- ⏳ FxCalendar · P2
- ⏳ FxMiniCalendar · P2

#### Files & Media Input
- ⏳ FxFileChooser · P2
- ⏳ FxFileDropzone · P2
- ⏳ FxImagePicker · P2
- ⏳ FxImageCropper · P3

#### Layout
- ✅ FxSeparator
- ⏳ FxSpacer · P0
- ⏳ FxStack (vertical flow) · P0
- ⏳ FxRow (horizontal flow) · P0
- ⏳ FxContainer (max-width centered) · P0
- ⏳ FxSection · P0
- ⏳ FxCard · P1
- ⏳ FxGrid · P2
- ⏳ FxFlex · P2
- ⏳ FxAspectRatio · P2
- ⏳ FxScrollArea · P2
- ⏳ FxSplitView (resizable split) · P2
- ⏳ FxResizablePane · P2
- ⏳ FxCollapse / FxAccordion · P2
- ⏳ FxStickyHeader · P2
- ⏳ FxResponsive (breakpoint helpers) · P2
- ❌ FxDivider — superseded by `FxSeparator`; will be removed from the roster

#### Navigation
- ⏳ FxTabs · P2
- ⏳ FxVerticalTabs · P2
- ⏳ FxAppBar / FxNavbar · P2
- ⏳ FxSidebar · P2
- ⏳ FxSidebarNav · P2
- ⏳ FxBreadcrumbs · P2
- ⏳ FxStepper · P2
- ⏳ FxPagination · P2
- ⏳ FxMenuBar · P2
- ⏳ FxContextMenu · P2
- ⏳ FxDropdownMenu · P2
- ⏳ FxAnchorNav (in-page jumps) · P2
- ⏳ FxCommandPalette · P3

#### Feedback & Status
- ⏳ FxBadge · P0
- ⏳ FxChip · P0
- ⏳ FxStatusDot · P0
- ⏳ FxProgressBar · P1
- ⏳ FxProgressCircle · P1
- ⏳ FxAlert · P1
- ⏳ FxBanner · P1
- ⏳ FxToast · P1 (load-bearing: establishes overlay-host infra)
- ⏳ FxSkeleton · P1
- ⏳ FxNotificationCenter · P2
- ⏳ FxEmptyState · P2
- ⏳ FxErrorState · P2
- ⏳ FxResultPage (success / failure summary) · P2

#### Overlays
- ⏳ FxTooltip · P1
- ⏳ FxDialog · P1
- ⏳ FxConfirmDialog · P1
- ⏳ FxFormDialog · P2
- ⏳ FxPopover · P2
- ⏳ FxHoverCard · P2
- ⏳ FxDrawer · P2
- ⏳ FxBottomSheet · P2
- ⏳ FxLightbox · P2

#### Data Display
- ⏳ FxAvatar · P0
- ⏳ FxAvatarGroup · P0
- ⏳ FxList · P2
- ⏳ FxTable · P2
- ⏳ FxTree · P2
- ⏳ FxTreeTable · P2
- ⏳ FxImage (with fallback / loading) · P2
- ⏳ FxStat (KPI card) · P2
- ⏳ FxDescriptionList · P2
- ⏳ FxDataGrid (sortable, filterable, paginated) · P3
- ⏳ FxVirtualList · P3
- ⏳ FxTimeline · P3
- ⏳ FxKanbanBoard · P3
- ⏳ FxMasonry · P3
- ⏳ FxCarousel · P3

#### Charts
- ⏳ FxLineChart · P3
- ⏳ FxBarChart · P3
- ⏳ FxPieChart · P3
- ⏳ FxDonutChart · P3
- ⏳ FxAreaChart · P3
- ⏳ FxScatterPlot · P3
- ⏳ FxSparkline · P3
- ⏳ FxGauge · P3
- ⏳ FxHeatmap · P3
- ⏳ FxRadarChart · P3

#### Media
- ⏳ FxVideoPlayer · P3
- ⏳ FxAudioPlayer · P3
- ⏳ FxImageGallery · P3
- ⏳ FxWaveform · P3

#### Validation
- ⏳ FxForm (declarative form binding) · P2
- ⏳ FxFormField (label + control + error slot) · P2
- ⏳ FxValidator (rule engine) · P2
- ⏳ FxErrorSummary · P2

#### Utilities
- ✅ FxIcon (icon glyph wrapper — bundles Feather pack)
- ⏳ FxThemeToggle · P2
- ⏳ FxKeybindingHint · P2
- ⏳ FxSearchHighlight · P2
- ⏳ FxScrollSpy · P2
- ⏳ FxPortal (render-elsewhere helper) · P2
- ⏳ FxFocusTrap · P2

## License

MIT
