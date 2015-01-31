package org.asciicerebrum.mydndgame.domain.gameentities;

/**
 * Builder for a unique weapon from the prototype.
 *
 * @author species8472
 */
public class WeaponFactory extends InventoryItemFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    protected InventoryItem getConcreteInventoryItem() {

        return this.getContext().getBean(Weapon.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void finalizeCreation(InventoryItem inventoryItem) {
        // so far nothing to do here.
    }
}