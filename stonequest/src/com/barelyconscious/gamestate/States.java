/* *****************************************************************************
 * Project:           stonequest
 * File Name:         States.java
 * Author:            Matt Schwartz
 * Date Created:      05.15.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gamestate;

public enum States {

    LOADING_STATE(0),
    WORLD_STATE(1);

    private final int value;

    private States(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

} // States
