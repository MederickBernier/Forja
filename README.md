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

| Milestone | Status |
|-----------|--------|
| v0.1 — token layer, core components | 🔨 In progress |
| v0.2 — form controls, feedback | ⏳ Planned |
| v0.3 — navigation, overlays | ⏳ Planned |
| v0.4 — data display, validation system | ⏳ Planned |
| v1.0 — stable API, Maven Central | ⏳ Planned |

## License

MIT