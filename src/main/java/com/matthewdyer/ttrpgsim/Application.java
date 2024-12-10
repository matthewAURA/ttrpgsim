package com.matthewdyer.ttrpgsim;

import org.asciicerebrum.neocortexengine.domain.core.particles.UniqueId;
import org.asciicerebrum.neocortexengine.domain.game.DndCharacter;
import org.asciicerebrum.neocortexengine.domain.game.DndCharacters;
import org.asciicerebrum.neocortexengine.domain.mechanics.workflow.Interaction;
import org.asciicerebrum.neocortexengine.domain.mechanics.workflow.InteractionType;
import org.asciicerebrum.neocortexengine.domain.setup.CharacterSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

    }


    private static Interaction executeRandomInteraction(SimulatedCharacter attacker, SimulatedCharacter target) {
        var interaction = new Interaction();

        interaction.setTriggeringCharacter(attacker.character());
        final DndCharacters targetCharacters = DndCharacters.create(target.character());
        interaction.setTargetCharacters(targetCharacters);
        interaction.setInteractionType(target.interactions().getFirst());

        return interaction;
    }


    record SimulatedCharacter(DndCharacter character, List<InteractionType> interactions) {

    }

}
