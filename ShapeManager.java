package com.example.tetris_concept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the generation and retrieval of shapes and colors in a randomized order.
 * <p>
 * The class maintains two bags (lists) for shapes and colors. It provides methods
 * to retrieve the current shape and color, as well as the next shape and color,
 * while ensuring that shapes and colors are shuffled and reused when the bags are empty.
 * </p>
 *
 * @author Eric
 */

public class ShapeManager {
    private final String[] SHAPES = {
            "O_block", // Square shape
            "I_block", // Line shape
            "T_block", // T-shape
            "L_block", // L-shape
            "J_block", // Reverse L-shape
            "S_block", // S-shape (Zig-zag)
            "Z_block"  // Z-shape (Zig-zag)
    };

    private final String[] COLORS = {"greenBlock", "yellowBlock", "brownBlock", "pinkBlock", "purpleBlock", "blueBlock"};

    private List<String> shapeBag;
    private List<String> colorBag; // Bag for colors
    private String currentShape;
    private String currentColor;
    private String nextShape;
    private String nextColor;

    /**
     * Constructs a new ShapeManager instance.
     * <p>
     * Initializes the shape and color bags, shuffles them, and generates the first
     * shape and color for use.
     * </p>
     */
    public ShapeManager() {
        shapeBag = new ArrayList<>();
        colorBag = new ArrayList<>();
        refillBags(); // Initialize both the shape and color bags
        generateNextShapeAndColor();  // Generate the first next shape and color
    }

    /**
     * Retrieves the current shape and color.
     *
     * @return An array containing the current shape and color as strings.
     */
    public String[] getCurrentShapeAndColor() {
        return new String[]{currentShape, currentColor};
    }

    /**
     * Retrieves the next shape and color, and sets them as the current shape and color.
     * <p>
     * This method also prepares the subsequent shape and color for future use.
     * </p>
     *
     * @return An array containing the next shape and color as strings.
     */    public String[] getNextShapeAndColor() {
        currentShape = nextShape;
        currentColor = nextColor;
        generateNextShapeAndColor();  // Generate the next shape and color for future use
        return new String[]{currentShape, currentColor};
    }

    /**
     * Generates the next shape and color from the respective bags.
     * <p>
     * If the shape or color bag is empty, this method refills and reshuffles them
     * to ensure a continuous supply of randomized shapes and colors.
     * </p>
     */    private void generateNextShapeAndColor() {
        // If the shape bag or color bag is empty, refill them
        if (shapeBag.isEmpty() || colorBag.isEmpty()) {
            refillBags();
        }

        // Get the next shape and color from the respective bags and remove them
        nextShape = shapeBag.remove(0);
        nextColor = colorBag.remove(0);
    }

    /**
     * Refills the shape and color bags with their respective predefined values
     * and shuffles them to randomize their order.
     * <p>
     * This method ensures that the bags are replenished when they run out of items.
     * </p>
     */    private void refillBags() {
        // Refill and shuffle the shape bag
        Collections.addAll(shapeBag, SHAPES);
        Collections.shuffle(shapeBag);

        // Refill and shuffle the color bag
        Collections.addAll(colorBag, COLORS);
        Collections.shuffle(colorBag);
    }
}

