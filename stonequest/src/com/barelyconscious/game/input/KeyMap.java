/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        KeyMap.java
 * Author:           Matt Schwartz
 * Date created:     07.06.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Keeps track of the each adjustable keybind for the player.
 *                   Keybinds determine how the game should react when its 
 *                   corresponding key is pressed on the keyboard.  Two default
 *                   maps are available for quickloading in functions below.
 **************************************************************************** */
package com.barelyconscious.game.input;

import com.barelyconscious.game.World;
import com.barelyconscious.game.graphics.gui.ingamemenu.TextLog;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.game.services.WindowManager;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public abstract class KeyMap {
    // Player-related keybindings

    public static final int PLAYER_MOVE_UP_DEFAULT_BINDING = KeyEvent.VK_W;
    public static final int PLAYER_MOVE_DOWN_DEFAULT_BINDING = KeyEvent.VK_S;
    public static final int PLAYER_MOVE_LEFT_DEFAULT_BINDING = KeyEvent.VK_A;
    public static final int PLAYER_MOVE_RIGHT_DEFAULT_BINDING = KeyEvent.VK_D;
    public static final int PLAYER_MOVE_UP_LEFT_DEFAULT_BINDING = KeyEvent.VK_Q;
    public static final int PLAYER_MOVE_UP_RIGHT_DEFAULT_BINDING = KeyEvent.VK_E;
    public static final int PLAYER_MOVE_DOWN_LEFT_DEFAULT_BINDING = KeyEvent.VK_Z;
    public static final int PLAYER_MOVE_DOWN_RIGHT_DEFAULT_BINDING = KeyEvent.VK_X;
    public static final int PLAYER_SKIP_TURN_DEFAULT_BINDING = KeyEvent.VK_SPACE;
    // Interface-related keybindings
    public static final int OPEN_INVENTORY_WINDOW_DEFAULT_BINDING = KeyEvent.VK_I;
    public static final int OPEN_CHARACTER_WINDOW_DEFAULT_BINDING = KeyEvent.VK_C;
    public static final int OPEN_UPGRADE_WEAPON_WINDOW_DEFAULT_BINDING = KeyEvent.VK_U;
    public static final int OPEN_JOURNAL_WINDOW_DEFAULT_BINDING = KeyEvent.VK_J;
    public static final int OPEN_SALVAGE_ITEM_WINDOW_DEFAULT_BINDING = KeyEvent.VK_K;
    public static final int OPEN_BREWING_WINDOW_DEFAULT_BINDING = KeyEvent.VK_B;
    public static final int ESCAPE_BUTTON_DEFAULT_BINDING = KeyEvent.VK_ESCAPE;
    public static final int PRINT_SCREEN_BUTTON_DEFAULT_BINDING = KeyEvent.VK_F12;
    // Player-related actions
    public static final KeyAction PLAYER_MOVE_UP = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            World.INSTANCE.getPlayer().moveUp();
            World.INSTANCE.tick();
        }
    };
    public static final KeyAction PLAYER_MOVE_DOWN = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            World.INSTANCE.getPlayer().moveDown();
            World.INSTANCE.tick();
        }
    };
    public static final KeyAction PLAYER_MOVE_LEFT = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            World.INSTANCE.getPlayer().moveLeft();
            World.INSTANCE.tick();
        }
    };
    public static final KeyAction PLAYER_MOVE_RIGHT = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            World.INSTANCE.getPlayer().moveRight();
            World.INSTANCE.tick();
        }
    };
    public static final KeyAction PLAYER_MOVE_UP_LEFT = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            World.INSTANCE.getPlayer().moveUpLeft();
            World.INSTANCE.tick();
        }
    };
    public static final KeyAction PLAYER_MOVE_UP_RIGHT = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            World.INSTANCE.getPlayer().moveUpRight();
            World.INSTANCE.tick();
        }
    };
    public static final KeyAction PLAYER_MOVE_DOWN_LEFT = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            World.INSTANCE.getPlayer().moveDownLeft();
            World.INSTANCE.tick();
        }
    };
    public static final KeyAction PLAYER_MOVE_DOWN_RIGHT = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            World.INSTANCE.getPlayer().moveDownRight();
            World.INSTANCE.tick();
        }
    };
    public static final KeyAction PLAYER_SKIP_TURN = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            TextLog.INSTANCE.append("You twiddle your thumbs.");
            World.INSTANCE.tick();
        }
    };
    // Interface-related actions
    public static final KeyAction OPEN_INVENTORY_WINDOW = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            WindowManager.INVENTORY_WINDOW.toggleUI();
        }
    };
    public static final KeyAction OPEN_CHARACTER_WINDOW = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            WindowManager.CHARACTER_WINDOW.toggleUI();
        }
    };
    public static final KeyAction OPEN_UPGRADE_WEAPON_WINDOW = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            WindowManager.UPGRADE_ITEM_WINDOW.toggleUI();
        }
    };
    public static final KeyAction OPEN_JOURNAL_WINDOW = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            WindowManager.JOURNAL_WINDOW.toggleUI();
        }
    };
    public static final KeyAction OPEN_SALVAGE_ITEM_WINDOW = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            WindowManager.SALVAGE_WINDOW.toggleUI();
        }
    };
    public static final KeyAction OPEN_BREWING_WINDOW = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            WindowManager.BREWING_WINDOW.toggleUI();
        }
    };
    public static final KeyAction ESCAPE_BUTTON = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            WindowManager.INSTANCE.closeAllWindows();
        }
    };
    public static final KeyAction PRINT_SCREEN_BUTTON = new KeyAction() {
        @Override
        public void action(KeyEvent e) {
            SceneService.INSTANCE.saveScreenshot();
        }
    };
    private static Map<Integer, KeyAction> keyBindings = new HashMap<Integer, KeyAction>();

    public static void addKeyBinding(int key, KeyAction action) {
        // Overwriting a key binding
        if (keyBindings.containsKey(key)) {
            keyBindings.remove(key);
        }

        keyBindings.put(key, action);
    }

    public static void setDefaultKeyBindings() {
        setDefaultPlayerKeyBindings();
        setDefaultInterfaceKeyBindings();
    }

    public static void setDefaultPlayerKeyBindings() {
        addKeyBinding(PLAYER_MOVE_LEFT_DEFAULT_BINDING, PLAYER_MOVE_LEFT);
        addKeyBinding(PLAYER_MOVE_RIGHT_DEFAULT_BINDING, PLAYER_MOVE_RIGHT);
        addKeyBinding(PLAYER_MOVE_UP_DEFAULT_BINDING, PLAYER_MOVE_UP);
        addKeyBinding(PLAYER_MOVE_DOWN_DEFAULT_BINDING, PLAYER_MOVE_DOWN);
        addKeyBinding(PLAYER_MOVE_LEFT_DEFAULT_BINDING, PLAYER_MOVE_LEFT);
        addKeyBinding(PLAYER_MOVE_RIGHT_DEFAULT_BINDING, PLAYER_MOVE_RIGHT);
        addKeyBinding(PLAYER_MOVE_UP_LEFT_DEFAULT_BINDING, PLAYER_MOVE_UP_LEFT);
        addKeyBinding(PLAYER_MOVE_UP_RIGHT_DEFAULT_BINDING, PLAYER_MOVE_UP_RIGHT);
        addKeyBinding(PLAYER_MOVE_DOWN_LEFT_DEFAULT_BINDING, PLAYER_MOVE_DOWN_LEFT);
        addKeyBinding(PLAYER_MOVE_DOWN_RIGHT_DEFAULT_BINDING, PLAYER_MOVE_DOWN_RIGHT);
        addKeyBinding(PLAYER_SKIP_TURN_DEFAULT_BINDING, PLAYER_SKIP_TURN);

        addKeyBinding(KeyEvent.VK_LEFT, PLAYER_MOVE_LEFT);
        addKeyBinding(KeyEvent.VK_RIGHT, PLAYER_MOVE_RIGHT);
        addKeyBinding(KeyEvent.VK_UP, PLAYER_MOVE_UP);
        addKeyBinding(KeyEvent.VK_DOWN, PLAYER_MOVE_DOWN);

    }

    public static void setDefaultInterfaceKeyBindings() {
        addKeyBinding(OPEN_INVENTORY_WINDOW_DEFAULT_BINDING, OPEN_INVENTORY_WINDOW);
        addKeyBinding(OPEN_CHARACTER_WINDOW_DEFAULT_BINDING, OPEN_CHARACTER_WINDOW);
        addKeyBinding(OPEN_UPGRADE_WEAPON_WINDOW_DEFAULT_BINDING, OPEN_UPGRADE_WEAPON_WINDOW);
        addKeyBinding(OPEN_JOURNAL_WINDOW_DEFAULT_BINDING, OPEN_JOURNAL_WINDOW);
        addKeyBinding(OPEN_SALVAGE_ITEM_WINDOW_DEFAULT_BINDING, OPEN_SALVAGE_ITEM_WINDOW);
        addKeyBinding(OPEN_BREWING_WINDOW_DEFAULT_BINDING, OPEN_BREWING_WINDOW);
        addKeyBinding(ESCAPE_BUTTON_DEFAULT_BINDING, ESCAPE_BUTTON);
        addKeyBinding(PRINT_SCREEN_BUTTON_DEFAULT_BINDING, PRINT_SCREEN_BUTTON);
    }

    public static KeyAction getAction(int key) {
        if (!keyBindings.containsKey(key)) {
            return null;
        }

        return keyBindings.get(key);
    }
}
