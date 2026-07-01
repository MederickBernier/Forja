package io.forja.components.media.fxAudioPlayer;

import io.forja.builder.FxNodeBuilder;
import io.forja.components.buttonsAndActions.fxIconButton.FxIconButton;
import io.forja.components.selection.fxSlider.FxSlider;
import io.forja.components.typography.fxLabel.FxLabel;
import io.forja.components.typography.fxLabel.LabelVariant;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * A compact audio player — play/pause + seek slider + elapsed/duration.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxAudioPlayer ap = FxAudioPlayer.builder()
 *          .url("file:/path/to/track.mp3")
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxAudioPlayer extends HBox {

    private final FxIconButton playPauseBtn = FxIconButton.builder().icon("fth-play").build();
    private final FxSlider seekSlider = FxSlider.builder().min(0).max(1).value(0).build();
    private final FxLabel timeLabel = new FxLabel("0:00 / 0:00", LabelVariant.SMALL);
    private final StringProperty url = new SimpleStringProperty(this, "url", "");
    private MediaPlayer player;

    /**
     * Creates an empty {@code FxAudioPlayer}.
     */
    public FxAudioPlayer() {
        super();
        getStyleClass().add("forja-audio-player");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(8);
        HBox.setHgrow(seekSlider, Priority.ALWAYS);
        timeLabel.setMuted(true);
        getChildren().addAll(playPauseBtn, seekSlider, timeLabel);

        playPauseBtn.setOnAction(e -> togglePlay());
        seekSlider.getSlider().valueProperty().addListener((obs, o, v) -> {
            if (player != null && seekSlider.getSlider().isValueChanging()) {
                player.seek(Duration.seconds(v.doubleValue()));
            }
        });
        url.addListener((obs, o, v) -> reloadMedia(v));
    }

    private void reloadMedia(String u) {
        if (player != null) { player.dispose(); player = null; }
        if (u == null || u.isEmpty()) return;
        try {
            Media media = new Media(u);
            player = new MediaPlayer(media);
            player.currentTimeProperty().addListener((obs, o, v) -> updateTime());
            player.setOnReady(() -> {
                seekSlider.setMax(player.getTotalDuration().toSeconds());
                updateTime();
            });
            player.setOnPlaying(() -> playPauseBtn.setIconLiteral("fth-pause"));
            player.setOnPaused(() -> playPauseBtn.setIconLiteral("fth-play"));
            player.setOnStopped(() -> playPauseBtn.setIconLiteral("fth-play"));
        } catch (Exception ignored) {}
    }

    private void updateTime() {
        if (player == null) return;
        double cur = player.getCurrentTime().toSeconds();
        double total = player.getTotalDuration() == null ? 0 : player.getTotalDuration().toSeconds();
        seekSlider.setValue(cur);
        timeLabel.setText(fmt(cur) + " / " + fmt(total));
    }

    private static String fmt(double sec) {
        if (sec < 0 || Double.isNaN(sec) || Double.isInfinite(sec)) sec = 0;
        int total = (int) sec;
        return String.format("%d:%02d", total / 60, total % 60);
    }

    /** Toggles play/pause. */
    public void togglePlay() {
        if (player == null) return;
        if (player.getStatus() == MediaPlayer.Status.PLAYING) player.pause();
        else player.play();
    }

    /** Starts playing. */
    public void play() { if (player != null) player.play(); }
    /** Pauses playing. */
    public void pause() { if (player != null) player.pause(); }
    /** Stops playing. */
    public void stop() { if (player != null) player.stop(); }

    /** Returns the underlying media player, or {@code null}. */
    public MediaPlayer getMediaPlayer() { return player; }
    /** Returns the play/pause button. */
    public FxIconButton getPlayPauseButton() { return playPauseBtn; }
    /** Returns the seek slider. */
    public FxSlider getSeekSlider() { return seekSlider; }
    /** Returns the time label. */
    public FxLabel getTimeLabel() { return timeLabel; }

    /** Returns the URL property. */
    public StringProperty urlProperty() { return url; }
    /** Returns the current URL. */
    public String getUrl() { return url.get(); }
    /** Sets the audio URL. */
    public void setUrl(String v) { url.set(v == null ? "" : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxAudioPlayer}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxAudioPlayer}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>url — empty</li>
     *   <li>autoPlay — {@code false}</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxAudioPlayer, Builder> {

        private String url = "";
        private boolean autoPlay = false;

        public Builder url(String url) { this.url = url == null ? "" : url; return this; }
        public Builder autoPlay(boolean autoPlay) { this.autoPlay = autoPlay; return this; }

        @Override
        public FxAudioPlayer build() {
            FxAudioPlayer ap = new FxAudioPlayer();
            ap.setUrl(url);
            if (autoPlay) ap.play();
            applyBase(ap);
            return ap;
        }
    }
}
