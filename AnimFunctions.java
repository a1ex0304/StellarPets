package com.example.tetris_concept;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Utility class providing functions for animating UI elements in the application.
 *
 * <p>This class includes methods for creating animations such as background image shifting
 * and resizing an ImageView's height dynamically based on screen dimensions.</p>
 *
 * <p>All animations are designed with smooth interpolators for a better user experience.</p>
 *
 * @author Aryan Farhang-pour
 */
public class AnimFunctions {

    /**
     * Animates the background image by shifting its position horizontally in a loop.
     *
     * @param backgroundImage the ImageView containing the background image to animate
     */
    public static void setupBackgroundAnimation(ImageView backgroundImage) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(backgroundImage, "translationX", -100f, 100f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(8000);  // Duration in milliseconds
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.start();
    }

    /**
     * Animates the height of an ImageView to 50% of the screen height.
     *
     * @param imageView the ImageView whose height will be animated
     * @param context   the context used to get display metrics
     * @param duration  the duration of the animation in milliseconds
     */
    public static void animateImageViewHeight(final ImageView imageView, Context context, int duration) {
        // Get the height of the screen using the context's window manager
        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (context != null) {
            context.getSystemService(Context.WINDOW_SERVICE);
            displayMetrics = context.getResources().getDisplayMetrics();
        }

        // Convert screen height from pixels to dp (if you prefer to work with dp)
        float density = displayMetrics.density;  // Get the screen density
        int screenHeightPx = displayMetrics.heightPixels;  // Screen height in pixels
        int screenHeightDp = (int) (screenHeightPx / density);  // Convert pixels to dp

        // Calculate the target height (50% of screen height in dp)
        final int targetHeightDp = screenHeightDp / 2;

        // Convert targetHeightDp back to pixels for the animation
        final int targetHeightPx = (int) (targetHeightDp * density);

        // Get the current height of the ImageView
        int currentHeight = imageView.getLayoutParams().height;

        // If the height is initially set to 0 (as in your layout), update the current height
        if (currentHeight == ViewGroup.LayoutParams.WRAP_CONTENT || currentHeight <= 0) {
            currentHeight = 1; // Set it to 1 to avoid division issues in animation
        }

        // Create a ValueAnimator to animate between the current height and target height
        ValueAnimator heightAnimator = ValueAnimator.ofInt(currentHeight, targetHeightPx);
        heightAnimator.setDuration(duration); // Duration of the animation in milliseconds
        heightAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // Smooth animation

        // Update the height of the ImageView as the animation progresses
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedHeight = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.height = animatedHeight;
                imageView.setLayoutParams(layoutParams);
            }
        });

        // Start the animation
        heightAnimator.start();
    }



    /**
     * Resets the height of an {@link ImageView} to its original size with a smooth animation.
     *
     * <p>This method animates the transition of the {@code ImageView}'s height from its current value
     * back to its original height, which is typically {@code WRAP_CONTENT}. The animation duration
     * and smoothness can be customized through the parameters.</p>
     *
     * @param imageView The {@link ImageView} whose height is being reset.
     * @param context   The {@link Context} used to fetch resources or perform additional calculations, if needed.
     * @param duration  The duration of the animation in milliseconds.
     */

    public static void resetImageViewHeight(final ImageView imageView, Context context, int duration) {
        // Get the current height of the ImageView
        int currentHeight = imageView.getLayoutParams().height;

        // Get the original height of the ImageView from its layout parameters
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        final int originalHeight = ViewGroup.LayoutParams.WRAP_CONTENT; // Default back to WRAP_CONTENT or specific value

        // Create a ValueAnimator to animate from current height back to original height
        ValueAnimator resetHeightAnimator = ValueAnimator.ofInt(currentHeight, originalHeight);
        resetHeightAnimator.setDuration(duration); // Duration of the reset animation in milliseconds
        resetHeightAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // Smooth animation

        // Update the height of the ImageView as the reset animation progresses
        resetHeightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedHeight = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.height = animatedHeight;
                imageView.setLayoutParams(layoutParams);
            }
        });

        // Start the reset animation
        resetHeightAnimator.start();
    }


    /**
     * Animates the visibility of a ConstraintLayout by fading it in.
     *
     * @param layout   the ConstraintLayout to animate
     * @param duration the duration of the fade-in animation in milliseconds
     */
    public static void animateConstraintLayoutVisible(final ViewGroup layout, long duration) {
        // If the layout is GONE, first set it to VISIBLE and set the alpha to 0 (invisible)
        if (layout.getVisibility() == View.GONE) {
            layout.setAlpha(0f);  // Initially transparent
            layout.setVisibility(View.VISIBLE);  // Make it visible but still transparent
        }

        // Animate the alpha property to 1 (fully visible) over the specified duration
        layout.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);  // No listener needed for visibility, as we're showing it
    }

    /**
     * Animates the disappearance of a {@link ViewGroup} (such as a {@link ConstraintLayout}) by fading it out
     * to full transparency and then setting its visibility to {@link View#GONE}.
     *
     * <p>This method gradually reduces the alpha of the layout to 0 (fully invisible) over a specified duration,
     * providing a smooth fade-out effect. Once the animation completes, the layout's visibility is set to {@code GONE},
     * effectively removing it from the layout hierarchy.</p>
     *
     * @param layout   The {@link ViewGroup} to animate, typically a {@link ConstraintLayout}.
     * @param duration The duration of the fade-out animation in milliseconds.
     */

    public static void animateConstraintLayoutGone(final ViewGroup layout, long duration) {
        // Animate the alpha property to 0 (fully invisible) over the specified duration
        layout.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Once the animation ends, set the layout visibility to GONE
                        layout.setVisibility(View.GONE);
                    }
                });
    }


    /**
     * Animates the disappearance of a {@link ViewGroup} (such as a {@link ConstraintLayout}) by fading it out
     * to full transparency after a specified delay and then sets its visibility to {@link View#GONE}.
     *
     * <p>This method posts a delayed action that, after the specified delay, animates the layout's alpha
     * to 0 (fully invisible) over the given duration. Once the animation completes, the layout's visibility
     * is set to {@code GONE}, effectively removing it from the layout hierarchy.</p>
     *
     * @param layout The {@link ViewGroup} to animate, typically a {@link ConstraintLayout}.
     * @param duration The duration of the fade-out animation in milliseconds.
     * @param delay The delay in milliseconds before the animation starts.
     */

    public static void animateConstraintLayoutGone(final ViewGroup layout, long duration, long delay) {
        // Post the animation with a delay
        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Animate the alpha property to 0 (fully invisible) over the specified duration
                layout.animate()
                        .alpha(0f)
                        .setDuration(duration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                // Once the animation ends, set the layout visibility to GONE
                                layout.setVisibility(View.GONE);
                            }
                        });
            }
        }, delay); // Delay before starting the animation
    }

    /**
     * Transitions the background color of a {@link ViewGroup} from one color to another with a smooth animation.
     *
     * <p>This method animates the background color change of the provided {@code ViewGroup} from a specified
     * starting color to an ending color. The color transition is smooth and occurs over a given duration,
     * with an optional delay before the animation starts.</p>
     *
     * @param viewGroup The {@link ViewGroup} whose background color will be animated.
     * @param colorFromHex The starting color in hexadecimal format (e.g., "#FFFFFF").
     * @param colorToHex The target color in hexadecimal format (e.g., "#000000").
     * @param duration The duration of the color transition animation in milliseconds.
     * @param delay The delay before the animation starts, in milliseconds.
     */

    public static void transitionBackgroundColor(final ViewGroup viewGroup, String colorFromHex, String colorToHex, long duration, long delay) {
        // Parse the hexadecimal colors into integer values
        int colorFrom = Color.parseColor(colorFromHex);
        int colorTo = Color.parseColor(colorToHex);

        // Create a Handler to apply the delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an ObjectAnimator to animate the background color
                ObjectAnimator colorAnimation = ObjectAnimator.ofArgb(viewGroup, "backgroundColor", colorFrom, colorTo);
                colorAnimation.setDuration(duration); // Set the duration of the animation
                colorAnimation.start(); // Start the animation
            }
        }, delay); // Delay in milliseconds
    }

    /**
     * Applies a gradient animation to a TextView, cycling through a set of colors.
     *
     * @param view the view containing the TextView to which the gradient will be applied
     */
    public static void applyTextGradient(View view) {
        TextView loadingStellarPetsText = view.findViewById(R.id.loadingStellarPetsText1);

        // Define the color array for the loading stellar pets text
        int[] loadingColors = {Color.parseColor("#e200ff"), Color.parseColor("#F78CC2")};
//        // Create and apply the LinearGradient shader
//        applyGradientShader(loadingStellarPetsText, loadingColors);

        // Start the animation for loading stellar pets text
//        animateTextColor(loadingStellarPetsText, loadingColors);
    }

    /**
     * Applies a gradient shader to the text of a TextView.
     *
     * @param textView the TextView to which the gradient will be applied
     * @param colors   an array of colors to use in the gradient
     */
    public static void applyGradientShader(TextView textView, int[] colors) {
        Shader shader = new LinearGradient(0, 0, 0, textView.getTextSize(),
                colors, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(shader);
        textView.invalidate(); // Refresh the text view to apply the shader
    }

    /**
     * Animates the text color of a TextView by blending between multiple colors.
     *
     * @param textView the TextView whose text color will be animated
     */
    public static void animateTextColor(final TextView textView, final String colorHex1, final String colorHex2) {
        // Convert the hexadecimal colors to integers
        final int color1 = Color.parseColor(colorHex1);
        final int color2 = Color.parseColor(colorHex2);

        // Create a ValueAnimator to smoothly transition between the two colors
        ValueAnimator colorAnimator = ValueAnimator.ofFloat(0f, 1f); // Cycle between 0 and 1 for smooth blending
        colorAnimator.setDuration(3000); // Duration of the transition
        colorAnimator.setInterpolator(new LinearInterpolator()); // Linear progression
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE); // Loop infinitely
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE); // Reverse the animation to blend back

        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                float ratio = (float) animator.getAnimatedValue();

                // Interpolate between the two colors
                int blendedColor = blendColors(color1, color2, ratio);

                // Set the text color to the interpolated color
                textView.setTextColor(blendedColor);
            }
        });

        colorAnimator.start(); // Start the animation
    }

    /**
     * Animates the text color of a {@link TextView} by smoothly transitioning between two colors.
     *
     * <p>This method animates the transition of the {@code TextView}'s text color from the first specified
     * color to the second, with smooth blending. The animation runs indefinitely, alternating between the two colors.</p>
     *
     * @param textView The {@link TextView} whose text color will be animated.
     * @param colorHex1 The starting color in hexadecimal format (e.g., "#FF0000").
     * @param colorHex2 The target color in hexadecimal format (e.g., "#0000FF").
     * @param duration The duration of each cycle of the color transition in milliseconds.
     */

    public static void animateTextColor(final TextView textView, final String colorHex1, final String colorHex2, int duration) {
        // Convert the hexadecimal colors to integers
        final int color1 = Color.parseColor(colorHex1);
        final int color2 = Color.parseColor(colorHex2);

        // Create a ValueAnimator to smoothly transition between the two colors
        ValueAnimator colorAnimator = ValueAnimator.ofFloat(0f, 1f); // Cycle between 0 and 1 for smooth blending
        colorAnimator.setDuration(duration); // Duration of the transition
        colorAnimator.setInterpolator(new LinearInterpolator()); // Linear progression
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE); // Loop infinitely
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE); // Reverse the animation to blend back

        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                float ratio = (float) animator.getAnimatedValue();

                // Interpolate between the two colors
                int blendedColor = blendColors(color1, color2, ratio);

                // Set the text color to the interpolated color
                textView.setTextColor(blendedColor);
            }
        });

        colorAnimator.start(); // Start the animation
    }



    /**
     * Blends two colors together based on a given ratio.
     *
     * @param color1 the first color to blend
     * @param color2 the second color to blend
     * @param ratio  the ratio of blending, between 0 and 1
     * @return the resulting blended color
     */
    public static int blendColors(int color1, int color2, float ratio) {
        float inverseRatio = 1 - ratio;
        float r = Color.red(color1) * inverseRatio + Color.red(color2) * ratio;
        float g = Color.green(color1) * inverseRatio + Color.green(color2) * ratio;
        float b = Color.blue(color1) * inverseRatio + Color.blue(color2) * ratio;
        return Color.rgb((int) r, (int) g, (int) b);
    }



    /**
     * Starts a countdown timer that updates a {@link TextView} with the remaining time.
     *
     * <p>This method initiates a countdown timer that decreases every second, displaying the remaining
     * time in the provided {@code TextView}. The countdown starts after the specified delay.</p>
     *
     * @param countdownText The {@link TextView} to display the remaining time in seconds.
     * @param seconds The total time in seconds for the countdown.
     * @param delay The delay before starting the countdown, in milliseconds.
     */

    public static void startCountdown(TextView countdownText, int seconds, int delay) {
        // Convert seconds to milliseconds
        long duration = seconds * 1000;

        // Create a new Handler
        Handler handler = new Handler();

        // Post a delayed runnable to start the countdown after the specified delay
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create a new CountDownTimer
                new CountDownTimer(duration, 1000) { // 1000 ms = 1 second interval

                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Update the TextView with the remaining time
                        countdownText.setText(String.valueOf(millisUntilFinished / 1000));
                    }

                    @Override
                    public void onFinish() {
                        // When the countdown finishes, display "Done"

                    }

                }.start();
            }
        }, delay); // Delay specified by the parameter
    }

    /**
     * Animates the text color of a {@link TextView} through a smooth rainbow transition.
     *
     * <p>This method animates the text color of the provided {@code TextView}, cycling through a rainbow
     * sequence (red, magenta, blue, cyan, green, yellow) with smooth blending between colors. The animation
     * runs indefinitely, creating a rainbow effect.</p>
     *
     * @param textView The {@link TextView} whose text color will be animated through the rainbow colors.
     */

    public static void animateRainbowText(final TextView textView) {
        // Define the colors of the rainbow
        final int[] rainbowColors = {
                Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED
        };

        // Create a ValueAnimator to animate between the colors
        ValueAnimator colorAnimator = ValueAnimator.ofFloat(0, rainbowColors.length - 1);
        colorAnimator.setDuration(100); // Set duration of one full cycle (1 second)
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE); // Repeat indefinitely
        colorAnimator.setRepeatMode(ValueAnimator.RESTART); // Restart the animation after each cycle

        // Update the text color during the animation
        colorAnimator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            int colorIndex = (int) animatedValue;
            int nextColorIndex = (colorIndex + 1) % rainbowColors.length;
            float fraction = animatedValue - colorIndex;

            // Interpolate between the two colors
            int blendedColor = blendColorsRainBow(rainbowColors[colorIndex], rainbowColors[nextColorIndex], fraction);
            textView.setTextColor(blendedColor);
        });

        colorAnimator.start();
    }

    /**
     * Blends two colors together based on the specified ratio.
     *
     * <p>This method performs a linear interpolation between two colors, blending them based on the
     * provided ratio. The ratio determines the mix of the two colors, where a ratio of 0 will return
     * the first color and a ratio of 1 will return the second color.</p>
     *
     * @param color1 The first color to blend.
     * @param color2 The second color to blend.
     * @param ratio The ratio of blending between the two colors. A value between 0 and 1, where 0
     *              means fully {@code color1} and 1 means fully {@code color2}.
     * @return The resulting blended color as an integer ARGB value.
     */

    private static int blendColorsRainBow(int color1, int color2, float ratio) {
        int alpha = (int) (Color.alpha(color1) * (1 - ratio) + Color.alpha(color2) * ratio);
        int red = (int) (Color.red(color1) * (1 - ratio) + Color.red(color2) * ratio);
        int green = (int) (Color.green(color1) * (1 - ratio) + Color.green(color2) * ratio);
        int blue = (int) (Color.blue(color1) * (1 - ratio) + Color.blue(color2) * ratio);
        return Color.argb(alpha, red, green, blue);
    }





    /**
     * Animates the background color change of multiple CardViews in a smooth transition.
     *
     * <p>This method uses a ValueAnimator to interpolate between two colors, transitioning the
     * background color of each CardView from a start color to an end color, and reversing the
     * animation in an infinite loop. The animation duration is set to 3 seconds for each cycle.</p>
     *
     * @param cardView1 The first CardView whose background color will be animated.
     * @param cardView2 The second CardView whose background color will be animated.
     * @param cardView3 The third CardView whose background color will be animated.
     * @param cardView4 The fourth CardView whose background color will be animated.
     */

    public static void animateColorChange(CardView cardView1, CardView cardView2, CardView cardView3, CardView cardView4) {
        // Define the colors to transition between
        int colorStart = 0xFFFF0000; // Red color
        int colorEnd = 0xFF0000FF;   // Blue color

        // Create a ValueAnimator that interpolates between two colors
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorStart, colorEnd);
        colorAnimator.setDuration(3000); // Duration for one cycle (in milliseconds)
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE); // Infinite loop
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE); // Reverse the animation on repeat

        // Update the background color of each CardView on each animation frame
        colorAnimator.addUpdateListener(animator -> {
            int animatedColor = (int) animator.getAnimatedValue();
            cardView1.setCardBackgroundColor(animatedColor);
            cardView2.setCardBackgroundColor(animatedColor);
            cardView3.setCardBackgroundColor(animatedColor);
            cardView4.setCardBackgroundColor(animatedColor);
        });

        // Start the animation
        colorAnimator.start();
    }


    /**
     * Animates the text color change of two TextViews in a smooth transition.
     *
     * <p>This method uses a ValueAnimator to interpolate between two colors, transitioning the text
     * color of each TextView from a start color to an end color, and reversing the animation in an
     * infinite loop. The animation duration is set to 500 milliseconds for rapid color changes.</p>
     *
     * @param textView1 The first TextView whose text color will be animated.
     * @param textView2 The second TextView whose text color will be animated.
     */

    public static void animateTextColorChange(TextView textView1, TextView textView2) {
        // Define the colors to transition between (for example, red and blue)
        int colorStart = 0xFF3FFFF3; // #3FFFF3 (light cyan)
        int colorEnd = 0xFFFF3FE8;   // #FF3FE8 (magenta)

        // Create a ValueAnimator to interpolate between the two colors
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorStart, colorEnd);
        colorAnimator.setDuration(500); // Short duration for rapid color change (in milliseconds)
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE); // Infinite loop to continuously change colors
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE); // Reverse the animation after each cycle

        // Update the text color of each TextView on each animation frame
        colorAnimator.addUpdateListener(animator -> {
            int animatedColor = (int) animator.getAnimatedValue();
            textView1.setTextColor(animatedColor);
            textView2.setTextColor(animatedColor);
        });

        // Start the color animation
        colorAnimator.start();
    }




    //____________________________________________________________________________________________

    //___________________________PET ANIMATION____________________________________________________

    //FLAME PET ANIMATION:________________________________________

    /**
     * Starts a frame-by-frame animation for a pet running.
     *
     * <p>This method sets the image resource for the provided ImageView to a frame animation
     * that represents the pet running. The frames are processed to disable bitmap filtering
     * for better performance, and the animation is started.</p>
     *
     * @param animatedImageView The ImageView where the frame animation will be displayed.
     */

    public static void flamePetRunAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fire_run_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies an idle animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the idle animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the idle animation to.
     */

    public static void flamePetIdleAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fire_idle_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies a shot animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the shot animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the shot animation to.
     */

    public static void flamePetShotAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fire_shot_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies a hurt shot animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the hurt shot animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the hurt shot animation to.
     */

    public static void flameHurtShotAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fire_hurt_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies a sleeping animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the sleeping animation,
     * applies a scaling transformation to the ImageView, and starts the frame animation.
     * The animation frames are adjusted to prevent bitmap filtering.</p>
     *
     * @param animatedImageView The ImageView to apply the sleeping animation to.
     */

    public static void flameSleepAnim(ImageView animatedImageView) {
        animatedImageView.setTranslationX(50f); // Move 50 pixels to the right
        animatedImageView.setImageResource(R.drawable.fire_sleep_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Start the initial animation
        frameAnimation.start();
    }


    private static final int ANIMATION_DURATION = 2000; // Duration each animation plays in milliseconds
    private static Handler animationHandler = new Handler();

    /**
     * Starts an infinite loop of animations for a pet in various states.
     *
     * <p>This method applies transformations (scaling and translation) to the provided
     * ImageView, then alternates between different animations representing the pet
     * in various states such as idle, sleep, and other actions. The loop continues indefinitely
     * by scheduling the next animation after each cycle.</p>
     *
     * @param animatedImageView The ImageView that will display the pet animations.
     */

    public static void playInfiniteFireAnimation(final ImageView animatedImageView) {

        animationHandler.removeCallbacksAndMessages(null);
        animatedImageView.setScaleX(-1.0f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.0f); // Scale height by 1.5 times
        animatedImageView.setTranslationX(0f); // Move 50 pixels to the right


        // Runnable to switch between animations
        Runnable animationRunnable = new Runnable() {
            int animationIndex = 0;

            @Override
            public void run() {
                switch (animationIndex) {
                    case 0:
                        flamePetRunAnim(animatedImageView);
                        break;
                    case 1:
                        flamePetIdleAnim(animatedImageView);
                        break;
                    case 2:
                        flamePetShotAnim(animatedImageView);
                        break;
                    case 3:
                        flameHurtShotAnim(animatedImageView);
                        break;
                }

                // Move to the next animation
                animationIndex = (animationIndex + 1) % 4;

                // Schedule the next animation after the duration
                animationHandler.postDelayed(this, ANIMATION_DURATION);
            }
        };

        // Start the infinite animation loop
        animationHandler.post(animationRunnable);
    }

    /**
     * Starts an infinite loop of animations for a pet in various states. v4
     *
     * <p>This method applies transformations (scaling and translation) to the provided
     * ImageView, then alternates between different animations representing the pet
     * in various states such as idle, sleep, and other actions. The loop continues indefinitely
     * by scheduling the next animation after each cycle.</p>
     *
     * @param animatedImageView The ImageView that will display the pet animations.
     */

    public void playInfiniteFireAnimationV4(final ImageView animatedImageView) {

//
//        animationHandler.removeCallbacksAndMessages(null);
        animatedImageView.setScaleX(-1.0f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.0f); // Scale height by 1.5 times
        animatedImageView.setTranslationY(30f); // Adjust the value as needed
        animatedImageView.setTranslationX(0f); // Move 50 pixels to the right


        // Runnable to switch between animations
        Runnable animationRunnable = new Runnable() {
            int animationIndex = 0;

            @Override
            public void run() {
                switch (animationIndex) {
                    case 0:
                        flamePetRunAnim(animatedImageView);
                        break;
                    case 1:
                        flamePetIdleAnim(animatedImageView);
                        break;
                    case 2:
                        flamePetShotAnim(animatedImageView);
                        break;
                    case 3:
                        flameHurtShotAnim(animatedImageView);
                        break;
                }

                // Move to the next animation
                animationIndex = (animationIndex + 1) % 4;

                // Schedule the next animation after the duration
                animationHandler.postDelayed(this, ANIMATION_DURATION);
            }
        };

        // Start the infinite animation loop
        animationHandler.post(animationRunnable);

    }

    /**
     * Starts an infinite loop of animations for a pet in various states. v2
     *
     * <p>This method applies transformations (scaling and translation) to the provided
     * ImageView, then alternates between different animations representing the pet
     * in various states such as idle, sleep, and other actions. The loop continues indefinitely
     * by scheduling the next animation after each cycle.</p>
     *
     * @param animatedImageView The ImageView that will display the pet animations.
     */

    public static void playInfiniteFireAnimationV2(final ImageView animatedImageView) {

        animationHandler.removeCallbacksAndMessages(null);
        animatedImageView.setScaleX(-1.4f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.4f); // Scale height by 1.5 times
        animatedImageView.setTranslationY(-50f); // Adjust the value as needed
        animatedImageView.setTranslationX(0f); // Adjust the value as needed


        // Runnable to switch between animations
        Runnable animationRunnable = new Runnable() {
            int animationIndex = 0;

            @Override
            public void run() {
                switch (animationIndex) {
                    case 0:
                        flamePetRunAnim(animatedImageView);
                        break;
                    case 1:
                        flamePetIdleAnim(animatedImageView);
                        break;
                    case 2:
                        flamePetShotAnim(animatedImageView);
                        break;
                    case 3:
                        flameHurtShotAnim(animatedImageView);
                        break;
                }

                // Move to the next animation
                animationIndex = (animationIndex + 1) % 4;

                // Schedule the next animation after the duration
                animationHandler.postDelayed(this, ANIMATION_DURATION);
            }
        };

        // Start the infinite animation loop
        animationHandler.post(animationRunnable);
    }


    //FOX PET ANIMATION:________________________________________

    /**
     * Starts a frame-by-frame animation for a pet running.
     *
     * <p>This method sets the image resource for the provided ImageView to a frame animation
     * that represents the pet running. The frames are processed to disable bitmap filtering
     * for better performance, and the animation is started.</p>
     *
     * @param animatedImageView The ImageView where the frame animation will be displayed.
     */

    public static void foxPetRunAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fox_run_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Starts a frame-by-frame animation for a pet running. V2
     *
     * <p>This method sets the image resource for the provided ImageView to a frame animation
     * that represents the pet running. The frames are processed to disable bitmap filtering
     * for better performance, and the animation is started.</p>
     *
     * @param animatedImageView The ImageView where the frame animation will be displayed.
     */

    public static void foxPetRunAnimV2(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fox_run_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }
        animatedImageView.setScaleX(-0.8f); // Scale width by 1.5 times
        animatedImageView.setScaleY(0.8f); // Scale height by 1.5 times
        animatedImageView.setTranslationX(-10f); // Adjust the value as needed
        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies an idle animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the idle animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the idle animation to.
     */

    public static void foxPetIdleAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fox_idle_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        animatedImageView.setScaleX(-0.8f); // Scale width by 1.5 times
        animatedImageView.setScaleY(0.8f); // Scale height by 1.5 times
//        animatedImageView.setTranslationY(30f); // Adjust the value as needed

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies an idle animation to the provided ImageView using a frame-by-frame animation. v4
     *
     * <p>This method sets the appropriate image resource for the idle animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the idle animation to.
     */

    public static void foxPetIdleAnimV4(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fox_idle_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        animatedImageView.setScaleX(-0.8f); // Scale width by 1.5 times
        animatedImageView.setScaleY(0.8f); // Scale height by 1.5 times
        animatedImageView.setTranslationX(-15f); // Adjust the value as needed

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies an idle animation to the provided ImageView using a frame-by-frame animation. v3
     *
     * <p>This method sets the appropriate image resource for the idle animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the idle animation to.
     */

    public static void foxPetIdleAnimV3(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fox_idle_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        animatedImageView.setScaleX(0.8f); // Scale width by 1.5 times
        animatedImageView.setScaleY(0.8f); // Scale height by 1.5 times
//        animatedImageView.setTranslationY(30f); // Adjust the value as needed

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies an idle animation to the provided ImageView using a frame-by-frame animation. v2
     *
     * <p>This method sets the appropriate image resource for the idle animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the idle animation to.
     */

    public static void foxPetIdleAnimV2(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fox_idle_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }


//        animatedImageView.setTranslationY(30f); // Adjust the value as needed

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies a shot animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the shot animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the shot animation to.
     */

    public static void foxPetShotAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fox_shot_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies a shot animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the shot animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the shot animation to.
     */

    public static void foxHurtShotAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fox_hurt_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies a shot animation to the provided ImageView using a frame-by-frame animation. v2
     *
     * <p>This method sets the appropriate image resource for the shot animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the shot animation to.
     */

    /**
     * Applies a hurt shot animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the hurt shot animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the hurt shot animation to.
     */

    public static void foxHurtShotAnimV2(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.fox_hurt_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }
        animatedImageView.setScaleX(-0.8f); // Scale width by 1.5 times
        animatedImageView.setScaleY(0.8f); // Scale height by 1.5 times
        animatedImageView.setTranslationX(2f); // Adjust the value as needed
        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies a sleeping animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the sleeping animation,
     * applies a scaling transformation to the ImageView, and starts the frame animation.
     * The animation frames are adjusted to prevent bitmap filtering.</p>
     *
     * @param animatedImageView The ImageView to apply the sleeping animation to.
     */

    public static void foxSleepAnim(ImageView animatedImageView) {

        animatedImageView.setScaleX(-0.8f); // Scale width by 1.5 times
        animatedImageView.setScaleY(0.8f); // Scale height by 1.5 times
        animatedImageView.setTranslationY(30f); // Adjust the value as needed
        animatedImageView.setTranslationX(30f); // Move 50 pixels to the right

        animatedImageView.setImageResource(R.drawable.fox_sleep_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Start the initial animation
        frameAnimation.start();
    }


    /**
     * Starts an infinite loop of animations for a pet in various states.
     *
     * <p>This method applies transformations (scaling and translation) to the provided
     * ImageView, then alternates between different animations representing the pet
     * in various states such as idle, sleep, and other actions. The loop continues indefinitely
     * by scheduling the next animation after each cycle.</p>
     *
     * @param animatedImageView The ImageView that will display the pet animations.
     */

    public static void playInfiniteFoxAnimation(final ImageView animatedImageView) {

        animationHandler.removeCallbacksAndMessages(null);
        animatedImageView.setScaleX(-0.8f); // Scale width by 1.5 times
        animatedImageView.setScaleY(0.8f); // Scale height by 1.5 times
        animatedImageView.setTranslationY(30f); // Adjust the value as needed
        animatedImageView.setTranslationX(0f); // Move 50 pixels to the right


        // Runnable to switch between animations
        Runnable animationRunnable = new Runnable() {
            int animationIndex = 0;

            @Override
            public void run() {
                switch (animationIndex) {
                    case 0:
                        foxPetRunAnim(animatedImageView);
                        break;
                    case 1:
                        foxPetIdleAnim(animatedImageView);
                        break;
                    case 2:
                        foxPetShotAnim(animatedImageView);
                        break;
                    case 3:
                        foxHurtShotAnim(animatedImageView);
                        break;
                }

                // Move to the next animation
                animationIndex = (animationIndex + 1) % 4;

                // Schedule the next animation after the duration
                animationHandler.postDelayed(this, ANIMATION_DURATION);
            }
        };

        // Start the infinite animation loop
        animationHandler.post(animationRunnable);
    }

    /**
     * Starts an infinite loop of animations for a pet in various states. v3
     *
     * <p>This method applies transformations (scaling and translation) to the provided
     * ImageView, then alternates between different animations representing the pet
     * in various states such as idle, sleep, and other actions. The loop continues indefinitely
     * by scheduling the next animation after each cycle.</p>
     *
     * @param animatedImageView The ImageView that will display the pet animations.
     */

    public void playInfiniteFoxAnimationV3(final ImageView animatedImageView) {

        animatedImageView.setScaleX(-0.8f); // Scale width by 1.5 times
        animatedImageView.setScaleY(0.8f); // Scale height by 1.5 times
        animatedImageView.setTranslationY(30f); // Adjust the value as needed
        animatedImageView.setTranslationX(0f); // Move 50 pixels to the right


        // Runnable to switch between animations
        Runnable animationRunnable = new Runnable() {
            int animationIndex = 0;

            @Override
            public void run() {
                switch (animationIndex) {
                    case 0:
                        foxPetRunAnim(animatedImageView);
                        break;
                    case 1:
                        foxPetIdleAnim(animatedImageView);
                        break;
                    case 2:
                        foxPetShotAnim(animatedImageView);
                        break;
                    case 3:
                        foxHurtShotAnim(animatedImageView);
                        break;
                }

                // Move to the next animation
                animationIndex = (animationIndex + 1) % 4;

                // Schedule the next animation after the duration
                animationHandler.postDelayed(this, ANIMATION_DURATION);
            }
        };

        // Start the infinite animation loop
        animationHandler.post(animationRunnable);
    }

    /**
     * Starts an infinite loop of animations for a pet in various states. v2
     *
     * <p>This method applies transformations (scaling and translation) to the provided
     * ImageView, then alternates between different animations representing the pet
     * in various states such as idle, sleep, and other actions. The loop continues indefinitely
     * by scheduling the next animation after each cycle.</p>
     *
     * @param animatedImageView The ImageView that will display the pet animations.
     */

    public static void playInfiniteFoxAnimationV2(final ImageView animatedImageView) {

        animationHandler.removeCallbacksAndMessages(null);
        animatedImageView.setScaleX(-1.16f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.16f); // Scale height by 1.5 times
        animatedImageView.setTranslationY(-30f); // Adjust the value as needed
        animatedImageView.setTranslationX(0); // Adjust the value as needed


        // Runnable to switch between animations
        Runnable animationRunnable = new Runnable() {
            int animationIndex = 0;

            @Override
            public void run() {
                switch (animationIndex) {
                    case 0:
                        foxPetRunAnim(animatedImageView);
                        break;
                    case 1:
                        foxPetIdleAnimV2(animatedImageView);
                        break;
                    case 2:
                        foxPetShotAnim(animatedImageView);
                        break;
                    case 3:
                        foxHurtShotAnim(animatedImageView);
                        break;
                }

                // Move to the next animation
                animationIndex = (animationIndex + 1) % 4;

                // Schedule the next animation after the duration
                animationHandler.postDelayed(this, ANIMATION_DURATION);
            }
        };

        // Start the infinite animation loop
        animationHandler.post(animationRunnable);
    }


    //SQUIRREL PET ANIMATION:________________________________________

    /**
     * Starts a frame-by-frame animation for a pet running.
     *
     * <p>This method sets the image resource for the provided ImageView to a frame animation
     * that represents the pet running. The frames are processed to disable bitmap filtering
     * for better performance, and the animation is started.</p>
     *
     * @param animatedImageView The ImageView where the frame animation will be displayed.
     */

    public static void squirrelPetRunAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.squirrel_run_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies an idle animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the idle animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the idle animation to.
     */

    public static void squirrelPetIdleAnim(ImageView animatedImageView) {
        // Set the image resource to the animation drawable
        animatedImageView.setImageResource(R.drawable.squirrel_idle_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();

        // Optional: Turn off bitmap filtering for sharper pixel scaling, if desired
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Apply scaling transformation to the ImageView to enlarge the animation
        animatedImageView.setScaleX(-1.7f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.7f); // Scale height by 1.5 times

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies an idle animation to the provided ImageView using a frame-by-frame animation. v3
     *
     * <p>This method sets the appropriate image resource for the idle animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the idle animation to.
     */

    public static void squirrelPetIdleAnimV3(ImageView animatedImageView) {
        // Set the image resource to the animation drawable
        animatedImageView.setImageResource(R.drawable.squirrel_idle_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();

        // Optional: Turn off bitmap filtering for sharper pixel scaling, if desired
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Apply scaling transformation to the ImageView to enlarge the animation
        animatedImageView.setScaleX(-1.7f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.7f); // Scale height by 1.5 times
        animatedImageView.setTranslationX(-15f); // Adjust the value as needed

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies an idle animation to the provided ImageView using a frame-by-frame animation. v2
     *
     * <p>This method sets the appropriate image resource for the idle animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the idle animation to.
     */

    public static void squirrelPetIdleAnimV2(ImageView animatedImageView) {
        // Set the image resource to the animation drawable
        animatedImageView.setImageResource(R.drawable.squirrel_idle_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();

        // Optional: Turn off bitmap filtering for sharper pixel scaling, if desired
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        // Apply scaling transformation to the ImageView to enlarge the animation
        animatedImageView.setScaleX(1.7f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.7f); // Scale height by 1.5 times
        animatedImageView.setTranslationX(50f); // Adjust the value as needed

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies a shot animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the shot animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the shot animation to.
     */

    public static void squirrelPetShotAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.squirrel_shot_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        animatedImageView.setScaleX(-1.7f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.7f); // Scale height by 1.5 times

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies a hurt shot animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the hurt shot animation,
     * applies necessary adjustments to prevent bitmap filtering, and starts the frame animation.</p>
     *
     * @param animatedImageView The ImageView to apply the hurt shot animation to.
     */

    public static void squirrelHurtShotAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.squirrel_hurt_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }

        animatedImageView.setScaleX(-1.7f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.7f); // Scale height by 1.5 times

        // Start the initial animation
        frameAnimation.start();
    }


    /**
     * Applies a sleeping animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the sleeping squirrel animation,
     * applies a scaling transformation to the ImageView, and starts the frame animation.
     * The animation frames are adjusted to prevent bitmap filtering.</p>
     *
     * @param animatedImageView The ImageView to apply the sleeping animation to.
     */

    public static void squirrelSleepAnim(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.squirrel_sleep_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }
        animatedImageView.setScaleX(-1.7f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.7f); // Scale height by 1.5 times

        // Start the initial animation
        frameAnimation.start();
    }

    /**
     * Applies a sleeping animation to the provided ImageView using a frame-by-frame animation.
     *
     * <p>This method sets the appropriate image resource for the sleeping squirrel animation,
     * applies a scaling transformation to the ImageView, and starts the frame animation.
     * The animation frames are adjusted to prevent bitmap filtering.</p>
     *
     * @param animatedImageView The ImageView to apply the sleeping animation to.
     */

    public static void squirrelSleepAnimV2(ImageView animatedImageView) {
        animatedImageView.setImageResource(R.drawable.squirrel_sleep_frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) animatedImageView.getDrawable();
        for (int i = 0; i < frameAnimation.getNumberOfFrames(); i++) {
            frameAnimation.getFrame(i).mutate().setFilterBitmap(false);
        }
        animatedImageView.setScaleX(-1.7f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.7f); // Scale height by 1.5 times
        animatedImageView.setTranslationX(-15f); // Adjust the value as needed

        // Start the initial animation
        frameAnimation.start();
    }


    /**
     * Starts an infinite loop of animations for a pet in various states.
     *
     * <p>This method applies transformations (scaling and translation) to the provided
     * ImageView, then alternates between different animations representing the pet
     * in various states such as idle, sleep, and other actions. The loop continues indefinitely
     * by scheduling the next animation after each cycle.</p>
     *
     * @param animatedImageView The ImageView that will display the pet animations.
     */

    public static void playInfiniteSquirrelAnimation(final ImageView animatedImageView) {
        // Runnable to switch between animations

        animationHandler.removeCallbacksAndMessages(null);
        animatedImageView.setScaleX(-1.7f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.7f); // Scale height by 1.5 times
        animatedImageView.setTranslationY(0f); // Adjust the value as needed
        animatedImageView.setTranslationX(0f); // Move 50 pixels to the right

        Runnable animationRunnable = new Runnable() {
            int animationIndex = 0;

            @Override
            public void run() {
                switch (animationIndex) {
                    case 0:
                        squirrelSleepAnim(animatedImageView);
                        break;
                    case 1:
                        squirrelPetIdleAnim(animatedImageView);
                        break;
                    case 2:
                        squirrelPetShotAnim(animatedImageView);
                        break;
                    case 3:
                        squirrelHurtShotAnim(animatedImageView);
                        break;
                }

                // Move to the next animation
                animationIndex = (animationIndex + 1) % 4;

                // Schedule the next animation after the duration
                animationHandler.postDelayed(this, ANIMATION_DURATION);
            }
        };

        // Start the infinite animation loop
        animationHandler.post(animationRunnable);
    }

    /**
     * Starts an infinite loop of animations for a pet in various states.v3
     *
     * <p>This method applies transformations (scaling and translation) to the provided
     * ImageView, then alternates between different animations representing the pet
     * in various states such as idle, sleep, and other actions. The loop continues indefinitely
     * by scheduling the next animation after each cycle.</p>
     *
     * @param animatedImageView The ImageView that will display the pet animations.
     */

    public void playInfiniteSquirrelAnimationV3(final ImageView animatedImageView) {
        // Runnable to switch between animations

        animatedImageView.setScaleX(-1.7f); // Scale width by 1.5 times
        animatedImageView.setScaleY(1.7f); // Scale height by 1.5 times
        animatedImageView.setTranslationX(0f); // Move 50 pixels to the right



        Runnable animationRunnable = new Runnable() {
            int animationIndex = 0;

            @Override
            public void run() {
                switch (animationIndex) {
                    case 0:
                        squirrelSleepAnim(animatedImageView);
                        break;
                    case 1:
                        squirrelPetIdleAnim(animatedImageView);
                        break;
                    case 2:
                        squirrelPetShotAnim(animatedImageView);
                        break;
                    case 3:

                        squirrelHurtShotAnim(animatedImageView);

                        break;
                }

                // Move to the next animation
                animationIndex = (animationIndex + 1) % 4;

                // Schedule the next animation after the duration
                animationHandler.postDelayed(this, ANIMATION_DURATION);
            }
        };

        // Start the infinite animation loop
        animationHandler.post(animationRunnable);
    }


    /**
     * Starts an infinite loop of animations for a pet in various states.v2
     *
     * <p>This method applies transformations (scaling and translation) to the provided
     * ImageView, then alternates between different animations representing the pet
     * in various states such as idle, sleep, and other actions. The loop continues indefinitely
     * by scheduling the next animation after each cycle.</p>
     *
     * @param animatedImageView The ImageView that will display the pet animations.
     */

    public static void playInfiniteSquirrelAnimationV2(final ImageView animatedImageView) {
        // Runnable to switch between animations

        animationHandler.removeCallbacksAndMessages(null);
        animatedImageView.setScaleX(-2.5f); // Scale width by 1.5 times
        animatedImageView.setScaleY(2.5f); // Scale height by 1.5 times
        animatedImageView.setTranslationY(-30f); // Adjust the value as needed
        animatedImageView.setTranslationX(30f); // Adjust the value as needed


        Runnable animationRunnable = new Runnable() {
            int animationIndex = 0;

            @Override
            public void run() {
                switch (animationIndex) {
                    case 0:
                        squirrelSleepAnim(animatedImageView);
                        animatedImageView.setScaleX(-2.5f); // Scale width by 1.5 times
                        animatedImageView.setScaleY(2.5f); // Scale height by 1.5 times
                        animatedImageView.setTranslationY(-30f); // Adjust the value as needed
                        animatedImageView.setTranslationX(-30f); // Adjust the value as needed

                        break;
                    case 1:
                        squirrelPetIdleAnim(animatedImageView);
                        animatedImageView.setScaleX(-2.5f); // Scale width by 1.5 times
                        animatedImageView.setScaleY(2.5f); // Scale height by 1.5 times
                        animatedImageView.setTranslationY(-30f); // Adjust the value as needed
                        animatedImageView.setTranslationX(-30f); // Adjust the value as needed

                        break;
                    case 2:
//
                        squirrelPetShotAnim(animatedImageView);
                        animatedImageView.setTranslationX(-30f); // Adjust the value as needed


                        break;
                    case 3:

                        squirrelHurtShotAnim(animatedImageView);
                        animatedImageView.setScaleX(-2.5f); // Scale width by 1.5 times
                        animatedImageView.setScaleY(2.5f); // Scale height by 1.5 times
                        animatedImageView.setTranslationY(30f); // Adjust the value as needed
                        animatedImageView.setTranslationX(-30f); // Adjust the value as needed

                        break;
                }

                // Move to the next animation
                animationIndex = (animationIndex + 1) % 4;

                // Schedule the next animation after the duration
                animationHandler.postDelayed(this, ANIMATION_DURATION);
            }
        };

        // Start the infinite animation loop
        animationHandler.post(animationRunnable);
    }


    //___________________________PET ANIMATION____________________________________________________
    /**
     * Animates a shake effect on the provided ImageView by moving it left and right.
     *
     * <p>This method creates a TranslateAnimation that moves the ImageView back and forth
     * along the X-axis. The shake effect is repeated a specified number of times,
     * with each shake lasting 100 milliseconds.</p>
     *
     * @param imageView The ImageView to apply the shake animation to.
     */

    public static void shakeImageView(ImageView imageView) {
        // Create a TranslateAnimation to move left and right
        TranslateAnimation shake = new TranslateAnimation(
                -10, 10, // From X (left) to X (right) offset in pixels
                0, 0     // From Y (top) to Y (bottom) offset in pixels
        );

        shake.setDuration(100); // Duration for each shake (100 ms)
        shake.setRepeatCount(10); // Number of times to repeat (total duration: 20 * 100 ms = 2000 ms)
        shake.setRepeatMode(Animation.REVERSE); // Reverse animation direction on each repeat

        // Start the animation on the ImageView
        imageView.startAnimation(shake);
    }


}
