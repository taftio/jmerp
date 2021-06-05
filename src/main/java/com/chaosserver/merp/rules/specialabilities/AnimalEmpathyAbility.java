package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;

public class AnimalEmpathyAbility extends SpecialAbilityBase {
    public void apply(MerpCharacter character) {
    }

    public String getName() {
        return "Animal Empathy";
    }

    public String getDescription() {
        return "Empathy with a type of animal: start with one pet or "
            + "loyal companion animal of that type (e.g., falcon, hawk, "
            + "weasel, cat, dog, horse, etc.).  A special +50 bonus for "
            + "Animal Handling when dealing with that animal.  Any "
            + "maneuver on or with such an animal receives a special "
            + "+25 bonus.";
    }
}