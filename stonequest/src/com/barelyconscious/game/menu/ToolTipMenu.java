/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        ToolTipMenu.java
 * Author:           Matt Schwartz
 * Date created:     02.23.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.menu;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.item.Armor;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.item.Potion;
import com.barelyconscious.game.item.Projectile;
import com.barelyconscious.game.item.Scroll;
import com.barelyconscious.game.item.Weapon;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.player.StatBonus;

public class ToolTipMenu implements Menu {
    private int xOffs;
    private int yOffs;
    private int menuLineWidth;
    private int menuLineHeight;
    private boolean hasFocus;
    private Item itemToDisplay;
    
    private String[] tooltipLines;
    
    @Override
    public void resizeMenu(int w, int h) {
        menuLineWidth = Game.invenMenu.getWidth();
        menuLineHeight = (Game.invenMenu.getOffsY() - 2 * Font.CHAR_HEIGHT) / Font.CHAR_HEIGHT;
        xOffs = Game.invenMenu.getOffsX();
        yOffs = 4;
    } // resizeMenu

    @Override
    public int getWidth() {
        return menuLineWidth;
    } // getWidth

    @Override
    public int getHeight() {
        return menuLineHeight;
    }

    @Override
    public int getOffsX() {
        return xOffs;
    }

    @Override
    public int getOffsY() {
        return yOffs;
    }

    @Override
    public void moveUp() {
        // unused
    }

    @Override
    public void moveDown() {
        // unused
    }

    @Override
    public void select() {
        // unused
    } // select

    @Override
    public boolean isActive() {
        return hasFocus;
    } // isActive

    @Override
    public void setActive() {
        hasFocus = true;
    } // setActive

    @Override
    public void clearFocus() {
        hasFocus = false;
        itemToDisplay = null;
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
        StatBonus stat;
        tooltipLines = new String[menuLineHeight];
        
        // Draw item affixes, if any
        if (itemToDisplay.getNumAffixes() > 0) {
            tooltipLines[lineNum++] = Common.centerString("Item Stats", "-", menuLineWidth);
            for (int i = 0; i < itemToDisplay.getNumAffixes(); i++) {
                stat = itemToDisplay.getAffixAt(i);
                if (stat.getStatMod() < 0) {
                    statmod = "-";
                } else {
                    statmod = "+";
                } // if-else
                
                if (itemToDisplay instanceof Scroll && 
                        !(Game.player.isScrollIdentified( ((Scroll)itemToDisplay).getScrollId())) ) {
                    tooltipLines[lineNum++] = Common.centerString("???", " ", menuLineWidth);
                } // if
                else {
                    statmod += (int)stat.getStatMod();
                    tooltipLines[lineNum++] = Common.alignToSides("" + stat, statmod, menuLineWidth);
                } // else
            } // for
        } // if
        
        if (itemToDisplay instanceof Armor) {
            tooltipLines[lineNum++] = Common.alignToSides("Bonus Armor:", "" + 
                    "+" + ((Armor)itemToDisplay).getBonusArmor(), menuLineWidth);
        } // if
        
        else if (itemToDisplay instanceof Weapon) {
            tooltipLines[lineNum++] = Common.alignToSides("Damage:", 
                    String.format("%.0f-%.0f", ((Weapon)itemToDisplay).getMinDamageBonus(), 
                    ((Weapon)itemToDisplay).getMaxDamageBonus()), menuLineWidth);
        } // else if
        
        else if (itemToDisplay instanceof Projectile) {
            tooltipLines[lineNum++] = Common.alignToSides("Damage:", 
                    String.format("%.0f-%.0f", ((Projectile)itemToDisplay).getMetal().getMin(), 
                    ((Projectile)itemToDisplay).getMetal().getMax()), menuLineWidth);
            
            tooltipLines[lineNum++] = Common.alignToSides("Crit:", 
                    (int)((Projectile)itemToDisplay).getMetal().getCrit() + "%", 
                    menuLineWidth);
        } // else if
        
        if (itemToDisplay instanceof Scroll) {
            if (Game.player.isScrollIdentified(((Scroll)itemToDisplay).getScrollId())) {
                ((Scroll)itemToDisplay).printAdditionalEffects(lineNum++);
            } // if
        } // if
        
        lineNum = menuLineHeight - 4;
        tooltipLines[lineNum++] = Common.rarityIdToString(itemToDisplay.getRarityColor()) + " item.";
        
        if ( (itemToDisplay instanceof Projectile) && ((Projectile)itemToDisplay).requiresBow() ) {
            tooltipLines[lineNum - 2] = "Requires bow to use.";
        } // if
        
        else if ( (itemToDisplay instanceof Potion) && 
                ((Potion)itemToDisplay).getPotionType() == Potion.STATBUFF) {
            tooltipLines[lineNum - 2] = "Effects last for " + ((Potion)itemToDisplay).getDuration() + " turns.";
        } // else if
        
        tooltipLines[lineNum++] = Common.alignToSides("Sell Value:", "" + itemToDisplay.getSellValue(), menuLineWidth);
        tooltipLines[lineNum++] = Common.alignToSides("Item Level:", "" + itemToDisplay.getItemLevel(), menuLineWidth);
        
        optionsText = "";
        for (int i = Item.USE; i <= Item.SALVAGE; i++) {
            optionsText += "[" + itemToDisplay.getOptionKeybind(i) + ": " + itemToDisplay.getOptionText(i) + "]";
        } // for
        tooltipLines[lineNum] = Common.centerString(optionsText, " ", menuLineWidth);
    } // drawItemToolTip
    
