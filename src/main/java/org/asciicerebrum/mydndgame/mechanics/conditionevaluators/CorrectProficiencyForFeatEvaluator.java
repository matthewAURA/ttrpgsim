package org.asciicerebrum.mydndgame.mechanics.conditionevaluators;

import java.util.Iterator;
import org.asciicerebrum.mydndgame.domain.core.UniqueEntity;
import org.asciicerebrum.mydndgame.domain.game.entities.DndCharacter;
import org.asciicerebrum.mydndgame.domain.game.entities.Weapon;
import org.asciicerebrum.mydndgame.domain.mechanics.interfaces.ConditionEvaluator;
import org.asciicerebrum.mydndgame.domain.rules.entities.FeatBinding;
import org.asciicerebrum.mydndgame.domain.rules.entities.FeatBindings;
import org.asciicerebrum.mydndgame.domain.rules.entities.FeatType;
import org.asciicerebrum.mydndgame.domain.rules.entities.Proficiency;
import org.asciicerebrum.mydndgame.facades.gameentities.WeaponServiceFacade;

/**
 *
 * @author species8472
 */
public class CorrectProficiencyForFeatEvaluator implements ConditionEvaluator {

    private FeatType featType;

    /**
     * Getting modified real-time-values from the weapon.
     */
    private WeaponServiceFacade weaponServiceFacade;

    @Override
    public final boolean evaluate(final DndCharacter dndCharacter,
            final UniqueEntity contextItem) {

        if (this.featType == null) {
            return false;
        }

        final FeatBindings featBindings = dndCharacter.getLevelAdvancements()
                .getFeatBindingsByFeatType(this.featType);

        if (!(contextItem instanceof Weapon)) {
            return false;
        }

        final Weapon weapon = (Weapon) contextItem;
        final Iterator<FeatBinding> featBindingIterator
                = featBindings.iterator();
        while (featBindingIterator.hasNext()) {
            final FeatBinding featBinding = featBindingIterator.next();
            if (!(featBinding instanceof Proficiency)) {
                continue;
            }

            if (this.weaponServiceFacade.hasProficiency(
                    (Proficiency) featBinding, weapon, dndCharacter)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param featTypeInput the featType to set
     */
    public final void setFeatType(final FeatType featTypeInput) {
        this.featType = featTypeInput;
    }

    /**
     * @param weaponServiceFacadeInput the weaponServiceFacade to set
     */
    public final void setWeaponServiceFacade(
            final WeaponServiceFacade weaponServiceFacadeInput) {
        this.weaponServiceFacade = weaponServiceFacadeInput;
    }

}