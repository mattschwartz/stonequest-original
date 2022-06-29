package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.*;
import com.barelyconscious.game.entity.graphics.*;
import com.barelyconscious.game.entity.resources.*;
import com.barelyconscious.game.physics.*;
import com.barelyconscious.game.shape.*;

import java.awt.*;

public class ItemLootActor extends Actor {

    private static final int DEFAULT_WIDTH = 30;
    private static final int DEFAULT_HEIGHT = 30;

    public ItemLootActor(final Vector transform) {
        super(transform);

        Sprite sprite = Resources.getSprite(ResourceSprite.ITEM_LOOT_ACTOR_SPRITE);

        addComponent(new SpriteComponent(this,
            new Sprite(sprite.getTexture(), DEFAULT_WIDTH, DEFAULT_HEIGHT),
            RenderLayer.LOOT));

        final Box bounds = new Box(0, DEFAULT_WIDTH, 0, DEFAULT_HEIGHT);
        final BoxColliderComponent collider = new BoxColliderComponent(
            this, false, true, bounds);

        collider.delegateOnEnter.bindDelegate(this::onEnter);
        collider.delegateOnLeave.bindDelegate(this::onLeave);

        addComponent(collider);
        attachPromptComponent();
    }

    private final Object lock = new Object();
    private Integer numHeroesOver = 0;

    private Void onLeave(final Actor actor) {
        if (actor instanceof Hero) {
            synchronized (lock) {
                --numHeroesOver;
            }
        }

        return null;
    }

    private Void onEnter(final CollisionData collisionData) {
        if (collisionData.causedByActor instanceof Hero) {
            synchronized (lock) {
                ++numHeroesOver;
            }
        }

        return null;
    }

    private void attachPromptComponent() {
        addComponent(new Component(this) {
            @Override
            public void render(EventArgs eventArgs, RenderContext renderContext) {
                final FontContext fc = renderContext.getFontContext();

                if (numHeroesOver > 0) {
                    fc.renderString("Pick up Item", Color.yellow, renderContext.camera.getScreenWidth() / 2 - 50, 30,
                        RenderLayer.GUI);
                }
            }
        });
    }
}