    private void drawStatsToolTip() {
        int lineNum = 0;
        tooltipLines = new String[menuLineHeight];
        
        tooltipLines[lineNum++] = Common.alignToSides("Magic Damage", String.format("%.1f-%.1f", Game.player.getMinMagicDamage(), Game.player.getMaxMagicDamage()), menuLineWidth);
        tooltipLines[lineNum++] = Common.alignToSides("Critical Strike Chance", String.format("%.1f%%", Game.player.getCritChance()), menuLineWidth);
        tooltipLines[lineNum++] = Common.alignToSides("Physical Damage Reduction", String.format("%.1f%%", Game.player.getPhysicalDamageReduction()), menuLineWidth);
        tooltipLines[lineNum++] = Common.alignToSides("Evade Chance", String.format("%.1f%%", Game.player.getEvadeChance()), menuLineWidth);
        tooltipLines[lineNum++] = Common.alignToSides("Fire Magic Bonus", String.format("%.1f%%", Game.player.getBonusToElement(Player.FIRE_MAGIC_CURRENT)), menuLineWidth);
        tooltipLines[lineNum++] = Common.alignToSides("Frost Magic Bonus", String.format("%.1f%%", Game.player.getBonusToElement(Player.FROST_MAGIC_CURRENT)), menuLineWidth);
        tooltipLines[lineNum++] = Common.alignToSides("Chaos Magic Bonus", String.format("%.1f%%", Game.player.getBonusToElement(Player.CHAOS_MAGIC_CURRENT)), menuLineWidth);
        tooltipLines[lineNum] = Common.alignToSides("Holy Magic Bonus", String.format("%.1f%%", Game.player.getBonusToElement(Player.HOLY_MAGIC_CURRENT)), menuLineWidth);
        
        lineNum = menuLineHeight - 2;
        
        tooltipLines[lineNum++] = Common.alignToSides("Magic School", "" + Player.idToString(Game.player.getPlayerCastSchool()), menuLineWidth);
        tooltipLines[lineNum] = Common.alignToSides("Bonus Armor", "" + Game.player.getBonusArmor(), menuLineWidth);
    } // drawStatsToolTip

    @Override
    public void render(Screen screen) {
        int fg = Common.THEME_FG_COLOR_RGB;
        int bg = Common.THEME_BG_COLOR_RGB;
        int lineNum = yOffs;
        
        if (!isActive()) {
            return;
        } // if        
        
        screen.fillRectangle(bg, xOffs, yOffs, menuLineWidth * Font.CHAR_WIDTH, menuLineHeight * Font.CHAR_HEIGHT);
        screen.fillRectangle(fg, xOffs, lineNum, menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT);
        
        // Draw tooltip borders
        if (itemToDisplay != null) {
            Font.drawMessage(screen, Common.centerString(itemToDisplay.getDisplayName() 
                    + " x" + itemToDisplay.getStackSize(), " ", menuLineWidth), 
                    bg, true, xOffs, lineNum);

            screen.fillRectangle(fg, xOffs, yOffs + (menuLineHeight - 2) * Font.CHAR_HEIGHT, 
                    menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT * 3);
            
            drawItemTooltip();
            
            for (int i = 0; i <= menuLineHeight - 4; i++) {
                lineNum += Font.CHAR_HEIGHT;
                Font.drawMessage(screen, tooltipLines[i], fg, false, xOffs, lineNum);
            } // for
            
            for (int i = menuLineHeight - 3; i < menuLineHeight; i++) {
                lineNum += Font.CHAR_HEIGHT;
                Font.drawMessage(screen, tooltipLines[i], bg, false, xOffs, lineNum);
            } // for
        } else {
            Font.drawMessage(screen, Common.centerString("Advanced Stats", " ", 
                    menuLineWidth), bg, true, xOffs, lineNum);
            screen.fillRectangle(fg, xOffs, yOffs + (menuLineHeight - 1) * Font.CHAR_HEIGHT, 
                    menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT * 2);
            drawStatsToolTip();
            
            for (int i = 0; i <= menuLineHeight - 3; i++) {
                lineNum += Font.CHAR_HEIGHT;
                Font.drawMessage(screen, tooltipLines[i], fg, false, xOffs, lineNum);
            } // for

            for (int i = menuLineHeight - 2; i < menuLineHeight; i++) {
                lineNum += Font.CHAR_HEIGHT;
                Font.drawMessage(screen, tooltipLines[i], bg, false, xOffs, lineNum);
            } // for
        } // if-else
        
        /*
        for (int i = 0; i < menuLineHeight - 3; i++) {
            lineNum += Font.CHAR_HEIGHT;
            Font.drawMessage(screen, tooltipLines[i], fg, false, xOffs, lineNum);
        } // for
        
        for (int i = menuLineHeight - 3; i < menuLineHeight; i++) {
            lineNum += Font.CHAR_HEIGHT;
            Font.drawMessage(screen, tooltipLines[i], bg, false, xOffs, lineNum);
        } // for
        */
    } // render
} // ToolTipMenu