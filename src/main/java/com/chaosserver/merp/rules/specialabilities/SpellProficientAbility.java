package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;

public class SpellProficientAbility extends SpecialAbilityBase {
    public void apply(MerpCharacter character) {
    }

    public String getName() {
        return "Spell Proficient";
    }

    public String getDescription() {
        return "Proficient with spells: start having learned one extra "
            + "spell list (this background option may one be obtained "
            + "once).  The type of spell list is still limited by "
            + "profession and race.";
    }
}