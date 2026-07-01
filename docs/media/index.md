# Media

Video, audio, image gallery, waveform.

Requires the `javafx-media` module at runtime for video/audio playback. Waveform reads PCM via `javax.sound.sampled` (WAV/AU/AIFF supported without extra codecs).

| Component | Summary |
|---|---|
| [FxVideoPlayer](#fxvideoplayer) | Video player with play/pause, seek slider, and elapsed/duration label. |
| [FxAudioPlayer](#fxaudioplayer) | Compact audio player with play/pause and progress. |
| [FxImageGallery](#fximagegallery) | Thumbnail grid that opens `FxLightbox` on click. |
| [FxWaveform](#fxwaveform) | Canvas-drawn PCM waveform display. |

## FxVideoPlayer

Video player — `MediaView` plus a play/pause button, a seek slider, and an elapsed/duration label. Requires the `javafx-media` module at runtime.

### Usage

```java
FxVideoPlayer vp = FxVideoPlayer.builder()
    .url("file:/path/to/video.mp4")
    .autoPlay(false)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `url(String)` | `String` | `""` | Media URL. |
| `autoPlay(boolean)` | `boolean` | `false` | Start playing on build. |
| `fitWidth(double)` | `double` | auto | Sets `MediaView.fitWidth`. |
| `fitHeight(double)` | `double` | auto | Sets `MediaView.fitHeight`. |

## FxAudioPlayer

Compact audio player — play/pause + seek slider + elapsed/duration label. Requires the `javafx-media` module at runtime.

### Usage

```java
FxAudioPlayer ap = FxAudioPlayer.builder()
    .url("file:/path/to/track.mp3")
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `url(String)` | `String` | `""` | Media URL. |
| `autoPlay(boolean)` | `boolean` | `false` | Start playing on build. |

## FxImageGallery

Grid of thumbnails that opens a full-screen `FxLightbox` on click. Backed by `FxImage` thumbnails that share the same URL list with the lightbox.

### Usage

```java
FxImageGallery gallery = FxImageGallery.builder()
    .images("file:/a.jpg", "file:/b.jpg", "file:/c.jpg")
    .thumbnailSize(120)
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `images(List<String>)` / `images(String...)` | items | empty | Image URLs. |
| `thumbnailSize(double)` | `double` | `120` | Thumbnail edge length in px. |

## FxWaveform

Waveform display for PCM audio. Reads WAV/AU/AIFF via `javax.sound.sampled`, decimates to the canvas width, paints a symmetric peaks polyline.

MP3 and other compressed formats require an external decoder — pass raw PCM bytes via `setPcmBytes` or normalized samples via `setSamples` when decoding externally.

### Usage

```java
FxWaveform w = FxWaveform.builder()
    .file(new File("track.wav"))
    .stroke(Color.web("#4F46E5"))
    .build();
```

### Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `file(File)` | `File` | none | Load a WAV/AU/AIFF file. |
| `url(URL)` | `URL` | none | Load from a URL. |
| `samples(double[])` | `double[]` | none | Pre-normalized `[-1, 1]` samples. |
| `stroke(Color)` | `Color` | indigo | Line color. |
| `width(double)` | `double` | `320` | Preferred width. |
| `height(double)` | `double` | `80` | Preferred height. |
