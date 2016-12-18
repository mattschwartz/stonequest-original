/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Condition.java
 * Author:           Matt Schwartz
 * Date created:     09.04.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Conditions are temporary effects which affect an Entity
 *                   (including the Player) for a pre-determined amount of time
 *                   (measured in game ticks). 
 *                   Conditions:
 *                 - Curse
 *                   Curses affect an Entity's attributes negatively and can
 *                   impede movement or vision or some other physical ability.
 *                 - Poison
 *                   Poisons deal damage over time.
 *                 - Potion
 *                   Potions benefit an Entity's attributes positively and can
 *                   grant other physical ability benefits as well.
 *                 - Aura
 *                   Auras are conditions that do not expire unless an
 *                   external condition is not met. For example, some items can
 *                   boost an Entity's attributes only as long as they are kept
 *                   in the Entity's inventory.
 **************************************************************************** */
package com.barelyconscious.game.player.condition;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Condition {

    public static final int DETRIMENT_CURSE_TYPE = 0;
    public static final int DETRIMENT_TOXIN_TYPE = 1;
    public static final int BENEFIT_AURA_TYPE = 2;
    public static final int BENEFIT_POTION_TYPE = 3;
    private static final UIElement POTION_CONDITION_ICON = UIElement.createUIElement("/gfx/conditions/benefits/potions/defaultPotionIcon.png");
    private static final UIElement AURA_CONDITION_ICON = UIElement.createUIElement("/gfx/conditions/benefits/auras/defaultAuraIcon.png");
    private static final UIElement CURSE_CONDITION_ICON = UIElement.createUIElement("/gfx/conditions/detriments/curses/defaultCurseIcon.png");
    private static final UIElement TOXIN_CONDITION_ICON = UIElement.createUIElement("/gfx/conditions/detriments/toxins/defaultToxinIcon.png");
    protected int duration;
    protected int conditionType;
    protected String name;
    protected List<AttributeMod> affectedAttributes = null;
    protected Entity affectedEntity;
    protected final UIElement conditionIcon;

    /**
     * Conditions are temporary effects which affect an Entity (including the
     * Player) either positively or negatively. Creates a new Condition with the
     * following attributes:
     *
     * @param duration if greater than zero, the duration is how long (in game
     * ticks) the Condition lasts
     * @param conditionType the conditionType refers to which type of Condition
     * is being constructed, must be one of the following:
     * <ul>
     * <li>DETRIMENT_CURSE_TYPE</li>
     * <li>DETRIMENT_TOXIN_TYPE</li>
     * <li>BENEFIT_AURA_TYPE</li>
     * <li>BENEFIT_POTION_TYPE</li>
     * </ul>
     * @param name the name of the Condition - this value will be known to the
     * Player
     * @param icon when the Condition is rendered to the Screen, this is what
     * will be drawn
     * @param affectedAttributes if non null, refers to a list of AttributeMods
     * which will tell how the Entity's attributes will be affected upon
     * application of the Condition
     */
    public Condition(int duration, int conditionType, String name, Entity affectedEntity, UIElement icon, AttributeMod... affectedAttributes) {
        this.affectedEntity = affectedEntity;
        this.conditionIcon = icon;
        this.duration = duration;
        this.conditionType = conditionType;
        this.name = name;

        if (affectedAttributes != null) {
            this.affectedAttributes = new ArrayList<AttributeMod>();
            this.affectedAttributes.addAll(Arrays.asList(affectedAttributes));
        }
    }

    /**
     *
     * @return the remaining number of game ticks that the Condition lasts for
     * before being removed
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Applies the Condition to an Entity, modifying any of its attributes or
     * other abilities.
     */
    public void apply() {
        affectedEntity.affect(this);

        for (AttributeMod attributeMod : affectedAttributes) {
            affectedEntity.adjustAttribute(attributeMod.getAttributeId(), attributeMod.getAttributeModifier());
        }
    }

    /**
     * Removes the Condition from an Entity, restoring any modified values to
     * normal.
     */
    public void remove() {
        for (AttributeMod attributeMod : affectedAttributes) {
            affectedEntity.adjustAttribute(attributeMod.getAttributeId(), -attributeMod.getAttributeModifier());
        }

        affectedEntity.dissolveCondiiton(this);
    }

    /**
     *
     * @return the type of Condition as an integer
     */
    public int getConditionType() {
        return conditionType;
    }

    /**
     *
     * @return the name of the Condition as a String
     */
    public String getName() {
        return name;
    }

    /**
     * This method should be overridden by subclasses and altered to more
     * appropriately fit the Condition.
     *
     * @return the description associated with this Condition as a String
     */
    public String getDescription() {
        return "A temporary effect that affects abilities.";
    }

    /**
     *
     * @return the attributes affected by this Condition in an array
     */
    public AttributeMod[] getAffectedAttributes() {
        if (affectedAttributes == null) {
            return null;
        }

        return affectedAttributes.toArray(new AttributeMod[]{});
    }

    public void setAffectedEntity(Entity affectedEntity) {
        this.affectedEntity = affectedEntity;
    }

    /**
     * This method is called during a game tick and the Condition should perform
     * any necessary functions during that time.
     */
    public void tick() {
        duration--;

        if (duration <= 0) {
            remove();
        }
    }

    /**
     * Renders the Condition's icon to the screen for the Player.
     */
    public void render(int x, int y) {
        conditionIcon.render(x, y);
    }
}
