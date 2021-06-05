package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;

public class MovingManeuversAbility extends SpecialAbilityBase {
    public void apply(MerpCharacter character) {
    }

    public String getName() {
        return "Adept Moving Maneuvers";
    }

    public String getDescription() {
        return "Adept at moving maneuvers: a special + 10 bonus to all "
            + "moving maneuvers.";
    }
}