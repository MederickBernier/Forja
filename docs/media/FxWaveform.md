# FxWaveform

Waveform display for PCM audio. Reads WAV/AU/AIFF via `javax.sound.sampled`, decimates to the canvas width, paints a symmetric peaks polyline.

MP3 and other compressed formats require an external decoder — pass raw PCM bytes via `setPcmBytes` or normalized samples via `setSamples` when decoding externally.

## Usage

```java
FxWaveform w = FxWaveform.builder()
    .file(new File("track.wav"))
    .stroke(Color.web("#4F46E5"))
    .build();
```

## Builder methods

| Method | Type | Default | Description |
|---|---|---|---|
| `file(File)` | `File` | none | Load a WAV/AU/AIFF file. |
| `url(URL)` | `URL` | none | Load from a URL. |
| `samples(double[])` | `double[]` | none | Pre-normalized `[-1, 1]` samples. |
| `stroke(Color)` | `Color` | indigo | Line color. |
| `width(double)` | `double` | `320` | Preferred width. |
| `height(double)` | `double` | `80` | Preferred height. |
