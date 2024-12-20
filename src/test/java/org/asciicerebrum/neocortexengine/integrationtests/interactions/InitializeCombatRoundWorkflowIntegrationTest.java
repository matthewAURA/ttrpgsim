package org.asciicerebrum.neocortexengine.integrationtests.interactions;

import com.google.common.collect.Iterators;
import javax.naming.OperationNotSupportedException;
import org.asciicerebrum.neocortexengine.domain.core.particles.DiceRoll;
import org.asciicerebrum.neocortexengine.domain.core.particles.UniqueId;
import org.asciicerebrum.neocortexengine.domain.factories.ArmorFactory;
import org.asciicerebrum.neocortexengine.domain.factories.CampaignFactory;
import org.asciicerebrum.neocortexengine.domain.factories.DndCharacterFactory;
import org.asciicerebrum.neocortexengine.domain.factories.WeaponFactory;
import org.asciicerebrum.neocortexengine.domain.game.Campaign;
import org.asciicerebrum.neocortexengine.domain.game.DndCharacter;
import org.asciicerebrum.neocortexengine.domain.game.DndCharacters;
import org.asciicerebrum.neocortexengine.domain.ruleentities.DiceAction;
import org.asciicerebrum.neocortexengine.domain.ruleentities.composition.Condition;
import org.asciicerebrum.neocortexengine.domain.setup.BaseAbilityEntrySetup;
import org.asciicerebrum.neocortexengine.domain.setup.CharacterSetup;
import org.asciicerebrum.neocortexengine.domain.setup.SetupProperty;
import org.asciicerebrum.neocortexengine.integrationtests.pool.dndCharacters.HarskDwarfFighter2;
import org.asciicerebrum.neocortexengine.integrationtests.pool.dndCharacters.MerisielElfRogue1;
import org.asciicerebrum.neocortexengine.integrationtests.pool.dndCharacters.ValerosHumanFighter1;
import org.asciicerebrum.neocortexengine.integrationtests.pool.inventoryItems.armors.MwkChainmail;
import org.asciicerebrum.neocortexengine.integrationtests.pool.inventoryItems.armors.MwkHeavySteelShield;
import org.asciicerebrum.neocortexengine.integrationtests.pool.inventoryItems.armors.StandardLightWoodenShield;
import org.asciicerebrum.neocortexengine.integrationtests.pool.inventoryItems.armors.StandardStuddedLeather;
import org.asciicerebrum.neocortexengine.integrationtests.pool.inventoryItems.weapons.MwkBastardsword;
import org.asciicerebrum.neocortexengine.integrationtests.pool.inventoryItems.weapons.MwkRapier;
import org.asciicerebrum.neocortexengine.integrationtests.pool.inventoryItems.weapons.StandardBattleaxe;
import org.asciicerebrum.neocortexengine.integrationtests.pool.inventoryItems.weapons.StandardLongsword;
import org.asciicerebrum.neocortexengine.mechanics.managers.CombatRoundManager;
import org.asciicerebrum.neocortexengine.mechanics.managers.DefaultRollResultManager;
import org.asciicerebrum.neocortexengine.mechanics.managers.DiceRollManager;
import org.asciicerebrum.neocortexengine.mechanics.managers.RollResultManager;
import org.asciicerebrum.neocortexengine.services.context.EntityPoolService;
import org.asciicerebrum.neocortexengine.testcategories.IntegrationTest;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author species8472
 */
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class InitializeCombatRoundWorkflowIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private EntityPoolService entityPoolService;

    @Autowired
    private DndCharacterFactory dndCharacterFactory;

    @Autowired
    private ArmorFactory armorFactory;

    @Autowired
    private WeaponFactory weaponFactory;

    @Autowired
    private CampaignFactory campaignFactory;

    @Autowired
    private CombatRoundManager combatRoundManager;

    private DiceRollManager mockDiceRollManager;

    @Before
    public void setUp() {

        this.entityPoolService.registerUniqueEntity(this.dndCharacterFactory
                .newEntity(HarskDwarfFighter2.getSetup()));

        this.entityPoolService.registerUniqueEntity(this.armorFactory
                .newEntity(StandardStuddedLeather.getSetup()));
        this.entityPoolService.registerUniqueEntity(this.weaponFactory
                .newEntity(StandardBattleaxe.getSetup()));
        this.entityPoolService.registerUniqueEntity(this.armorFactory
                .newEntity(StandardLightWoodenShield.getSetup()));

        this.entityPoolService.registerUniqueEntity(this.dndCharacterFactory
                .newEntity(MerisielElfRogue1.getSetup()));

        this.entityPoolService.registerUniqueEntity(this.weaponFactory
                .newEntity(MwkRapier.getSetup()));

        this.entityPoolService.registerUniqueEntity(this.dndCharacterFactory
                .newEntity(ValerosHumanFighter1.getSetup()));

        this.entityPoolService.registerUniqueEntity(this.armorFactory
                .newEntity(MwkChainmail.getSetup()));
        this.entityPoolService.registerUniqueEntity(this.armorFactory
                .newEntity(MwkHeavySteelShield.getSetup()));
        this.entityPoolService.registerUniqueEntity(this.weaponFactory
                .newEntity(StandardLongsword.getSetup()));
        this.entityPoolService.registerUniqueEntity(this.weaponFactory
                .newEntity(MwkBastardsword.getSetup()));

        this.mockDiceRollManager = mock(DiceRollManager.class);
        ((DefaultRollResultManager) this.context
                .getBean(RollResultManager.class))
                .setDiceRollManager(this.mockDiceRollManager);

    }

    @Test
    public void initiateCombatRoundSimpleTest()
            throws OperationNotSupportedException {
        final Campaign campaign = this.campaignFactory.newEntity();
        final DndCharacters participants = new DndCharacters();

        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("harsk")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("merisiel")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("valeros")));

        when(this.mockDiceRollManager.rollDice((DiceAction) any()))
                .thenReturn(new DiceRoll(5L), new DiceRoll(20L),
                        new DiceRoll(8L));

        this.combatRoundManager.initiateCombatRound(campaign, participants);

        assertEquals("merisiel", campaign.getCombatRound()
                .getCurrentParticipantId().getValue());
    }

    @Test
    public void initiateCombatRoundSameResultsTest()
            throws OperationNotSupportedException {
        final Campaign campaign = this.campaignFactory.newEntity();
        final DndCharacters participants = new DndCharacters();

        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("harsk")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("merisiel")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("valeros")));

        when(this.mockDiceRollManager.rollDice((DiceAction) any()))
                .thenReturn(new DiceRoll(5L), new DiceRoll(17L),
                        new DiceRoll(14L));

        this.combatRoundManager.initiateCombatRound(campaign, participants);

        assertEquals("valeros", campaign.getCombatRound()
                .getCurrentParticipantId().getValue());
    }

    @Test
    public void initiateCombatRoundSameResultsSameBoniTest()
            throws OperationNotSupportedException {
        final Campaign campaign = this.campaignFactory.newEntity();
        final DndCharacters participants = new DndCharacters();

        // giving harsk a little more dexterity to be equal with merisiel
        final BaseAbilityEntrySetup dexSetup = new BaseAbilityEntrySetup();
        dexSetup.setAbility("dex");
        dexSetup.setAbilityValue("16");

        final CharacterSetup setup = HarskDwarfFighter2.getSetup();
        setup.getPropertySetups(SetupProperty.BASE_ABILITY_ENTRIES)
                .add(dexSetup);

        this.entityPoolService.registerUniqueEntity(this.dndCharacterFactory
                .newEntity(setup));

        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("harsk")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("merisiel")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("valeros")));

        // giving additional rolls to resolve the tie between harsk and merisiel
        when(this.mockDiceRollManager.rollDice((DiceAction) any()))
                .thenReturn(new DiceRoll(12L), new DiceRoll(12L),
                        new DiceRoll(1L), new DiceRoll(1L), new DiceRoll(2L));

        this.combatRoundManager.initiateCombatRound(campaign, participants);

        assertEquals("merisiel", campaign.getCombatRound()
                .getCurrentParticipantId().getValue());
    }

    @Test
    public void initiateCombatRoundFlatFootedTest()
            throws OperationNotSupportedException {
        final Campaign campaign = this.campaignFactory.newEntity();
        final DndCharacters participants = new DndCharacters();

        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("harsk")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("merisiel")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("valeros")));

        when(this.mockDiceRollManager.rollDice((DiceAction) any()))
                .thenReturn(new DiceRoll(5L), new DiceRoll(20L),
                        new DiceRoll(8L));

        this.combatRoundManager.initiateCombatRound(campaign, participants);

        final DndCharacter harsk = (DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("harsk"));

        assertEquals("flatFooted", harsk.getConditions().iterator().next()
                .getConditionType().getUniqueId().getValue());
    }

    @Test
    public void initiateCombatRoundFirstParticipantNotFlatFootedTest()
            throws OperationNotSupportedException {
        final Campaign campaign = this.campaignFactory.newEntity();
        final DndCharacters participants = new DndCharacters();

        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("harsk")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("merisiel")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("valeros")));

        when(this.mockDiceRollManager.rollDice((DiceAction) any()))
                .thenReturn(new DiceRoll(5L), new DiceRoll(20L),
                        new DiceRoll(8L));

        this.combatRoundManager.initiateCombatRound(campaign, participants);

        final DndCharacter merisiel = (DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("merisiel"));

        assertEquals(0L, Iterators.size(merisiel.getConditions().iterator()));
    }

    @Test
    public void initiateCombatRoundFlatFootedExpiryPositionTest()
            throws OperationNotSupportedException {
        final Campaign campaign = this.campaignFactory.newEntity();
        final DndCharacters participants = new DndCharacters();

        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("harsk")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("merisiel")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("valeros")));

        when(this.mockDiceRollManager.rollDice((DiceAction) any()))
                .thenReturn(new DiceRoll(5L), new DiceRoll(20L),
                        new DiceRoll(8L));

        this.combatRoundManager.initiateCombatRound(campaign, participants);

        final DndCharacter harsk = (DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("harsk"));

        final Condition flatFooted = harsk.getConditions().iterator().next();

        assertEquals("007002", flatFooted.getExpiryDate()
                .getCombatRoundPosition().getValue());
    }

    @Test
    public void initiateCombatRoundFlatFootedExpiryRoundTest()
            throws OperationNotSupportedException {
        final Campaign campaign = this.campaignFactory.newEntity();
        final DndCharacters participants = new DndCharacters();

        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("harsk")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("merisiel")));
        participants.addDndCharacter((DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("valeros")));

        when(this.mockDiceRollManager.rollDice((DiceAction) any()))
                .thenReturn(new DiceRoll(5L), new DiceRoll(20L),
                        new DiceRoll(8L));

        this.combatRoundManager.initiateCombatRound(campaign, participants);

        final DndCharacter harsk = (DndCharacter) this.entityPoolService
                .getEntityById(new UniqueId("harsk"));

        final Condition flatFooted = harsk.getConditions().iterator().next();

        assertEquals(0L, flatFooted.getExpiryDate()
                .getCombatRoundNumber().getValue());
    }

}
