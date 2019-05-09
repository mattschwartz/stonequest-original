/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Common.java
 * Author:           Matt Schwartz
 * Date created:     07.03.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 this file.  You are not allowed to take credit for code
 that was not written fully by yourself, or to remove 
 credit from code that was not written fully by yourself.  
 Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Contains constant values and frequently used functions for
 classes throughout the project.  This class is never 
 instantiated. 
 All colors should be defined in their RGB integer value
              
 **************************************************************************** */
package com.barelyconscious.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Common {

    /**
     * The width and height (tiles are square) of each Tile.
     */
    public static final int TILE_SIZE = 32;
    /**
     * The scale of the game. This value is not used and will probably be removed
     */
    public static final int SCALE = 1;
    public static final int THEME_PASTEL_RED = new Color(255, 96, 136).getRGB();
    public static final int THEME_PASTEL_GREEN = new Color(142, 255, 117).getRGB();
    public static final int THEME_PASTEL_BLUE = new Color(117, 156, 255).getRGB();
    public static final int TRANSPARENT_COLOR = new Color(255, 0, 255).getRGB();
    /**
     * The background "theme" for the game. Most graphical features in the game make use of this color to paint its
     * component
     */
    public static final int THEME_BG_COLOR_RGB = new Color(15, 15, 15).getRGB();
    /**
     * The foreground "theme" for the game. Most graphical features in the game make use of this color to paint its
     * component
     */
    public static int themeForegroundColor = new Color(255, 255, 0).getRGB();
    // Tile colors for the mini map
    /**
     * The RGB color value for loot objects that appear on the minimap.
     */
    public static final int MINIMAP_LOOT_TILE_RGB = new Color(15, 200, 15).getRGB();
    /**
     * The RGB color value for entities who appear on the minimap.
     */
    public static final int MINIMAP_ENTITY_TILE_RGB = new Color(200, 15, 15).getRGB();
    /**
     * The RGB color value for the player on the minimap.
     */
    public static final int MINIMAP_PLAYER_TILE_RGB = new Color(255, 255, 0).getRGB();
    /**
     * The RGB color value for interactable objects on the minimap.
     */
    public static final int MINIMAP_CONTAINER_TILE_RGB = new Color(15, 50, 255).getRGB();
    /**
     * The RGB color value for level change objects (stairs, tunnels) on the minimap.
     */
    public static final int MINIMAP_NEXUS_TILE_RGB = new Color(125, 50, 255).getRGB();
    /**
     * The RGB color of the text that displays the player's gold value.
     */
    public static final int GOLD_AMOUNT_TEXT_RGB = Color.yellow.getRGB();
    /**
     * The RGB color of the text that displays the remaining duration associated with curses.
     */
    public static final int CURSE_DURATION_TEXT_RGB = new Color(138, 43, 226).getRGB();
    /**
     * The RGB color of the text that displays the remaining duration associated with poisons.
     */
    public static final int POISON_DURATION_TEXT_RGB = new Color(15, 175, 6).getRGB();
    /**
     * The RGB color of the text that displays the remaining duration associated with potions.
     */
    public static final int POTION_DURATION_TEXT_RGB = new Color(79, 105, 198).getRGB();
    // The following color constants are used in the text log
    /**
     * The default RGB color value of text for the text log.
     */
    public static final int FONT_DEFAULT_RGB = Color.lightGray.getRGB();
    /**
     * A white RGB color value for drawing fonts.
     */
    public static final int FONT_WHITE_RGB = Color.white.getRGB();
    /**
     * Null RGB color value for use in the text log. This value is used when digits in the text log should not be
     * colored differently.
     */
    public static final int FONT_NULL_RGB = new Color(69, 69, 69).getRGB();
    /**
     * The RGB color value of damage text for the text log.
     */
    public static final int FONT_DAMAGE_TEXT_RGB = new Color(255, 3, 62).getRGB();
    /**
     * The RGB color value for poison labels for the text log.
     */
    public static final int FONT_POISON_LABEL_RGB = POISON_DURATION_TEXT_RGB;
    /**
     * The RGB color value for health gain labels for the text log.
     */
    public static final int FONT_HEALTH_GAIN_LABEL_RGB = new Color(0, 255, 0).getRGB();
    /**
     * The RGB color value for entity labels for the text log.
     */
    public static final int FONT_ENTITY_LABEL_RGB = FONT_WHITE_RGB;
    /**
     * The RGB color value for loot labels for the text log.
     */
    public static final int FONT_LOOT_LABEL_RGB = new Color(137, 207, 240).getRGB();
    /**
     * The RGB color value for world tile selections that are valid.
     */
    public static final int WORLD_TILE_SELECT_GOOD = new Color(255, 255, 0).getRGB();
    /**
     * The RGB color value for world tile selections that are not valid.
     */
    public static final int WORLD_TILE_SELECT_BAD = new Color(255, 0, 0).getRGB();
    /**
     * The RGB color value associated with items that have a common rarity.
     */
    public static final int ITEM_RARITY_COMMON_RGB = Color.yellow.getRGB();
    /**
     * The RGB color value associated with items that have a magic rarity.
     */
    public static final int ITEM_RARITY_MAGIC_RGB = new Color(137, 207, 240).getRGB();
    /**
     * The RGB color value associated with items that have a rare rarity.
     */
    public static final int ITEM_RARITY_RARE_RGB = Color.orange.getRGB();
    /**
     * The RGB color value associated with items that have a unique rarity.
     */
    public static final int ITEM_RARITY_UNIQUE_RGB = new Color(205, 133, 63).getRGB();
    /**
     * The RGB color value of damaged health that appears beneath Entities in the world.
     */
    public static final int ENTITY_DAMAGED_HEALTH_RGB = new Color(250, 55, 25).getRGB();
    /**
     * The RGB color value of health remaining that appears beneath Entities in the world.
     */
    public static final int ENTITY_HEALTH_RGB = new Color(10, 250, 12).getRGB();
    /**
     * A list of randomly generated "gibberish" words for use whenever a scroll has not been identified.
     */
    public static final ArrayList GIBBERISH_WORD_LIST = new ArrayList<String>();
    /**
     * These values are used to generate a gibberish word list.
     */
    private static final char[] CONSONANTS_BY_FREQ = {'t', 'n', 's', 'h', 'r',
        'd', 'l', 'c', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z'};
    private static final double[] CONSONANTS_OCCURRENCE_PERC = {9.056, 6.749, 6.327,
        6.094, 5.987, 4.253, 4.025, 2.782, 2.406, 2.36, 2.228, 2.015, 1.974, 1.929,
        1.492, 0.978, 0.772, 0.153, 0.15, 0.095, 0.074};
    private static final char[] VOWELS_BY_FREQ = {'e', 'a', 'o', 'i', 'u'};
    private static final double[] VOWEL_FREQ_PERC = {12.702, 8.167, 7.507, 6.966, 2.758};
    private static final Map CONSONANT_FREQUENCY_MAP = new HashMap<Character, Double>();
    private static final Map VOWEL_FREQUENCY_MAP = new HashMap<Character, Double>();

    public static void setThemeForeground(int newForegroundTheme) {
        themeForegroundColor = newForegroundTheme;
    } // setThemeForeground

    // Methods used throughout the program
    /**
     * Converts the itemRarity into a String that can be displayed for the player
     *
     * @param itemRarity the RGB color value of the item rarity
     * @return a String representation of the item rarity
     */
    public static String rarityIdToString(int itemRarity) {
        if (itemRarity == ITEM_RARITY_COMMON_RGB) {
            return "Common";
        } // if

        if (itemRarity == ITEM_RARITY_MAGIC_RGB) {
            return "Magic";
        } // if

        if (itemRarity == ITEM_RARITY_RARE_RGB) {
            return "Rare";
        } // if

        if (itemRarity == ITEM_RARITY_UNIQUE_RGB) {
            return "Unique";
        } // if

        return "Unknown"; // should never be reached
    } // rarityIdToString

    /**
     * Takes in an integer,
     *
     * @number, and returns a String that adds commas to the appropriate locations within the number.
     * @param number
     * @return
     */
    public static String formatNumber(int number) {
        ArrayList<Character> formattedNumberArray = new ArrayList();
        String formattedNumber = "" + intReverse(number);

        if (number < 1000) {
            return "" + number;
        }

        for (int i = formattedNumber.length() - 1; i > 0; i--) {
            formattedNumberArray.add(formattedNumber.charAt(i));
            if (i % 3 == 0 && i != 0) {
                formattedNumberArray.add(',');
                formattedNumberArray.add(formattedNumber.charAt(--i));
            }
        }
        formattedNumberArray.add(formattedNumber.charAt(0));
        formattedNumber = "";
        for (char c : formattedNumberArray) {
            formattedNumber += c;
        }

        return formattedNumber;
    } // formatNumber

    public static String stringReverse(String str) {
        char[] reversed = str.toCharArray();
        String reversedString = "";

        for (int i = reversed.length - 1; i >= 0; i--) {
            reversedString += reversed[i];
        }

        return reversedString;
    } // stringReverse

    public static int intReverse(int num) {
        char[] reversed = ("" + num).toCharArray();
        String reversedInt = "";

        for (int i = reversed.length - 1; i >= 0; i--) {
            reversedInt += reversed[i];
        }

        return Integer.parseInt(reversedInt);
    } // stringReverse

    /**
     * Takes a string and adds a roughly even number of spaces to either side to fit within 20 characters, centering the
     * given text (str). Used for the detailed items pane as the Inventory frame's title
     *
     * @param str the String to be centered
     * @param filler the characters which will serve as space-filler around the centered String
     * @param lineLength the length of the line in which str will be centered
     * @return the new String that has been centered
     */
    public static String centerString(String str, String filler, int lineLength) {
        String newStr = "";
        int leftSpaces = (int) (Math.floor((lineLength - str.length()) / 2.0));
        int rightSpaces = (int) (Math.ceil((lineLength - str.length()) / 2.0));

        for (int i = 0; i < leftSpaces; i++) {
            newStr += filler;
        } // for

        newStr += str;

        for (int i = 0; i < rightSpaces; i++) {
            newStr += filler;
        } // for

        return newStr;
    } // centerString

    /**
     * Aligns text across line with strLeft on the far left and strRight on the far right
     *
     * @param strLeft the left-hand side of the new line
     * @param strRight the right-hand side of the new line
     * @param lineLength the length of the String
     * @return the new line with the side-aligned Strings
     */
    public static String alignToSides(String strLeft, String strRight, int lineLength) {
        String newStr;
        newStr = strLeft;

        for (int i = 0; i < (lineLength - (strLeft.length() + strRight.length())); i++) {
            newStr += " ";
        } // for

        return newStr + strRight;
    } // alignToSides

    /**
     * Generate an array filled with gibberish words. Used for the static naming of unidentified words
     */
    public static void generateGibberish(int numWords) {
        for (int i = 0; i < CONSONANTS_BY_FREQ.length; i++) {
            CONSONANT_FREQUENCY_MAP.put(CONSONANTS_BY_FREQ[i], CONSONANTS_OCCURRENCE_PERC[i]);
        } // for
        for (int i = 0; i < VOWELS_BY_FREQ.length; i++) {
            VOWEL_FREQUENCY_MAP.put(VOWELS_BY_FREQ[i], VOWEL_FREQ_PERC[i]);
        } // for

        String str;
        int lengthOfWord;

        // Create 15000 unintelligible words
        for (int i = 0; i < numWords; i++) {
            str = "";
            lengthOfWord = 2;

            // Determine the length of a word, 2-5 letters
            if (Math.random() * 100 <= 80) {
                lengthOfWord++;
                if (Math.random() * 100 <= 55) {
                    lengthOfWord++;
                    if (Math.random() * 100 <= 45) {
                        lengthOfWord++;
                    } // if
                } // if
            } // if

            // 17% chance that word begins with a vowel
            str += Math.random() * 100 <= 17 ? getWeightedRandomCharacter(VOWEL_FREQUENCY_MAP)
                    : getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP);

            for (int j = 1; j < lengthOfWord; j++) {
                // If last letter was a vowel
                if (VOWEL_FREQUENCY_MAP.containsKey(str.toCharArray()[j - 1])) {
                    str += getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP);
                } // if
                // Otherwise, it was a consonant
                else {
                    // If last two letters were consonants
                    if (j >= 2 && CONSONANT_FREQUENCY_MAP.containsKey(str.toCharArray()[j - 2])) {
                        str += (Math.random() * 100) <= 5 ? getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP)
                                : getWeightedRandomCharacter(VOWEL_FREQUENCY_MAP);
                    } // if
                    else {
                        str += (Math.random() * 100) <= 44 ? getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP)
                                : getWeightedRandomCharacter(VOWEL_FREQUENCY_MAP);
                    } // else 
                } // else
            } // for

            if (GIBBERISH_WORD_LIST.contains(str)) {
                i--;
                continue;
            } // if

            GIBBERISH_WORD_LIST.add(str);
        } // for
    } // generateGibberish

    /**
     * Performs a weighted randomized letter generator where the weights are based on each letter's rough percent
     * frequency
     *
     * @param charMap a map containing characters and the corresponding character's frequency
     * @return a weighted random character
     */
    private static char getWeightedRandomCharacter(Map<Character, Double> charMap) {
        double totalWeight = 0;
        double randomValue;

        for (double val : charMap.values()) {
            totalWeight += val;
        } // for

        randomValue = Math.random() * totalWeight;

        for (char letter : charMap.keySet()) {
            if (randomValue < charMap.get(letter)) {
                return letter;
            } // if
            randomValue -= charMap.get(letter);
        } // for

        return '?';
    } // getWeightedRandomCharacter
} // Common
