package org.asciicerebrum.mydndgame.domain.rules.composition;

import org.asciicerebrum.mydndgame.domain.rules.entities.FeatBindings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.asciicerebrum.mydndgame.domain.mechanics.entities.Boni;
import org.asciicerebrum.mydndgame.domain.mechanics.entities.BonusSource;
import org.asciicerebrum.mydndgame.domain.mechanics.entities.BonusSources;
import org.asciicerebrum.mydndgame.domain.mechanics.entities.ObserverSource;
import org.asciicerebrum.mydndgame.domain.core.particles.AbilityScore;
import org.asciicerebrum.mydndgame.domain.core.particles.AdvancementNumber;
import org.asciicerebrum.mydndgame.domain.core.particles.HitPoints;
import org.asciicerebrum.mydndgame.domain.rules.entities.Ability;
import org.asciicerebrum.mydndgame.domain.rules.entities.ClassLevel;
import org.asciicerebrum.mydndgame.domain.rules.entities.FeatType;

/**
 *
 * @author species8472
 */
public class LevelAdvancements implements BonusSource, ObserverSource {

    private final List<LevelAdvancement> elements
            = new ArrayList<LevelAdvancement>();

    public final void add(final LevelAdvancement levelAdvancement) {
        this.elements.add(levelAdvancement);
    }

    @Override
    public final Boni getBoni() {
        return Boni.EMPTY_BONI;
    }

    @Override
    public final BonusSources getBonusSources() {
        BonusSources bonusSources = new BonusSources();

        for (LevelAdvancement levelAdvancement : this.elements) {
            bonusSources.add(levelAdvancement);
        }

        return bonusSources;
    }

    public final Iterator<LevelAdvancement> iterator() {
        return this.elements.iterator();
    }

    public final Iterator<ClassLevel> classLevelIterator() {
        final Iterator<LevelAdvancement> lvlAdvIterator = this.iterator();
        return new Iterator<ClassLevel>() {

            @Override
            public final boolean hasNext() {
                return lvlAdvIterator.hasNext();
            }

            @Override
            public final ClassLevel next() {
                return lvlAdvIterator.next().getClassLevel();
            }

            @Override
            public final void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public final Iterator<HitPoints> hpIterator() {
        final Iterator<LevelAdvancement> lvlAdvIterator = this.iterator();
        return new Iterator<HitPoints>() {

            @Override
            public final boolean hasNext() {
                return lvlAdvIterator.hasNext();
            }

            @Override
            public final HitPoints next() {
                return lvlAdvIterator.next().getHpAdvancement();
            }

            @Override
            public final void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public final AbilityScore countAbility(final Ability ability) {
        long count = 0;
        for (final LevelAdvancement lvlAdv : this.elements) {
            if (ability.equals(lvlAdv.getAbilityAdvancement())) {
                count++;
            }
        }
        return new AbilityScore(count);
    }

    public final LevelAdvancement getLevelAdvancementByNumber(
            final AdvancementNumber advNo) {
        for (final LevelAdvancement lvlAdv : this.elements) {
            if (advNo.equals(lvlAdv.getAdvNumber())) {
                return lvlAdv;
            }
        }
        return null;
    }

    public final FeatBindings getFeatBindingsByFeatType(
            final FeatType featType) {

        final FeatBindings featBindings = new FeatBindings();

        for (final LevelAdvancement lvlAdv : this.elements) {
            if (lvlAdv.hasFeatType(featType)) {
                featBindings.add(lvlAdv.getFeatAdvancement().getFeatBinding());
            }
        }

        return featBindings;
    }

}
