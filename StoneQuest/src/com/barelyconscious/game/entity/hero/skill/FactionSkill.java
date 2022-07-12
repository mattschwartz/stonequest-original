package com.barelyconscious.game.entity.hero.skill;

public interface FactionSkill extends Skill {

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
