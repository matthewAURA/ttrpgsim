package org.asciicerebrum.mydndgame.services.core.accumulator.observer;

import org.asciicerebrum.mydndgame.domain.core.attribution.ClassLevel;
import org.asciicerebrum.mydndgame.domain.core.mechanics.ObserverSource;
import org.asciicerebrum.mydndgame.domain.gameentities.UniqueEntity;
import org.asciicerebrum.mydndgame.observers.Observers;

/**
 *
 * @author species8472
 */
public class ClassLevelObserverAccumulatorStrategy
        implements ObserverAccumulatorStrategy {

    /**
     * The class feats of the character class.
     */
    private ObserverAccumulatorStrategy characterClassStrategy;

    @Override
    public final Observers getObservers(final ObserverSource observerSource,
            final UniqueEntity targetEntity) {
        final ClassLevel classLevel = (ClassLevel) observerSource;
        final Observers observers = new Observers();

        observers.add(this.characterClassStrategy.getObservers(
                classLevel.getCharacterClass(), targetEntity));

        return observers;
    }

    @Override
    public final boolean isApplicable(final ObserverSource observerSource) {
        return observerSource instanceof ClassLevel;
    }

    /**
     * @param characterClassStrategyInput the characterClassStrategy to set
     */
    public final void setCharacterClassStrategy(
            final ObserverAccumulatorStrategy characterClassStrategyInput) {
        this.characterClassStrategy = characterClassStrategyInput;
    }

}