package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;

public class LightningReactionAbility extends SpecialAbilityBase {
    public void apply(MerpCharacter character) {
    }

    public String getName() {
        return "Lightning Reaction";
    }

    public String getDescription() {
        return "Lightning reactions: +5 to DB and +5 to all OBs.";
    }
}