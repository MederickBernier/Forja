package io.forja.demo.categories;

import io.forja.components.media.fxAudioPlayer.FxAudioPlayer;
import io.forja.components.media.fxImageGallery.FxImageGallery;
import io.forja.components.media.fxVideoPlayer.FxVideoPlayer;
import io.forja.components.media.fxWaveform.FxWaveform;
import javafx.scene.Node;
import javafx.scene.Scene;

public class MediaDemo implements CategoryDemo {

    @Override public String key() { return "media"; }
    @Override public String title() { return "Media"; }
    @Override public String icon() { return "fth-play-circle"; }

    @Override
    public Node build(Scene scene) {
        // ponytail: no real media bundled — placeholder source
        return Demo.category(title(),
                Demo.block("FxAudioPlayer", "Audio player with transport controls.",
                        FxAudioPlayer.builder().url("https://example.com/sample.mp3").build()),

                Demo.block("FxVideoPlayer", "Video player with a fixed fit size.",
                        FxVideoPlayer.builder().url("https://example.com/sample.mp4")
                                .fitWidth(320).fitHeight(180).build()),

                Demo.block("FxImageGallery", "Thumbnail gallery over a list of image URLs.",
                        FxImageGallery.builder()
                                .images("https://example.com/a.jpg", "https://example.com/b.jpg")
                                .thumbnailSize(80).build()),

                Demo.block("FxWaveform", "Audio waveform rendered from sample amplitudes.",
                        FxWaveform.builder()
                                .samples(new double[] { 0.1, 0.5, 0.3, 0.9, 0.4, 0.7, 0.2, 0.6, 0.8, 0.3 })
                                .width(240).height(64).build()));
    }
}
