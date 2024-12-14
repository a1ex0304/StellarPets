package com.example.tetris_concept;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import static com.example.tetris_concept.AnimFunctions.flamePetIdleAnim;
import static com.example.tetris_concept.AnimFunctions.flamePetRunAnim;
import static com.example.tetris_concept.AnimFunctions.flameSleepAnim;
import static com.example.tetris_concept.AnimFunctions.foxPetIdleAnim;
import static com.example.tetris_concept.AnimFunctions.foxSleepAnim;
import static com.example.tetris_concept.AnimFunctions.playInfiniteFireAnimation;
//import static com.example.tetris_concept.AnimFunctions.playInfiniteFireAnimationV4;
import static com.example.tetris_concept.AnimFunctions.playInfiniteFoxAnimation;
//import static com.example.tetris_concept.AnimFunctions.playInfiniteSquirrelAnimation;
import static com.example.tetris_concept.AnimFunctions.playInfiniteSquirrelAnimation;
import static com.example.tetris_concept.AnimFunctions.setupBackgroundAnimation;
import static com.example.tetris_concept.AnimFunctions.squirrelPetIdleAnim;
import static com.example.tetris_concept.AnimFunctions.squirrelSleepAnim;
import static com.example.tetris_concept.AppliedSettings.btnSoundFx;
import static com.example.tetris_concept.AppliedSettings.hideNavigationBar;
import static com.example.tetris_concept.DialogList.loadCurrentPetName;
import static com.example.tetris_concept.DialogList.showPauseDialog1;
import static com.example.tetris_concept.DialogList.showPauseDialog2;
import static com.example.tetris_concept.GameState.currentChosenPet;
import static com.example.tetris_concept.Storage.parentTimeIsOn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * HomeFragment is a Fragment representing the home screen of the application.
 * This fragment handles various UI elements like buttons, animations, and user interactions.
 * It manages the display of background music, pet animations, and energy levels,
 * as well as interactions with different game features (e.g., play, settings, inventory).
 *
 * The fragment includes methods for controlling UI elements, such as hiding the navigation bar,
 * applying gradient effects to text, starting and stopping background music, and checking for energy refill.
 * It also includes methods for handling user actions like switching between game states and showing dialogs.
 *
 * @author Alex
 */
public class HomeFragment extends Fragment {

    ProgressBar homeEnergyProg;

    private boolean playingHomeMusic = true;  // Keeps track of the music state

    /**
     * Inflates the fragment's view, sets up event listeners for buttons, and handles UI elements.
     * This method also handles window insets for system UI, hides the navigation bar, and applies animations.
     *
     * @param inflater           The LayoutInflater object to inflate the fragment's layout.
     * @param container          The container that the fragment's view will be attached to.
     * @param savedInstanceState A Bundle containing saved instance state, if any.
     * @return The view for this fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Handle window insets for system UI
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.mainTest), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize: ____________________________________________________________________________
        Storage appStorage = new Storage(view.getContext());
        ImageView backgroundImage = view.findViewById(R.id.background_image);
        ImageView animatedImageView = view.findViewById(R.id.animatedImageHomeFragView);
        Button musicBtn = view.findViewById(R.id.musicBtn);
        Button playBtn = view.findViewById(R.id.playBtn);
        Button aboutBtn = view.findViewById(R.id.aboutBtn);
        Button settingBtn = view.findViewById(R.id.settingBtn);
        ConstraintLayout petInventory = view.findViewById(R.id.petInventory);
        Button exitGameBTN = view.findViewById(R.id.exitGameBTN);
        homeEnergyProg = view.findViewById(R.id.homeEnergyProg);
        Button choosePetBtn = view.findViewById(R.id.choosePetBtn);

        // On load: ____________________________________________________________________________
        hideNavigationBar(view);         // Hide navigation bar and set full-screen mode
        setupBackgroundAnimation(backgroundImage); // Set up background animation for the background image
        animatedImageView.setImageResource(R.drawable.fire_run_frame_animation);
        applyTextGradient(view);
        homeEnergyProg.setProgress(appStorage.getGameEnergy());
        startDateCheck(view.getContext(), view);
        loadCurrentChosenPet(appStorage.getCurrentChosenPet(), animatedImageView);
        currentChosenPet = appStorage.getCurrentChosenPet();


//      On click listeners ________________________________________________________________________
        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playingHomeMusic) {
                    pauseMusic();
                    playingHomeMusic = false;
                } else {
                    resumeMusic();
                    playingHomeMusic = true;
                }

            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(getActivity());
                if (!parentTimeIsOn) {
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new GameMapFragment());
                    transaction.addToBackStack(null);  // Add to back stack for back navigation
                    transaction.commit();
                } else {
                    showPauseDialog(view.getContext());
                }
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(getActivity());
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new SettingsFragment());
                transaction.addToBackStack(null);  // Add to back stack for back navigation
                transaction.commit();
            }
        });

        // About button logic: plays a sound effect
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(view.getContext());

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new AboutFragment());
                transaction.addToBackStack(null);  // Add to back stack for back navigation
                transaction.commit();
            }
        });



        choosePetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(view.getContext());
                if (appStorage.getDateRefillEnergy().equals("NONE")) {
                    showPetInventoryDialog(view.getContext(), animatedImageView);
                }else{
                    showPauseDialog2(view.getContext());
                }

            }
        });


        exitGameBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(view.getContext());
                showPauseDialog1(view.getContext());
            }
        });


        return view;
    }

    /**
     * Applies a gradient effect to the text views in the home fragment.
     * This method creates linear gradients and applies them to the text views.
     *
     * @param view The root view of the fragment, which contains the text views.
     */
    private void applyTextGradient(View view) {
        TextView stellarText = view.findViewById(R.id.stellar_text);
        Shader stellarTextShader = new LinearGradient(0, 0, 0, stellarText.getTextSize(),
                new int[]{Color.parseColor("#e200ff"), Color.parseColor("#F78CC2")},
                null, Shader.TileMode.CLAMP);
        stellarText.getPaint().setShader(stellarTextShader);

        TextView petsText = view.findViewById(R.id.pets_text);
        Shader petsTextShader = new LinearGradient(0, 0, 0, petsText.getTextSize(),
                new int[]{Color.parseColor("#e200ff"), Color.parseColor("#00f6ff")},
                null, Shader.TileMode.CLAMP);
        petsText.getPaint().setShader(petsTextShader);
    }

