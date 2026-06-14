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

Legend: ✅ available · 🔨 in progress · ⏳ planned

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

### Components

#### Typography
- ✅ FxLabel
- ⏳ FxText
- ⏳ FxHeading
- ⏳ FxCode
- ⏳ FxKbd
- ⏳ FxLink
- ⏳ FxBlockquote
- ⏳ FxList (ordered / unordered)

#### Buttons & Actions
- ✅ FxButton
- ✅ FxIconButton
- ⏳ FxToggleButton
- ⏳ FxSplitButton
- ⏳ FxButtonGroup (segmented)
- ⏳ FxMenuButton
- ⏳ FxCopyButton

#### Inputs
- ⏳ FxTextField
- ⏳ FxTextArea
- ⏳ FxPasswordField
- ⏳ FxNumberField
- ⏳ FxMaskedInput
- ⏳ FxSearchField
- ⏳ FxOTPInput
- ⏳ FxAutocomplete
- ⏳ FxTagInput
- ⏳ FxRichTextEditor
- ⏳ FxMarkdownEditor
- ⏳ FxCodeEditor
- ⏳ FxJsonEditor

#### Selection
- ⏳ FxCheckBox
- ⏳ FxCheckGroup
- ⏳ FxRadioGroup
- ⏳ FxSwitch
- ⏳ FxSlider
- ⏳ FxRangeSlider
- ⏳ FxComboBox
- ⏳ FxMultiSelect
- ⏳ FxSegmentedControl
- ⏳ FxRating
- ⏳ FxColorPicker

#### Date & Time
- ⏳ FxDatePicker
- ⏳ FxTimePicker
- ⏳ FxDateTimePicker
- ⏳ FxDateRangePicker
- ⏳ FxCalendar
- ⏳ FxMiniCalendar

#### Files & Media Input
- ⏳ FxFileChooser
- ⏳ FxFileDropzone
- ⏳ FxImagePicker
- ⏳ FxImageCropper

#### Layout
- ⏳ FxStack (vertical flow)
- ⏳ FxRow (horizontal flow)
- ⏳ FxGrid
- ⏳ FxFlex
- ⏳ FxContainer (max-width centered)
- ⏳ FxSection
- ⏳ FxCard
- ✅ FxSeparator
- ⏳ FxDivider
- ⏳ FxSpacer
- ⏳ FxAspectRatio
- ⏳ FxScrollArea
- ⏳ FxSplitView (resizable split)
- ⏳ FxResizablePane
- ⏳ FxCollapse / FxAccordion
- ⏳ FxStickyHeader
- ⏳ FxResponsive (breakpoint helpers)

#### Navigation
- ⏳ FxAppBar / FxNavbar
- ⏳ FxSidebar
- ⏳ FxSidebarNav
- ⏳ FxBreadcrumbs
- ⏳ FxTabs
- ⏳ FxVerticalTabs
- ⏳ FxStepper
- ⏳ FxPagination
- ⏳ FxMenuBar
- ⏳ FxContextMenu
- ⏳ FxDropdownMenu
- ⏳ FxCommandPalette
- ⏳ FxAnchorNav (in-page jumps)

#### Feedback & Status
- ⏳ FxAlert
- ⏳ FxBanner
- ⏳ FxToast
- ⏳ FxNotificationCenter
- ⏳ FxProgressBar
- ⏳ FxProgressCircle
- ⏳ FxSkeleton
- ⏳ FxBadge
- ⏳ FxChip
- ⏳ FxStatusDot
- ⏳ FxEmptyState
- ⏳ FxErrorState
- ⏳ FxResultPage (success / failure summary)

#### Overlays
- ⏳ FxDialog
- ⏳ FxConfirmDialog
- ⏳ FxFormDialog
- ⏳ FxDrawer
- ⏳ FxBottomSheet
- ⏳ FxPopover
- ⏳ FxTooltip
- ⏳ FxHoverCard
- ⏳ FxLightbox

#### Data Display
- ⏳ FxTable
- ⏳ FxDataGrid (sortable, filterable, paginated)
- ⏳ FxList
- ⏳ FxVirtualList
- ⏳ FxTree
- ⏳ FxTreeTable
- ⏳ FxTimeline
- ⏳ FxStat (KPI card)
- ⏳ FxAvatar
- ⏳ FxAvatarGroup
- ⏳ FxImage (with fallback / loading)
- ⏳ FxKanbanBoard
- ⏳ FxMasonry
- ⏳ FxCarousel
- ⏳ FxDescriptionList

#### Charts
- ⏳ FxLineChart
- ⏳ FxBarChart
- ⏳ FxPieChart
- ⏳ FxDonutChart
- ⏳ FxAreaChart
- ⏳ FxScatterPlot
- ⏳ FxSparkline
- ⏳ FxGauge
- ⏳ FxHeatmap
- ⏳ FxRadarChart

#### Media
- ⏳ FxVideoPlayer
- ⏳ FxAudioPlayer
- ⏳ FxImageGallery
- ⏳ FxWaveform

#### Validation
- ⏳ FxForm (declarative form binding)
- ⏳ FxFormField (label + control + error slot)
- ⏳ FxValidator (rule engine)
- ⏳ FxErrorSummary

#### Utilities
- ⏳ FxThemeToggle
- ⏳ FxKeybindingHint
- ⏳ FxSearchHighlight
- ⏳ FxScrollSpy
- ✅ FxIcon (icon glyph wrapper — bundles Feather pack)
- ⏳ FxPortal (render-elsewhere helper)
- ⏳ FxFocusTrap

## License

MIT
