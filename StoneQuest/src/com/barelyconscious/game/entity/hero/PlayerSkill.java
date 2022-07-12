package com.barelyconscious.game.entity.hero;

public class PlayerSkill {

    /**
     * Skills that a Hero can do.
     */
    public enum HeroSkillName {
        MEDICINE,
        SURVIVAL,
        ANIMAL_HANDLING,
        COOKING;
    }

    /**
     * Skills used by the faction's citizens. These are skills directly
     * used by your citizens and therefore impacts the overall capability
     * of your faction's ability to operate.
     */
    public enum CitizenSkillName {
        BOTANY,
        GEOLOGY,
        HUNTING,
        FORESTRY,
        FARMING,
    }

    /**
     * Skills used by the faction itself.
     */
    public enum FactionSkillName {

        SYNTHESIS,
        TAILORING,
        SMITHING,
        COOKING,
        CHEMISTRY,

    }
}
