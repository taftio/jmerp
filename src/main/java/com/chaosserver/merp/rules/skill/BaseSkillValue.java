package com.chaosserver.merp.rules.skill;

import com.chaosserver.merp.character.skill.CharSkill;
import com.chaosserver.merp.character.skill.ICharSkill;

/**
 * Represents a basic skill value.
 * <p>
 * A race's basic skill set is held in these objects to define
 * what a new character from this race starts with in skills.
 * </p>
 *
 * Contains a reference to a skill and to a rank.
 */

public class BaseSkillValue {
    /** The skill the has a value for. */
    protected Skill m_skill;

    /** The value for the skill. */
    protected int m_value = 0;

    /**
     * Empty Constructor.
     */
    public BaseSkillValue() {
    }

    /**
     * Getter for skill.
     *
     * @return The skill
     * @see #setSkill
     */
    public Skill getSkill() {
        return m_skill;
    }

    /**
     * Setter for skill.
     *
     * @param skill The new skill
     * @see #getSkill
     */
    public void setSkill(Skill skill) {
        m_skill = skill;
    }

    /**
     * Getter for value.
     *
     * @return The value
     * @see #setValue
     */
    public int getValue() {
        return m_value;
    }

    /**
     * Setter for value.
     *
     * @param value the new value
     * @see #getValue
     */
    public void setValue(int value) {
        m_value = value;
    }

    /**
     * Creates a character skill from this base skill value.
     *
     * @return A character skill from this base skill value
     */
    public ICharSkill getCharSkill() {
        return new CharSkill(getSkill(), getValue());
    }

    /**
     * Converts the object to string representation.
     *
     * @return a string representation of the object
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("\n----- BaseSkillValue.toString START -----\n");
        sb.append(super.toString() + "\n");
        sb.append("BaseSkillValue.Skill = " + getSkill().getName() + "\n");
        sb.append("BaseSkillValue.Value = " + getValue() + "\n");
        sb.append("----- BaseSkillValue.toString STOP -----\n");
        return sb.toString();

    }
}