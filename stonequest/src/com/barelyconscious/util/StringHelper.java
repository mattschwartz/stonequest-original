/* *****************************************************************************
   * Project:           StoneQuest
   * File Name:         StringHelper.java
   * Author:            Matt Schwartz
   * Date Created:      12.03.2013 
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringHelper {
    private static List<Character> vowels = new ArrayList<Character>() {{
        add('a');
        add('e');
        add('i');
        add('o');
        add('u');
    }};
    private static final Map VOWEL_FREQUENCY_MAP = new HashMap<Character, Double>();
    private static final char[] VOWELS_BY_FREQ = {'e', 'a', 'o', 'i', 'u'};
    private static final double[] VOWEL_FREQ_PERC = {12.702, 8.167, 7.507, 6.966, 2.758};
    /**
     * These values are used to generate a gibberish word list.
     */
    private static final char[] CONSONANTS_BY_FREQ = {'t', 'n', 's', 'h', 'r', 'd', 'l', 'c', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z'};
    private static final double[] CONSONANTS_OCCURRENCE_PERC = {9.056, 6.749, 6.327, 6.094, 5.987, 4.253, 4.025, 2.782, 2.406, 2.36, 2.228, 2.015, 1.974, 1.929, 1.492, 0.978, 0.772, 0.153, 0.15, 0.095, 0.074};
    private static final Map CONSONANT_FREQUENCY_MAP = new HashMap<Character, Double>();
    /**
     * A list of randomly generated "gibberish" words for use whenever a scroll has not been identified.
     */
    public static final ArrayList GIBBERISH_WORD_LIST = new ArrayList<String>();
    
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.equals(" ")) {
            return true;
        }
        
        for (char c : str.toCharArray()) {
            if (c != ' ') {
                break;
            }
        }
        
        return false;
    }
    
    public static String aOrAn(String str) {
        if (vowels.contains(str.charAt(0))) {
            return "an " + str;
        }
        
        return "a " + str;
    }

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
        }

        newStr += str;

        for (int i = 0; i < rightSpaces; i++) {
            newStr += filler;
        }

        return newStr;
    }

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
        }

        return newStr + strRight;
    }

    public static int intReverse(int num) {
        char[] reversed = ("" + num).toCharArray();
        String reversedInt = "";

        for (int i = reversed.length - 1; i >= 0; i--) {
            reversedInt += reversed[i];
        }

        return Integer.parseInt(reversedInt);
    }

    /**
     * Takes in an integer,
     *
     * @number, and returns a String that adds commas to the appropriate locations within the number.
     * @param number
     * @return
     */
    public static String formatNumber(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

    public static String stringReverse(String str) {
        char[] reversed = str.toCharArray();
        String reversedString = "";
        for (int i = reversed.length - 1; i >= 0; i--) {
            reversedString += reversed[i];
        }
        return reversedString;
    }

    /**
     * Generate an array filled with gibberish words. Used for the static naming of unidentified words
     */
    public static void generateGibberish(int numWords) {
        for (int i = 0; i < CONSONANTS_BY_FREQ.length; i++) {
            CONSONANT_FREQUENCY_MAP.put(CONSONANTS_BY_FREQ[i], CONSONANTS_OCCURRENCE_PERC[i]);
        }
        for (int i = 0; i < VOWELS_BY_FREQ.length; i++) {
            VOWEL_FREQUENCY_MAP.put(VOWELS_BY_FREQ[i], VOWEL_FREQ_PERC[i]);
        }
        String str;
        int lengthOfWord;
        for (int i = 0; i < numWords; i++) {
            str = "";
            lengthOfWord = 2;
            if (Math.random() * 100 <= 80) {
                lengthOfWord++;
                if (Math.random() * 100 <= 55) {
                    lengthOfWord++;
                    if (Math.random() * 100 <= 45) {
                        lengthOfWord++;
                    }
                }
            }
            str += Math.random() * 100 <= 17 ? getWeightedRandomCharacter(VOWEL_FREQUENCY_MAP) : getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP);
            for (int j = 1; j < lengthOfWord; j++) {
                if (VOWEL_FREQUENCY_MAP.containsKey(str.toCharArray()[j - 1])) {
                    str += getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP);
                } else {
                    if (j >= 2 && CONSONANT_FREQUENCY_MAP.containsKey(str.toCharArray()[j - 2])) {
                        str += (Math.random() * 100) <= 5 ? getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP) : getWeightedRandomCharacter(VOWEL_FREQUENCY_MAP);
                    } else {
                        str += (Math.random() * 100) <= 44 ? getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP) : getWeightedRandomCharacter(VOWEL_FREQUENCY_MAP);
                    }
                }
            }
            if (GIBBERISH_WORD_LIST.contains(str)) {
                i--;
                continue;
            }
            GIBBERISH_WORD_LIST.add(str);
        }
    }

    /**
     * Break the textArea's text into lines along words. Algorithm copied from:
     * http://www.java-forums.org/new-java/4669-how-split-string-into-multiple-lines-x-characters-each.html
     *
     * @param originalString the original string text that will be broken
     * @return the broken string as a list of strings
     */
    public static List<String> splitStringAlongWords(String originalString, int maxLineLength) {
        List<String> broken = new ArrayList<String>();
        char[] chars = originalString.toCharArray();
        boolean endOfString = false;
        int start = 0;
        int end;
        while (start < chars.length - 1) {
            int charCount = 0;
            int lastSpace = 0;
            while (charCount < maxLineLength) {
                if (chars[charCount + start] == '\n') {
                    lastSpace = charCount;
                    break;
                } else if (chars[charCount + start] == ' ') {
                    lastSpace = charCount;
                }
                charCount++;
                if (charCount + start == originalString.length()) {
                    endOfString = true;
                    break;
                }
            }
            end = endOfString ? originalString.length() : (lastSpace > 0) ? lastSpace + start : charCount + start;
            broken.add(originalString.substring(start, end));
            start = end + 1;
        }
        return broken;
    }

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
        }
        randomValue = Math.random() * totalWeight;
        for (char letter : charMap.keySet()) {
            if (randomValue < charMap.get(letter)) {
                return letter;
            }
            randomValue -= charMap.get(letter);
        }
        return '?';
    }

    // Methods used throughout the program
    /**
     * Converts the itemRarity into a String that can be displayed for the player
     *
     * @param itemRarity the RGB color value of the item rarity
     * @return a String representation of the item rarity
     */
    public static String rarityIdToString(int itemRarity) {
        if (itemRarity == ColorHelper.ITEMRARITY_COMMON_COLOR) {
            return "Common";
        }
        if (itemRarity == ColorHelper.ITEMRARITY_MAGIC_COLOR) {
            return "Magic";
        }
        if (itemRarity == ColorHelper.ITEMRARITY_RARE_COLOR) {
            return "Rare";
        }
        if (itemRarity == ColorHelper.ITEMRARITY_UNIQUE_COLOR) {
            return "Unique";
        }
        return "Unknown"; // should never be reached
    }
    
}
