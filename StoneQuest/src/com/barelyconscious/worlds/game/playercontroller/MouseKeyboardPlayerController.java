package com.barelyconscious.worlds.game.playercontroller;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.components.*;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.entity.Hero;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.input.KeyInputHandler;
import com.barelyconscious.worlds.engine.input.MouseInputHandler;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.common.UMath;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;

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
public class MouseKeyboardPlayerController extends PlayerController {

    public final Delegate<Boolean> delegateOnQuitRequested = new Delegate<>();

    public static final Delegate<Object> delegateOnInterruptRequested = new Delegate<>();

    public MouseKeyboardPlayerController(
        @NonNull final Inventory inventory,
        final MouseInputHandler mouseHandler,
        final KeyInputHandler keyHandler
    ) {
        super(inventory);

        mouseHandler.onMouseClicked.bindDelegate(this::onMouseClicked);
        mouseHandler.onMouseMoved.bindDelegate(this::onMouseMoved);
        mouseHandler.onMouseReleased.bindDelegate(this::onMouseReleased);
        mouseHandler.onMouseExited.bindDelegate(this::onMouseExited);

        keyHandler.delegateOnKeyPressed.bindDelegate(this::onKeyPressed);
        keyHandler.delegateOnKeyReleased.bindDelegate(this::onKeyReleased);
    }

    private Void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            final Hero usedBy = GameInstance.instance().getHeroSelected();
            final Vector facing = usedBy.facing;

            JobActionComponent jobComponent = usedBy.getComponent(JobActionComponent.class);
            if (jobComponent == null) {
                return null;
            }
            try {
                jobComponent.queueAction(e -> {
                    final int wait2 = 75;
                    final int wait3 = 100;

                    new AwesomeBulletWeaponItem().use(usedBy, facing, e.getEventArgs());

                    e.yield(wait2, t -> {
                        new AwesomeBulletWeaponItem().use(usedBy, facing, e.getEventArgs());
                        return null;
                    });
                    e.yield(wait3, t -> {
                        new AwesomeBulletWeaponItem().use(usedBy, facing, e.getEventArgs());
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

    private Void onKeyPressed(KeyEvent keyEvent) {
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
            log.info("Requesting interrupt");
            delegateOnInterruptRequested.call(true);
        }
        if (keyEvent.getKeyChar() == KeyEvent.VK_Q) {
            System.out.println("Requesting stop");
            delegateOnQuitRequested.call(true);
        }
        if (keyEvent.getKeyChar() == KeyEvent.VK_1) {
            GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.LEFT);
        }
        if (keyEvent.getKeyChar() == KeyEvent.VK_2) {
            GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.MIDDLE);
        }
        if (keyEvent.getKeyChar() == KeyEvent.VK_3) {
            GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.RIGHT);
        }

        if (keyEvent.getKeyChar() == 'f' || keyEvent.getKeyChar() == 'F') {
            GameInstance.instance()
                .getHeroSelected()
                .getHealthComponent()
                .adjust(1.2f);
        }

        final MoveComponent move = GameInstance.instance()
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
            int curIndex = GameInstance.instance().getSelectedHeroId().index;
            int newIndex = (curIndex + 1) % 3;
            GameInstance.instance().setHeroSelectedSlot(
                GameInstance.PartySlot.fromSlotId(newIndex));
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
        mouseWorldPos = GameInstance.instance().getCamera().screenToWorldPos(mouseScreenPos);
        return null;
    }

    private Void onMouseClicked(final MouseEvent mouseEvent) {
        mouseClickedScreenPos = mouseScreenPos = new Vector(mouseEvent.getX(), mouseEvent.getY());
        mouseClickedWorldPos = mouseWorldPos = GameInstance.instance().getCamera().screenToWorldPos(mouseScreenPos);
        return null;
    }

    private static final class AwesomeBulletWeaponItem {

        public void use(
            final Actor usedBy,
            final Vector facing,
            final EventArgs eventArgs
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

                    if (col.hit instanceof EntityActor hit) {
                        final DynamicValueComponent health = hit.getHealthComponent();
                        if (health != null && health.isEnabled()) {
                            health.adjust(UMath.RANDOM.nextFloat() * -3);
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
                        .addForce(facing, 4500f);
                }
            });

            eventArgs.getWorldContext()
                .addActor(aBullet);
        }
    }
}
