package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;

/**
 * A special ability for senory enhancement.
 * <p>
 * The standard version of this is 100' infravision.
 * </p>
 *
 * @author Jordan Reed
 */
public class SensoryEnhancementAbility extends SpecialAbilityBase {
    /** Holds the string of how the sensory enhancement affects the character. */
    protected String sense;

    /**
     * Default constructor has no sense.
     */
    public SensoryEnhancementAbility() {
    }

    /**
     * Takes in the sense that this ability affects.
     *
     * @param sense The sense this is affected.
     */
    public SensoryEnhancementAbility(String sense) {
        setSense(sense);
        setValid(true);
    }

    public String getName() {
        if(null == getSense()) {
            return "Sensory Enhancement";
        }
        else {
            return "Sensory Enhancement: " + getSense();
        }
    }

    public String getDescription() {
        return "Infravision: ability to see sources of heat in darkness.  "
            + "Range is up to 100' (alternatively, any one other sense "
            + "may be enhanced in a similar manner).";
    }

    /**
     * Gets the sense this enhancement applies to.
     *
     * @return The sense this applies to
     * @see #setSense
     */
    public String getSense() {
        return this.sense;
    }

    /**
     * Sets the sense this ability spplies to.
     *
     * @param sense The sense this ability applies to.
     * @see #getSense
     */
    public void setSense(String sense) {
        this.sense = sense;
    }


    public Object clone() {
        SensoryEnhancementAbility sensoryEnhancementAbility = new SensoryEnhancementAbility("Infravision");
        return sensoryEnhancementAbility;
    }
}