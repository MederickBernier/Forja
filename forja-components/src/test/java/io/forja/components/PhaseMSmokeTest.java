package io.forja.components;

import io.forja.components.media.fxAudioPlayer.FxAudioPlayer;
import io.forja.components.media.fxImageGallery.FxImageGallery;
import io.forja.components.media.fxVideoPlayer.FxVideoPlayer;
import io.forja.components.media.fxWaveform.FxWaveform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static io.forja.testsupport.ForjaTestSupport.onFx;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class PhaseMSmokeTest {

    @Start
    void start(Stage stage) {}

    @Test
    void videoPlayer_defaultShape() {
        FxVideoPlayer vp = onFx(() -> FxVideoPlayer.builder().build());
        assertNotNull(vp.getMediaView());
        assertNotNull(vp.getPlayPauseButton());
        assertNotNull(vp.getSeekSlider());
        assertTrue(vp.getStyleClass().contains("forja-video-player"));
    }

    @Test
    void videoPlayer_urlPropertyDefaultsEmpty() {
        // Skip actual media loading — headless test env lacks the native
        // media backend. Just verify the property is wired.
        FxVideoPlayer vp = onFx(() -> FxVideoPlayer.builder().build());
        assertEquals("", vp.getUrl());
    }

    @Test
    void audioPlayer_defaultShape() {
        FxAudioPlayer ap = onFx(() -> FxAudioPlayer.builder().build());
        assertNotNull(ap.getSeekSlider());
        assertNotNull(ap.getPlayPauseButton());
        assertTrue(ap.getStyleClass().contains("forja-audio-player"));
    }

    @Test
    void imageGallery_registersImagesAndLightbox() {
        FxImageGallery g = onFx(() -> FxImageGallery.builder()
                .images("a.jpg", "b.jpg", "c.jpg")
                .thumbnailSize(80)
                .build());
        assertEquals(3, g.getImages().size());
        assertEquals(3, g.getLightbox().getImages().size());
        assertEquals(3, g.getChildren().size());
        assertEquals(80.0, g.getThumbnailSize());
    }

    @Test
    void waveform_acceptsRawSamples() {
        double[] samples = new double[8000];
        for (int i = 0; i < samples.length; i++) samples[i] = Math.sin(i * 0.02);
        FxWaveform w = onFx(() -> FxWaveform.builder().samples(samples).stroke(Color.RED).build());
        assertEquals(samples.length, w.getSamples().length);
        assertEquals(Color.RED, w.getStroke());
    }

    @Test
    void waveform_emptyByDefault() {
        FxWaveform w = onFx(() -> FxWaveform.builder().build());
        assertEquals(0, w.getSamples().length);
    }
}
