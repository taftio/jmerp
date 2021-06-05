package com.chaosserver.merp.rules.profession.modifiers;

import com.chaosserver.merp.rules.skill.Skill;
import com.chaosserver.merp.rules.skill.modifier.BaseSkillModifier;
import com.chaosserver.merp.rules.skill.modifier.ISkillModifier;

/**
 * Holds the skill bonuses associated with a profession.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class ProfessionBonus extends BaseSkillModifier {
    /** Value of the profession bonus. */
    private int m_value = 0;

    /**
     * Constructor.
     */
    public ProfessionBonus() {
        setBonusType(PROFESSION_BONUS);
    }

    /**
     * Setter for the value.
     *
     * @param value The value ofthe modifier value
     * @see #getValue
     */
    public void setValue(int value) {
        m_value = value;
    }

    /**
     * Getter for the value.
     *
     * @return The value ofthe modifier value
     * @see #setValue
     */
    public int getValue() {
        return m_value;
    }

    public String toString() {
        return "ProfessionBonus [" + getAffectedAsString() + ", " + getValue() + "]";
    }
}