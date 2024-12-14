package com.example.tetris_concept;

import static com.example.tetris_concept.AnimFunctions.setupBackgroundAnimation;
import static com.example.tetris_concept.AppliedSettings.btnSoundFx;
import static com.example.tetris_concept.AppliedSettings.hideNavigationBar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * The {@code AboutFragment} class represents a fragment in the application that displays the "About" section.
 * It includes functionalities for animating the background, hiding the navigation bar, and handling
 * a back button click event to navigate to the previous screen.
 *
 * <p>This fragment inflates its layout from {@code fragment_about.xml} and uses an ImageView
 * for background animations and a Button for navigation.</p>
 *
 * @author Collin Hill
 */
public class AboutFragment extends Fragment {

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater  The {@link LayoutInflater} object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     *                  The fragment should not add the view itself but use this to apply layout parameters.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The {@link View} for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // Find views from the layout
        ImageView abtFragBg = view.findViewById(R.id.abtFragBg);
        Button backBtn = view.findViewById(R.id.backBtn);

        // Hide the navigation bar for immersive UI
        hideNavigationBar(view);

        // Set up background animation on the ImageView
        setupBackgroundAnimation(abtFragBg);

        // Set a click listener on the back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the back button click event.
             *
             * <p>Plays a button sound effect and navigates the user to the previous screen.</p>
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                btnSoundFx(view.getContext()); // Play button click sound effect
                requireActivity().onBackPressed(); // Navigate back
            }
        });

        return view;
    }
}