    /**
     * Shows a pause dialog when the user's session has expired.
     * The dialog contains a message and a button to dismiss it.
     *
     * @param context The context in which the dialog will be shown.
     */
    public void showPauseDialog(Context context) {
        // Create a dialog instance
        Dialog dialog = new Dialog(context, R.style.CustomDialogTheme);
        // Set the content view to your custom layout
        dialog.setContentView(R.layout.warning_dialog);

        // Initialize dialog components: _______________________________
        TextView warningHeader = dialog.findViewById(R.id.warningHeader);
        TextView warningMessage = dialog.findViewById(R.id.warningMessage);
        Button warningBtn = dialog.findViewById(R.id.warningBtn);

        // Events On Load: ____________________________
        warningHeader.setText("MESSAGE");
        warningMessage.setText("Your session has expired. For more time, provide your phone to your guardian.");
        warningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();  // Close the dialog
            }
        });

        dialog.show();
    }

    /**
     * Loads the animation for the currently chosen pet based on the given pet type and its energy refill status.
     * The function checks the current pet type and decides which animation to play: either an infinite animation
     * (if the pet's energy is full) or a sleep animation (if the pet's energy is refilling).
     *
     * @param currentPet The type of the currently chosen pet. Valid values are "FIRE", "FOX", and "SQUIRREL".
     * @param view The view from which the animations will be applied. This is used to find the corresponding
     *             image view for the pet's animation.
     *
     * @throws IllegalArgumentException If the pet type is invalid or not recognized.
     */
    static void loadCurrentChosenPet(String currentPet, View view) {
        Storage appStorage = new Storage(view.getContext());

        switch (currentPet) {
            case "FIRE":
                if (appStorage.getDateRefillEnergy().equals("NONE")) {
                    playInfiniteFireAnimation(view.findViewById(R.id.animatedImageHomeFragView));
                } else {
                    flameSleepAnim(view.findViewById(R.id.animatedImageHomeFragView));
                }


                break;
            case "FOX":
                if (appStorage.getDateRefillEnergy().equals("NONE")) {
                    ImageView mainImageView2 = view.findViewById(R.id.animatedImageHomeFragView);
                    AnimFunctions animationHelper2 = new AnimFunctions();
                    animationHelper2.playInfiniteFoxAnimationV3(mainImageView2);
                } else {
                    foxSleepAnim(view.findViewById(R.id.animatedImageHomeFragView));
                }

                break;
            case "SQUIRREL":

                if (appStorage.getDateRefillEnergy().equals("NONE")) {
                    ImageView mainImageView3 = view.findViewById(R.id.animatedImageHomeFragView);
                    AnimFunctions animationHelper3 = new AnimFunctions();
                    animationHelper3.playInfiniteSquirrelAnimationV3(mainImageView3);
                } else {
                    squirrelSleepAnim(view.findViewById(R.id.animatedImageHomeFragView));
                }

                break;
            default:
                System.out.println("Invalid pet selection.");
                break;
        }
    }

    /**
     * Pauses the background music by stopping the MusicService.
     * This method creates an intent to stop the MusicService, effectively halting the playback of any background music.
     */
    public void pauseMusic() {
        Intent intent = new Intent(getActivity(), MusicService.class);
        getActivity().stopService(intent);
    }

    /**
     * Resumes the background music by restarting the MusicService.
     * This method creates an intent to start the MusicService, allowing the background music to resume playback.
     */
    public void resumeMusic() {
        Intent musicServiceIntent = new Intent(getActivity(), MusicService.class);
        getActivity().startService(musicServiceIntent);
    }


    @Override
    public void onResume() {
        super.onResume();

        // Access SharedPreferences through the parent activity
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("home_page", Context.MODE_PRIVATE);

        // Retrieve the progress value and set it on the ProgressBar
        int progress = sharedPreferences.getInt("gameEnergy", 100);
        if (homeEnergyProg != null) {
            homeEnergyProg.setProgress(progress);
        }
    }

    /**
     * Starts a periodic check to monitor whether the target date for energy refill has passed.
     * The method uses a handler to repeatedly check the target date stored in `appStorage` and compares it
     * with the current date to determine if the game energy should be reset.
     * If the current date has passed the target date, the energy is reset to full, and the current pet animation is reloaded.
     *
     * @param context The context in which the method is being executed, typically used to access application resources.
     * @param view The view associated with the current screen, used to trigger UI updates such as reloading the pet animation.
     */
    public void startDateCheck(Context context, View view) {
        Handler handler = new Handler(Looper.getMainLooper());
        Handler handler2 = new Handler(Looper.getMainLooper());

        Storage appStorage = new Storage(context);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

        Runnable checkDateRunnable = new Runnable() {
            @Override
            public void run() {
                // Check if target date has been set
                if (!appStorage.getDateRefillEnergy().equals("NONE")) {
                    try {
                        Date targetDate = dateFormat.parse(appStorage.getDateRefillEnergy());
                        Date currentDate = new Date();

                        // Check if the current date has passed the target date
                        if (currentDate.after(targetDate)) {
                            appStorage.resetGameEnergyToFull();
                            appStorage.setDateRefillEnergy("NONE");
                            System.out.println("Target date has passed, energy reset.");
                            homeEnergyProg.setProgress(appStorage.getGameEnergy());

                            handler2.postDelayed(() -> { // waits so the text is written in json file!
                                loadCurrentChosenPet(appStorage.getCurrentChosenPet(), view);
                            }, 1000); // 1000 milliseconds = 1 second

                        } else {
                            System.out.println("Current time is still before the target time.");
                        }

                    } catch (ParseException e) {
                        System.out.println("Error parsing target date string.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Target date not yet set. Waiting...");
                }

                // Post the runnable again to run it every second (1000 milliseconds)
                handler.postDelayed(this, 5000);
            }
        };

        // Start the periodic task
        handler.post(checkDateRunnable);
    }

    /**
     * Displays a dialog allowing the user to interact with their pet inventory. The dialog includes options to
     * choose between three pets (Fire, Fox, and Squirrel), view and edit pet names, and animate the pets.
     * It also handles dynamic UI updates, such as displaying the pet's name, health, and other attributes,
     * and allows the user to update the pet's name in real-time.
     *
     * @param context The context in which the dialog is being displayed, used for accessing application resources.
     * @param view The view associated with the current screen, typically used to trigger UI updates (e.g., animated pet images).
     */
    public void showPetInventoryDialog(Context context, View view) {
        // Create a dialog instance
        Dialog dialog = new Dialog(context, R.style.CustomDialogTheme);
        // Set the content view to your custom layout
        dialog.setContentView(R.layout.dialog_pet_inventory);

        // Get the window and adjust layout parameters
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(params);
        }

        // Hide the navigation bar and status bar for Android 10 (API 29) and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            WindowInsetsController controller = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                controller = dialog.getWindow().getDecorView().getWindowInsetsController();
            }
            if (controller != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                }
            }
        } else {
            // For devices below Android 10, use system UI visibility flags
            View decorView = dialog.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }


