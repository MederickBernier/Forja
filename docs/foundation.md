# Foundation

Everything above the component layer.

## Token layer

Forja's visual system is built on CSS custom properties (`-forja-*`) defined
in `base.css`. Every component reaches for these tokens instead of hardcoding
colors, radii, or spacing. Swap the token set and the whole library restyles.

| Token family | Sample tokens | Purpose |
|---|---|---|
| Accent | `-forja-accent`, `-forja-accent-hover`, `-forja-accent-focus`, `-forja-accent-tint` | Primary indigo action color + interaction states |
| Backgrounds | `-forja-bg-page`, `-forja-bg-surface`, `-forja-input-bg`, `-forja-input-bg-hover` | Layered surfaces |
| Text | `-forja-text-primary`, `-forja-text-muted` | Body and helper copy |
| Borders | `-forja-border-default`, `-forja-border-strong` | Component outlines |
| Status | `-forja-success`, `-forja-warning`, `-forja-danger`, `-forja-info` (+ `-tint` variants) | Semantic feedback |
| Sizing | `-forja-component-height-{compact,default,comfortable}` | Density scale |
| Typography | `-forja-font-size-{small,body,heading,display,mono}` | Type ramp |

## Theme system

Three themes ship: `LIGHT`, `DARK`, and `SYSTEM` (resolves to `LIGHT` today).
Toggle via `Forja.applyTheme(scene, theme)` or drop an `FxThemeToggle`.

```java
Forja.applyTheme(scene, FxTheme.DARK);
```

## Builder bases

Two abstract builders sit under every component and provide the shared
fluent surface (id, style classes, tooltip, disabled, visible, user data).

**`FxComponentBuilder<T extends Control, B>`** — for JavaFX `Control`
subclasses (buttons, inputs, tables). Exposes the full surface including
tooltip and `FxStyle` per-instance overrides.

**`FxNodeBuilder<T extends Node, B>`** — relaxed base for `Region`/`Shape`
components that aren't `Control`s (icons, spacers, layout primitives).
Skips the Control-only knobs.

Every concrete builder ends `build()` with `applyBase(instance)`. Extend
either base to add your own Forja-style components with the same
consistency:

```java
public static class Builder extends FxComponentBuilder<MyControl, Builder> {

    private String text = "";

    public Builder text(String text) { this.text = text; return this; }

    @Override
    public MyControl build() {
        MyControl c = new MyControl(text);
        applyBase(c);
        return c;
    }
}
```

## Semantic variants

`SemanticVariant` is a cross-cutting enum used by any component that carries
status color: `DEFAULT`, `MUTED`, `ACCENT`, `SUCCESS`, `WARNING`, `DANGER`,
`INFO`. Toasts, alerts, badges, chips, progress bars, and status pages all
accept it.

```java
FxAlert.builder()
    .variant(SemanticVariant.SUCCESS)
    .title("Saved")
    .build();
```

## Per-instance overrides

`FxStyle` / `FxStyleBuilder` (in `io.forja.styling`) generate a scoped CSS
stylesheet targeted at a single component instance. Use it when a caller
needs to nudge just one component without a global rule.

```java
FxButton btn = FxButton.builder()
    .text("Emphasize me")
    .style(FxStyle.builder()
        .backgroundColor("#7C3AED")
        .textFill("white")
        .build())
    .build();
```

## Overlay layer

The overlay-host wraps a scene once on first use (via `OverlayHost.getLayer`)
and hosts all Forja overlays: `FxToast`, `FxDialog`, `FxDrawer`,
`FxBottomSheet`, `FxLightbox`, `FxCommandPalette`. Custom overlay content can
be posted the same way:

```java
OverlayHost.show(scene, myCustomOverlayNode);
OverlayHost.dismiss(myCustomOverlayNode);
```

## Test support

`ForjaTestSupport` (test scope) ships three headless-TestFX helpers used
across the internal test suite and safe to reuse in your own tests:

- `<T> T onFx(Callable<T> body)` — runs a Callable on the FX thread and
  waits for events to drain.
- `assertHasPseudoClass(Node, String)` / `assertLacksPseudoClass(Node, String)`
  — assert visual-state pseudo-class membership without querying CSS.
