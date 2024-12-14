package com.example.tetris_concept;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;

/**
 * MusicService is a background Android service responsible for managing and playing background music.
 * <p>
 * This service provides functionality for playing, pausing, resuming, and stopping music. It supports
 * the initialization of a {@link MediaPlayer} instance with looping audio playback, as well as advanced
 * features such as fading out music before stopping. The service is designed to handle lifecycle events
 * like starting, stopping, and destroying the service to ensure proper resource management.
 * </p>
 *
 * <p><strong>Key Features:</strong></p>
 * <ul>
 *   <li>Plays background music using the Android {@link MediaPlayer} API.</li>
 *   <li>Allows toggling music mute, pause, and resume states.</li>
 *   <li>Supports dynamic track changes based on {@link Intent} extras.</li>
 *   <li>Includes a fade-out mechanism for smooth music transitions.</li>
 *   <li>Ensures proper cleanup of resources during service destruction.</li>
 * </ul>
 *
 * <p><strong>Usage:</strong></p>
 * This service is designed to run in the background and is started using an {@link android.content.Intent}.
 * The service will continue running until explicitly stopped by the application.
 *
 * <p><strong>Notes:</strong></p>
 * <ul>
 *   <li>Does not support binding to a client, always returns {@code null} in {@link #onBind(Intent)}.</li>
 *   <li>Implements {@link android.app.Service#START_STICKY} to keep the service alive until explicitly stopped.</li>
 * </ul>
 *
 * @see android.app.Service
 * @see android.media.MediaPlayer
 *
 * @author Collin
 */
public class MusicService extends Service {

    static boolean isMusicMute = false;

    private static MediaPlayer mediaPlayer;  // MediaPlayer instance for audio playback
    private int currentTrackId = R.raw.music_pixel_rush;  // Default track

    private static Handler fadeOutHandler = new Handler();

    /**
     * Called when the service is first created. Initializes the MediaPlayer with a default track.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        initializeMediaPlayer(currentTrackId);
    }

    /**
     * Initializes the MediaPlayer with the specified music track.
     * <p>
     * This method prepares the MediaPlayer to play a specific track identified by its resource ID.
     * If an existing MediaPlayer instance exists, it is released to free resources before creating
     * a new instance. The MediaPlayer is set to loop the music track continuously.
     * </p>
     *
     * @param trackId the resource ID of the music track to be played
     */
    private void initializeMediaPlayer(int trackId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, trackId);
        mediaPlayer.setLooping(true);  // Set the music to loop
    }

    /**
     * Called when the service is started or receives a start command.
     * <p>
     * This method handles music playback logic when the service is started. If the music is muted,
     * it stops the MediaPlayer without further action. Otherwise, it checks if a new music track ID
     * is provided in the intent. If the track ID differs from the currently playing track, it initializes
     * the MediaPlayer with the new track. It ensures the music starts playing if not already running.
     * </p>
     *
     * @param intent the Intent supplied to the service with optional parameters (e.g., track ID)
     * @param flags  additional data about the start request
     * @param startId a unique integer ID for the start request
     * @return {@link android.app.Service#START_STICKY} to keep the service running until explicitly stopped
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isMusicMute) {
            stopMusic();
            return START_STICKY;  // Do nothing further if music is muted
        }

        // Check if the intent contains a new track ID to play
        if (intent != null && intent.hasExtra("TRACK_ID")) {
            int trackId = intent.getIntExtra("TRACK_ID", R.raw.music_pixel_rush);  // Default to the first track
            if (trackId != currentTrackId) {
                currentTrackId = trackId;
                initializeMediaPlayer(currentTrackId);
            }
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();  // Start playing the selected track
        }
        return START_STICKY;  // Keep the service running until explicitly stopped
    }

    /**
     * Stops and releases the MediaPlayer if it is currently playing.
     * <p>
     * This method checks if the MediaPlayer instance is not null and playing music. If true,
     * it stops the playback, releases the resources associated with the MediaPlayer, and sets
     * the instance to {@code null} to prevent memory leaks.
     * </p>
     */
    static void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Called when the service is destroyed.
     * <p>
     * This method is part of the service lifecycle and ensures proper cleanup of resources
     * when the service is no longer needed. Specifically, it stops and releases the MediaPlayer
     * instance by calling {@link #stopMusic()} to free up memory and avoid resource leaks.
     * </p>
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    /**
     * Pauses music playback if the MediaPlayer is currently playing.
     * <p>
     * This method checks whether the MediaPlayer instance exists and is in a playing state.
     * If true, it pauses the playback, allowing it to be resumed later.
     * </p>
     */
    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * Resumes music playback if the MediaPlayer is initialized and music is not muted.
     * <p>
     * This method checks whether the MediaPlayer instance exists and that the music is not
     * muted before starting the playback. It ensures the application handles music playback
     * states properly without throwing exceptions.
     * </p>
     */
    static public void resumeMusic() {
        if (mediaPlayer != null && !isMusicMute) {
            mediaPlayer.start();
        }
    }

    /**
     * Gradually fades out the music over a specified duration and stops the MediaPlayer.
     * <p>
     * This method decreases the volume of the music in steps over a 2-second period and
     * then stops and releases the MediaPlayer instance to free up resources. The fading
     * is handled by a Runnable that reduces the volume at regular intervals using a
     * {@link Handler}.
     * </p>
     *
     * <p><strong>Behavior:</strong></p>
     * <ul>
     * <li>If the MediaPlayer is null, the method returns immediately without performing any actions.</li>
     * <li>Uses a fade duration of 2 seconds (2000ms) and reduces the volume in steps every 100ms.</li>
     * <li>Once the fade-out process is complete, the MediaPlayer is stopped, released, and set to null.</li>
     * </ul>
     *
     * <p><strong>Implementation Details:</strong></p>
     * <ul>
     * <li>The initial volume is set to 1.0 (maximum).</li>
     * <li>The volume is reduced incrementally based on the fade duration and interval.</li>
     * <li>A {@link Handler} is used to schedule the volume reduction steps on the main thread.</li>
     * </ul>
     */
    public static void fadeOutAndStop() {
        if (mediaPlayer == null) return;

        final int fadeDuration = 2000; // 2 seconds
        final int fadeInterval = 100;  // Interval to reduce volume
        final float initialVolume = 1.0f;

        // Create a runnable to reduce volume
        Runnable fadeOutRunnable = new Runnable() {
            float volume = initialVolume;
            int elapsed = 0;

            @Override
            public void run() {
                if (mediaPlayer != null && volume > 0.0f) {
                    volume -= initialVolume / (fadeDuration / fadeInterval);
                    mediaPlayer.setVolume(volume, volume);
                    elapsed += fadeInterval;
                    if (elapsed < fadeDuration) {
                        fadeOutHandler.postDelayed(this, fadeInterval);
                    } else {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            }
        };

        fadeOutHandler.post(fadeOutRunnable);
    }

    /**
     * Returns null because this service does not support binding.
     * <p>
     * This method is part of the {@link android.app.Service} lifecycle and is called when
     * a client attempts to bind to the service. Since this service is not designed for binding,
     * it always returns {@code null}.
     * </p>
     *
     * @param intent The Intent that was used to bind to this service.
     * @return Always returns {@code null} as binding is not implemented.
     */

    @Override
    public IBinder onBind(Intent intent) {
        return null;  // Binding is not implemented for this service
    }
}
