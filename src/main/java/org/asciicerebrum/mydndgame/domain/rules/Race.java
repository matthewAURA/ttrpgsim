package org.asciicerebrum.mydndgame.domain.rules;

import org.asciicerebrum.mydndgame.domain.mechanics.interfaces.Boni;
import org.asciicerebrum.mydndgame.domain.mechanics.interfaces.BonusSource;
import org.asciicerebrum.mydndgame.domain.mechanics.interfaces.BonusSources;
import org.asciicerebrum.mydndgame.domain.mechanics.ObserverSource;
import org.asciicerebrum.mydndgame.domain.core.particles.UniqueId;

/**
 *
 * @author species8472
 */
public class Race implements BonusSource, ObserverSource {

    /**
     * The unique id of this race.
     */
    private UniqueId uniqueId;

    /**
     * The sizeCategory which is associated with this race.
     */
    private SizeCategory size;

    /**
     * All the body slots this race is providing. The structure of body slots
     * has to be cloned (deep copy) when a new character is created and the copy
     * has to be put into the dnd character object.
     */
    private BodySlots bodySlotBluePrint;

    public final SizeCategory getSize() {
        return size;
    }

    public final void setSize(final SizeCategory sizeInput) {
        this.size = sizeInput;
    }

    /**
     * @return the uniqueId
     */
    public final UniqueId getUniqueId() {
        return uniqueId;
    }

    /**
     * @param uniqueIdInput the uniqueId to set
     */
    public final void setUniqueId(final UniqueId uniqueIdInput) {
        this.uniqueId = uniqueIdInput;
    }

    @Override
    public final Boni getBoni() {
        return Boni.EMPTY_BONI;
    }

    @Override
    public final BonusSources getBonusSources() {
        final BonusSources bonusSources = new BonusSources();

        bonusSources.add(this.size);

        return bonusSources;
    }

    /**
     * @param bodySlotBluePrintInput the bodySlotBluePrint to set
     */
    public final void setBodySlotBluePrint(
            final BodySlots bodySlotBluePrintInput) {
        this.bodySlotBluePrint = bodySlotBluePrintInput;
    }

    /**
     * @return the bodySlotBluePrint
     */
    public final BodySlots getBodySlotBluePrint() {
        return bodySlotBluePrint;
    }

}
