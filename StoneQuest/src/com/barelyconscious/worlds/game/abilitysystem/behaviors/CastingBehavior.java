package com.barelyconscious.worlds.game.abilitysystem.behaviors;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.game.abilitysystem.AbilityContext;
import com.barelyconscious.worlds.game.abilitysystem.Behavior;
import com.barelyconscious.worlds.game.abilitysystem.BehaviorFeedback;
import com.barelyconscious.worlds.game.abilitysystem.ContinuationResult;

import static com.barelyconscious.worlds.game.playercontroller.MouseKeyboardPlayerController.delegateOnInterruptRequested;

public class CastingBehavior implements Behavior {

    public final Delegate<Float> delegateOnCastingProgressUpdated = new Delegate<>();

    /**
     * todo Something has to tell someone whether a Hero is currently casting, and what ability that
     *  is. This is so that the UI can display the casting progress bar.
     *
     * @return
     */
    public boolean isCasting() {
        return false;
    }

    @Override
    public BehaviorFeedback perform(AbilityContext context) {

        float castingTime = (Float) context.get("castingTime");
        if (castingTime <= 0) {
            return new BehaviorFeedback(ContinuationResult.CONTINUE, null);
        }

        delegateOnInterruptRequested.bindDelegate(this::interruptAbility);

        // do the wait thing

        delegateOnInterruptRequested.freeDelegate(this::interruptAbility);

        return new BehaviorFeedback(ContinuationResult.CONTINUE, null);
    }

    /**
     * Updates listeners when the casting progress is updated
     */
    private void reportCastingProgress() {
    }

    private Void interruptAbility(Object o) {
        return null;
    }
}
