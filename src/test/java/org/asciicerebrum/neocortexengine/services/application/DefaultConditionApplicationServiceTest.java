package org.asciicerebrum.neocortexengine.services.application;

import com.google.common.collect.Iterators;
import org.asciicerebrum.neocortexengine.domain.core.particles.CombatRoundNumber;
import org.asciicerebrum.neocortexengine.domain.core.particles.CombatRoundPosition;
import org.asciicerebrum.neocortexengine.domain.game.DndCharacter;
import org.asciicerebrum.neocortexengine.domain.mechanics.ObserverHooks;
import org.asciicerebrum.neocortexengine.domain.mechanics.WorldDate;
import org.asciicerebrum.neocortexengine.domain.mechanics.observer.source.ObserverSources;
import org.asciicerebrum.neocortexengine.domain.ruleentities.ConditionType;
import org.asciicerebrum.neocortexengine.domain.ruleentities.composition.Condition;
import org.asciicerebrum.neocortexengine.domain.ruleentities.composition.Conditions;
import org.asciicerebrum.neocortexengine.services.core.ObservableService;
import org.asciicerebrum.neocortexengine.services.events.EventTriggerService;
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
public class DefaultConditionApplicationServiceTest {

    private DefaultConditionApplicationService service;

    private ObservableService observableService;

    private EventTriggerService eventTriggerService;

    public DefaultConditionApplicationServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.service = new DefaultConditionApplicationService();
        this.observableService = mock(ObservableService.class);
        this.eventTriggerService = mock(EventTriggerService.class);

        this.service.setObservableService(this.observableService);
        this.service.setEventTriggerService(this.eventTriggerService);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void applyConditionNormalSizeTest() {
        final DndCharacter dndCharacter = new DndCharacter();
        final Conditions conditions = new Conditions();
        final Condition condA = new Condition();
        final Condition condB = new Condition();
        final ConditionType condType = new ConditionType();
        condA.setConditionType(condType);
        condB.setConditionType(condType);
        conditions.add(condA);
        conditions.add(condB);

        when(this.observableService.triggerObservers(
                eq(condA), eq(dndCharacter),
                (ObserverSources) any(),
                (ObserverHooks) any(), eq(dndCharacter)))
                .thenReturn(condA);

        this.service.applyCondition(dndCharacter, conditions);
        assertEquals(1L, Iterators.size(dndCharacter
                .getConditions().iterator()));
    }

    @Test
    public void applyConditionNormalObjectTest() {
        final DndCharacter dndCharacter = new DndCharacter();
        final Conditions conditions = new Conditions();
        final Condition condA = new Condition();
        final Condition condB = new Condition();
        final ConditionType condType = new ConditionType();
        condA.setConditionType(condType);
        condB.setConditionType(condType);
        conditions.add(condA);
        conditions.add(condB);

        when(this.observableService.triggerObservers(
                eq(condA), eq(dndCharacter),
                (ObserverSources) any(),
                (ObserverHooks) any(), eq(dndCharacter)))
                .thenReturn(condA);

        this.service.applyCondition(dndCharacter, conditions);
        assertEquals(condA, dndCharacter.getConditions().iterator().next());
    }

    @Test
    public void removeExpiredConditionsObjectTest() {
        final DndCharacter dndCharacter = new DndCharacter();
        final Condition condA = new Condition();
        final WorldDate dateA = new WorldDate();
        dateA.setCombatRoundNumber(new CombatRoundNumber(1L));
        dateA.setCombatRoundPosition(new CombatRoundPosition("1"));
        condA.setExpiryDate(dateA);
        final Condition condB = new Condition();
        final WorldDate dateB = new WorldDate();
        dateB.setCombatRoundNumber(new CombatRoundNumber(3L));
        dateB.setCombatRoundPosition(new CombatRoundPosition("4"));
        condB.setExpiryDate(dateB);
        dndCharacter.addCondition(condB);

        final WorldDate currentDate = new WorldDate();
        currentDate.setCombatRoundNumber(new CombatRoundNumber(2L));
        currentDate.setCombatRoundPosition(new CombatRoundPosition("0"));

        this.service.removeExpiredConditions(dndCharacter, currentDate);
        assertEquals(condB, dndCharacter.getConditions().iterator().next());
    }

    @Test
    public void removeExpiredConditionsCountTest() {
        final DndCharacter dndCharacter = new DndCharacter();
        final Condition condA = new Condition();
        final WorldDate dateA = new WorldDate();
        dateA.setCombatRoundNumber(new CombatRoundNumber(1L));
        dateA.setCombatRoundPosition(new CombatRoundPosition("1"));
        condA.setExpiryDate(dateA);
        final Condition condB = new Condition();
        final ConditionType condType = new ConditionType();
        condA.setConditionType(condType);
        condB.setConditionType(condType);
        final WorldDate dateB = new WorldDate();
        dateB.setCombatRoundNumber(new CombatRoundNumber(3L));
        dateB.setCombatRoundPosition(new CombatRoundPosition("4"));
        condB.setExpiryDate(dateB);
        dndCharacter.addCondition(condA);
        dndCharacter.addCondition(condB);

        final WorldDate currentDate = new WorldDate();
        currentDate.setCombatRoundNumber(new CombatRoundNumber(2L));
        currentDate.setCombatRoundPosition(new CombatRoundPosition("0"));

        this.service.removeExpiredConditions(dndCharacter, currentDate);
        assertEquals(1L, Iterators.size(dndCharacter
                .getConditions().iterator()));
    }
}