//      Init : _____________________________________________________________________________________
        ImageView animatedImageView = dialog.findViewById(R.id.animatedImageHomeFragView);
        ImageView animatedFireIdleImg = dialog.findViewById(R.id.animatedFireIdleImg);
        ImageView animatedFoxIdleImg = dialog.findViewById(R.id.animatedFoxIdleImg);
        ImageView animatedSquirrelIdleImg = dialog.findViewById(R.id.animatedSquirrelIdleImg);
        ConstraintLayout fireConstraint = dialog.findViewById(R.id.fireConstraint);
        ConstraintLayout foxConstraint = dialog.findViewById(R.id.foxConstraint);
        ConstraintLayout squirrelConstraint = dialog.findViewById(R.id.squirrelConstraint);
        EditText petNameEdit = dialog.findViewById(R.id.petNameEdit);
        Storage appStorage = new Storage(dialog.getContext());
        TextView squirrelNameText = dialog.findViewById(R.id.squirrelNameText);
        TextView fireNameText = dialog.findViewById(R.id.fireNameText);
        TextView foxNameText = dialog.findViewById(R.id.foxNameText);
        TextView hpTxt = dialog.findViewById(R.id.hpTxt);
        TextView foTxt = dialog.findViewById(R.id.foTxt);
        TextView maTxt = dialog.findViewById(R.id.maTxt);
        TextView viTxt = dialog.findViewById(R.id.viTxt);


