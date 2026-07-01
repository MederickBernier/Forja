# FxAudioPlayer

Compact audio player — play/pause + seek slider + elapsed/duration label. Requires the `javafx-media` module at runtime.

## Usage

```java
FxAudioPlayer ap = FxAudioPlayer.builder()
    .url("file:/path/to/track.mp3")
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `url(String)` | `String` | `""` | Media URL. |
| `autoPlay(boolean)` | `boolean` | `false` | Start playing on build. |
