///* *****************************************************************************
// * Project:          Roguelike2.0
// * File displayName:        AttributesMenu.java
// * Author:           Matt Schwartz
// * Date created:     07.06.2012 
// * Redistribution:   You are free to use, reuse, and edit any of the text in
//                     this file.  You are not allowed to take credit for code
//                     that was not written fully by yourself, or to remove 
//                     credit from code that was not written fully by yourself.  
//                     Please email schwamat@gmail.com for issues or concerns.
// * File description: Displays the Player's attributes to the screen in a Menu
//                     object.  Also displays the Player level, XP and unspent
//                     attribute points.  The user can interact with the Menu in
//                     order to select attributes to level up if he or she has 
//                     a least 1 unspent attribute point.
// **************************************************************************** */
//
//package com.barelyconscious.game.menu;
//
//import com.barelyconscious.game.Screen;
//import com.barelyconscious.game.Common;
//import com.barelyconscious.game.Game;
//import com.barelyconscious.game.graphics.Font;
//import com.barelyconscious.game.input.Interactable;
//import com.barelyconscious.game.input.KeyMap;
//import com.barelyconscious.game.input.MouseHandler;
//import com.barelyconscious.game.player.Player;
//
//public class AttributesMenu extends Interactable implements Menu {
//    private static final int MIN_SELECTABLE_STAT = 0;
//    private static final int MAX_SELECTABLE_STAT = 9;
//    private int selectedStat;
//    private int xOffs = 25;
//    private int yOffs = 50;
//    private int menuLineWidth = 49;
//    private int menuLineHeight = 20;
//    
//    private String[] attributesMenuListBuffer;
//    private final Player PLAYER;
//    private final ToolTipMenu TOOLTIP;
//    
//    /**
//     * Create at most one AttributesMenu per runtime for the displaying of 
//     * Player attributes and other Player information
//     * @param player the Player whose attributes are to be displayed
//     * @param tooltip the tooltip is used to display additional information about
//     * the Player when the AttributesMenu gains focus
//     */
//    public AttributesMenu() {
//        PLAYER = Game.player;
//        TOOLTIP = Game.toolTipMenu;
//        defineFocusKey(KeyMap.Key.OPEN_SKILL_TAB);
//    } // constructor
//    
//    /**
//     * Recalculates the x and y offsets for AttributesMenu when the Screen is
//     * resized; the line width and height do not change
//     * @param width
//     * @param height 
//     */
//    @Override
//    public void resizeMenu(int width, int height) {
//        xOffs = width - (menuLineWidth + 1) * Font.CHAR_WIDTH;
//        yOffs = height - menuLineHeight * Font.CHAR_HEIGHT - 33;
//        
//        defineMouseZone(xOffs, yOffs + Font.CHAR_HEIGHT, menuLineWidth * Font.CHAR_WIDTH, (menuLineHeight-1) * Font.CHAR_HEIGHT);
//    } // resizeMenu
//    
//    /**
//     * 
//     * @return the width of the Menu in pixels
//     */
//    @Override
//    public int getPixelWidth() {
//        return menuLineWidth * Font.CHAR_WIDTH;
//    } // getPixelWidth
//    
//    /**
//     * 
//     * @return the height of the Menu in pixels
//     */
//    @Override
//    public int getPixelHeight() {
//        return menuLineHeight * Font.CHAR_HEIGHT;
//    } // getPixelHeight
//    
//    /**
//     * 
//     * @return how wide the Menu is in characters
//     */
//    public int getCharacterLineWidth() {
//        return menuLineWidth;
//    } // getCharacterLineWidth
//    
//    /**
//     * 
//     * @return how tall the Menu is in characters
//     */
//    public int getCharacterLineHeight() {
//        return menuLineHeight;
//    } // getCharacterLineHeight
//    
//    /**
//     * 
//     * @return the x offset for the Menu
//     */
//    @Override
//    public int getOffsX() {
//        return xOffs;
//    } // getPixelWidth
//    
//    /**
//     * 
//     * @return the y offset for the Menu
//     */
//    @Override
//    public int getOffsY() {
//        return yOffs;
//    } // getPixelHeight
//    
//    /**
//     * If the Menu is focused by the player, the highlight "cursor" is moved
//     * up one line; if it is already at the top of the Menu, nothing happens.
//     */
//    @Override
//    public void moveUp() {
//        if (selectedStat - 1 < MIN_SELECTABLE_STAT) {
//            return;
//        } // if
//        
//        selectedStat--;
//    } // moveUp
//
//    /**
//     * If the Menu is focused by the player, the highlight "cursor" is moved up
//     * one line; if it is already at the top of the Menu, nothing happens.
//     */
//    @Override
//    public void moveDown() {
//        if (selectedStat + 1 > MAX_SELECTABLE_STAT) {
//            return;
//        } // if
//        
//        selectedStat++;
//    } // moveDown
//
//    /**
//     * Adds an attribute point to the currently selected attribute if the player
//     * has enough attribute points to spare.
//     */
//    @Override
//    public void select() {
//        PLAYER.addPointToAttribute(selectedStat);
//    } // select
//
//    @Override
//    public void mouseClicked(int button, int x, int y) {
//        y = y - yOffs - Font.CHAR_HEIGHT - 5;
//        
//        selectedStat = y / Font.CHAR_HEIGHT;
//        
//        if (selectedStat > MAX_SELECTABLE_STAT) {
//            selectedStat = MAX_SELECTABLE_STAT;
//        } // if
//        
//        if (button == MouseHandler.LEFT_CLICK) {
//            select();
//        } // if
//        
//        else if (button == MouseHandler.RIGHT_CLICK) {
//            // describe ?
//        } // else if
//    } // mouseClicked
//
//    @Override
//    public void mouseMoved(int x, int y) {
//        if (!isActive()) {
//            setActive();
//        } // if
//        
//        y = y - yOffs - Font.CHAR_HEIGHT - 5;
//        
//        selectedStat = y / Font.CHAR_HEIGHT;
//        
//        if (selectedStat > MAX_SELECTABLE_STAT) {
//            selectedStat = MAX_SELECTABLE_STAT;
//        } // if
//    } // mouseMoved
//
//    @Override
//    public void mouseExited() {
//        clearFocus();
//    } // mouseExited
//
//    @Override
//    public void keyPressed(int key) {
//        int moveDown = KeyMap.getKeyCode(KeyMap.Key.MENU_MOVE_DOWN);
//        int moveUp = KeyMap.getKeyCode(KeyMap.Key.MENU_MOVE_UP);
//        int select = KeyMap.getKeyCode(KeyMap.Key.MENU_SELECT);
//        
//        if (key == moveDown) {
//            moveDown();
//        } // else if
//        
//        else if (key == moveUp) {
//            moveUp();
//        } // else if
//        
//        else if (key == select) {
//            select();
//        } // else if
//    } // keyPressed
//
//    /**
//     * Sets the focus to the AttributesMenu so that the player can interact with
//     * it. 
//     */
//    @Override
//    public void setActive() {
//        Game.lootWindow.clearFocus();
//        selectedStat = 0;
//        TOOLTIP.setItem(null);
//        TOOLTIP.setActive();
//        super.setActive();
//    } // setActive
//
//    /**
//     * Clears the focus of the AttributesMenu when the player changes focus to
//     * another Menu or the world.
//     */
//    @Override
//    public void clearFocus() {
//        TOOLTIP.clearFocus();
//        super.clearFocus();
//    } // clearFocus
//    
//    /**
//     * Fills a String array with information about the player's attributes, level,
//     * XP and unspent attribute points; this array represents what is eventually
//     * drawn to the screen in the AttributesMenu.
//     */
//    private void drawStats() {
//        int lineNum = 0;
//        
//        attributesMenuListBuffer = new String[menuLineHeight];
//        
//        attributesMenuListBuffer[lineNum++] = Common.centerString("Player Stats", " ", 
//                menuLineWidth);
//        
//        for (int i = Player.HITPOINTS; i <= Player.HOLY_MAGIC_BONUS; i++) {
//            attributesMenuListBuffer[lineNum++] = Common.alignToSides(Player.idToString(i), 
//                    (int)PLAYER.getAttribute(i + 10) + "/" + (int)PLAYER.getAttribute(i), 
//                    menuLineWidth);
//        } // for
//        
//        attributesMenuListBuffer[lineNum++] = Common.alignToSides(Player.idToString(
//                Player.PLUS_ALL_MAGIC_BONUS), "" + (int)PLAYER.getAttribute(
//                Player.PLUS_ALL_MAGIC_BONUS), menuLineWidth);
//        
//        lineNum += 6;
//        
//        attributesMenuListBuffer[lineNum++] = Common.alignToSides("Unspent points", "" + 
//                PLAYER.getUnspentAttributePoints(), menuLineWidth);
//        
//        attributesMenuListBuffer[lineNum++] = Common.alignToSides("Level", "" + 
//                PLAYER.getLevel(), menuLineWidth);
//        
//        attributesMenuListBuffer[lineNum] = Common.alignToSides("XP to level", "" + 
//                (PLAYER.getExperienceReq(PLAYER.getLevel() + 1) - 
//                PLAYER.getCurrentExp()), menuLineWidth);
//    } // drawStats
//    
//    /**
//     * This function draws the Menu to the Screen
//     * @param screen the Screen to draw the Menu to
//     */
//    @Override
//    public void render(Screen screen) {
//        int fg = Common.THEME_FG_COLOR_RGB;
//        int bg = Common.THEME_BG_COLOR_RGB;
//        
//        drawStats();
//        
//        screen.fillRectangle(fg, xOffs, yOffs, menuLineWidth * Font.CHAR_WIDTH, menuLineHeight * Font.CHAR_HEIGHT + 3);
//        screen.drawLine(bg, xOffs, yOffs + (menuLineHeight - 3) * Font.CHAR_HEIGHT + 1, xOffs + menuLineWidth * Font.CHAR_WIDTH, yOffs + (menuLineHeight - 3) * Font.CHAR_HEIGHT + 1);
//        
//        // Draw the selected stat if the menu is active
//        if (isActive()) {
//            screen.fillRectangle(bg, xOffs, 
//                    yOffs + ((selectedStat + 1) * Font.CHAR_HEIGHT) + 3, 
//                    menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT);
//            TOOLTIP.setActive();
//            TOOLTIP.render(screen);
//        } // if
//        
//        screen.drawLine(bg, xOffs, yOffs + Font.CHAR_HEIGHT + 2, xOffs + menuLineWidth * Font.CHAR_WIDTH, yOffs + Font.CHAR_HEIGHT + 2);
//        Font.drawMessage(screen, attributesMenuListBuffer[0], bg, true, xOffs, yOffs);
//
//        for (int i = 1; i < menuLineHeight; i++) {
//            if (isActive() && i == (selectedStat + 1) ) {
//                Font.drawMessage(screen, attributesMenuListBuffer[i], fg, true, xOffs + 1, yOffs + 3 + i * Font.CHAR_HEIGHT);
//                continue;
//            } // if
//
//            Font.drawMessage(screen, attributesMenuListBuffer[i], bg, false, xOffs + 2, yOffs + 3 + i * Font.CHAR_HEIGHT);
//        } // for
//        
//    } // onUpdate
//} // AttributesMenu
