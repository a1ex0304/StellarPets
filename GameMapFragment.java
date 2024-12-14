package com.example.tetris_concept;

import static com.example.tetris_concept.AnimFunctions.animateTextColor;
import static com.example.tetris_concept.AnimFunctions.applyTextGradient;
import static com.example.tetris_concept.AnimFunctions.setupBackgroundAnimation;
import static com.example.tetris_concept.AppliedSettings.btnSoundFx;
import static com.example.tetris_concept.AppliedSettings.hideNavigationBar;
import static com.example.tetris_concept.Storage.parentTimeIsOn;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * GameMapFragment is a fragment representing a game map interface within the application.
 * <p>
 * This fragment displays a map with a scrollable horizontal list of game items using a RecyclerView.
 * It includes UI elements such as buttons for navigation, background animations, and loading text effects.
 * Additionally, it handles session management by periodically checking if the user's session has expired
 * and showing a custom pause dialog when necessary.
 * </p>
 *
 * <p>Author: Alex</p>
 */
public class GameMapFragment extends Fragment {
    private Handler handler = new Handler();
    private static final int CHECK_INTERVAL = 1000;
    private Button backBtn;
    private TextView loadingStellarPetsText1, loadingStellarPetsText2;

    GameMapRecyclerAdapter adapter;

    /**
     * Default constructor for GameMapFragment.
     * Required for fragment instantiation.
     */
    public GameMapFragment() {
        // Required empty public constructor
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is where the UI elements and animations are initialized.
     *
     * @param inflater           The LayoutInflater object used to inflate views in the fragment
     * @param container          The parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return The View for the fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_map, container, false);

//        init :
        Button tutBtn = view.findViewById(R.id.tutBtn);
        Storage appStorage = new Storage(view.getContext());


        // Hide the navigation bar
        hideNavigationBar(view);

        // Setup background animation
        ImageView backgroundImage = view.findViewById(R.id.background_image);
        setupBackgroundAnimation(backgroundImage);

        // Initialize the back button
        backBtn = view.findViewById(R.id.backBtn);

        // Initialize RecyclerView with a horizontal LinearLayoutManager
        RecyclerView recyclerView = view.findViewById(R.id.myRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setStackFromEnd(true);  // Ensures items start from the right
        recyclerView.setLayoutManager(layoutManager);

        // Initialize transitions and layout components
        ImageView transition1 = view.findViewById(R.id.transition1);
        ImageView transition2 = view.findViewById(R.id.transition2);
        ConstraintLayout logoTransition = view.findViewById(R.id.transitionLogo);
        LinearLayout tipTransition = view.findViewById(R.id.tipTransition);

        // Set up the RecyclerView adapter
        adapter = new GameMapRecyclerAdapter(view.getContext(), transition1, transition2, logoTransition, tipTransition);
        recyclerView.setAdapter(adapter);

        // Attach a LinearSnapHelper to snap to items
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        // Set up the back button click listener
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(view.getContext());  // Play sound effect
                requireActivity().onBackPressed();  // Navigate back
            }
        });

        tutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSoundFx(view.getContext());

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new TutFragment());
                transaction.addToBackStack(null);  // Add to back stack for back navigation
                transaction.commit();

            }
        });


        // Initialize loading text views and animate their text colors
        loadingStellarPetsText1 = view.findViewById(R.id.loadingStellarPetsText1);
        loadingStellarPetsText2 = view.findViewById(R.id.loadingStellarPetsText2);

        // Animate the text colors
        animateTextColor(loadingStellarPetsText1, "#F78CC2", "#D900FF"); // Soft pink to soft magenta
        animateTextColor(loadingStellarPetsText2, "#A500FF", "#00E8FF");  // Deep purple to soft cyan

        startCheckingTime();


        return view;
    }

    /**
     * Starts a repeated task to check the current state of the fragment and display a pause dialog if needed.
     * <p>
     * This method posts a delayed task that periodically checks if the fragment is added, resumed,
     * and the parent time condition is met. If these conditions are satisfied, it shows a pause dialog
     * and pops the fragment from the back stack. The check is repeated at a specified interval as long as
     * the fragment remains visible and active.
     * </p>
     *
     * @see #CHECK_INTERVAL the time interval between checks
     */
    public void startCheckingTime() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ensure the fragment is in the resumed state and is still attached to its activity
                if (isAdded() && isResumed() && parentTimeIsOn) {
                    showPauseDialog(getContext());
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                // Re-run the check after each interval if the fragment is still visible and active
                handler.postDelayed(this, CHECK_INTERVAL);
            }
        }, CHECK_INTERVAL);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Start the handler when the fragment is resumed (when the user comes back)
        startCheckingTime();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop the handler when the fragment is paused or no longer visible
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up to prevent memory leaks
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * Displays a custom pause dialog to the user.
     * <p>
     * This dialog is used to show a warning message when a session has expired.
     * The dialog includes a header, message, and a button to dismiss the dialog.
     * </p>
     *
     * @param context the context in which the dialog should be displayed
     */
    public void showPauseDialog(Context context) {
        // Create a dialog instance
        Dialog dialog = new Dialog(context, R.style.CustomDialogTheme);
        // Set the content view to your custom layout
        dialog.setContentView(R.layout.warning_dialog);
//      init __________________________________________________________________
        TextView warningHeader = dialog.findViewById(R.id.warningHeader);
        TextView warningMessage = dialog.findViewById(R.id.warningMessage);
        Button warningBtn = dialog.findViewById(R.id.warningBtn);

//      On load __________________________________________________________________
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




}

