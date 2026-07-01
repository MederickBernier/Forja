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
| `FxComponentBuilder` base (for Controls) | ✅ |
| `FxNodeBuilder` base (for non-Control Nodes) | ✅ |
| `FxStyle` / `FxStyleBuilder` per-instance overrides | ✅ |
| `ForjaStylesheets` scoped stylesheet registry | ✅ |
| `SemanticVariant` shared token enum (default/muted/accent/success/warning/danger/info) | ✅ |
| `ForjaTestSupport` headless TestFX helpers | ✅ |
| Bundled fonts (Inter, JetBrains Mono) | ⏳ |
| Javadoc site & Maven Central release | ⏳ |

### Package layout

```
io.forja.builder        — FxComponentBuilder (for Controls),
                          FxNodeBuilder (for non-Control Nodes)
io.forja.styling        — FxStyle, FxStyleBuilder
io.forja.util           — ForjaStylesheets
io.forja.tokens         — SemanticVariant, SpacingSize
                          (cross-cutting token enums)
io.forja.components.<category>.<fxComponent>/
                        — each component lives in its own subpackage
                          containing the component class, its Skin (if any),
                          and any component-local enums.
```

Component packages:

```
typography/        fxLabel · fxText · fxLink · fxBlockquote · fxKbd · fxCode
buttonsAndActions/ fxButton · fxIconButton
layout/            fxSeparator · fxSpacer · fxStack · fxRow · fxContainer · fxSection · fxCard
feedbackAndStatus/ fxBadge · fxChip · fxStatusDot
dataDisplay/       fxAvatar · fxAvatarGroup
utilities/         fxIcon
inputs/            fxTextField · fxTextArea · fxPasswordField · fxNumberField · fxSearchField · fxAutocomplete · fxTagInput · fxOTPInput · fxMaskedInput · fxCodeEditor · fxRichTextEditor · fxMarkdownEditor · fxJsonEditor
                   (+ shared InputVariant)
dateAndTime/       fxDatePicker · fxTimePicker · fxDateTimePicker · fxDateRangePicker · fxCalendar · fxMiniCalendar
selection/         fxCheckBox · fxCheckGroup · fxRadioGroup · fxSwitch · fxSlider · fxComboBox · fxMultiSelect · fxRangeSlider · fxRating · fxColorPicker
buttonsAndActions/ fxButton · fxIconButton · fxToggleButton · fxButtonGroup · fxCopyButton · fxSplitButton · fxMenuButton
feedbackAndStatus/ fxBadge · fxChip · fxStatusDot · fxProgressBar · fxProgressCircle · fxSkeleton · fxAlert · fxBanner · fxToast · fxNotificationCenter · fxEmptyState · fxErrorState · fxResultPage
overlays/          OverlayHost · fxTooltip · fxDialog · fxConfirmDialog · fxPopover · fxHoverCard · fxDrawer · fxBottomSheet · fxLightbox · fxFormDialog
layout/            (P0) + fxGrid · fxFlex · fxAspectRatio · fxScrollArea · fxSplitView · fxResizablePane · fxCollapse · fxAccordion · fxStickyHeader · fxResponsive
dataDisplay/       fxAvatar · fxAvatarGroup · fxList · fxTable · fxTree · fxTreeTable · fxImage · fxStat · fxDescriptionList
utilities/         fxIcon · fxThemeToggle · fxKeybindingHint · fxSearchHighlight · fxScrollSpy · fxPortal · fxFocusTrap
typography/        (P0) + fxBulletList
```

All category folders now populated.

```
charts/            fxLineChart · fxBarChart · fxAreaChart · fxScatterPlot · fxPieChart · fxDonutChart · fxSparkline · fxGauge · fxHeatmap · fxRadarChart
media/             fxVideoPlayer · fxAudioPlayer · fxImageGallery · fxWaveform
```

```
validation/        fxValidator (+ Rule) · fxFormField · fxErrorSummary · fxForm
```

```
navigation/        fxTabs · fxVerticalTabs · fxAppBar · fxSidebar · fxSidebarNav · fxBreadcrumbs · fxStepper · fxPagination · fxMenuBar · fxContextMenu · fxDropdownMenu · fxAnchorNav
```

```
fileAndMediaInput/ fxFileChooser · fxFileDropzone · fxImagePicker
```

### Build roadmap

Build order across the roster. Each tier ships as a milestone.

**v0.2-alpha — P0 (trivial wrappers, high-usage) ✅ shipped**
FxLink · FxText · FxBlockquote · FxKbd · FxCode · FxBadge · FxChip · FxStatusDot · FxSpacer · FxAvatar · FxAvatarGroup · FxStack · FxRow · FxContainer · FxSection

**v0.2 — P1 (form basics + feedback)**
FxCard · FxTextField · FxTextArea · FxPasswordField · FxNumberField · FxSearchField · FxCheckBox · FxCheckGroup · FxRadioGroup · FxSwitch · FxComboBox · FxSlider · FxProgressBar · FxProgressCircle · FxAlert · FxBanner · FxTooltip · FxDialog · FxConfirmDialog · FxToast · FxSkeleton · FxToggleButton · FxButtonGroup / FxSegmentedControl · FxCopyButton

**v0.3 — P2 (composites: navigation, overlays, data display, layout, advanced inputs, validation, utilities) ✅ shipped**
Navigation, overlays built on the FxToast overlay-host, data tables/lists/trees, advanced layout primitives, validation infrastructure, theme/keybinding/scroll utilities. ~60 items — see categories below.

