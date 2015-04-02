package org.asciicerebrum.mydndgame.integrationtests.pool.dndCharacters;

import java.util.ArrayList;
import org.asciicerebrum.mydndgame.domain.setup.BaseAbilityEntrySetup;
import org.asciicerebrum.mydndgame.domain.setup.CharacterSetup;
import org.asciicerebrum.mydndgame.domain.setup.EntitySetup;
import org.asciicerebrum.mydndgame.domain.setup.LevelAdvancementSetup;
import org.asciicerebrum.mydndgame.domain.setup.PersonalizedBodySlotSetup;

/**
 *
 * @author species8472
 */
public class HarskDwarfFighter2 {

    public static CharacterSetup getSetup() {
        final CharacterSetup harsk = new CharacterSetup();

        harsk.setId("harsk");
        harsk.setRace("dwarf");
        harsk.setCurrentXp("1000");
        harsk.setCurrentHp("18");

        final BaseAbilityEntrySetup strSetup = new BaseAbilityEntrySetup();
        strSetup.setAbility("str");
        strSetup.setAbilityValue("14");
        final BaseAbilityEntrySetup dexSetup = new BaseAbilityEntrySetup();
        dexSetup.setAbility("dex");
        dexSetup.setAbilityValue("15");
        final BaseAbilityEntrySetup conSetup = new BaseAbilityEntrySetup();
        conSetup.setAbility("con");
        conSetup.setAbilityValue("15");
        final BaseAbilityEntrySetup intSetup = new BaseAbilityEntrySetup();
        intSetup.setAbility("int");
        intSetup.setAbilityValue("10");
        final BaseAbilityEntrySetup wisSetup = new BaseAbilityEntrySetup();
        wisSetup.setAbility("wis");
        wisSetup.setAbilityValue("12");
        final BaseAbilityEntrySetup chaSetup = new BaseAbilityEntrySetup();
        chaSetup.setAbility("cha");
        chaSetup.setAbilityValue("6");

        harsk.setBaseAbilitySetups(new ArrayList<EntitySetup>() {
            {
                this.add(strSetup);
                this.add(dexSetup);
                this.add(conSetup);
                this.add(intSetup);
                this.add(wisSetup);
                this.add(chaSetup);
            }
        });

        final LevelAdvancementSetup fighter1Setup = new LevelAdvancementSetup();
        fighter1Setup.setAdvancementNumber("0");
        fighter1Setup.setClassLevel("fighter1");
        fighter1Setup.setHpAdvancement("0");
        final LevelAdvancementSetup fighter2Setup = new LevelAdvancementSetup();
        fighter2Setup.setAdvancementNumber("1");
        fighter2Setup.setClassLevel("fighter2");
        fighter2Setup.setHpAdvancement("6");

        harsk.setLevelAdvancementSetups(new ArrayList<EntitySetup>() {
            {
                this.add(fighter1Setup);
                this.add(fighter2Setup);
            }
        });

        final PersonalizedBodySlotSetup hand1Setup
                = new PersonalizedBodySlotSetup();
        hand1Setup.setBodySlotType("primaryHand");
        hand1Setup.setItem("standardBattleaxe");
        final PersonalizedBodySlotSetup hand2Setup
                = new PersonalizedBodySlotSetup();
        hand2Setup.setBodySlotType("secondaryHand");
        hand2Setup.setItem("standardLightWoodenShield");
        final PersonalizedBodySlotSetup torsoSetup
                = new PersonalizedBodySlotSetup();
        torsoSetup.setBodySlotType("torso");
        torsoSetup.setItem("standardStuddedLeather");

        harsk.setBodySlotSetups(new ArrayList<EntitySetup>() {
            {
                this.add(hand1Setup);
                this.add(hand2Setup);
                this.add(torsoSetup);
            }
        });

        return harsk;
    }

}