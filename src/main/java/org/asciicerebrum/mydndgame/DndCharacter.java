package org.asciicerebrum.mydndgame;

import org.asciicerebrum.mydndgame.interfaces.entities.ObserverHook;
import org.asciicerebrum.mydndgame.interfaces.services.BonusCalculationService;
import org.asciicerebrum.mydndgame.interfaces.entities.ICharacter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.asciicerebrum.mydndgame.interfaces.entities.BonusTarget;
import org.asciicerebrum.mydndgame.interfaces.entities.IAbility;
import org.asciicerebrum.mydndgame.interfaces.entities.IBodySlotType;
import org.asciicerebrum.mydndgame.interfaces.entities.IBonus;
import org.asciicerebrum.mydndgame.interfaces.entities.ICharacterSetup;
import org.asciicerebrum.mydndgame.interfaces.entities.IClass;
import org.asciicerebrum.mydndgame.interfaces.entities.IFeat;
import org.asciicerebrum.mydndgame.interfaces.entities.IInventoryItem;
import org.asciicerebrum.mydndgame.interfaces.entities.ILevel;
import org.asciicerebrum.mydndgame.interfaces.entities.IObserver;
import org.asciicerebrum.mydndgame.interfaces.entities.IRace;
import org.asciicerebrum.mydndgame.interfaces.entities.ISituationContext;
import org.asciicerebrum.mydndgame.interfaces.entities.IWeaponCategory;
import org.asciicerebrum.mydndgame.interfaces.entities.Slotable;
import org.asciicerebrum.mydndgame.interfaces.observing.Observable;
import org.asciicerebrum.mydndgame.interfaces.observing.ObservableDelegate;

/**
 *
 * @author species8472
 */
public final class DndCharacter implements ICharacter, Observable {

    /**
     * The setup for the character creation.
     */
    private ICharacterSetup setup;

    /**
     * The race of this dnd character.
     */
    @BonusGranter
    private IRace race;

    /**
     * The ordered list of classes this character advanced in.
     */
    @BonusGranter
    private List<IClass> classList
            = new ArrayList<IClass>();
    /**
     * The ordered list of additions to the hit points this character acquired
     * during class level advancements.
     */
    private List<Long> hpAdditionList
            = new ArrayList<Long>();
    /**
     * The map of abilities together with their original base values.
     */
    @BonusGranter
    private Map<IAbility, Long> baseAbilityMap
            = new HashMap<IAbility, Long>();
    /**
     * When a level advancement grants an increase in the ability score, this
     * ability is recorded in this list. Later on the effective score is
     * calculated by the original base value from the abilityMap and the number
     * of entries of the same ability within this list.
     */
    private List<IAbility> abilityAdvances = new ArrayList<IAbility>();
    /**
     * The ordered list of class levels this character advanced in.
     */
    @BonusGranter
    private List<ILevel> classLevels = new ArrayList<ILevel>();

    /**
     * The complete collection of all boni which are associated with this
     * character.
     */
    private List<IBonus> boni = new ArrayList<IBonus>();

    /**
     * The collection of base attack boni. This list MUST NOT BE mixed with the
     * normal boni list because these are special boni that do not participate
     * in the bonus accumulation process. So they are left out!!!
     */
    private List<IBonus> baseAtkBoni = new ArrayList<IBonus>();

    //TODO make DndCharacter Spring Prototype and set these values via
    // application context xml
    /**
     * The bonus calculation service needed for dynamic bonus value calculation.
     */
    private BonusCalculationService bonusService;
    /**
     * The diceAction with id hp.
     */
    private DiceAction hp;
    /**
     * The diceAction with id acAction.
     */
    private DiceAction acAction;
    /**
     * The diceAction with id attackAction.
     */
    private DiceAction attackAction;
    /**
     * The diceAction with id meleeAttackAction.
     */
    private DiceAction meleeAttackAction;
    /**
     * The diceAction with id rangedAttackAction.
     */
    private DiceAction rangedAttackAction;
    /**
     * The diceAction with id damage.
     */
    private DiceAction damageAction;
    /**
     * The bonusType with id baseAttackBonus.
     */
    private BonusType baseAttackBonus;
    /**
     * The list of specific body slots provided by the race of the dnd
     * character.
     */
    @BonusGranter
    private List<Slotable> bodySlots = new ArrayList<Slotable>();

