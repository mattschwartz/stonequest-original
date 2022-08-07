package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.MouseListenerComponent;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.engine.EventArgs;
import com.barelyconscious.game.entity.graphics.FontContext;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.item.Item;
import com.barelyconscious.game.entity.resources.ResourceSprite;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.physics.CollisionData;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.awt.Color;
import java.awt.event.MouseEvent;

// todo(p1): interacting with items should be handled by the player controller instead, something like casting out
//  from the hero to get all Items in the vicinity
@Log4j2
public class ItemLootActor extends Actor {

    private static final int DEFAULT_WIDTH = 30;
    private static final int DEFAULT_HEIGHT = 30;

    private final MouseListenerComponent mouseListenerComponent;

    @Setter
    private Item item;

    public ItemLootActor(final Vector transform, final Item item) {
        super(transform);
        this.item = item;

        Sprite sprite = Resources.getSprite(ResourceSprite.ITEM_LOOT_ACTOR_SPRITE);

        addComponent(new SpriteComponent(this,
            new Sprite(sprite.getTexture(), DEFAULT_WIDTH, DEFAULT_HEIGHT),
            RenderLayer.LOOT));

        final BoxColliderComponent collider = new BoxColliderComponent(
            this, false, true, new Box(-DEFAULT_WIDTH, DEFAULT_WIDTH*3, -DEFAULT_HEIGHT, DEFAULT_HEIGHT*3));

        collider.delegateOnEnter.bindDelegate(this::onEnter);
        collider.delegateOnLeave.bindDelegate(this::onLeave);

        addComponent(collider);
        attachPromptComponent();
        addComponent(mouseListenerComponent = new MouseListenerComponent(this, new Box(0, DEFAULT_WIDTH, 0, DEFAULT_HEIGHT)));

        mouseListenerComponent.delegateOnMouseClicked.bindDelegate(this::onItemPickup);

        addComponent(nextUpdateComponent);
    }

    private final Component nextUpdateComponent = new Component(this) {
    };

    private Void onItemPickup(MouseEvent keyEvent) {

        nextUpdateComponent.onNextUpdate(eventArgs -> {
            if (numHeroesOver > 0 && item != null) {
                final Inventory inventory = eventArgs.getPlayerController().getInventory();
                if (inventory.addItem(item)) {
                    System.out.println("Picked up: " + item.getName());
                    item = null;
                    destroy();
                } else {
                    System.out.println("Could not pick up item, inventory was full.");
                }
            }
            return null;
        });

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
                if (item == null) {
                    if (!isErrorLogged) {
                        log.error("ItemLootActor#{} has null item!", getParent().id);
                        isErrorLogged = true;
                    }
                    getParent().destroy();
                    return;
                }

                final FontContext fc = renderContext.getFontContext();

                if (!mouseListenerComponent.isMouseOver()) {
                    return;
                }

                final StringBuilder sb = new StringBuilder();
                sb.append("{COLOR=GREEN}").append(item.getName()).append("\n");

                if (numHeroesOver > 0) {
                    sb.append("{COLOR=GRAY}").append(item.getDescription()).append("\n\n");
                    sb.append("{COLOR=WHITE}Click to pick up.").append("\n");
                } else {
                    sb.append("{COLOR=RED}").append("Move closer\n");
                }

                final String msg = sb.toString();
                final int screenWidth = renderContext.camera.getScreenWidth();
                final int fontWidth = fc.getStringWidth(msg);
                final int fontHeight = fc.getStringHeight(msg);

                final Box stringBounds = new Box(0, screenWidth, 15, 15 + fontHeight);

                fc.setRenderLayer(RenderLayer.GUI);

                final Box boxBounds = new Box(
                    (screenWidth - fontWidth) / 2 - 4,
                    (screenWidth - fontWidth) / 2 + fontWidth + 8,
                    0,
                    10+fontHeight);

                renderContext.renderGuiRect(new Color(33, 33, 33), true,
                    boxBounds);
                fc.drawString(
                    msg,
                    FontContext.TextAlign.CENTER,
                    stringBounds);
            }
        });
    }
}
