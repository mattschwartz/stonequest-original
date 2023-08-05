package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.components.EntityLevelComponent;
import com.barelyconscious.worlds.gamedata.EntityStatsCalculator;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.TraitName;
import com.barelyconscious.worlds.game.hero.HeroClassType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class EntityFactory {

    public static EntityActorBuilder anEntity() {
        return new EntityActorBuilder();
    }

    public static class EntityActorBuilder {

        protected String name;
        protected Vector transform;
        protected int entityLevel;
        protected float currentExperience;
        protected int difficultyClass;
        protected Map<TraitName, Pair<Float, Float>> traits = new HashMap<>();
        protected Map<StatName, Pair<Float, Float>> stats = new HashMap<>();

        private final EntityStatsCalculator statsCalculator = new EntityStatsCalculator();

        public EntityActorBuilder called(String name) {
            this.name = name;
            return this;
        }

        public EntityActorBuilder locatedAt(Vector transform) {
            this.transform = transform;
            return this;
        }

        public EntityActorBuilder withCreatureLevel(int entityLevel, float currentExperience, int difficultyClass) {
            this.entityLevel = entityLevel;
            this.currentExperience = currentExperience;
            this.difficultyClass = difficultyClass;

            postBuildDelegate.bindDelegate((actor) -> {
                return null;
            });

            return this;
        }

        public EntityActorBuilder withTrait(TraitName traitName, float value) {
            traits.put(traitName, Pair.of(value, value));

            return this;
        }

        public EntityActorBuilder withHeroClass(HeroClassType heroClassType) {
            return withTrait(TraitName.DEXTERITY, heroClassType.getStartingDexterity())
                .withTrait(TraitName.STRENGTH, heroClassType.getStartingStrength())
                .withTrait(TraitName.CONSTITUTION, heroClassType.getStartingConstitution())
                .withTrait(TraitName.INTELLIGENCE, heroClassType.getStartingIntelligence())
                .withTrait(TraitName.FAITH, heroClassType.getStartingFaith());
        }

        public EntityActorBuilder withStat(StatName statName, float value) {
            return withStat(statName, value, value);
        }

        public EntityActorBuilder withStat(StatName statName, float currentValue, float maxValue) {
            stats.put(statName, Pair.of(currentValue, maxValue));

            return this;
        }

        public EntityActorBuilder withTrait(TraitName traitName, float currentValue, float maxValue) {
            traits.put(traitName, Pair.of(currentValue, maxValue));

            return this;
        }

        private Delegate<EntityActor> postBuildDelegate = new Delegate<>();

        protected void preBuild() {
            for (var traitName : traits.keySet()) {
                postBuildDelegate.bindDelegate((actor) -> {
                    actor.trait(traitName).set(traits.get(traitName).getLeft(), traits.get(traitName).getRight());
                    return null;
                });
            }

            var consTrait = traits.get(TraitName.CONSTITUTION);
            if (consTrait != null) {
                postBuildDelegate.bindDelegate((actor) -> {
                    var curHealth = statsCalculator.toHealth(consTrait.getLeft(), entityLevel, difficultyClass);
                    var maxHealth = statsCalculator.toHealth(consTrait.getRight(), entityLevel, difficultyClass);

                    actor.trait(TraitName.CONSTITUTION).set(consTrait.getLeft(), consTrait.getRight());
                    stats.put(StatName.HEALTH, Pair.of(curHealth, maxHealth));

                    return null;
                });
            }

            var intTrait = traits.get(TraitName.INTELLIGENCE);
            if (intTrait != null) {
                postBuildDelegate.bindDelegate((actor) -> {
                    var curPower = statsCalculator.toPower(intTrait.getLeft(), entityLevel, difficultyClass);
                    var maxPower = statsCalculator.toPower(intTrait.getRight(), entityLevel, difficultyClass);

                    actor.trait(TraitName.INTELLIGENCE).set(intTrait.getLeft(), intTrait.getRight());
                    stats.put(StatName.ENERGY, Pair.of(curPower, maxPower));
                    return null;
                });
            }

            for (var statName : StatName.values()) {
                postBuildDelegate.bindDelegate((actor) -> {
                    if (stats.containsKey(statName)) {
                        actor.stat(statName).set(stats.get(statName).getLeft(), stats.get(statName).getRight());
                    } else {
                        actor.stat(statName).set(0, 0);
                    }
                    return null;
                });
            }
        }

        protected EntityActor postBuild(EntityActor result) {
            postBuildDelegate.call(result);
            return result;
        }

        public EntityActor build() {
            preBuild();
            var result = new EntityActor(name, transform);
            result.addComponent(new EntityLevelComponent(result, entityLevel, currentExperience));

            return postBuild(result);
        }

        public Hero buildHero(Inventory inventory, HeroClassType heroClassType) {
            preBuild();
            var result = new Hero(name, transform, inventory, heroClassType);
            result.addComponent(new EntityLevelComponent(result, entityLevel, currentExperience));

            return (Hero) postBuild(result);
        }


        EntityActorBuilder() {
        }
    }
}
