package com.barelyconscious.game.entity.playercontroller;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.MoveComponent;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.input.KeyInputHandler;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.resources.ResourceSprite;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;
import lombok.val;

import javax.annotation.Nullable;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Facilitates interactions between the player and the game, such as handling keyboard and mouse
 * input.
 */
public class PlayerController {

    @Getter
    @Nullable
    private Vector mouseScreenPos;
    @Getter
    @Nullable
    private Vector mouseWorldPos;

    @Getter
    @Nullable
    private Vector mouseClickedScreenPos;
    @Getter
    @Nullable
    private Vector mouseClickedWorldPos;

    public PlayerController(final MouseInputHandler mouseHandler, final KeyInputHandler keyHandler) {
        mouseHandler.onMouseClicked.bindDelegate(this::onMouseClicked);
        mouseHandler.onMouseMoved.bindDelegate(this::onMouseMoved);
        mouseHandler.onMouseReleased.bindDelegate(this::onMouseReleased);
        mouseHandler.onMouseExited.bindDelegate(this::onMouseExited);

        keyHandler.onKeyTyped.bindDelegate(this::onKeyTyped);
    }

    private Void onKeyTyped(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == KeyEvent.VK_1) {
            GameInstance.getInstance().setHeroSelectedSlot(GameInstance.PartySlot.LEFT);
        }
        if (keyEvent.getKeyChar() == KeyEvent.VK_2) {
            GameInstance.getInstance().setHeroSelectedSlot(GameInstance.PartySlot.MIDDLE);
        }
        if (keyEvent.getKeyChar() == KeyEvent.VK_3) {
            GameInstance.getInstance().setHeroSelectedSlot(GameInstance.PartySlot.RIGHT);
        }
        return null;
    }

    private Void onMouseExited(MouseEvent mouseEvent) {
        mouseScreenPos = null;
        mouseWorldPos = null;
        mouseClickedScreenPos = null;
        mouseClickedWorldPos = null;

        return null;
    }

    private Void onMouseReleased(MouseEvent mouseEvent) {
        mouseClickedScreenPos = mouseClickedWorldPos = null;
        return null;
    }

    private Void onMouseMoved(MouseEvent mouseEvent) {
        mouseScreenPos = new Vector(mouseEvent.getX(), mouseEvent.getY());
        mouseWorldPos = GameInstance.getInstance().getCamera().screenToWorldPos(mouseScreenPos);
        return null;
    }

    private Void onMouseClicked(final MouseEvent mouseEvent) {
        mouseClickedScreenPos = mouseScreenPos = new Vector(mouseEvent.getX(), mouseEvent.getY());
        mouseClickedWorldPos = mouseWorldPos = GameInstance.getInstance().getCamera().screenToWorldPos(mouseScreenPos);

        final Actor usedBy = GameInstance.getInstance().getHeroSelected();
        final Vector facing = usedBy.facing;

        new AwesomeBulletWeaponItem().use(usedBy, facing);

        return null;
    }

    private static final class AwesomeBulletWeaponItem {

        public void use(
            final Actor usedBy,
            final Vector facing
        ) {
            val aBullet = new Actor(usedBy + "#Bullet", usedBy.transform.plus(32, 0));
            aBullet.addComponent(new MoveComponent(aBullet, 2f));
            aBullet.addComponent(new BoxColliderComponent(aBullet, false, true, new Box(0, 32, 0, 32)));
            aBullet.addComponent(new SpriteComponent(aBullet, Resources.getSprite(ResourceSprite.POTION)));
            aBullet.getComponent(BoxColliderComponent.class)
                .delegateOnOverlap.bindDelegate((col) -> {
                    if (col.causedByActor != aBullet) {
                        return null;
                    }

                    final Actor hit = col.hit;

                    final HealthComponent health = hit.getComponent(HealthComponent.class);
                    if (health != null && health.isEnabled()) {
                        health.adjust(-0.4f);
                    }

                    final MoveComponent move = hit.getComponent(MoveComponent.class);
                    if (move != null && move.isEnabled()) {
                        move.addForce(facing, 64);
                    }

//                    aBullet.destroy();
                    return null;
                });

            aBullet.getComponent(MoveComponent.class)
                .addForce(facing, 45f);

            GameInstance.getInstance().getWorld()
                .spawnActor(aBullet);
        }
    }
}
