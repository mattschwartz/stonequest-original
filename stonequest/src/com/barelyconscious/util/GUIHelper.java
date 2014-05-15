/* *****************************************************************************
 * Project:           stonequest
 * File Name:         GUIHelper.java
 * Author:            Matt Schwartz
 * Date Created:      05.14.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.util;

import de.matthiasmann.twl.Widget;
import org.lwjgl.opengl.Display;

public class GUIHelper {

    public static void setSize(Widget w, float absX, float absY, float relX, float relY) {
        float x = Display.getWidth() * absX + relX;
        float y = Display.getHeight() * absY + relY;

        w.setSize((int) x, (int) y);
    }

    public static void setPosition(Widget w, float absX, float absY, float relX, float relY) {
        float x = Display.getWidth() * absX + relX;
        float y = Display.getHeight() * absY + relY;

        w.setPosition((int) x, (int) y);
    }
} // GUIHelper
