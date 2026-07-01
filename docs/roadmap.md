# Roadmap

Every component in the README roster shipped:

- **P0 tier** — trivial wrappers (14 items)
- **P1 tier** — form basics + feedback (24 items)
- **P2 tier** — composites, navigation, overlays, data display, validation, utilities (57 items)
- **P3 tier** — editors, charts, media, virtualization, kanban, command palette (28 items)

Total: **135 components** across 14 categories, 428 unit + smoke tests.

## Foundation status

| Piece | Status |
|---|---|
| Token layer (palette, typography, spacing, radii, semantic colors) | ✅ |
| Theme system (light / dark / system) | ✅ |
| `Forja` installer | ✅ |
| Builder bases (`FxComponentBuilder`, `FxNodeBuilder`) | ✅ |
| `FxStyle` per-instance overrides | ✅ |
| `SemanticVariant` shared token enum | ✅ |
| `ForjaTestSupport` headless TestFX helpers | ✅ |
| CSS backlog (all shipped components styled) | ✅ |
| Bundled fonts (Inter, JetBrains Mono) | ⏳ |
| Maven Central release (plugins configured) | 🔨 setup complete, publish pending credentials |

## Next milestones

- **v0.2** — publish `0.1.0` to Maven Central once GPG key + Sonatype tokens land
- **v0.3** — bundle Inter + JetBrains Mono fonts as classpath resources
- **v0.4** — screenshot gallery per component (this doc site)
- **v1.0** — first stable API freeze
