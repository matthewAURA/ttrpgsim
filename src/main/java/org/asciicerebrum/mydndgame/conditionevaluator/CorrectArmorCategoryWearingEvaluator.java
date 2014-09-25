package org.asciicerebrum.mydndgame.conditionevaluator;

import org.asciicerebrum.mydndgame.interfaces.entities.ConditionEvaluator;
import org.asciicerebrum.mydndgame.interfaces.entities.IArmor;
import org.asciicerebrum.mydndgame.interfaces.entities.IArmorCategory;
import org.asciicerebrum.mydndgame.interfaces.entities.ICharacter;

/**
 *
 * @author species8472
 */
public class CorrectArmorCategoryWearingEvaluator
        implements ConditionEvaluator {

    /**
     * The armor category in question.
     */
    private IArmorCategory armorCategory;

    /**
     * {@inheritDoc} Checks if the character wears armor of the given category.
     */
    @Override
    public final Boolean evaluate(final ICharacter character) {

        if (this.getArmorCategory() == null) {
            return Boolean.FALSE;
        }

        for (IArmor armor : character.getWieldedArmor()) {
            if (armor.getArmorCategory() != null
                    && armor.getArmorCategory()
                    .equals(this.getArmorCategory())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * @return the armorCategory
     */
    public final IArmorCategory getArmorCategory() {
        return armorCategory;
    }

    /**
     * @param armorCategoryInput the armorCategory to set
     */
    public final void setArmorCategory(
            final IArmorCategory armorCategoryInput) {
        this.armorCategory = armorCategoryInput;
    }

}
