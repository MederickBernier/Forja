# FxOTPInput

One-time-code entry — a row of `length` single-character text boxes with auto-advance on type and clear-back on backspace.

## Usage

```java
FxOTPInput otp = FxOTPInput.builder()
    .length(6)
    .digitsOnly(true)
    .onComplete(code -> viewModel.verify(code))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `length(int)` | `int` | `6` | Number of boxes. |
| `digitsOnly(boolean)` | `boolean` | `true` | Restrict input to `[0-9]`. |
| `initial(String)` | `String` | `""` | Pre-fill values. |
| `onComplete(OnComplete)` | callback | none | Fires when every box is filled. |
