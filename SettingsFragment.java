package com.example.tetris_concept;

import static com.example.tetris_concept.AnimFunctions.setupBackgroundAnimation;
import static com.example.tetris_concept.AnimFunctions.shakeImageView;
import static com.example.tetris_concept.AppliedSettings.btnSoundFx;
import static com.example.tetris_concept.AppliedSettings.hideNavigationBar;
import static com.example.tetris_concept.AppliedSettings.isSoundFXMute;
import static com.example.tetris_concept.MusicService.isMusicMute;
import static com.example.tetris_concept.MusicService.resumeMusic;
import static com.example.tetris_concept.MusicService.stopMusic;
import static com.example.tetris_concept.Storage.parentTimeIsOn;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The {@code SettingsFragment} class provides the functionality for the settings page in the application.
 * <p>
 * This fragment includes features such as parental controls, audio settings, and time limit management.
 * It allows the user to interact with various elements of the application settings, such as toggling sound
 * effects and music, resetting gameplay statistics, and configuring parental controls.
 * </p>
 *
 * <h2>Features:</h2>
 * <ul>
 *     <li>Parental controls to restrict access with a password.</li>
 *     <li>Options to toggle sound effects and music on or off.</li>
 *     <li>Ability to set and reset time limits for gameplay.</li>
 *     <li>Reset gameplay statistics like total games played and average playtime.</li>
 *     <li>Background animation and immersive mode integration.</li>
 * </ul>
 *
 * <h2>Dependencies:</h2>
 * <ul>
 *     <li>{@link MusicService} for managing background music playback.</li>
 *     <li>{@link Storage} for storing and retrieving application settings.</li>
 * </ul>
 *
 * @author Alex
 */
public class SettingsFragment extends Fragment {
    // Variable to store the formatted date and time
    String formattedDateTime = "";

    // Constant for the parental control password
    private final String PARENT_PASS = "CS2212";

    /**
     * Required empty public constructor for SettingsFragment.
     */
    public SettingsFragment() {
        // Default constructor
    }

