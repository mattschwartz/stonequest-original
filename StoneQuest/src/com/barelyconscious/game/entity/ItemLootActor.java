package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.*;
import com.barelyconscious.game.entity.graphics.*;
import com.barelyconscious.game.entity.item.*;
import com.barelyconscious.game.entity.resources.*;
import com.barelyconscious.game.physics.*;
import com.barelyconscious.game.shape.*;
import lombok.*;
import lombok.extern.log4j.*;

import java.awt.*;
import java.awt.event.*;

@Log4j2
public class ItemLootActor extends Actor {

    private static final int DEFAULT_WIDTH = 30;
    private static final int DEFAULT_HEIGHT = 30;
    @Setter
    private Item item;

    public ItemLootActor(final Vector transform) {
        this(transform, null);
    }

    public ItemLootActor(final Vector transform, final Item item) {
        super(transform);
        this.item = item;

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
        addComponent(new KeyListenerComponent(this, 'e', this::onItemPickup));
    }

    private Void onItemPickup(KeyEvent keyEvent) {
        if (numHeroesOver > 0 && item != null) {
            final Inventory inventory = GameInstance.getInstance().getPlayerController().getInventory();
            if (inventory.addItem(item)) {
                System.out.println("Picked up: " + item.getName());
                item = null;
                destroy();
            } else {
                System.out.println("Could not pick up item, inventory was full.");
            }
        }
        return null;
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
            private boolean isErrorLogged = false;

            @Override
            public void render(EventArgs eventArgs, RenderContext renderContext) {
                if (item == null && !isErrorLogged) {
                    log.error("ItemLootActor#{} has null item!", getParent().id);
                    isErrorLogged = true;
                    getParent().destroy();
                    return;
                }

                final FontContext fc = renderContext.getFontContext();

                if (numHeroesOver > 0 && item != null) {
                    final String msg = String.format("Pick up '%s'", item.getName());
                    fc.renderString(msg, Color.yellow, renderContext.camera.getScreenWidth() / 2 - 50, 30,
                        RenderLayer.GUI);
                }
            }
        });
    }
}
