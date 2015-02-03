package org.asciicerebrum.mydndgame.domain.rules;

import org.asciicerebrum.mydndgame.domain.mechanics.bonus.Boni;
import org.asciicerebrum.mydndgame.domain.mechanics.bonus.source.BonusSource;
import org.asciicerebrum.mydndgame.domain.mechanics.bonus.source.BonusSources;
import org.asciicerebrum.mydndgame.domain.mechanics.observer.source.ObserverSource;

/**
 *
 * @author species8472
 */
public class Feat implements BonusSource, ObserverSource {

    /**
     * The feat itself.
     */
    private FeatType featType;

    /**
     * The object this feat is bound to. Only needed for special feats which are
     * restricted to be bound to one type of object only. E.g. Weapon Focus,
     * which is bound to one type of weapon.
     */
    private FeatBinding featBinding;

    /**
     * @return the featType
     */
    public final FeatType getFeatType() {
        return featType;
    }

    /**
     * @param featTypeInput the featType to set
     */
    public final void setFeatType(final FeatType featTypeInput) {
        this.featType = featTypeInput;
    }

    /**
     * @return the featBinding
     */
    public final FeatBinding getFeatBinding() {
        return featBinding;
    }

    /**
     * @param featBindingInput the featBinding to set
     */
    public final void setFeatBinding(final FeatBinding featBindingInput) {
        this.featBinding = featBindingInput;
    }

    @Override
    public final Boni getBoni() {
        return Boni.EMPTY_BONI;
    }

    @Override
    public final BonusSources getBonusSources() {
        final BonusSources bonusSources = new BonusSources();

        bonusSources.add(this.featType);

        return bonusSources;
    }

}