    /**
     * Called when the fragment's view is created. Inflates the layout and sets up UI elements.
     *
     * @param inflater           The LayoutInflater object to inflate views in the fragment.
     * @param container          The parent view the fragment's UI should attach to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The view for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize UI elements
        TextView totalGamesPlayedNum = view.findViewById(R.id.totalGamesPlayedNum);
        TextView totalTimePlayedNum = view.findViewById(R.id.totalTimePlayedNum);
        TextView avgTimePlayedNum = view.findViewById(R.id.AvgTimePlayedNum);

        Storage AppStorage = new Storage(view.getContext());
        ImageView bg = view.findViewById(R.id.background_image);
        ImageView lockIconImg = view.findViewById(R.id.lockIconImg);
        LinearLayout unlockBtnPrnt = view.findViewById(R.id.unlockBtnPrnt);
        ConstraintLayout parentalControlSign = view.findViewById(R.id.parentalControlSign);
        EditText passwordInput = view.findViewById(R.id.passwordInput);
        ScrollView parentSettingLayout = view.findViewById(R.id.parentSettingLayout);
        Button audioFXBtnOnOff = view.findViewById(R.id.audioFXBtnOnOff);
        Button musicBtnOnOff = view.findViewById(R.id.musicBtnOnOff);
        Storage appStorage = new Storage(view.getContext());
        Button backBtn = view.findViewById(R.id.backBtn);
        Button setTimeBtn = view.findViewById(R.id.setTimeBtn);
        Button unlockSetTime = view.findViewById(R.id.unlockSetTime);
        TimePicker timePicker = view.findViewById(R.id.timePicker);
        TextView setTimeoutDate = view.findViewById(R.id.setTimeoutDate);
        Button resetStatsBtn = view.findViewById(R.id.resetStatsBtn);

//      On load________________________________________________________
        totalGamesPlayedNum.setText(appStorage.getTotalPlayedGame() + " G");
        totalTimePlayedNum.setText(String.format("%.1f", appStorage.getTotalPlayedTime()) + " min");
        float avg = 0;
        if (appStorage.getTotalPlayedGame() > 0) {
            avg = appStorage.getTotalPlayedTime() / appStorage.getTotalPlayedGame();  // Avoid division by zero
        }
        avgTimePlayedNum.setText(String.format("%.1f", avg) + " min/G");


        // Set up initial view and animation functions
        hideNavigationBar(view); // Hide navigation bar for immersive mode
        setupBackgroundAnimation(bg); // Set up background animation
        changeBtnStatus(isMusicMute, musicBtnOnOff); // Set music button status
        changeBtnStatus(isSoundFXMute, audioFXBtnOnOff); // Set sound effect button status
        setTimeoutDate.setText(appStorage.getTimeLimit()); // Set the time limit

        // Set onClick listener for unlocking parental controls
        unlockBtnPrnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the password input matches the stored password
                if (checkPass(passwordInput.getText().toString())) {
                    parentalControlSign.setVisibility(View.GONE); // Hide the lock sign
                    parentSettingLayout.setVisibility(View.VISIBLE); // Show parental controls
                } else {
                    // If password is incorrect, shake the lock icon and show a toast message
                    shakeImageView(lockIconImg);
                    Toast.makeText(getContext(), "INVALID PASSWORD", Toast.LENGTH_SHORT).show();
                    parentalControlSign.setVisibility(View.VISIBLE);
                    parentSettingLayout.setVisibility(View.GONE);
                }
            }
        });

        // Set onClick listener for toggling music settings
        musicBtnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle music mute state and update the button label
                if (isMusicMute) {
                    resumeMusic();
                    isMusicMute = false;
                    AppStorage.setIsMusicMute(isMusicMute);
                    musicBtnOnOff.setText("ON");
                } else {
                    pauseMusic();
                    isMusicMute = true;
                    AppStorage.setIsMusicMute(isMusicMute);
                    musicBtnOnOff.setText("OFF");
                }
            }
        });

        resetStatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(view.getContext());
                appStorage.setTotalPlayedGame(0);
                appStorage.setTotalPlayedTime(0);

                totalGamesPlayedNum.setText(appStorage.getTotalPlayedGame() + " G");
                totalTimePlayedNum.setText(String.format("%.1f", appStorage.getTotalPlayedTime()) + " min");

                avgTimePlayedNum.setText(0 + " min/G");
            }
        });

        // Set onClick listener for toggling audio FX settings
        audioFXBtnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle sound FX mute state and update the button label
                if (isSoundFXMute) {
                    isSoundFXMute = false;
                    audioFXBtnOnOff.setText("ON");
                    AppStorage.setIsSoundFXMute(isSoundFXMute);
                } else {
                    isSoundFXMute = true;
                    audioFXBtnOnOff.setText("OFF");
                    AppStorage.setIsSoundFXMute(isSoundFXMute);
                }
                btnSoundFx(view.getContext()); // Apply the sound FX settings
            }
        });

        // Set onClick listener for the back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(view.getContext());
                requireActivity().onBackPressed();  // Navigate back
            }
        });

        // Set onClick listener for unlocking the time settings
        unlockSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the time limit to NONE and update the display
                appStorage.setTimeLimit("NONE");
                setTimeoutDate.setText("NONE");
            }
        });

        // Set onClick listener for setting the time limit
        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the time limit based on the selected time and update the display
//                Toast.makeText(getContext(), formattedDateTime, Toast.LENGTH_SHORT).show();
                parentTimeIsOn = false;
                appStorage.setTimeLimit(formattedDateTime);
                setTimeoutDate.setText(formattedDateTime);
            }
        });

        // Set listener for the time picker
        timePicker.setOnTimeChangedListener((timePicker1, hourOfDay, minute) -> {
            // Get the current date and set the selected hour and minute
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            // Format the date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            formattedDateTime = dateFormat.format(calendar.getTime());

            // Display the formatted date and time in a Toast message
//            Toast.makeText(view.getContext(), "Selected Date and Time: " + formattedDateTime, Toast.LENGTH_SHORT).show();
        });

        return view;  // Return the inflated view with updated UI elements
    }

    /**
     * Checks if the provided password matches the stored parental control password.
     * <p>
     * This method compares the user-provided password input to the predefined parental control password.
     * It ensures that only authorized users can access restricted features.
     * </p>
     *
     * @param input The entered password.
     * @return {@code true} if the password is correct, {@code false} otherwise.
     */
    boolean checkPass(String input) {
        return input.equals(PARENT_PASS);
    }

    /**
     * Updates the text of a button based on the current sound effects mute status.
     * <p>
     * This method adjusts the button's label to reflect whether sound effects are muted
     * or unmuted. For example, the button displays "OFF" when muted and "ON" when unmuted.
     * </p>
     *
     * @param status    The current mute status. {@code true} for muted, {@code false} for unmuted.
     * @param btnStatus The {@link Button} whose text will be updated.
     */
    void changeBtnStatus(boolean status, Button btnStatus) {
        if (status) {
            btnStatus.setText("OFF");
        } else {
            btnStatus.setText("ON");
        }
    }

    /**
     * Pauses the background music by stopping the {@link MusicService}.
     * <p>
     * This method sends an intent to stop the {@link MusicService}, halting background
     * music playback. It is typically used when the user disables music or navigates away
     * from the activity requiring background music.
     * </p>
     */
    public void pauseMusic() {
        Intent intent = new Intent(getActivity(), MusicService.class);
        getActivity().stopService(intent);
    }

    /**
     * Resumes the background music by starting the {@link MusicService}.
     * <p>
     * This method sends an intent to start the {@link MusicService}, resuming background
     * music playback. It is typically used when the user re-enables music or navigates
     * back to an activity that requires background music.
     * </p>
     */
    public void resumeMusic() {
        Intent musicServiceIntent = new Intent(getActivity(), MusicService.class);
        getActivity().startService(musicServiceIntent);
    }

}
