package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.MoveComponent;
import com.barelyconscious.game.physics.CollisionData;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.val;

public final class GameInstance {

    public void _testSpawnActors() {
    }

    private Actor _testCreateBulletActor() {
        val aBullet = new Actor(new Vector(0, 0));

        val bulletCollider = new BoxColliderComponent(
            aBullet,
            false, true,
            new Box(0, 10, 0, 10));

        aBullet.addComponent(bulletCollider);

        final float bulletDamage = 14f;

        // damage the other actor and destroy the bullet object
        bulletCollider.delegateOnHit.bindDelegate((data) -> {
            val actorHit = data.hit;
            val healthComponent = actorHit.getComponent(HealthComponent.class);
            if (healthComponent != null) {
                healthComponent.adjustHealth(-bulletDamage);
            }

            // bullet actor has done its job. time to go bye-bye
            data.causedByActor.destroy();

            return null;
        });

        val moveComponent = new MoveComponent(aBullet, 144f);
        aBullet.addComponent(moveComponent);

        // spawn it with some initial velocity
        val bulletDirection = Vector.UP;
        moveComponent.addForce(bulletDirection, 144f);

        return aBullet;
    }

    public int getX() {
        return -1;
    }

}
