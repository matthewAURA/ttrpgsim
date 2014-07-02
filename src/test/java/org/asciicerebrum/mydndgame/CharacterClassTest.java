package org.asciicerebrum.mydndgame;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author species8472
 */
public class CharacterClassTest {

    private CharacterClass chClass;
    private ClassLevel cLevel1;
    private ClassLevel cLevel2;
    private ClassLevel cLevel3;

    public CharacterClassTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        this.chClass = new CharacterClass();
        this.cLevel1 = new ClassLevel();
        this.cLevel1.setLevel(1);
        this.cLevel2 = new ClassLevel();
        this.cLevel2.setLevel(2);
        this.cLevel3 = new ClassLevel();
        this.cLevel3.setLevel(3);
        List<ClassLevel> classLevels = new ArrayList<ClassLevel>();
        classLevels.add(this.cLevel1);
        classLevels.add(this.cLevel2);
        classLevels.add(this.cLevel3);
        this.chClass.setClassLevels(classLevels);

        // level 1: +1
        Bonus atkBonus_1_0 = new Bonus();
        atkBonus_1_0.setValue(1L);
        atkBonus_1_0.setRank(0L);
        List<Bonus> atkBoni1 = new ArrayList<Bonus>();
        atkBoni1.add(atkBonus_1_0);
        cLevel1.setBaseAtkBoni(atkBoni1);

        // level 2: +2/+1
        Bonus atkBonus_2_1 = new Bonus();
        atkBonus_2_1.setValue(10L);
        atkBonus_2_1.setRank(1L);
        Bonus atkBonus_2_0 = new Bonus();
        atkBonus_2_0.setValue(50L);
        atkBonus_2_0.setRank(0L);
        List<Bonus> atkBoni2 = new ArrayList<Bonus>();
        atkBoni2.add(atkBonus_2_0);
        atkBoni2.add(atkBonus_2_1);
        this.cLevel2.setBaseAtkBoni(atkBoni2);

        // level 3: +3/+2/+1
        Bonus atkBonus_3_2 = new Bonus();
        atkBonus_3_2.setValue(100L);
        atkBonus_3_2.setRank(2L);
        Bonus atkBonus_3_1 = new Bonus();
        atkBonus_3_1.setValue(500L);
        atkBonus_3_1.setRank(1L);
        Bonus atkBonus_3_0 = new Bonus();
        atkBonus_3_0.setValue(1000L);
        atkBonus_3_0.setRank(0L);
        List<Bonus> atkBoni3 = new ArrayList<Bonus>();
        atkBoni3.add(atkBonus_3_0);
        atkBoni3.add(atkBonus_3_1);
        atkBoni3.add(atkBonus_3_2);
        cLevel3.setBaseAtkBoni(atkBoni3);

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getClassLevelByLevel method, of class CharacterClass.
     */
    @Test
    public void testGetClassLevelByLevel() {
        ClassLevel cLevel = this.chClass.getClassLevelByLevel(2);

        assertEquals(this.cLevel2, cLevel);
    }

    /**
     * Test of getClassLevelByLevel method, of class CharacterClass. Level out
     * of range.
     */
    @Test
    public void testGetClassLevelByLevelOutOfRange() {
        ClassLevel cLevel = this.chClass.getClassLevelByLevel(5);

        assertNull(cLevel);
    }

    /**
     * Test of getBaseAtkBonusValueDeltaByLevelAndRank method, of class
     * CharacterClass.
     */
    @Test
    public void testGetBaseAtkBonusValueDeltaByLevelAndRank() {

        Long bonusDelta
                = this.chClass.getBaseAtkBonusValueDeltaByLevelAndRank(
                        this.cLevel3, 1L);

        assertEquals(Long.valueOf(490L), bonusDelta);
    }

    /**
     * Test of getBaseAtkBonusValueDeltaByLevelAndRank method, of class
     * CharacterClass. When level - 1 not existing (+2) -> ..
     */
    @Test
    public void testGetBaseAtkBonusValueDeltaByLevelAndRankNoPrevLevel() {

        Long bonusDelta
                = this.chClass.getBaseAtkBonusValueDeltaByLevelAndRank(
                        this.cLevel1, 0L);

        assertEquals(Long.valueOf(1L), bonusDelta);
    }

    /**
     * Test of getBaseAtkBonusValueDeltaByLevelAndRank method, of class
     * CharacterClass. When rank of level - 1 not existing (+3/+2/+1) ->
     * (+2/+1).
     */
    @Test
    public void testGetBaseAtkBonusValueDeltaByLevelAndRankNoPrevLevelRank() {

        Long bonusDelta
                = this.chClass.getBaseAtkBonusValueDeltaByLevelAndRank(
                        this.cLevel3, 2L);

        assertEquals(Long.valueOf(100L), bonusDelta);
    }

}
