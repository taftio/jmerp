package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;

public class CharismaticAbility extends SpecialAbilityBase {
    public void apply(MerpCharacter character) {
    }

    public String getName() {
        return "Charismatic";
    }

    public String getDescription() {
        return "Charismatic: a special +10 bonus to all Influence Skills.";
    }
}