/* *****************************************************************************
 * File Name:         UIElement.java
 * Author:            Matt Schwartz
 * Date Created:      01.03.2013
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 this file.  You are not allowed to take credit for code
 that was not written fully by yourself, or to remove 
 credit from code that was not written fully by yourself.  
 Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  Similar to the Tile.java class, but these images serve
 a different purpose as icons.  And then a font sheet.
 ************************************************************************** */
package com.barelyconscious.game.graphics;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class UIElement {

    private final int THEME_REPLACEMENT_COLOR = new Color(13, 13, 13).getRGB();

    /**
     * Image containing characters used in drawing Strings to the screen.
     */
    public static final BufferedImage FONT_SHEET = loadImage("/GUI/icons/fontsheet.png");
    /**
     * The icon representing a cursed effect on the player.
     */
    public static final UIElement CURSE_ICON = new UIElement("/icons/active_effects/debuff_curse.png");
    /**
     * The icon representing a poisoned effect on the player.
     */
    public static final UIElement POISON_ICON = new UIElement("/icons/active_effects/debuff_poison.png");
    /**
     * The icon representing a potion effect on the player.
     */
    public static final UIElement POTION_ICON = new UIElement("/icons/active_effects/buff_potion.png");
    /**
     * The icon representing the coin pouch, where all the player's gold is stored.
     */
    public static final UIElement COIN_POUCH_ICON = new UIElement("/GUI/icons/coin_pouch.png");
    public static final UIElement INVENTORY_BACKGROUND = new UIElement("/GUI/icons/inventory_background.png");
    public static final UIElement INVENTORY_TAB = new UIElement("/GUI/icons/inventory_tab.png");
    public static final UIElement INVENTORY_EXTENDER = new UIElement("/GUI/icons/tab_extender.png");
    /**
     * UIElement representing that the user can level up this skill.
     */
    public static final UIElement SKILL_UP_ICON = new UIElement("/GUI/icons/skill_icons/skill_raise.png");
    public static final UIElement HITPOINTS_ICON = new UIElement("/GUI/icons/skill_icons/hitpoints_icon.png");
    public static final UIElement AGILITY_ICON = new UIElement("/GUI/icons/skill_icons/agility_icon.png");
    public static final UIElement ACCURACY_ICON = new UIElement("/GUI/icons/skill_icons/accuracy_icon.png");
    public static final UIElement DEFENSE_ICON = new UIElement("/GUI/icons/skill_icons/defense_icon.png");
    public static final UIElement STRENGTH_ICON = new UIElement("/GUI/icons/skill_icons/strength_icon.png");
    public static final UIElement FIRE_MAGIC_ICON = new UIElement("/GUI/icons/skill_icons/fire_magic_icon.png");
    public static final UIElement FROST_MAGIC_ICON = new UIElement("/GUI/icons/skill_icons/frost_magic_icon.png");
    public static final UIElement HOLY_MAGIC_ICON = new UIElement("/GUI/icons/skill_icons/holy_magic_icon.png");
    public static final UIElement CHAOS_MAGIC_ICON = new UIElement("/GUI/icons/skill_icons/chaos_magic_icon.png");
    public static final UIElement ALL_MAGIC_ICON = new UIElement("/GUI/icons/skill_icons/all_magic_icon.png");
    public static final UIElement CLOSE_BUTTON_ICON = new UIElement("/GUI/icons/close_button.png");

    // GUI elements - buffs and debuffs
    public static final UIElement BUFF_BAR_POPOUT = new UIElement("/GUI/buffbar/buff_frame_popout.png");
    public static final UIElement DEBUFF_BAR_POPOUT = new UIElement("/GUI/buffbar/debuff_frame_popout.png");
    public static final UIElement ACTIVE_EFFECT_BACKGROUND_FRAME = new UIElement("/GUI/buffbar/active_effect_background_frame.png");
    public static final UIElement DROP_DOWN_ARROW = new UIElement("/GUI/drop_down_notify/drop_down_arrow.png");

    // GUI elements - the minimap
    public static final UIElement MINIMAP_FRAME = new UIElement("/GUI/minimap.png");
    public static final UIElement MINIMAP_ZONE_LEVEL_IDENTIFIER_TAB = new UIElement("/GUI/minimap_zone_level_identifier_tab.png");
    public static final UIElement MINIMAP_REMAINING_ELITES_IDENTIFIER_TAB = new UIElement("/GUI/minimap_remaining_elites_identifier_tab.png");

    // GUI elements - unit frames
    public static final UIElement UNITFRAME_PLAYER = new UIElement("/GUI/unitframe_player.png");
    public static final UIElement UNITFRAME_PLAYER_HEALTHBAR = new UIElement("/GUI/unitframe_player_healthbar.png");
    public static final UIElement UNITFRAME_ENTITY_HEALTHBAR = new UIElement("/GUI/unitframe_entity_healthbar.png");
    public static final UIElement UNITFRAME_ENTITY = new UIElement("/GUI/unitframe_entity.png");
    public static final UIElement UNITFRAME_ELITE = new UIElement("/GUI/unitframe_elite.png");

    // GUI elements - Inventory Frame
    public static final UIElement INVENTORY_FRAME = new UIElement("/GUI/inventory/inventory_frame.png");
    public static final UIElement INVENTORY_ITEM_INFO_FRAME_TOP = new UIElement("/GUI/inventory/inventory_item_info_frame_top.png");
    public static final UIElement INVENTORY_ITEM_INFO_FRAME_BOTTOM = new UIElement("/GUI/inventory/inventory_item_info_frame_bottom.png");
    public static final UIElement INVENTORY_ITEM_SELECT_HIGHLIGHT = new UIElement("/GUI/inventory/item_select_highlight.png");
    public static final UIElement INVENTORY_BAG_SELECT_ELEMENT = new UIElement("/GUI/inventory/inventory_bag_select_element.png");
    public static final UIElement INVENTORY_BAG_SELECT_NOTIFY = new UIElement("/GUI/inventory/open_bag_notify.png");

    // GUI elements - Attributes Frame
    public static final UIElement ATTRIBUTES_FRAME = new UIElement("/GUI/attributes/stats_frame.png");
    public static final UIElement ATTRIBUTES_HIGHLIGHTED_DETAILS_BUTTON = new UIElement("/GUI/attributes/details_button_highlight.png");
    public static final UIElement ATTRIBUTES_HIGHLIGHTED_STAT = new UIElement("/GUI/attributes/selected_stat_highlight.png");
    public static final UIElement ATTRIBUTES_DETAILS_PAGE_VISIBLE = new UIElement("/GUI/attributes/details_page_visible.png");
    public static final UIElement ATTRIBUTES_DETAILS_PAGE_NOT_VISIBLE = new UIElement("/GUI/attributes/details_page_not_visible.png");

    /**
     * The RGB color value of the transparent color 255,0,255 which is used to provide transparency when drawing the
     * UIElement.
     */
    private final int TRANSPARENT_COLOR = new Color(255, 0, 255).getRGB(); // 255,0,255
    /**
     * The flattened 2D containing pixel data of the UIElement.
     */
    private int[] pixels;
    /**
     * The width of the image.
     */
    private int width;
    /**
     * The height of the image.
     */
    private int height;

    /**
     * Opens the file containing the image representing the UIElement, loading its data into the pixels array, reporting any
     * errors
     *
     * @param imageFile the displayName of the file location of the UIElement
     */
    public UIElement(String imageFile) {
        System.out.print(" [NOTIFY] Loading image \"" + trim(imageFile) + "\"...");
        BufferedImage img = loadImage(imageFile);
        width = img.getWidth();
        height = img.getHeight();
        pixels = img.getRGB(0, 0, width, height, null, 0, width);
        System.out.println("done.");
    }

    /**
     * Loads an image from file location iconFile into a BufferedImage which it then returns
     *
     * @param iconFile the UIElement file location
     * @return the loaded-in image as a BufferedImage
     */
    public static BufferedImage loadImage(String iconFile) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(Game.class.getResourceAsStream(iconFile));
        } catch (IOException ex) {
            System.err.println("\n [ERR] Failed to load image (" + iconFile + "): " + ex);
            System.exit(-1);
        }

        return img;
    }

    private static String trim(String string) {
        String parts[] = string.split("/");

        return parts[parts.length - 1];
    }

    /**
     * Draw the UIElement to the screen at xStart,yStart
     *
     * @param screen the screen which the UIElement is drawn to
     * @param xStart the starting x coordinate of the UIElement
     * @param yStart the starting y coordinate of the UIElement
     */
    public void render(Screen screen, int xStart, int yStart) {
        int pix;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                try {
                    pix = pixels[x + y * width];
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
                if (pix == TRANSPARENT_COLOR) {
                    continue;
                } else if (pix == THEME_REPLACEMENT_COLOR) {
                    pix = Common.themeForegroundColor;
                }

                int xValue = xStart + x;
                int yValue = yStart + y;

                if (xValue >= 0 && xValue < screen.getScreenWidth()
                    && yValue >= 0 && yValue < screen.getScreenHeight()) {
                    screen.setPixel(pix, xStart + x, yStart + y);
                }
            }
        }
    }

    /**
     * Draw the UIElement to the screen at xStart,yStart
     *
     * @param screen the screen which the UIElement is drawn to
     * @param xStart the starting x coordinate of the UIElement
     * @param yStart the starting y coordinate of the UIElement
     */
    public void renderGrayscale(Screen screen, int xStart, int yStart) {
        int pix;
        int r, g, b, mask = 0xFF;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pix = pixels[x + y * width];
                r = (pix >> 16) & mask;
                g = (pix >> 8) & mask;
                b = pix & mask;

                if (pix == TRANSPARENT_COLOR) {
                    continue;
                }

                if (r > g && r > b) {
                    g = b = r;
                } else if (g > r && g > b) {
                    r = b = g;
                } else if (b > r && b > g) {
                    r = g = b;
                }

                pix = (r << 16) + (g << 8) + b;

                screen.setPixel(pix, xStart + x, yStart + y);
            }
        }
    }

    /**
     * Draw the UIElement to the screen at xStart,yStart
     *
     * @param screen the screen which the UIElement is drawn to
     * @param xStart the starting x coordinate of the UIElement
     * @param yStart the starting y coordinate of the UIElement
     */
    public void renderHighlighted(Screen screen, int xStart, int yStart) {
        int pix;
        int r, g, b, mask = 0xFF;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pix = pixels[x + y * width];

                if (pix == TRANSPARENT_COLOR) {
                    continue;
                } else if (pix == THEME_REPLACEMENT_COLOR) {
                    pix = Common.themeForegroundColor;
                }

                r = (pix >> 16) & mask;
                g = (pix >> 8) & mask;
                b = pix & mask;

                r = (int) (r * 1.35);
                g = (int) (g * 1.25);
                b = (int) (b * 1.25);

                r = r > 255 ? 255 : r;
                g = g > 255 ? 255 : g;
                b = b > 255 ? 255 : b;

                pix = (r << 16) + (g << 8) + b;

                screen.setPixel(pix, xStart + x, yStart + y);
            }
        }
    }

    public void renderScaled(Screen scr, int xStart, int yStart, int scale) {
        int pix;
        int mask = 0xff;
        int r, g, b;
        int raster[][] = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pix = pixels[x + y * width];

//                if (pix == TRANSPARENT_COLOR) {
//                    continue;
//                }

                if (pix == THEME_REPLACEMENT_COLOR) {
                    pix = Common.themeForegroundColor;
                }

                raster[x][y] = pix;
            }
        }

        scr.scale(xStart, yStart, raster, scale);
    }
}
