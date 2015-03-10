package org.asciicerebrum.mydndgame.domain.core.particles;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author species8472
 */
public class CombatRoundPositionsTest {

    private CombatRoundPositions combatRoundPositions;

    private CombatRoundPosition posA;
    private CombatRoundPosition posB;
    private CombatRoundPosition posC;

    public CombatRoundPositionsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.combatRoundPositions = new CombatRoundPositions();

        this.posA = new CombatRoundPosition("10");
        this.posB = new CombatRoundPosition("20");
        this.posC = new CombatRoundPosition("30");

        this.combatRoundPositions.addCombatRoundPosition(this.posA);
        this.combatRoundPositions.addCombatRoundPosition(this.posB);
        this.combatRoundPositions.addCombatRoundPosition(this.posC);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getFollowUpTest() {
        final CombatRoundPosition result
                = this.combatRoundPositions.getFollowUp(this.posB);

        assertEquals(this.posC, result);
    }

    @Test
    public void getFollowUpLastTest() {
        final CombatRoundPosition result
                = this.combatRoundPositions.getFollowUp(this.posC);

        assertEquals(this.posA, result);
    }

}
