package com.barelyconscious.worlds.game.systems.combat;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.EntityActor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreatTableTest {

    private ThreatTable classUnderTest;

    EntityActor combatant1;
    EntityActor combatant2;
    EntityActor combatant3;

    @BeforeEach
    void setUp() {
        classUnderTest = new ThreatTable();
        setUpTestSubjects();
    }

    /*
        threat table by defender

        defenders
          |
          v
        	            SewerRat1	SewerRat2	Bill	Bob	    Joe <- attackers
        SewerRat1	0	        0	        1	    3       2
        SewerRat2	0	        0	        4   	2	    3
        Bill	    0	        0   	    0  	    0       0
        Joe     	0	        0   	    0	    0       0
        Bob	        0	        0   	    0	    0       0
     */

    @Test
    void addThreat_combatant1AttacksCombatant2_shouldGenerateThreatAgainstCombatant2() {
        // combatant1 generates 1 threat against combatant2
        classUnderTest.addThreat(combatant1, combatant2, 1);

        // combatant2 feels 1 threat with combatant1
        assertEquals(1, classUnderTest.getThreat(combatant1, combatant2));
        // combatant1 feels no threat against combatant2 since combatant2 hasn't
        //  generated any threat against it
        assertEquals(0, classUnderTest.getThreat(combatant2, combatant1));
    }

    @Test
    void addThreat_actorNotInTable_shouldAddActorToThreatTable() {
        EntityActor newCombatant = new EntityActor("newCombatant", Vector.ZERO);
        classUnderTest.addThreat(newCombatant, combatant1, 1);

        assertEquals(1, classUnderTest.getThreat(newCombatant, combatant1));
        assertEquals(0, classUnderTest.getThreat(combatant1, newCombatant));
    }

    @Test
    void addThreat_neitherActorInTable_shouldAddBothActorsToTable() {
        EntityActor newCombatant1 = new EntityActor("newCombatant1", Vector.ZERO);
        EntityActor newCombatant2 = new EntityActor("newCombatant2", Vector.ZERO);
        classUnderTest.addThreat(newCombatant1, newCombatant2, 1);

        assertEquals(1, classUnderTest.getThreat(newCombatant1, newCombatant2));
        assertEquals(0, classUnderTest.getThreat(newCombatant2, newCombatant1));
    }

    @Test
    void addThreat_actorInTable_shouldAddThreat() {
        classUnderTest.addThreat(combatant1, combatant2, 1);
        assertEquals(1, classUnderTest.getThreat(combatant1, combatant2));

        classUnderTest.addThreat(combatant1, combatant2, 1);
        assertEquals(2, classUnderTest.getThreat(combatant1, combatant2));
    }

    @Test
    void getHighestThreat_withTwoCombatants_shouldReturnHighestThreatCombatant() {
        // combatant1 generates 2 threat against combatant3
        classUnderTest.addThreat(combatant1, combatant3, 2);
        // combatant2 generates 1 threat against combatant3
        classUnderTest.addThreat(combatant2, combatant3, 1);

        assertEquals(combatant1, classUnderTest.getHighestThreatActor(combatant3));
    }

    @Test
    void clearThreat_actorInTable_shouldClearAllThreat() {
        // threat that combatant1 has generated against others
        classUnderTest.addThreat(combatant1, combatant2, 1);
        classUnderTest.addThreat(combatant1, combatant3, 1);

        // threat that has been generated against combatant1
        classUnderTest.addThreat(combatant2, combatant1, 2);
        classUnderTest.addThreat(combatant3, combatant1, 2);

        classUnderTest.clearThreat(combatant1);

        assertEquals(0, classUnderTest.getThreat(combatant1, combatant2));
        assertEquals(0, classUnderTest.getThreat(combatant1, combatant3));
        //
        assertEquals(2, classUnderTest.getThreat(combatant2, combatant1));
        assertEquals(2, classUnderTest.getThreat(combatant3, combatant1));
    }

    @Test
    void clearThreat_actorNotInTable_shouldAddActorToTableWith0Threat() {
        EntityActor newCombatant = new EntityActor("newCombatant", Vector.ZERO);
        classUnderTest.clearThreat(newCombatant);

        assertEquals(0, classUnderTest.getThreat(newCombatant, combatant1));
        assertEquals(0, classUnderTest.getThreat(newCombatant, combatant2));
        assertEquals(0, classUnderTest.getThreat(newCombatant, combatant3));

        assertEquals(0, classUnderTest.getThreat(combatant1, newCombatant));
        assertEquals(0, classUnderTest.getThreat(combatant2, newCombatant));
        assertEquals(0, classUnderTest.getThreat(combatant3, newCombatant));
    }

    private void setUpTestSubjects() {
        combatant1 = new EntityActor("combatant1", Vector.ZERO);
        combatant2 = new EntityActor("combatant2", Vector.ZERO);
        combatant3 = new EntityActor("combatant3", Vector.ZERO);

        classUnderTest.addCombatant(combatant1);
        classUnderTest.addCombatant(combatant2);
        classUnderTest.addCombatant(combatant3);
    }
}
