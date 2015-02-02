package org.asciicerebrum.mydndgame.services.core.accumulator.observer;

import org.asciicerebrum.mydndgame.domain.mechanics.entities.ObserverSource;
import org.asciicerebrum.mydndgame.domain.core.UniqueEntity;
import org.asciicerebrum.mydndgame.domain.mechanics.entities.Observers;

/**
 *
 * @author species8472
 */
public interface ObserverAccumulatorStrategy {

    public Observers getObservers(ObserverSource observerSource,
            UniqueEntity targetEntity);

    boolean isApplicable(ObserverSource observerSource);

}
