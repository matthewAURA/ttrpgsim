package org.asciicerebrum.mydndgame.domain.mechanics.entities;

import org.asciicerebrum.mydndgame.domain.rules.entities.WeaponCategory;

/**
 *
 * @author species8472
 */
public interface BonusTarget {

    /**
     * @return the associatedAttackMode
     */
    WeaponCategory getAssociatedAttackMode();

    /**
     * @param associatedAttackMode the associatedAttackMode to set
     */
    void setAssociatedAttackMode(WeaponCategory associatedAttackMode);

    /**
     * @return the associatedHook
     */
    ObserverHook getAssociatedHook();

    /**
     * @param associatedHook the associatedHook to set
     */
    void setAssociatedHook(ObserverHook associatedHook);

}