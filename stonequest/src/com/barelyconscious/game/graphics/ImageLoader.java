/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        ImageLoader.java
 * Author:           Matt Schwartz
 * Date created:     11.04.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.graphics;

public class ImageLoader extends UIElement {
    /**
     * Opens the file containing the image representing the UIElement, loading its data into the pixels array, reporting any
     * errors
     *
     * @param imageFile the name of the file location of the UIElement
     */
    public ImageLoader(String imageFile) {
        super(imageFile);
    } // constructor
} // ImageLoader
