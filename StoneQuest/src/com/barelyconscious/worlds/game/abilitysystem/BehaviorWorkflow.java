package com.barelyconscious.worlds.game.abilitysystem;

public class BehaviorWorkflow {

    public record WorkflowExecutionResult(boolean succeeded, String message) {
        public static WorkflowExecutionResult success() {
            return new WorkflowExecutionResult(true, null);
        }

        public static WorkflowExecutionResult failure(String message) {
            return new WorkflowExecutionResult(false, message);
        }
    }

    private Behavior[] behaviors;

    public BehaviorWorkflow(Behavior... behaviors) {
        this.behaviors = behaviors;
    }

    public WorkflowExecutionResult run(AbilityContext context) {
        for (var behavior : behaviors) {
            var feedback = behavior.perform(context);
            if (feedback.result() == ContinuationResult.STOP) {
                return WorkflowExecutionResult.failure(feedback.errorMessage());
            }
            context = feedback.context();
        }

        return WorkflowExecutionResult.success();
    }
}
