package org.asciicerebrum.neocortexengine.services.core.accumulator.observer;

import com.google.common.collect.Iterators;
import org.asciicerebrum.neocortexengine.domain.core.UniqueEntity;
import org.asciicerebrum.neocortexengine.domain.game.DndCharacter;
import org.asciicerebrum.neocortexengine.domain.game.Weapon;
import org.asciicerebrum.neocortexengine.domain.mechanics.observer.Observer;
import org.asciicerebrum.neocortexengine.domain.mechanics.observer.Observers;
import org.asciicerebrum.neocortexengine.domain.mechanics.observer.source.ObserverSource;
import org.asciicerebrum.neocortexengine.domain.ruleentities.Ability;
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
public class DndCharacterObserverAccumulatorStrategyTest {
    
    private DndCharacterObserverAccumulatorStrategy strategy;
    
    private ObserverAccumulatorStrategy multiStrategy;
    
    public DndCharacterObserverAccumulatorStrategyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.strategy = new DndCharacterObserverAccumulatorStrategy();
        this.multiStrategy = mock(ObserverAccumulatorStrategy.class);
        
        this.strategy.setBaseAbilitiesStrategy(this.multiStrategy);
        this.strategy.setConditionsStrategy(this.multiStrategy);
        this.strategy.setLevelAdvancementsStrategy(this.multiStrategy);
        this.strategy.setPersonalizedBodySlotsStrategy(this.multiStrategy);
        this.strategy.setRaceStrategy(this.multiStrategy);
        this.strategy.setPrototypeSpecialAbilitiesStrategy(this.multiStrategy);
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
        final DndCharacter observerSource = new DndCharacter();
        final UniqueEntity targetEntity = new Weapon();
        
        final Observer subObserver = new Observer();
        final Observers subObservers = new Observers();
        subObservers.add(subObserver);
        
        when(this.multiStrategy.getObservers(
                (ObserverSource) any(),
                eq(targetEntity))).thenReturn(subObservers);
        
        final Observers result = this.strategy.getObservers(
                observerSource, targetEntity);
        
        assertEquals(6L, Iterators.size(result.iterator()));
    }
    
}
