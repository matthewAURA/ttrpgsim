package org.asciicerebrum.mydndgame.domain.factories;

import org.asciicerebrum.mydndgame.domain.core.particles.UniqueId;
import org.asciicerebrum.mydndgame.domain.game.Campaign;
import org.asciicerebrum.mydndgame.domain.game.CombatRound;
import org.asciicerebrum.mydndgame.domain.game.DndCharacter;
import org.asciicerebrum.mydndgame.domain.game.InventoryItem;
import org.asciicerebrum.mydndgame.domain.game.Weapon;
import org.asciicerebrum.mydndgame.domain.setup.CampaignSetup;
import org.asciicerebrum.mydndgame.domain.setup.CharacterSetup;
import org.asciicerebrum.mydndgame.domain.setup.CombatRoundSetup;
import org.asciicerebrum.mydndgame.domain.setup.InventoryItemSetup;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author species8472
 */
public class CampaignFactoryTest {

    private CampaignFactory factory;

    private ApplicationContext applicationContext;

    private EntityFactory<DndCharacter> characterFactory;

    private EntityFactory<InventoryItem> inventoryItemFactory;

    private EntityFactory<CombatRound> combatRoundFactory;

    public CampaignFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.factory = new CampaignFactory();
        this.characterFactory = mock(EntityFactory.class);
        this.inventoryItemFactory = mock(EntityFactory.class);
        this.combatRoundFactory = mock(EntityFactory.class);
        this.applicationContext = mock(ApplicationContext.class,
                withSettings()
                .extraInterfaces(ConfigurableApplicationContext.class));

        this.factory.setCharacterFactory(this.characterFactory);
        this.factory.setCombatRoundFactory(this.combatRoundFactory);
        this.factory.setInventoryItemFactory(this.inventoryItemFactory);
        this.factory.setContext(this.applicationContext);

        when(this.applicationContext.getBean(Campaign.class))
                .thenReturn(new Campaign());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void newEntitySimpleCompleteTest() {
        final CampaignSetup setup = new CampaignSetup();
        final Reassignments reassignments = new Reassignments();

        final Campaign campaign = this.factory.newEntity(setup, reassignments);

        assertNotNull(campaign);
    }

    @Test
    public void newEntityWithInventoryItemsTest() {
        final CampaignSetup setup = new CampaignSetup();
        final Reassignments reassignments = new Reassignments();

        final InventoryItemSetup itemSetup = new InventoryItemSetup() {
        };
        setup.addInventoryItem(itemSetup);
        setup.addInventoryItem(itemSetup);
        final Weapon weapon = new Weapon();
        weapon.setUniqueId(new UniqueId("weaponId"));

        when(this.inventoryItemFactory.newEntity(itemSetup, reassignments))
                .thenReturn(weapon);

        this.factory.newEntity(setup, reassignments);

        verify(this.inventoryItemFactory, times(2))
                .newEntity(itemSetup, reassignments);
    }

    @Test
    public void newEntityWithParticipantsTest() {
        final CampaignSetup setup = new CampaignSetup();
        final Reassignments reassignments = new Reassignments();

        final CharacterSetup characterSetup = new CharacterSetup();
        setup.addDndCharacter(characterSetup);
        setup.addDndCharacter(characterSetup);
        setup.addDndCharacter(characterSetup);
        final DndCharacter dndCharacter = new DndCharacter();
        dndCharacter.setUniqueId(new UniqueId("characterId"));

        when(this.characterFactory.newEntity(characterSetup, reassignments))
                .thenReturn(dndCharacter);

        this.factory.newEntity(setup, reassignments);

        verify(this.characterFactory, times(3))
                .newEntity(characterSetup, reassignments);
    }

    @Test
    public void newEntityWithCombatRoundTest() {
        final CampaignSetup setup = new CampaignSetup();
        final Reassignments reassignments = new Reassignments();

        final CombatRoundSetup combatRoundSetup = new CombatRoundSetup();
        setup.setCombatRound(combatRoundSetup);
        final CombatRound combatRound = new CombatRound();

        when(this.combatRoundFactory.newEntity(combatRoundSetup, reassignments))
                .thenReturn(combatRound);

        final Campaign result = this.factory.newEntity(setup, reassignments);

        assertEquals(combatRound, result.getCombatRound());
    }

    @Test
    public void newEntityReassignmentsTest() {
        final CampaignSetup setup = new CampaignSetup();
        final Reassignments reassignments = new Reassignments();

        final CharacterSetup characterSetup = new CharacterSetup();
        final DndCharacter dndCharacter = new DndCharacter();
        reassignments.addEntry(this.characterFactory, characterSetup,
                dndCharacter);

        this.factory.newEntity(setup, reassignments);

        verify(this.characterFactory, times(1))
                .reAssign(characterSetup, dndCharacter, reassignments);
    }

}