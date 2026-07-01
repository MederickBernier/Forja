# FxFocusTrap

Non-visual utility that traps keyboard focus inside a root node's subtree. Intercepts `TAB` / `SHIFT+TAB` and cycles focus among focusable descendants.

Typical use: overlay dialogs — install on the dialog's panel so users can't tab out into the underlying app.

## Usage

```java
FxFocusTrap trap = FxFocusTrap.builder()
    .root(dialogPanel)
    .build();
// autoInstall=true (default) wires the listener immediately.

// later
trap.uninstall();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `root(Parent)` | `Parent` | `null` | Root subtree to trap focus in. |
| `autoInstall(boolean)` | `boolean` | `true` | Attach key-listener at build time. |
