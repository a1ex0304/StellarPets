package com.example.tetris_concept;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a game map item that includes information about a specific map, music,
 * food and vibe points, experience points, and the chosen pet for the map.
 * <p>
 * This class stores all the necessary data related to a particular game map, such as the
 * name of the map, the music associated with it, the image related to the music, various
 * point values (XP, food, vibe), and the chosen pet for the map. It also provides methods
 * for accessing and modifying these properties.
 * </p>
 *
 * <p>
 * The class includes the following properties:
 * <ul>
 *     <li>{@code mapName}: The name of the map.</li>
 *     <li>{@code musicName}: The name of the music associated with the map.</li>
 *     <li>{@code musicImageAddress}: The address or path of the image related to the music.</li>
 *     <li>{@code savedXPPoints}: The number of XP points saved for the map.</li>
 *     <li>{@code foodPoint1}, {@code foodPoint2}, {@code foodPoint3}: The food points for the map.</li>
 *     <li>{@code vibePoint1}, {@code vibePoint2}, {@code vibePoint3}: The vibe points for the map.</li>
 *     <li>{@code chosenPetForMap}: The pet chosen to be associated with the map.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The class also includes methods for getting and setting each of these properties,
 * ensuring the data is easily accessible and modifiable.
 * </p>
 *
 * @author Aryan Farhnag-pour
 */
public class GameMapItem {

    private String mapName;
    private String musicName;

    private String chosenPetForMap;

    private String musicImageAddress;
    private Map<String, Integer> musicIntervalsMap;

    private long savedXPPoints;
    private long foodPoint1, foodPoint2, foodPoint3, vibePoint1, vibePoint2, vibePoint3;


    /**
     * Constructs a new {@code GameMapItem} object with the specified parameters.
     * <p>
     * This constructor initializes a new instance of the {@code GameMapItem} class
     * with the provided map name, music information, food and vibe points, XP points,
     * and the pet chosen for the map. If the {@code musicIntervalsMap} is provided,
     * it is copied; otherwise, an empty map is used.
     * </p>
     *
     * @param mapName The name of the map.
     * @param musicName The name of the music associated with the map.
     * @param musicImageAddress The address or path of the image related to the music.
     * @param savedXPPoints The number of XP points to be saved.
     * @param foodPoint1 The value for the first food point.
     * @param foodPoint2 The value for the second food point.
     * @param foodPoint3 The value for the third food point.
     * @param vibePoint1 The value for the first vibe point.
     * @param vibePoint2 The value for the second vibe point.
     * @param vibePoint3 The value for the third vibe point.
     * @param chosenPetForMap The name of the pet chosen for the map.
     *
     * @throws NullPointerException if any required parameter is {@code null}.
     */
    // Constructor
    public GameMapItem(String mapName, String musicName, String musicImageAddress, long savedXPPoints, long foodPoint1, long foodPoint2, long foodPoint3, long vibePoint1, long vibePoint2, long vibePoint3, String chosenPetForMap) {
        this.mapName = mapName;
        this.musicName = musicName;
        this.musicImageAddress = musicImageAddress;
        this.musicIntervalsMap = musicIntervalsMap != null ? new HashMap<>(musicIntervalsMap) : new HashMap<>();
        this.savedXPPoints = savedXPPoints;
        this.foodPoint1 = foodPoint1;
        this.foodPoint2 = foodPoint2;
        this.foodPoint3 = foodPoint3;
        this.vibePoint1 = vibePoint1;
        this.vibePoint2 = vibePoint2;
        this.vibePoint3 = vibePoint3;
        this.chosenPetForMap = chosenPetForMap;

    }



    // Getters and Setters

    /**
     * Retrieves the name of the map.
     * <p>
     * This method returns the name of the map that is currently in use. The map name could be used
     * for identifying or referencing the map in the application.
     * </p>
     *
     * @return The name of the map.
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Sets the name of the map.
     * <p>
     * This method allows the user to set the name of the map, which can be used for identifying
     * the map in the application.
     * </p>
     *
     * @param mapName The name of the map to be set.
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * Retrieves the name of the music track associated with the map.
     * <p>
     * This method returns the name of the music track that is currently linked with the map.
     * The music name could be used for referencing or playing the music track.
     * </p>
     *
     * @return The name of the music track.
     */
    public String getMusicName() {
        return musicName;
    }

