/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Common.java
 * Author:           Matt Schwartz
 * Date created:     07.03.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Common {
    public static final int TILE_SIZE                       = 20;
    public static final int SCALE                           = 1;
    public static final char PLAYER_ICON                    = '@';
    public static final char GOLD_LOOT_ICON                 = '$';
    public static final char WALL_LOOT_ICON                 = '|';
    public static final char ARMOR_LOOT_ICON                = ']';
    public static final char WEAPON_LOOT_ICON               = ')';
    public static final char FOOD_LOOT_ICON                 = ':';
    public static final char ARROW_LOOT_ICON                = '/';
    public static final char SCROLL_LOOT_ICON               = '%';
    public static final char POTION_LOOT_ICON               = '8';
    public static final char JUNK_LOOT_ICON                 = '&';
    public static final char CEILING_FLOOR_ICON             = '-';
    public static final char DOOR_LOCKED_ICON               = '+';
    public static final char OPTION_SELECT_ICON             = '·';
    
    public static final int THEME_BG_COLOR_RGB              = new Color(15, 15, 15).getRGB();
    public static final int THEME_FG_COLOR_RGB              = new Color(0, 180, 0).getRGB();
    
    public static final Font DEFAULT_FONT                   = new Font(Font.MONOSPACED, Font.TRUETYPE_FONT, 12);
    public static final Font BOLD_FONT                      = new Font(Font.MONOSPACED, Font.BOLD, 12);
    
    public static final Font PLAYER_ICON_FONT               = DEFAULT_FONT;
    
    public static final Font INFO_FRAME_MSG_FONT_DEFAULT    = DEFAULT_FONT;
    public static final Font INFO_FRAME_MSG_FONT_BOLD       = BOLD_FONT;
    
    /* World text colors */
    public static final int PLAYER_GOLD_TEXT_COLOR_RGB      = Color.yellow.getRGB();
    public static final Color PLAYER_CURSE_TEXT_COLOR       = new Color(138, 43, 226);
    public static final Color PLAYER_POISON_TEXT_COLOR      = new Color(15, 175, 6);
    public static final Color PLAYER_POTION_TEXT_COLOR      = new Color(79, 105, 198);
    
    public static final int CURSE_COLOR_RGB                 = new Color(138, 43, 226).getRGB();
    public static final int POISON_COLOR_RGB                = new Color(15, 175, 6).getRGB();
    public static final int POTION_COLOR_RGB                = new Color(79, 105, 198).getRGB();
    
    public static final int BUFF_RGB                        = new Color(31, 107, 29).getRGB();
    public static final int DEBUFF_RGB                      = new Color(173, 0, 30).getRGB();
    
    /* For use in info log */
    public static final Color INFO_FRAME_DEFAULT_MSG_COLOR  = Color.lightGray;
    public static final Color DAMAGE_TEXT_COLOR             = new Color(255, 3, 62);
    public static final Color POISON_TEXT_COLOR             = PLAYER_POISON_TEXT_COLOR;
    public static final Color HEALTH_GAIN_TEXT_COLOR        = new Color(0, 255, 0);
    public static final Color ENTITY_TEXT_COLOR             = Color.white;
    public static final Color WORLD_OBJECT_TEXT_COLOR       = new Color(137, 207, 240);
    
    public static final Font PLAYER_STATS_FONT              = DEFAULT_FONT;
    public static final Font PLAYER_STATS_FONT_BOLD         = BOLD_FONT;
    
    public static final Color PLAYER_STATS_COLOR            = Color.black;
    
    public static final int COMMON_ITEM_COLOR_RGB           = Color.yellow.getRGB();
    public static final int RARE_ITEM_COLOR_RGB             = new Color(137, 207, 240).getRGB();
    public static final int LEGENDARY_ITEM_COLOR_RGB        = Color.orange.getRGB();
    
    public static final Font INV_FRAME_FONT_DEFAULT         = DEFAULT_FONT;
    public static final Font INV_FRAME_FONT_BOLD            = BOLD_FONT;
    
    public static final Color INV_FRAME_TEXT_COLOR          = Color.black;
    public static final int TOOLTIP_TEXT_COLOR_RGB          = THEME_FG_COLOR_RGB;
    
    public static final ArrayList GIBBERISH_WORD_LIST        = new ArrayList<String>();
    
    // These variables are not accessible from outside this class
    private static final char[] CONSONANTS_BY_FREQ     = {'t', 'n', 's', 'h', 'r', 'd', 'l', 'c', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z'};
    private static final double[] CONSONANTS_FREQ_PERC = {9.056, 6.749, 6.327, 6.094, 5.987, 4.253, 4.025, 2.782, 2.406, 2.36, 2.228, 2.015, 1.974, 1.929, 1.492, 0.978, 0.772, 0.153, 0.15, 0.095, 0.074};
    private static final char[] VOWELS_BY_FREQ         = {'e', 'a', 'o', 'i', 'u'};
    private static final double[] VOWEL_FREQ_PERC      = {12.702, 8.167, 7.507, 6.966, 2.758};
    
    private static final Map CONSONANT_FREQUENCY_MAP   = new HashMap<Character, Double>();
    private static final Map VOWEL_FREQUENCY_MAP       = new HashMap<Character, Double>();
    
    // Methods used throughout the program
    
    public static String rarityIdToString(int itemRarity) {
        if (itemRarity == RARE_ITEM_COLOR_RGB) {
            return "Rare";
        } // if
        
        if (itemRarity == LEGENDARY_ITEM_COLOR_RGB) {
            return "Legendary";
        } // if
        
        return "Common";
    } // rarityIdToString
    
    /* Takes a string and adds a roughly even number of spaces to either side
        to fit within 20 characters, centering the given text (str).
        Used for the detailed items pane as the Inventory frame's title */
    public static String centerString(String str, String filler, int lineLength) {
        String newStr = "";
        int leftSpaces = (int)(Math.floor( (lineLength - str.length()) / 2.0));
        int rightSpaces = (int)(Math.ceil( (lineLength - str.length()) / 2.0));
        
        for (int i = 0; i < leftSpaces; i++) {
            newStr += filler;
        } // for
        
        newStr += str;
        
        for (int i = 0; i < rightSpaces; i++) {
            newStr += filler;
        } // for
        
        return newStr;
    } // centerString
    
    /* Aligns text across line with strLeft on the far left and strRight on the 
        far right */
    public static String alignToSides(String strLeft, String strRight, int lineLength) {
        String newStr;
        newStr = strLeft;
        
        for (int i = 0; i < (lineLength - (strLeft.length() + strRight.length())); i++) {
            newStr += " ";
        } // for
        
        return newStr + strRight;
    } // alignToSides

    /* Generate an array filled with gibberish words.  Used for the static naming
        of unidentified words */
    public static void generateGibberish() {
        for (int i = 0; i < CONSONANTS_BY_FREQ.length; i++) {
                CONSONANT_FREQUENCY_MAP.put(CONSONANTS_BY_FREQ[i], CONSONANTS_FREQ_PERC[i]);
        } // for
        for (int i = 0; i < VOWELS_BY_FREQ.length; i++) {
            VOWEL_FREQUENCY_MAP.put(VOWELS_BY_FREQ[i], VOWEL_FREQ_PERC[i]);
        } // for
        
        String str;
        int lengthOfWord;
        
        // Create 15000 unintelligible words
        for (int i = 0; i < 1000; i++) {
            str = "";
            lengthOfWord = 2;
            
            // Determine the length of a word, 2-5 letters
            if (Math.random()*100 <= 80) {
                lengthOfWord++;
                if (Math.random()*100 <= 55) {
                    lengthOfWord++;
                    if (Math.random()*100 <= 45) {
                        lengthOfWord++;
                    } // if
                } // if
            } // if
            
            // 17% chance that word begins with a vowel
            str += Math.random()*100 <= 17 ? getWeightedRandomCharacter(VOWEL_FREQUENCY_MAP)
                                                       : getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP);
            
            for (int j = 1; j < lengthOfWord; j++) {
                // If last letter was a vowel
                if (VOWEL_FREQUENCY_MAP.containsKey(str.toCharArray()[j-1])) {
                    str += getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP);
                } // if
                
                // Otherwise, it was a consonant
                else {
                    // If last two letters were consonants
                    if (j >= 2 && CONSONANT_FREQUENCY_MAP.containsKey(str.toCharArray()[j-2])) {
                        str += (Math.random()*100) <= 5 ? getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP)
                                                       : getWeightedRandomCharacter(VOWEL_FREQUENCY_MAP);
                    } // if
                    else {
                        str += (Math.random()*100) <= 44 ? getWeightedRandomCharacter(CONSONANT_FREQUENCY_MAP)
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
    
    /* Performs a weighted randomized letter generator where the weights are
        based on each letter's rough % frequency */
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