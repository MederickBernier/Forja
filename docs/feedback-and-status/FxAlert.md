# FxAlert

Inline colored notice with icon and dismiss action. Icon glyph is auto-selected per variant.

## Usage

```java
FxAlert saved = FxAlert.builder()
    .variant(SemanticVariant.SUCCESS)
    .title("Saved")
    .description("Your changes have been synced.")
    .dismissible(true)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `variant(SemanticVariant)` | `SemanticVariant` | `INFO` | Color + auto-icon. |
| `title(String)` | `String` | `""` | Alert title. |
| `description(String)` | `String` | `""` | Optional description line. |
| `dismissible(boolean)` | `boolean` | `false` | Show the × close icon. |
| `leadingIcon(Node)` | `Node` | auto | Override the auto-selected icon. |
| `leadingIcon(String)` | `String` | auto | Sugar taking an Ikonli literal. |
| `onDismiss(OnDismiss)` | callback | none | Fired when the alert is dismissed. |
