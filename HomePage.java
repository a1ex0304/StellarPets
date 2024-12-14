package com.example.tetris_concept;

import static com.example.tetris_concept.AppliedSettings.loadSettings;
import static com.example.tetris_concept.MusicService.isMusicMute;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

/**
 * The HomePage class represents the main screen of the application. It is an Activity that is responsible for
 * initializing the user interface, managing fragments, handling background music, and ensuring proper
 * management of system UI elements (such as status bars and navigation bars).
 *
 * This class also integrates features such as edge-to-edge display for a full-screen experience, as well as
 * functionality for managing parental control time limits and settings.
 *
 * The HomePage class is part of the app's core activity lifecycle, and it overrides key lifecycle methods
 * such as onCreate(), onPause(), and onResume() to handle UI setup and music service management.
 *
 * @author Collin
 */
public class HomePage extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * Sets up the UI, fragments, and initializes any necessary services.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Enables edge-to-edge display for a full-screen experience
        setContentView(R.layout.activity_home_page);

        // Apply window insets to handle system UI (e.g., status bar and navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainTest), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ______________________________________ Init ____________________________________________
        Storage appStorage = new Storage(this);

        // ______________________________________ On load ____________________________________________

        // On activity load: Load the HomeFragment into the fragment container
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.commit();
        // Start checking time limits in the background (e.g., parental controls)
        appStorage.startCheckingTime();
        // Load settings from AppliedSettings class
        loadSettings(this);
    }

    /**
     * Called when the activity goes into the background.
     * This method pauses the background music by stopping the MusicService.
     * The music service is stopped to ensure that music is no longer playing when the user navigates away from the activity.
     * The method is automatically invoked by the Android lifecycle when the activity is paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);  // Stop the music service when the app goes into the background

    }

    /**
     * Called when the activity comes back to the foreground.
     * This method resumes the background music by restarting the MusicService.
     * The music service is started again to resume playback of the music when the user returns to the activity.
     * The method is automatically invoked by the Android lifecycle when the activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Intent musicServiceIntent = new Intent(this, MusicService.class);
        startService(musicServiceIntent);  // Restart the music service when the app is resumed
    }


}
