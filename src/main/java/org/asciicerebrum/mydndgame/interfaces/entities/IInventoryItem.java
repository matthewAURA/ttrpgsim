package org.asciicerebrum.mydndgame.interfaces.entities;

import java.util.List;
import org.asciicerebrum.mydndgame.interfaces.observing.Observable;

/**
 *
 * @author Tabea Raab
 */
public interface IInventoryItem extends Identifiable, Observable {

    /**
     * @return the name
     */
    String getName();

    /**
     * @param name the name to set
     */
    void setName(String name);

    /**
     * @return the size
     */
    ISizeCategory getSize();

    /**
     * @param sizeCategory the size to set
     */
    void setSize(ISizeCategory sizeCategory);

    /**
     * @return the cost
     */
    Long getBaseCost();

    /**
     * @param baseCost the cost to set
     */
    void setBaseCost(Long baseCost);

    /**
     *
     * @return the effective price of the item, including special abilities like
     * mwk or magic, etc.
     */
    Long getCost();

    /**
     * @return the specialAbilities
     */
    List<ISpecialAbility> getSpecialAbilities();

    /**
     * @param specialAbilities the specialAbilities to set
     */
    void setSpecialAbilities(List<ISpecialAbility> specialAbilities);

    /**
     * Two items resemble each other if they have the same name.
     *
     * @param item the weapon to check the resemblance with.
     * @return true if they have the same name.
     */
    Boolean resembles(IInventoryItem item);

    /**
     * @return the observers
     */
    List<IObserver> getObservers();

    /**
     * @param observers the observers to set
     */
    void setObservers(List<IObserver> observers);

    /**
     * Adjust attributes of this weapon according to the new size. The prototype
     * is set up for a medium sized weapon.
     *
     * @param newSize the target size.
     */
    void adaptToSize(ISizeCategory newSize);

    /**
     * @return the designatedBodySlots
     */
    List<IBodySlotType> getDesignatedBodySlots();

    /**
     * @param designatedBodySlots the designatedBodySlots to set
     */
    void setDesignatedBodySlots(List<IBodySlotType> designatedBodySlots);

    /**
     * Determines if the wielding of this item is according to the
     * specifications given in the designatedBodySlots.
     *
     * @param bodySlotType the type of body slot this item is wielded in.
     * @return if it is designated for this slot.
     */
    Boolean isCorrectlyWielded(IBodySlotType bodySlotType);
}
