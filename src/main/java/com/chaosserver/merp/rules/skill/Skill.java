package com.chaosserver.merp.rules.skill;

import com.chaosserver.merp.rules.stat.Stat;
import com.chaosserver.data.find.Findable;

/**
 * Holds the information relating to a skill
 *
 * A skill contains a lot of information that is used when applied to a
 * character for creation and level gains.
 *
 * @author Jordan Reed
 * @version 1.0
 */
public class Skill implements Findable, Comparable {
    /** Indicates that there is no maximum number of ranks in this skill. */
    public static int NO_MAX = -1;

    /** Holds the name of the skill. */
    private String m_name;

    /** Holds the stat that modified the skill.  This may be null. */
    private Stat m_stat;

    /** Holds a bonus that is always applied to a character. */
    private int m_specialBonus = 0;

    /** Holds the progression list for this stat. */
    private ProgressionList m_progressionList;

    /** Holds the internal ID for the skill. */
    protected int Id;

    /**
     * Holds the maximum ranks a character can have in this skill.
     * Note:  A value of -1 is NO_MAX and indicates there is no
     * maximum number of ranks.
     */
    private int m_maxRanks = NO_MAX;

    /**
     * Contains the number of DP you must transfer into this skill
     * to get on DP in it, if this skill category has any DPs from
     * profession.
     */
    private int m_transferToNonZero = 2;

    /**
     * Contains the number of DP you must transfer into this skill
     * to get one DP in it, if this skill category has no DP from
     * profession.
     */
    private int m_transferToZero = 4;

    /**
     * Empty Constructor.
     */
    public Skill() {
    }

    /**
     * The main constructor takes in everything.
     * <p>
     * A system skill is immutable, thus the constructor takes in
     * a value for every property and it doesn't change.
     * </p>
     *
     * @param name The name of the skill
     * @param stat The stat that bonuses this skill (null if there is not stat)
     * @param specialBonus The bonus that always gets applied with this skill
     * @param progressionList The progression list for this stat
     * @param maxRanks Maximum ranks this skill can have
     * @param transferToNonZero Sets the transferToNonZero property
     * @param transferToZero sets the transferToZero property
     */
    public Skill(String name,
                 Stat stat,
                 int specialBonus,
                 ProgressionList progressionList,
                 int maxRanks,
                 int transferToNonZero,
                 int transferToZero) {


        setName(name);
        setStat(stat);
        setProgressionList(progressionList);
        setMaxRanks(maxRanks);
        setTransferToNonZero(transferToNonZero);
        setTransferToZero(transferToZero);
    }

    // Inherits javadoc from interface
    public String getPkName() {
        return "Name";
    }

    // Inherits javadoc from interface
    public Object getFinder() {
        return SkillFinder.instance();
    }

    /**
     * Getter for the Id for this skill.
     *
     * @return the Id for this skill
     * @see #setId
     */
    public int getId() {
        return this.Id;
    }

    /**
     * Setter for the Id for this skill.
     *
     * @param Id the Id for this skill
     * @see #getId
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * Getter for the name property.
     *
     * @return the skill name
     * @see #setName
     */
    public String getName() {
        return m_name;
    }

    /**
     * Setter for the name property.
     *
     * @param name The new skill name
     * @see #getName
     */
    public void setName(String name) {
        m_name = name;
    }

    /**
     * Getter for the stat property.
     *
     * @return The stat that gives it bonus to this skill
     * @see #setStat
     */
    public Stat getStat() {
        return m_stat;
    }

    /**
     * Setter for the stat property.
     *
     * @param stat The stat that gives its bonus to this skill
     * @see #getStat
     */
    public void setStat(Stat stat) {
        m_stat = stat;
    }

    /**
     * Getter for the special bonus applied to this skill.
     *
     * @return the special bonus
     * @see #setSpecialBonus
     */
    public int getSpecialBonus() {
        return m_specialBonus;
    }

    /**
     * Setter for the special bonus applied to this skill.
     *
     * @param specialBonus the special bonus
     * @see #getSpecialBonus
     */
    public void setSpecialBonus(int specialBonus) {
        m_specialBonus = specialBonus;
    }

    /**
     * Getter for the max ranks this skill may have.
     *
     * @return the max ranks
     * @see #setMaxRanks
     */
    public int getMaxRanks() {
        return m_maxRanks;
    }

    /**
     * Setter for the max ranks this skill may have.
     *
     * @param maxRanks the max ranks
     * @see #getMaxRanks
     */
    public void setMaxRanks(int maxRanks) {
        m_maxRanks = maxRanks;
    }

    /**
     * Getter for the transfer to non zero property.
     *
     * @return the transfer to non zero value
     * @see #setTransferToNonZero
     */
    public int getTransferToNonZero() {
        return m_transferToNonZero;
    }

    /**
     * Setter for the transfer to non zero property.
     *
     * @param transferToNonZero the transfer to non zero value
     * @see #getTransferToNonZero
     */
    public void setTransferToNonZero(int transferToNonZero) {
        m_transferToNonZero = transferToNonZero;
    }

    /**
     * Getter for the transfer to zero property.
     *
     * @return the transfer to zero property
     * @see #setTransferToZero
     */
    public int getTransferToZero() {
        return m_transferToZero;
    }

    /**
     * Setter for the transfer to zero property.
     *
     * @param transferToZero the transfer to zero property
     * @see #getTransferToZero
     */
    public void setTransferToZero(int transferToZero) {
        m_transferToZero = transferToZero;
    }

    /**
     * Setter for the progression list property.
     *
     * @param progressionList The progression list
     * @see #getProgressionList
     */
    public void setProgressionList(ProgressionList progressionList) {
        m_progressionList = progressionList;
    }

    /**
     * Getter for the progression list property.
     *
     * @return the progression list
     * @see #setProgressionList
     */
    public ProgressionList getProgressionList() {
        return m_progressionList;
    }

    /**
     * Converts the object to string representation.
     *
     * @return a string representation of the object
     */
     public String toString() {
        return getName();
    }

    /**
     * Compares this to another Skill based on the Id.
     *
     * @param skill The skill to compare against
     * @return a negative integer, zero, or a positive integer as this
     *         object is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Object skill) {
        return (this.getId() - ((Skill)skill).getId());
    }
}