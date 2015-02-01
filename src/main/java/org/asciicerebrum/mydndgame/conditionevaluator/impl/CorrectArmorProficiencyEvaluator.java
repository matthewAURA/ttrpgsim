package org.asciicerebrum.mydndgame.conditionevaluator.impl;

import org.asciicerebrum.mydndgame.conditionevaluator.ConditionEvaluator;
import org.asciicerebrum.mydndgame.domain.rules.entities.Proficiency;
import org.asciicerebrum.mydndgame.domain.core.mechanics.Bonus;
import org.asciicerebrum.mydndgame.domain.core.mechanics.Observer;
import org.asciicerebrum.mydndgame.domain.game.entities.DndCharacter;

/**
 *
 * @author species8472
 */
public class CorrectArmorProficiencyEvaluator implements ConditionEvaluator {

    /**
     * The proficiency in question.
     */
    private Proficiency proficiency;

    /**
     * {@inheritDoc} Checks if the worn armor is of the given proficiency.
     */
    @Override
    public final boolean evaluate(final DndCharacter dndCharacter,
            final Observer referenceObserver) {
        if (this.proficiency == null) {
            return false;
        }

        return dndCharacter.getArmorWorn()
                .containsProficiency(this.proficiency);
    }

    @Override
    public final boolean evaluate(final DndCharacter dndCharacter,
            final Bonus referenceBonus) {
        return this.evaluate(dndCharacter, (Observer) null);
    }

    /**
     * @return the proficiency
     */
    public final Proficiency getProficiency() {
        return proficiency;
    }

    /**
     * @param proficiencyInput the proficiency to set
     */
    public final void setProficiency(final Proficiency proficiencyInput) {
        this.proficiency = proficiencyInput;
    }

}