    /**
     * Sets the name of the music track associated with the map.
     * <p>
     * This method allows the user to set the name of the music track linked with the map.
     * The music name can be used to identify or load the corresponding music track.
     * </p>
     *
     * @param musicName The name of the music track to be set.
     */
    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    /**
     * Retrieves the address of the music image associated with the map.
     * <p>
     * This method returns the URL or file path of the image representing the music track.
     * The image can be used to visually represent the music on the UI.
     * </p>
     *
     * @return The address (URL or file path) of the music image.
     */
    public String getMusicImageAddress() {
        return musicImageAddress;
    }

    /**
     * Sets the address of the music image associated with the map.
     * <p>
     * This method allows the user to set the URL or file path of the image that represents the
     * music track associated with the map.
     * </p>
     *
     * @param musicImageAddress The URL or file path of the music image to be set.
     */
    public void setMusicImageAddress(String musicImageAddress) {
        this.musicImageAddress = musicImageAddress;
    }

    /**
     * Retrieves a copy of the map of music intervals.
     * <p>
     * This method returns a copy of the map that contains music intervals, where the key is a
     * String representing the interval's name, and the value is an Integer representing the
     * duration or time in milliseconds for that interval. The method returns a copy to maintain
     * immutability and avoid external modification of the original map.
     * </p>
     *
     * @return A copy of the music intervals map.
     */
    public Map<String, Integer> getMusicIntervalsMap() {
        return new HashMap<>(musicIntervalsMap); // Returning a copy to maintain immutability
    }

    /**
     * Sets the map of music intervals.
     * <p>
     * This method allows the user to set the map that contains music intervals, where each
     * entry in the map has a key (interval name) and a value (interval duration).
     * A new HashMap is created to store the intervals to ensure that the original map remains
     * unchanged.
     * </p>
     *
     * @param musicIntervalsMap The map containing the music intervals to be set.
     */
    public void setMusicIntervalsMap(Map<String, Integer> musicIntervalsMap) {
        this.musicIntervalsMap = new HashMap<>(musicIntervalsMap);
    }

    /**
     * Retrieves the saved XP points.
     * <p>
     * This method returns the total number of XP points that have been saved for the user or pet.
     * The XP points could be used to track progress or achievement within the game.
     * </p>
     *
     * @return The number of saved XP points.
     */
    public long getSavedXPPoints() {
        return savedXPPoints;
    }

    /**
     * Sets the saved XP points.
     * <p>
     * This method allows the user to set the number of XP points that have been saved.
     * The XP points can be used to track progress or achievement in the game.
     * </p>
     *
     * @param savedXPPoints The number of XP points to be set.
     */
    public void setSavedXPPoints(long savedXPPoints) {
        this.savedXPPoints = savedXPPoints;
    }

    /**
     * Retrieves the value of the first food point.
     * <p>
     * This method returns the amount of food points associated with the first food item.
     * The food points may represent how much food the pet has consumed or the food's effectiveness.
     * </p>
     *
     * @return The value of the first food point.
     */
    public long getFoodPoint1() {
        return foodPoint1;
    }

    /**
     * Sets the value of the first food point.
     * <p>
     * This method allows the user to set the number of food points for the first food item.
     * The food points can affect the pet's health or other gameplay mechanics.
     * </p>
     *
     * @param foodPoint1 The value of the first food point to be set.
     */
    public void setFoodPoint1(long foodPoint1) {
        this.foodPoint1 = foodPoint1;
    }

    /**
     * Retrieves the value of the second food point.
     * <p>
     * This method returns the amount of food points associated with the second food item.
     * The food points may influence the pet's health or provide in-game benefits.
     * </p>
     *
     * @return The value of the second food point.
     */
    public long getFoodPoint2() {
        return foodPoint2;
    }

