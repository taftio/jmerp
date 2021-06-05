package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;

public class ResistanceAbility extends SpecialAbilityBase {
    public void apply(MerpCharacter character) {
    }

    public String getName() {
        return "Resistance";
    }

    public String getDescription() {
        return "Resistance: a special +10 bonus to RRs against one type "
            + "of adversity, normally Essence spells, Channeling spells, "
            + "poisons, or diseases.";
    }
}