/* *****************************************************************************
 * Project:           stonequest
 * File Name:         KeyMap.java
 * Author:            Matt Schwartz
 * Date Created:      05.15.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.input;

import com.barelyconscious.gamestate.State;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class KeyMap {

    private static final Map<Integer, KeyAction> keymap;
    private static boolean showFPS = false;
    
    public static int playerMoveUp = Keyboard.KEY_W;
    public static int playerMoveDown = Keyboard.KEY_S;
    public static int playerMoveLeft = Keyboard.KEY_A;
    public static int playerMoveRight = Keyboard.KEY_D;
    
    public static int pickupItemKey = Keyboard.KEY_P;

    static {
        keymap = new HashMap<>();
        keymap.put((int) 'i', new KeyAction() {

            @Override
            public void invoke(KeyboardArgs args) {
                openInventory();
            }
        });
        keymap.put((int) 'c', new KeyAction() {

            @Override
            public void invoke(KeyboardArgs args) {
                openCharacterSheet();
            }
        });
        keymap.put(Keyboard.KEY_F12, new KeyAction() {

            @Override
            public void invoke(KeyboardArgs args) {
                showFPS(args);
            }
        });
        keymap.put(Keyboard.KEY_ESCAPE, new KeyAction() {

            @Override
            public void invoke(KeyboardArgs args) {
                toggleOptionsMenu(args);
            }
        });
    }

    public static void changeKeybinding(int oldKey, int newKey) {
        if (!keymap.containsKey(oldKey)) {
            return;
        }

        KeyAction ka = keymap.get(oldKey);
        keymap.put(newKey, ka);
        keymap.remove(oldKey);
    }

    public static void invoke(KeyboardArgs args) {
        if (keymap.containsKey(args.keyCode)) {
            keymap.get(args.keyCode).invoke(args);
        }
    }

    private static void openInventory() {
    }

    private static void openCharacterSheet() {
    }

    private static void showFPS(KeyboardArgs args) {
        showFPS = !showFPS;

        args.container.setShowFPS(showFPS);
    }

    private static void toggleOptionsMenu(KeyboardArgs args) {
        if (args.game.getCurrentStateID() == State.IN_GAME_OPTIONS_MENU_STATE.getValue()) {
            args.game.enterState(State.WORLD_STATE.getValue(), new FadeOutTransition(Color.black, 175), new FadeInTransition(Color.black, 175));
        } else {
            args.game.enterState(State.IN_GAME_OPTIONS_MENU_STATE.getValue(), new FadeOutTransition(Color.black, 175), new FadeInTransition(Color.black, 175));
        }
    }
} // KeyMap
