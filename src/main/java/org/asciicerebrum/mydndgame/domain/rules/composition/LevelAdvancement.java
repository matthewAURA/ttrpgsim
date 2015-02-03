package org.asciicerebrum.mydndgame.domain.rules.composition;

import org.asciicerebrum.mydndgame.domain.rules.Feat;
import org.asciicerebrum.mydndgame.domain.rules.FeatType;
import org.asciicerebrum.mydndgame.domain.rules.Ability;
import org.asciicerebrum.mydndgame.domain.rules.ClassLevel;
import org.asciicerebrum.mydndgame.domain.mechanics.interfaces.Boni;
import org.asciicerebrum.mydndgame.domain.mechanics.interfaces.BonusSource;
import org.asciicerebrum.mydndgame.domain.mechanics.interfaces.BonusSources;
import org.asciicerebrum.mydndgame.domain.mechanics.ObserverSource;
import org.asciicerebrum.mydndgame.domain.core.particles.AdvancementNumber;
import org.asciicerebrum.mydndgame.domain.core.particles.HitPoints;

/**
 *
 * @author species8472
 */
public class LevelAdvancement implements BonusSource, ObserverSource {

    private AdvancementNumber advNumber;

    private ClassLevel classLevel;

    private HitPoints hpAdvancement;

    private Ability abilityAdvancement;

    /**
     * Feat type plus its binding to a special entity.
     */
    private Feat featAdvancement;

    /**
     * @return the advNumber
     */
    public final AdvancementNumber getAdvNumber() {
        return advNumber;
    }

    /**
     * @param advNumberInput the advNumber to set
     */
    public final void setAdvNumber(final AdvancementNumber advNumberInput) {
        this.advNumber = advNumberInput;
    }

    /**
     * @return the classLevel
     */
    public final ClassLevel getClassLevel() {
        return classLevel;
    }

    /**
     * @param classLevelInput the classLevel to set
     */
    public final void setClassLevel(final ClassLevel classLevelInput) {
        this.classLevel = classLevelInput;
    }

    /**
     * @return the hpAdvancement
     */
    public final HitPoints getHpAdvancement() {
        return hpAdvancement;
    }

    /**
     * @param hpAdvancementInput the hpAdvancement to set
     */
    public final void setHpAdvancement(final HitPoints hpAdvancementInput) {
        this.hpAdvancement = hpAdvancementInput;
    }

    /**
     * @return the abilityAdvancement
     */
    public final Ability getAbilityAdvancement() {
        return abilityAdvancement;
    }

    /**
     * @param abilityAdvancementInput the abilityAdvancement to set
     */
    public final void setAbilityAdvancement(
            final Ability abilityAdvancementInput) {
        this.abilityAdvancement = abilityAdvancementInput;
    }

    /**
     * @return the featAdvancement
     */
    public final Feat getFeatAdvancement() {
        return featAdvancement;
    }

    /**
     * @param featAdvancementInput the featAdvancement to set
     */
    public final void setFeatAdvancement(final Feat featAdvancementInput) {
        this.featAdvancement = featAdvancementInput;
    }

    public final boolean hasFeatType(final FeatType featType) {
        return this.getFeatAdvancement().getFeatType().equals(featType);
    }

    @Override
    public final Boni getBoni() {
        return Boni.EMPTY_BONI;
    }

    @Override
    public final BonusSources getBonusSources() {
        final BonusSources bonusSources = new BonusSources();

        bonusSources.add(this.classLevel);
        bonusSources.add(this.featAdvancement);

        return bonusSources;
    }

}
