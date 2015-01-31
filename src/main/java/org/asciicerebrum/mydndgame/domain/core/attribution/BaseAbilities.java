package org.asciicerebrum.mydndgame.domain.core.attribution;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.asciicerebrum.mydndgame.domain.core.mechanics.Boni;
import org.asciicerebrum.mydndgame.domain.core.mechanics.BonusSource;
import org.asciicerebrum.mydndgame.domain.core.mechanics.BonusSources;
import org.asciicerebrum.mydndgame.domain.core.mechanics.ObserverSource;
import org.asciicerebrum.mydndgame.domain.core.particles.AbilityScore;

/**
 *
 * @author species8472
 */
public class BaseAbilities implements BonusSource, ObserverSource {

    private final Map<Ability, AbilityScore> elements
            = new HashMap<Ability, AbilityScore>();

    public final void addBaseAbilityEntry(final BaseAbilityEntry entry) {
        this.elements.put(entry.getAbility(), entry.getAbilityValue());
    }

    public final AbilityScore getValueForAbility(final Ability ability) {
        return this.elements.get(ability);
    }

    @Override
    public Boni getBoni() {
        return Boni.EMPTY_BONI;
    }

    @Override
    public BonusSources getBonusSources() {
        BonusSources bonusSources = new BonusSources();

        for (Ability ability : this.elements.keySet()) {
            bonusSources.add(ability);
        }

        return bonusSources;
    }

    public final Iterator<Ability> abilityIterator() {
        return this.elements.keySet().iterator();
    }

}