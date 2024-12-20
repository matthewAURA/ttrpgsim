package org.asciicerebrum.neocortexengine.mechanics.conditionevaluators.impl;

import org.asciicerebrum.neocortexengine.domain.core.ICharacter;
import org.asciicerebrum.neocortexengine.domain.core.UniqueEntity;
import org.asciicerebrum.neocortexengine.domain.core.particles.UniqueId;
import org.asciicerebrum.neocortexengine.domain.game.Armor;
import org.asciicerebrum.neocortexengine.domain.game.DndCharacter;
import org.asciicerebrum.neocortexengine.domain.game.InventoryItem;
import org.asciicerebrum.neocortexengine.domain.game.Weapon;
import org.asciicerebrum.neocortexengine.services.context.EntityPoolService;
import org.asciicerebrum.neocortexengine.services.context.SituationContextService;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author species8472
 */
public class ItemInUseEvaluatorTest {

    private ItemInUseEvaluator evaluator;

    private SituationContextService sitConService;

    private EntityPoolService entityPoolService;

    public ItemInUseEvaluatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.evaluator = new ItemInUseEvaluator();
        this.sitConService = mock(SituationContextService.class);
        this.entityPoolService = mock(EntityPoolService.class);

        this.evaluator.setCtxService(this.sitConService);
        this.evaluator.setEntityPoolService(this.entityPoolService);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void evaluateBothNullTest() {
        final ICharacter dndCharacter = new DndCharacter();
        final UniqueEntity contextItem = null;

        when(this.sitConService.getActiveItemId((DndCharacter) dndCharacter))
                .thenReturn(null);

        final boolean result = this.evaluator.evaluate(
                dndCharacter, contextItem);
        assertTrue(result);
    }

    @Test
    public void evaluateBothEqualTest() {
        final ICharacter dndCharacter = new DndCharacter();
        final UniqueEntity contextItem = new Weapon();

        when(this.entityPoolService.getEntityById((UniqueId) any()))
                .thenReturn((InventoryItem) contextItem);

        final boolean result = this.evaluator.evaluate(
                dndCharacter, contextItem);
        assertTrue(result);
    }

    @Test
    public void evaluateBothNotEqualTest() {
        final ICharacter dndCharacter = new DndCharacter();
        final UniqueEntity contextItem = new Weapon();
        contextItem.setUniqueId(new UniqueId("contextItem"));

        final Armor inventoryItem = new Armor();
        inventoryItem.setUniqueId(new UniqueId("inventoryItem"));

        when(this.entityPoolService.getEntityById((UniqueId) any()))
                .thenReturn((InventoryItem) inventoryItem);

        final boolean result = this.evaluator.evaluate(
                dndCharacter, contextItem);
        assertFalse(result);
    }

    @Test
    public void evaluateContextNullTest() {
        final ICharacter dndCharacter = new DndCharacter();
        final UniqueEntity contextItem = new Weapon();

        when(this.entityPoolService.getEntityById((UniqueId) any()))
                .thenReturn((InventoryItem) contextItem);

        final boolean result = this.evaluator.evaluate(
                dndCharacter, null);
        assertFalse(result);
    }

    @Test
    public void evaluateActiveNullTest() {
        final ICharacter dndCharacter = new DndCharacter();
        final UniqueEntity contextItem = new Weapon();

        when(this.entityPoolService.getEntityById((UniqueId) any()))
                .thenReturn((InventoryItem) null);

        final boolean result = this.evaluator.evaluate(
                dndCharacter, contextItem);
        assertFalse(result);
    }

}
