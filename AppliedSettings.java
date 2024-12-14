package com.example.tetris_concept;

import static com.example.tetris_concept.MusicService.isMusicMute;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A utility class for managing application settings and providing functionality such as
 * hiding the navigation bar, playing sound effects, and loading user preferences.
 *
 * <p>This class centralizes actions related to the application's settings, including
 * toggling music and sound effects, ensuring a seamless user experience.</p>
 *
 * @author Bilal
 */
public class AppliedSettings {
    static boolean isSoundFXMute = false;

    /**
     * Hides the navigation bar and makes the view fullscreen with immersive sticky mode.
     *
     * @param view the view on which the navigation bar will be hidden
     */
    public static void hideNavigationBar(View view) {
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    /**
     * Plays a button sound effect.
     *
     * @param context the context used to access the sound resource
     */
    public static void btnSoundFx(Context context) {
        if (!isSoundFXMute) {
            MediaPlayer btnSoundFx = MediaPlayer.create(context, R.raw.btn_fx_two);
            btnSoundFx.start();
        }

    }

    /**
     * Loads application settings related to music and sound effects from storage and applies them.
     *
     * <p>This method retrieves the user's music and sound effect preferences and starts
     * the music service if music is not muted.</p>
     *
     * @param context The context used to access storage and start the music service.
     */

    public static void loadSettings(Context context) {
        Storage appStorage = new Storage(context);
        isMusicMute = appStorage.getIsMusicMute();
        isSoundFXMute = appStorage.getIsSoundFXMute();


        if (isMusicMute) {
        } else {
            Intent musicServiceIntent = new Intent(context, MusicService.class);
            context.startService(musicServiceIntent);  // Restart the music service when the app is resumed
        }




    }


}
