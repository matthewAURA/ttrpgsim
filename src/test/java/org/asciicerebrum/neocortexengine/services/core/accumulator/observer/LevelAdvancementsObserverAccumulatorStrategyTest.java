package org.asciicerebrum.neocortexengine.services.core.accumulator.observer;

import com.google.common.collect.Iterators;
import org.asciicerebrum.neocortexengine.domain.core.UniqueEntity;
import org.asciicerebrum.neocortexengine.domain.core.particles.UniqueId;
import org.asciicerebrum.neocortexengine.domain.game.Weapon;
import org.asciicerebrum.neocortexengine.domain.mechanics.observer.Observer;
import org.asciicerebrum.neocortexengine.domain.mechanics.observer.Observers;
import org.asciicerebrum.neocortexengine.domain.mechanics.observer.source.ObserverSource;
import org.asciicerebrum.neocortexengine.domain.ruleentities.Ability;
import org.asciicerebrum.neocortexengine.domain.ruleentities.composition.LevelAdvancement;
import org.asciicerebrum.neocortexengine.domain.ruleentities.composition.LevelAdvancements;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author species8472
 */
public class LevelAdvancementsObserverAccumulatorStrategyTest {
    
    private LevelAdvancementsObserverAccumulatorStrategy strategy;
    
    private ObserverAccumulatorStrategy lvlAdvStrategy;
    
    public LevelAdvancementsObserverAccumulatorStrategyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.strategy = new LevelAdvancementsObserverAccumulatorStrategy();
        this.lvlAdvStrategy = mock(ObserverAccumulatorStrategy.class);
        
        this.strategy.setLvlAdvStrategy(this.lvlAdvStrategy);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void getObserversWrongInstanceTest() {
        final Ability observerSource = new Ability();
        final UniqueEntity targetEntity = new Weapon();
        
        final Observers result = this.strategy.getObservers(
                observerSource, targetEntity);
        
        assertEquals(0L, Iterators.size(result.iterator()));
    }
    
    @Test
    public void getObserversCorrectSizeTest() {
        final LevelAdvancements observerSource = new LevelAdvancements();
        final UniqueEntity targetEntity = new Weapon();
        targetEntity.setUniqueId(new UniqueId("weapon"));
        
        final LevelAdvancement lvlAdvA = new LevelAdvancement();
        final LevelAdvancement lvlAdvB = new LevelAdvancement();
        observerSource.add(lvlAdvA);
        observerSource.add(lvlAdvB);
        
        final Observer subObserver = new Observer();
        final Observers subObservers = new Observers();
        subObservers.add(subObserver);
        
        when(this.lvlAdvStrategy.getObservers(
                (ObserverSource) any(),
                eq(targetEntity))).thenReturn(subObservers);
        
        final Observers result = this.strategy.getObservers(
                observerSource, targetEntity);
        
        assertEquals(2L, Iterators.size(result.iterator()));
    }
    
}
