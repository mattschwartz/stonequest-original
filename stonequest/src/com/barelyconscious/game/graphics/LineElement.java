/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        LineElement.java
 * Author:           Matt Schwartz
 * Date created:     07.04.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.graphics;

import com.barelyconscious.game.item.Item;
import java.awt.Color;

public class LineElement {
    private String sElement;
    private boolean isBold;
    private int msgColor;

    public LineElement(String str, boolean bold, int color) {
        sElement = str;
        isBold = bold;
        msgColor = color;
    } // constructor
    
    public LineElement(Item item) {
        sElement = "[" + item.getInternalName() + "]";
        isBold = true;
        msgColor = item.getRarityColor();
    } // constructor
    
    public LineElement(String str, boolean bold, Color co) {
        sElement = str;
        isBold = bold;
        msgColor = co.getRGB();
    } // constructor
    
    public String getElement() {
        return sElement;
    } // getElement

    public int getColor() {
        return msgColor;
    } // getColor
    
    public void setColor(int color) {
        msgColor = color;
    } // setColor

    public boolean getBold() {
        return isBold;
    } // getFont

    public void setBold(boolean bold) {
        isBold = bold;
    } // setFont

    @Override
    public String toString() {
        return "" + sElement;
    } // toString
} // LineElement