package com.barelyconscious.worlds.gamedata.abilities;

import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.components.*;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.abilitysystem.*;
import com.barelyconscious.worlds.game.abilitysystem.behaviors.ConsumeCombatResourceBehavior;
import com.barelyconscious.worlds.game.abilitysystem.behaviors.EntityHasStatBehavior;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import lombok.val;

public class BulletAbility extends Ability {

    private final double energyCost = 5;

    public BulletAbility() {
        super("Bullet time", .5);

        setBehaviorWorkflow(new BehaviorWorkflow(
            new EntityHasStatBehavior(StatName.ENERGY, energyCost),
            new Behavior() {
                @Override
                public BehaviorFeedback perform(AbilityContext context) {
                    if (!(context.getCaster() instanceof EntityActor usedBy)) {
                        return new BehaviorFeedback(ContinuationResult.STOP, context, "Caster is not an entity");
                    }

                    val aBullet = new Actor(usedBy.name + "#Bullet", usedBy.getTransform().plus(32, 0));
                    aBullet.addComponent(new MoveComponent(aBullet, 32f));
                    aBullet.addComponent(new BoxColliderComponent(aBullet, false, true, new Box(0, 32, 0, 32)));
                    aBullet.addComponent(new SpriteComponent(aBullet, Resources.getSprite(ResourceSprite.POTION)));
                    aBullet.getComponent(BoxColliderComponent.class)
                        .delegateOnEnter.bindDelegate((col) -> {
                            if (col.causedByActor != aBullet) {
                                return null;
                            }

                            if (col.hit instanceof EntityActor hit) {
                                final DynamicValueComponent health = hit.getHealthComponent();
                                if (health != null && health.isEnabled()) {
                                    health.adjustCurrentValueBy((UMath.RANDOM.nextFloat() * -8) - 6);
                                    aBullet.destroy();
                                }
                            }

                            return null;
                        });
                    aBullet.addComponent(new LifetimeComponent(aBullet, 200));
                    aBullet.addComponent(new Component(aBullet) {
                        @Override
                        public void update(EventArgs eventArgs) {
                            aBullet.getComponent(MoveComponent.class)
                                .addForce(usedBy.facing, 4500f);
                        }
                    });

                    context.getWorld().addActor(aBullet);

                    return new BehaviorFeedback(ContinuationResult.CONTINUE, context);
                }
            },
            new ConsumeCombatResourceBehavior(StatName.ENERGY, -energyCost)
        ));
    }
}
