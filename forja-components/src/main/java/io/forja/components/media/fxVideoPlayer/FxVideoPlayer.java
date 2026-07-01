package io.forja.components.media.fxVideoPlayer;

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
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * A styled video player — {@link MediaView} + play/pause, seek slider, and
 * elapsed/duration label.
 *
 * <p>Builder usage:</p>
 * <pre>
 *     {@code
 *      FxVideoPlayer vp = FxVideoPlayer.builder()
 *          .url("file:/path/to/video.mp4")
 *          .autoPlay(false)
 *          .build();
 *     }
 * </pre>
 *
 * @see Builder
 */
public class FxVideoPlayer extends VBox {

    private final MediaView mediaView = new MediaView();
    private final FxIconButton playPauseBtn = FxIconButton.builder().icon("fth-play").build();
    private final FxSlider seekSlider = FxSlider.builder().min(0).max(1).value(0).build();
    private final FxLabel timeLabel = new FxLabel("0:00 / 0:00", LabelVariant.SMALL);
    private final HBox controls = new HBox(8, playPauseBtn, seekSlider, timeLabel);
    private final StringProperty url = new SimpleStringProperty(this, "url", "");
    private MediaPlayer player;

    /**
     * Creates an empty {@code FxVideoPlayer}.
     */
    public FxVideoPlayer() {
        super();
        getStyleClass().add("forja-video-player");
        setSpacing(6);
        mediaView.setPreserveRatio(true);
        controls.getStyleClass().add("forja-video-player-controls");
        controls.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(seekSlider, Priority.ALWAYS);
        timeLabel.setMuted(true);
        getChildren().addAll(mediaView, controls);

        playPauseBtn.setOnAction(e -> togglePlay());
        seekSlider.getSlider().setOnMousePressed(e -> {
            if (player != null) player.seek(Duration.seconds(seekSlider.getValue()));
        });
        seekSlider.getSlider().valueProperty().addListener((obs, o, v) -> {
            if (player != null && seekSlider.getSlider().isValueChanging()) {
                player.seek(Duration.seconds(v.doubleValue()));
            }
        });

        url.addListener((obs, o, v) -> reloadMedia(v));
    }

    private void reloadMedia(String u) {
        if (player != null) { player.dispose(); player = null; }
        if (u == null || u.isEmpty()) { mediaView.setMediaPlayer(null); return; }
        try {
            Media media = new Media(u);
            player = new MediaPlayer(media);
            mediaView.setMediaPlayer(player);
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
        int m = total / 60;
        int s = total % 60;
        return String.format("%d:%02d", m, s);
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
    /** Returns the media view. */
    public MediaView getMediaView() { return mediaView; }
    /** Returns the seek slider. */
    public FxSlider getSeekSlider() { return seekSlider; }
    /** Returns the play/pause button. */
    public FxIconButton getPlayPauseButton() { return playPauseBtn; }
    /** Returns the time label. */
    public FxLabel getTimeLabel() { return timeLabel; }
    /** Returns the controls row. */
    public HBox getControls() { return controls; }

    /** Returns the URL property. */
    public StringProperty urlProperty() { return url; }
    /** Returns the current URL. */
    public String getUrl() { return url.get(); }
    /** Sets the media URL (reloads player). */
    public void setUrl(String v) { url.set(v == null ? "" : v); }

    /**
     * Returns a new {@link Builder} for constructing an {@code FxVideoPlayer}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for constructing an {@link FxVideoPlayer}.
     *
     * <p>Defaults:
     * <ul>
     *   <li>url — empty</li>
     *   <li>autoPlay — {@code false}</li>
     *   <li>fitWidth / fitHeight — auto (unset)</li>
     * </ul>
     */
    public static class Builder extends FxNodeBuilder<FxVideoPlayer, Builder> {

        private String url = "";
        private boolean autoPlay = false;
        private double fitWidth = 0;
        private double fitHeight = 0;

        public Builder url(String url) { this.url = url == null ? "" : url; return this; }
        public Builder autoPlay(boolean autoPlay) { this.autoPlay = autoPlay; return this; }
        public Builder fitWidth(double fitWidth) { this.fitWidth = fitWidth; return this; }
        public Builder fitHeight(double fitHeight) { this.fitHeight = fitHeight; return this; }

        @Override
        public FxVideoPlayer build() {
            FxVideoPlayer vp = new FxVideoPlayer();
            if (fitWidth > 0) vp.getMediaView().setFitWidth(fitWidth);
            if (fitHeight > 0) vp.getMediaView().setFitHeight(fitHeight);
            vp.setUrl(url);
            if (autoPlay) vp.play();
            applyBase(vp);
            return vp;
        }
    }
}
