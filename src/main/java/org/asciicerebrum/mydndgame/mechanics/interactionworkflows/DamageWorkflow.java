package org.asciicerebrum.mydndgame.mechanics.interactionworkflows;

import org.asciicerebrum.mydndgame.domain.mechanics.workflow.IWorkflow;
import org.asciicerebrum.mydndgame.domain.core.particles.BonusValue;
import org.asciicerebrum.mydndgame.domain.core.particles.DiceRoll;
import org.asciicerebrum.mydndgame.domain.game.InventoryItem;
import org.asciicerebrum.mydndgame.domain.game.Weapon;
import org.asciicerebrum.mydndgame.domain.mechanics.damage.Damage;
import org.asciicerebrum.mydndgame.domain.mechanics.damage.Damages;
import org.asciicerebrum.mydndgame.domain.mechanics.workflow.Interaction;
import org.asciicerebrum.mydndgame.domain.ruleentities.composition.RollResult;
import org.asciicerebrum.mydndgame.facades.game.WeaponServiceFacade;
import org.asciicerebrum.mydndgame.mechanics.managers.RollResultManager;
import org.asciicerebrum.mydndgame.services.application.DamageApplicationService;
import org.asciicerebrum.mydndgame.services.context.SituationContextService;
import org.asciicerebrum.mydndgame.services.statistics.DamageCalculationService;

/**
 *
 * @author species8472
 */
public class DamageWorkflow implements IWorkflow {

    /**
     * Service for rolling dice.
     */
    private RollResultManager rollResultManager;

    /**
     * The damage application service.
     */
    private DamageApplicationService damageApplicationService;

    /**
     * The least damage you can do on a successful attack roll.
     */
    private DiceRoll minimumDamage;

    /**
     * The damage calculation service.
     */
    private DamageCalculationService damageService;

    /**
     * Getting settings from the character.
     */
    private SituationContextService situationContextService;

    /**
     * Getting modified real-time-values from the weapon.
     */
    private WeaponServiceFacade weaponServiceFacade;

    /**
     * {@inheritDoc} Applies damage on the character.
     */
    @Override
    public final void runWorkflow(final Interaction interaction) {

        final InventoryItem sourceWeapon
                = this.getSituationContextService().getActiveItem(interaction
                        .getTriggeringCharacter());

        if (!(sourceWeapon instanceof Weapon)) {
            return;
        }

        final BonusValue sourceDamageBonus
                = this.getDamageService().calcDamageBonus((Weapon) sourceWeapon,
                        interaction.getTriggeringCharacter());

        // make one damage roll
        final RollResult damageRollResult
                = this.getRollResultManager().retrieveRollResult(
                        sourceDamageBonus,
                        this.getWeaponServiceFacade().getDamage(
                                (Weapon) sourceWeapon,
                                interaction.getTriggeringCharacter()),
                        sourceWeapon,
                        interaction.getTriggeringCharacter(),
                        null,
                        interaction.getCombatRound().getCurrentDate(),
                        interaction.getCampaign());

        // consider minimum damage!
        final DiceRoll totalSourceDamage
                = DiceRoll.max(this.getMinimumDamage(),
                        damageRollResult.calcTotalResult());

        // apply damage: this must be done at the character (special method for
        // applying damage) because special attributes like damage reduction
        // could apply! (character has observers for that.)
        //TODO consider possible sneak attack of rogue, etc.
        //TODO an attack could inflict multiple types of damage,
        // e.g. +1d6 poison, etc. So we deliver a list of damages.
        Damage sourceDamage = new Damage();
        sourceDamage.setDamageValue(totalSourceDamage);
        sourceDamage.setWeapon((Weapon) sourceWeapon);
        sourceDamage.setDamageType(this.getSituationContextService()
                .getItemDamageType(sourceWeapon.getUniqueId(),
                        interaction.getTriggeringCharacter()));

        this.getDamageApplicationService().applyDamage(
                interaction.getFirstTargetCharacter(),
                new Damages(sourceDamage));

        // set conditions, e.g. unconscious --> this is done in the character!
    }

    /**
     * @param minimumDamageInput the minimumDamage to set
     */
    public final void setMinimumDamage(final DiceRoll minimumDamageInput) {
        this.minimumDamage = minimumDamageInput;
    }

    /**
     * @param situationContextServiceInput the situationContextService to set
     */
    public final void setSituationContextService(
            final SituationContextService situationContextServiceInput) {
        this.situationContextService = situationContextServiceInput;
    }

    /**
     * @param damageApplicationServiceInput the damageApplicationService to set
     */
    public final void setDamageApplicationService(
            final DamageApplicationService damageApplicationServiceInput) {
        this.damageApplicationService = damageApplicationServiceInput;
    }

    /**
     * @param damageServiceInput the damageService to set
     */
    public final void setDamageService(
            final DamageCalculationService damageServiceInput) {
        this.damageService = damageServiceInput;
    }

    /**
     * @param weaponServiceFacadeInput the weaponServiceFacade to set
     */
    public final void setWeaponServiceFacade(
            final WeaponServiceFacade weaponServiceFacadeInput) {
        this.weaponServiceFacade = weaponServiceFacadeInput;
    }

    /**
     * @return the damageApplicationService
     */
    public final DamageApplicationService getDamageApplicationService() {
        return damageApplicationService;
    }

    /**
     * @return the minimumDamage
     */
    public final DiceRoll getMinimumDamage() {
        return minimumDamage;
    }

    /**
     * @return the damageService
     */
    public final DamageCalculationService getDamageService() {
        return damageService;
    }

    /**
     * @return the situationContextService
     */
    public final SituationContextService getSituationContextService() {
        return situationContextService;
    }

    /**
     * @return the weaponServiceFacade
     */
    public final WeaponServiceFacade getWeaponServiceFacade() {
        return weaponServiceFacade;
    }

    /**
     * @return the rollResultManager
     */
    public final RollResultManager getRollResultManager() {
        return rollResultManager;
    }

    /**
     * @param rollResultManagerInput the rollResultManager to set
     */
    public final void setRollResultManager(
            final RollResultManager rollResultManagerInput) {
        this.rollResultManager = rollResultManagerInput;
    }

}
