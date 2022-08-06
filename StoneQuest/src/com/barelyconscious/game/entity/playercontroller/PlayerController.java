package com.barelyconscious.game.entity.playercontroller;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.Hero;
import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.JobActionComponent;
import com.barelyconscious.game.entity.components.LifetimeComponent;
import com.barelyconscious.game.entity.components.MoveComponent;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.input.KeyInputHandler;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.resources.ResourceSprite;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import com.barelyconscious.util.UMath;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import javax.annotation.Nullable;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * todo(p0) When selected and while player holding the alt key,
 *  directional arrows with party members will show up around
 *  the selected hero to give player better sense of spatial
 *  relations between the party.
 *  \
 *  other heroes will be slightly highlighted as well.
 * <p>
 * Facilitates interactions between the player and the game, such as handling keyboard and mouse
 * input.
 */
@Log4j2
public class PlayerController {

    public final Delegate<Boolean> delegateOnQuitRequested = new Delegate<>();

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

    @Getter
    private final Inventory inventory;

    public PlayerController(
        @NonNull final Inventory inventory,
        final MouseInputHandler mouseHandler,
        final KeyInputHandler keyHandler
    ) {
        this.inventory = inventory;

        mouseHandler.onMouseClicked.bindDelegate(this::onMouseClicked);
        mouseHandler.onMouseMoved.bindDelegate(this::onMouseMoved);
        mouseHandler.onMouseReleased.bindDelegate(this::onMouseReleased);
        mouseHandler.onMouseExited.bindDelegate(this::onMouseExited);

        keyHandler.onKeyPressed.bindDelegate(this::onKeyTyped);
    }

    private Void onKeyTyped(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_F1) {
            if (EventArgs.IS_DEBUG) {
                if (EventArgs.IS_VERBOSE) {
                    EventArgs.IS_DEBUG = false;
                    EventArgs.IS_VERBOSE = false;
                } else {
                    EventArgs.IS_VERBOSE = true;
                }
            } else {
                EventArgs.IS_DEBUG = true;
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("Requesting stop");
            delegateOnQuitRequested.call(true);
        }
        if (keyEvent.getKeyChar() == KeyEvent.VK_1) {
            GameInstance.getInstance().setHeroSelectedSlot(GameInstance.PartySlot.LEFT);
        }
        if (keyEvent.getKeyChar() == KeyEvent.VK_2) {
            GameInstance.getInstance().setHeroSelectedSlot(GameInstance.PartySlot.MIDDLE);
        }
        if (keyEvent.getKeyChar() == KeyEvent.VK_3) {
            GameInstance.getInstance().setHeroSelectedSlot(GameInstance.PartySlot.RIGHT);
        }

        if (keyEvent.getKeyChar() == 'f' || keyEvent.getKeyChar() == 'F') {
            GameInstance.getInstance()
                .getHeroSelected()
                .getHealthComponent()
                .adjust(1.2f);
        }

        final MoveComponent move = GameInstance.getInstance()
            .getHeroSelected()
            .getComponent(MoveComponent.class);

        if (move != null) {
            if (keyEvent.getKeyChar() == 'd') {
                move.addForce(Vector.RIGHT, 100f);
            }
            if (keyEvent.getKeyChar() == 'a') {
                move.addForce(Vector.LEFT, 100f);
            }
            if (keyEvent.getKeyChar() == 'w') {
                move.addForce(Vector.UP, 100f);
            }
            if (keyEvent.getKeyChar() == 's') {
                move.addForce(Vector.DOWN, 100f);
            }
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_TAB) {
            int curIndex = GameInstance.getInstance().getSelectedHeroId().index;
            int newIndex = (curIndex + 1) % 3;
            GameInstance.getInstance().setHeroSelectedSlot(
                GameInstance.PartySlot.fromSlotId(newIndex));
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            final Hero usedBy = GameInstance.getInstance().getHeroSelected();
            final Vector facing = usedBy.facing;

            JobActionComponent jobComponent = usedBy.getComponent(JobActionComponent.class);
            if (jobComponent == null) {
                return null;
            }
            try {
                jobComponent.queueAction(e -> {
                    int waitTime = UMath.RANDOM.nextInt(60) + 40;
                    e.yield(waitTime, t -> {
                        new AwesomeBulletWeaponItem().use(usedBy, facing);
                        return null;
                    });
                    return null;
                });
                jobComponent.queueAction(e -> {
                    int waitTime = UMath.RANDOM.nextInt(60) + 40;
                    e.yield(waitTime, t -> {
                        new AwesomeBulletWeaponItem().use(usedBy, facing);
                        return null;
                    });
                    return null;
                });
                jobComponent.queueAction(e -> {
                    int waitTime = UMath.RANDOM.nextInt(60) + 40;
                    e.yield(waitTime, t -> {
                        new AwesomeBulletWeaponItem().use(usedBy, facing);
                        return null;
                    });
                    return null;
                });
            } catch (JobActionComponent.TooManyActionsException e) {
                log.error("You can't handle that many things at once.");
            }
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
        return null;
    }

    private static final class AwesomeBulletWeaponItem {

        public void use(
            final Actor usedBy,
            final Vector facing
        ) {
            val aBullet = new Actor(usedBy + "#Bullet", usedBy.transform.plus(32, 0));
            aBullet.addComponent(new MoveComponent(aBullet, 32f));
            aBullet.addComponent(new BoxColliderComponent(aBullet, false, true, new Box(0, 32, 0, 32)));
            aBullet.addComponent(new SpriteComponent(aBullet, Resources.getSprite(ResourceSprite.POTION)));
            aBullet.getComponent(BoxColliderComponent.class)
                .delegateOnEnter.bindDelegate((col) -> {
                    if (col.causedByActor != aBullet) {
                        return null;
                    }

                    final Actor hit = col.hit;

                    final HealthComponent health = hit.getComponent(HealthComponent.class);
                    if (health != null && health.isEnabled()) {
                        health.adjust(UMath.RANDOM.nextFloat() * -3);
                        aBullet.destroy();
                    }

                    return null;
                });
            aBullet.addComponent(new LifetimeComponent(aBullet, 200));

            aBullet.addComponent(new Component(aBullet) {
                @Override
                public void update(EventArgs eventArgs) {
                    aBullet.getComponent(MoveComponent.class)
                        .addForce(facing, 4500f);
                }
            });

            GameInstance.getInstance().getWorld()
                .spawnActor(aBullet);
        }
    }
}
