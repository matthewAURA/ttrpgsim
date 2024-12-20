package org.asciicerebrum.neocortexengine.mechanics.interactionworkflows;

import java.util.Iterator;
import org.asciicerebrum.neocortexengine.domain.core.UniqueEntity;
import org.asciicerebrum.neocortexengine.domain.core.particles.BonusValue;
import org.asciicerebrum.neocortexengine.domain.core.particles.CombatRoundPosition;
import org.asciicerebrum.neocortexengine.domain.core.particles.DiceRoll;
import org.asciicerebrum.neocortexengine.domain.core.particles.UniqueId;
import org.asciicerebrum.neocortexengine.domain.core.particles.UniqueIds;
import org.asciicerebrum.neocortexengine.domain.game.Campaign;
import org.asciicerebrum.neocortexengine.domain.game.CombatRound;
import org.asciicerebrum.neocortexengine.domain.game.DndCharacter;
import org.asciicerebrum.neocortexengine.domain.game.DndCharacters;
import org.asciicerebrum.neocortexengine.domain.mechanics.WorldDate;
import org.asciicerebrum.neocortexengine.domain.ruleentities.ConditionType;
import org.asciicerebrum.neocortexengine.domain.ruleentities.DiceAction;
import org.asciicerebrum.neocortexengine.domain.ruleentities.composition.Conditions;
import org.asciicerebrum.neocortexengine.domain.ruleentities.composition.RollResult;
import org.asciicerebrum.neocortexengine.mechanics.managers.RollResultManager;
import org.asciicerebrum.neocortexengine.services.application.ConditionApplicationService;
import org.asciicerebrum.neocortexengine.services.context.EntityPoolService;
import org.asciicerebrum.neocortexengine.services.context.MapBasedEntityPoolService;
import org.asciicerebrum.neocortexengine.services.statistics.InitiativeCalculationService;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author species8472
 */
public class InitializeCombatRoundWorkflowTest {

    private InitializeCombatRoundWorkflow wf;
    private DiceAction initiativeAction;
    private ConditionType flatFootedType;
    private InitiativeCalculationService initService;
    private ConditionApplicationService conditionService;
    private DndCharacter positiveCharacter;
    private DndCharacter negativeCharacter;
    private EntityPoolService entityPoolService;
    private RollResultManager rollResultManager;

    public InitializeCombatRoundWorkflowTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.wf = new InitializeCombatRoundWorkflow();
        this.rollResultManager = mock(RollResultManager.class);
        this.initiativeAction = mock(DiceAction.class);
        this.flatFootedType = mock(ConditionType.class);
        this.initService = mock(InitiativeCalculationService.class);
        this.conditionService = mock(ConditionApplicationService.class);
        this.entityPoolService = new MapBasedEntityPoolService();

        this.wf.setConditionApplicationService(this.conditionService);
        this.wf.setRollResultManager(this.rollResultManager);
        this.wf.setFlatFootedType(this.flatFootedType);
        this.wf.setInitiativeAction(this.initiativeAction);
        this.wf.setInitiativeCalculationService(this.initService);
        this.wf.setEntityPoolService(this.entityPoolService);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void rollInitiativeTest() {
        final DndCharacters characters = new DndCharacters();
        final CombatRound combatRound = new CombatRound();
        final Campaign campaign = new Campaign();

        final DndCharacter characterA = new DndCharacter();
        characterA.setUniqueId(new UniqueId("A"));
        final DndCharacter characterB = new DndCharacter();
        characterB.setUniqueId(new UniqueId("B"));
        characters.addDndCharacter(characterA);
        characters.addDndCharacter(characterB);

        this.entityPoolService.registerUniqueEntity(characterA);
        this.entityPoolService.registerUniqueEntity(characterB);

        when(this.initService.calcInitBonus(characterA))
                .thenReturn(new BonusValue(3));
        when(this.initService.calcInitBonus(characterB))
                .thenReturn(new BonusValue(5));

        final RollResult result = new RollResult(new DiceRoll(0L),
                new BonusValue());
        when(this.rollResultManager.retrieveRollResult(
                (BonusValue) any(),
                eq(this.initiativeAction),
                (UniqueEntity) any(),
                (DndCharacter) any(),
                (UniqueIds) any(),
                (WorldDate) any(),
                (Campaign) any()))
                .thenReturn(result);

        this.wf.rollInitiative(characters.iterator(), combatRound, campaign);

        final Iterator<UniqueId> dndIt = combatRound.participantsIterator();
        dndIt.next();

        assertEquals(characterB.getUniqueId(), dndIt.next());
    }

    private CombatRound setupCombatRound() {
        final CombatRound combatRound = new CombatRound();
        this.positiveCharacter = new DndCharacter();
        this.positiveCharacter.setUniqueId(new UniqueId("A"));
        final DndCharacter characterB = new DndCharacter();
        characterB.setUniqueId(new UniqueId("B"));
        this.negativeCharacter = new DndCharacter();
        this.negativeCharacter.setUniqueId(new UniqueId("C"));

        combatRound.addParticipant(this.positiveCharacter.getUniqueId(),
                new CombatRoundPosition("a"));
        combatRound.addParticipant(this.negativeCharacter.getUniqueId(),
                new CombatRoundPosition("c"));
        combatRound.addParticipant(characterB.getUniqueId(),
                new CombatRoundPosition("a"));

        this.entityPoolService.registerUniqueEntity(characterB);
        this.entityPoolService.registerUniqueEntity(this.positiveCharacter);
        this.entityPoolService.registerUniqueEntity(this.negativeCharacter);

        return combatRound;
    }

