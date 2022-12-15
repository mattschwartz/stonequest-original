package com.barelyconscious.worlds.systems.information;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Describes how an entity or idea aligns with different beliefs.
 */
@Data
public class BeliefAlignment {

    /**
     * Describes how the agent feels about different political subjects with varying levels of support.
     * eg, WAR=-1 would mean the agent feels strongly opposed to war.
     * <p>
     * key=ID of the governmental policy
     * value=range of [-1, 1] indicating strong opposition to strong support
     */
    private Map<Integer, Float> supportByPolicy = new HashMap<>();
    /**
     * Describes how the agent feels about current events happening in the world, including how governments are responding
     * to them.
     * <p>
     * key=ID of the current event
     * value=range of [-1,1] indicating approval rating, with -1 meaning strong disapproval and 1 meaning strong approval
     */
    private Map<Integer, Float> supportByCurrentEvent = new HashMap<>();
    /**
     * Describes how important various political values are to this agent.
     * <p>
     * key=ID of the political value
     * value=measure of importance between [0,1] where 0 means not important at all and 1 means highly important
     */
    private Map<Integer, Float> importanceByPoliticalValue = new HashMap<>();

    /**
     * Political values are human values applied to politics.
     */
    @AllArgsConstructor
    enum PoliticalValues {
        /**
         * Agents who have strong importance to family are more
         * favorable of policy that encourages individualist protection and armament.
         */
        FAMILY(0),
        EQUALITY(1),
        MORALITY(2),
        /**
         * Agents who value ethics highly are
         * more favorable of policy that protects human rights for all, including criminals.
         */
        ETHICS(3),
        ;

        public final int politicalValueId;
    }

    /**
     * The topic being discussed
     */
    @AllArgsConstructor
    enum GovernmentalPolicy {
        WAR(0),
        ECONOMY(1),
        RELIGION(2);

        public final int policyId;
    }

    /**
     * Topics about ongoing events in the world. Eg, wildfires spreading to the west that is not addressed by the govt
     * could generate disapproval in the populace who believes it is the govt's responsibility.
     */
    @AllArgsConstructor
    enum CurrentEventSubject {

        CATASTROPHIC_EVENT(1),
        ;

        /**
         * A unique identifier for the current event.
         */
        public final int currentEventId;
    }
}
