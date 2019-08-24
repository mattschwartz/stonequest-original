/* *****************************************************************************
 * Project:          StoneQuest
 * File displayName:        ToolTipMenu.java
 * Author:           Matt Schwartz
 * Date created:     02.23.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: The ToolTipMenu is a contextual Menu that appears depending
                     on the currently active Menu.
 **************************************************************************** */

package com.barelyconscious.game.menu;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.item.Armor;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.item.Potion;
import com.barelyconscious.game.item.Projectile;
import com.barelyconscious.game.item.Scroll;
import com.barelyconscious.game.item.Weapon;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.player.activeeffects.PotionEffect;
import com.barelyconscious.gui.IWidget;

public class ToolTipMenu extends Interactable
    implements Menu, IWidget {

    private int xOffs;
    private int yOffs;
    private int menuLineWidth;
    private int menuLineHeight;
    
    private String[] toolTipMenuListBuffer;
    private Item itemToDisplay;
    
    private final Player player;

    /**
     * Creates a new ToolTipMenu for drawing contextual Menus based on the active
     * Menu
     * @param player required for displaying the current Player's attributes
     */
    public ToolTipMenu(final Player player) {
        this.player = player;
    } // constructor
    
    /**
     * Resizes the ToolTipMenu and alters positioning as necessary based on the
     * new size of the Game's window
     * @param w the new width of the window
     * @param h the new height of the window
     */
    @Override
    public void resize(int w, int h) {
        menuLineWidth =41;
        menuLineHeight = 28;
        xOffs = w - menuLineWidth * Font.CHAR_WIDTH - 15;
        yOffs = 4;
    } // resize

    /**
     * 
     * @return the width of the Menu in pixels
     */
    @Override
    public int getPixelWidth() {
        return menuLineWidth * Font.CHAR_WIDTH;
    } // getPixelWidth

    /**
     * 
     * @return the height of the Menu in pixels
     */
    @Override
    public int getPixelHeight() {
        return menuLineHeight * Font.CHAR_HEIGHT;
    } // getPixelHeight

    /**
     * 
     * @return the x offset for the Menu
     */
    @Override
    public int getOffsX() {
        return xOffs;
    } // getOffsX

    /**
     * 
     * @return the y offset for the Menu
     */
    @Override
    public int getOffsY() {
        return yOffs;
    } // getOffsY
    
    /**
     * Function is not used by this Menu.
     */
    @Override
    public void moveUp() {
        // unused
    } // moveUp

    /**
     * Function is not used by this Menu.
     */
    @Override
    public void moveDown() {
        // unused
    } // moveDown

    /**
     * Function is not used by this Menu.
     */
    @Override
    public void select() {
        // unused
    } // select

    /**
     * Removes focus from the Menu so that it is no longer drawn to the Screen.
     */
    @Override
    public void clearFocus() {
        toolTipMenuListBuffer = new String[menuLineHeight];
        super.clearFocus();
    } // clearFocus
    
    /**
     * Sets the item to be drawn in the tooltip to item.
     * @param item 
     */
    public void setItem(Item item) {
        itemToDisplay = item; 
   } // setItem
    
    /**
     * If ToolTipMenu was called to draw the tooltip for a particular item, this
     * function will be called to consolidate the drawing function into one
     * function.
     */
    private void drawItemTooltip() {
        int lineNum = 0;
        String optionsText;
        String statmod;
        AttributeMod stat;
        toolTipMenuListBuffer = new String[menuLineHeight];
        
        if (itemToDisplay == null) {
            return;
        }
        
        // Draw item affixes, if any
        if (itemToDisplay.getNumAffixes() > 0) {
            toolTipMenuListBuffer[lineNum++] = Common.centerString("Item Stats", "-", menuLineWidth);
            for (int i = 0; i < itemToDisplay.getNumAffixes(); i++) {
                stat = itemToDisplay.getAffixAt(i);
                if (stat.getAttributeModifier() < 0) {
                    statmod = "-";
                } else {
                    statmod = "+";
                } // if-else
                
                if (itemToDisplay instanceof Scroll && 
                        !(player.isScrollIdentified( ((Scroll)itemToDisplay).getScrollId())) ) {
                    toolTipMenuListBuffer[lineNum++] = Common.centerString("???", " ", menuLineWidth);
                } // if
                else {
                    statmod += (int)stat.getAttributeModifier();
                    toolTipMenuListBuffer[lineNum++] = Common.alignToSides("" + stat, statmod, menuLineWidth);
                } // else
            } // for
        } // if
        
        if (itemToDisplay instanceof Armor) {
            toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Bonus Armor:", "" + 
                    "+" + ((Armor)itemToDisplay).getBonusArmor(), menuLineWidth);
        } // if
        
        else if (itemToDisplay instanceof Weapon) {
            toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Damage:", 
                    String.format("%.0f-%.0f", ((Weapon)itemToDisplay).getMinDamageBonus(), 
                    ((Weapon)itemToDisplay).getMaxDamageBonus()), menuLineWidth);
        } // else if
        
        else if (itemToDisplay instanceof Projectile) {
            toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Damage:", 
                    String.format("%.0f-%.0f", ((Projectile)itemToDisplay).getMetal().getMin(), 
                    ((Projectile)itemToDisplay).getMetal().getMax()), menuLineWidth);
            
            toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Crit:", 
                    (int)((Projectile)itemToDisplay).getMetal().getCrit() + "%", 
                    menuLineWidth);
        } // else if
        
        if (itemToDisplay instanceof Scroll) {
            if (player.isScrollIdentified(((Scroll)itemToDisplay).getScrollId())) {
//                ((Scroll)itemToDisplay).printAdditionalEffects(lineNum++);
            } // if
        } // if
        
        lineNum = menuLineHeight - 4;
        toolTipMenuListBuffer[lineNum++] = Common.rarityIdToString(itemToDisplay.getRarityColor()) + " item.";
        
        if ( (itemToDisplay instanceof Projectile) && ((Projectile)itemToDisplay).doesRequireBow() ) {
            toolTipMenuListBuffer[lineNum - 2] = "Requires bow to use.";
        } // if
        
        else if ( (itemToDisplay instanceof Potion) && 
                ((Potion)itemToDisplay).getEffects().getPotionType() == PotionEffect.STATBUFF) {
            toolTipMenuListBuffer[lineNum - 2] = "Effects last for " + ((Potion)itemToDisplay).getEffects().getDurationInTicks() + " turns.";
        } // else if
        
        toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Sell Value:", "" + itemToDisplay.getSellValue(), menuLineWidth);
        toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Item Level:", "" + itemToDisplay.getItemLevel(), menuLineWidth);
        
        optionsText = "";
        for (int i = Item.USE; i <= Item.SALVAGE; i++) {
            optionsText += "[" + itemToDisplay.getOptionKeybind(i) + ": " + itemToDisplay.getOptionText(i) + "]";
        } // for
        toolTipMenuListBuffer[lineNum] = Common.centerString(optionsText, " ", menuLineWidth);
    } // drawItemToolTip
    
    /**
     * If the player has focused on the AttributesMenu for the game, the tooltip
     * is called to draw advanced information for the Player's attributes as well
     * as bonus armor and the currently selected school of magic for casting spells.
     */
    private void drawStatsToolTip() {
        int lineNum = 0;
        toolTipMenuListBuffer = new String[menuLineHeight];
        
        toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Magic Damage", String.format("%.1f-%.1f", player.getMinMagicDamage(), player.getMaxMagicDamage()), menuLineWidth);
        toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Critical Strike Chance", String.format("%.1f%%", player.getCritChance()), menuLineWidth);
        toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Physical Damage Reduction", String.format("%.1f%%", player.getPhysicalDamageReduction()), menuLineWidth);
        toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Evade Chance", String.format("%.1f%%", player.getEvadeChance()), menuLineWidth);
        toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Fire Magic Bonus", String.format("%.1f%%", player.getBonusToElement(Player.FIRE_MAGIC_CURRENT)), menuLineWidth);
        toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Frost Magic Bonus", String.format("%.1f%%", player.getBonusToElement(Player.FROST_MAGIC_CURRENT)), menuLineWidth);
        toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Chaos Magic Bonus", String.format("%.1f%%", player.getBonusToElement(Player.CHAOS_MAGIC_CURRENT)), menuLineWidth);
        toolTipMenuListBuffer[lineNum] = Common.alignToSides("Holy Magic Bonus", String.format("%.1f%%", player.getBonusToElement(Player.HOLY_MAGIC_CURRENT)), menuLineWidth);
        
        lineNum = menuLineHeight - 2;
        
        toolTipMenuListBuffer[lineNum++] = Common.alignToSides("Magic School", "" + Player.idToString(player.getSchoolOfMagic()), menuLineWidth);
        toolTipMenuListBuffer[lineNum] = Common.alignToSides("Bonus Armor", "" + player.getBonusArmor(), menuLineWidth);
    } // drawStatsToolTip

    /**
     * Draws the ToolTipMenu to the screen only if it has focus; this function 
     * immediately returns if it does not have focus
     * @param screen the Screen to draw the Menu to
     */
    @Override
    public void render(Screen screen) {
        int fg = Common.themeForegroundColor;
        int bg = Common.THEME_BG_COLOR_RGB;
        int lineNum = yOffs;
        
        /* If the Menu is not active, don't draw anything */
        if (!isActive()) {
            return;
        } // if
        
        /* Draw the background and title bar for the ToolTipMenu */
        screen.fillRectangle(bg, xOffs, yOffs, menuLineWidth * Font.CHAR_WIDTH, menuLineHeight * Font.CHAR_HEIGHT);
        screen.fillRectangle(fg, xOffs, lineNum, menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT);
        
        /* Draw the contextual Menu for the Item information to the Menu if the 
            InventoryMenu is the currently active Menu */
        if (itemToDisplay != null) {
            Font.drawMessage(screen, Common.centerString(itemToDisplay.getDisplayName() 
                    + " x" + itemToDisplay.getStackSize(), " ", menuLineWidth), 
                    bg, true, xOffs, lineNum);
            
            /* Prepares the String buffer with Item information to draw it to the
                Screen */
            drawItemTooltip();
            
            /* Draw the uppermost lines of the Item description in the foreground
                theme color for aesthetics */
            for (int i = 0; i <= menuLineHeight - 4; i++) {
                lineNum += Font.CHAR_HEIGHT;
                Font.drawMessage(screen, toolTipMenuListBuffer[i], fg, false, xOffs, lineNum);
            } // for
            
//            /* Don't draw the Item option text if the loot window is active */
//            if (lootWindow.isActive()) {
//                return;
//            } // if

            /* Adds a foreground colored bar at the bottom of the contextual Menu
                for aesthetics, where information common to all Items are drawn.  The
                color is the "foreground" color of the currently selected theme */
            screen.fillRectangle(fg, xOffs, yOffs + (menuLineHeight - 2) * Font.CHAR_HEIGHT, 
                    menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT * 3);
            
            /* Draw the bottom lines of the Item description in the background
                theme color for aesthetics */
            for (int i = menuLineHeight - 3; i < menuLineHeight; i++) {
                lineNum += Font.CHAR_HEIGHT;
                Font.drawMessage(screen, toolTipMenuListBuffer[i], bg, false, xOffs, lineNum);
            } // for
            
            screen.fillRectangle(bg, xOffs - Common.TILE_SIZE - 3, yOffs, Common.TILE_SIZE + 1, Common.TILE_SIZE + 1);
            screen.drawRectangle(fg, xOffs - Common.TILE_SIZE - 3, yOffs, Common.TILE_SIZE + 1, Common.TILE_SIZE + 1);
            Tile.getTile(itemToDisplay.getTileId()).render(screen, xOffs - Common.TILE_SIZE - 2, yOffs + 1);
        } // if
        
        /* Draw the contextual Menu for the player's advanced attribute information */
        else {
            /* Write the title of the attribute contextual Menu to the top of
                the Menu*/
            Font.drawMessage(screen, Common.centerString("Advanced Stats", " ", 
                    menuLineWidth), bg, true, xOffs, lineNum);
            
            /* Draw a title bar for aesthetics */
            screen.fillRectangle(fg, xOffs, yOffs + (menuLineHeight - 1) * Font.CHAR_HEIGHT, 
                    menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT * 2);
            
            /* Fill the String buffer with advanced stats information to draw it
                to the Menu */
            drawStatsToolTip();
            
            /* Draw the upper lines of the menu as the foreground theme color for
                aesthetics */
            for (int i = 0; i <= menuLineHeight - 3; i++) {
                lineNum += Font.CHAR_HEIGHT;
                Font.drawMessage(screen, toolTipMenuListBuffer[i], fg, false, xOffs, lineNum);
            } // for

            /* Draw the lower lines of the menu as the background theme color for
                aesthetics */
            for (int i = menuLineHeight - 2; i < menuLineHeight; i++) {
                lineNum += Font.CHAR_HEIGHT;
                Font.drawMessage(screen, toolTipMenuListBuffer[i], bg, false, xOffs, lineNum);
            } // for
        } // if-else
    } // render
} // ToolTipMenu
