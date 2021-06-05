package com.chaosserver.merp.rules.skill.modifier;

/**
 * Holds information about a speical bonus.
 *
 * @author Jordan Reed
 */
public class SpecialBonus extends BaseSkillModifier implements Cloneable {
    /** Holds the value of the special bonus. */
    protected int value;

    /**
     * Constructs a special bonus.
     */
    public SpecialBonus() {
        setBonusType(SPECIAL_BONUS);
    }

    /**
     * Sets the value of the special bonus.
     *
     * @param value The value of the bonus
     * @see #getValue
     */
    public void setValue(int value) {
        this.value = value;
    }

    // Inherits from interface
    public int getValue() {
        return this.value;
    }

    /**
     * Converts the object to string representation.
     *
     * @return a string representation of the object
     */
    public String toString() {
        String applyTo;
        return "SpecialBonus [" + getAffectedAsString() + ", " + getValue() + "]";
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch(CloneNotSupportedException e) {
            return null;
        }
    }
}