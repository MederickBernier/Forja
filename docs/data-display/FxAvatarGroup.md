# FxAvatarGroup

Stacked avatar row with an overflow badge showing the hidden count.

## Usage

```java
FxAvatarGroup team = FxAvatarGroup.builder()
    .avatars(a1, a2, a3, a4, a5, a6)
    .maxVisible(3)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `avatars(FxAvatar...)` | `FxAvatar[]` | empty | Avatars to stack. |
| `maxVisible(int)` | `int` | `4` | Show at most N avatars; the rest become the `+N` badge. |
