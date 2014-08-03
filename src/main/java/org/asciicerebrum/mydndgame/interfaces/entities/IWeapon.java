package org.asciicerebrum.mydndgame.interfaces.entities;

/**
 *
 * @author species8472
 */
public interface IWeapon extends IInventoryItem, Identifiable {

    /**
     * @return the encumbrance
     */
    IEncumbrance getEncumbrance();

}
