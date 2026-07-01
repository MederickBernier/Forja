# FxStepper

Horizontal multi-step progress indicator — numbered dots + optional labels connected by lines. Active and completed dots get `:active` / `:done` pseudo-classes.

## Usage

```java
FxStepper wizard = FxStepper.builder()
    .steps("Account", "Profile", "Review")
    .currentStep(1)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `steps(List<String>)` / `steps(String...)` | items | empty | Step captions. |
| `currentStep(int)` | `int` | `0` | Current step index. |
