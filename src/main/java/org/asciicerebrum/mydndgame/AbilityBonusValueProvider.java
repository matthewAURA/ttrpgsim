package org.asciicerebrum.mydndgame;

/**
 *
 * @author species8472
 */
public class AbilityBonusValueProvider implements BonusValueProvider {

    /**
     * Offset for calculating the ability bonus from its score.
     */
    private static final Integer ABILITY_BONUS_OFFSET = 10;

    /**
     * The associated ability for this value provider.
     */
    private Ability ability;

    /**
     *
     * @param dndCharacter The character as the context for calculating the
     * bonus of the given ability.
     * @return the dynamically calculated bonus value of the given ability -
     * depending on the character.
     */
    public final Long getDynamicValue(final DndCharacter dndCharacter) {

        //TODO collect all boni/mali with this ability as target
        // e.g. sicknesses can give a -4 malus on Constitution.
        // or some potions can grant a +4 bonus on Dexterity (Cat's Grace)
        final Long abilityScore
                = dndCharacter.getAbilityMap().get(this.ability);
        return this.calculateAbilityMod(abilityScore);
    }

    /**
     *
     * @param score The ability score to calculate the bonus for.
     * @return The calculated ability bonus.
     */
    private Long calculateAbilityMod(final Long score) {
        return Math.round(Math.floor((score - ABILITY_BONUS_OFFSET) / 2.0));
    }

    /**
     * @return the ability
     */
    public final Ability getAbility() {
        return ability;
    }

    /**
     * @param abilityInput the ability to set
     */
    public final void setAbility(final Ability abilityInput) {
        this.ability = abilityInput;
    }

}
