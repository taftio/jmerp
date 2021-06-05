package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;

public class PainResistanceAbility extends SpecialAbilityBase {
    public void apply(MerpCharacter character) {
    }

    public String getName() {
        return "Pain Resistance";
    }

    public String getDescription() {
        return "Resistant to pain: +3 to each D120 roll for concussion "
            + "hits from Body Development skill development.";
    }
}