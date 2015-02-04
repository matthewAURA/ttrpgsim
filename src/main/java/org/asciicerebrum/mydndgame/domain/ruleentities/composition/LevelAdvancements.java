package org.asciicerebrum.mydndgame.domain.ruleentities.composition;

import org.asciicerebrum.mydndgame.domain.ruleentities.FeatBindings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.asciicerebrum.mydndgame.domain.mechanics.bonus.Boni;
import org.asciicerebrum.mydndgame.domain.mechanics.bonus.source.BonusSource;
import org.asciicerebrum.mydndgame.domain.mechanics.bonus.source.BonusSources;
import org.asciicerebrum.mydndgame.domain.mechanics.observer.source.ObserverSource;
import org.asciicerebrum.mydndgame.domain.core.particles.AbilityScore;
import org.asciicerebrum.mydndgame.domain.core.particles.AdvancementNumber;
import org.asciicerebrum.mydndgame.domain.core.particles.HitPoints;
import org.asciicerebrum.mydndgame.domain.ruleentities.Ability;
import org.asciicerebrum.mydndgame.domain.ruleentities.ClassLevel;
import org.asciicerebrum.mydndgame.domain.ruleentities.FeatType;

/**
 *
 * @author species8472
 */
public class LevelAdvancements implements BonusSource, ObserverSource {

    private static class ClassLevelIterator implements Iterator<ClassLevel> {

        private final Iterator<LevelAdvancement> lvlAdvIterator;

        public ClassLevelIterator(
                final Iterator<LevelAdvancement> lvlAdvIteratorInput) {
            this.lvlAdvIterator = lvlAdvIteratorInput;
        }

        @Override
        public final boolean hasNext() {
            return this.lvlAdvIterator.hasNext();
        }

        @Override
        public final ClassLevel next() {
            return this.lvlAdvIterator.next().getClassLevel();
        }

        @Override
        public final void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private static class HpIterator implements Iterator<HitPoints> {

        private final Iterator<LevelAdvancement> lvlAdvIterator;

        public HpIterator(
                final Iterator<LevelAdvancement> lvlAdvIteratorInput) {
            this.lvlAdvIterator = lvlAdvIteratorInput;
        }

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
    }

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
        return new ClassLevelIterator(this.elements.iterator());
    }

    public final Iterator<HitPoints> hpIterator() {
        return new HpIterator(this.elements.iterator());
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
