package com.barelyconscious.worlds.game.abilitysystem;

public record BehaviorFeedback(
    ContinuationResult result,
    AbilityContext context,
    String errorMessage
) {
    public BehaviorFeedback(
        ContinuationResult result,
        AbilityContext context
    ) {
        this(result, context, null);
    }
}
