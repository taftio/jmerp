package com.chaosserver.merp.rules.specialabilities;

import com.chaosserver.merp.character.MerpCharacter;

/**
 * Base implementation of special abilities.
 *
 * @author Jordan Reed
 */
public abstract class SpecialAbilityBase implements SpecialAbility, Cloneable {
    /** Holds the default if a special ability should allow multiples. */
    protected boolean ALLOW_MULTIPLE_DEFAULT = true;

    /** Holds the validity of a character. */
    protected boolean valid = false;

    public String toString() {
        return getName();
    }


    public void apply(MerpCharacter character) {
    }

    public boolean allowMultiple() {
        return ALLOW_MULTIPLE_DEFAULT;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch(CloneNotSupportedException e) {
            // Eat it.  Yum.
            return null;
        }
    }

    public boolean isValid() {
        return this.valid;
    }

    /**
     * Sets the special abilities validity tag.
     *
     * @param valid the validity of the special ability
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

}