    @Test
    public void getTieingParticipantsPositiveTest() {
        final DndCharacters dndCharacters
                = this.wf.getTieingParticipants(this.setupCombatRound());

        assertTrue(dndCharacters.contains(this.positiveCharacter));
    }

    @Test
    public void getTieingParticipantsNegativeTest() {
        final DndCharacters dndCharacters
                = this.wf.getTieingParticipants(this.setupCombatRound());

        assertFalse(dndCharacters.contains(this.negativeCharacter));
    }

    private DndCharacters setupTieingParticipants(final CombatRound cRound) {
        final DndCharacters tieingParticipants = new DndCharacters();
        tieingParticipants.addDndCharacter(this.negativeCharacter);
        final DndCharacter furtherCharacter = new DndCharacter();
        furtherCharacter.setUniqueId(new UniqueId("D"));
        tieingParticipants.addDndCharacter(furtherCharacter);

        cRound.addParticipant(furtherCharacter.getUniqueId(),
                new CombatRoundPosition("c"));
        // the positive character is already there but has to get another
        // position to not interfere with the tieing of the others.
        cRound.addParticipant(this.positiveCharacter.getUniqueId(),
                new CombatRoundPosition("xyz"));

        return tieingParticipants;
    }

    @Test
    public void tieResolutionStepPositiveTest() {
        final CombatRound combatRound = this.setupCombatRound();
        final DndCharacters tieingParticipants
                = this.setupTieingParticipants(combatRound);
        final Campaign campaign = new Campaign();

        final RollResult result1 = new RollResult(new DiceRoll(1L),
                new BonusValue());
        final RollResult result2 = new RollResult(new DiceRoll(2L),
                new BonusValue());
        // resolving ties with different dice rolls
        when(this.rollResultManager.retrieveRollResult(
                (BonusValue) any(),
                eq(this.initiativeAction),
                (UniqueEntity) any(),
                (DndCharacter) any(),
                (UniqueIds) any(),
                (WorldDate) any(),
                (Campaign) any()))
                .thenReturn(result1, result2);

        final DndCharacters dndCharacters
                = this.wf.tieResolutionStep(tieingParticipants, combatRound,
                        campaign);

        assertFalse(dndCharacters.hasEntries());
    }

    @Test
    public void tieResolutionStepNegativeTest() {
        final CombatRound combatRound = this.setupCombatRound();
        final DndCharacters tieingParticipants
                = this.setupTieingParticipants(combatRound);
        final Campaign campaign = new Campaign();

        final RollResult result = new RollResult(new DiceRoll(4L),
                new BonusValue());
        // keeping ties with equal dice rolls
        when(this.rollResultManager.retrieveRollResult(
                (BonusValue) any(),
                eq(this.initiativeAction),
                (UniqueEntity) any(),
                (DndCharacter) any(),
                (UniqueIds) any(),
                (WorldDate) any(),
                (Campaign) any()))
                .thenReturn(result, result);

        final DndCharacters dndCharacters
                = this.wf.tieResolutionStep(tieingParticipants, combatRound,
                        campaign);

        assertTrue(dndCharacters.hasEntries());
    }

    @Test
    public void applyFlatFootedTest() {
        final CombatRound combatRound = new CombatRound();

        combatRound.addParticipant(new UniqueId("A"),
                new CombatRoundPosition("020"));
        combatRound.addParticipant(new UniqueId("B"),
                new CombatRoundPosition("010"));

        final DndCharacter characterA = new DndCharacter();
        characterA.setUniqueId(new UniqueId("A"));
        final DndCharacter characterB = new DndCharacter();
        characterB.setUniqueId(new UniqueId("B"));

        this.entityPoolService.registerUniqueEntity(characterA);
        this.entityPoolService.registerUniqueEntity(characterB);

        this.wf.applyFlatFooted(combatRound);

        // only the second character becomes flat footed!
        verify(this.conditionService, times(1)).applyCondition(
                eq(characterB), (Conditions) any()
        );
    }

    @Test
    public void applyFlatFootedFirstCharacterTest() {
        final CombatRound combatRound = new CombatRound();

        combatRound.addParticipant(new UniqueId("A"),
                new CombatRoundPosition("020"));
        combatRound.addParticipant(new UniqueId("B"),
                new CombatRoundPosition("010"));

        final DndCharacter characterA = new DndCharacter();
        characterA.setUniqueId(new UniqueId("A"));
        final DndCharacter characterB = new DndCharacter();
        characterB.setUniqueId(new UniqueId("B"));

        this.entityPoolService.registerUniqueEntity(characterA);
        this.entityPoolService.registerUniqueEntity(characterB);

        this.wf.applyFlatFooted(combatRound);

        // only the second character becomes flat footed!
        verify(this.conditionService, times(0)).applyCondition(
                eq(characterA), (Conditions) any()
        );
    }
}
