package org.asciicerebrum.mydndgame.interactionworkflows;

import java.util.Iterator;
import org.asciicerebrum.mydndgame.domain.game.aspect.CombatRound;
import org.asciicerebrum.mydndgame.domain.game.entities.DndCharacter;
import org.asciicerebrum.mydndgame.domain.transfer.Interaction;
import org.asciicerebrum.mydndgame.services.application.ConditionApplicationService;

/**
 *
 * @author species8472
 */
public class ConditionExpirationWorkflow implements IWorkflow {

    private ConditionApplicationService conditionService;

    /**
     * {@inheritDoc} It checks all conditions of all participants if they expire
     * in this new combat round position and unregisters them.
     */
    @Override
    public final void runWorkflow(final Interaction interaction) {

        final CombatRound combatRound = interaction.getCombatRound();

        final Iterator<DndCharacter> iterator
                = combatRound.participantsIterator();
        while (iterator.hasNext()) {
            final DndCharacter participant = iterator.next();

            this.conditionService.removeExpiredConditions(participant,
                    combatRound.getCurrentDate());
        }
    }

    /**
     * @param conditionServiceInput the conditionService to set
     */
    public final void setConditionService(
            final ConditionApplicationService conditionServiceInput) {
        this.conditionService = conditionServiceInput;
    }

}
