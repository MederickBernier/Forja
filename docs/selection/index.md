# Selection

Choice controls — pick one, pick many, pick a number, pick a color.

| Component | Summary |
|---|---|
| [FxCheckBox](#fxcheckbox) | Styled checkbox with optional indeterminate state. |
| [FxCheckGroup](#fxcheckgroup) | Multi-select group of checkboxes with observable selected-values list. |
| [FxRadioGroup](#fxradiogroup) | Single-select radio group with observable value property. |
| [FxSwitch](#fxswitch) | Pill-style on/off toggle switch. |
| [FxSlider](#fxslider) | Numeric range slider with optional value label. |
| [FxComboBox](#fxcombobox) | Styled dropdown combo box. |
| [FxMultiSelect](#fxmultiselect) | Multi-select combo with checkable items and N-selected summary. |
| [FxRangeSlider](#fxrangeslider) | Two-thumb slider for numeric range selection. |
| [FxRating](#fxrating) | Star-rating input with hover preview. |
| [FxColorPicker](#fxcolorpicker) | Styled color picker. |

## FxCheckBox

Styled checkbox built on `javafx.scene.control.CheckBox`. Optional indeterminate state.

### Usage

```java
FxCheckBox agree = FxCheckBox.builder()
    .text("I agree to the terms")
    .selected(false)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Label. |
| `selected(boolean)` | `boolean` | `false` | Initial selected state. |
| `indeterminate(boolean)` | `boolean` | `false` | Initial indeterminate state. |
| `allowIndeterminate(boolean)` | `boolean` | `false` | Allow the indeterminate state. |
| `onAction(EventHandler<ActionEvent>)` | handler | none | Click callback. |

## FxCheckGroup

Multi-select group of `FxCheckBox` items. Exposes an observable list of selected values and an optional heading label.

### Usage

```java
FxCheckGroup toppings = FxCheckGroup.builder()
    .label("Toppings")
    .items("Cheese", "Pepperoni", "Mushrooms")
    .selected("Cheese")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<String>)` / `items(String...)` | items | empty | Option labels. |
| `selected(List<String>)` / `selected(String...)` | items | empty | Initially selected values. |
| `orientation(Orientation)` | `Orientation` | `VERTICAL` | Row or column layout. |
| `label(String)` | `String` | `""` | Optional heading label. |

## FxRadioGroup

Single-select radio group. Radio buttons share a `ToggleGroup`; the current selection is exposed as a `String` value.

### Usage

```java
FxRadioGroup size = FxRadioGroup.builder()
    .label("Size")
    .items("S", "M", "L")
    .value("M")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<String>)` / `items(String...)` | items | empty | Option labels. |
| `value(String)` | `String` | `null` | Initially selected value. |
| `orientation(Orientation)` | `Orientation` | `VERTICAL` | Row or column. |
| `label(String)` | `String` | `""` | Optional heading label. |

## FxSwitch

Pill-style on/off toggle switch. Clicking the track flips the state; the knob slides via CSS transitions on the `:selected` pseudo-class. Keyboard: SPACE toggles when focused.

### Usage

```java
FxSwitch dark = FxSwitch.builder()
    .text("Dark mode")
    .selected(true)
    .onChange(v -> theme.setDark(v))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Optional label. |
| `selected(boolean)` | `boolean` | `false` | Initial state. |
| `onChange(OnChange)` | callback | none | Fires when selected changes. |

## FxSlider

Numeric range slider with optional value label + prefix/suffix. Value label auto-formats with the configured `decimals`.

### Usage

```java
FxSlider volume = FxSlider.builder()
    .min(0).max(100)
    .value(60).step(5)
    .suffix("%")
    .showValue(true)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `min(double)` | `double` | `0` | Minimum value. |
| `max(double)` | `double` | `100` | Maximum value. |
| `value(double)` | `double` | `0` | Initial value. |
| `step(double)` | `double` | `1` | Block increment. |
| `decimals(int)` | `int` | `0` | Fractional digits for value label. |
| `prefix(String)` | `String` | `""` | Value-label prefix. |
| `suffix(String)` | `String` | `""` | Value-label suffix. |
| `showValue(boolean)` | `boolean` | `false` | Show trailing value label. |
| `showTickMarks(boolean)` | `boolean` | `false` | Show tick marks. |
| `showTickLabels(boolean)` | `boolean` | `false` | Show tick labels. |
| `majorTickUnit(double)` | `double` | `25` | Major tick unit. |

## FxComboBox

Styled dropdown combo box built on `javafx.scene.control.ComboBox<T>`.

### Usage

```java
FxComboBox<String> country = FxComboBox.<String>builder()
    .items("Canada", "USA", "Mexico")
    .value("Canada")
    .promptText("Select country")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` / `items(T...)` | items | empty | Options. |
| `value(T)` | `T` | `null` | Initial value. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `editable(boolean)` | `boolean` | `false` | Editable text field. |
| `visibleRowCount(int)` | `int` | `10` | Dropdown row cap. |

## FxMultiSelect

Multi-select combo — a summary row + a dropdown popup of checkable items. Summary shows the prompt when empty, a single value when one is picked, or `N selected` for multiple.

### Usage

```java
FxMultiSelect ms = FxMultiSelect.builder()
    .items("bug", "docs", "enhancement")
    .selected("docs")
    .promptText("Labels")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<String>)` / `items(String...)` | items | empty | Options. |
| `selected(List<String>)` / `selected(String...)` | items | empty | Initially selected values. |
| `promptText(String)` | `String` | `""` | Summary shown when nothing selected. |

## FxRangeSlider

Two-thumb range slider. Implemented as two overlaid `Slider`s; the low thumb is clamped to `≤ highValue` and the high thumb to `≥ lowValue`.

### Usage

```java
FxRangeSlider price = FxRangeSlider.builder()
    .min(0).max(1000)
    .lowValue(100).highValue(500)
    .step(10)
    .prefix("$")
    .showValues(true)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `min(double)` | `double` | `0` | Minimum. |
| `max(double)` | `double` | `100` | Maximum. |
| `lowValue(double)` | `double` | `0` | Low thumb value. |
| `highValue(double)` | `double` | `100` | High thumb value. |
| `step(double)` | `double` | `1` | Block increment. |
| `showValues(boolean)` | `boolean` | `false` | Show trailing "low – high" label. |
| `prefix(String)` | `String` | `""` | Value-label prefix. |
| `suffix(String)` | `String` | `""` | Value-label suffix. |

## FxRating

Star-rating input. Click a star to set the rating; hover previews highlight up to that position. Set `readonly` to display without interaction.

### Usage

```java
FxRating stars = FxRating.builder()
    .max(5)
    .value(3)
    .onChange(v -> logger.info("rated {}", v))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `max(int)` | `int` | `5` | Star count. |
| `value(int)` | `int` | `0` | Initial rating, clamped to `[0, max]`. |
| `readonly(boolean)` | `boolean` | `false` | Disable interaction. |
| `onChange(OnChange)` | callback | none | Fires when value changes. |

## FxColorPicker

Styled color picker built on `javafx.scene.control.ColorPicker`. Everything native remains accessible.

### Usage

```java
FxColorPicker cp = FxColorPicker.builder()
    .value(Color.web("#4F46E5"))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(Color)` | `Color` | `Color.BLACK` | Initial color. |
