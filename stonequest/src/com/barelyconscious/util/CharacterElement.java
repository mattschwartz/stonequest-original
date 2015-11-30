/* *****************************************************************************
 *   Project:        StoneQuest
 *   File name:      CharacterElement.java
 *   Author:         Matt Schwartz
 *   Date:           12.14.2013
 *   License:        You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email mattschwartz@utexas.edu for issues or concerns.
 *   Description:    
 **************************************************************************** */

package com.barelyconscious.util;

import java.awt.Color;

public class CharacterElement {
    public char data;
    public int color;
    
    public CharacterElement(char c, Color col) {
        this.data = c;
        this.color = col.getRGB();
    } // constructor
    
    public CharacterElement(char c, int col) {
        this.data = c;
        this.color = col;
    } // constructor

    @Override
    public String toString() {
        return "" + data;
    }
} // CharacterElement
