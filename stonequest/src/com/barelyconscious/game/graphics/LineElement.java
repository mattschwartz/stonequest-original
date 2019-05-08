/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        LineElement.java
 * Author:           Matt Schwartz
 * Date created:     07.04.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Line elements are Strings that allow for each character to
                     have different colors and other formatting.
 **************************************************************************** */

package com.barelyconscious.game.graphics;

import com.barelyconscious.game.item.Item;

public class LineElement {
    private String sElement;
    private boolean isBold;
    private int msgColor;

    /**
     * Create a new LineElement with uniform color and formatting
     * @param str the String to be elementized
     * @param bold if true, the String will be bolded
     * @param color the color of the String
     */
    public LineElement(String str, boolean bold, int color) {
        sElement = str;
        isBold = bold;
        msgColor = color;
    } // constructor
    
    /**
     * Creates a new LineElement based on an Item's data, for convenience when
     * writing an Item to the screen.
     * @param item 
     */
    public LineElement(Item item) {
        sElement = "[" + item.getDisplayName()+ "]";
        isBold = true;
        msgColor = item.getRarityColor();
    } // constructor
    
    /**
     * 
     * @return the message of the LineElement as a String
     */
    public String getElementMessage() {
        return sElement;
    } // getElementMessage

    /**
     * 
     * @return the uniform color of the message as an integer RGB value
     */
    public int getColorAsRGB() {
        return msgColor;
    } // getColorAsRGB
    
    /**
     * Changes the uniform color of the message
     * @param color the new color of the message as an integer RGB value
     */
    public void setColor(int color) {
        msgColor = color;
    } // setColor

    /**
     * 
     * @return true if the message is to be drawn bolded
     */
    public boolean getBold() {
        return isBold;
    } // getFont

    /**
     * Changes if the message should be drawn bolded
     * @param bold the new value for bold
     */
    public void setBold(boolean bold) {
        isBold = bold;
    } // setFont
    
    @Override
    public String toString() {
        return sElement;
    } // toString
} // LineElement
