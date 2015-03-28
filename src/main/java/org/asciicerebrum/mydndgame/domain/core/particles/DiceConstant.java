package org.asciicerebrum.mydndgame.domain.core.particles;

/**
 *
 * @author species8472
 */
public class DiceConstant extends LongParticle {

    /**
     * Creates an instance from a long primitive.
     *
     * @param longInput the long to create the dice constant from.
     */
    public DiceConstant(final long longInput) {
        this.setValue(longInput);
    }

    @Override
    public final boolean equals(final Object o) {
        return this.equalsHelper(o);
    }

    @Override
    public final int hashCode() {
        return this.hashCodeHelper();
    }
}
