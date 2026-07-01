package io.forja.components.media.fxWaveform;

import io.forja.builder.FxNodeBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;

/**
 * A waveform display for PCM audio. Reads WAV/AU/AIFF via
 * {@link javax.sound.sampled.AudioSystem}, decimates the sample buffer to
 * the canvas width, and paints a symmetric peaks polyline.
 *
 * <p>MP3 and other compressed formats are not supported without an
 * additional decoder — pass raw PCM bytes via {@link #setSamples} if you
 * decode externally.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxWaveform w = FxWaveform.builder()
 *          .file(new File("track.wav"))
 *          .stroke(Color.web("#4F46E5"))
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxWaveform extends Region {

    private double[] samples = new double[0];
    private final ObjectProperty<Color> stroke = new SimpleObjectProperty<>(this, "stroke", Color.web("#4F46E5"));
    private final Canvas canvas = new Canvas(320, 80);

    /**
     * Creates an empty {@code FxWaveform}.
     */
    public FxWaveform() {
        super();
        getStyleClass().add("forja-waveform");
        getChildren().add(canvas);
        setPrefSize(320, 80);
        widthProperty().addListener((obs, o, v) -> { canvas.setWidth(v.doubleValue()); redraw(); });
        heightProperty().addListener((obs, o, v) -> { canvas.setHeight(v.doubleValue()); redraw(); });
        stroke.addListener((obs, o, v) -> redraw());
    }

    /** Loads samples from a {@link File} (WAV/AU/AIFF). */
    public void setFile(File file) {
        if (file == null) { samples = new double[0]; redraw(); return; }
        try (AudioInputStream in = AudioSystem.getAudioInputStream(file)) {
            samples = readSamples(in);
            redraw();
        } catch (Exception ignored) { samples = new double[0]; redraw(); }
    }

    /** Loads samples from a URL (WAV/AU/AIFF). */
    public void setUrl(URL url) {
        if (url == null) { samples = new double[0]; redraw(); return; }
        try (AudioInputStream in = AudioSystem.getAudioInputStream(url)) {
            samples = readSamples(in);
            redraw();
        } catch (Exception ignored) { samples = new double[0]; redraw(); }
    }

    /** Loads samples from raw PCM bytes (16-bit signed, little-endian, mono). */
    public void setPcmBytes(byte[] bytes) {
        if (bytes == null || bytes.length < 2) { samples = new double[0]; redraw(); return; }
        try (AudioInputStream in = new AudioInputStream(new ByteArrayInputStream(bytes),
                new AudioFormat(44100, 16, 1, true, false), bytes.length / 2)) {
            samples = readSamples(in);
            redraw();
        } catch (Exception ignored) { samples = new double[0]; redraw(); }
    }

    /** Loads normalized {@code [-1, 1]} samples directly. */
    public void setSamples(double[] samples) {
        this.samples = samples == null ? new double[0] : samples;
        redraw();
    }

    private double[] readSamples(AudioInputStream in) throws Exception {
        AudioFormat fmt = in.getFormat();
        AudioFormat pcm = new AudioFormat(fmt.getSampleRate(), 16, 1, true, false);
        AudioInputStream conv = AudioSystem.getAudioInputStream(pcm, in);
        byte[] buf = new byte[64 * 1024];
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        int r;
        while ((r = conv.read(buf)) > 0) out.write(buf, 0, r);
        byte[] raw = out.toByteArray();
        int n = raw.length / 2;
        double[] out2 = new double[n];
        for (int i = 0; i < n; i++) {
            int lo = raw[i * 2] & 0xFF;
            int hi = raw[i * 2 + 1];
            short s = (short) ((hi << 8) | lo);
            out2[i] = s / 32768.0;
        }
        return out2;
    }

    private void redraw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        g.clearRect(0, 0, w, h);
        if (samples.length == 0 || w <= 0 || h <= 0) return;
        int cols = (int) w;
        int chunk = Math.max(1, samples.length / cols);
        double mid = h / 2.0;
        g.setStroke(getStroke());
        g.setLineWidth(1);
        for (int i = 0; i < cols && i * chunk < samples.length; i++) {
            double lo = 0, hi = 0;
            int end = Math.min(samples.length, (i + 1) * chunk);
            for (int j = i * chunk; j < end; j++) {
                double v = samples[j];
                if (v < lo) lo = v;
                if (v > hi) hi = v;
            }
            double y1 = mid + lo * mid;
            double y2 = mid + hi * mid;
            g.strokeLine(i, y1, i, y2);
        }
    }

    /** Returns the stroke property. */
    public ObjectProperty<Color> strokeProperty() { return stroke; }
    /** Returns the stroke color. */
    public Color getStroke() { return stroke.get(); }
    /** Sets the stroke color. */
    public void setStroke(Color v) { stroke.set(v == null ? Color.web("#4F46E5") : v); }

    /** Returns the samples snapshot. */
    public double[] getSamples() { return samples; }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxWaveform}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxWaveform}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>samples / file / url — none</li>
     *   <li>stroke — accent indigo</li>
     *   <li>width / height — {@code 320} / {@code 80}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxWaveform, Builder> {

        private File file;
        private URL url;
        private double[] samples;
        private Color stroke = Color.web("#4F46E5");
        private double width = 320;
        private double height = 80;

        public Builder file(File file) { this.file = file; return this; }
        public Builder url(URL url) { this.url = url; return this; }
        public Builder samples(double[] samples) { this.samples = samples; return this; }
        public Builder stroke(Color stroke) { this.stroke = stroke == null ? Color.web("#4F46E5") : stroke; return this; }
        public Builder width(double width) { this.width = width; return this; }
        public Builder height(double height) { this.height = height; return this; }

        @Override
        public FxWaveform build() {
            FxWaveform w = new FxWaveform();
            w.setStroke(stroke);
            w.setPrefSize(width, height);
            if (samples != null) w.setSamples(samples);
            else if (file != null) w.setFile(file);
            else if (url != null) w.setUrl(url);
            applyBase(w);
            return w;
        }
    }
}