    /**
     * Sets the value of the second food point.
     * <p>
     * This method allows the user to set the number of food points for the second food item.
     * Food points could impact the pet's wellbeing or game performance.
     * </p>
     *
     * @param foodPoint2 The value of the second food point to be set.
     */
    public void setFoodPoint2(long foodPoint2) {
        this.foodPoint2 = foodPoint2;
    }

    /**
     * Retrieves the value of the third food point.
     * <p>
     * This method returns the amount of food points associated with the third food item.
     * These points can be used for various gameplay purposes, such as feeding or healing pets.
     * </p>
     *
     * @return The value of the third food point.
     */
    public long getFoodPoint3() {
        return foodPoint3;
    }

    /**
     * Sets the value of the third food point.
     * <p>
     * This method allows the user to set the number of food points for the third food item.
     * The food points may influence gameplay, such as replenishing energy or health for the pet.
     * </p>
     *
     * @param foodPoint3 The value of the third food point to be set.
     */
    public void setFoodPoint3(long foodPoint3) {
        this.foodPoint3 = foodPoint3;
    }

    /**
     * Retrieves the value of the first vibe point.
     * <p>
     * This method returns the number of vibe points for the first vibe item.
     * Vibe points may affect the pet's mood, energy, or game interactions.
     * </p>
     *
     * @return The value of the first vibe point.
     */
    public long getVibePoint1() {
        return vibePoint1;
    }

    /**
     * Sets the value of the first vibe point.
     * <p>
     * This method allows the user to set the number of vibe points for the first vibe item.
     * Vibe points can alter how the pet behaves or interacts with other elements in the game.
     * </p>
     *
     * @param vibePoint1 The value of the first vibe point to be set.
     */
    public void setVibePoint1(long vibePoint1) {
        this.vibePoint1 = vibePoint1;
    }

    /**
     * Retrieves the value of the second vibe point.
     * <p>
     * This method returns the amount of vibe points associated with the second vibe item.
     * These points may be used to improve or change the pet's emotional or gameplay status.
     * </p>
     *
     * @return The value of the second vibe point.
     */
    public long getVibePoint2() {
        return vibePoint2;
    }

    /**
     * Sets the value of the second vibe point.
     * <p>
     * This method allows the user to set the number of vibe points for the second vibe item.
     * These points could impact the pet's reactions, mood, or interactions with the game environment.
     * </p>
     *
     * @param vibePoint2 The value of the second vibe point to be set.
     */
    public void setVibePoint2(long vibePoint2) {
        this.vibePoint2 = vibePoint2;
    }

    /**
     * Retrieves the value of the third vibe point.
     * <p>
     * This method returns the amount of vibe points associated with the third vibe item.
     * These points may influence the pet's energy or game interactions.
     * </p>
     *
     * @return The value of the third vibe point.
     */
    public long getVibePoint3() {
        return vibePoint3;
    }

    /**
     * Sets the value of the third vibe point.
     * <p>
     * This method allows the user to set the number of vibe points for the third vibe item.
     * These points may impact how the pet interacts with the environment or other pets.
     * </p>
     *
     * @param vibePoint3 The value of the third vibe point to be set.
     */
    public void setVibePoint3(long vibePoint3) {
        this.vibePoint3 = vibePoint3;
    }

    /**
     * Retrieves the name of the chosen pet for the map.
     * <p>
     * This method returns the name or identifier of the pet that has been selected to appear on the map.
     * This pet will likely be the one interacted with during the map gameplay.
     * </p>
     *
     * @return The name of the chosen pet for the map.
     */
    public String getChosenPetForMap() {
        return chosenPetForMap;
    }
    /**
     * Sets the chosen pet for the map.
     * <p>
     * This method allows the user to set the pet that will be featured or interacted with in the map gameplay.
     * The chosen pet will likely have certain abilities or characteristics related to the map's gameplay.
     * </p>
     *
     * @param chosenPetForMap The name or identifier of the pet to be chosen for the map.
     */
    public void setChosenPetForMap(String chosenPetForMap) {
        this.chosenPetForMap = chosenPetForMap;
    }
}
