package org.asciicerebrum.mydndgame.mechanics.conditionevaluators.impl;

import org.asciicerebrum.mydndgame.domain.core.UniqueEntity;
import org.asciicerebrum.mydndgame.domain.core.particles.UniqueId;
import org.asciicerebrum.mydndgame.domain.game.DndCharacter;
import org.asciicerebrum.mydndgame.domain.game.Weapon;
import org.asciicerebrum.mydndgame.domain.ruleentities.BodySlot;
import org.asciicerebrum.mydndgame.domain.ruleentities.composition.PersonalizedBodySlot;
import org.asciicerebrum.mydndgame.domain.ruleentities.composition.PersonalizedBodySlots;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author species8472
 */
public class CorrectInventoryItemWieldingEvaluatorTest {

    private CorrectInventoryItemWieldingEvaluator evaluator;

    public CorrectInventoryItemWieldingEvaluatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.evaluator = new CorrectInventoryItemWieldingEvaluator();

        this.evaluator.setWieldingType(
                CorrectInventoryItemWieldingEvaluator.WieldingType.BOTH);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void evaluateNullWieldingTypeTest() {
        final DndCharacter dndCharacter = new DndCharacter();
        dndCharacter.setUniqueId(new UniqueId("character"));
        final UniqueEntity contextItem = new Weapon();

        this.evaluator.setWieldingType(null);

        final boolean result = this.evaluator.evaluate(
                dndCharacter, contextItem);
        assertFalse(result);
    }

    @Test
    public void evaluateNullContextItemTest() {
        final DndCharacter dndCharacter = new DndCharacter();
        dndCharacter.setUniqueId(new UniqueId("character"));
        final UniqueEntity contextItem = null;

        final boolean result = this.evaluator.evaluate(
                dndCharacter, contextItem);
        assertFalse(result);
    }

    @Test
    public void evaluateBothCorrectTest() {
        final DndCharacter dndCharacter = new DndCharacter();
        dndCharacter.setUniqueId(new UniqueId("character"));
        final UniqueEntity contextItem = new Weapon();
        contextItem.setUniqueId(new UniqueId("contextItem"));

        final PersonalizedBodySlots pSlots = new PersonalizedBodySlots();
        final PersonalizedBodySlot pSlot = new PersonalizedBodySlot();
        final PersonalizedBodySlot pSlotCounter = new PersonalizedBodySlot();
        final BodySlot slot = new BodySlot();
        final BodySlot slotCounter = new BodySlot();
        slot.setCounterSlot(slotCounter);
        slotCounter.setCounterSlot(slot);
        pSlot.setItemId(contextItem.getUniqueId());
        pSlot.setBodySlot(slot);
        pSlotCounter.setItemId(contextItem.getUniqueId());
        pSlotCounter.setBodySlot(slotCounter);
        pSlots.add(pSlot);
        pSlots.add(pSlotCounter);
        dndCharacter.setPersonalizedBodySlots(pSlots);

        final boolean result = this.evaluator.evaluate(
                dndCharacter, contextItem);
        assertTrue(result);
    }

    @Test
    public void evaluateBothNothingInSlotTest() {
        final DndCharacter dndCharacter = new DndCharacter();
        dndCharacter.setUniqueId(new UniqueId("character"));
        final UniqueEntity contextItem = new Weapon();
        contextItem.setUniqueId(new UniqueId("contextItem"));

        final PersonalizedBodySlots pSlots = new PersonalizedBodySlots();
        final PersonalizedBodySlot pSlot = new PersonalizedBodySlot();
        final PersonalizedBodySlot pSlotCounter = new PersonalizedBodySlot();
        final BodySlot slot = new BodySlot();
        final BodySlot slotCounter = new BodySlot();
        slot.setCounterSlot(slotCounter);
        slotCounter.setCounterSlot(slot);
        pSlot.setItemId(null);
        pSlot.setBodySlot(slot);
        pSlotCounter.setItemId(null);
        pSlotCounter.setBodySlot(slotCounter);
        pSlots.add(pSlot);
        pSlots.add(pSlotCounter);
        dndCharacter.setPersonalizedBodySlots(pSlots);

        final boolean result = this.evaluator.evaluate(
                dndCharacter, contextItem);
        assertFalse(result);
    }

    @Test
    public void evaluateBothNoCounterSlotTest() {
        final DndCharacter dndCharacter = new DndCharacter();
        dndCharacter.setUniqueId(new UniqueId("character"));
        final UniqueEntity contextItem = new Weapon();
        contextItem.setUniqueId(new UniqueId("contextItem"));

        final PersonalizedBodySlots pSlots = new PersonalizedBodySlots();
        final PersonalizedBodySlot pSlot = new PersonalizedBodySlot();
        final PersonalizedBodySlot pSlotCounter = new PersonalizedBodySlot();
        final BodySlot slot = new BodySlot();
        final BodySlot slotCounter = new BodySlot();
        slot.setCounterSlot(null);
        slotCounter.setCounterSlot(null);
        pSlot.setItemId(contextItem.getUniqueId());
        pSlot.setBodySlot(slot);
        pSlotCounter.setItemId(contextItem.getUniqueId());
        pSlotCounter.setBodySlot(slotCounter);
        pSlots.add(pSlot);
        pSlots.add(pSlotCounter);
        dndCharacter.setPersonalizedBodySlots(pSlots);

        final boolean result = this.evaluator.evaluate(
                dndCharacter, contextItem);
        assertFalse(result);
    }

    @Test
    public void evaluateBothNoCounterItemTest() {
        final DndCharacter dndCharacter = new DndCharacter();
        dndCharacter.setUniqueId(new UniqueId("character"));
        final UniqueEntity contextItem = new Weapon();
        contextItem.setUniqueId(new UniqueId("contextItem"));

        final PersonalizedBodySlots pSlots = new PersonalizedBodySlots();
        final PersonalizedBodySlot pSlot = new PersonalizedBodySlot();
        final PersonalizedBodySlot pSlotCounter = new PersonalizedBodySlot();
        final BodySlot slot = new BodySlot();
        final BodySlot slotCounter = new BodySlot();
        slot.setCounterSlot(slotCounter);
        slotCounter.setCounterSlot(slot);
        pSlot.setItemId(contextItem.getUniqueId());
        pSlot.setBodySlot(slot);
        pSlotCounter.setItemId(null);
        pSlotCounter.setBodySlot(slotCounter);
        pSlots.add(pSlot);
        pSlots.add(pSlotCounter);
        dndCharacter.setPersonalizedBodySlots(pSlots);

        final boolean result = this.evaluator.evaluate(
                dndCharacter, contextItem);
        assertFalse(result);
    }

}