//      On load : ____________________________________________________________________________________
//        playInfiniteSquirrelAnimation(animatedImageView);
        flamePetIdleAnim(animatedFireIdleImg);
        foxPetIdleAnim(animatedFoxIdleImg);
        squirrelPetIdleAnim(animatedSquirrelIdleImg);
        loadCurrentPetName(petNameEdit, appStorage, animatedImageView, hpTxt, foTxt, maTxt, viTxt);
        foxNameText.setText(appStorage.getFoxPetName());
        fireNameText.setText(appStorage.getFirePetName());
        squirrelNameText.setText(appStorage.getSquirrelPetName());

//      On click Listeners  ___________________________________________________________________________

        fireConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(dialog.getContext());
                currentChosenPet = "FIRE"; // choosing FIRE pet
                appStorage.setCurrentChosenPet("FIRE");
                loadCurrentPetName(petNameEdit, appStorage, animatedImageView, hpTxt, foTxt, maTxt, viTxt);

                AnimFunctions animationHelper = new AnimFunctions();
                ImageView mainImageView = view.findViewById(R.id.animatedImageHomeFragView);
                animationHelper.playInfiniteFireAnimationV4(mainImageView);

            }
        });

        foxConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(dialog.getContext());
                currentChosenPet = "FOX"; // choosing FIRE pet
                appStorage.setCurrentChosenPet("FOX");
                loadCurrentPetName(petNameEdit, appStorage, animatedImageView, hpTxt, foTxt, maTxt, viTxt);

                AnimFunctions animationHelper = new AnimFunctions();
                ImageView mainImageView = view.findViewById(R.id.animatedImageHomeFragView);
                animationHelper.playInfiniteFoxAnimationV3(mainImageView);
            }
        });

        squirrelConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(dialog.getContext());
                currentChosenPet = "SQUIRREL"; // choosing FIRE pet
                appStorage.setCurrentChosenPet("SQUIRREL");
                loadCurrentPetName(petNameEdit, appStorage, animatedImageView, hpTxt, foTxt, maTxt, viTxt);

                AnimFunctions animationHelper = new AnimFunctions();
                ImageView mainImageView = view.findViewById(R.id.animatedImageHomeFragView);
                animationHelper.playInfiniteSquirrelAnimationV3(mainImageView);

            }
        });


        // Add a TextWatcher to listen for changes
        petNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called as the text is being changed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This method is called after the text is changed
                String inputText = s.toString();
                if (inputText.isEmpty()) {
                    inputText = "NO NAME";
                }
                // Display or use the text value
                switch (currentChosenPet) {
                    case "FIRE":
                        appStorage.setFirePetName(inputText);
                        fireNameText.setText(appStorage.getFirePetName());
                        break;
                    case "FOX":
                        appStorage.setFoxPetName(inputText);
                        foxNameText.setText(appStorage.getFoxPetName());
                        break;
                    case "SQUIRREL":
                        appStorage.setSquirrelPetName(inputText);
                        squirrelNameText.setText(appStorage.getSquirrelPetName());
                        break;
                    default:
                        System.out.println("Invalid something selection.");
                        break;
                }

            }
        });


//       ___________________________

        // Show the dialog
        dialog.show();
    }


}
