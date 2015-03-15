package org.asciicerebrum.mydndgame.domain.ruleentities.composition;

import org.asciicerebrum.mydndgame.domain.core.particles.AdvancementNumber;
import org.asciicerebrum.mydndgame.domain.core.particles.DiceSides;
import org.asciicerebrum.mydndgame.domain.core.particles.HitPoints;
import org.asciicerebrum.mydndgame.domain.ruleentities.CharacterClass;
import org.asciicerebrum.mydndgame.domain.ruleentities.ClassLevel;
import org.asciicerebrum.mydndgame.domain.ruleentities.Dice;
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
public class LevelAdvancementTest {
    
    private LevelAdvancement levelAdvancement;
    
    public LevelAdvancementTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.levelAdvancement = new LevelAdvancement();
        
        this.levelAdvancement.setAdvNumber(AdvancementNumber.ADV_NO_0);
        
        final ClassLevel clLvl = new ClassLevel();
        final CharacterClass chCl = new CharacterClass();
        final Dice hd = new Dice();
        final DiceSides sides = new DiceSides(12L);
        
        hd.setSides(sides);
        chCl.setHitDice(hd);
        clLvl.setCharacterClass(chCl);
        this.levelAdvancement.setClassLevel(clLvl);
        this.levelAdvancement.setHpAdvancement(new HitPoints(20L));
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void getHpAdvancementLvl0Test() {
        assertEquals(12L, this.levelAdvancement.getHpAdvancement().getValue());
    }
    
    @Test
    public void getHpAdvancementLvl1Test() {
        this.levelAdvancement.setAdvNumber(AdvancementNumber.ADV_NO_1);
        assertEquals(20L, this.levelAdvancement.getHpAdvancement().getValue());
    }
    
}
