package com.example.tetris_concept;

import static com.example.tetris_concept.AnimFunctions.flamePetIdleAnim;
import static com.example.tetris_concept.AnimFunctions.foxPetIdleAnim;
import static com.example.tetris_concept.AnimFunctions.playInfiniteFireAnimationV2;
import static com.example.tetris_concept.AnimFunctions.playInfiniteFoxAnimationV2;
//import static com.example.tetris_concept.AnimFunctions.playInfiniteSquirrelAnimation;
import static com.example.tetris_concept.AnimFunctions.playInfiniteSquirrelAnimationV2;
import static com.example.tetris_concept.AnimFunctions.squirrelPetIdleAnim;
import static com.example.tetris_concept.AppliedSettings.btnSoundFx;
import static com.example.tetris_concept.GameState.currentChosenPet;
//import static com.example.tetris_concept.HomeFragment.checkCurrentPet;
//import static com.example.tetris_concept.HomeFragment.loadCurrentChosenPet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * This class contains utility methods to display dialogs and manage pet-related UI updates.
 *
 * <p>It provides functionalities to handle pet information loading, idle animations,
 * and dialog displays for various game scenarios such as exiting a session or showing
 * energy recovery status.</p>
 *
 * <p><strong>Author:</strong> Alex</p>
 */

public class DialogList {

    /**
     * Loads the current pet's name and stats from storage and updates the UI accordingly.
     *
     * <p>This method retrieves the name and stats (HP, FO, MA, VI) of the currently selected pet
     * and sets them to the respective UI components. It also plays the corresponding pet's
     * idle animation.</p>
     *
     * @param petNameEdit      The EditText field where the pet's name will be displayed.
     * @param appStorage       The storage object used to retrieve pet data.
     * @param animatedImageView The ImageView where the pet's animation will be played.
     * @param hpTxt            The TextView displaying the pet's HP stat.
     * @param foTxt            The TextView displaying the pet's FO stat.
     * @param maTxt            The TextView displaying the pet's MA stat.
     * @param viTxt            The TextView displaying the pet's VI stat.
     */

    static void loadCurrentPetName(EditText petNameEdit, Storage appStorage, ImageView animatedImageView, TextView hpTxt, TextView foTxt, TextView maTxt, TextView viTxt) {

        switch (appStorage.getCurrentChosenPet()) {
            case "FIRE":
                petNameEdit.setText(appStorage.getFirePetName());
                playInfiniteFireAnimationV2(animatedImageView);
                //set stats:
                hpTxt.setText(String.valueOf(appStorage.getFirePetHP()));
                foTxt.setText(String.valueOf(appStorage.getFirePetFO()));
                maTxt.setText(String.valueOf(appStorage.getFirePetMA()));
                viTxt.setText(String.valueOf(appStorage.getFirePetVI()));


                break;
            case "FOX":
                petNameEdit.setText(appStorage.getFoxPetName());
                playInfiniteFoxAnimationV2(animatedImageView);
                //set stats:
                hpTxt.setText(String.valueOf(appStorage.getFoxPetHP()));
                foTxt.setText(String.valueOf(appStorage.getFoxPetFO()));
                maTxt.setText(String.valueOf(appStorage.getFoxPetMA()));
                viTxt.setText(String.valueOf(appStorage.getFoxPetVI()));
                break;
            case "SQUIRREL":
                petNameEdit.setText(appStorage.getSquirrelPetName());
                playInfiniteSquirrelAnimationV2(animatedImageView);
                //set stats:
                hpTxt.setText(String.valueOf(appStorage.getSquirrelPetHP()));
                foTxt.setText(String.valueOf(appStorage.getSquirrelPetFO()));
                maTxt.setText(String.valueOf(appStorage.getSquirrelPetMA()));
                viTxt.setText(String.valueOf(appStorage.getSquirrelPetVI()));
                break;
            default:
                System.out.println("Invalid color selection.");
                break;
        }

    }


    /**
     * Displays a pause dialog to confirm exiting the current game session.
     *
     * <p>This method creates and displays a custom dialog prompting the user to confirm
     * if they want to leave their current game session. If confirmed, the dialog closes,
     * plays a button sound effect, and terminates the application.</p>
     *
     * @param context The context used to create and display the dialog, and to manage
     *                the application's activity stack.
     */

    static void showPauseDialog1(Context context) {
        // Create a dialog instance
        Dialog dialog = new Dialog(context, R.style.CustomDialogTheme);
        // Set the content view to your custom layout
        dialog.setContentView(R.layout.warning_dialog);

        // Initialize dialog components: _______________________________
        TextView warningHeader = dialog.findViewById(R.id.warningHeader);
        TextView warningMessage = dialog.findViewById(R.id.warningMessage);
        Button warningBtn = dialog.findViewById(R.id.warningBtn);

        // Events On Load: ____________________________
        warningHeader.setText("EXIT ?");
        warningMessage.setText("Are you sure you want to leave your current game session?");
        warningBtn.setText("EXIT");
        warningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();  // Close the dialog
                btnSoundFx(dialog.getContext());

                if (context instanceof Activity) {
                    ((Activity) context).finishAffinity(); // Closes all activities in the stack
                    System.exit(0); // Ensures app termination
                }
            }
        });

        dialog.show();
    }

    /**
     * Displays a pause dialog informing the user that pets are regaining energy.
     *
     * <p>This method creates and displays a custom dialog with a message indicating that
     * all pets are currently sleeping to regain energy until a specified date. The dialog
     * includes a button to dismiss it and plays a button sound effect upon interaction.</p>
     *
     * @param context The context used to create and display the dialog, and to access
     *                stored information about energy refill timing.
     */

    static void showPauseDialog2(Context context) {
        Dialog dialog = new Dialog(context, R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.warning_dialog);
        Storage appStorage = new Storage(context);

        // Initialize dialog components: _______________________________
        TextView warningHeader = dialog.findViewById(R.id.warningHeader);
        TextView warningMessage = dialog.findViewById(R.id.warningMessage);
        Button warningBtn = dialog.findViewById(R.id.warningBtn);

        // Events On Load: ____________________________
        warningHeader.setText("NO ENERGY!");
        warningMessage.setText("All Pets Are Sleeping to Regain Energy Unitl:\n" + appStorage.getDateRefillEnergy());
        warningBtn.setText("OKAY");
        warningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();  // Close the dialog
                btnSoundFx(context);
            }
        });

        dialog.show();
    }





}
