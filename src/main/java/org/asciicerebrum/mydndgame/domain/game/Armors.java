package org.asciicerebrum.mydndgame.domain.game;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.asciicerebrum.mydndgame.domain.ruleentities.ArmorCategory;
import org.asciicerebrum.mydndgame.domain.ruleentities.Proficiency;

/**
 *
 * @author species8472
 */
public class Armors extends InventoryItems<Armor> {

    /**
     * This is used as a predicate to determine if a given armor is compatible
     * with the setup armor category. The basic (unmodified value) is used.
     * Predicates are good for list filters.
     */
    private static class HasArmorCategoryPredicate implements Predicate {

        /**
         * The armor category to test for.
         */
        private final ArmorCategory armorCategory;

        /**
         * Constructor for setting up the armor category.
         *
         * @param armorCategoryInput the armor category in question.
         */
        public HasArmorCategoryPredicate(
                final ArmorCategory armorCategoryInput) {
            this.armorCategory = armorCategoryInput;
        }

        @Override
        public final boolean evaluate(final Object object) {
            Armor armor = (Armor) object;
            return armor.hasArmorCategory(this.armorCategory);
        }
    }

    /**
     * Standard constructor for empty armors list.
     */
    public Armors() {

    }

    /**
     * Constructor for putting a list of armors into the array.
     *
     * @param armorList the armors to be used in the list.
     */
    public Armors(final List<Armor> armorList) {
        this.getElements().addAll(armorList);
    }

    /**
     * Tests if at least one armor in the list is compatible with the given
     * armor category. Only the basic values are used.
     *
     * @param armorCategory the armor category in question.
     * @return true if found within the list, false otherwise.
     */
    public final boolean containsArmorCategory(
            final ArmorCategory armorCategory) {
        for (final Armor armor : this.getElements()) {
            if (armor.hasArmorCategory(armorCategory)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if at least one armor in the list is compatible with the given
     * proficiency. Only the basic values are used.
     *
     * @param proficiency the proficiency in question.
     * @return true if found within the list, false otherwise.
     */
    public final boolean containsProficiency(final Proficiency proficiency) {
        for (final Armor armor : this.getElements()) {
            if (armor.hasProficiency(proficiency)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Selects all elements in the list compatible with the given armor
     * category.
     *
     * @param armorCategory the armor category in question.
     * @return the list with armors that are compatible with this armor
     * category.
     */
    public final Armors filterByArmorCateogry(
            final ArmorCategory armorCategory) {

        List<Armor> armorList = new ArrayList<Armor>();

        CollectionUtils.select(this.getElements(),
                new HasArmorCategoryPredicate(armorCategory), armorList);

        return new Armors(armorList);
    }
}
