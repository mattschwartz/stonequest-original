package com.barelyconscious.worlds.systems.information;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class Agent {

    @Builder
    public static class AgentConfiguration {

        /**
         * Higher influence means the agent is more likely to shift its alignment when seeing the same message
         * repeatedly.
         * <p>
         * value is 0+
         */
        private final float repeatExposureInfluence;

        /**
         * Agent is more or less susceptible to neighbors' beliefs. With higher influence, this agent is more likely
         * to shift its alignment based on what its neighbors align with.
         */
        private final float peerInfluence;

        /**
         * A scale of 0 to 1 which represents how likely an agent will accept the alignment of messages that it has
         * seen for the first time.
         */
        private final float gullibility;
    }

    private final AgentConfiguration brain;

    /**
     * Describes this agent's internal belief alignments.
     */
    private final BeliefAlignment beliefAlignment;
    /**
     * Counts how many times the agent has been exposed to a message.
     * key=message ID
     * value=count, 0 and not present mean never seen
     */
    private final Map<Integer, Integer> exposureCountByMessageId;

    @Getter
    @AllArgsConstructor
    public static class ProcessMessageRequest {
        private final Message message;
        private final Agent[] neighbors;
    }

    public void processMessage(final ProcessMessageRequest request) {
        // extract message attributes
        BeliefAlignment messageAlignment = request.message.getBeliefAlignment();

        // apply weight to msg attributes based on neighbors (their word adds strength)
        BeliefAlignment weightedAlignment = applyNeighborBeliefs(messageAlignment, request.neighbors);

        // test message id against exposure map (repeat exposure)
        weightedAlignment = applyRepeatExposure(request.message.getId(), weightedAlignment);

        // test weighted message attributes against this agent's internal belief model

        // Update internal belief model if applicable
        // Decide whether to send message and to whom (selective exposure)
    }

    private BeliefAlignment applyNeighborBeliefs(BeliefAlignment messageAlignment, Agent[] neighbors) {
        BeliefAlignment result = new BeliefAlignment();
        result.setImportanceByPoliticalValue(messageAlignment.getImportanceByPoliticalValue());

        // Considers the neighbors support for each policy within this message's contents
        // Takes the absolute difference between the avg neighbor support and the message policy support
        // then applies this agent's peer influence to the difference to describe how this agent sees the contents
        // through the lens of its peers
        for (final var entry : messageAlignment.getSupportByPolicy().entrySet()) {
            final int policyId = entry.getKey();
            final float support = entry.getValue();

            float peerSupportSum = 0;
            for (final Agent n : neighbors) {
                Float nSupport = n.getBeliefAlignment().getSupportByPolicy().get(policyId);
                if (nSupport != null) {
                    peerSupportSum += nSupport;
                }
            }
            float peerSupportAverage = (peerSupportSum / neighbors.length);

            result.getSupportByPolicy()
                .put(policyId, Math.abs(peerSupportAverage - support) * brain.peerInfluence);
        }

        for (final var entry : messageAlignment.getSupportByCurrentEvent().entrySet()) {
            final int policyId = entry.getKey();
            final float support = entry.getValue();

            float peerSupportSum = 0;
            for (final Agent n : neighbors) {
                Float nSupport = n.getBeliefAlignment().getSupportByCurrentEvent().get(policyId);
                if (nSupport != null) {
                    peerSupportSum += nSupport;
                }
            }
            float peerSupportAverage = (peerSupportSum / neighbors.length);

            result.getSupportByCurrentEvent()
                .put(policyId, Math.abs(peerSupportAverage - support) * brain.peerInfluence);
        }

        return result;
    }

    private BeliefAlignment applyRepeatExposure(final int messageId, final BeliefAlignment messageAlignment) {
        Integer exposureCount = exposureCountByMessageId.get(messageId);
        if (exposureCount == null || exposureCount == 0) {
            return messageAlignment;
        }
        final BeliefAlignment result = new BeliefAlignment();
        result.setImportanceByPoliticalValue(messageAlignment.getImportanceByPoliticalValue());

        for (final var entry : messageAlignment.getSupportByPolicy().entrySet()) {
            final int policyId = entry.getKey();
            final float support = entry.getValue();
            final float influenceFromExposure = exposureCount * brain.repeatExposureInfluence;

            result.getSupportByPolicy()
                .put(policyId, support + (support * influenceFromExposure));
        }

        for (final var entry : messageAlignment.getSupportByCurrentEvent().entrySet()) {
            final int policyId = entry.getKey();
            final float support = entry.getValue();
            final float influenceFromExposure = exposureCount * brain.repeatExposureInfluence;

            result.getSupportByCurrentEvent()
                .put(policyId, support + (support * influenceFromExposure));
        }

        return result;
    }
}
