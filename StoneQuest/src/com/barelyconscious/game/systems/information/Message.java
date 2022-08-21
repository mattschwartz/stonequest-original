package com.barelyconscious.game.systems.information;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message {

    /**
     * Uniquely identifies this message.
     */
    private final int id;
    /**
     * Describes how this message's contents align with different belief systems.
     */
    private final BeliefAlignment beliefAlignment;
}
