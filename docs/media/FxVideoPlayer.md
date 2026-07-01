# FxVideoPlayer

Video player — `MediaView` plus a play/pause button, a seek slider, and an elapsed/duration label. Requires the `javafx-media` module at runtime.

## Usage

```java
FxVideoPlayer vp = FxVideoPlayer.builder()
    .url("file:/path/to/video.mp4")
    .autoPlay(false)
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `url(String)` | `String` | `""` | Media URL. |
| `autoPlay(boolean)` | `boolean` | `false` | Start playing on build. |
| `fitWidth(double)` | `double` | auto | Sets `MediaView.fitWidth`. |
| `fitHeight(double)` | `double` | auto | Sets `MediaView.fitHeight`. |
