package com.example.tetris_concept;

/**
 * The {@code GameState} class represents the state of the game, including the player's pet choice,
 * map choice, and various point values for food and vibe.
 * <p>
 * This class contains state variables for tracking points and a chosen pet for a specific map.
 * It also provides getter and setter methods to access and modify these variables.
 * </p>
 *
 * @author Eric
 */
public class GameState {

    // Settings Page State:__________________________________________________________________________
    /** The currently chosen pet for the game (default is "FIRE"). */
    public static String currentChosenPet = "FIRE";

    /** The currently chosen map for the game (default is "MYSTICAL HEART"). */
    public static String currentChosenMap = "MYSTICAL HEART";

    /** The chosen pet for the specific map (default is "FIRE"). */
    private String chosenPetForMap = "FIRE";

    /** The total points accumulated by the player. */
    private long totalPoints = 0;

    /** Points related to food item 1. */
    private long foodPoint1 = 0;

    /** Points related to food item 2. */
    private long foodPoint2 = 0;

    /** Points related to food item 3. */
    private long foodPoint3 = 0;

    /** Points related to vibe item 1. */
    private long vibePoint1 = 0;

    /** Points related to vibe item 2. */
    private long vibePoint2 = 0;

    /** Points related to vibe item 3. */
    private long vibePoint3 = 0;

    /**
     * Gets the total points accumulated by the player.
     *
     * @return the total points
     */
    public long getTotalPoints() {
        return totalPoints;
    }

    /**
     * Sets the total points accumulated by the player.
     *
     * @param totalPoints the new total points value
     */
    public void setTotalPoints(long totalPoints) {
        this.totalPoints = totalPoints;
    }

    /**
     * Gets the points for food item 1.
     *
     * @return the points for food item 1
     */
    public long getFoodPoint1() {
        return foodPoint1;
    }

    /**
     * Sets the points for food item 1.
     *
     * @param foodPoint1 the new points for food item 1
     */
    public void setFoodPoint1(long foodPoint1) {
        this.foodPoint1 = foodPoint1;
    }

    /**
     * Gets the points for food item 2.
     *
     * @return the points for food item 2
     */
    public long getFoodPoint2() {
        return foodPoint2;
    }

    /**
     * Sets the points for food item 2.
     *
     * @param foodPoint2 the new points for food item 2
     */
    public void setFoodPoint2(long foodPoint2) {
        this.foodPoint2 = foodPoint2;
    }

    /**
     * Gets the points for food item 3.
     *
     * @return the points for food item 3
     */
    public long getFoodPoint3() {
        return foodPoint3;
    }

    /**
     * Sets the points for food item 3.
     *
     * @param foodPoint3 the new points for food item 3
     */
    public void setFoodPoint3(long foodPoint3) {
        this.foodPoint3 = foodPoint3;
    }

    /**
     * Gets the points for vibe item 1.
     *
     * @return the points for vibe item 1
     */
    public long getVibePoint1() {
        return vibePoint1;
    }

    /**
     * Sets the points for vibe item 1.
     *
     * @param vibePoint1 the new points for vibe item 1
     */
    public void setVibePoint1(long vibePoint1) {
        this.vibePoint1 = vibePoint1;
    }

    /**
     * Gets the points for vibe item 2.
     *
     * @return the points for vibe item 2
     */
    public long getVibePoint2() {
        return vibePoint2;
    }

    /**
     * Sets the points for vibe item 2.
     *
     * @param vibePoint2 the new points for vibe item 2
     */
    public void setVibePoint2(long vibePoint2) {
        this.vibePoint2 = vibePoint2;
    }

    /**
     * Gets the points for vibe item 3.
     *
     * @return the points for vibe item 3
     */
    public long getVibePoint3() {
        return vibePoint3;
    }

    /**
     * Sets the points for vibe item 3.
     *
     * @param vibePoint3 the new points for vibe item 3
     */
    public void setVibePoint3(long vibePoint3) {
        this.vibePoint3 = vibePoint3;
    }

    /**
     * Gets the pet chosen for the specific map.
     *
     * @return the chosen pet for the map
     */
    public String getChosenPetForMap() {
        return chosenPetForMap;
    }

    /**
     * Sets the pet chosen for the specific map.
     *
     * @param chosenPetForMap the new chosen pet for the map
     */
    public void setChosenPetForMap(String chosenPetForMap) {
        this.chosenPetForMap = chosenPetForMap;
    }
}
