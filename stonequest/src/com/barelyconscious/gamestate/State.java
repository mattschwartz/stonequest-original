/* *****************************************************************************
 * Project:           stonequest
 * File Name:         State.java
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

public enum State {

    MAIN_MENU_STATE(0),
    NEW_PLAYER_MENU_STATE(1),
    LOAD_PLAYER_MENU_STATE(2),
    OPTIONS_MENU_STATE(3),
    LOADING_MENU_STATE(4),
    WORLD_STATE(5);

    private final int value;

    private State(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

} // State
