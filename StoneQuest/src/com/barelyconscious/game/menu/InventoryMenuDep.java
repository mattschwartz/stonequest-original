///* *****************************************************************************
// * Project:          Roguelike2.0
// * File displayName:        InventoryMenu.java
// * Author:           Matt Schwartz
// * Date created:     07.07.2012 
// * Redistribution:   You are free to use, reuse, and edit any of the text in
//                     this file.  You are not allowed to take credit for code
//                     that was not written fully by yourself, or to remove 
//                     credit from code that was not written fully by yourself.  
//                     Please email schwamat@gmail.com for issues or concerns.
// * File description: Responsible for drawing player inventory information to the
//                     Game's screen and all other GUI renderings.
// **************************************************************************** */
//
//package com.barelyconscious.game.menu;
//
//import com.barelyconscious.game.item.Potion;
//import com.barelyconscious.game.Screen;
//import com.barelyconscious.game.player.Player;
//import com.barelyconscious.game.player.Inventory;
//import com.barelyconscious.game.*;
//import com.barelyconscious.game.item.*;
//import com.barelyconscious.game.Sound;
//import com.barelyconscious.game.graphics.Font;
//import com.barelyconscious.game.graphics.LineElement;
//import com.barelyconscious.game.graphics.tiles.Tile;
//import com.barelyconscious.game.input.Interactable;
//import com.barelyconscious.game.input.KeyMap;
//import com.barelyconscious.game.input.MouseHandler;
//import com.barelyconscious.game.spawnable.Loot;
//
//public class InventoryMenuDep extends Interactable implements Menu {
//    private int xOffs;
//    private int yOffs;
//    private int menuLineWidth = 21;
//    private int menuLineHeight = 21; // also the number of inventory slots
//    
//    // Used for item select on Screen
//    private int selectedItem;
//    private final int MIN_SELECTABLE_ITEM = 0;
////    private int tooltipTextHeight = 28;
//    
//    private final Inventory INVENTORY;
//    private final TextLog TEXT_LOG;
//    private final Player PLAYER;
//    private final World WORLD;
//    
//    private String[] invenListBuffer;
//    
//    public InventoryMenu() {
//        WORLD = Game.world;
//        PLAYER = Game.player;
//        INVENTORY = Game.inventory;
//        TEXT_LOG = Game.textLog;
//        defineFocusKey(KeyMap.Key.OPEN_INVENTORY);
//    } // constructor
//    
//    @Override
//    public void resizeMenu(int width, int height) {
//        menuLineWidth = 49;
//        
//        xOffs = (width * Common.SCALE) - ((menuLineWidth + 1) * (Font.CHAR_WIDTH));
//        yOffs = Game.attributesMenu.getOffsY() - (menuLineHeight + 1) * Font.CHAR_HEIGHT + 1;
//        defineMouseZone(xOffs, yOffs + Font.CHAR_HEIGHT, menuLineWidth * Font.CHAR_WIDTH, (menuLineHeight-1) * Font.CHAR_HEIGHT);
//    } // resizeMenu
//    
//    @Override
//    public int getPixelWidth() {
//        return menuLineWidth * Font.CHAR_WIDTH;
//    } // getPixelWidth
//    
//    @Override
//    public int getPixelHeight() {
//        return menuLineHeight * Font.CHAR_HEIGHT;
//    } // getPixelHeight
//    
//    @Override
//    public int getOffsX() {
//        return xOffs;
//    } // getOffsX
//    
//    @Override
//    public int getOffsY() {
//        return yOffs;
//    } // getOffsY
//
//    /**
//     * Moves the cursor up within the Inventory frame to select an item or 
//     * item option.  Does nothing if cursor is at the top of the list
//     */
//    @Override
//    public void moveUp() {
//        if (selectedItem - 1 < MIN_SELECTABLE_ITEM) {
//            return;
//        } // if
//
//        selectedItem--;
//    } // moveUp
//
//    /**
//     * Moves the cursor down in the inventory list.
//     */
//    @Override
//    public void moveDown() {
//        if (selectedItem + 2 > INVENTORY.getNumItems()) {
//            return;
//        } // if
//
//        selectedItem++;
//    } // moveDown
//
//    @Override
//    public void select() {
//    } // select
//
//    @Override
//    public void mouseClicked(int button, int x, int y) {
//        y = y - yOffs - Font.CHAR_HEIGHT - 5;
//        
//        selectedItem = y / Font.CHAR_HEIGHT;
//        if (selectedItem >= 20) {
//            selectedItem = 19;
//        } // if
//        
//        if (button == MouseHandler.LEFT_CLICK) {
//            useItem();
//        } // if
//        
//        else if (button == MouseHandler.RIGHT_CLICK) {
//            examineItem();
//        } // else if
//    } // updateMouseLocation
//
//    @Override
//    public void mouseMoved(int x, int y) {
//        if (!isActive()) {
//            setActive();
//        } // if
//        
//        y = y - yOffs - Font.CHAR_HEIGHT - 5;
//        
//        selectedItem = y / Font.CHAR_HEIGHT;
//        if (selectedItem >= 20) {
//            selectedItem = 19;
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
//        int useItem = KeyMap.getKeyCode(KeyMap.Key.USE_ITEM);
//        int examineItem = KeyMap.getKeyCode(KeyMap.Key.EXAMINE_ITEM);
//        int dropItem = KeyMap.getKeyCode(KeyMap.Key.DROP_ITEM);
//        int salvageItem = KeyMap.getKeyCode(KeyMap.Key.SALVAGE_ITEM);
//        
//        if (key == moveDown) {
//            moveDown();
//        } // else if
//        
//        else if (key == moveUp) {
//            moveUp();
//        } // else if
//        
//        else if (key == useItem) {
//            useItem();
//        } // else if
//        
//        else if (key == examineItem) {
//            examineItem();
//        } // else if
//        
//        else if (key == dropItem) {
//            dropItem();
//        } // else if
//        
//        else if (key == salvageItem) {
//            salvageItem();
//        } // else if
//    } // keyPressed
//
//    @Override
//    public void setActive() {
//        Game.lootWindow.clearFocus();
//        selectedItem = 0;
//        super.setActive();
//    } // setActive
//
//    @Override
//    public void clearFocus() {
//        selectedItem = 0;
//        Game.toolTipMenu.setItem(null);
//        Game.toolTipMenu.clearFocus();
//        super.clearFocus();
//    } // clearFocus
//    
//    /**
//     * Writes the inventory list to the array to be drawn to the Screen in renderSprites().
//     */
//    private void drawInventoryList() {
//        int lineNum = 0;
//        
//        invenListBuffer = new String[menuLineHeight];
//        
//        invenListBuffer[lineNum++] = Common.centerString("Player Inventory", " ", menuLineWidth);
//        
//        for (int i = 0; i < INVENTORY.getNumItems(); i++) {
//            invenListBuffer[lineNum] = INVENTORY.getItemAt(i).getDisplayName();
//            
//            if ( (INVENTORY.getItemAt(i) instanceof Armor) && (((Armor)INVENTORY.getItemAt(i)).isEquipped()) ||
//                    (INVENTORY.getItemAt(i) instanceof Weapon) && (((Weapon)INVENTORY.getItemAt(i)).isEquipped())) {
//                invenListBuffer[lineNum] = Common.alignToSides(invenListBuffer[lineNum], "[Equipped]", menuLineWidth);
//            } // if
//            
//            else if (INVENTORY.getItemAt(i).getStackSize() > 1) {
//                invenListBuffer[lineNum] = Common.alignToSides(invenListBuffer[lineNum], "" + INVENTORY.getItemAt(i).getStackSize(), menuLineWidth);
//            } // else 
//            
//            lineNum++;
//        } // for
//    } // drawInventoryList
//    
//    public void useItem() {
//        String str;
//        Item item = INVENTORY.getItemAt(selectedItem);
//        
//        if (item == null) {
//            return;
//        } // if
//        
//        // Item is a piece of armor
//        if (item instanceof Armor || item instanceof Weapon) {
//            PLAYER.equipItem(item);
//            Sound.EQUIP_ITEM.play();
//        } // if
//
//        // Item is food
//        else if (item instanceof Food) {
//            PLAYER.eat((Food)item);
//            TEXT_LOG.writeFormattedString("You regain some health.", Common.FONT_NULL_RGB);
//
//            item.adjustStackBy(-1);
//        } // else if
//
//        // Item is a potion
//        else if (item instanceof Potion) {
//            PLAYER.quaff(new Potion(item.getDisplayName(), item.getSellValue(), 1, ((Potion)item).getDurationInTicks(), ((Potion)item).getPotionType(), item.getAllAffixes()));
//            str = item.getDisplayName();
//            
//            // Print the message that gets displayed to the screen when the player uses the potion
//            switch(((Potion)item).getPotionType()) {
//                case Potion.STATBUFF:   
//                            str += " grants ";
//                            for (int i = 0; i < item.getNumAffixes() - 1; i++) {
//                                str += item.getAffixAt(i) + " (" + (int)item.getAffixAt(i).getStatMod() + "), ";
//                            } // for
//
//                            str += item.getAffixAt(item.getNumAffixes() - 1) + "(" + (int)item.getAffixAt(item.getNumAffixes() - 1).getStatMod() + ").";
//                            break;
//                    
//                case Potion.ANTIMAGIC:  
//                            str += " cleanses your afflictions.";
//                            break;
//                    
//                case Potion.ANTIVENOM:  
//                            str += " cures your infections.";
//            } // switch
//            
//            TEXT_LOG.writeFormattedString(str, Common.FONT_LOOT_LABEL_RGB, new LineElement(item.getDisplayName(), true, item.getRarityColor()));
//
//            item.adjustStackBy(-1);
//            Sound.DRINK_POTION.play();
//            
//            if (!INVENTORY.addItem(new Item("Glass Bottle", 15, Tile.GLASS_BOTTLE_TILE_ID))) {
//                Loot droppedLoot = new Loot(new Item("Glass Bottle", 15, Tile.GLASS_BOTTLE_TILE_ID), WORLD.getPlayerX(), WORLD.getPlayerY());
//                WORLD.addLoot(droppedLoot);
//            }
//        } // else if
//        
//        // Item is a scroll
//        else if (item instanceof Scroll) {
//            TEXT_LOG.writeFormattedString("The scroll crumbles to dust...", Common.FONT_NULL_RGB);            
//            
////            if (!PLAYER.isScrollIdentified(((Scroll)item).getScrollId())) {
////                ((Scroll)item).identifyScroll();
////                TEXT_LOG.writeFormattedString("It was a " + item.getDisplayName() + "!", Common.FONT_NULL_RGB);
////            } // if
//            
//            PLAYER.read((Scroll)item);
//            
//            item.adjustStackBy(-1);
//            Sound.READ_SCROLL.play();
//        } // else if
//            
//        if (item.getStackSize() <= 0) {
//            INVENTORY.removeItemAt(selectedItem);
//            selectedItem--;
//        } // if
//        
//        if (selectedItem <= 0) {
//            selectedItem = 0;
//        } // if
//        
//        // No more items in inventory, so tooltip should be blank
//        if (INVENTORY.getItemAt(selectedItem) == null) {
//            // stop showing the tooltip since there is no item selected
//        } // if
//        
//        WORLD.tick();
//    } // useItem
//    
//    public void examineItem() {
//        Item item = INVENTORY.getItemAt(selectedItem);
//        
//        if (item == null) {
//            return;
//        } // if
//        
//        TEXT_LOG.writeFormattedString(item.getItemDescription(), Common.FONT_NULL_RGB);
//    } // examineItem
//    
//    public void dropItem() {
//        Item item = INVENTORY.getItemAt(selectedItem);
//        Item droppedItem;
//        Loot droppedLoot;
//        
//        // If selected item to drop exists, drop it in the world
//        if (item != null) {
//            item.adjustStackBy(-1);
//
//            if (item.getStackSize() <= 0 || item instanceof Projectile) {
//                INVENTORY.removeItemAt(selectedItem);
//                selectedItem--;
//                
//                if (selectedItem < 0) {
//                    selectedItem = 0;
//                } // if
//            } // if
//
//            if (item instanceof Armor) {
//                droppedItem = new Armor(item.getDisplayName(), item.getSellValue(), ((Armor)item).getBonusArmor(), ((Armor)item).getArmorType(), item.getTileId(), item.getAllAffixes());
//            } // if
//
//            else if (item instanceof Weapon) {
//                droppedItem = new Weapon(item.getDisplayName(), item.getSellValue(), ((Weapon)item).getMinDamageBonus(), ((Weapon)item).getMaxDamageBonus(), item.getTileId(), item.getAllAffixes());
//            } // else if
//
//            else if (item instanceof Food) { 
//                droppedItem = new Food(item.getDisplayName(), item.getSellValue(), 1, item.getTileId(), ((Food)item).getHealthChange());
//            } // else if
//
//            else if (item instanceof Potion) { 
//                droppedItem = new Potion(item.getDisplayName(), item.getSellValue(), 1, ((Potion)item).getDurationInTicks(), ((Potion)item).getPotionType(), item.getAllAffixes());
//            } // else if
//
//            else if (item instanceof Projectile) { 
//                droppedItem = new Projectile(item.getDisplayName(), item.getSellValue(), item.getStackSize() + 1, item.getTileId(), ((Projectile)item).doesRequireBow(), ((Projectile)item).getMetal());
//            } // else if 
//
//            else if (item instanceof Scroll) {
//                droppedItem = new Scroll(item.getInternalName(), item.getSellValue(), ((Scroll)item).getScrollId(), item.getAllAffixes());
//            } // else if
//            
//            else {
//                droppedItem = new Item(item.getDisplayName(), item.getSellValue(), item.getTileId());
//            } // else
//            
//            droppedItem.setRarityColor(item.getRarityColor());
//            droppedLoot = new Loot(droppedItem, WORLD.getPlayerX(), WORLD.getPlayerY());
//            WORLD.addLoot(droppedLoot);
//        } // if
//        
//        // No more items in inventory, so tooltip should be blank
//        if (INVENTORY.getItemAt(0) == null) {
//            // De-activate the tooltip when this happens and set focus to false
//            clearFocus();
//        } // if
//        
//        WORLD.tick();
//    } // dropItem
//    
//    /**
//     * [NYI] Salvaging items destroys an item in order to regain some of the materials
//     * from which the item is created.  Salvaging an item will almost always grant
//     * fewer monetary gains but will allow for more crafting options for the player.
//     */
//    public void salvageItem() {
//        TEXT_LOG.writeFormattedString("Salvaging is not yet implemented", Common.FONT_NULL_RGB);
//        WORLD.tick();
//    } // salvageItem
//
//    /**
//     * This method is called from Screen to renderSprites any elements it needs to.
//     * @param screen the screen to be drawn to
//     */
//    @Override
//    public final void render(Screen screen) {
//        int fg = Common.THEME_FG_COLOR_RGB;
//        int bg = Common.THEME_BG_COLOR_RGB;
//        
//        drawInventoryList();
//            
//        screen.fillRectangle(fg, xOffs, yOffs, menuLineWidth * Font.CHAR_WIDTH, menuLineHeight * Font.CHAR_HEIGHT + 3);
//        screen.drawLine(bg, xOffs, yOffs + Font.CHAR_HEIGHT + 2, xOffs + menuLineWidth * Font.CHAR_WIDTH, yOffs + Font.CHAR_HEIGHT + 2);
//        
//        // Draw the selected item if the menu is active
//        if (isActive()) {
//            if (INVENTORY.getItemAt(selectedItem) != null) {
//                screen.fillRectangle(bg, xOffs, 
//                        yOffs + ((selectedItem + 1) * Font.CHAR_HEIGHT) + 3, 
//                        menuLineWidth * Font.CHAR_WIDTH, Font.CHAR_HEIGHT);
//            
//                Game.toolTipMenu.setItem(INVENTORY.getItemAt(selectedItem));
//                Game.toolTipMenu.setActive();
//                // Render the tooltip for the item
//                Game.toolTipMenu.render(screen);
//            } // if
//        } // if
//        
//        Font.drawMessage(screen, invenListBuffer[0], Common.THEME_BG_COLOR_RGB, true, xOffs, yOffs);
//        
//        for (int i = 1; i <= INVENTORY.getNumItems(); i++) {
//            if (INVENTORY.getItemAt(selectedItem) != null && isActive() && i == (selectedItem + 1) ) {
//                Font.drawMessage(screen, invenListBuffer[i], fg, true, xOffs, yOffs + 3 + i * Font.CHAR_HEIGHT);
//                continue;
//            } // if
//            
//            Font.drawMessage(screen, invenListBuffer[i], bg, false, xOffs, yOffs + 3 + i * Font.CHAR_HEIGHT);
//        } // for
//    } // renderSprites
//} // InventoryMenu
