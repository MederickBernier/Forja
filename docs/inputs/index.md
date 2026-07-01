# Inputs

Text-entry components. Every field carries a shared `InputVariant` (`DEFAULT` / `ERROR` / `SUCCESS`) and helper/error label slots.

| Component | Summary |
|---|---|
| [FxTextField](#fxtextfield) | Text input with icons, helper/error text, and validation-state variants. |
| [FxTextArea](#fxtextarea) | Multi-line text input with auto-resize and validation. |
| [FxPasswordField](#fxpasswordfield) | Masked input with optional reveal-toggle. |
| [FxNumberField](#fxnumberfield) | Numeric input with min/max clamp, steppers, prefix/suffix. |
| [FxSearchField](#fxsearchfield) | Text input preset with search icon and clear button. |
| [FxAutocomplete](#fxautocomplete) | Text input with a filtered suggestion popup. |
| [FxTagInput](#fxtaginput) | Chip-based tag entry with add-on-Enter and remove-with-backspace. |
| [FxOTPInput](#fxotpinput) | Row of single-character boxes for one-time codes. |
| [FxMaskedInput](#fxmaskedinput) | Text input with a fixed input mask (`###-###`, `AAA-####`). |
| [FxCodeEditor](#fxcodeeditor) | Syntax-highlightable code editor built on RichTextFX. |
| [FxRichTextEditor](#fxrichtexteditor) | Rich-text editor with bold/italic/underline toolbar. |
| [FxMarkdownEditor](#fxmarkdowneditor) | Split source + live-rendered markdown preview. |
| [FxJsonEditor](#fxjsoneditor) | Code editor preset with a JSON syntax highlighter. |

## FxTextField

Styled text input with optional leading/trailing icons, helper text, and validation-state variants. Setting `errorText` to a non-empty value automatically flips the variant to `ERROR`; clearing it does not auto-revert.

### Usage

```java
FxTextField email = FxTextField.builder()
    .promptText("name@example.com")
    .leadingIcon("fth-mail")
    .helperText("We'll never share your email.")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial text. |
| `promptText(String)` | `String` | `""` | Placeholder shown when empty. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | `DEFAULT` / `ERROR` / `SUCCESS`. |
| `leadingIcon(Node)` | `Node` | none | Leading slot node. |
| `leadingIcon(String)` | `String` | none | Sugar taking an Ikonli literal. |
| `trailingIcon(Node)` | `Node` | none | Trailing slot node. |
| `trailingIcon(String)` | `String` | none | Sugar taking an Ikonli literal. |
| `helperText(String)` | `String` | `""` | Helper line under the field. |
| `errorText(String)` | `String` | `""` | Error line; non-empty flips variant to `ERROR`. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |

## FxTextArea

Multi-line text input with validation-state variants and an optional auto-resize behavior. When `autoResize` is `true`, `prefRowCount` grows to fit the line count, clamped between `rows` and `maxRows`.

### Usage

```java
FxTextArea bio = FxTextArea.builder()
    .promptText("Tell us about yourself")
    .rows(4).maxRows(12)
    .autoResize(true)
    .helperText("Max 500 characters.")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial text. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line; non-empty flips variant to `ERROR`. |
| `autoResize(boolean)` | `boolean` | `false` | Grow/shrink `prefRowCount` with content. |
| `rows(int)` | `int` | `4` | Minimum row count. |
| `maxRows(int)` | `int` | `12` | Maximum row count. |
| `wrapText(boolean)` | `boolean` | `true` | Wrap long lines. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |

## FxPasswordField

Styled password input with an optional reveal-toggle that swaps between the masked `PasswordField` and a plain `TextField` view sharing the same text.

### Usage

```java
FxPasswordField password = FxPasswordField.builder()
    .promptText("••••••••")
    .leadingIcon("fth-lock")
    .revealable(true)
    .helperText("8+ characters with a number and symbol.")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial value. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `leadingIcon(Node)` / `leadingIcon(String)` | node / literal | none | Leading icon slot. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line; flips variant to `ERROR`. |
| `revealable(boolean)` | `boolean` | `false` | Show the eye toggle. |
| `revealed(boolean)` | `boolean` | `false` | Initially revealed (only honored when `revealable`). |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |

## FxNumberField

Numeric input with min/max clamp, up/down steppers, and optional prefix/suffix affixes. Typing is filtered by a regex admitting sign, digits, and configurable decimal places.

### Usage

```java
FxNumberField price = FxNumberField.builder()
    .value(9.99)
    .min(0.0).max(1000.0)
    .step(0.25).decimals(2)
    .prefix("$")
    .showSteppers(true)
    .helperText("Retail price, taxes excluded.")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `value(Double)` / `value(double)` | number | `null` | Initial value (`null` = empty). |
| `min(Double)` / `min(double)` | number | `null` | Inclusive minimum. |
| `max(Double)` / `max(double)` | number | `null` | Inclusive maximum. |
| `step(double)` | `double` | `1.0` | Increment/decrement step. |
| `decimals(int)` | `int` | `0` | Fractional-digit count for display + input filter. |
| `prefix(String)` | `String` | `""` | Prefix affix (e.g. `"$"`). |
| `suffix(String)` | `String` | `""` | Suffix affix (e.g. `"kg"`). |
| `showSteppers(boolean)` | `boolean` | `false` | Show up/down stepper column. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `leadingIcon(Node)` / `leadingIcon(String)` | node / literal | none | Leading icon slot. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line; flips variant to `ERROR`. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |

## FxSearchField

Search-flavored text field — `FxTextField` preset with a leading search icon and an optional clear (×) button that appears while the field has text.

### Usage

```java
FxSearchField search = FxSearchField.builder()
    .promptText("Search users")
    .clearable(true)
    .onSearch(q -> viewModel.query(q))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial text. |
| `promptText(String)` | `String` | `"Search"` | Placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |
| `clearable(boolean)` | `boolean` | `false` | Show × when text is present. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |
| `onSearch(OnSearch)` | callback | none | Fires on ENTER with the current text. |

## FxAutocomplete

Text input with a suggestion popup — `FxTextField` plus a `ListView` in a `Popup` anchored below. Filters candidates against the field's text via a stringifier (default: `Object::toString`).

### Usage

```java
FxAutocomplete<String> ac = FxAutocomplete.<String>builder()
    .items("apple", "apricot", "banana")
    .promptText("Fruit")
    .onSelect(fruit -> logger.info("chose {}", fruit))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `items(List<T>)` / `items(T...)` | items | empty | Candidate items. |
| `text(String)` | `String` | `""` | Initial text. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |
| `stringifier(Function<T, String>)` | fn | `Object::toString` | Renders + filters items. |
| `onSelect(OnSelect<T>)` | callback | none | Fires when a suggestion is chosen. |

## FxTagInput

Chip-based tag entry — a wrapping row of `FxChip` tags plus an inline text editor. Press `ENTER` to commit, `BACKSPACE` on empty to remove the last tag, click × on any chip to remove it.

### Usage

```java
FxTagInput tags = FxTagInput.builder()
    .tags("java", "javafx")
    .promptText("Add tag…")
    .onChange(list -> viewModel.setTags(list))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `tags(List<String>)` / `tags(String...)` | tags | empty | Initial tag list. |
| `promptText(String)` | `String` | `""` | Editor placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |
| `onChange(OnChange)` | callback | none | Fires when tag list changes. |

## FxOTPInput

One-time-code entry — a row of `length` single-character text boxes with auto-advance on type and clear-back on backspace.

### Usage

```java
FxOTPInput otp = FxOTPInput.builder()
    .length(6)
    .digitsOnly(true)
    .onComplete(code -> viewModel.verify(code))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `length(int)` | `int` | `6` | Number of boxes. |
| `digitsOnly(boolean)` | `boolean` | `true` | Restrict input to `[0-9]`. |
| `initial(String)` | `String` | `""` | Pre-fill values. |
| `onComplete(OnComplete)` | callback | none | Fires when every box is filled. |

## FxMaskedInput

Text input with a fixed input mask. Placeholder characters: `#` = digit, `A` = ASCII letter, `*` = any character. Any other mask character is a literal separator.

### Usage

```java
FxMaskedInput phone = FxMaskedInput.builder()
    .mask("###-###-####")
    .promptText("555-123-4567")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `mask(String)` | `String` | `""` | Mask pattern (`#`, `A`, `*`, literals). |
| `text(String)` | `String` | `""` | Initial value. |
| `promptText(String)` | `String` | `""` | Placeholder. |
| `variant(InputVariant)` | `InputVariant` | `DEFAULT` | Validation variant. |
| `helperText(String)` | `String` | `""` | Helper line. |
| `errorText(String)` | `String` | `""` | Error line. |

## FxCodeEditor

Code editor built on RichTextFX `CodeArea`. Optional line-number gutter, plus a highlighter callback (RichTextFX `StyleSpans<Collection<String>>` pattern) that re-tokenizes on text change.

### Usage

```java
FxCodeEditor ed = FxCodeEditor.builder()
    .text("int x = 42;")
    .showLineNumbers(true)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial source. |
| `showLineNumbers(boolean)` | `boolean` | `true` | Line-number gutter. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |
| `highlighter(Function<String, StyleSpans<Collection<String>>>)` | fn | none | Syntax highlighter callback. |

## FxRichTextEditor

Rich-text editor built on RichTextFX `InlineCssTextArea`. Ships a bold / italic / underline toolbar that applies inline CSS to the current selection.

### Usage

```java
FxRichTextEditor ed = FxRichTextEditor.builder()
    .text("Type here…")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial plain text. |
| `editable(boolean)` | `boolean` | `true` | Editable flag. |

## FxMarkdownEditor

Split source-editor + rendered-preview markdown editor. Preview renders a compact subset: `#`/`##`/`###` headings, blank-line paragraphs, `-` bullets, and inline `**bold**` / `*italic*` markers (strip-only).

### Usage

```java
FxMarkdownEditor md = FxMarkdownEditor.builder()
    .text("# Hello\n\nSome **bold** text.")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial markdown source. |
| `dividerPosition(double)` | `double` | `0.5` | Split divider position in `[0, 1]`. |

## FxJsonEditor

JSON-flavored `FxCodeEditor` — wraps a code editor preset with a regex-driven JSON tokenizer emitting CSS classes `json-key`, `json-string`, `json-number`, `json-boolean`, `json-null`, `json-punctuation`.

### Usage

```java
FxJsonEditor ed = FxJsonEditor.builder()
    .text("{\"name\":\"Forja\",\"version\":0.1}")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `text(String)` | `String` | `""` | Initial JSON source. |