    /**
     * The feats of a character.
     */
    private final List<IFeat> feats = new ArrayList<IFeat>();

    /**
     * Service for the handling of observable calls and registering into the
     * hooks.
     */
    private ObservableDelegate observableDelegate;

    /**
     * Holder for the observer hooks and their corresponding list of observers.
     */
    private Map<ObserverHook, List<IObserver>> observerMap
            = new EnumMap<ObserverHook, List<IObserver>>(ObserverHook.class);

    /**
     * The observers of this character. They are designed to be registered in
     * the targeted character to modify certain values of all kinds of
     * attributes.
     */
    private List<IObserver> observers = new ArrayList<IObserver>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Slotable getBodySlotByType(final IBodySlotType bsType) {
        for (Slotable bSlot : this.bodySlots) {
            if (bSlot.getBodySlotType().equals(bsType)) {
                return bSlot;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getMeleeAtkBonus(final IBodySlotType bodySlotType) {
        // TODO get body slot by body slot type
        // is there a weapon in this slot?
        // is it ranged or melee? - no if-construct here. let the weapon
        // category decide and associate the bonus to it (Dex for ranged, Str
        // for melee)
        // collect all boni associated with attack
        // collect all boni associated with meleeattack/rangedattack, depending
        // on the weapon category in this slot
        // IMPORTANT: consider only this weapon's boni as the other weapon's
        // boni are irrelevant in this case!!!

        // KEEP THE FOLLOWING USE CASES IN MIND:
        // 1st: Feat Weapon Finesse: With a light weapon, rapier, whip, or
        // spiked chain made for a creature of your size category, you may use
        // your Dexterity modifier instead of your Strength modifier on attack
        // rolls. If you carry a shield, its armor check penalty applies to
        // your attack rolls.
        //
        // 2nd: Feat Power Attack: On your action, before making attack rolls
        // for a round, you may choose to subtract a number from all melee
        // attack rolls and add the same number to all melee damage rolls. This
        // number may not exceed your base attack bonus. The penalty on attacks
        // and bonus on damage apply until your next turn.
        //
        // 3rd: Masterwork weapon: Wielding it provides a +1 enhancement bonus
        // on attack rolls. This also affects the price of the weapon!
        //
        // 4th: a thrown dagger in your secondary hand (which is actually a
        // melee weapon). I could also hit someone with a bow. Could this be
        // solved by differentiating two methods: getMeleeAtkBonus() /
        // getRangedAtkBonus() and I as a player have to decide what to do with
        // the weapon in my hand (attack in melee or ranged)?
        // Sometimes objects not crafted to be weapons nonetheless see use in
        // combat. Because such objects are not designed for this use, any
        // creature that uses one in combat is considered to be nonproficient
        // with it and takes a -4 penalty on attack rolls made with that object.
        return this.getGenericAtkBonus(bodySlotType, this.meleeAttackAction,
                ObserverHook.MELEE_ATTACK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getRangedAtkBonus(final IBodySlotType bodySlotType) {
        return this.getGenericAtkBonus(bodySlotType, this.rangedAttackAction,
                ObserverHook.RANGED_ATTACK);
    }

    /**
     * Generates a specific situation context by this character and all relevant
     * active entities.
     *
     * @param bsType the body slot type important for context.
     * @param attackMode how the attack is executed, melee or ranged.
     * @return the created situation context.
     */
    private ISituationContext generateSituationContext(
            final IBodySlotType bsType, final IWeaponCategory attackMode) {
        ISituationContext sitCon = new SituationContext();
        sitCon.setCharacter(this);
        sitCon.setBodySlotType(bsType);
        sitCon.setAttackMode(attackMode);
        return sitCon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISituationContext generateSituationContextSimple() {
        return this.generateSituationContext(null, null);
    }

    /**
     * Generic method to retrieve the attack boni for a certain type of attack
     * (melee or ranged).
     *
     * @param bodySlotType de facto the wielded weapon to attack with.
     * @param bonusTarget melee or ranged attack.
     * @param observerHook melee or ranged attack.
     * @return the list of attack boni with that weapon in that mode.
     */
    private List<Long> getGenericAtkBonus(final IBodySlotType bodySlotType,
            final BonusTarget bonusTarget, final ObserverHook observerHook) {
        List<Long> atkBoni = new ArrayList<Long>();

        // gather all non-weapon-dependent boni for melee/ranged attack + attack
        List<IBonus> genericBoni = this.bonusService
                .traverseBoniByTarget(this, bonusTarget);
        genericBoni.addAll(this.bonusService
                .traverseBoniByTarget(this, this.attackAction));

        IInventoryItem item = this.getBodySlotByType(bodySlotType).getItem();
        genericBoni.addAll(this.bonusService.traverseBoniByTarget(
                item, this.attackAction));

        // post processing of the bonus list, e.g. by registered feat
        // methods. (observer pattern)
        ISituationContext attackSitCon
                = this.generateSituationContext(bodySlotType,
                        bonusTarget.getAssociatedAttackMode());
        genericBoni = (List<IBonus>) this.getObservableDelegate()
                .triggerObservers(
                        observerHook, genericBoni, this.getObserverMap(),
                        attackSitCon);
        genericBoni = (List<IBonus>) this.getObservableDelegate()
                .triggerObservers(
                        ObserverHook.ATTACK, genericBoni,
                        this.getObserverMap(),
                        attackSitCon);

        Long bonusValue = this.bonusService.accumulateBonusValue(attackSitCon,
                genericBoni);

        for (IBonus baseAtkBonus : this.getBaseAtkBoni()) {
            atkBoni.add(baseAtkBonus.getEffectiveValue(attackSitCon)
                    + bonusValue);
        }

        return atkBoni;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer countClassLevelsByCharacterClass(final IClass charCl) {

        return Collections.frequency(this.getClassList(), charCl);
    }

    /**
     * The list is rank-ordered from low to high. Its size is equal to the max
     * attack number.
     *
     * @return the list of base attack boni for this character.
     */
    @Override
    public List<IBonus> getBaseAtkBoni() {

        Long maxSize = this.getMaxAttackNumber();

        // order the list
        Collections.sort(this.baseAtkBoni, new Bonus.RankComparator());

        // make a sublist of size maxSize
        return this.baseAtkBoni.subList(0, maxSize.intValue());
    }

    /**
     * @return the classLevels
     */
    @Override
    public List<ILevel> getClassLevels() {
        return classLevels;
    }

    /**
     * @param classLevelsInput the classLevels to set
     */
    @Override
    public void setClassLevels(final List<ILevel> classLevelsInput) {
        this.classLevels = classLevelsInput;
    }

    /**
     * @return the boni
     */
    @Override
    public List<IBonus> getBoni() {
        return boni;
    }

    /**
     * @param boniInput the boni to set
     */
    @Override
    public void setBoni(final List<IBonus> boniInput) {
        this.boni = boniInput;
    }

    /**
     * @return the acAction
     */
    @Override
    public DiceAction getAcAction() {
        return acAction;
    }

    /**
     * @return the attackAction
     */
    @Override
    public DiceAction getAttackAction() {
        return attackAction;
    }

    /**
     * @return the maxHp
     */
    @Override
    public Long getMaxHp() {

        Long maxHp = 0L;

        for (int i = 0; i < this.getClassList().size(); i++) {
            // in case of null use max hp addition, defined by
            // the class' hitdice
            if (this.getHpAdditionList().get(i) != null) {
                maxHp += this.getHpAdditionList().get(i);
            } else {
                maxHp += this.getClassList().get(i).getHitDice().getSides();
            }

        }
        // hp add con-modifier for each class level
        maxHp += this.getClassList().size()
                * this.bonusService.retrieveEffectiveBonusValueByTarget(
                        this.generateSituationContextSimple(), this, this.hp);

        return maxHp;
    }

    /**
     *
     * @return the calculated armor class of this character.
     */
    @Override
    public Long getAc() {
        return this.acAction.getConstValue()
                + this.bonusService.retrieveEffectiveBonusValueByTarget(
                        this.generateSituationContextSimple(), this,
                        this.acAction);
    }

    /**
     *
     * @return the hp dice action.
     */
    @Override
    public DiceAction getHp() {
        return hp;
    }

    /**
     * @return the baseAttackBonus
     */
    @Override
    public BonusType getBaseAttackBonus() {
        return baseAttackBonus;
    }

    /**
     * @return the classList
     */
    @Override
    public List<IClass> getClassList() {
        return classList;
    }

    /**
     * @param classListInput the classList to set
     */
    @Override
    public void setClassList(final List<IClass> classListInput) {
        this.classList = classListInput;
    }

    /**
     * @return the hpAdditionList
     */
    @Override
    public List<Long> getHpAdditionList() {
        return hpAdditionList;
    }

    /**
     * @param hpAdditionListInput the hpAdditionList to set
     */
    @Override
    public void setHpAdditionList(final List<Long> hpAdditionListInput) {
        this.hpAdditionList = hpAdditionListInput;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Slotable> getBodySlots() {
        return bodySlots;
    }

    /**
     * @param bodySlotsInput the bodySlots to set
     */
    public void setBodySlots(final List<Slotable> bodySlotsInput) {
        this.bodySlots = bodySlotsInput;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IFeat> getFeats() {
        return feats;
    }

    /**
     * @param bonusServiceInput the bonusService to set
     */
    public void setBonusService(
            final BonusCalculationService bonusServiceInput) {
        this.bonusService = bonusServiceInput;
    }

    /**
     * @param hpInput the hp to set
     */
    public void setHp(final DiceAction hpInput) {
        this.hp = hpInput;
    }

    /**
     * @param acActionInput the acAction to set
     */
    public void setAcAction(final DiceAction acActionInput) {
        this.acAction = acActionInput;
    }

    /**
     * @param attackActionInput the attackAction to set
     */
    public void setAttackAction(final DiceAction attackActionInput) {
        this.attackAction = attackActionInput;
    }

    /**
     * @param meleeAttackActionInput the meleeAttackAction to set
     */
    public void setMeleeAttackAction(final DiceAction meleeAttackActionInput) {
        this.meleeAttackAction = meleeAttackActionInput;
    }

    /**
     * @param baseAttackBonusInput the baseAttackBonus to set
     */
    public void setBaseAttackBonus(final BonusType baseAttackBonusInput) {
        this.baseAttackBonus = baseAttackBonusInput;
    }

    /**
     * @return the rangedAttackAction
     */
    public DiceAction getRangedAttackAction() {
        return rangedAttackAction;
    }

    /**
     * @param rangedAttackActionInput the rangedAttackAction to set
     */
    public void setRangedAttackAction(
            final DiceAction rangedAttackActionInput) {
        this.rangedAttackAction = rangedAttackActionInput;
    }

    /**
     * @return the setup
     */
    @Override
    public ICharacterSetup getSetup() {
        return setup;
    }

    /**
     * @return the race
     */
    @Override
    public IRace getRace() {
        return race;
    }

    /**
     * @param raceInput the race to set
     */
    @Override
    public void setRace(final IRace raceInput) {
        this.race = raceInput;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSetup(final ICharacterSetup setupInput) {
        this.setup = setupInput;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getMaxAttackNumber() {
        Long maxAttackNumber = 0L;
        for (ILevel cLevel : this.classLevels) {
            if (cLevel.getBaseAtkBoni().size() > maxAttackNumber) {
                maxAttackNumber = Long.valueOf(cLevel.getBaseAtkBoni().size());
            }
        }
        return maxAttackNumber;
    }

    /**
     * @return the baseAbilityMap
     */
    public Map<IAbility, Long> getBaseAbilityMap() {
        return baseAbilityMap;
    }

    /**
     * @param baseAbilityMapInput the baseAbilityMap to set
     */
    public void setBaseAbilityMap(
            final Map<IAbility, Long> baseAbilityMapInput) {
        this.baseAbilityMap = baseAbilityMapInput;
    }

    /**
     * @return the abilityAdvances
     */
    public List<IAbility> getAbilityAdvances() {
        return abilityAdvances;
    }

    /**
     * @param abilityAdvancesInput the abilityAdvances to set
     */
    public void setAbilityAdvances(final List<IAbility> abilityAdvancesInput) {
        this.abilityAdvances = abilityAdvancesInput;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObservableDelegate getObservableDelegate() {
        return observableDelegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObservableDelegate(
            final ObservableDelegate observableDelegateInput) {
        this.observableDelegate = observableDelegateInput;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<ObserverHook, List<IObserver>> getObserverMap() {
        return observerMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObserverMap(
            final Map<ObserverHook, List<IObserver>> observerMapInput) {
        this.observerMap = observerMapInput;
    }

    /**
     * @param baseAtkBoniInput the baseAtkBoni to set
     */
    public void setBaseAtkBoni(final List<IBonus> baseAtkBoniInput) {
        this.baseAtkBoni = baseAtkBoniInput;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    //TODO there must be a melee and a ranged version because, for example,
    // attacking with a bow in ranged mode gives other damage boni as hitting
    // someone with it.
    public Long getMeleeDamageBonus(final IBodySlotType bodySlotType) {
        return this.getDamageBonus(bodySlotType,
                this.meleeAttackAction.getAssociatedAttackMode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getRangedDamageBonus(final IBodySlotType bodySlotType) {
        return this.getDamageBonus(bodySlotType,
                this.rangedAttackAction.getAssociatedAttackMode());
    }

    /**
     * Calculates the damgage bonus in a generic way. The attack mode is
     * important because you can hit someone with a bow in melee mode. Then the
     * str bonus applies, even if it is positive!
     *
     * @param bodySlotType the body slot to take damage specific boni into
     * account.
     * @param attackMode the mode of attack (ranged or melee).
     * @return the calculated bonus value.
     */
    private Long getDamageBonus(final IBodySlotType bodySlotType,
            final IWeaponCategory attackMode) {

        // non-weapon dependent stuff
        List<IBonus> genericBoni = this.bonusService
                .traverseBoniByTarget(this, this.getDamageAction());

        // weapon-dependent stuff
        IInventoryItem item = this.getBodySlotByType(bodySlotType).getItem();
        genericBoni.addAll(this.bonusService.traverseBoniByTarget(
                item, this.getDamageAction()));

        // post-processing with observers
        ISituationContext attackSitCon
                = this.generateSituationContext(bodySlotType, attackMode);
        genericBoni = (List<IBonus>) this.getObservableDelegate()
                .triggerObservers(
                        ObserverHook.DAMAGE, genericBoni, this.getObserverMap(),
                        attackSitCon);
        // post-processing with item/weapon observers
        genericBoni = (List<IBonus>) item.getObservableDelegate()
                .triggerObservers(
                        ObserverHook.DAMAGE, genericBoni, item.getObserverMap(),
                        attackSitCon);

        Long bonusValue = this.bonusService.accumulateBonusValue(
                attackSitCon, genericBoni);

        return bonusValue;
    }

    /**
     * @return the damageAction
     */
    public DiceAction getDamageAction() {
        return damageAction;
    }

    /**
     * @param damageActionInput the damageAction to set
     */
    public void setDamageAction(final DiceAction damageActionInput) {
        this.damageAction = damageActionInput;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<IObserver> getObservers() {
        return observers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setObservers(final List<IObserver> observersInput) {
        this.observers = observersInput;
    }
}
