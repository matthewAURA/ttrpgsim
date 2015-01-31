package org.asciicerebrum.mydndgame.services.core.accumulator.observer;

import org.asciicerebrum.mydndgame.domain.core.mechanics.ObserverSource;
import org.asciicerebrum.mydndgame.domain.gameentities.BodySlot;
import org.asciicerebrum.mydndgame.domain.gameentities.UniqueEntity;
import org.asciicerebrum.mydndgame.observers.Observers;

/**
 *
 * @author species8472
 */
public class BodySlotObserverAccumulatorStrategy
        implements ObserverAccumulatorStrategy {

    /**
     * Accumulator strategy for the item inside the body slot.
     */
    private ObserverAccumulatorStrategy itemStrategy;

    @Override
    public final Observers getObservers(final ObserverSource observerSource,
            final UniqueEntity targetEntity) {
        final Observers observers = new Observers();
        final BodySlot bodySlot = (BodySlot) observerSource;

        observers.add(this.itemStrategy.getObservers(
                bodySlot.getItem(), targetEntity));

        return observers;
    }

    @Override
    public final boolean isApplicable(final ObserverSource observerSource) {
        return observerSource instanceof BodySlot;
    }

    /**
     * @param itemStrategyInput the itemStrategy to set
     */
    public final void setItemStrategy(
            final ObserverAccumulatorStrategy itemStrategyInput) {
        this.itemStrategy = itemStrategyInput;
    }

}