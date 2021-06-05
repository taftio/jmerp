package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;

public class ObservantAbility extends SpecialAbilityBase {
    public String getName() {
        return "Observant";
    }

    public String getDescription() {
        return "Very observant: a special +10 bonus to Perception and "
            + "Track skills";
    }

    public void apply(MerpCharacter character) {
    }
}