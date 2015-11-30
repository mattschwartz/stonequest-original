/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        ScreenElement.java
 * Author:           Matt Schwartz
 * Date created:     07.03.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: A Screen element corresponds to an individual character
                     drawn anywhere on the Screen, including within the World,
                     player stats frame, inventory frame, and info frame.  Each
                     element can have its own Color and Font as well, to allow
                     for a more vibrant and a game that is easier to follow.
 **************************************************************************** */

package com.barelyconscious.game.graphics;

public class ScreenElement {
    private char element;
    private boolean isBolded;
    private int elementColor;
    
    public ScreenElement(char c, boolean bold, int co) {
        element = c;
        isBolded = bold;
        elementColor = co;
    } // constructor
    
    public static ScreenElement[] getElementsAsArray(String msg, boolean bold, int color) {
        ScreenElement[] array = new ScreenElement[msg.length()];
        for (int i = 0; i < msg.length(); i++) {
            array[i] = new ScreenElement(msg.charAt(i), bold, color);
        } // for
        return array;
    } // getElementsAsArray
    
    public void setElement(char c) {
        element = c;
    } // setElement
    
    public char getElement() {
        return element;
    } // getElement
    
    public void setBold(boolean bold) {
        isBolded = bold;
    } // setFont
    
    public boolean isBold() {
        return isBolded;
    } // getFont
    
    public void setColor(int co) {
        elementColor = co;
    } // setColor
    
    public int getColor() {
        return elementColor;
    } // getColor

    @Override
    public String toString() {
        return "" + element;
    } // toString
} // ScreenElement