**v1.0 candidates — P3 (heavy / specialized)**
Rich text & code editors (need RichTextFX), virtualized lists, charts (need chart lib choice), media players, complex data-display (Kanban, Masonry, Carousel, Timeline, Command palette). Opt-in by champion use case, not roadmap.

### Components

#### Typography
- ✅ FxLabel
- ✅ FxLink
- ✅ FxText
- ✅ FxBlockquote
- ✅ FxKbd
- ✅ FxCode
- ✅ FxBulletList (ordered / unordered)

#### Buttons & Actions
- ✅ FxButton
- ✅ FxIconButton
- ✅ FxToggleButton
- ✅ FxCopyButton
- ✅ FxButtonGroup / FxSegmentedControl
- ✅ FxSplitButton
- ✅ FxMenuButton

#### Inputs
- ✅ FxTextField
- ✅ FxTextArea
- ✅ FxPasswordField
- ✅ FxNumberField
- ✅ FxSearchField
- ✅ FxAutocomplete
- ✅ FxTagInput
- ✅ FxOTPInput
- ✅ FxMaskedInput
- ✅ FxRichTextEditor
- ✅ FxMarkdownEditor
- ✅ FxCodeEditor
- ✅ FxJsonEditor

#### Selection
- ✅ FxCheckBox
- ✅ FxCheckGroup
- ✅ FxRadioGroup
- ✅ FxSwitch
- ✅ FxComboBox
- ✅ FxSlider
- ✅ FxMultiSelect
- ✅ FxRangeSlider
- ✅ FxRating
- ✅ FxColorPicker

#### Date & Time
- ✅ FxDatePicker
- ✅ FxTimePicker
- ✅ FxDateTimePicker
- ✅ FxDateRangePicker
- ✅ FxCalendar
- ✅ FxMiniCalendar

#### Files & Media Input
- ✅ FxFileChooser
- ✅ FxFileDropzone
- ✅ FxImagePicker
- ⏳ FxImageCropper · P3

#### Layout
- ✅ FxSeparator
- ✅ FxSpacer
- ✅ FxStack (vertical flow)
- ✅ FxRow (horizontal flow)
- ✅ FxContainer (max-width centered)
- ✅ FxSection
- ✅ FxCard
- ✅ FxGrid
- ✅ FxFlex
- ✅ FxAspectRatio
- ✅ FxScrollArea
- ✅ FxSplitView (resizable split)
- ✅ FxResizablePane
- ✅ FxCollapse / FxAccordion
- ✅ FxStickyHeader
- ✅ FxResponsive (breakpoint helpers)

#### Navigation
- ✅ FxTabs
- ✅ FxVerticalTabs
- ✅ FxAppBar / FxNavbar
- ✅ FxSidebar
- ✅ FxSidebarNav
- ✅ FxBreadcrumbs
- ✅ FxStepper
- ✅ FxPagination
- ✅ FxMenuBar
- ✅ FxContextMenu
- ✅ FxDropdownMenu
- ✅ FxAnchorNav (in-page jumps)
- ⏳ FxCommandPalette · P3

#### Feedback & Status
- ✅ FxBadge
- ✅ FxChip
- ✅ FxStatusDot
- ✅ FxProgressBar
- ✅ FxProgressCircle
- ✅ FxAlert
- ✅ FxBanner
- ✅ FxToast (rides on OverlayHost)
- ✅ FxSkeleton
- ✅ FxNotificationCenter
- ✅ FxEmptyState
- ✅ FxErrorState
- ✅ FxResultPage (success / failure summary)

#### Overlays
- ✅ FxTooltip
- ✅ FxDialog
- ✅ FxConfirmDialog
- ✅ FxFormDialog
- ✅ FxPopover
- ✅ FxHoverCard
- ✅ FxDrawer
- ✅ FxBottomSheet
- ✅ FxLightbox

#### Data Display
- ✅ FxAvatar
- ✅ FxAvatarGroup
- ✅ FxList
- ✅ FxTable
- ✅ FxTree
- ✅ FxTreeTable
- ✅ FxImage (with fallback / loading)
- ✅ FxStat (KPI card)
- ✅ FxDescriptionList
- ⏳ FxDataGrid (sortable, filterable, paginated) · P3
- ⏳ FxVirtualList · P3
- ⏳ FxTimeline · P3
- ⏳ FxKanbanBoard · P3
- ⏳ FxMasonry · P3
- ⏳ FxCarousel · P3

#### Charts
- ✅ FxLineChart
- ✅ FxBarChart
- ✅ FxPieChart
- ✅ FxDonutChart
- ✅ FxAreaChart
- ✅ FxScatterPlot
- ✅ FxSparkline
- ✅ FxGauge
- ✅ FxHeatmap
- ✅ FxRadarChart

#### Media
- ✅ FxVideoPlayer
- ✅ FxAudioPlayer
- ✅ FxImageGallery
- ✅ FxWaveform

#### Validation
- ✅ FxForm (declarative form binding)
- ✅ FxFormField (label + control + error slot)
- ✅ FxValidator (rule engine)
- ✅ FxErrorSummary

#### Utilities
- ✅ FxIcon (icon glyph wrapper — bundles Feather pack)
- ✅ FxThemeToggle
- ✅ FxKeybindingHint
- ✅ FxSearchHighlight
- ✅ FxScrollSpy
- ✅ FxPortal (render-elsewhere helper)
- ✅ FxFocusTrap

## License

MIT
