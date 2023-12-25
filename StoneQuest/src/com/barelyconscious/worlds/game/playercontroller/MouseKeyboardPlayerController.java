package com.barelyconscious.worlds.game.playercontroller;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.entity.components.*;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.input.KeyInputHandler;
import com.barelyconscious.worlds.engine.input.MouseInputHandler;
import com.barelyconscious.worlds.game.abilitysystem.Ability;
import com.barelyconscious.worlds.game.abilitysystem.AbilityContext;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.systems.PartySystem;
import lombok.extern.log4j.Log4j2;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

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
        final MouseInputHandler mouseHandler,
        final KeyInputHandler keyHandler
    ) {
        mouseHandler.onMouseClicked.bindDelegate(this::onMouseClicked);
        mouseHandler.onMouseMoved.bindDelegate(this::onMouseMoved);
        mouseHandler.onMouseReleased.bindDelegate(this::onMouseReleased);
        mouseHandler.onMouseExited.bindDelegate(this::onMouseExited);

        keyHandler.delegateOnKeyPressed.bindDelegate(this::onKeyPressed);
        keyHandler.delegateOnKeyReleased.bindDelegate(this::onKeyReleased);

        Ability.delegateOnCooldownChanged.bindDelegate(e -> {
            log.info("{} {}", e.ability.getName(), e.onCooldown ? "on cooldown" : "off cooldown");
            return null;
        });

        Ability.delegateOnAbilityFailed.bindDelegate(e -> {
            log.error("{} failed: {}", e.ability.getName(), e.message);
            return null;
        });
    }

    private Void onKeyReleased(KeyEvent keyEvent) {
        return null;
    }

    /**
     * todo - convert to use configurable key bindings
     */
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
        if (keyEvent.getKeyCode() == KeyEvent.VK_F1) {
            GameInstance.instance().getSystem(PartySystem.class)
                .setHeroSelectedSlot(PartySystem.PartySlot.LEFT);
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_F2) {
            GameInstance.instance().getSystem(PartySystem.class)
                .setHeroSelectedSlot(PartySystem.PartySlot.MIDDLE);
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_F3) {
            GameInstance.instance().getSystem(PartySystem.class)
                .setHeroSelectedSlot(PartySystem.PartySlot.RIGHT);
        }

        handleAbilityKeybinding(keyEvent);

        final MoveComponent move = GameInstance.instance()
            .getSystem(PartySystem.class)
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
            int curIndex = GameInstance.instance()
                .getGameState()
                .getPartyState()
                .getSelectedHeroId().index;
            int newIndex = (curIndex + 1) % 3;
            GameInstance.instance().getSystem(PartySystem.class)
                .setHeroSelectedSlot(PartySystem.PartySlot.fromSlotId(newIndex));
        }

        return null;
    }

    /**
     * When player presses a keybinding, this method determines which hero's ability
     * is performed.
     *
     * @param keyEvent
     */
    private void handleAbilityKeybinding(KeyEvent keyEvent) {
        int abilityIndex = -1;

        if (keyEvent.getKeyCode() == KeyEvent.VK_1) {
            abilityIndex = 0;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_2) {
            abilityIndex = 1;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_3) {
            abilityIndex = 2;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_4) {
            abilityIndex = 3;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_5) {
            abilityIndex = 4;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_6) {
            abilityIndex = 5;
        }

        if (abilityIndex >= 0) {
            List<AbilityComponent> abilityComponents = GameInstance.instance()
                .getSystem(PartySystem.class)
                .getHeroSelected()
                .getComponentsOfType(AbilityComponent.class);

            if (abilityComponents != null && abilityIndex < abilityComponents.size()) {
                AbilityComponent firstAbility = abilityComponents.get(abilityIndex);
                if (firstAbility != null) {
                    firstAbility.getAbility().enact(AbilityContext.builder()
                        .world(GameInstance.instance().getWorld())
                        .caster(GameInstance.instance()
                            .getSystem(PartySystem.class)
                            .getHeroSelected())
                        .build());
                }
            }
        }
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
}
