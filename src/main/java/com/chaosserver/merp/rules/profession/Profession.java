package com.chaosserver.merp.rules.profession;

import com.chaosserver.assertion.Assertion;
import com.chaosserver.data.find.Findable;
import com.chaosserver.merp.rules.restriction.IRestricted;
import com.chaosserver.merp.rules.restriction.RestrictionList;
import com.chaosserver.merp.rules.skill.modifier.ISkillModifiable;
import com.chaosserver.merp.rules.skill.modifier.SkillModifierList;
import com.chaosserver.merp.rules.stat.Stat;

/**
 * A profession in the Merp system.
 *
 * @author Jordan Reed
 * @version 1.0
 * @since The Beginning
 */
public class Profession implements IRestricted, ISkillModifiable, Findable {
    /** Holds the name of the profession. */
    private String m_name;

    /** Holds the prime stat. */
    private Stat m_primeStat;

    /** Holds the restriction list. */
    private RestrictionList m_restrictionList;

    /** Holds the skill modifier list. */
    private SkillModifierList m_skillModifierList;

    /**
     * Getter for the name.
     *
     * @return the name
     * @see #setName
     */
    public String getName() {
        return m_name;
    }

    /**
     * Setter for the name.
     *
     * @param name The name
     * @see #getName
     */
    public void setName(String name) {
        m_name = name;
    }

    /**
     * Setter for the restriction list.
     *
     * @param restrictionList the new restriction list
     * @see #getRestrictionList
     */
    public void setRestrictionList(RestrictionList restrictionList) {
        m_restrictionList = restrictionList;
    }

    // Inherits from interface
    public RestrictionList getRestrictionList() {
        return m_restrictionList;
    }


    /**
     * Setter for the skill modifier list.
     *
     * @param skillModifierList The skill modifier list
     * @see #getSkillModifierList
     */
    public void setSkillModifierList(SkillModifierList skillModifierList) {
        m_skillModifierList = skillModifierList;
    }

    // Inherits from interface
    public SkillModifierList getSkillModifierList() {
        return m_skillModifierList;
    }

    /**
     * geter for the prime stat.
     *
     * @return The stat
     * @see #setPrimeStat
     */
    public Stat getPrimeStat() {
        return m_primeStat;
    }

    /**
     * Setter for the prime stat.
     *
     * @param primeStat The new prime stat
     * @see #getPrimeStat
     */
    public void setPrimeStat(Stat primeStat) {
        m_primeStat = primeStat;
    }

    /**
     * Main constructor is private.  No one should be making these
     * except the ProfessionList.
     *
     * @param name The name of the profession
     * @param primeStat The prime stat for the profession
     * @param restrictionList The restriction list for this profession
     */
    Profession(String name, Stat primeStat, RestrictionList restrictionList) {
        Assertion.isNotNull(name, "Profession cannot have null name");
        Assertion.isNotNull(primeStat, "Profession cannot have null prime stat");
        setName(name);
        setPrimeStat(primeStat);
        setRestrictionList(restrictionList);
    }

    /**
     * Emptry Constructor.
     */
    public Profession() {
    }

    // Inherits javadoc from interface
    public String getPkName() {
        return "Name";
    }

    // Inherits javadoc from interface
    public Object getFinder() {
        return ProfessionFinder.instance();
    }

    /**
     * Converts the object to string representation.
     *
     * @return a string representation of the object
     */
     public String toString() {
        return getName();
    }
}
