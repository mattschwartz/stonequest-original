/* *****************************************************************************
 * Project:          Roguelike2.0
 * File displayName:        ScreenElement.java
 * Author:           Matt Schwartz
 * Date created:     07.03.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: ScreenElements are defined with a character, a color and other
                     types of formatting to be drawn to the screen in a similar
                     manner.  
           -    char element: the character element that would be drawn to the
                      screen
           - boolean isBolded: true if the character should be drawn using the
                      bolded font set
           -     int elementColor: the RGB color value for the character to be
                      drawn to the Screen
 **************************************************************************** */

package com.barelyconscious.game.graphics;

public class ScreenElement {
    private char element;
    private boolean isBolded;
    private int elementColor;
    
    /**
     * Create a new ScreenElement with the following parameters:
     * @param c character value for the ScreenElement
     * @param bold if true, the character will be drawn using a bold font set
     * @param co the RGB color value for the ScreenElement
     */
    public ScreenElement(char c, boolean bold, int co) {
        element = c;
        isBolded = bold;
        elementColor = co;
    } // constructor
    
    /**
     * Function should never be called
     * @param c the new character value for the ScreenElement
     */
    public void setElement(char c) {
        element = c;
    } // setElement
    
    /**
     * 
     * @return the character value for the ScreenElement
     */
    public char getElement() {
        return element;
    } // getElement
    
    /**
     * Changes whether the character is bold
     * @param bold the new bold value for the character
     */
    public void setBold(boolean bold) {
        isBolded = bold;
    } // setFont
    
    /**
     * 
     * @return true if the character should be drawn bolded
     */
    public boolean isBold() {
        return isBolded;
    } // getFont
    
    /**
     * Changes the color of the character to be drawn
     * @param co the new integer RGB color value for the character 
     */
    public void setColor(int co) {
        elementColor = co;
    } // setColor
    
    /**
     * 
     * @return the integer RGB color value for the character
     */
    public int getColor() {
        return elementColor;
    } // getColor

    @Override
    public String toString() {
        return "" + element;
    } // toString
} // ScreenElement
